package com.tcg.mis.common.exception;


import com.tcg.mis.common.constant.ErrorCode;

/**
 * Description: http client timeout <br/>
 *
 * @author Eddie
 */
public class TimeoutException extends MisBaseException {
    public TimeoutException(String description, Throwable throwable) {
        super(ErrorCode.NETWORK_TIMEOUT, description, throwable);
    }
}
