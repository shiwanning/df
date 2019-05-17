package com.tcg.mis.to.response;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class RecordTo<T> {
    
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

