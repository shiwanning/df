package com.tcg.mis.service.merchant;

import com.tcg.mis.model.MerchantBillDiscountLog;

import java.util.List;

/**
 * @author
 */
public interface MerchantBillDiscountLogService {

    List<MerchantBillDiscountLog> getList(MerchantBillDiscountLog merchantBillDiscountLog);

    List<MerchantBillDiscountLog> getListByBillId(String billId);

    int insert(MerchantBillDiscountLog merchantBillDiscountLog);
}
