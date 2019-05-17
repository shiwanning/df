package com.tcg.mis.to.condition;

import java.util.Date;
import java.util.List;

public class AcsTransactionCondition extends PageCondition {
	
	private List<Integer> txTypeIds;
	private Date startDate;
	private Date endDate;
	private Integer type;
	private Long bankAcctRid;
	private Integer accountTypeId;
	private String merchantCode;
    private Integer debitType;

    public Integer getDebitType() {
        return debitType;
    }

    public void setDebitType(Integer debitType) {
        this.debitType = debitType;
    }

    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Integer accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public List<Integer> getTxTypeIds() {
        return txTypeIds;
    }

    public void setTxTypeIds(List<Integer> txTypeIds) {
        this.txTypeIds = txTypeIds;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getBankAcctRid() {
        return bankAcctRid;
    }

    public void setBankAcctRid(Long bankAcctRid) {
        this.bankAcctRid = bankAcctRid;
    }
}
