package com.tcg.mis.to;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerOnlineStatus implements Serializable {
    private String action;
    private Map<Integer, Boolean> content;
    private Integer errorCodeId;
    private String errorDesc;
    private String requestParam;

    public PlayerOnlineStatus() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<Integer, Boolean> getContent() {
        return content;
    }

    public void setContent(Map<Integer, Boolean> content) {
        this.content = content;
    }

    public Integer getErrorCodeId() {
        return errorCodeId;
    }

    public void setErrorCodeId(Integer errorCodeId) {
        this.errorCodeId = errorCodeId;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }
}
