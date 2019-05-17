package com.tcg.mis.to.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class TPSRawBank implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7856140451621653611L;

    private String bankCode;
    private String bankEngName;
    private String bankChName;
    private BigDecimal min;
    private BigDecimal max;
    private int category;
    private Map<String, TPSMTBankSubtype> subTypes;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankEngName() {
        return bankEngName;
    }

    public void setBankEngName(String bankEngName) {
        this.bankEngName = bankEngName;
    }

    public String getBankChName() {
        return bankChName;
    }

    public void setBankChName(String bankChName) {
        this.bankChName = bankChName;
    }

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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Map<String, TPSMTBankSubtype> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(Map<String, TPSMTBankSubtype> subTypes) {
        this.subTypes = subTypes;
    }

    public boolean hasSubType(String subType) {
        if (this.subTypes == null) {
            return false;
        }
        if (this.subTypes.containsKey(subType)) {
            return true;
        }
        return false;
    }

    public BigDecimal getSubtypeMin(String subType) {
        if (this.subTypes == null) {
            return BigDecimal.ZERO;
        }
        if (this.subTypes.containsKey(subType)) {
            return this.subTypes.get(subType).getMin();
        }

        return BigDecimal.ZERO;
    }

    public BigDecimal getSubtypeMax(String subType) {
        if (this.subTypes == null) {
            return BigDecimal.ZERO;
        }
        if (this.subTypes.containsKey(subType)) {
            return this.subTypes.get(subType).getMax();
        }

        return BigDecimal.ZERO;
    }
}
