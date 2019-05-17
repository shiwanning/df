package com.tcg.mis.model.vo;

public class ThirdPartyAccount {

	private Long id;
	private ThirdPartyVendor vendor;
	private String remarks;
	private Long systemId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ThirdPartyVendor getVendor() {
		return vendor;
	}

	public void setVendor(ThirdPartyVendor vendor) {
		this.vendor = vendor;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

}
