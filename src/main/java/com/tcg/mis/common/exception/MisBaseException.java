package com.tcg.mis.common.exception;

import com.tcg.mis.common.constant.ErrorCode;

import org.apache.commons.lang3.StringUtils;

/**
 * Description: ODS <br/>
 *
 * @author Eddie
 */
public class MisBaseException extends RuntimeException {
    private ErrorCode errorCode;

    public MisBaseException(ErrorCode errorCode, String description, Throwable throwable) {
        super(StringUtils.isBlank(description) ? errorCode.toString() :
                      errorCode == null ? description : "[" + errorCode + "] " + description, throwable);
        this.errorCode = errorCode;
    }

    public MisBaseException(ErrorCode errorCode, String description) {
        super(StringUtils.isBlank(description) ? errorCode.toString() :
                      errorCode == null ? description : "[" + errorCode + "] " + description);
        this.errorCode = errorCode;
    }
    
    public MisBaseException(ErrorCode errorCode, Throwable throwable) {
        this(errorCode, null, throwable);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
