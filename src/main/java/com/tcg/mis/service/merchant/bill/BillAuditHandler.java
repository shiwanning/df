package com.tcg.mis.service.merchant.bill;

import com.tcg.mis.common.constant.BillStatus;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.util.OperationUserUtil;
import com.tcg.mis.model.MerchantBill;
import com.tcg.mis.service.merchant.BillStatusHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * com.tcg.mis.service.merchant.bill
 *
 * @author lyndon.j
 * @version 1.0
 * @date 2019/5/16 18:02
 */
@Component
public class BillAuditHandler extends AbstractBillHandler implements BillStatusHandler {
    @Override
    public void handler() {
        MerchantBill bill = super.getMerchantBill(true);
        if (!bill.getStatus().equals(BillStatus.WAITE_AUDIT)) {
            throw new MisBaseException(ErrorCode.STATUS_ERR, "bill status is not wait audit.");
        }
        bill.setStatus(BillStatus.WAITE_SEND.getCode());
        bill.setUpdateOperatorName(OperationUserUtil.getOperatorName());
        bill.setUpdateTime(new Date());

        super.merchantBillService.update(bill);
    }
}
