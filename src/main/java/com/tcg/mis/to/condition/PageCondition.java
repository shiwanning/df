package com.tcg.mis.to.condition;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.tcg.mis.common.page.OrderVO;
import com.tcg.mis.common.page.Page;
import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.to.request.PageAndSortTO;

public class PageCondition extends Page {

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
}
