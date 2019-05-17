package com.tcg.mis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcg.mis.model.MisMerchantChargeTemplateDetailIntervalrate;

public interface MisMerchantChargeTemplateDetailIntervalrateMapper {

	void insert(MisMerchantChargeTemplateDetailIntervalrate entity);
    void update(MisMerchantChargeTemplateDetailIntervalrate entity);
	List<MisMerchantChargeTemplateDetailIntervalrate> findByDetailRid(@Param("detailRid") Long detailRid);
	
}
