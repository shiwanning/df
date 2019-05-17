package com.tcg.mis.service.merchant.bill;

import com.tcg.mis.model.MerchantBill;
import com.tcg.mis.service.merchant.MerchantBillDiscountLogService;
import com.tcg.mis.service.merchant.MerchantBillService;
import com.tcg.mis.to.request.BillTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * com.tcg.mis.service.merchant.bill
 *
 * @author lyndon.j
 * @version 1.0
 * @date 2019/5/17 16:48
 */
public class AbstractBillHandler {

    @Autowired
    protected MerchantBillService merchantBillService;
    @Autowired
    protected MerchantBillDiscountLogService merchantBillDiscountLogService;

    protected BillTo billTo;

    protected MerchantBill getMerchantBill(boolean isFinance) {
       return merchantBillService.getBill(billTo.getMerchantCode(), billTo.getBillNo(), isFinance);
    }

    protected boolean isAllPaid(MerchantBill merchantBill) {

        if (merchantBill.getBillAmount().compareTo(merchantBill.getDiscountAmount().add(merchantBill.getPaidAmount())) == 0) {
            return true;
        }
        return  false;
    }


    public void setBillTo(BillTo billTo) {
        this.billTo = billTo;
    }
}
