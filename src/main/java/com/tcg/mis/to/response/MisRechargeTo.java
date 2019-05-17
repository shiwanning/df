package com.tcg.mis.to.response;

import com.tcg.mis.common.constant.TransactionStatus;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class MisRechargeTo {

	private String merchantCode;
	
	private Long rid;

    private String orderNo;

    private Integer txType;
    
    private Integer rechargeType;

    private Integer rechargeBankId;

    private String rechargeBankName;

    private String rechargeBankAccount;
    
    private BigDecimal rechargeCharge;
    
    private BigDecimal rechargeFee;

    private BigDecimal rechargeAccountAmount;

    private Integer status;

    private BigDecimal txAmount;

    private String remark;
    
    private String detailRemark;
    
    private String fileUrl;
    
    private String bankType;

    private String createOperatorName;

    private String updateOperatorName;

    private Date createTime;

    private Date updateTime;
    
    private BigDecimal actualAmount;
    private Date getTime;
    private String message;
    
    public String getDetailRemark() {
		return detailRemark;
	}

	public void setDetailRemark(String detailRemark) {
		this.detailRemark = detailRemark;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @ApiModelProperty(required = true, value = "是否可手工操作")
    public Boolean getCanManual() {
    	return TransactionStatus.canManualStatus(status);
    }
    
    public BigDecimal getRechargeFee() {
		return rechargeFee;
	}

	public void setRechargeFee(BigDecimal rechargeFee) {
		this.rechargeFee = rechargeFee;
	}

	public BigDecimal getRechargeCharge() {
		return rechargeCharge;
	}

	public void setRechargeCharge(BigDecimal rechargeCharge) {
		this.rechargeCharge = rechargeCharge;
	}

	public Integer getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(Integer rechargeType) {
		this.rechargeType = rechargeType;
	}
	
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getTxType() {
		return txType;
	}

	public void setTxType(Integer txType) {
		this.txType = txType;
	}

	public Integer getRechargeBankId() {
		return rechargeBankId;
	}

	public void setRechargeBankId(Integer rechargeBankId) {
		this.rechargeBankId = rechargeBankId;
	}

	public String getRechargeBankName() {
		return rechargeBankName;
	}

	public void setRechargeBankName(String rechargeBankName) {
		this.rechargeBankName = rechargeBankName;
	}

	public String getRechargeBankAccount() {
		String result = rechargeBankAccount;
		
		if(result == null || result.length() <= 8) {
			return result;
		}
		
		return result.substring(0, 4) + "****" + result.substring(result.length() - 4, result.length());
	}

	public void setRechargeBankAccount(String rechargeBankAccount) {
		this.rechargeBankAccount = rechargeBankAccount;
	}

	public BigDecimal getRechargeAccountAmount() {
		return rechargeAccountAmount;
	}

	public void setRechargeAccountAmount(BigDecimal rechargeAccountAmount) {
		this.rechargeAccountAmount = rechargeAccountAmount;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getTxAmount() {
		return txAmount;
	}

	public void setTxAmount(BigDecimal txAmount) {
		this.txAmount = txAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Date getGetTime() {
		return getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}
	
}
