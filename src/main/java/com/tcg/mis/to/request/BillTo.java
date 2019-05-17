package com.tcg.mis.to.request;

import java.math.BigDecimal;

/**
 * com.tcg.mis.to.request
 *
 * @author lyndon.j
 * @version 1.0
 * @date 2019/5/17 14:04
 */
public class BillTo {
    private String merchantCode;
    private String billNo;
    private BigDecimal discountAmount;
    private String remark;
    private BigDecimal paidAmount;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }
}
