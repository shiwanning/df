package com.tcg.mis.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * MIS_RECHARGE_TRANSACTION
 * @author 
 */
public class MisRechargeTransaction implements Serializable {
    private BigDecimal rid;

    private String orderNo;

    private BigDecimal rechargeBankId;

    private String rechargeBankName;

    private String rechargeBankAccount;

    private BigDecimal rechargeAccountAmount;

    private String tpOrderNo;

    private Integer status;

    private BigDecimal txAmount;

    private BigDecimal actualAmount;

    private String remark;

    private String createOperatorName;

    private String updateOperatorName;

    private Date createTime;

    private Date updateTime;

    private Date getTime;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public BigDecimal getRechargeBankId() {
        return rechargeBankId;
    }

    public void setRechargeBankId(BigDecimal rechargeBankId) {
        this.rechargeBankId = rechargeBankId;
    }

    public String getRechargeBankName() {
        return rechargeBankName;
    }

    public void setRechargeBankName(String rechargeBankName) {
        this.rechargeBankName = rechargeBankName;
    }

    public String getRechargeBankAccount() {
        return rechargeBankAccount;
    }

    public void setRechargeBankAccount(String rechargeBankAccount) {
        this.rechargeBankAccount = rechargeBankAccount;
    }

    public BigDecimal getRechargeAccountAmount() {
        return rechargeAccountAmount;
    }

    public void setRechargeAccountAmount(BigDecimal rechargeAccountAmount) {
        this.rechargeAccountAmount = rechargeAccountAmount;
    }

    public String getTpOrderNo() {
        return tpOrderNo;
    }

    public void setTpOrderNo(String tpOrderNo) {
        this.tpOrderNo = tpOrderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(BigDecimal txAmount) {
        this.txAmount = txAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
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

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }
}