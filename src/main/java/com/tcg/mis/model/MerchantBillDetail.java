package com.tcg.mis.model;

import java.util.Date;

public class MerchantBillDetail extends BaseAuditEntity {

	private static final long serialVersionUID = 1L;

	private java.math.BigDecimal id;

	private java.math.BigDecimal billMasterId;

	private String productName;

	private String subProductName;

	private java.math.BigDecimal lowestLimitFee;

	private Boolean negativeAccumulation;

	private Boolean rateLadder;

	private java.math.BigDecimal gameLossesAmount;

	private java.math.BigDecimal gameWinningAmount;

	private java.math.BigDecimal plantBettingAmount;

	private java.math.BigDecimal gamePnlAmount;

	private java.math.BigDecimal grossWinAmount;

	private java.sql.Clob rates;

	private java.math.BigDecimal amount;

	private java.math.BigDecimal tcgExchangeRate;

	private String settlementCurrency;

	private java.math.BigDecimal settlementAmount;

	private Integer status;

	private Date createTime;

	private String createOperatorName;

	private Date updateTime;

	private String updateOperatorName;

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
	
	public void setProductName(String value) {
		this.productName = value;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setSubProductName(String value) {
		this.subProductName = value;
	}
	
	public String getSubProductName() {
		return this.subProductName;
	}
	
	public void setLowestLimitFee(java.math.BigDecimal value) {
		this.lowestLimitFee = value;
	}
	
	public java.math.BigDecimal getLowestLimitFee() {
		return this.lowestLimitFee;
	}
	
	public void setNegativeAccumulation(Boolean value) {
		this.negativeAccumulation = value;
	}
	
	public Boolean getNegativeAccumulation() {
		return this.negativeAccumulation;
	}
	
	public void setRateLadder(Boolean value) {
		this.rateLadder = value;
	}
	
	public Boolean getRateLadder() {
		return this.rateLadder;
	}
	
	public void setGameLossesAmount(java.math.BigDecimal value) {
		this.gameLossesAmount = value;
	}
	
	public java.math.BigDecimal getGameLossesAmount() {
		return this.gameLossesAmount;
	}
	
	public void setGameWinningAmount(java.math.BigDecimal value) {
		this.gameWinningAmount = value;
	}
	
	public java.math.BigDecimal getGameWinningAmount() {
		return this.gameWinningAmount;
	}
	
	public void setPlantBettingAmount(java.math.BigDecimal value) {
		this.plantBettingAmount = value;
	}
	
	public java.math.BigDecimal getPlantBettingAmount() {
		return this.plantBettingAmount;
	}
	
	public void setGamePnlAmount(java.math.BigDecimal value) {
		this.gamePnlAmount = value;
	}
	
	public java.math.BigDecimal getGamePnlAmount() {
		return this.gamePnlAmount;
	}
	
	public void setGrossWinAmount(java.math.BigDecimal value) {
		this.grossWinAmount = value;
	}
	
	public java.math.BigDecimal getGrossWinAmount() {
		return this.grossWinAmount;
	}
	
	public void setRates(java.sql.Clob value) {
		this.rates = value;
	}
	
	public java.sql.Clob getRates() {
		return this.rates;
	}
	
	public void setAmount(java.math.BigDecimal value) {
		this.amount = value;
	}
	
	public java.math.BigDecimal getAmount() {
		return this.amount;
	}
	
	public void setTcgExchangeRate(java.math.BigDecimal value) {
		this.tcgExchangeRate = value;
	}

	public java.math.BigDecimal getTcgExchangeRate() {
		return this.tcgExchangeRate;
	}

	public void setSettlementCurrency(String value) {
		this.settlementCurrency = value;
	}
	
	public Object getSettlementCurrency() {
		return this.settlementCurrency;
	}
	
	public void setSettlementAmount(java.math.BigDecimal value) {
		this.settlementAmount = value;
	}
	
	public java.math.BigDecimal getSettlementAmount() {
		return this.settlementAmount;
	}
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateOperatorName(String value) {
		this.createOperatorName = value;
	}
	
	public String getCreateOperatorName() {
		return this.createOperatorName;
	}
	
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateOperatorName(String value) {
		this.updateOperatorName = value;
	}
	
	public String getUpdateOperatorName() {
		return this.updateOperatorName;
	}
	
}
