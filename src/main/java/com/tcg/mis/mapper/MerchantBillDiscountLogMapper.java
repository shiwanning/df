package com.tcg.mis.mapper;

import com.tcg.mis.model.MerchantBillDiscountLog;
import java.util.List;


 public interface MerchantBillDiscountLogMapper
        {

 List<MerchantBillDiscountLog> getList(MerchantBillDiscountLog merchantBillDiscountLog);


 int insert(MerchantBillDiscountLog merchantBillDiscountLog);

 int update(MerchantBillDiscountLog merchantBillDiscountLog);


 int delete(String[] ids);

        }