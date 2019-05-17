package com.tcg.mis.common.constant;

public final class RechareType {

    public static final Integer TX_MERCHANT_RECHARGE = 0;
    public static final Integer TX_WALLET_SETTING = 1;
    public static final Integer WALLET_MAIN = 0;
    public static final Integer WALLET_CASH_PLEDGE = 1;
    public static final Integer BILLING = 2;
    

    private RechareType() {
        throw new IllegalStateException("Constant class");
    }
    
}
