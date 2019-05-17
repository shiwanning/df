package com.tcg.mis.to;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 */
public class OperatorInfo extends TaskBaseResponse<OperatorInfo> {

    private Long operatorId;

    private String operatorName;

    private String nickName;

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getNickName() {
        return nickName;
    }
    @JsonProperty("operatorNickname")
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private List<String> merchants;

    public List<String> getMerchants() {
        return merchants;
    }
    @JsonProperty("merchant")
    public void setMerchants(List<String> merchants) {
        this.merchants = merchants;
    }
}
