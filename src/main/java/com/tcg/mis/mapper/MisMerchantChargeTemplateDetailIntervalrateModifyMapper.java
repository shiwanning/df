package com.tcg.mis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcg.mis.model.MisMerchantChargeTemplateDetailIntervalrateModify;

public interface MisMerchantChargeTemplateDetailIntervalrateModifyMapper {

	void insert(MisMerchantChargeTemplateDetailIntervalrateModify entity);
    void update(MisMerchantChargeTemplateDetailIntervalrateModify entity);
    
    List<MisMerchantChargeTemplateDetailIntervalrateModify> findByDetailRid(@Param("detailRid") Long detailRid);
	
}
