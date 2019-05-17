package com.tcg.mis.to.request;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class PssMtDepositTo extends PssDepositBaseTo{
    private String bankRefNo;
    private String groupId = "-1";
    private Map<String,String> extraParams =  ImmutableMap.of("mt_type", "acct_no");

    public String getBankRefNo() {
        return bankRefNo;
    }

    public void setBankRefNo(String bankRefNo) {
        this.bankRefNo = bankRefNo;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Map<String, String> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, String> extraParams) {
        this.extraParams = extraParams;
    }
}
