package com.tcg.mis.mapper;

import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.model.MisWithdrawTransaction;
import com.tcg.mis.to.condition.PageCondition;
import com.tcg.mis.to.response.MisWithdrawTo;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MisWithdrawTransactionMapper {
    int deleteByPrimaryKey(BigDecimal rid);

    int insert(MisWithdrawTransaction record);

    int insertSelective(MisWithdrawTransaction record);

    MisWithdrawTransaction selectByPrimaryKey(BigDecimal rid);

    int updateByPrimaryKeySelective(MisWithdrawTransaction record);

    int updateByPrimaryKey(MisWithdrawTransaction record);

    MisWithdrawTransaction findByOrderNo(@Param("orderNo")String orderNo);

    List<MisWithdrawTo> findByCondition(@Param("condition") PageCondition condition, @Param("page") PaginationAndOrderVO page);

    MisWithdrawTo sumByCondition(@Param("condition") PageCondition condition);
}