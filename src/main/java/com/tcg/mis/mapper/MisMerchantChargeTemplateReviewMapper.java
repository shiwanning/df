package com.tcg.mis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.model.MisMerchantChargeTemplateModify;
import com.tcg.mis.model.MisMerchantChargeTemplateReview;
import com.tcg.mis.to.condition.ChargeTemplateModifyCondition;

public interface MisMerchantChargeTemplateReviewMapper {

	void insert(MisMerchantChargeTemplateReview entity);
    void update(MisMerchantChargeTemplateReview entity);

    List<MisMerchantChargeTemplateReview> findByCondition(@Param("condition") ChargeTemplateModifyCondition condition,
    		@Param("page") PaginationAndOrderVO page);
	
	MisMerchantChargeTemplateReview findByTemplateId(@Param("templateId") Long templateId);
	
	MisMerchantChargeTemplateReview findOne(@Param("rid") Long rid);
	
	MisMerchantChargeTemplateReview findUnReviewById(@Param("rid") Long rid);
	
	MisMerchantChargeTemplateReview findUnReviewByTemplateId(@Param("templateRid") Long templateRid);
	
}
