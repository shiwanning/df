package com.tcg.mis.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * MIS_PRODUCT_TRANSACTION
 * @author 
 */
public class MisProductTransaction {
    private Long rid;

    private String orderNo;

    private String merchantCode;

    private String product;

    private String subProduct;

    private Long productAccountId;

    private BigDecimal productBalance;

    private BigDecimal procuctAmount;

    private Long mainAccountId;

    private BigDecimal mainBalance;

    private BigDecimal mainAmount;

    private BigDecimal rate;

    private Integer txType;

    private Integer status;

    private String remark;

    private String createOperatorName;

    private String updateOperatorName;

    private Date createTime;

    private Date updateTime;

    private Integer taskId;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSubProduct() {
        return subProduct;
    }

    public void setSubProduct(String subProduct) {
        this.subProduct = subProduct;
    }

    public Long getProductAccountId() {
        return productAccountId;
    }

    public void setProductAccountId(Long productAccountId) {
        this.productAccountId = productAccountId;
    }

    public BigDecimal getProductBalance() {
        return productBalance;
    }

    public void setProductBalance(BigDecimal productBalance) {
        this.productBalance = productBalance;
    }

    public BigDecimal getProcuctAmount() {
        return procuctAmount;
    }

    public void setProcuctAmount(BigDecimal procuctAmount) {
        this.procuctAmount = procuctAmount;
    }

    public Long getMainAccountId() {
        return mainAccountId;
    }

    public void setMainAccountId(Long mainAccountId) {
        this.mainAccountId = mainAccountId;
    }

    public BigDecimal getMainBalance() {
        return mainBalance;
    }

    public void setMainBalance(BigDecimal mainBalance) {
        this.mainBalance = mainBalance;
    }

    public BigDecimal getMainAmount() {
        return mainAmount;
    }

    public void setMainAmount(BigDecimal mainAmount) {
        this.mainAmount = mainAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getTxType() {
        return txType;
    }

    public void setTxType(Integer txType) {
        this.txType = txType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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