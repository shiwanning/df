package com.tcg.mis.to.response;

import java.math.BigDecimal;
import java.util.Date;

import com.tcg.mis.common.constant.TransactionStatus;

public class MisWithdrawTo {

	private BigDecimal rid;

    private String orderNo;

    private BigDecimal withdrawBankId;

    private String withdrawBankName;

    private String withdrawBankAccount;

    private BigDecimal withdrawAccountAmount;

    private BigDecimal depositBankId;

    private String depositBankName;

    private String depositBankStat;

    private String depositBankBranch;

    private String depositBankAccount;

    private String depositAccountName;

    private BigDecimal status;
    
    private BigDecimal withdrawCharge;

    private BigDecimal txAmount;

    private String remark;

    private String updateOperatorName;

    private Date updateTime;
    
    private Date createTime;

    private String approveRemark;

    private BigDecimal actualAmount;

    public Boolean getCanManual() {
    	return this.status != null && TransactionStatus.canManualStatus(this.status.intValue());
    }
    
    public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getApproveRemark() {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark;
    }

    public BigDecimal getWithdrawCharge() {
		return withdrawCharge;
	}

	public void setWithdrawCharge(BigDecimal withdrawCharge) {
		this.withdrawCharge = withdrawCharge;
	}

	public BigDecimal getRid() {
		return rid;
	}

	public void setRid(BigDecimal rid) {
		this.rid = rid;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getWithdrawBankId() {
		return withdrawBankId;
	}

	public void setWithdrawBankId(BigDecimal withdrawBankId) {
		this.withdrawBankId = withdrawBankId;
	}

	public String getWithdrawBankName() {
		return withdrawBankName;
	}

	public void setWithdrawBankName(String withdrawBankName) {
		this.withdrawBankName = withdrawBankName;
	}

	public String getWithdrawBankAccount() {
		return withdrawBankAccount;
	}

	public void setWithdrawBankAccount(String withdrawBankAccount) {
		this.withdrawBankAccount = withdrawBankAccount;
	}

	public BigDecimal getWithdrawAccountAmount() {
		return withdrawAccountAmount;
	}

	public void setWithdrawAccountAmount(BigDecimal withdrawAccountAmount) {
		this.withdrawAccountAmount = withdrawAccountAmount;
	}

	public BigDecimal getDepositBankId() {
		return depositBankId;
	}

	public void setDepositBankId(BigDecimal depositBankId) {
		this.depositBankId = depositBankId;
	}

	public String getDepositBankName() {
		return depositBankName;
	}

	public void setDepositBankName(String depositBankName) {
		this.depositBankName = depositBankName;
	}

	public String getDepositBankStat() {
		return depositBankStat;
	}

	public void setDepositBankStat(String depositBankStat) {
		this.depositBankStat = depositBankStat;
	}

	public String getDepositBankBranch() {
		return depositBankBranch;
	}

	public void setDepositBankBranch(String depositBankBranch) {
		this.depositBankBranch = depositBankBranch;
	}

	public String getDepositBankAccount() {
		String result = depositBankAccount;
		
		if(result == null || result.length() <= 8) {
			return result;
		}
		
		return result.substring(0, 4) + "****" + result.substring(result.length() - 4, result.length());
	}

	public void setDepositBankAccount(String depositBankAccount) {
		this.depositBankAccount = depositBankAccount;
	}

	public String getDepositAccountName() {
		return depositAccountName;
	}

	public void setDepositAccountName(String depositAccountName) {
		this.depositAccountName = depositAccountName;
	}

	public BigDecimal getStatus() {
		return status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public BigDecimal getTxAmount() {
		return txAmount;
	}

	public void setTxAmount(BigDecimal txAmount) {
		this.txAmount = txAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateOperatorName() {
		return updateOperatorName;
	}

	public void setUpdateOperatorName(String updateOperatorName) {
		this.updateOperatorName = updateOperatorName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
}
