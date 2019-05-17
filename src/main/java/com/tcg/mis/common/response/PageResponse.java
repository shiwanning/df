package com.tcg.mis.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcg.mis.common.page.PaginationAndOrderVO;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * Description: page value <br/>
 *
 * @author Eddie
 */
public class PageResponse<T, S> extends BaseResponse implements Iterable<T> {
    private PageValue<T, S> value;

    public static <T, S> PageResponse<T, S> emptyPage() {
        return new PageResponse<>(Collections.<T>emptyList());
    }

    public static <T, S> PageResponse<T, S> emptyPage(int total, int totalPages) {
        PageResponse<T, S> r = new PageResponse<>(Collections.<T>emptyList());
        r.value = new PageValue<>();
        r.value.setTotal(total);
        r.value.setTotalPages(totalPages);
        return r;
    }

    public PageResponse(List<T> list) {
        super(true);
        this.value = new PageValue<>();
        this.value.setList(list);
    }

    public PageResponse(S footer) {
        super(true);
        this.value = new PageValue<>();
        this.value.setFooter(footer);
    }

    public PageResponse(List<T> list, S footer) {
        super(true);
        this.value = new PageValue<>();
        this.value.setList(list);
        this.value.setFooter(footer);
    }

    public PageResponse(List<T> list, PaginationAndOrderVO page) {
        super(true);
        this.value = new PageValue<>();
        this.value.setList(list);
        this.value.setTotal(page.getTotal());
        this.value.setTotalPages(page.getTotalPages());
    }

    public PageResponse(List<T> list, PaginationAndOrderVO page, S footer) {
        super(true);
        this.value = new PageValue<>();
        this.value.setList(list);
        this.value.setTotal(page.getTotal());
        this.value.setTotalPages(page.getTotalPages());
        this.value.setFooter(footer);
    }

    public PageResponse(List<T> list, Integer total, Integer totalPages) {
        super(true);
        this.value = new PageValue<>();
        this.value.setList(list);
        this.value.setTotal(total);
        this.value.setTotalPages(totalPages);
    }

    public PageResponse(List<T> list, Integer total, Integer totalPages, S footer) {
        super(true);
        this.value = new PageValue<>();
        this.value.setList(list);
        this.value.setFooter(footer);
        this.value.setTotal(total);
        this.value.setTotalPages(totalPages);
    }

    public PageValue<T, S> getValue() {
        return value;
    }

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public Integer getTotal() {
        return this.value.getTotal();
    }

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public Integer getTotalPages() {
        return this.value.getTotalPages();
    }

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    public S getFooter() {
        return this.value.getFooter();
    }

    @Override
    public Iterator<T> iterator() {
        if (value != null && CollectionUtils.isNotEmpty(value.getList())) {
            return value.getList().iterator();
        }
        return Collections.emptyIterator();
    }

}
