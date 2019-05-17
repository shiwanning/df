package com.tcg.mis.to.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class RecordTO<T> {
    
    @ApiModelProperty(required = true, value = "记录ID")
    @NotNull(message = "can not be null")
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}

