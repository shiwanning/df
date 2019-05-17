package com.tcg.mis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * MIS_ACCOUNT_OFFERED_BANKS
 * @author 
 */
public class MisAccountOfferedBanks implements Serializable {
    private Long rid;

    private Long bankAcctRid;

    private String bankType;

    private BigDecimal merchantRate;

    private BigDecimal merchantMinAmount;

    private BigDecimal merchantMaxAmount;

    private BigDecimal bankFeeRate;

    private BigDecimal bankMinFee;

    private BigDecimal bankMaxFee;

    private Integer status;

    private String createOperatorName;

    private String updateOperatorName;

    private Date createTime;

    private Date updateTime;

    private BigDecimal txMaxAmount;

    private BigDecimal txMinAmount;

    public BigDecimal getTxMinAmount() {
        return txMinAmount;
    }

    public void setTxMinAmount(BigDecimal txMinAmount) {
        this.txMinAmount = txMinAmount;
    }

    public BigDecimal getTxMaxAmount() {
        return txMaxAmount;
    }

    public void setTxMaxAmount(BigDecimal txMaxAmount) {
        this.txMaxAmount = txMaxAmount;
    }

    private static final long serialVersionUID = 1L;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getBankAcctRid() {
        return bankAcctRid;
    }

    public void setBankAcctRid(Long bankAcctRid) {
        this.bankAcctRid = bankAcctRid;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public BigDecimal getMerchantRate() {
		return merchantRate;
	}

	public void setMerchantRate(BigDecimal merchantRate) {
		this.merchantRate = merchantRate;
	}

	public BigDecimal getMerchantMinAmount() {
		return merchantMinAmount;
	}

	public void setMerchantMinAmount(BigDecimal merchantMinAmount) {
		this.merchantMinAmount = merchantMinAmount;
	}

	public BigDecimal getMerchantMaxAmount() {
		return merchantMaxAmount;
	}

	public void setMerchantMaxAmount(BigDecimal merchantMaxAmount) {
		this.merchantMaxAmount = merchantMaxAmount;
	}

	public BigDecimal getBankFeeRate() {
		return bankFeeRate;
	}

	public void setBankFeeRate(BigDecimal bankFeeRate) {
		this.bankFeeRate = bankFeeRate;
	}

	public BigDecimal getBankMinFee() {
		return bankMinFee;
	}

	public void setBankMinFee(BigDecimal bankMinFee) {
		this.bankMinFee = bankMinFee;
	}

	public BigDecimal getBankMaxFee() {
		return bankMaxFee;
	}

	public void setBankMaxFee(BigDecimal bankMaxFee) {
		this.bankMaxFee = bankMaxFee;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateOperatorName() {
        return createOperatorName;
    }

    public void setCreateOperatorName(String createOperatorName) {
        this.createOperatorName = createOperatorName;
    }

    public String getUpdateOperatorName() {
        return updateOperatorName;
    }

    public void setUpdateOperatorName(String updateOperatorName) {
        this.updateOperatorName = updateOperatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}