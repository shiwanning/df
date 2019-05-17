package com.tcg.mis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * MIS_RECHARGE_DETAIL
 * @author 
 */
public class MisRechargeDetail implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private BigDecimal rid;

    private String orderNo;

    private String merchantCode;

    private String bankType;

    private BigDecimal bankAcctRid;

    private BigDecimal txType;

    private BigDecimal rechargeType;

    private BigDecimal accountId;

    private BigDecimal billingId;

    private BigDecimal originalAmount;

    private BigDecimal txAmount;

    private BigDecimal rechargeCharge;
    
    private String fileUrl;
    
    private String remark;

    private String createOperatorName;

    private String updateOperatorName;

    private Date createTime;

    private Date updateTime;

    private BigDecimal rechargeFee;

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public BigDecimal getRid() {
        return rid;
    }

    public void setRid(BigDecimal rid) {
        this.rid = rid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public BigDecimal getBankAcctRid() {
        return bankAcctRid;
    }

    public void setBankAcctRid(BigDecimal bankAcctRid) {
        this.bankAcctRid = bankAcctRid;
    }

    public BigDecimal getTxType() {
        return txType;
    }

    public void setTxType(BigDecimal txType) {
        this.txType = txType;
    }

    public BigDecimal getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(BigDecimal rechargeType) {
        this.rechargeType = rechargeType;
    }

    public BigDecimal getAccountId() {
        return accountId;
    }

    public void setAccountId(BigDecimal accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBillingId() {
        return billingId;
    }

    public void setBillingId(BigDecimal billingId) {
        this.billingId = billingId;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(BigDecimal txAmount) {
        this.txAmount = txAmount;
    }

    public BigDecimal getRechargeCharge() {
        return rechargeCharge;
    }

    public void setRechargeCharge(BigDecimal rechargeCharge) {
        this.rechargeCharge = rechargeCharge;
    }

    public String getCreateOperatorName() {
        return createOperatorName;
    }

    public void setCreateOperatorName(String createOperatorName) {
        this.createOperatorName = createOperatorName;
    }

    public String getUpdateOperatorName() {
        return updateOperatorName;
    }

    public void setUpdateOperatorName(String updateOperatorName) {
        this.updateOperatorName = updateOperatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getRechargeFee() {
        return rechargeFee;
    }

    public void setRechargeFee(BigDecimal rechargeFee) {
        this.rechargeFee = rechargeFee;
    }
}