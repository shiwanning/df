package com.tcg.mis.service.merchant;

import com.tcg.mis.model.MerchantBillDetail;

import java.util.List;

/**
 * @author
 */
public interface MerchantBillDetailService {


    List<MerchantBillDetail> getList(String billMasterId);

    int insert(MerchantBillDetail merchantBillDetail);

    int update(MerchantBillDetail merchantBillDetail);

    int updateDetail(MerchantBillDetail merchantBillDetail);

    int updateDetails(List<MerchantBillDetail> details);
}
