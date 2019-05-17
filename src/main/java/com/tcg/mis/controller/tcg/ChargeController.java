package com.tcg.mis.controller.tcg;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tcg.mis.client.GlsClientService;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.common.response.BaseCollectionResponseT;
import com.tcg.mis.common.response.BaseResponse;
import com.tcg.mis.common.response.BaseResponseT;
import com.tcg.mis.common.response.SimplePageResponse;
import com.tcg.mis.common.util.NamingUtils;
import com.tcg.mis.model.vo.MerchantChargeProductTo;
import com.tcg.mis.service.charge.MerchantChargeService;
import com.tcg.mis.service.subsystem.OSService;
import com.tcg.mis.to.FileInfoTo;
import com.tcg.mis.to.OperatorInfo;
import com.tcg.mis.to.WalletTo;
import com.tcg.mis.to.request.CamelPageAndSortTo;
import com.tcg.mis.to.response.MerchantCashPledgeTo;
import com.tcg.mis.to.response.MerchantChargeCreditReviewTo;
import com.tcg.mis.to.response.MerchantChargeTemplateConfigTO;
import com.tcg.mis.to.response.MerchantChargeTemplateMerchangsTO;
import com.tcg.mis.to.response.MerchantChargeTemplateReviewTO;
import com.tcg.mis.to.response.MerchantChargeTemplateTO;
import com.tcg.mis.to.response.MerchantChargeTemplateTypeTO;
import com.tcg.mis.to.response.MerchantCreditDetailTo;
import com.tcg.mis.to.response.MerchantCreditTo;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/merchant/charge", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChargeController {


    @Autowired
    private MerchantChargeService merchantChargeService;
    @Autowired
    private GlsClientService glsClientService;
    @Autowired
    private OSService ossService;

    @GetMapping(value = {"/template/list", "/template/wps-list"})
    public SimplePageResponse<MerchantChargeTemplateTO> getTemplateList(
            @RequestParam(value = "merchantCode", required = false) String merchantCode,
            @RequestParam(value = "templateName", required = false) String templateName,
            @RequestParam(value = "templateType", required = false) Integer templateType,
            @RequestParam(value = "operator", required = false) String operator,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "startDate", required = false) Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "endDate", required = false) Date endDate,
            @RequestParam(value = "pageNo", required = true) int pageNo,
            @RequestParam(value = "pageSize", required = true) int pageSize,
            @RequestParam(value = "sortColumn", required = true, defaultValue="updateTime") String sortColumn,
            @RequestParam(value = "sortType", required = true, defaultValue="desc") String sortType) {

    	PaginationAndOrderVO pageable = new PaginationAndOrderVO(pageNo - 1, pageSize);
        if(sortColumn != null && sortType != null) {
        	pageable.addSort(NamingUtils.camelToUnderline(sortColumn), sortType);
        }

        return merchantChargeService.getTemplateList(merchantCode, templateName, templateType, operator, startDate, endDate, pageable);
    }

    @GetMapping("/template/types")
    public BaseResponseT<List<MerchantChargeTemplateTypeTO>> getTemplateTypeList(
            @RequestParam(value = "type",required = false) Integer type) {

        return new BaseResponseT<>(merchantChargeService.getTemplateTypeList(type));
    }

    @GetMapping("/template")
    public BaseResponseT<MerchantChargeTemplateTO> getTemplate(
            @RequestParam(value = "rid", required = true) Long rid) {
        MerchantChargeTemplateTO template= merchantChargeService.getTemplateById(rid);
        if(template == null) {
        	throw new MisBaseException(ErrorCode.DATA_NOT_FOUND, "No template found for id: " + rid);
        }
        template.setWaitingForReview(merchantChargeService.isWaitingForReview(rid));
        return new BaseResponseT<>(template);
    }

    @PostMapping("/template")
    public BaseResponse createTemplate(@RequestBody MerchantChargeTemplateTO to,
    		@ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token) {
        OperatorInfo userInfo = ossService.getCurrentUser(token);
        if(to.getDetailList()==null || to.getDetailList().isEmpty()){
            return new BaseResponse(ErrorCode.TEMPLATE_DETAIL_EMPTY);
        }
        if(merchantChargeService.getTemplateList(null, to.getName(), null, null, null, null,new PaginationAndOrderVO(0, 10)).getTotal() > 0){
            return new BaseResponse(ErrorCode.TEMPLATE_NAME_DUPLICATE);
        }
        if(!merchantChargeService.isValidTemplateTO(to)){
            return new BaseResponse(ErrorCode.TEMPLATE_CONTENT_DUPLICATE);
        }
        merchantChargeService.createTemplate(to, userInfo.getOperatorName());
        return new BaseResponse(true);
    }

    @PutMapping("/template")
    public BaseResponse updateTemplate(@RequestBody MerchantChargeTemplateTO to,
    		@ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token) {
        OperatorInfo userInfo = ossService.getCurrentUser(token);
        if(to.getDetailList()==null || to.getDetailList().isEmpty()){
            return new BaseResponse(ErrorCode.TEMPLATE_DETAIL_EMPTY);
        }
        if(merchantChargeService.isWaitingForReview(to.getRid())){
            return new BaseResponse(ErrorCode.REVIEW_TEMPLATE_EXIST);
        }
        merchantChargeService.updateTemplate(to, userInfo.getOperatorName());
        return new BaseResponse(true);
    }

    @PostMapping("/review/approve")
    public BaseResponse approveTemplateModify(
            @RequestParam(value = "rid", required = true) Long rid,
            @RequestParam(value = "comment", required = true) String comment,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token) {
        OperatorInfo userInfo = ossService.getCurrentUser(token);
        if(!merchantChargeService.isUnReview(rid)){
            return new BaseResponse(ErrorCode.REVIEW_TEMPLATE_NON_EXIST);
        }
        if(merchantChargeService.approveTemplateModify(rid, comment, userInfo.getOperatorName())){
            return new BaseResponse(true);
        }else{
            return new BaseResponse(ErrorCode.TEMPLATE_CONTENT_DUPLICATE);
        }
    }

    @PostMapping("/review/reject")
    public BaseResponse rejectTemplateModify(
            @RequestParam(value = "rid", required = true) Long rid,
            @RequestParam(value = "comment", required = true) String comment,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token) {
    	OperatorInfo userInfo = ossService.getCurrentUser(token);
        if(!merchantChargeService.isUnReview(rid)){
            return new BaseResponse(ErrorCode.REVIEW_TEMPLATE_NON_EXIST);
        }
        merchantChargeService.rejectTemplateModify(rid, comment, userInfo.getOperatorName());
        return new BaseResponse(true);
    }

    @GetMapping("/review/list")
    public SimplePageResponse<MerchantChargeTemplateReviewTO> getReviewList(
            @RequestParam(value = "merchantCode", required = false) String merchantCode,
            @RequestParam(value = "templateName", required = false) String templateName,
            @RequestParam(value = "operator", required = false) String operator,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "startDate", required = false) Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "endDate", required = false) Date endDate,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "pageNo", required = false) int pageNo,
            @RequestParam(value = "pageSize", required = false) int pageSize) {

        PaginationAndOrderVO pageable = new PaginationAndOrderVO(pageNo - 1, pageSize);
        
        pageable.addSort("update_time", "desc");
        
        return merchantChargeService.getReviewList(merchantCode, templateName, operator, startDate, endDate, status, pageable);

    }

    @GetMapping("/review/diff")
    public BaseResponseT<MerchantChargeTemplateReviewTO> getReviewDiff(
            @RequestParam(value = "rid", required = true) Long rid) {
        return new BaseResponseT<>(merchantChargeService.getReviewDiff(rid));
    }

    @PostMapping("/config")
    public BaseResponse saveConfig(
            @RequestBody MerchantChargeTemplateConfigTO to, 
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token) {
        OperatorInfo userInfo = ossService.getCurrentUser(token);
        merchantChargeService.merchantConfig(to, userInfo.getOperatorName());
        return new BaseResponse(true);
    }

    @GetMapping("/merchant/templates")
    public BaseCollectionResponseT<MerchantChargeTemplateTypeTO> getMerchantTemplateList(
            @RequestParam(value = "merchantCode") String merchantCode) {
        return new BaseCollectionResponseT<>(merchantChargeService.getMerchantTemplateList(merchantCode));
    }

    @GetMapping("/merchant/product/info")
    public BaseResponseT<MerchantChargeProductTo> getMerchantTemplateDetailList(
            @RequestParam(value = "merchantCode") String merchantCode) {
        return new BaseResponseT<>(merchantChargeService.getProductInfo(merchantCode));
    }
    
    @GetMapping("/merchant/product/cash-pledge-info")
    public SimplePageResponse<MerchantCashPledgeTo> getCashPledgeInfo(
            @RequestParam(value = "merchantCode", required = false) String merchantCode,
            @Validated CamelPageAndSortTo pageAndSortTo
            ) {
    	
    	pageAndSortTo.withDefaultSortColumn("merchantCode").withDefaultSortType("asc");
    	
        return merchantChargeService.getMerchantCashPledgeTo(merchantCode, pageAndSortTo.generatePaginationAndOrderVO());
    }

    @GetMapping("/template/merchants")
    public BaseResponseT<MerchantChargeTemplateMerchangsTO> getTemplateMerchantList(
            @RequestParam(value = "templateId") Long templateId) {
        return new BaseResponseT<>(merchantChargeService.getTemplateMerchantList(templateId));
    }

    @GetMapping("/wallet/info")
    public BaseCollectionResponseT<WalletTo> getWalletInfo() {
        return new BaseCollectionResponseT<>(glsClientService.getWalletInfo());
    }

    @PutMapping("/credit/info")
    public BaseResponse updateLeverMultiplier(
            @RequestBody MerchantCreditTo merchantCreditTo,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token) {
        OperatorInfo userInfo = ossService.getCurrentUser(token);
        merchantChargeService.updateMerchantCreditInfo(merchantCreditTo , userInfo.getOperatorName());
        return new BaseResponse(true);
    }

    @GetMapping("/credit/info")
    public BaseResponseT<MerchantCreditDetailTo> getLeverMultiplier(
            @RequestParam(value = "merchantCode", required = true) String merchantCode) {
        return new BaseResponseT<>(merchantChargeService.getMerchantCreditInfo(merchantCode));
    }

    @PostMapping("/credit/review/approve")
    public BaseResponse approveCreditReview(
            @RequestParam(value = "rid", required = true) Long rid,
            @RequestParam(value = "comment", required = true) String comment,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token) {
        OperatorInfo userInfo = ossService.getCurrentUser(token);
        merchantChargeService.approveCreditReview(rid, comment, userInfo.getOperatorName());
        return new BaseResponse(true);
    }

    @PostMapping("/credit/review/reject")
    public BaseResponse rejectCreditReview(
            @RequestParam(value = "rid", required = true) Long rid,
            @RequestParam(value = "comment", required = true) String comment,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token) {
        OperatorInfo userInfo = ossService.getCurrentUser(token);
        merchantChargeService.rejectCreditReview(rid, comment, userInfo.getOperatorName());
        return new BaseResponse(true);
    }

    @GetMapping("/credit/review/list")
    public SimplePageResponse<MerchantChargeCreditReviewTo> getReviewList(
            @RequestParam(value = "merchantCode", required = false) String merchantCode,
            @RequestParam(value = "leverMultiplier", required = false) BigDecimal leverMultiplier,
            @RequestParam(value = "operator", required = false) String operator,
            @RequestParam(value = "status", required = false) Integer status,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "startDate", required = false) Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "endDate", required = false) Date endDate,
            @RequestParam(value = "pageNo", required = false) int pageNo,
            @RequestParam(value = "pageSize", required = false) int pageSize) {

        PaginationAndOrderVO pageable = new PaginationAndOrderVO(pageNo - 1, pageSize);
        pageable.addSort("RID", "DESC");

        return merchantChargeService.getCreditReviewList(merchantCode, leverMultiplier, operator, startDate, endDate, status, pageable);
    }
	
}
