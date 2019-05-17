package com.tcg.mis.model;

import java.util.Date;

public class BillLog extends BaseAuditEntity{

	private static final long serialVersionUID = 1L;
	
	private java.math.BigDecimal id;
	
	private java.math.BigDecimal billMasterId;
	
	private java.math.BigDecimal billDetailId;
	
	private Boolean detailModify;
	
	private String updateContent;
	
	private String updateOperatorName;
	
	private Date updateTime;

	public void setId(java.math.BigDecimal value) {
		this.id = value;
	}
	
	public java.math.BigDecimal getId() {
		return this.id;
	}
	
	public void setBillMasterId(java.math.BigDecimal value) {
		this.billMasterId = value;
	}
	
	public java.math.BigDecimal getBillMasterId() {
		return this.billMasterId;
	}
	
	public void setBillDetailId(java.math.BigDecimal value) {
		this.billDetailId = value;
	}
	
	public java.math.BigDecimal getBillDetailId() {
		return this.billDetailId;
	}
	
	public void setDetailModify(Boolean value) {
		this.detailModify = value;
	}
	
	public Boolean getDetailModify() {
		return this.detailModify;
	}
	
	public void setUpdateContent(String value) {
		this.updateContent = value;
	}
	
	public String getUpdateContent() {
		return this.updateContent;
	}
	
	public void setUpdateOperatorName(String value) {
		this.updateOperatorName = value;
	}
	
	public String getUpdateOperatorName() {
		return this.updateOperatorName;
	}
	
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	
}
