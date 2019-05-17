package com.tcg.mis.model.vo;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

public class MerchantProductWalletTo {

    private Integer accountTypeId;
    private String displayName;
    private String accountTypeName;
    private BigDecimal balance;
    private List<PrepayTemplateDetailTO> details = Lists.newLinkedList();

    public List<PrepayTemplateDetailTO> getDetails() {
        return details;
    }

    public void setDetails(List<PrepayTemplateDetailTO> details) {
        this.details = details;
    }

    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Integer accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
