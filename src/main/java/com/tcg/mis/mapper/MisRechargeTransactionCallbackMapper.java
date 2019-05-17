package com.tcg.mis.mapper;

import com.tcg.mis.model.MisRechargeTransactionCallback;
import java.math.BigDecimal;

public interface MisRechargeTransactionCallbackMapper {
    int deleteByPrimaryKey(BigDecimal rid);

    int insert(MisRechargeTransactionCallback record);

    int insertSelective(MisRechargeTransactionCallback record);

    MisRechargeTransactionCallback selectByPrimaryKey(BigDecimal rid);

    int updateByPrimaryKeySelective(MisRechargeTransactionCallback record);

    int updateByPrimaryKey(MisRechargeTransactionCallback record);
}