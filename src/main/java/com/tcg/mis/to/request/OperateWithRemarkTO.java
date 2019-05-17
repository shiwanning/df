package com.tcg.mis.to.request;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class OperateWithRemarkTO<T> extends BaseOperateTO<T> {

    @ApiModelProperty(required = true, value = "备注")
    @NotBlank(message = "can not be blank")
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
