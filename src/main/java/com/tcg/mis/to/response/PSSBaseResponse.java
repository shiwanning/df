package com.tcg.mis.to.response;

import java.io.Serializable;

public class PSSBaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean status;
	private String errorDesc;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

}
