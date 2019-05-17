package com.tcg.mis.to.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentSystemResponse {

	private int status;
	@JsonProperty("error_msg")
	private String errorMsg;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public boolean isSuccess(){
		return status == 1;
	}
	
}
