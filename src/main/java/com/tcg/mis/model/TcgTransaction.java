package com.tcg.mis.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description: acs tx <br/>
 *
 * @author Eddie
 */
public class TcgTransaction {
    private Long txId;
    private Integer txTypeId;
    private Integer debit;
    private Date txTime;
    private BigDecimal amount;
    private BigDecimal currentBalance;
    private BigDecimal currentAvailBalance;
    private String remark;
    private String orderNo;
    private String merchantCode;
    private Integer rechargeTxType;
    private String transRemark;
    private String accountTypeName;
    private String displayName;
    private Integer accountTypeId;

    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Integer accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTransRemark() {
        return transRemark;
    }

    public void setTransRemark(String transRemark) {
        this.transRemark = transRemark;
    }

    public Long getTxId() {
        return txId;
    }

    public void setTxId(Long txId) {
        this.txId = txId;
    }

    public Integer getTxTypeId() {
        return txTypeId;
    }

    public void setTxTypeId(Integer txTypeId) {
        this.txTypeId = txTypeId;
    }

    public Integer getDebit() {
        return debit;
    }

    public void setDebit(Integer debit) {
        this.debit = debit;
    }

    public Date getTxTime() {
        return txTime;
    }

    public void setTxTime(Date txTime) {
        this.txTime = txTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getCurrentAvailBalance() {
        return currentAvailBalance;
    }

    public void setCurrentAvailBalance(BigDecimal currentAvailBalance) {
        this.currentAvailBalance = currentAvailBalance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getRechargeTxType() {
        return rechargeTxType;
    }

    public void setRechargeTxType(Integer rechargeTxType) {
        this.rechargeTxType = rechargeTxType;
    }
}
