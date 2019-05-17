package com.tcg.mis.mapper;

import com.tcg.mis.model.MerchantBillDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MerchantBillDetailMapper {

    List<MerchantBillDetail> getList(@Param("billMasterId")String billMasterId);

    int insert(MerchantBillDetail merchantBillDetail);

    int update(List<MerchantBillDetail> details);

}