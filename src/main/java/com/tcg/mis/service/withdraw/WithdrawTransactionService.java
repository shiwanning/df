package com.tcg.mis.service.withdraw;

import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.to.condition.WithdrawTransactionCondition;
import com.tcg.mis.to.response.MisWithdrawTo;

public interface WithdrawTransactionService {

	PageResponse<MisWithdrawTo, MisWithdrawTo> getMisWithdrawTransactionList(WithdrawTransactionCondition condition);

}
