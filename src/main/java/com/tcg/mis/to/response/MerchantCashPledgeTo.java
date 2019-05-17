package com.tcg.mis.to.response;

import java.math.BigDecimal;

public class MerchantCashPledgeTo {
	
	private String merchantCode;
	private BigDecimal levelMultiplier;
	private BigDecimal cashPledgeAmount;
	private BigDecimal totalAmount;

	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public BigDecimal getLevelMultiplier() {
		return levelMultiplier;
	}
	public void setLevelMultiplier(BigDecimal levelMultiplier) {
		this.levelMultiplier = levelMultiplier;
	}
	public BigDecimal getCashPledgeAmount() {
		return cashPledgeAmount;
	}
	public void setCashPledgeAmount(BigDecimal cashPledgeAmount) {
		this.cashPledgeAmount = cashPledgeAmount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
