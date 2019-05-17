package com.tcg.mis.service.product;

import com.tcg.mis.common.response.BaseResponseT;
import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.model.MisProductTransaction;
import com.tcg.mis.model.vo.MerchantProductTo;
import com.tcg.mis.to.condition.ProductTransactionCondition;
import com.tcg.mis.to.request.ProductRechargeTo;
import com.tcg.mis.to.request.ProductTransferTo;

public interface ProductWalletService {

    BaseResponseT<MerchantProductTo> getMerchantProductWallet(String merchantCode);

    void doProductRecharge(ProductRechargeTo to, String operator, String token);

    void doProductTransfer(ProductTransferTo to, String operator, String token);

    PageResponse<MisProductTransaction,MisProductTransaction> getTransactionDetails(ProductTransactionCondition condition);

    BaseResponseT<MerchantProductTo> getMerchantWallet(String merchantCode);

    Boolean doProductWalletManualReject(String orderNo,String remark, String operator, String token);

    Boolean doProductWalletManualApprove(String orderNo,String remark, String operator, String token);
}
