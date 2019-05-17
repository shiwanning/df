package com.tcg.mis.mapper;

import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.model.MisRechargeTransaction;
import com.tcg.mis.to.condition.RechargeTransactionCondition;
import com.tcg.mis.to.response.MisRechargeTo;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MisRechargeTransactionMapper {
    int deleteByPrimaryKey(BigDecimal rid);

    int insert(MisRechargeTransaction record);

    int insertSelective(MisRechargeTransaction record);

    MisRechargeTransaction selectByPrimaryKey(BigDecimal rid);

    int updateByPrimaryKeySelective(MisRechargeTransaction record);

    int updateByPrimaryKey(MisRechargeTransaction record);

    List<MisRechargeTo> findByCondition(@Param("condition") RechargeTransactionCondition condition, @Param("page") PaginationAndOrderVO page);

    MisRechargeTo sumByCondition(@Param("condition") RechargeTransactionCondition condition);

    MisRechargeTransaction findByOrderNo(@Param("orderNo") String orderNo);
}