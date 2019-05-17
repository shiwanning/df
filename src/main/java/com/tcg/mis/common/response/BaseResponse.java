package com.tcg.mis.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcg.mis.common.constant.ErrorCode;

import io.swagger.annotations.ApiModelProperty;

public class BaseResponse {
    private boolean success;

    public static final BaseResponse SUCCESS_BASE_RESPONSE = new BaseResponse(true);
    public static final BaseResponse DATA_NOT_FOUND_BASE_RESPONSE = new BaseResponse(ErrorCode.DATA_NOT_FOUND);

    @ApiModelProperty(example = "success")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;

    @ApiModelProperty(example = "request.param.err")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String errorCode;

    public BaseResponse() {
        this.success = false;
    }

    public BaseResponse(boolean success, ErrorCode errorCode, String message) {
        this.success = success;
        this.errorCode = errorCode.getCode();
        this.message = message;
    }
    
    public BaseResponse(ErrorCode errorCode, String message) {
        this(false, errorCode, message);
    }
    
    public BaseResponse(ErrorCode errorCode) {
        this.success = false;
        this.errorCode = errorCode.getCode();
        this.message = errorCode == null ? null : errorCode.getCode();
    }

    public BaseResponse(Boolean success) {
        this.success = success;
    }
    
    public BaseResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setModule(String module) {
        this.errorCode = module + this.errorCode;
    }
}
