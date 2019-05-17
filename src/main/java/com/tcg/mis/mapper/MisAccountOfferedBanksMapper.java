package com.tcg.mis.mapper;

import com.tcg.mis.model.MisAccountOfferedBanks;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MisAccountOfferedBanksMapper {
    int deleteByPrimaryKey(BigDecimal rid);

    int insert(MisAccountOfferedBanks record);

    int insertSelective(MisAccountOfferedBanks record);

    MisAccountOfferedBanks selectByPrimaryKey(BigDecimal rid);

    int updateByPrimaryKeySelective(MisAccountOfferedBanks record);

    int updateByPrimaryKey(MisAccountOfferedBanks record);

    List<MisAccountOfferedBanks> findByAcctId(@Param("acctId") Long acctId , @Param("bankType") String bankType);

    List<String> findAllPaymentTypes();

    MisAccountOfferedBanks findByOrderNo(@Param("orderNo") String orderNo);
}