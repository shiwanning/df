package com.tcg.mis.model.vo;

import java.math.BigDecimal;
import java.util.Date;

public class AcsAccount {
    private Long accountId;
    private BigDecimal balance;
    private Integer accountTypeId;
    private BigDecimal availBalance;
    private BigDecimal creditLimit;
    private String version;
    private Date createTime;
    private Date updateTime;
    private Integer activeFlag;
    private Long customerId;
    private String typeConstant;
    private String accountTypeName;

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public Integer getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getTypeConstant() {
        return typeConstant;
    }

    public void setTypeConstant(String typeConstant) {
        this.typeConstant = typeConstant;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Integer accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public BigDecimal getAvailBalance() {
        return availBalance;
    }

    public void setAvailBalance(BigDecimal availBalance) {
        this.availBalance = availBalance;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }
}
