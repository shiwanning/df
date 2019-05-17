package com.tcg.mis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.model.MisMerchantChargeTemplate;
import com.tcg.mis.to.condition.ChargeTemplateCondition;

public interface MisMerchantChargeTemplateMapper {
	
    void insert(MisMerchantChargeTemplate entity);
    void update(MisMerchantChargeTemplate entity);

     List<MisMerchantChargeTemplate> findByCondition(@Param("condition") ChargeTemplateCondition condition, @Param("page") PaginationAndOrderVO page);

     List<MisMerchantChargeTemplate> findByTemplateType(@Param("templateType") Integer templateType);
	 MisMerchantChargeTemplate findByReviewRid(@Param("reviewRid") Long rid);
	 MisMerchantChargeTemplate findOne(@Param("rid") Long rid);
}
