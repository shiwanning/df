package com.tcg.mis.to.response;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class BaseOperateTo<T> extends RecordTo<T> {
    
    @ApiModelProperty(required = true, value = "操作人名称")
    @NotBlank(message = "can not be blank")
    private String operatorName;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    
}
