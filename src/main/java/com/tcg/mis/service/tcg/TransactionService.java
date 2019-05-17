package com.tcg.mis.service.tcg;

import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.model.TcgTransaction;
import com.tcg.mis.model.TcgTransactionSummary;
import com.tcg.mis.to.condition.AcsTransactionCondition;

public interface TransactionService {

    PageResponse<TcgTransaction, TcgTransactionSummary> getTcgTransactionList(AcsTransactionCondition condition);

    PageResponse<TcgTransaction, TcgTransactionSummary> getMerchantTransactionList(AcsTransactionCondition condition);

}
