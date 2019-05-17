package com.tcg.mis.common.mybatis.interceptor;


import com.tcg.mis.common.log.TcgLogFactory;
import com.tcg.mis.common.mybatis.dialect.DialectFactory;
import com.tcg.mis.common.page.OrderVO;
import com.tcg.mis.common.page.PaginationAndOrderVO;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class PageInterceptor extends MyBatisInterceptor {
    private static final Logger LOGGER = TcgLogFactory.getLogger(PageInterceptor.class);
    private String dialect; // 使用哪种数据库方言

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        // 元数据
        MetaObject metaStatementHandler = getMetaStatementHandler(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");

        BoundSql boundSql = statementHandler.getBoundSql();
        Object parameterObject = boundSql.getParameterObject(); // 传入参数对象
        String originalSql = boundSql.getSql();// 得到原始sql语句

        // other support interface param: page
        // 只有為select查詢語句時才進行下一步
        final String pageParameterName = "delegate.boundSql.parameterObject.page";
        
        boolean hasPageParameter = parameterObject != null && metaStatementHandler.hasGetter(pageParameterName) && metaStatementHandler.getValue(pageParameterName) instanceof PaginationAndOrderVO;
        
        if (hasPageParameter) {
            PaginationAndOrderVO page = (PaginationAndOrderVO) metaStatementHandler.getValue(pageParameterName);
            String finalSql = gerenteSortSql(page, originalSql);
            if(page.isNeedPagination()) {
            	// 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
                metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
                metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);

                if (page.getTotal() == -1) {
                    // 表示从未取得过总记录数，需要取得总记录数
                    Connection connection = (Connection) invocation.getArgs()[0];
                    // 重设分页参数里的总页数等
                    setPageParameter(originalSql, connection, mappedStatement, boundSql, page);
                }
                finalSql = DialectFactory.createDialect(dialect).changeToPageSql(finalSql, page.getPage());
            }
            
            FieldUtils.writeField(boundSql, "sql", finalSql, true);
            
        }
        

        return invocation.proceed();
    }

    private String gerenteSortSql(PaginationAndOrderVO page, String originalSql) {
    	String sortedSql = originalSql;
        final List<OrderVO> orders = removeNullSort(page.getOrders());
        if (CollectionUtils.isNotEmpty(orders)) {
            StringBuilder orderSQL = new StringBuilder(" ORDER BY ");
            for (OrderVO order : orders) {
                orderSQL.append(order);
                if(order.getNullLastOrFirst() != null) {
                    if(order.getNullLastOrFirst() == 1) {
                        orderSQL.append(" NULLS FIRST");
                    } else if(order.getNullLastOrFirst() == 2) {
                        orderSQL.append(" NULLS LAST");
                    }
                }
                orderSQL.append(",");
            }
            orderSQL.deleteCharAt(orderSQL.length() - 1);
            sortedSql = String.format("SELECT * FROM (%s) %s", sortedSql, orderSQL.toString());
        }
		return sortedSql;
	}

	private List<OrderVO> removeNullSort(Collection<OrderVO> orders) {
        if (CollectionUtils.isEmpty(orders)) {
            return Collections.emptyList();
        }
        Map<String, OrderVO> orderMap = new LinkedHashMap<>();
        for (OrderVO order : orders) {
            if (StringUtils.isNotBlank(order.getKey())) {
                orderMap.put(order.getKey(), order);
            }
        }

        return new ArrayList<>(orderMap.values());
    }

    private String formatSql(String sql) {
        return sql.replaceAll("\n", "").replaceAll("\t", " ").replaceAll("\\s+", " ");
    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }
    
    /**
     * 从数据库里查询总的记录数，回写进分页参数<code>PaginationVO</code>,这样调用
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, PaginationAndOrderVO page) throws SQLException, ReflectiveOperationException {
        // 记录总记录数
        String countSql = "SELECT count(*) FROM (" + sql + ") a ";
        LOGGER.debug("=========page:selectTotalItems: {} ======", formatSql(countSql));
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());

            Field metaParamsField = getFieldByFieldName(boundSql, "metaParameters");
            if (metaParamsField != null) {
                MetaObject mo = (MetaObject) getValueByFieldName(boundSql, "metaParameters");
                setValueByFieldName(countBS, "metaParameters", mo);
            }

            Field additionalField = getFieldByFieldName(boundSql, "additionalParameters");
            if (additionalField != null) {
                Map<String, Object> map = (Map<String, Object>) getValueByFieldName(boundSql, "additionalParameters");
                setValueByFieldName(countBS, "additionalParameters", map);
            }

            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());

            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotal(totalCount);
            LOGGER.debug("=========page:selectTotalItems={}======", totalCount);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignored) {
                LOGGER.error("Ignore this exception.", ignored);
            }
            try {
                if (countStmt != null) {
                    countStmt.close();
                }
            } catch (SQLException ignored) {
                LOGGER.error("Ignore this exception.", ignored);
            }
        }
    }

    /**
     * 对SQL参数(?)设值
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     *
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
            }
        }
        return null;
    }

    public static Object getValueByFieldName(Object obj, String fieldName) throws IllegalAccessException {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if (field != null) {
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    public static void setValueByFieldName(Object obj, String fieldName, Object value) throws ReflectiveOperationException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }
}