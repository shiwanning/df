package com.tcg.mis.service.merchant.bill;

import com.tcg.mis.service.merchant.BillStatusHandler;

/**
 * com.tcg.mis.service.merchant.bill
 *
 * @author lyndon.j
 * @version 1.0
 * @date 2019/5/16 13:59
 */
public class BillStatusHandlerContext implements BillStatusHandler {
    private BillStatusHandler billStatusHandler;

    @Override
    public void handler() {
        billStatusHandler.handler();
    }

    public BillStatusHandler getBillStatusHandler() {
        return billStatusHandler;
    }

    public void setBillStatusHandler(BillStatusHandler billStatusHandler) {
        this.billStatusHandler = billStatusHandler;
    }
}
