package com.tcg.mis.mapper;

import com.tcg.mis.model.MisWithdrawTransactionRequest;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface MisWithdrawTransactionRequestMapper {
    int deleteByPrimaryKey(BigDecimal rid);

    int insert(MisWithdrawTransactionRequest record);

    int insertSelective(MisWithdrawTransactionRequest record);

    MisWithdrawTransactionRequest selectByPrimaryKey(BigDecimal rid);

    int updateByPrimaryKeySelective(MisWithdrawTransactionRequest record);

    int updateByPrimaryKey(MisWithdrawTransactionRequest record);

    MisWithdrawTransactionRequest findByOrderNo(@Param("orderNo")String orderNo);
}