package com.tcg.mis.mapper;

import com.tcg.mis.model.MerchantBill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface MerchantBillMapper {

    List<MerchantBill> getList(@Param("merchantCode") String  merchantCode, @Param("billDate")Date billDate, @Param("status")List<Integer> status,@Param("billNo")String billNo);

    int insert(MerchantBill merchantBill);

    int update(MerchantBill merchantBill);

}