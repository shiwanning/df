package com.tcg.mis.mapper;

import com.tcg.mis.model.MisRechargeTransactionRequest;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface MisRechargeTransactionRequestMapper {
    int deleteByPrimaryKey(BigDecimal rid);

    int insert(MisRechargeTransactionRequest record);

    int insertSelective(MisRechargeTransactionRequest record);

    MisRechargeTransactionRequest selectByPrimaryKey(BigDecimal rid);

    int updateByPrimaryKeySelective(MisRechargeTransactionRequest record);

    int updateByPrimaryKey(MisRechargeTransactionRequest record);

    MisRechargeTransactionRequest findByOrderNo(@Param("orderNo") String orderNo);
}