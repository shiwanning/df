package com.tcg.mis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcg.mis.model.MisMerchantChargeTemplateDetailModify;

public interface MisMerchantChargeTemplateDetailModifyMapper {
    
	void insert(MisMerchantChargeTemplateDetailModify entity);
    void update(MisMerchantChargeTemplateDetailModify entity);
	
    MisMerchantChargeTemplateDetailModify findOne(@Param("rid") long rid);
    List<MisMerchantChargeTemplateDetailModify> findByTemplateRid(@Param("templateRid") Long templateRid);
}
