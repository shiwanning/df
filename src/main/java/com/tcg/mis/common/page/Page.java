package com.tcg.mis.common.page;

import java.io.Serializable;

import io.swagger.annotations.ApiParam;

public class Page implements Serializable {
    @ApiParam(defaultValue = "1")
    private Integer page = 1;

    @ApiParam(defaultValue = "10")
    private Integer size = 10;

    @ApiParam(hidden = true)
    private Integer total = 0;

    public Page() {
    }


    /**
     * @param page
     * @param size
     */
    public Page(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    /**
     * @param page
     * @param size
     * @param total
     */
    public Page(Integer page, Integer size, Integer total) {
        super();
        this.page = page;
        this.size = size;
        this.total = total;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
