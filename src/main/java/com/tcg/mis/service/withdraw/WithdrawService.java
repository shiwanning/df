package com.tcg.mis.service.withdraw;

import com.tcg.mis.common.response.BaseResponseT;
import com.tcg.mis.model.vo.BankAccountInfo;
import com.tcg.mis.to.request.TPSWithdrawResult;
import com.tcg.mis.to.request.TransactionApproveTo;
import com.tcg.mis.to.response.WithdrawTpTo;
import com.yx.commons.constant.AcsDebitTypeConstant;

import java.math.BigDecimal;
import java.util.List;

public interface WithdrawService {

    BaseResponseT<List<BankAccountInfo>> getWithdrawAccounts();

    Boolean doTpWithdraw(WithdrawTpTo to, String operator);

    void doTpWithdrawCallback(TPSWithdrawResult result);

    void doTpWithdrawApprove(TransactionApproveTo transactionApproveTo, String operator);

    void confirmBooked(Long transactionId,Integer txTypeId, AcsDebitTypeConstant debitType);
}
