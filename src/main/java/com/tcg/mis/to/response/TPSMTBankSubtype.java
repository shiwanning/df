package com.tcg.mis.to.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class TPSMTBankSubtype implements Serializable {

    private BigDecimal min;
    private BigDecimal max;

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }
}
