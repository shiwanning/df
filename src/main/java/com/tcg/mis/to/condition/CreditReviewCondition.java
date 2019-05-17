package com.tcg.mis.to.condition;

import java.math.BigDecimal;

public class CreditReviewCondition extends OperatorBaseCondition {
	
	private Integer status;
	private BigDecimal leverMultiplier;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getLeverMultiplier() {
		return leverMultiplier;
	}
	public void setLeverMultiplier(BigDecimal leverMultiplier) {
		this.leverMultiplier = leverMultiplier;
	}
}
