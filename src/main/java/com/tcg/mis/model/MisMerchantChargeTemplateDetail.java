package com.tcg.mis.model;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;

public class MisMerchantChargeTemplateDetail extends BaseAuditEntity {
	
	private Long rid;
    private int detailType;
    private String code;
    private String product;
    private String subProduct;
    private Integer rangeRateFlag;
    private Integer winLossCalculateFlag;
    private Integer accumulateNegative;
    private Integer rateType;
    private BigDecimal rate;
    private BigDecimal charge;
    private String remark;
    private Long templateRid;
    private Long detailRid;
    
    // for service use
    private List<MisMerchantChargeTemplateDetailIntervalrate> intervalrateList = Lists.newLinkedList();
    
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public int getDetailType() {
		return detailType;
	}
	public void setDetailType(int detailType) {
		this.detailType = detailType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getSubProduct() {
		return subProduct;
	}
	public void setSubProduct(String subProduct) {
		this.subProduct = subProduct;
	}
	public Integer getRangeRateFlag() {
		return rangeRateFlag;
	}
	public void setRangeRateFlag(Integer rangeRateFlag) {
		this.rangeRateFlag = rangeRateFlag;
	}
	public Integer getWinLossCalculateFlag() {
		return winLossCalculateFlag;
	}
	public void setWinLossCalculateFlag(Integer winLossCalculateFlag) {
		this.winLossCalculateFlag = winLossCalculateFlag;
	}
	public Integer getAccumulateNegative() {
		return accumulateNegative;
	}
	public void setAccumulateNegative(Integer accumulateNegative) {
		this.accumulateNegative = accumulateNegative;
	}
	public Integer getRateType() {
		return rateType;
	}
	public void setRateType(Integer rateType) {
		this.rateType = rateType;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getCharge() {
		return charge;
	}
	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getTemplateRid() {
		return templateRid;
	}
	public void setTemplateRid(Long templateRid) {
		this.templateRid = templateRid;
	}
	public Long getDetailRid() {
		return detailRid;
	}
	public void setDetailRid(Long detailRid) {
		this.detailRid = detailRid;
	}
	public List<MisMerchantChargeTemplateDetailIntervalrate> getIntervalrateList() {
		return intervalrateList;
	}
	public void setIntervalrateList(List<MisMerchantChargeTemplateDetailIntervalrate> intervalrateList) {
		this.intervalrateList = intervalrateList;
	}

}
