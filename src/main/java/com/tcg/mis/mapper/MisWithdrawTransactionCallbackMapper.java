package com.tcg.mis.mapper;

import com.tcg.mis.model.MisWithdrawTransactionCallback;
import java.math.BigDecimal;

public interface MisWithdrawTransactionCallbackMapper {
    int deleteByPrimaryKey(BigDecimal rid);

    int insert(MisWithdrawTransactionCallback record);

    int insertSelective(MisWithdrawTransactionCallback record);

    MisWithdrawTransactionCallback selectByPrimaryKey(BigDecimal rid);

    int updateByPrimaryKeySelective(MisWithdrawTransactionCallback record);

    int updateByPrimaryKey(MisWithdrawTransactionCallback record);
}