package com.tcg.mis.model.vo;

import com.tcg.mis.model.BaseAuditEntity;
import com.tcg.mis.model.MerchantBillDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MerchantBillVo extends BaseAuditEntity {

	private static final long serialVersionUID = 1L;

	private String billNo;

	private Date balanceDate;

	private Integer billMonthly;

	private String merchantCode;

	private String merchantName;

	private BigDecimal customerNo;

	private BigDecimal billAmount;

	private BigDecimal paidAmount;

	private BigDecimal unpaidAmount;

	private BigDecimal commission;

	private String refer;

	private String currency;

	private Integer timeLimit;

	private Integer status;

	private String remark;

	private String financeManagerEmail;

	private Date startDate;

	private Date endDate;

	private List<MerchantBillDetail> details;

	public void setBillNo(String value) {
		this.billNo = value;
	}

	public String getBillNo() {
		return this.billNo;
	}

	public void setBalanceDate(Date value) {
		this.balanceDate = value;
	}

	public Date getBalanceDate() {
		return this.balanceDate;
	}

	public void setBillMonthly(Integer value) {
		this.billMonthly = value;
	}

	public Integer getBillMonthly() {
		return this.billMonthly;
	}

	public void setMerchantCode(String value) {
		this.merchantCode = value;
	}

	public String getMerchantCode() {
		return this.merchantCode;
	}

	public void setCustomerNo(BigDecimal value) {
		this.customerNo = value;
	}

	public BigDecimal getCustomerNo() {
		return this.customerNo;
	}

	public void setBillAmount(BigDecimal value) {
		this.billAmount = value;
	}

	public BigDecimal getBillAmount() {
		return this.billAmount;
	}

	public void setPaidAmount(BigDecimal value) {
		this.paidAmount = value;
	}

	public BigDecimal getPaidAmount() {
		return this.paidAmount;
	}

	public void setUnpaidAmount(BigDecimal value) {
		this.unpaidAmount = value;
	}

	public BigDecimal getUnpaidAmount() {
		return this.unpaidAmount;
	}

	public void setCommission(BigDecimal value) {
		this.commission = value;
	}

	public BigDecimal getCommission() {
		return this.commission;
	}
	
	public void setRefer(String value) {
		this.refer = value;
	}
	
	public String getRefer() {
		return this.refer;
	}
	
	public void setCurrency(String value) {
		this.currency = value;
	}
	
	public String getCurrency() {
		return this.currency;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setFinanceManagerEmail(String value) {
		this.financeManagerEmail = value;
	}
	
	public String getFinanceManagerEmail() {
		return this.financeManagerEmail;
	}
	
	public void setStartDate(Date value) {
		this.startDate = value;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public void setEndDate(Date value) {
		this.endDate = value;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public List<MerchantBillDetail> getDetails() {
		return details;
	}

	public void setDetails(List<MerchantBillDetail> details) {
		this.details = details;
	}
}
