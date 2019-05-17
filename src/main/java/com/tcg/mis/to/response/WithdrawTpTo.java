package com.tcg.mis.to.response;


import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class WithdrawTpTo {

    private Long bankAccountId;
    @Min(1) @Max(50000)
    private BigDecimal amount;
    private String depositAccountName;
    private String depositAccount;
    private Long depositBankId;
    private String pssPgCode;
    private String depositBankName;
    private String depositBankCity;
    private String depositStat;
    private String depositBranch;
    private String remark;

    public String getPssPgCode() {
        return pssPgCode;
    }

    public void setPssPgCode(String pssPgCode) {
        this.pssPgCode = pssPgCode;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDepositAccountName() {
        return depositAccountName;
    }

    public void setDepositAccountName(String depositAccountName) {
        this.depositAccountName = depositAccountName;
    }

    public String getDepositAccount() {
        return depositAccount;
    }

    public void setDepositAccount(String depositAccount) {
        this.depositAccount = depositAccount;
    }

    public Long getDepositBankId() {
        return depositBankId;
    }

    public void setDepositBankId(Long depositBankId) {
        this.depositBankId = depositBankId;
    }

    public String getDepositBankName() {
        return depositBankName;
    }

    public void setDepositBankName(String depositBankName) {
        this.depositBankName = depositBankName;
    }

    public String getDepositStat() {
        return depositStat;
    }

    public void setDepositStat(String depositStat) {
        this.depositStat = depositStat;
    }

    public String getDepositBranch() {
        return depositBranch;
    }

    public void setDepositBranch(String depositBranch) {
        this.depositBranch = depositBranch;
    }

    public String getDepositBankCity() {
        return depositBankCity;
    }

    public void setDepositBankCity(String depositBankCity) {
        this.depositBankCity = depositBankCity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
