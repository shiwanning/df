package com.tcg.mis.to.response;

import java.math.BigDecimal;

public class BanksTo {
    private Long bankAcctRid;
    private BigDecimal txMaxAmount;
    private BigDecimal txMinAmount;

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

    public Long getBankAcctRid() {
        return bankAcctRid;
    }

    public void setBankAcctRid(Long bankAcctRid) {
        this.bankAcctRid = bankAcctRid;
    }
}
