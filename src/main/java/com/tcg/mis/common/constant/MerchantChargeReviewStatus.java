package com.tcg.mis.common.constant;

public enum MerchantChargeReviewStatus {
    WAITING(0),
    APPROVE(1),
    REJECT(2);

    private int value;


    MerchantChargeReviewStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
