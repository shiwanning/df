package com.tcg.mis.model.vo;

public class ThirdPartyVendor {

	private Long id;
	private String vendorCode;
	private String vendorName;
	private VendorStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public VendorStatus getStatus() {
		return status;
	}

	public void setStatus(VendorStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ThirdPartyVendor [id=" + id + ", vendorCode=" + vendorCode
				+ ", vendorName=" + vendorName + ", status=" + status + "]";
	}
	
	public enum VendorStatus {
		A, I;
	}

}
