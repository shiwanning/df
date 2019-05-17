package com.tcg.mis.to.response;

import com.tcg.mis.model.vo.ThirdPartyVendorParameter;

import java.util.List;

public class ThirdPartyRequiredParameter extends PaymentSystemResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2263963986364344449L;

	private List<ThirdPartyVendorParameter> data;

	public List<ThirdPartyVendorParameter> getData() {
		return data;
	}

	public void setData(List<ThirdPartyVendorParameter> data) {
		this.data = data;
	}

}
