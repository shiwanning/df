package com.tcg.mis.common.response;


import com.tcg.mis.common.constant.ErrorCode;

public class BaseResponseT<T> extends BaseResponse {

    private T value;

    public BaseResponseT(boolean success) {
        super(success);
    }

    public BaseResponseT(ErrorCode errorCode) {
        super(errorCode);
    }
    
    public BaseResponseT(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
    
    public BaseResponseT(T object) {
        super(true);
        this.value = object;
    }

    public static <T> BaseResponseT<T> emptyData() {
        return new BaseResponseT<>(true);
    }

    public T getValue() {
        return value;
    }

}
