package com.tcg.mis.common.constant;

public final class TransactionStatus {
	
    public static final Integer REQUEST = 1;
    public static final Integer CALLBACK_FAIL = 2;
    public static final Integer CALLBACK_SUCCESS = 3;
    public static final Integer MANUAL_REJECT = 4;
    public static final Integer MANUAL_SUCCESS = 5;
    public static final Integer REQUEST_FAIL = 6;
    
    public static boolean canManualStatus(Integer status) {
    	return status == REQUEST;
    }
    
    public static boolean isSuccessStatus(Integer status) {
    	return status == CALLBACK_SUCCESS || status == MANUAL_SUCCESS;
    }
    
    private TransactionStatus() {
        throw new IllegalStateException("Constant class");
    }
    
}
