package com.tcg.mis.model;

import java.math.BigDecimal;

public class TcgTransactionSummary {
    private BigDecimal txAmountTotal;
    private BigDecimal creditAmountTotal;
    private BigDecimal debitAmountTotal;

    public BigDecimal getTxAmountTotal() {
        return txAmountTotal;
    }

    public void setTxAmountTotal(BigDecimal txAmountTotal) {
        this.txAmountTotal = txAmountTotal;
    }

    public BigDecimal getCreditAmountTotal() {
        return creditAmountTotal;
    }

    public void setCreditAmountTotal(BigDecimal creditAmountTotal) {
        this.creditAmountTotal = creditAmountTotal;
    }

    public BigDecimal getDebitAmountTotal() {
        return debitAmountTotal;
    }

    public void setDebitAmountTotal(BigDecimal debitAmountTotal) {
        this.debitAmountTotal = debitAmountTotal;
    }
}
