package com.tcg.mis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * MIS_WITHDRAW_TRANSACTION
 * @author 
 */
public class MisWithdrawTransaction implements Serializable {
    private BigDecimal rid;

    private String orderNo;

    private BigDecimal withdrawBankId;

    private String withdrawBankName;

    private String withdrawBankAccount;

    private BigDecimal withdrawAccountAmount;

    private BigDecimal depositBankId;

    private String depositBankName;

    private String depositBankCity;

    private String depositBankStat;

    private String depositBankBranch;

    private String depositBankAccount;

    private String depositAccountName;

    private BigDecimal status;

    private BigDecimal txAmount;

    private BigDecimal actualAmount;

    private String remark;

    private String createOperatorName;

    private String updateOperatorName;

    private Date createTime;

    private Date updateTime;

    private Date getTime;

    private Long bookTxId;

    private static final long serialVersionUID = 1L;

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

    public BigDecimal getWithdrawBankId() {
        return withdrawBankId;
    }

    public void setWithdrawBankId(BigDecimal withdrawBankId) {
        this.withdrawBankId = withdrawBankId;
    }

    public String getWithdrawBankName() {
        return withdrawBankName;
    }

    public void setWithdrawBankName(String withdrawBankName) {
        this.withdrawBankName = withdrawBankName;
    }

    public String getWithdrawBankAccount() {
        return withdrawBankAccount;
    }

    public void setWithdrawBankAccount(String withdrawBankAccount) {
        this.withdrawBankAccount = withdrawBankAccount;
    }

    public BigDecimal getWithdrawAccountAmount() {
        return withdrawAccountAmount;
    }

    public void setWithdrawAccountAmount(BigDecimal withdrawAccountAmount) {
        this.withdrawAccountAmount = withdrawAccountAmount;
    }

    public BigDecimal getDepositBankId() {
        return depositBankId;
    }

    public void setDepositBankId(BigDecimal depositBankId) {
        this.depositBankId = depositBankId;
    }

    public String getDepositBankName() {
        return depositBankName;
    }

    public void setDepositBankName(String depositBankName) {
        this.depositBankName = depositBankName;
    }

    public String getDepositBankCity() {
        return depositBankCity;
    }

    public void setDepositBankCity(String depositBankCity) {
        this.depositBankCity = depositBankCity;
    }

    public String getDepositBankStat() {
        return depositBankStat;
    }

    public void setDepositBankStat(String depositBankStat) {
        this.depositBankStat = depositBankStat;
    }

    public String getDepositBankBranch() {
        return depositBankBranch;
    }

    public void setDepositBankBranch(String depositBankBranch) {
        this.depositBankBranch = depositBankBranch;
    }

    public String getDepositBankAccount() {
        return depositBankAccount;
    }

    public void setDepositBankAccount(String depositBankAccount) {
        this.depositBankAccount = depositBankAccount;
    }

    public String getDepositAccountName() {
        return depositAccountName;
    }

    public void setDepositAccountName(String depositAccountName) {
        this.depositAccountName = depositAccountName;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public BigDecimal getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(BigDecimal txAmount) {
        this.txAmount = txAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
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

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public Long getBookTxId() {
        return bookTxId;
    }

    public void setBookTxId(Long bookTxId) {
        this.bookTxId = bookTxId;
    }
}