package com.tcg.mis.to.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class ProductRechargeTo {
    @NotNull
    String merchantCode;
    @NotNull
    String product;
    String subProduct;
    @NotNull
    Integer toAccountTypeId;
    @NotNull
    BigDecimal amount;
    @NotNull
    Integer amountType;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSubProduct() {
        return subProduct;
    }

    public void setSubProduct(String subProduct) {
        this.subProduct = subProduct;
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

    public Integer getAmountType() {
        return amountType;
    }

    public void setAmountType(Integer amountType) {
        this.amountType = amountType;
    }
}
