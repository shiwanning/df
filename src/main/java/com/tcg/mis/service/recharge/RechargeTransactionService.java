package com.tcg.mis.service.recharge;

import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.model.MisRechargeTransaction;
import com.tcg.mis.model.vo.LobbyAccountType;
import com.tcg.mis.model.vo.Merchant;
import com.tcg.mis.to.condition.RechargeTransactionCondition;
import com.tcg.mis.to.request.TransactionApproveTo;
import com.tcg.mis.to.response.MisRechargeTo;

import java.math.BigDecimal;
import java.util.List;

public interface RechargeTransactionService {
	PageResponse<MisRechargeTo, MisRechargeTo> getMisRechargeTransactionList(RechargeTransactionCondition condition);

	MisRechargeTransaction getTransaction(String orderNo);

	void manual(TransactionApproveTo transactionApproveTo, String updateOperatorName);

	void rechargeSuccessProcess(String orderNo, BigDecimal actualAmount, String remark, String updateOperatorName,
			Boolean isManual);

    Merchant getMerchant(String merchantCode);

    List<LobbyAccountType> getWalletList();
}
