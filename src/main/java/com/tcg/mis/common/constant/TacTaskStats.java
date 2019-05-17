package com.tcg.mis.common.constant;

public enum TacTaskStats {
    PRODUCT_DEPOSIT_REQUEST(400),
    PRODUCT_DEPOSIT_APPROVE(401),
    PRODUCT_DEPOSIT_REJECT(402),
    ;

    private final Integer id;

    TacTaskStats(Integer id) {
        this.id = id;
    }

    public Integer getId(){
        return id;
    }
}
