package com.tcg.mis.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * Description: Page value <br/>
 *
 * @author Eddie
 */
public class PageValue<T, S> {
    
    @ApiModelProperty(required = true)
    private List<T> list = Collections.emptyList();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private S footer;

    @ApiModelProperty(required = true, example = "1")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer total;

    @ApiModelProperty(required = true, example = "10")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer totalPages;

    public PageValue() {
    }

    public PageValue(List<T> list) {
        setList(list);
    }

    public PageValue(S footer) {
        setFooter(footer);
    }

    public PageValue(List<T> list, S footer) {
        setList(list);
        setFooter(footer);
    }

    public PageValue(List<T> list, Integer total, Integer totalPages) {
        setList(list);
        setTotal(total);
        setTotalPages(totalPages);
    }

    public PageValue(List<T> list, Integer total, Integer totalPages, S footer) {
        setList(list);
        setFooter(footer);
        setTotal(total);
        setTotalPages(totalPages);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list == null ? Collections.<T>emptyList() : list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public S getFooter() {
        return footer;
    }

    public void setFooter(S footer) {
        this.footer = footer;
    }
}
