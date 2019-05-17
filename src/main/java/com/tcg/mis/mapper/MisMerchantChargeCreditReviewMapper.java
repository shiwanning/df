package com.tcg.mis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.model.MisMerchantChargeCreditReview;
import com.tcg.mis.to.condition.CreditReviewCondition;

public interface MisMerchantChargeCreditReviewMapper {

	void insert(MisMerchantChargeCreditReview entity);
    void update(MisMerchantChargeCreditReview entity);
	
	MisMerchantChargeCreditReview findOne(@Param("rid") Long rid);

	List<MisMerchantChargeCreditReview> findByCondition(@Param("condition") CreditReviewCondition condition,
			@Param("page") PaginationAndOrderVO page);
	
	MisMerchantChargeCreditReview findUnReviewById(@Param("rid") Long rid);
	
	MisMerchantChargeCreditReview findLastUnReviewByMerchantCode(@Param("merchantCode") String merchantCode);

}
