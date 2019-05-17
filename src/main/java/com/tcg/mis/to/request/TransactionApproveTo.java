package com.tcg.mis.to.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class TransactionApproveTo {
	
	@NotNull
	private String orderNo;

	private BigDecimal amount;

	@NotNull
	private Boolean approve;

	private String remark;

	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Boolean getApprove() {
		return approve;
	}
	public void setApprove(Boolean approve) {
		this.approve = approve;
	}
	
}
