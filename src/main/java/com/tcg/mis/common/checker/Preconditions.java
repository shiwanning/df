package com.tcg.mis.common.checker;


import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;

import org.apache.commons.lang.ArrayUtils;

public class Preconditions {

    public static void checkParameter(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new MisBaseException(ErrorCode.REQ_ERR, String.valueOf(errorMessage));
        }
    }

    public static void checkNotNull(Object reference, Object errorMessage) {
        if (reference == null) {
            throw new MisBaseException(ErrorCode.REQ_ERR, String.valueOf(errorMessage));
        }
    }
    
    public static void checkContains(Object reference, Object errorMessage, Object... container) {
        if (!ArrayUtils.contains(container, reference)) {
            throw new MisBaseException(ErrorCode.REQ_ERR, String.valueOf(errorMessage));
        }
    }
}
