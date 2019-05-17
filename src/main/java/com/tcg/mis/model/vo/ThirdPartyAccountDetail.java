package com.tcg.mis.model.vo;

import java.util.List;
import java.util.Map;

public class ThirdPartyAccountDetail {


	private ThirdPartyAccount account;
	private List<ThirdPartyAccountParameter> params;
	private Map<String, String> staticParams;

	public ThirdPartyAccount getAccount() {
		return account;
	}

	public void setAccount(ThirdPartyAccount account) {
		this.account = account;
	}

	public List<ThirdPartyAccountParameter> getParams() {
		return params;
	}

	public void setParams(List<ThirdPartyAccountParameter> params) {
		this.params = params;
	}

	public Map<String, String> getStaticParams() {
		return staticParams;
	}

	public void setStaticParams(Map<String, String> staticParams) {
		this.staticParams = staticParams;
	}
}
