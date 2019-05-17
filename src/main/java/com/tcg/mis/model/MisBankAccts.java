package com.tcg.mis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * MIS_BANK_ACCTS
 * @author 
 */
public class MisBankAccts implements Serializable {
	
    private Long rid;

    private Integer vendorId;
    
    private Integer bankId;

    private String bankName;

    private Integer status;

    private Integer tpAcctId;
    
    private Integer acsCustId;

    private Integer acsAcctId;
    
    private String acctNumber;

    private String createOperatorName;

    private String updateOperatorName;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
    
    public String getAcctNumber() {
		return acctNumber;
	}

	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}

	public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Integer getBankId() {
        return bankId;
    }

    public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTpAcctId() {
        return tpAcctId;
    }

    public void setTpAcctId(Integer tpAcctId) {
        this.tpAcctId = tpAcctId;
    }

    public Integer getAcsCustId() {
        return acsCustId;
    }

    public void setAcsCustId(Integer acsCustId) {
        this.acsCustId = acsCustId;
    }

    public Integer getAcsAcctId() {
        return acsAcctId;
    }

    public void setAcsAcctId(Integer acsAcctId) {
        this.acsAcctId = acsAcctId;
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
}