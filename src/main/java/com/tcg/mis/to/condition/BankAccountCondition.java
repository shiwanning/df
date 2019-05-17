package com.tcg.mis.to.condition;

public class BankAccountCondition extends PageCondition {
	
	private Integer bankId;
	private Integer rid;
	private Integer status;
	private String channelName;
	private String activePayType;
	private String bankType;

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public Integer getBankId() {
		return bankId;
	}
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getActivePayType() {
		return activePayType;
	}
	public void setActivePayType(String activePayType) {
		this.activePayType = activePayType;
	}

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }
}
