package com.tcg.mis.to.response;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class BankAccountTo {

	private Long rid;

    private Integer bankId;

    private String bankName;

    private String acctNumber;

    private Integer status;

    private Integer tpAcctId;
    
    private Integer vendorId;

    private String mtDisplayAccount;

    private BigDecimal acsCustId;

    private BigDecimal acsAcctId;
    
    @ApiModelProperty(required = true, value = "可用余额")
    private BigDecimal balance;

    private Integer withdrawAcctStatus;

    private BigDecimal withdrawTxMaxAmount;

    private BigDecimal withdrawTxMinAmount;

    public BigDecimal getWithdrawTxMinAmount() {
        return withdrawTxMinAmount;
    }

    public void setWithdrawTxMinAmount(BigDecimal withdrawTxMinAmount) {
        this.withdrawTxMinAmount = withdrawTxMinAmount;
    }

    public BigDecimal getWithdrawTxMaxAmount() {
        return withdrawTxMaxAmount;
    }

    public void setWithdrawTxMaxAmount(BigDecimal withdrawTxMaxAmount) {
        this.withdrawTxMaxAmount = withdrawTxMaxAmount;
    }

    public Integer getWithdrawAcctStatus() {
        return withdrawAcctStatus;
    }

    public void setWithdrawAcctStatus(Integer withdrawAcctStatus) {
        this.withdrawAcctStatus = withdrawAcctStatus;
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

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAcctNumber() {
		return acctNumber;
	}

	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
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

	public String getMtDisplayAccount() {
		return mtDisplayAccount;
	}

	public void setMtDisplayAccount(String mtDisplayAccount) {
		this.mtDisplayAccount = mtDisplayAccount;
	}

	public BigDecimal getAcsCustId() {
		return acsCustId;
	}

	public void setAcsCustId(BigDecimal acsCustId) {
		this.acsCustId = acsCustId;
	}

	public BigDecimal getAcsAcctId() {
		return acsAcctId;
	}

	public void setAcsAcctId(BigDecimal acsAcctId) {
		this.acsAcctId = acsAcctId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	
}
