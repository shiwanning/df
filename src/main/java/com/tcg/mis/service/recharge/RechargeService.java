package com.tcg.mis.service.recharge;

import com.tcg.mis.common.response.BaseResponseT;
import com.tcg.mis.to.request.TPSRechargeResult;
import com.tcg.mis.to.response.BanksTo;
import com.tcg.mis.to.response.PgBanksTo;
import com.tcg.mis.to.response.TpMtTo;
import com.tcg.mis.to.response.TpPgTo;

import java.math.BigDecimal;
import java.util.List;

public interface RechargeService {

    BaseResponseT<List<String>> getPaymentTypes();

    BaseResponseT<PgBanksTo> getPgBanks();

    BaseResponseT<BanksTo> getMtBanks();

    BaseResponseT<TpPgTo> doPgRecharge(String merchantCode, Integer bankAcctId, String bankId, Integer rechargeType, Long billingId, BigDecimal amount, String operator);

    BaseResponseT<TpMtTo> doMtRecharge(String merchantCode, Integer bankAcctId, Integer rechargeType, BigDecimal amount, String operator);

    void doPgRechargeCallback(TPSRechargeResult result);

    BigDecimal getCharge(BigDecimal txAmount, BigDecimal chargeRate, BigDecimal minAmount, BigDecimal maxAmount);

    void updateRechargeDetailByAdmin(String orderNo, String merchantCode, Integer accountType, BigDecimal amount, String remark, String fileUrl,String operator);
}
