package com.tcg.mis.model;

import java.math.BigDecimal;

public class MisMerchantChargeTemplateDetailIntervalrate extends BaseAuditEntity {

	private Long rid;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private BigDecimal rate;
    private Long detailRid;
    
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public BigDecimal getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}
	public BigDecimal getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Long getDetailRid() {
		return detailRid;
	}
	public void setDetailRid(Long detailRid) {
		this.detailRid = detailRid;
	}
	
}
