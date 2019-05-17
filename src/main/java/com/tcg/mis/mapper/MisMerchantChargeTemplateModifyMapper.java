package com.tcg.mis.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcg.mis.model.MisMerchantChargeTemplateModify;

public interface MisMerchantChargeTemplateModifyMapper {

	void insert(MisMerchantChargeTemplateModify entity);
    void update(MisMerchantChargeTemplateModify entity);
	
	List<MisMerchantChargeTemplateModify> findByCondition(@Param("templateRid") Long templateRid, @Param("status") Integer status, @Param("createTime")Date createTime);
	
	MisMerchantChargeTemplateModify findOne(@Param("rid") Long rid);

}

