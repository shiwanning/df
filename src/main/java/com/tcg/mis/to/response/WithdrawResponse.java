package com.tcg.mis.to.response;

import java.math.BigDecimal;

public class WithdrawResponse extends PaymentSystemResponse {

	private BigDecimal transaction_charge;
	private String tc_trans_id;
	
	public BigDecimal getTransaction_charge() {
		return transaction_charge;
	}
	public void setTransaction_charge(BigDecimal transaction_charge) {
		this.transaction_charge = transaction_charge;
	}
	public String getTc_trans_id() {
		return tc_trans_id;
	}
	public void setTc_trans_id(String tc_trans_id) {
		this.tc_trans_id = tc_trans_id;
	}
	
	
}
