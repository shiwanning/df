package com.tcg.mis.to.condition;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeTransactionCondition extends PageCondition {
	
	private String merchantCode;
	private String orderNo;
	private Long rid;
	private Integer status;
	private String bankType;
	private BigDecimal minRequestAmount;
	private BigDecimal maxRequestAmount;
	private Date requestStartDate;
	private Date requestEndDate;
	private Date payStartDate;
    private Date payEndDate;
    private String message;
	private Integer rechargeType;
	private BigDecimal billingId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public BigDecimal getMinRequestAmount() {
		return minRequestAmount;
	}
	public void setMinRequestAmount(BigDecimal minRequestAmount) {
		this.minRequestAmount = minRequestAmount;
	}
	public BigDecimal getMaxRequestAmount() {
		return maxRequestAmount;
	}
	public void setMaxRequestAmount(BigDecimal maxRequestAmount) {
		this.maxRequestAmount = maxRequestAmount;
	}
	public Date getRequestStartDate() {
		return requestStartDate;
	}
	public void setRequestStartDate(Date requestStartDate) {
		this.requestStartDate = requestStartDate;
	}
	public Date getRequestEndDate() {
		return requestEndDate;
	}
	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = requestEndDate;
	}
	public Date getPayStartDate() {
		return payStartDate;
	}
	public void setPayStartDate(Date payStartDate) {
		this.payStartDate = payStartDate;
	}
	public Date getPayEndDate() {
		return payEndDate;
	}
	public void setPayEndDate(Date payEndDate) {
		this.payEndDate = payEndDate;
	}

	public Integer getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(Integer rechargeType) {
		this.rechargeType = rechargeType;
	}


	public BigDecimal getBillingId() {
		return billingId;
	}

	public void setBillingId(BigDecimal billingId) {
		this.billingId = billingId;
	}
}
