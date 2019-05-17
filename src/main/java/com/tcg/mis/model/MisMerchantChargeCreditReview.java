package com.tcg.mis.model;

import java.math.BigDecimal;

public class MisMerchantChargeCreditReview extends BaseAuditEntity {

	private Long rid;
    private String merchantCode;
    private BigDecimal leverMultiplier;
    private BigDecimal modifyLeverMultiplier;
    private Integer status;
    private String remark;
    
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
	public BigDecimal getLeverMultiplier() {
		return leverMultiplier;
	}
	public void setLeverMultiplier(BigDecimal leverMultiplier) {
		this.leverMultiplier = leverMultiplier;
	}
	public BigDecimal getModifyLeverMultiplier() {
		return modifyLeverMultiplier;
	}
	public void setModifyLeverMultiplier(BigDecimal modifyLeverMultiplier) {
		this.modifyLeverMultiplier = modifyLeverMultiplier;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
