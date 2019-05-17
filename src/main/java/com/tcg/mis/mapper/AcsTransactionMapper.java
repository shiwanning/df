package com.tcg.mis.mapper;

import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.model.TcgTransaction;
import com.tcg.mis.model.TcgTransactionSummary;
import com.tcg.mis.to.condition.AcsTransactionCondition;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AcsTransactionMapper {

    List<TcgTransaction> findTcgTransaction(@Param("condition") AcsTransactionCondition condition, @Param("page") PaginationAndOrderVO page);

    TcgTransactionSummary sumByTcgTransaction(@Param("condition") AcsTransactionCondition condition);

    List<TcgTransaction> findMerchantTransaction(@Param("condition") AcsTransactionCondition condition, @Param("page") PaginationAndOrderVO page);

    TcgTransactionSummary sumByMerchantTransaction(@Param("condition") AcsTransactionCondition condition);
}