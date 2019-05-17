package com.tcg.mis.service.merchant.bill;

import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.model.MerchantBill;
import com.tcg.mis.model.MerchantBillDiscountLog;
import com.tcg.mis.service.merchant.BillStatusHandler;
import com.tcg.mis.to.request.BillTo;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * com.tcg.mis.service.merchant.bill
 *
 * @author lyndon.j
 * @version 1.0
 * @date 2019/5/16 18:01
 */
@Component
public class BillDiscountHandler extends AbstractBillHandler implements BillStatusHandler {

    @Override
    public void handler() {

        MerchantBill merchantBill = super.getMerchantBill(true);

        merchantBill.setDiscountAmount(merchantBill.getDiscountAmount().add(billTo.getDiscountAmount()));

        merchantBill.setRemark(billTo.getRemark());

        isOutOfBillAmount(merchantBill);

        if (isAllPaid(merchantBill)) {
            merchantBill.setStatus(com.tcg.mis.common.constant.BillStatus.ALL_PAID.getCode());
        }

        merchantBillService.update(merchantBill);

        merchantBillDiscountLogService.insert(buildLog(merchantBill, billTo));
    }

    private void isOutOfBillAmount(MerchantBill merchantBill) {
        if (merchantBill.getBillAmount().compareTo(merchantBill.getDiscountAmount().add(merchantBill.getPaidAmount()))<0) {
            throw new MisBaseException(ErrorCode.SYS_ERR, "can not to discount amount out of bill amount");
        }
    }

    private MerchantBillDiscountLog buildLog(MerchantBill merchantBill,BillTo billTo) {
        MerchantBillDiscountLog log = new MerchantBillDiscountLog();
        log.setBillMasterId(merchantBill.getBillMasterId());
        log.setCreateDate(new Date());
        log.setDiscountAmount(billTo.getDiscountAmount());
        log.setOperator(merchantBill.getUpdateOperatorName());
        log.setDiscountType(2);
        return log;
    }




}
