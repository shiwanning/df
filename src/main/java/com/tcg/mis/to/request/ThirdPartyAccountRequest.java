package com.tcg.mis.to.request;

import java.io.Serializable;
import java.util.Map;

public class ThirdPartyAccountRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 953277090296437908L;

	private Long accountId;
	private Long vendorId;
	private String systemId = "5";
	private String remarks;
	private Map<Long, String> stringTypeParams;
	private Map<Long, FileResource> fileTypeParams;

	public String getSystemId() {
		return systemId;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Map<Long, String> getStringTypeParams() {
		return stringTypeParams;
	}

	public void setStringTypeParams(Map<Long, String> stringTypeParams) {
		this.stringTypeParams = stringTypeParams;
	}

	public Map<Long, FileResource> getFileTypeParams() {
		return fileTypeParams;
	}

	public void setFileTypeParams(Map<Long, FileResource> fileTypeParams) {
		this.fileTypeParams = fileTypeParams;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

}
