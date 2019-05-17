package com.tcg.mis.model;

public class MisMerchantChargeTemplateReview extends BaseAuditEntity {

	private Long rid;
    private int status;
    private String remark;
    private String reviewComment;
    private Long modifyRid;
    
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReviewComment() {
		return reviewComment;
	}
	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}
	public Long getModifyRid() {
		return modifyRid;
	}
	public void setModifyRid(Long modifyRid) {
		this.modifyRid = modifyRid;
	}
	
}
