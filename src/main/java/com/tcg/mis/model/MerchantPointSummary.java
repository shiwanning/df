package com.tcg.mis.model;

import java.util.Date;

public class MerchantPointSummary{

	private static final long serialVersionUID = 1L;
	
	private java.math.BigDecimal id;
	
	private Date balanceDate;
	
	private String merchantCode;
	
	private Long rechargeNum;
	
	private java.math.BigDecimal rechargeAmount;
	
	private String vendor;
	
	private String subProduct;
	
	private java.math.BigDecimal rates;
	
	private java.math.BigDecimal buyPoints;
	
	private java.math.BigDecimal buyPointsAmount;
	
	private java.math.BigDecimal sellPoints;
	
	private java.math.BigDecimal sellPointsAmount;
	
	private java.math.BigDecimal usedPoints;
	
	private String currency;
	
	private java.math.BigDecimal begainPintNumber;
	
	private java.math.BigDecimal endPointNumber;
	
	private Date startDate;
	
	private Date endDate;

	public void setId(java.math.BigDecimal value) {
		this.id = value;
	}
	
	public java.math.BigDecimal getId() {
		return this.id;
	}
	
	public void setBalanceDate(Date value) {
		this.balanceDate = value;
	}
	
	public Date getBalanceDate() {
		return this.balanceDate;
	}
	
	public void setMerchantCode(String value) {
		this.merchantCode = value;
	}
	
	public String getMerchantCode() {
		return this.merchantCode;
	}
	
	public void setRechargeNum(Long value) {
		this.rechargeNum = value;
	}
	
	public Long getRechargeNum() {
		return this.rechargeNum;
	}
	
	public void setRechargeAmount(java.math.BigDecimal value) {
		this.rechargeAmount = value;
	}
	
	public java.math.BigDecimal getRechargeAmount() {
		return this.rechargeAmount;
	}
	
	public void setVendor(String value) {
		this.vendor = value;
	}
	
	public String getVendor() {
		return this.vendor;
	}
	
	public void setSubProduct(String value) {
		this.subProduct = value;
	}
	
	public String getSubProduct() {
		return this.subProduct;
	}
	
	public void setRates(java.math.BigDecimal value) {
		this.rates = value;
	}
	
	public java.math.BigDecimal getRates() {
		return this.rates;
	}
	
	public void setBuyPoints(java.math.BigDecimal value) {
		this.buyPoints = value;
	}
	
	public java.math.BigDecimal getBuyPoints() {
		return this.buyPoints;
	}
	
	public void setBuyPointsAmount(java.math.BigDecimal value) {
		this.buyPointsAmount = value;
	}
	
	public java.math.BigDecimal getBuyPointsAmount() {
		return this.buyPointsAmount;
	}
	
	public void setSellPoints(java.math.BigDecimal value) {
		this.sellPoints = value;
	}
	
	public java.math.BigDecimal getSellPoints() {
		return this.sellPoints;
	}
	
	public void setSellPointsAmount(java.math.BigDecimal value) {
		this.sellPointsAmount = value;
	}
	
	public java.math.BigDecimal getSellPointsAmount() {
		return this.sellPointsAmount;
	}
	
	public void setUsedPoints(java.math.BigDecimal value) {
		this.usedPoints = value;
	}
	
	public java.math.BigDecimal getUsedPoints() {
		return this.usedPoints;
	}
	
	public void setCurrency(String value) {
		this.currency = value;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	
	public void setBegainPintNumber(java.math.BigDecimal value) {
		this.begainPintNumber = value;
	}
	
	public java.math.BigDecimal getBegainPintNumber() {
		return this.begainPintNumber;
	}
	
	public void setEndPointNumber(java.math.BigDecimal value) {
		this.endPointNumber = value;
	}
	
	public java.math.BigDecimal getEndPointNumber() {
		return this.endPointNumber;
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
	
}
