package com.tcg.mis.model;

import java.math.BigDecimal;
import java.util.Date;

public class MerchantBillDiscountLog{

	private static final long serialVersionUID = 1L;
	
	private java.math.BigDecimal id;
	
	private java.math.BigDecimal billMasterId;
	
	private Integer discountType;
	
	private BigDecimal discountAmount;
	
	private String operator;
	
	private Date createDate;

	public void setId(java.math.BigDecimal value) {
		this.id = value;
	}
	
	public java.math.BigDecimal getId() {
		return this.id;
	}
	
	public void setBillMasterId(java.math.BigDecimal value) {
		this.billMasterId = value;
	}
	
	public java.math.BigDecimal getBillMasterId() {
		return this.billMasterId;
	}
	
	public void setDiscountType(Integer value) {
		this.discountType = value;
	}
	
	public Integer getDiscountType() {
		return this.discountType;
	}
	
	public void setDiscountAmount(BigDecimal value) {
		this.discountAmount = value;
	}
	
	public BigDecimal getDiscountAmount() {
		return this.discountAmount;
	}
	
	public void setOperator(String value) {
		this.operator = value;
	}
	
	public String getOperator() {
		return this.operator;
	}
	
	public void setCreateDate(Date value) {
		this.createDate = value;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
	
}
