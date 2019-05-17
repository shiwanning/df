package com.tcg.mis.mapper;

import com.tcg.mis.model.MisRechargeDetail;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface MisRechargeDetailMapper {
    int deleteByPrimaryKey(BigDecimal rid);

    int insert(MisRechargeDetail record);

    int insertSelective(MisRechargeDetail record);

    MisRechargeDetail selectByPrimaryKey(BigDecimal rid);

    int updateByPrimaryKeySelective(MisRechargeDetail record);

    int updateByPrimaryKey(MisRechargeDetail record);

    MisRechargeDetail findByOrderNo(@Param("orderNo") String orderNo);
}