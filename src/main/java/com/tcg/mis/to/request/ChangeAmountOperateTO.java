package com.tcg.mis.to.request;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import io.swagger.annotations.ApiModelProperty;

public class ChangeAmountOperateTO<T> extends OperateWithRemarkTO<T> {

    @ApiModelProperty(required = true, value = "修改後金額")
    @Digits(integer = 8, fraction = 4)
    @DecimalMin("0.0001")
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
}
