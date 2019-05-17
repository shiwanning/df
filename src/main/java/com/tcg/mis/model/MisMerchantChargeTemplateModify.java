package com.tcg.mis.model;

public class MisMerchantChargeTemplateModify extends BaseAuditEntity {
	
	private Long rid;
    private String name;
    private int templateType;
    private int status;
    private Long templateRid;
    
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTemplateType() {
		return templateType;
	}
	public void setTemplateType(int templateType) {
		this.templateType = templateType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getTemplateRid() {
		return templateRid;
	}
	public void setTemplateRid(Long templateRid) {
		this.templateRid = templateRid;
	}
    
}
