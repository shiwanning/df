package com.tcg.mis.common.constant;

public enum Currency {
    CNY("CNY");

    private final String currency;

    Currency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return this.currency;
    }
}
