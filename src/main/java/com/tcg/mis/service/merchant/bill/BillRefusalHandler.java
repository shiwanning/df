package com.tcg.mis.service.merchant.bill;

import com.tcg.mis.common.constant.BillStatus;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.model.MerchantBill;
import com.tcg.mis.service.merchant.BillStatusHandler;

/**
 * com.tcg.mis.service.merchant.bill
 *
 * @author lyndon.j
 * @version 1.0
 * @date 2019/5/17 17:39
 */
public class BillRefusalHandler extends AbstractBillHandler implements BillStatusHandler {

    @Override
    public void handler() {
        MerchantBill bill = super.getMerchantBill(false);
        if (!bill.getStatus().equals(BillStatus.UNPAID.getCode()) && !bill.getStatus().equals(BillStatus.PARTIAL_PAID.getCode())) {
            throw new MisBaseException(ErrorCode.STATUS_ERR, "you bill can not refusal.");
        }

        bill.setStatus(BillStatus.WAITE_AUDIT.getCode());
        merchantBillService.update(bill);
    }
}
