package com.tcg.mis.common.response;


import com.tcg.mis.common.constant.ErrorCode;

import java.util.Collection;

public class BaseCollectionResponseT<T> extends BaseResponseT<Collection<T>> {

    public BaseCollectionResponseT(boolean success) {
        super(success);
    }

    public BaseCollectionResponseT(ErrorCode errorCode) {
        super(errorCode);
    }
    
    public BaseCollectionResponseT(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
    
    public BaseCollectionResponseT(Collection<T> object) {
        super(object);
    }
    
}
