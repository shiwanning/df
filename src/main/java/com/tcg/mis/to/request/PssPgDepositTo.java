package com.tcg.mis.to.request;

public class PssPgDepositTo extends PssDepositBaseTo{
    private String bankCode;
    private String ipAddress;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
