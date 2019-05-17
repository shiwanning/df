package com.tcg.mis.service.merchant.bill;

import com.tcg.mis.common.constant.BillStatus;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.util.OperationUserUtil;
import com.tcg.mis.model.MerchantBill;
import com.tcg.mis.model.MerchantBillDiscountLog;
import com.tcg.mis.service.merchant.BillStatusHandler;
import com.tcg.mis.to.request.BillTo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * com.tcg.mis.service.merchant.bill
 *
 * @author lyndon.j
 * @version 1.0
 * @date 2019/5/16 17:38
 */
@Component
public class BillBadDebtHandler extends AbstractBillHandler implements BillStatusHandler {


    @Override
    public void handler() {

        MerchantBill merchantBill = super.getMerchantBill(true);

        if (BillStatus.BAD_DEBT.getCode().equals(merchantBill.getStatus())) {
            throw new MisBaseException(ErrorCode.SYS_ERR,"can not set bill to bad debt because the status is wrong.");
        }

        merchantBill.setStatus(com.tcg.mis.common.constant.BillStatus.BAD_DEBT.getCode());
        BigDecimal badDebtAmount = merchantBill.getUnpaidAmount();

        merchantBill.setPaidAmount(merchantBill.getBillAmount());
        merchantBill.setUnpaidAmount(BigDecimal.ZERO);
        merchantBill.setUpdateOperatorName(OperationUserUtil.getOperatorName());
        merchantBill.setUpdateTime(new Date());
        merchantBill.setRemark(billTo.getRemark());
        merchantBillService.update(merchantBill);

        merchantBillDiscountLogService.insert(buildLog(merchantBill));
    }

    private MerchantBillDiscountLog buildLog(MerchantBill merchantBill) {
        MerchantBillDiscountLog log = new MerchantBillDiscountLog();
        log.setBillMasterId(merchantBill.getBillMasterId());
        log.setCreateDate(new Date());
        log.setDiscountAmount(merchantBill.getUnpaidAmount());
        log.setOperator(merchantBill.getUpdateOperatorName());
        log.setDiscountType(1);
        return log;
    }



}
