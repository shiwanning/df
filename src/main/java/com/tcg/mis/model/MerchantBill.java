package com.tcg.mis.model;

import com.tcg.mis.to.response.MisRechargeTo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MerchantBill extends BaseAuditEntity {

	private static final long serialVersionUID = 1L;

	private java.math.BigDecimal billMasterId;

	private String billNo;

	private Date balanceDate;

	private Integer billMonthly;

	private String merchantCode;

	private java.math.BigDecimal customerNo;

	private java.math.BigDecimal billAmount;

	private java.math.BigDecimal paidAmount;

	private java.math.BigDecimal unpaidAmount;

	private java.math.BigDecimal discountAmount;

	private java.math.BigDecimal commission;

	private String refer;

	private String currency;

	private Integer timeLimit;

	private Integer status;

	private String remark;

	private Boolean overdue;

	private String financeManagerEmail;

	private Date startDate;

	private Date endDate;

	private String merchantName;


	private List<MerchantBillDetail> details;

	private List<MisRechargeDetail> rechargeDetails;

	private List<MisRechargeTo> rechargeTos;

	public BigDecimal getBillMasterId() {
		return billMasterId;
	}

	public void setBillMasterId(BigDecimal billMasterId) {
		this.billMasterId = billMasterId;
	}

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
	
	public void setCustomerNo(java.math.BigDecimal value) {
		this.customerNo = value;
	}
	
	public java.math.BigDecimal getCustomerNo() {
		return this.customerNo;
	}
	
	public void setBillAmount(java.math.BigDecimal value) {
		this.billAmount = value;
	}
	
	public java.math.BigDecimal getBillAmount() {
		return this.billAmount;
	}
	
	public void setPaidAmount(java.math.BigDecimal value) {
		this.paidAmount = value;
	}
	
	public java.math.BigDecimal getPaidAmount() {
		return this.paidAmount;
	}
	
	public void setUnpaidAmount(java.math.BigDecimal value) {
		this.unpaidAmount = value;
	}
	
	public java.math.BigDecimal getUnpaidAmount() {
		return this.unpaidAmount;
	}
	
	public void setCommission(java.math.BigDecimal value) {
		this.commission = value;
	}
	
	public java.math.BigDecimal getCommission() {
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

	public List<MisRechargeDetail> getRechargeDetails() {
		return rechargeDetails;
	}

	public void setRechargeDetails(List<MisRechargeDetail> rechargeDetails) {
		this.rechargeDetails = rechargeDetails;
	}

	public List<MisRechargeTo> getRechargeTos() {
		return rechargeTos;
	}

	public void setRechargeTos(List<MisRechargeTo> rechargeTos) {
		this.rechargeTos = rechargeTos;
	}

	public Boolean getOverdue() {
		return overdue;
	}

	public void setOverdue(Boolean overdue) {
		this.overdue = overdue;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}
}
