package com.tcg.mis.to.request;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.tcg.mis.common.page.OrderVO;
import com.tcg.mis.common.page.PaginationAndOrderVO;

import io.swagger.annotations.ApiParam;

public class PageAndSortTO {

    @ApiParam(value = "排序字段(sortColumn)") 
    private String sortColumn;
    
    @ApiParam(value = "排序类型(sortType)") 
    private String sortType;

    @ApiParam(value = "获取第n页(page)", defaultValue="1", required=true) 
    @Min(value = 1L, message = "current page must be >= 1") 
    @NotNull
    private Integer page = 1;
    
    @ApiParam(value = "分页笔数(size)", defaultValue="10", required=true) 
    @Min(value = 1L, message = "page size must be >= 1") 
    @NotNull
    private Integer size = 10;
    
    @ApiParam(value = "是否要分页(pagable)", defaultValue="true", required=true) 
    @NotNull
    private Boolean pageable = true;

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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getPageable() {
        return pageable;
    }

    public void setPageable(Boolean pageable) {
        this.pageable = pageable;
    }
    
    public PageAndSortTO withDefaultSortColumn(String defaultSortColumn) {
        if(getSortColumn() == null) {
            setSortColumn(defaultSortColumn);
        }
        return this;
    }
    
    public PageAndSortTO withDefaultSortType(String defaultSortType) {
        if(getSortType() == null) {
            setSortType(defaultSortType);
        }
        return this;
    }

	public PageAndSortTO pageable(boolean pageable) {
		this.pageable = pageable;
		return this;
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
        
        PaginationAndOrderVO vo = new PaginationAndOrderVO(getPage(), getSize());
        
        vo.setNeedPagination(getPageable());
        
        vo.setOrders(getSortSet());
        
        return vo;
    }
    
}
