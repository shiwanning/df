package com.tcg.mis.common.constant;

public enum TpBankType {
    PG("PG"),
    MT("MT"),
    ;

    private final String type;

    TpBankType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
