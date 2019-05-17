package com.tcg.mis.model.vo;

public class ThirdPartyAccountParameter {

	private ThirdPartyAccount tpAcct;
	private ThirdPartyVendorParameter tpParam;
	private Object val;

	public ThirdPartyAccount getTpAcct() {
		return tpAcct;
	}

	public void setTpAcct(ThirdPartyAccount tpAcct) {
		this.tpAcct = tpAcct;
	}

	public ThirdPartyVendorParameter getTpParam() {
		return tpParam;
	}

	public void setTpParam(ThirdPartyVendorParameter tpParam) {
		this.tpParam = tpParam;
	}

	public Object getVal() {
		return val;
	}

	public void setVal(Object val) {
		this.val = val;
	}

}
