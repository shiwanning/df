package com.tcg.mis.model.vo;

import java.math.BigDecimal;

public class BankAccountInfo {

    private String rid;
    private String acctNumber;
    private String bankName;
    private BigDecimal txMaxAmount;
    private BigDecimal txMinAmount;
    private BigDecimal balance;

    public BigDecimal getTxMinAmount() {
        return txMinAmount;
    }

    public void setTxMinAmount(BigDecimal txMinAmount) {
        this.txMinAmount = txMinAmount;
    }

    public BigDecimal getTxMaxAmount() {
        return txMaxAmount;
    }

    public void setTxMaxAmount(BigDecimal txMaxAmount) {
        this.txMaxAmount = txMaxAmount;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getAcctNumber() {
        return acctNumber;
    }

    public void setAcctNumber(String acctNumber) {
        this.acctNumber = acctNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
