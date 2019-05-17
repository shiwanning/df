package com.tcg.mis.model.vo;

import java.math.BigDecimal;

public class Merchant {
	private Long merchantId;
	private Long usMerchantId;
	private String merchantCode;
	private String merchantName;
	private String merchantType;
	private Integer status;
	private Long parentId;
	private String upline;
	private Long customerId;
	private Integer createOperator;
	private String currency;
    private BigDecimal leverMultiplier;
    private BigDecimal virtualCashPledge;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getUsMerchantId() {
        return usMerchantId;
    }

    public void setUsMerchantId(Long usMerchantId) {
        this.usMerchantId = usMerchantId;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUpline() {
        return upline;
    }

    public void setUpline(String upline) {
        this.upline = upline;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getCreateOperator() {
        return createOperator;
    }

    public void setCreateOperator(Integer createOperator) {
        this.createOperator = createOperator;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getLeverMultiplier() {
        return leverMultiplier;
    }

    public void setLeverMultiplier(BigDecimal leverMultiplier) {
        this.leverMultiplier = leverMultiplier;
    }

    public BigDecimal getVirtualCashPledge() {
        return virtualCashPledge;
    }

    public void setVirtualCashPledge(BigDecimal virtualCashPledge) {
        this.virtualCashPledge = virtualCashPledge;
    }
}

