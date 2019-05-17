package com.tcg.mis.to.condition;


import com.google.common.collect.Lists;
import com.tcg.mis.common.log.TcgLogFactory;
import com.tcg.mis.common.page.OrderVO;
import com.tcg.mis.common.page.Page;
import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.to.request.PageAndSortTO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Description: Base <br/>
 */
public class BaseCondition extends Page {
    
    private static final Logger LOGGER = TcgLogFactory.getLogger(BaseCondition.class);

    private String merchantCode;
    // 会员或代理ID
    private Integer customerId;
    private String customerName;
    // 下级会员或下级代理ID
    private Integer subordinateId;
    private String subordinateName;
    // 游戏厂商
    private String vendor;
    // 游戏类型
    private String gameCategory;
    private Date balanceDate;
    private Date startDate = DateUtils.truncate(DateTime.now().toDate(), Calendar.DATE);
    private Date endDate = DateUtils.truncate(DateTime.now().toDate(), Calendar.DATE);
    private String sortColumn;
    private String sortType;
    private boolean pageable = true;

    public void setPageAndSortTO(PageAndSortTO to) {
        this.setPage(to.getPage());
        this.setSize(to.getSize());
        this.setSortColumn(to.getSortColumn());
        this.setSortType(to.getSortType());
        this.setPageable(to.getPageable());
    }
    
    public boolean isPageable() {
        return pageable;
    }

    public void setPageable(boolean pageable) {
        this.pageable = pageable;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getSubordinateId() {
        return subordinateId;
    }

    public void setSubordinateId(Integer subordinateId) {
        this.subordinateId = subordinateId;
    }

    public String getSubordinateName() {
        return subordinateName;
    }

    public void setSubordinateName(String subordinateName) {
        this.subordinateName = subordinateName;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getGameCategory() {
        return gameCategory;
    }

    public void setGameCategory(String gameCategory) {
        this.gameCategory = gameCategory;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartDateStr() {
        return DateFormatUtils.format(startDate, "yyyy-MM-dd");
    }
    
    public String getEndDateStr() {
        return DateFormatUtils.format(endDate, "yyyy-MM-dd");
    }
    
    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public List<OrderVO> getSortSet() {

        List<OrderVO> result = Lists.newLinkedList();

        if (sortColumn == null) {
            return result;
        }

        String[] sortColumnArray = sortColumn.split(",");
        String[] sortTypesArray = sortType.split(",");

        for (int i = 0; i < sortColumnArray.length ; i++) {
            result.add(new OrderVO(StringUtils.trim(sortColumnArray[i]), StringUtils.trim(sortTypesArray[i])));
        }

        return result;
    }
    
    public PaginationAndOrderVO generatePaginationAndOrderVO() {
        
        PaginationAndOrderVO page = new PaginationAndOrderVO(getPage(), getSize());
        
        page.setNeedPagination(isPageable());
        
        page.setOrders(getSortSet());
        
        return page;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            final Class bcc = this.getClass();
            Field[] fields = bcc.getDeclaredFields();
            for (Field f : fields) {
                if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                    continue;
                }
                String fieldName = f.getName();
                if (fieldName.equalsIgnoreCase("pageable")) {
                    continue;
                }
                String getMethodName = "get".concat(fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1, fieldName.length())));
                final Method bcGetMethod = bcc.getMethod(getMethodName);
                Object value = bcGetMethod.invoke(this);
                sb.append(fieldName).append("=").append(value).append(",");
            }
        } catch (Exception e) {
            LOGGER.error("condition toString error.", e);
        }
        return sb.delete(sb.length()-1, sb.length()).toString();
    }
}
