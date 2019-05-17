package com.tcg.mis.to.request;

import java.math.BigDecimal;

/**
 * Created by ivan.julius on 2/20/2017.
 */
public class TPSWithdrawRequest {

	private String acctId;
	private String withdrawId;
	private String bankCode;
	private String customerId;
	private BigDecimal amount;
	private String ipAddress;
	private String customerAcctNum;
	private String customerAcctName;
	private String bankProvince;
	private String bankCity;
	private String bankBranch;

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getWithdrawId() {
		return withdrawId;
	}

	public void setWithdrawId(String withdrawId) {
		this.withdrawId = withdrawId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getCustomerAcctNum() {
		return customerAcctNum;
	}

	public void setCustomerAcctNum(String customerAcctNum) {
		this.customerAcctNum = customerAcctNum;
	}

	public String getCustomerAcctName() {
		return customerAcctName;
	}

	public void setCustomerAcctName(String customerAcctName) {
		this.customerAcctName = customerAcctName;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
}
