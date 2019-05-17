package com.tcg.mis.to.response;


import com.tcg.mis.model.vo.ThirdPartyAccountDetail;

public class ThirdPartyAccountResponse extends PSSBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1434345697266210642L;
	private ThirdPartyAccountDetail data;

	public ThirdPartyAccountDetail getData() {
		return data;
	}

	public void setData(ThirdPartyAccountDetail data) {
		this.data = data;
	}

}
