package com.tcg.mis.to.condition;

import java.math.BigDecimal;
import java.util.Date;

public class WithdrawTransactionCondition extends PageCondition {

    private Long rid;
	private String orderNo;
	private Integer status;
	private Integer depositBankId;
	private String depositBankAccount;
	private Integer withdrawBankId;
	private String withdrawBankName;
	private String withdrawBankAccount;
	private BigDecimal minTxAmount;
	private BigDecimal maxTxAmount;
	private Date requestStartDate;
	private Date requestEndDate;
	private Date payStartDate;
    private Date payEndDate;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getWithdrawBankName() {
		return withdrawBankName;
	}
	public void setWithdrawBankName(String withdrawBankName) {
		this.withdrawBankName = withdrawBankName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getDepositBankId() {
		return depositBankId;
	}
	public void setDepositBankId(Integer depositBankId) {
		this.depositBankId = depositBankId;
	}
	public String getDepositBankAccount() {
		return depositBankAccount;
	}
	public void setDepositBankAccount(String depositBankAccount) {
		this.depositBankAccount = depositBankAccount;
	}
	public Integer getWithdrawBankId() {
		return withdrawBankId;
	}
	public void setWithdrawBankId(Integer withdrawBankId) {
		this.withdrawBankId = withdrawBankId;
	}
	public String getWithdrawBankAccount() {
		return withdrawBankAccount;
	}
	public void setWithdrawBankAccount(String withdrawBankAccount) {
		this.withdrawBankAccount = withdrawBankAccount;
	}
	public BigDecimal getMinTxAmount() {
		return minTxAmount;
	}
	public void setMinTxAmount(BigDecimal minTxAmount) {
		this.minTxAmount = minTxAmount;
	}
	public BigDecimal getMaxTxAmount() {
		return maxTxAmount;
	}
	public void setMaxTxAmount(BigDecimal maxTxAmount) {
		this.maxTxAmount = maxTxAmount;
	}
	public Date getRequestStartDate() {
		return requestStartDate;
	}
	public void setRequestStartDate(Date requestStartDate) {
		this.requestStartDate = requestStartDate;
	}
	public Date getRequestEndDate() {
		return requestEndDate;
	}
	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = requestEndDate;
	}
	public Date getPayStartDate() {
		return payStartDate;
	}
	public void setPayStartDate(Date payStartDate) {
		this.payStartDate = payStartDate;
	}
	public Date getPayEndDate() {
		return payEndDate;
	}
	public void setPayEndDate(Date payEndDate) {
		this.payEndDate = payEndDate;
	}
	
}
