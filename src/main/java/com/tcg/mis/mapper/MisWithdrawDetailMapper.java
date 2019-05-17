package com.tcg.mis.mapper;

import com.tcg.mis.model.MisWithdrawDetail;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface MisWithdrawDetailMapper {
    int deleteByPrimaryKey(BigDecimal rid);

    int insert(MisWithdrawDetail record);

    int insertSelective(MisWithdrawDetail record);

    MisWithdrawDetail selectByPrimaryKey(BigDecimal rid);

    int updateByPrimaryKeySelective(MisWithdrawDetail record);

    int updateByPrimaryKey(MisWithdrawDetail record);

    Integer findWithdrawBankAccountAcsAcctId(@Param("orderNo") String orderNo);

    BigDecimal findWithdrawCharge(@Param("orderNo") String orderNo);
}