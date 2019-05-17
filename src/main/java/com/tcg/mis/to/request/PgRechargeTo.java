package com.tcg.mis.to.request;

import java.math.BigDecimal;

public class PgRechargeTo {
    String merchantCode;
    String bankId;
    Integer bankAcctRid;
    Integer rechargeType;
    BigDecimal amount;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public Integer getBankAcctRid() {
        return bankAcctRid;
    }

    public void setBankAcctRid(Integer bankAcctRid) {
        this.bankAcctRid = bankAcctRid;
    }

    public Integer getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(Integer rechargeType) {
        this.rechargeType = rechargeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
