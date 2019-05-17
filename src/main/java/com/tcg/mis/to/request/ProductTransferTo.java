package com.tcg.mis.to.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class ProductTransferTo {
    @NotNull
    String merchantCode;
    @NotNull
    String fromProduct;
    String fromSubProduct;
    @NotNull
    Integer fromAccountTypeId;
    String toProduct;
    String toSubProduct;
    @NotNull
    Integer toAccountTypeId;
    @NotNull
    BigDecimal amount;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getFromProduct() {
        return fromProduct;
    }

    public void setFromProduct(String fromProduct) {
        this.fromProduct = fromProduct;
    }

    public String getFromSubProduct() {
        return fromSubProduct;
    }

    public void setFromSubProduct(String fromSubProduct) {
        this.fromSubProduct = fromSubProduct;
    }

    public Integer getFromAccountTypeId() {
        return fromAccountTypeId;
    }

    public void setFromAccountTypeId(Integer fromAccountTypeId) {
        this.fromAccountTypeId = fromAccountTypeId;
    }

    public String getToProduct() {
        return toProduct;
    }

    public void setToProduct(String toProduct) {
        this.toProduct = toProduct;
    }

    public String getToSubProduct() {
        return toSubProduct;
    }

    public void setToSubProduct(String toSubProduct) {
        this.toSubProduct = toSubProduct;
    }

    public Integer getToAccountTypeId() {
        return toAccountTypeId;
    }

    public void setToAccountTypeId(Integer toAccountTypeId) {
        this.toAccountTypeId = toAccountTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
