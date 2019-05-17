package com.tcg.mis.common.mybatis.dialect;


import com.tcg.mis.common.page.PaginationVO;

public class OracleDialect implements Dialect {

    /**
     * OFFSET (${currentPage}-1)*${pageSize} ROWS FETCH NEXT ${pageSize} ROWS ONLY
     *
     * @param sql origin sql
     * @param page page vo
     *
     * @return pagination sql
     */
    @Override
    public String changeToPageSql(String sql, PaginationVO page) {
        boolean isForUpdate = false;
        String fixedSql = sql;
        if (sql.trim().toLowerCase().endsWith(" for update")) {
            fixedSql = sql.trim().substring(0, sql.length() - 11);
            isForUpdate = true;
        }

        String pageSql = String.format("%s OFFSET %d ROWS FETCH NEXT %d ROWS ONLY",
                fixedSql, (page.getPage() - 1) * page.getSize(), page.getSize());
        if (isForUpdate) {
            pageSql += " FOR UPDATE";
        }

        return pageSql;
    }
}
