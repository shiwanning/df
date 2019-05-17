package com.tcg.mis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcg.mis.model.MisMerchantChargeConfig;

public interface MisMerchantChargeConfigMapper {

	void insert(MisMerchantChargeConfig entity);
    void update(MisMerchantChargeConfig entity);
	
	void deleteByMerchantCode(@Param("merchantCode") String merchantCode);
    List<MisMerchantChargeConfig> findByMerchantCode(@Param("merchantCode") String merchantCode);
    List<MisMerchantChargeConfig> findByTemplateRid(@Param("templateRid") Long templateRid);
	
}
