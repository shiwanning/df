package com.tcg.mis.service.merchant.bill;

import com.tcg.mis.common.constant.BillStatus;
import com.tcg.mis.common.util.OperationUserUtil;
import com.tcg.mis.model.MerchantBill;
import com.tcg.mis.service.merchant.BillStatusHandler;

import java.math.BigDecimal;
import java.util.Date;

/**
 * com.tcg.mis.service.merchant.bill
 *
 * @author lyndon.j
 * @version 1.0
 * @date 2019/5/17 17:41
 */
public class BillPaidHandler extends AbstractBillHandler implements BillStatusHandler {
    @Override
    public void handler() {
        MerchantBill bill = super.getMerchantBill(true);
        bill.setPaidAmount(bill.getPaidAmount().add(billTo.getPaidAmount()));
        bill.setUnpaidAmount(bill.getUnpaidAmount().subtract(bill.getPaidAmount()));

        if (bill.getUnpaidAmount().compareTo(BigDecimal.ZERO) < 0) {
            bill.setUnpaidAmount(BigDecimal.ZERO);
            //TODO 调用ACS 给 商户主钱包充值金额，金额数目为超出账单金额部分。

        }

        bill.setUpdateOperatorName(OperationUserUtil.getOperatorName());
        bill.setUpdateTime(new Date());
        bill.setStatus(BillStatus.PARTIAL_PAID.getCode());

        if (isAllPaid(bill)) {
            bill.setStatus(BillStatus.ALL_PAID.getCode());
        }

        merchantBillService.update(bill);
    }

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(0);
        BigDecimal b = new BigDecimal(-1);
        BigDecimal c = new BigDecimal(1);
        System.out.println(a.compareTo(BigDecimal.ZERO));
        System.out.println(b.compareTo(BigDecimal.ZERO));
        System.out.println(c.compareTo(BigDecimal.ZERO));
    }
}
