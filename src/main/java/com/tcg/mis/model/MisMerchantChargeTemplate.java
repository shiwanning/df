package com.tcg.mis.model;

import java.util.List;

public class MisMerchantChargeTemplate extends BaseAuditEntity {
	
	private Long rid;
    private String name;
    private int templateType;
    
    // for service use
    private List<MisMerchantChargeTemplateDetail> detailList;
    
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
	public List<MisMerchantChargeTemplateDetail> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<MisMerchantChargeTemplateDetail> detailList) {
		this.detailList = detailList;
	}
	
}
