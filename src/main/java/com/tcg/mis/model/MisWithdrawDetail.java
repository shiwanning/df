package com.tcg.mis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * MIS_WITHDRAW_DETAIL
 * @author 
 */
public class MisWithdrawDetail implements Serializable {
    private BigDecimal rid;

    private String orderNo;

    private BigDecimal bankAcctRid;

    private BigDecimal txType;

    private BigDecimal txAmount;

    private BigDecimal withdrawCharge;

    private String remark;

    private String createOperatorName;

    private String updateOperatorName;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

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

    public BigDecimal getBankAcctRid() {
        return bankAcctRid;
    }

    public void setBankAcctRid(BigDecimal bankAcctRid) {
        this.bankAcctRid = bankAcctRid;
    }

    public BigDecimal getTxType() {
        return txType;
    }

    public void setTxType(BigDecimal txType) {
        this.txType = txType;
    }

    public BigDecimal getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(BigDecimal txAmount) {
        this.txAmount = txAmount;
    }

    public BigDecimal getWithdrawCharge() {
        return withdrawCharge;
    }

    public void setWithdrawCharge(BigDecimal withdrawCharge) {
        this.withdrawCharge = withdrawCharge;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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