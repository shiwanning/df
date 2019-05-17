package com.tcg.mis.common.page;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 分页及排序控件VO对象
 * 2014-8-22 上午09:22:55
 */
public class PaginationAndOrderVO {
    //传入mybatis参数时map的key值
    public final static String PAGE_KEY = "MUST_PAGE_ORDER";

    private PaginationVO page;
    private List<OrderVO> orders;
    private boolean isNeedPagination = true;

    public PaginationAndOrderVO() {
        isNeedPagination = false;
    }

    public PaginationAndOrderVO(int page, int size) {
        this.page = new PaginationVO(page, size);
    }

    public PaginationAndOrderVO(int page, int size, boolean needPagination) {
        this.page = new PaginationVO(page, size);
        isNeedPagination = needPagination;
    }

    public PaginationVO getPage() {
        return page;
    }

    public boolean isNeedPagination() {
        return isNeedPagination;
    }

    public PaginationAndOrderVO setNeedPagination(boolean needPagination) {
        isNeedPagination = needPagination;
        return this;
    }

    public int getTotal() {
        return page.getTotal();
    }

    public void setTotal(int total) {
        this.page.setTotal(total);
    }
    
    // 让 sql 不要 count
    public PaginationAndOrderVO disableCount() {
        this.page.setTotal(0);
        return this;
    }

    public int getTotalPages() {
        return (page.getTotal() + page.getSize() - 1) / page.getSize();
    }

    public void setPage(PaginationVO page) {
        this.page = page;
    }

    public List<OrderVO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderVO> orders) {
        this.orders = orders;
    }

    public PaginationAndOrderVO addSort(String key, String direction) {
        if (CollectionUtils.isEmpty(orders)) {
            orders = new ArrayList<>();
        }

        if (!StringUtils.equalsAnyIgnoreCase(direction, "asc", "desc")) {
            return this;
        }

        orders.add(new OrderVO(StringUtils.trim(key), StringUtils.trim(direction)));
        return this;
    }

    public PaginationAndOrderVO addSort(String key, String direction, Integer nullFirstOrLast) {
        if (CollectionUtils.isEmpty(orders)) {
            orders = new ArrayList<>();
        }

        if (!StringUtils.equalsAnyIgnoreCase(direction, "asc", "desc")) {
            return this;
        }

        orders.add(new OrderVO(StringUtils.trim(key), StringUtils.trim(direction), nullFirstOrLast));
        return this;
    }

    public void removeSort() {
        this.orders = new ArrayList<>();
    }
}
