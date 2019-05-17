package com.tcg.mis.model;

public class MisMerchantChargeConfig extends BaseAuditEntity {
	
	private Long rid;
    private String merchantCode;
    private Long templateRid;
    
	public Long getRid() {
		return rid;
	}
	
	public void setRid(Long rid) {
		this.rid = rid;
	}
	
	public String getMerchantCode() {
		return merchantCode;
	}
	
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	public Long getTemplateRid() {
		return templateRid;
	}
	
	public void setTemplateRid(Long templateRid) {
		this.templateRid = templateRid;
	}

}
