package com.tcg.mis.mapper;

import com.tcg.mis.model.vo.AcsAccount;
import com.tcg.mis.model.vo.MerchantProductWalletTo;
import com.tcg.mis.to.condition.AcsAccountCondition;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AcsAccountMapper {

    List<AcsAccount> findAcsAccount(@Param("condition")AcsAccountCondition condition);

    List<MerchantProductWalletTo> findMerchantProductWalletInfo(@Param("merchantCode")String merchantCode);

    List<MerchantProductWalletTo> findMerchantWallet(@Param("merchantCode")String merchantCode);

    List<AcsAccount> findAcsAccounts(@Param("customerId") Long customerId);

}