package com.tcg.mis.service.merchant;

import com.tcg.mis.model.MerchantBill;

import java.util.List;

/**
 * @author
 */
public interface MerchantBillService {

    List<MerchantBill> getList(MerchantBill merchantBill);

    MerchantBill getBillDetailByBillNo(String merchantCode, String billNo,boolean isFinance);

    List<MerchantBill> merchantGetBillList(String merchantCode,Integer year,Integer month,Integer status);

    List<MerchantBill> financeGetBillList(String merchantCode,Integer year,Integer month,Integer status);

    MerchantBill getBill(String merchantCode, String billNo, boolean isFinance);

    void discount(String billNo,Double amount);

    void badDebt(String billNo);

    int insert(MerchantBill merchantBill);

    int update(MerchantBill merchantBill);

}
