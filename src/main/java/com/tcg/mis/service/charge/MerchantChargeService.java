package com.tcg.mis.service.charge;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.common.response.SimplePageResponse;
import com.tcg.mis.model.vo.MerchantChargeProductTo;
import com.tcg.mis.to.FileInfoTo;
import com.tcg.mis.to.response.MerchantCashPledgeTo;
import com.tcg.mis.to.response.MerchantChargeCreditReviewTo;
import com.tcg.mis.to.response.MerchantChargeTemplateConfigTO;
import com.tcg.mis.to.response.MerchantChargeTemplateMerchangsTO;
import com.tcg.mis.to.response.MerchantChargeTemplateReviewTO;
import com.tcg.mis.to.response.MerchantChargeTemplateTO;
import com.tcg.mis.to.response.MerchantChargeTemplateTypeTO;
import com.tcg.mis.to.response.MerchantCreditDetailTo;
import com.tcg.mis.to.response.MerchantCreditTo;


public interface MerchantChargeService {

	SimplePageResponse<MerchantChargeTemplateTO> getTemplateList(String merchant, String templateName, Integer templateType, String operator, Date startDate, Date endDate, PaginationAndOrderVO page);

    List<MerchantChargeTemplateTypeTO> getTemplateTypeList(Integer templateType);

    SimplePageResponse<MerchantChargeTemplateReviewTO> getReviewList(String merchant, String templateName, String operator, Date startDate, Date endDate, Integer status, PaginationAndOrderVO page);

    MerchantChargeTemplateTO getTemplateById(Long rid);

    void createTemplate(MerchantChargeTemplateTO to,String operatorName);

    void updateTemplate(MerchantChargeTemplateTO to, String operatorName);

    Boolean approveTemplateModify(Long rid, String comment, String operatorName);

    void rejectTemplateModify(Long rid, String comment, String operatorName);

    boolean isWaitingForReview(Long rid);

    boolean isUnReview(Long rid);

    MerchantChargeTemplateReviewTO getReviewDiff(Long rid);

    void merchantConfig(MerchantChargeTemplateConfigTO to, String operatorName);

    List<MerchantChargeTemplateTypeTO> getMerchantTemplateList(String merchantCode);

    MerchantChargeTemplateMerchangsTO getTemplateMerchantList(Long templateRid);

    Boolean isValidTemplateTO(MerchantChargeTemplateTO templateTO);

    void updateMerchantCreditInfo(MerchantCreditTo merchantCreditTo, String operatorName);

    MerchantCreditDetailTo getMerchantCreditInfo(String merchantCode);

    void rejectCreditReview(Long rid, String remark, String operatorName);

    void approveCreditReview(Long rid, String remark, String operatorName);

    SimplePageResponse<MerchantChargeCreditReviewTo> getCreditReviewList(String merchant, BigDecimal leverMultiplier, String operator, Date startDate, Date endDate, Integer status, PaginationAndOrderVO page);

    MerchantChargeProductTo getProductInfo(String merchant);

    Boolean updateMerchantAccountAmount(String merchantCode, BigDecimal amount, Boolean isCashPledge, String remark, String operatorName, FileInfoTo file);

    Boolean cashoutMerchantAccountAmount(String merchantCode, BigDecimal amount, Boolean isCashPledge, String remark);

    SimplePageResponse<MerchantCashPledgeTo> getMerchantCashPledgeTo(String merchantCode, PaginationAndOrderVO page);
	
}
