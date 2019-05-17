package com.tcg.mis.mapper;

import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.model.MisProductTransaction;
import com.tcg.mis.to.condition.ProductTransactionCondition;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MisProductTransactionMapper {
    int deleteByPrimaryKey(BigDecimal rid);

    int insert(MisProductTransaction record);

    int insertSelective(MisProductTransaction record);

    MisProductTransaction selectByPrimaryKey(BigDecimal rid);

    int updateByPrimaryKeySelective(MisProductTransaction record);

    int updateByPrimaryKey(MisProductTransaction record);

    List<MisProductTransaction> findByCondition(@Param("condition") ProductTransactionCondition condition, @Param("page") PaginationAndOrderVO page);

}