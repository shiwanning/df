package com.tcg.mis.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tcg.mis.common.constant.ErrorCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class DisburseResponse extends BaseResponse{

    private List<DisburseResult> errorList = new ArrayList<>();

    public DisburseResponse(Boolean success){
        super(success);
    }

    public List<DisburseResult> getErrorList() {
        return errorList;
    }

   public void addToErrorList(Long id, ErrorCode errorCode, String message){
        this.errorList.add(new DisburseResult(id, errorCode, message));
   }

    public void addToErrorList(Long id,Date balanceDate,ErrorCode errorCode,String message){
        this.errorList.add(new DisburseResult(id,balanceDate, errorCode, message));
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public class DisburseResult{
        @ApiModelProperty(example = "1254")
        private Long id;
        @ApiModelProperty(example = "不存在的分红ID")
        private String message;
        @ApiModelProperty(example = "request.param.err")
        private ErrorCode errorCode;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date balanceDate;

        public Date getBalanceDate() {
            return balanceDate;
        }

        public void setBalanceDate(Date balanceDate) {
            this.balanceDate = balanceDate;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(ErrorCode errorCode) {
            this.errorCode = errorCode;
        }

        DisburseResult(Long id, ErrorCode errorCode, String message){
            this.id = id;
            this.message = message;
            this.errorCode = errorCode;
        }

        DisburseResult(Long id, Date balanceDate, ErrorCode errorCode, String message){
            this.id = id;
            this.balanceDate = balanceDate;
            this.message = message;
            this.errorCode = errorCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
