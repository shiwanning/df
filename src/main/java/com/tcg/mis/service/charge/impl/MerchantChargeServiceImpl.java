package com.tcg.mis.service.charge.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tcg.mis.client.AcsClientService;
import com.tcg.mis.client.GlsClientService;
import com.tcg.mis.common.constant.ACSConstants;
import com.tcg.mis.common.constant.AcsAccountType;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.constant.MerchantChargeReviewStatus;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.common.response.SimplePageResponse;
import com.tcg.mis.common.util.OrderNoUtil;
import com.tcg.mis.mapper.MerchantMapper;
import com.tcg.mis.mapper.MisMerchantChargeConfigMapper;
import com.tcg.mis.mapper.MisMerchantChargeCreditReviewMapper;
import com.tcg.mis.mapper.MisMerchantChargeTemplateDetailIntervalrateMapper;
import com.tcg.mis.mapper.MisMerchantChargeTemplateDetailIntervalrateModifyMapper;
import com.tcg.mis.mapper.MisMerchantChargeTemplateDetailMapper;
import com.tcg.mis.mapper.MisMerchantChargeTemplateDetailModifyMapper;
import com.tcg.mis.mapper.MisMerchantChargeTemplateMapper;
import com.tcg.mis.mapper.MisMerchantChargeTemplateModifyMapper;
import com.tcg.mis.mapper.MisMerchantChargeTemplateReviewMapper;
import com.tcg.mis.model.MisMerchantChargeConfig;
import com.tcg.mis.model.MisMerchantChargeCreditReview;
import com.tcg.mis.model.MisMerchantChargeTemplate;
import com.tcg.mis.model.MisMerchantChargeTemplateDetail;
import com.tcg.mis.model.MisMerchantChargeTemplateDetailIntervalrate;
import com.tcg.mis.model.MisMerchantChargeTemplateDetailIntervalrateModify;
import com.tcg.mis.model.MisMerchantChargeTemplateDetailModify;
import com.tcg.mis.model.MisMerchantChargeTemplateModify;
import com.tcg.mis.model.MisMerchantChargeTemplateReview;
import com.tcg.mis.model.vo.Merchant;
import com.tcg.mis.model.vo.MerchantChargeProductTo;
import com.tcg.mis.service.charge.MerchantChargeService;
import com.tcg.mis.service.recharge.RechargeService;
import com.tcg.mis.to.FileInfoTo;
import com.tcg.mis.to.MerchantWalletManageTo;
import com.tcg.mis.to.TemplateCacheTo;
import com.tcg.mis.to.WalletTo;
import com.tcg.mis.to.client.AccountInfo;
import com.tcg.mis.to.condition.ChargeTemplateCondition;
import com.tcg.mis.to.condition.ChargeTemplateModifyCondition;
import com.tcg.mis.to.condition.CreditReviewCondition;
import com.tcg.mis.to.response.MerchantCashPledgeTo;
import com.tcg.mis.to.response.MerchantChargeCreditReviewTo;
import com.tcg.mis.to.response.MerchantChargeDetailTO;
import com.tcg.mis.to.response.MerchantChargeIntervalrateTO;
import com.tcg.mis.to.response.MerchantChargeTemplateConfigTO;
import com.tcg.mis.to.response.MerchantChargeTemplateMerchangsTO;
import com.tcg.mis.to.response.MerchantChargeTemplateModifyTO;
import com.tcg.mis.to.response.MerchantChargeTemplateReviewTO;
import com.tcg.mis.to.response.MerchantChargeTemplateTO;
import com.tcg.mis.to.response.MerchantChargeTemplateTypeTO;
import com.tcg.mis.to.response.MerchantCreditDetailTo;
import com.tcg.mis.to.response.MerchantCreditTo;
import com.tcg.mis.to.response.MerchantProductDetailTo;
import com.tcg.mis.to.response.MerchantTo;

@Service
public class MerchantChargeServiceImpl implements MerchantChargeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantChargeServiceImpl.class);
	
	@Autowired
	private RechargeService rechargeService;
	
	@Autowired
	private MisMerchantChargeConfigMapper merchantChargeConfigMapper;
	
	@Autowired
	private MisMerchantChargeCreditReviewMapper creditReviewMapper;
	
	@Autowired
	private MisMerchantChargeTemplateDetailIntervalrateMapper detailIntervalrateMapper;
	
	@Autowired
	private MisMerchantChargeTemplateDetailIntervalrateModifyMapper detailIntervalrateModifyMapper;
	
	@Autowired
	private MisMerchantChargeTemplateDetailMapper templateDetailMapper;
	
	@Autowired
	private MisMerchantChargeTemplateDetailModifyMapper templateDetailModifyMapper;
	
	@Autowired
	private MisMerchantChargeTemplateMapper templateMapper;
	
	@Autowired
	private MisMerchantChargeTemplateModifyMapper templateModifyMapper;
	
	@Autowired
	private MisMerchantChargeTemplateReviewMapper templateReviewMapper;
	
	@Autowired
	private MerchantMapper merchantMapper;
	
	@Autowired
    private AcsClientService acsClientService;
	
	@Autowired
	private GlsClientService glsClientService;
	
	@Override
	public SimplePageResponse<MerchantChargeTemplateTO> getTemplateList(String merchantCode, String templateName,
			Integer templateType, String operator, Date startDate, Date endDate, PaginationAndOrderVO page) {
		
		ChargeTemplateCondition condition = new ChargeTemplateCondition();
		
		condition.setMerchantCode(merchantCode);
		condition.setTemplateName(templateName);
		condition.setTemplateType(templateType);
		condition.setOperator(operator);
		condition.setStartDate(startDate);
		condition.setEndDate(endDate);
		
		List<MisMerchantChargeTemplate> result = templateMapper.findByCondition(condition, page);

        List<MerchantChargeTemplateTO> to = convertMerchantChargeTemplateTO(result);

        return new SimplePageResponse<>(to, page);
	}
	
	private List<MerchantChargeTemplateTO> convertMerchantChargeTemplateTO(List<MisMerchantChargeTemplate> templates) {
        List<MerchantChargeTemplateTO> targets = Lists.newLinkedList();

        for(MisMerchantChargeTemplate source : templates) {
            targets.add(convertMerchantChargeTemplateTO(source));
        }
        
        return targets;
    }
	
    private MerchantChargeTemplateTO convertMerchantChargeTemplateTO(MisMerchantChargeTemplate templates) {
        MerchantChargeTemplateTO target = new MerchantChargeTemplateTO();
        BeanUtils.copyProperties(templates, target);
        List<MisMerchantChargeTemplateDetail> details = templateDetailMapper.findByTemplateRid(templates.getRid());
        target.setDetailList(convertMerchantChargeDetailList(details));
	    return target;
	}
    
    private List<MerchantChargeDetailTO> convertMerchantChargeDetailList(List<MisMerchantChargeTemplateDetail> detailList){
        List<MerchantChargeDetailTO> detailTOs = Lists.newLinkedList();

        for(MisMerchantChargeTemplateDetail detail : detailList){
            detailTOs.add(convertMerchantChargeDetailTo(detail));
        }
        return detailTOs;
    }
    
    private MerchantChargeDetailTO convertMerchantChargeDetailTo(MisMerchantChargeTemplateDetail detail){
        MerchantChargeDetailTO detailTO = new MerchantChargeDetailTO();
        BeanUtils.copyProperties(detail, detailTO);
        detailTO.setIntervalrateList(convertIntervalrateList(detail));
        return detailTO;
    }
    
    private List<MerchantChargeIntervalrateTO> convertIntervalrateList(MisMerchantChargeTemplateDetail detail){
        List<MerchantChargeIntervalrateTO> intervalrateTOS = Lists.newLinkedList();
        List<MisMerchantChargeTemplateDetailIntervalrate> ms = detailIntervalrateMapper.findByDetailRid(detail.getRid());
        for (MisMerchantChargeTemplateDetailIntervalrate intervalrate : ms) {
            MerchantChargeIntervalrateTO intervalrateTO = new MerchantChargeIntervalrateTO();
            BeanUtils.copyProperties(intervalrate, intervalrateTO);
            intervalrateTOS.add(intervalrateTO);
        }
        return intervalrateTOS;
    }
    
	@Override
	public List<MerchantChargeTemplateTypeTO> getTemplateTypeList(Integer templateType) {
		List<MisMerchantChargeTemplate> list = templateMapper.findByTemplateType(templateType);
        List<MerchantChargeTemplateTypeTO> toList = Lists.newLinkedList();
        for(MisMerchantChargeTemplate template: list){
            MerchantChargeTemplateTypeTO to = new MerchantChargeTemplateTypeTO();
            BeanUtils.copyProperties(template, to);
            toList.add(to);
        }
        return toList;
	}

	@Override
	public SimplePageResponse<MerchantChargeTemplateReviewTO> getReviewList(String merchantCode, String templateName,
			String operator, Date startDate, Date endDate, Integer status, PaginationAndOrderVO page) {
		
		ChargeTemplateModifyCondition condition = new ChargeTemplateModifyCondition();
		
		condition.setMerchantCode(merchantCode);
		condition.setTemplateName(templateName);
		condition.setOperator(operator);
		condition.setStartDate(startDate);
		condition.setEndDate(endDate);
		condition.setStatus(status);
		
		List<MisMerchantChargeTemplateReview> result = templateReviewMapper.findByCondition(condition, page);

		List<MerchantChargeTemplateReviewTO> to = convertMerchantChargeTemplateReviewTO(result);
		
		return new SimplePageResponse<>(to, page);
	}

	private List<MerchantChargeTemplateReviewTO> convertMerchantChargeTemplateReviewTO(
			List<MisMerchantChargeTemplateReview> result) {
		List<MerchantChargeTemplateReviewTO> targets = Lists.newLinkedList();
        for(MisMerchantChargeTemplateReview source : result) {

            MerchantChargeTemplateReviewTO reviewTO = new MerchantChargeTemplateReviewTO();
            MerchantChargeTemplateModifyTO templateTO = new MerchantChargeTemplateModifyTO();

            BeanUtils.copyProperties(source ,reviewTO);
            MisMerchantChargeTemplate template = templateMapper.findByReviewRid(source.getRid());

            BeanUtils.copyProperties(template ,templateTO);
            templateTO.setDetailList(null);
            reviewTO.setMerchantChargeTemplate(templateTO);
            reviewTO.setMerchantChargeTemplateModify(null);

            targets.add(reviewTO);
        }

        return targets;
	}

	@Override
	public MerchantChargeTemplateTO getTemplateById(Long rid) {
		MisMerchantChargeTemplate template = templateMapper.findOne(rid);
        return template == null ? null : convertMerchantChargeTemplateTO(template);
	}

	@Override
	public void createTemplate(MerchantChargeTemplateTO to, String operatorName) {
		MisMerchantChargeTemplate template = new MisMerchantChargeTemplate();
        BeanUtils.copyProperties(to, template);
        template.setRid(null);
        template.setCreateOperatorName(operatorName);
        template.setUpdateOperatorName(operatorName);
        templateMapper.insert(template);

        for(MerchantChargeDetailTO detailTO:to.getDetailList()){

            MisMerchantChargeTemplateDetail templateDetail = new MisMerchantChargeTemplateDetail();

            BeanUtils.copyProperties(detailTO,templateDetail);
            templateDetail.setRid(null);
            templateDetail.setTemplateRid(template.getRid());
            templateDetail.setCreateOperatorName(operatorName);
            templateDetail.setUpdateOperatorName(operatorName);
            templateDetailMapper.insert(templateDetail);

            if(detailTO.getIntervalrateList() != null && !detailTO.getIntervalrateList().isEmpty()){

                for(MerchantChargeIntervalrateTO intervalrateTO : detailTO.getIntervalrateList()){

                    MisMerchantChargeTemplateDetailIntervalrate intervalrate = new MisMerchantChargeTemplateDetailIntervalrate();
                    BeanUtils.copyProperties(intervalrateTO,intervalrate);
                    intervalrate.setRid(null);
                    intervalrate.setDetailRid(templateDetail.getTemplateRid());
                    intervalrate.setCreateOperatorName(operatorName);
                    intervalrate.setUpdateOperatorName(operatorName);
                    detailIntervalrateMapper.insert(intervalrate);
                }
            }
        }

        createTemplateModify(to, template.getRid(), operatorName , MerchantChargeReviewStatus.APPROVE.getValue());
	}

	private MisMerchantChargeTemplateModify createTemplateModify(MerchantChargeTemplateTO to, Long templateRid, String operatorName , int status){

		MisMerchantChargeTemplateModify templateModify = new MisMerchantChargeTemplateModify();
        templateModify.setRid(null);
        templateModify.setName(to.getName());
        templateModify.setTemplateType(to.getTemplateType());
        templateModify.setCreateOperatorName(operatorName);
        templateModify.setUpdateOperatorName(operatorName);
        templateModify.setStatus(status);
        templateModify.setTemplateRid(templateRid);
        templateModifyMapper.insert(templateModify);

        for(MerchantChargeDetailTO detailTO:to.getDetailList()){

            MisMerchantChargeTemplateDetailModify templateDetailModify = new MisMerchantChargeTemplateDetailModify();

            BeanUtils.copyProperties(detailTO,templateDetailModify);
            templateDetailModify.setRid(null);
            templateDetailModify.setCreateOperatorName(operatorName);
            templateDetailModify.setUpdateOperatorName(operatorName);
            templateDetailModify.setTemplateRid(templateModify.getRid());

            templateDetailModifyMapper.insert(templateDetailModify);

            if(detailTO.getIntervalrateList() != null && !detailTO.getIntervalrateList().isEmpty()){

                for(MerchantChargeIntervalrateTO intervalrateTO : detailTO.getIntervalrateList()){

                    MisMerchantChargeTemplateDetailIntervalrateModify intervalrateModify = new MisMerchantChargeTemplateDetailIntervalrateModify();
                    BeanUtils.copyProperties(intervalrateTO,intervalrateModify);
                    intervalrateModify.setRid(null);
                    intervalrateModify.setDetailRid(templateDetailModify.getRid());
                    intervalrateModify.setCreateOperatorName(operatorName);
                    intervalrateModify.setUpdateOperatorName(operatorName);
                    detailIntervalrateModifyMapper.insert(intervalrateModify);
                }
            }
        }
        return templateModify;
    }
	
	@Override
	public void updateTemplate(MerchantChargeTemplateTO to, String operatorName) {
		MisMerchantChargeTemplateModify templateModify = createTemplateModify(to, to.getRid(),operatorName , MerchantChargeReviewStatus.WAITING.getValue());

        MisMerchantChargeTemplateReview review = new MisMerchantChargeTemplateReview();
        review.setModifyRid(templateModify.getRid());
        review.setRemark(to.getRemark());
        review.setStatus(0);
        review.setCreateOperatorName(operatorName);
        review.setUpdateOperatorName(operatorName);

        templateReviewMapper.insert(review);
	}

	@Override
	public Boolean approveTemplateModify(Long rid, String comment, String operatorName) {
		MisMerchantChargeTemplateReview review = templateReviewMapper.findUnReviewById(rid);
		MisMerchantChargeTemplateModify templateModify = templateModifyMapper.findOne(review.getModifyRid());
		MisMerchantChargeTemplate originalTemplate = templateMapper.findOne(templateModify.getTemplateRid());
        MisMerchantChargeTemplate approveTemplate = convertTemplateModifyToTemplate(templateModify, originalTemplate, operatorName);

        if(!isValidTemplate(approveTemplate)){
            return false;
        }

        review.setReviewComment(comment);
        review.setUpdateOperatorName(operatorName);
        review.setStatus(MerchantChargeReviewStatus.APPROVE.getValue());
        templateReviewMapper.update(review);

        templateModify.setStatus(MerchantChargeReviewStatus.APPROVE.getValue());
        templateModify.setUpdateOperatorName(operatorName);
        templateModifyMapper.update(templateModify);
        templateDetailMapper.deleteByTemplateRid(originalTemplate.getRid());

        for(MisMerchantChargeTemplateDetail detail : approveTemplate.getDetailList() ){

            templateDetailMapper.insert(detail);
            for(MisMerchantChargeTemplateDetailIntervalrate intervalrate : detail.getIntervalrateList()){
                detailIntervalrateMapper.insert(intervalrate);
            }
        }
        return true;
	}
	
    private MisMerchantChargeTemplate convertTemplateModifyToTemplate(MisMerchantChargeTemplateModify templateModify, MisMerchantChargeTemplate template, String operatorName){

    	MisMerchantChargeTemplate approveTemplate = new MisMerchantChargeTemplate();
        BeanUtils.copyProperties(template,approveTemplate);

        List<MisMerchantChargeTemplateDetail> detailList = Lists.newLinkedList();
        List<MisMerchantChargeTemplateDetailModify> detailModifys = templateDetailModifyMapper.findByTemplateRid(templateModify.getRid());
        for(MisMerchantChargeTemplateDetailModify detailModify : detailModifys){

        	MisMerchantChargeTemplateDetail templateDetail = new MisMerchantChargeTemplateDetail();

            BeanUtils.copyProperties(detailModify,templateDetail);
            templateDetail.setRid(null);
            templateDetail.setTemplateRid(approveTemplate.getRid());
            templateDetail.setCreateOperatorName(operatorName);
            templateDetail.setUpdateOperatorName(operatorName);

            List<MisMerchantChargeTemplateDetailIntervalrateModify> intervalrates = detailIntervalrateModifyMapper.findByDetailRid(detailModify.getRid());
            
            if(!intervalrates.isEmpty()){
                List<MisMerchantChargeTemplateDetailIntervalrate> intervalrateList = Lists.newLinkedList();
                for(MisMerchantChargeTemplateDetailIntervalrateModify intervalrateModify : intervalrates){

                    MisMerchantChargeTemplateDetailIntervalrate intervalrate = new MisMerchantChargeTemplateDetailIntervalrate();
                    BeanUtils.copyProperties(intervalrateModify, intervalrate);
                    intervalrate.setRid(null);
                    intervalrate.setCreateOperatorName(operatorName);
                    intervalrate.setUpdateOperatorName(operatorName);
                    intervalrateList.add(intervalrate);
                }
                templateDetail.setIntervalrateList(intervalrateList);
            }
            detailList.add(templateDetail);
        }
        approveTemplate.setDetailList(detailList);
        return approveTemplate;
    }

	private boolean isValidTemplate(MisMerchantChargeTemplate template) {
		// 、若模板为一次性费用，则财务编号不能重复
    	if(template.getTemplateType() == 1) {
    		MisMerchantChargeTemplateDetail firstDetail = templateDetailMapper.findFirstByTemplateRid(template.getRid());
    		List<MisMerchantChargeTemplateDetail> exist = templateDetailMapper.findByDetailTypeAndCode(1, firstDetail.getCode());
    		if(exist.size() > 1) {
    			return false;
    		} else if(exist.isEmpty()) {
    			return true;
    		} else {
    			return exist.get(0).getTemplateRid().equals(template.getRid());
    		}
    	}
    	
        List<String> detailString =  Lists.newLinkedList();
        List<MisMerchantChargeTemplateDetail> details = templateDetailMapper.findByTemplateRid(template.getRid());
        for(MisMerchantChargeTemplateDetail detail : details){
            detailString.add(detail.toString());
        }
        return verifyTemplateCache(template.getTemplateType(), detailString);
	}
	
	private Boolean verifyTemplateCache(Integer templateType,List<String> detailStrings){

        List<TemplateCacheTo> cacheTos = getTemplateCache(templateType, detailStrings);

        for(TemplateCacheTo cacheTo : cacheTos){

            List<String> cacheDetails = cacheTo.getDetailStringValues();

            boolean isEqual = true;
            for(String detailString : detailStrings){
                if(!cacheDetails.contains(detailString)){
                    isEqual = false;
                    break;
                }
            }
            if(isEqual){
                return false;
            }
        }
        return true;
    }
	
    private List<TemplateCacheTo> getTemplateCache(Integer templateType, List<String> detailStrings){

        List<MisMerchantChargeTemplate> templates = templateMapper.findByTemplateType(templateType);
        List<TemplateCacheTo> cacheTos = Lists.newLinkedList();

        for(MisMerchantChargeTemplate template: templates){

        	List<MisMerchantChargeTemplateDetail> details = templateDetailMapper.findByTemplateRid(template.getRid());
        	
            if(detailStrings.size() != details.size()){
                continue;
            }

            TemplateCacheTo cacheTo = new TemplateCacheTo();
            cacheTo.setRid(template.getRid());
            cacheTo.setType(template.getTemplateType());
            cacheTo.setName(template.getName());
            List<String> stringValues = Lists.newLinkedList();
            for(MisMerchantChargeTemplateDetail detail : details){
                stringValues.add(detail.toString());
            }
            cacheTo.setDetailStringValues(stringValues);
            cacheTos.add(cacheTo);
        }
        return cacheTos;
    }

	@Override
	public void rejectTemplateModify(Long rid, String comment, String operatorName) {
		MisMerchantChargeTemplateReview review = templateReviewMapper.findUnReviewById(rid);
		MisMerchantChargeTemplateModify templateModify = templateModifyMapper.findOne(review.getModifyRid());
        review.setReviewComment(comment);
        review.setUpdateOperatorName(operatorName);
        review.setStatus(MerchantChargeReviewStatus.REJECT.getValue());
        templateReviewMapper.update(review);

        templateModify.setStatus(MerchantChargeReviewStatus.REJECT.getValue());
        templateModify.setUpdateOperatorName(operatorName);
        templateModifyMapper.update(templateModify);
	}

	@Override
	public boolean isWaitingForReview(Long templateRid) {
        return templateReviewMapper.findUnReviewByTemplateId(templateRid) != null;
	}

	@Override
	public boolean isUnReview(Long rid) {
        return templateReviewMapper.findUnReviewById(rid) != null;
	}

	@Override
	public MerchantChargeTemplateReviewTO getReviewDiff(Long rid) {
		MisMerchantChargeTemplateReview review = templateReviewMapper.findOne(rid);
		
		if(review == null) {
			throw new MisBaseException(ErrorCode.DATA_NOT_FOUND, "No review for id: " + rid);
		}
		
		MisMerchantChargeTemplateModify modifyVersion = templateModifyMapper.findOne(review.getModifyRid());
		
		if(modifyVersion == null) {
			throw new MisBaseException(ErrorCode.DATA_NOT_FOUND, "No modifyVersion for id: " + review.getModifyRid());
		}
		
        Long templateRid = modifyVersion.getTemplateRid();
        Date createTime = review.getCreateTime();

        List<MisMerchantChargeTemplateModify> list = templateModifyMapper.findByCondition(templateRid , MerchantChargeReviewStatus.APPROVE.getValue(), createTime);
        MisMerchantChargeTemplateModify beforeVersion = list.get(0);

        MerchantChargeTemplateReviewTO reviewTO = new MerchantChargeTemplateReviewTO();
        BeanUtils.copyProperties(review, reviewTO);
        reviewTO.setMerchantChargeTemplate(convertMerchantChargeTemplateTO(beforeVersion));
        reviewTO.setMerchantChargeTemplateModify(convertMerchantChargeTemplateTO(modifyVersion));

        return reviewTO;
	}

	private MerchantChargeTemplateModifyTO convertMerchantChargeTemplateTO(MisMerchantChargeTemplateModify modify) {

        MerchantChargeTemplateModifyTO target = new MerchantChargeTemplateModifyTO();
        BeanUtils.copyProperties(modify, target);
        
        target.setTemplateRid(modify.getTemplateRid());

        List<MerchantChargeDetailTO> detailTOs = Lists.newLinkedList();
        List<MisMerchantChargeTemplateDetailModify> details = templateDetailModifyMapper.findByTemplateRid(modify.getRid());
        for(MisMerchantChargeTemplateDetailModify detail : details){
            MerchantChargeDetailTO detailTO = new MerchantChargeDetailTO();
            BeanUtils.copyProperties(detail, detailTO);
            detailTOs.add(detailTO);

            List<MerchantChargeIntervalrateTO> intervalrateTOs = Lists.newLinkedList();

            List<MisMerchantChargeTemplateDetailIntervalrateModify> intervalrates = 
            		detailIntervalrateModifyMapper.findByDetailRid(detail.getRid());
            
            for(MisMerchantChargeTemplateDetailIntervalrateModify intervalrate : intervalrates){
                MerchantChargeIntervalrateTO intervalrateTO = new MerchantChargeIntervalrateTO();
                BeanUtils.copyProperties(intervalrate, intervalrateTO);
                intervalrateTOs.add(intervalrateTO);
            }
            detailTO.setIntervalrateList(intervalrateTOs);
        }
        target.setDetailList(detailTOs);
        return target;
    }

	@Override
	public void merchantConfig(MerchantChargeTemplateConfigTO to, String operatorName) {
		merchantChargeConfigMapper.deleteByMerchantCode(to.getMerchantCode());

        for(Long rid : to.getTemplateIds()){
            MisMerchantChargeTemplate template = templateMapper.findOne(rid);
            if(template == null){
                continue;
            }
            MisMerchantChargeConfig config = new MisMerchantChargeConfig();
            config.setMerchantCode(to.getMerchantCode());
            config.setTemplateRid(template.getRid());
            config.setCreateOperatorName(operatorName);
            config.setUpdateOperatorName(operatorName);
            merchantChargeConfigMapper.insert(config);
        }
	}

	@Override
	public List<MerchantChargeTemplateTypeTO> getMerchantTemplateList(String merchantCode) {
        List<MisMerchantChargeConfig> list = merchantChargeConfigMapper.findByMerchantCode(merchantCode);
        List<MerchantChargeTemplateTypeTO> toList = Lists.newLinkedList();
        for(MisMerchantChargeConfig config: list){
            MerchantChargeTemplateTypeTO to = new MerchantChargeTemplateTypeTO();
            MisMerchantChargeTemplate chargeTemplate = templateMapper.findOne(config.getTemplateRid());
            to.setRid(config.getTemplateRid());
            to.setName(chargeTemplate.getName());
            to.setTemplateType(chargeTemplate.getTemplateType());
            toList.add(to);
        }
        return toList;
	}

	@Override
	public MerchantChargeTemplateMerchangsTO getTemplateMerchantList(Long templateRid) {
		MisMerchantChargeTemplate template = templateMapper.findOne(templateRid);
		
		if(template == null) {
			throw new MisBaseException(ErrorCode.DATA_NOT_FOUND, "No data for template id: " + templateRid);
		}
		
        List<MisMerchantChargeConfig> list = merchantChargeConfigMapper.findByTemplateRid(template.getRid());

        MerchantChargeTemplateMerchangsTO to = new MerchantChargeTemplateMerchangsTO();
        to.setTemplateId(template.getRid());
        to.setTemplateName(template.getName());
        to.setMerchantCount(list.size());

        List<MerchantTo> merchantToList = Lists.newLinkedList();
        for(MisMerchantChargeConfig config : list){
            MerchantTo merchantTo = new MerchantTo();
            merchantTo.setMerchantCode(config.getMerchantCode());
            merchantTo.setMerchantNames(merchantMapper.findMerchant(config.getMerchantCode()).getMerchantName());
            merchantToList.add(merchantTo);
        }
        to.setMerchants(merchantToList);
        return to;
	}

	@Override
	public Boolean isValidTemplateTO(MerchantChargeTemplateTO templateTO) {
		List<String> detailString =  Lists.newLinkedList();
        // 、若模板为一次性费用，则财务编号不能重复
    	if(templateTO.getTemplateType() == 1) {
    		List<MisMerchantChargeTemplateDetail> exist = templateDetailMapper.findByDetailTypeAndCode(1, templateTO.getDetailList().get(0).getCode());
    		return exist.isEmpty();
    	}
        for(MerchantChargeDetailTO detailTO : templateTO.getDetailList()){
            detailString.add(detailTO.toString());
        }
        return verifyTemplateCache(templateTO.getTemplateType(), detailString);
	}

	@Override
	public void updateMerchantCreditInfo(MerchantCreditTo merchantCreditTo, String operatorName) {
		Merchant merchant = merchantMapper.findMerchant(merchantCreditTo.getMerchantCode());
        if(merchant.getLeverMultiplier()!= null && merchant.getLeverMultiplier().signum() >= 1){
        	merchant.setVirtualCashPledge(merchantCreditTo.getVirtualCashPledge());
        	merchantMapper.updateVirtualCashPledge(merchantCreditTo.getMerchantCode(), merchantCreditTo.getVirtualCashPledge());
        	
            // comment: 值一样就忽略
            if(!merchantCreditTo.getLeverMultiplier().setScale(2, BigDecimal.ROUND_HALF_UP).equals(merchant.getLeverMultiplier())) {
            	MisMerchantChargeCreditReview chargeCreditReview = new MisMerchantChargeCreditReview();
            	chargeCreditReview.setLeverMultiplier(merchant.getLeverMultiplier());
                chargeCreditReview.setModifyLeverMultiplier(merchantCreditTo.getLeverMultiplier().setScale(2, BigDecimal.ROUND_HALF_UP));
                chargeCreditReview.setMerchantCode(merchant.getMerchantCode());
                chargeCreditReview.setStatus(MerchantChargeReviewStatus.WAITING.getValue());
                chargeCreditReview.setCreateOperatorName(operatorName);
                chargeCreditReview.setUpdateOperatorName(operatorName);
                creditReviewMapper.insert(chargeCreditReview);
            } else {
            	updateVirtualAccountTotalCredit(merchant);
            }
            
        }else{
            merchant.setVirtualCashPledge(merchantCreditTo.getVirtualCashPledge());
            merchant.setLeverMultiplier(merchantCreditTo.getLeverMultiplier().setScale(2, BigDecimal.ROUND_HALF_UP));
            merchantMapper.updateVirtualCashPledgeAndLeverMultiplier(merchantCreditTo.getMerchantCode(), merchantCreditTo.getVirtualCashPledge(), merchantCreditTo.getLeverMultiplier().setScale(2, BigDecimal.ROUND_HALF_UP));
            //庚新用户虚拟信用额度
            updateVirtualAccountTotalCredit(merchant);
        }
	}
	
    private void updateVirtualAccountTotalCredit(Merchant merchant){
		AccountInfo cashPledgeWallet = acsClientService.getCustomerAccountInfo(merchant.getCustomerId(), AcsAccountType.CASH_PLEDGE.getId());
        //无压金则刷脸
        if(cashPledgeWallet != null && cashPledgeWallet.getBalance().signum() > 0){
            return;
        }

        BigDecimal totalCredit = merchant.getLeverMultiplier().multiply(merchant.getVirtualCashPledge());
        //庚新用户总信用额度
        acsClientService.addorupdateTotalCreditLimit(merchant.getCustomerId().intValue(),totalCredit);
        List<AccountInfo> accountInfoList = acsClientService.getCustomerAccountList(merchant.getCustomerId());
        List productWalletAccountTypes = Lists.newArrayList(getWalletMap().values());

        for(AccountInfo accountInfo : accountInfoList){
            if(!productWalletAccountTypes.contains(accountInfo.getAccountTypeId())){
                continue;
            }
            //更新虚拟信用额度
            acsClientService.updateCreditLimit(accountInfo.getAccountId().intValue(), totalCredit);
        }
    }
    
    private Map<String,Integer> getWalletMap(){
        Map<String,Integer> map = Maps.newHashMap();
        //2:主钱包 5:押金钱包 900:Bank Account
        List<Integer> withoutProductAccountTypeIds = Lists.newArrayList(2,5,900);
        List<WalletTo> list = glsClientService.getWalletInfo();
        for(WalletTo wallet: list){
            if(withoutProductAccountTypeIds.contains(wallet.getAccountTypeId())){
                continue;
            }
            map.put(wallet.getAccountName(),wallet.getAccountTypeId());
        }
        return map;
    }

	@Override
	public MerchantCreditDetailTo getMerchantCreditInfo(String merchantCode) {
		Merchant merchant = merchantMapper.findMerchant(merchantCode);
		MerchantCreditDetailTo merchantCreditTo = new MerchantCreditDetailTo();
		merchantCreditTo.setMerchantCode(merchantCode);
        merchantCreditTo.setVirtualCashPledge(merchant.getVirtualCashPledge());
        MerchantCreditDetailTo.LeverMultiplier lm = new MerchantCreditDetailTo.LeverMultiplier ();
        lm.setCurrentValue(merchant.getLeverMultiplier());
        MisMerchantChargeCreditReview review = creditReviewMapper.findLastUnReviewByMerchantCode(merchantCode);
        if(review == null) {
        	lm.setApproved(true);
        } else {
        	lm.setApproved(false);
        	lm.setApplyValue(review.getModifyLeverMultiplier());
        }
        
        merchantCreditTo.setLeverMultiplier(lm);
        
        return merchantCreditTo;
	}

	@Override
	public void rejectCreditReview(Long rid, String remark, String operatorName) {
		MisMerchantChargeCreditReview review = creditReviewMapper.findUnReviewById(rid);
        review.setStatus(MerchantChargeReviewStatus.REJECT.getValue());
        review.setRemark(remark);
        review.setUpdateOperatorName(operatorName);
	}

	@Override
	public void approveCreditReview(Long rid, String remark, String operatorName) {
		MisMerchantChargeCreditReview review = creditReviewMapper.findUnReviewById(rid);
        review.setStatus(MerchantChargeReviewStatus.APPROVE.getValue());
        review.setRemark(remark);
        review.setUpdateOperatorName(operatorName);
        creditReviewMapper.update(review);
        
        Merchant merchant = merchantMapper.findMerchant(review.getMerchantCode());
        merchant.setLeverMultiplier(review.getModifyLeverMultiplier());
        merchantMapper.updateVirtualCashPledgeAndLeverMultiplier(merchant.getMerchantCode(), merchant.getVirtualCashPledge(), merchant.getLeverMultiplier());

        updateTotalCredit(merchant);
	}
	
    private void updateTotalCredit(Merchant merchant){
        //更新额度
        BigDecimal totalCredit;
        AccountInfo cashPledgeWallet = acsClientService.getCustomerAccountInfo(merchant.getCustomerId(), AcsAccountType.CASH_PLEDGE.getId());
        //get用户原有总信用额度
        BigDecimal originalTotalCreditLimit = acsClientService.getTotalCreditLimit(merchant.getCustomerId().intValue());

        if(cashPledgeWallet != null && cashPledgeWallet.getBalance().signum() > 0){
            totalCredit = merchant.getLeverMultiplier().multiply(cashPledgeWallet.getBalance());
        }else{
            //无压金则刷脸
            totalCredit = merchant.getLeverMultiplier().multiply(merchant.getVirtualCashPledge());
        }

        //庚新用户总信用额度
        acsClientService.addorupdateTotalCreditLimit(merchant.getCustomerId().intValue(), totalCredit);
        List<AccountInfo> accountInfoList = acsClientService.getCustomerAccountList(merchant.getCustomerId());
        List productWalletAccountTypes = Lists.newArrayList(getWalletMap().values());

        for(AccountInfo accountInfo : accountInfoList){
            if(!productWalletAccountTypes.contains(accountInfo.getAccountTypeId())){
                continue;
            }
            //更新信用额度
            if(accountInfo.getCreditLimit() == 0 || accountInfo.getCreditLimit() == originalTotalCreditLimit.longValue()){
                acsClientService.updateCreditLimit(accountInfo.getAccountId().intValue(), totalCredit);
            }
        }
    }

	@Override
	public SimplePageResponse<MerchantChargeCreditReviewTo> getCreditReviewList(String merchantCode,
			BigDecimal leverMultiplier, String operator, Date startDate, Date endDate, Integer status,
			PaginationAndOrderVO page) {
		
		CreditReviewCondition condition = new CreditReviewCondition();
		
		condition.setMerchantCode(merchantCode);
		condition.setLeverMultiplier(leverMultiplier);
		condition.setOperator(operator);
		condition.setStartDate(startDate);
		condition.setEndDate(endDate);
		condition.setStatus(status);
		List<MisMerchantChargeCreditReview> list = creditReviewMapper.findByCondition(condition, page);

	    return convertMerchantChargeCreditReviewTO(list, page);
	}

	private SimplePageResponse<MerchantChargeCreditReviewTo> convertMerchantChargeCreditReviewTO(
			List<MisMerchantChargeCreditReview> reviews, PaginationAndOrderVO page) {
		List<MerchantChargeCreditReviewTo> targets = Lists.newLinkedList();
        for(MisMerchantChargeCreditReview source : reviews) {

            MerchantChargeCreditReviewTo reviewTO = new MerchantChargeCreditReviewTo();
            BeanUtils.copyProperties(source ,reviewTO);

            Merchant merchant = merchantMapper.findMerchant(reviewTO.getMerchantCode());
            AccountInfo cashPledgeWallet = acsClientService.getCustomerAccountInfo(merchant.getCustomerId(), AcsAccountType.CASH_PLEDGE.getId());
            reviewTO.setCashPledge((cashPledgeWallet != null && cashPledgeWallet.getBalance().signum() > 0 )? cashPledgeWallet.getBalance().longValue() : merchant.getVirtualCashPledge().longValue());
            targets.add(reviewTO);
        }

        return new SimplePageResponse<>(targets, page);
	}

	@Override
	public MerchantChargeProductTo getProductInfo(String merchantCode) {
		Merchant merchant = merchantMapper.findMerchant(merchantCode);

        List<MerchantWalletManageTo> merchantWalletList = glsClientService.getMerchantWalletInfo(merchantCode);
        
        AccountInfo mainWallet = acsClientService.getCustomerAccountInfo(merchant.getCustomerId(), AcsAccountType.MAIN.getId());
        AccountInfo cashPledgeWallet = acsClientService.getCustomerAccountInfo(merchant.getCustomerId(), AcsAccountType.CASH_PLEDGE.getId());

        MerchantChargeProductTo merchantProduct = new MerchantChargeProductTo();
        merchantProduct.setMainWallet((mainWallet==null || mainWallet.getActiveFlag() != 1) ? new BigDecimal(0) : mainWallet.getBalance());
        merchantProduct.setCashPledge((cashPledgeWallet == null || cashPledgeWallet.getActiveFlag() != 1) ? new BigDecimal(0) : cashPledgeWallet.getBalance());

        //總信用額
        merchantProduct.setMerchantCredit(acsClientService.getTotalCreditLimit(merchant.getCustomerId().intValue()));

        List<MisMerchantChargeTemplateDetail> detailList = templateDetailMapper.findProductByMerchantCode(merchant.getMerchantCode());

        List<MerchantProductDetailTo> productDetailList = getProductDetailList(detailList,merchantWalletList);

        merchantProduct.setProductDetailList(productDetailList);

        return calculateProductDetail(merchantProduct, merchant);
	}
	
	private MerchantChargeProductTo calculateProductDetail(MerchantChargeProductTo merchantProduct, Merchant merchant){

        Map<String,Integer> walletMap = getWalletMap();

        BigDecimal totalProductBalance = new BigDecimal(0);
        //剩餘信用額
        merchantProduct.setAvailableCredit(merchantProduct.getMerchantCredit());
        for(MerchantProductDetailTo productDetail:merchantProduct.getProductDetailList()){

            Integer accountTypeId = walletMap.get(productDetail.getProduct());
            
            if(accountTypeId == null) {
            	LOGGER.warn("product wallet account is null: {}", productDetail.getProduct());
            	continue;
            }

            //產品餘額
            AccountInfo productWallet = acsClientService.getCustomerAccountInfo(merchant.getCustomerId(),accountTypeId);
            BigDecimal productBalance = (productWallet != null && productWallet.getActiveFlag() == 1) ? productWallet.getBalance() : new BigDecimal(0);
            productDetail.setProductBalance(productBalance);

            if(productBalance.signum() < 0){
                merchantProduct.setAvailableCredit(merchantProduct.getAvailableCredit().add(productBalance));
            }else{
                //總產品餘額
                totalProductBalance = totalProductBalance.add(productBalance);
            }

        }
        //可用余额
        BigDecimal totalAvailableBalance = totalProductBalance.add(merchantProduct.getMainWallet()).add(merchantProduct.getAvailableCredit());
        merchantProduct.setAvailableBalance(totalAvailableBalance);
        return merchantProduct;
    }
	
	private List<MerchantProductDetailTo> getProductDetailList(List<MisMerchantChargeTemplateDetail> detailList, List<MerchantWalletManageTo> merchantWalletList){

        Map<String,MerchantProductDetailTo> productMap = Maps.newTreeMap();

        for(MerchantWalletManageTo merchantWalletManageTo : merchantWalletList){

            if(merchantWalletManageTo.getIsUse() == 0){
                continue;
            }

            MerchantProductDetailTo productDetailTo = productMap.get(merchantWalletManageTo.getAccountTypeName());
            if(productDetailTo == null){
                productDetailTo = new MerchantProductDetailTo();
                productDetailTo.setProduct(merchantWalletManageTo.getAccountTypeName());
                productDetailTo.setProductName(merchantWalletManageTo.getDisplayName());
                productMap.put(merchantWalletManageTo.getAccountTypeName(),productDetailTo);
            }
            for(MisMerchantChargeTemplateDetail detail : detailList){
                if(!merchantWalletManageTo.getAccountTypeName().equals(detail.getProduct())){
                    continue;
                }
                //保底
                if(detail.getDetailType() == 3){
                    productDetailTo.setMinimumFees(productDetailTo.getMinimumFees().add(detail.getCharge()));
                }
                productDetailTo.getDetailList().add(convertMerchantChargeDetailTo(detail));
            }
        }
        return Lists.newArrayList(productMap.values());
    }

	@Override
	public Boolean updateMerchantAccountAmount(String merchantCode, BigDecimal amount, Boolean isCashPledge,
			String remark, String operatorName, FileInfoTo file) {

        Merchant merchant = merchantMapper.findMerchant(merchantCode);
        Integer transactionCode = ACSConstants.WALLET_C_DEPOSIT_MAIN;
        
        AccountInfo mainWallet = getDepositAccount(merchant.getCustomerId(), AcsAccountType.MAIN.getId());
        String orderNo = OrderNoUtil.getOrderNo(OrderNoUtil.OrderTypeEnum.TCG);

        //主钱包加钱
        Boolean isMainSuccess = acsClientService.lodgeCreditTransaction(mainWallet.getAccountId().intValue()
                , amount, transactionCode , remark, orderNo);

        if(isMainSuccess && !isCashPledge){
            updateMisRechargeDetail(merchant.getMerchantCode(), orderNo, AcsAccountType.MAIN.getId(), amount, operatorName, file, remark);
            return true;
        } else if(isMainSuccess){

            AccountInfo pledgeWallet = getDepositAccount(merchant.getCustomerId(), AcsAccountType.CASH_PLEDGE.getId());
            //主钱包扣钱
            transactionCode = ACSConstants.WALLET_D_DEPOSIT_MAIN;
            if(acsClientService.lodgeDebitTransaction(mainWallet.getAccountId().intValue(), amount, transactionCode , remark, orderNo)){
                //押金钱包加钱
                transactionCode = ACSConstants.WALLET_C_DEPOSIT_PLEDGE;

                if(acsClientService.lodgeCreditTransaction(pledgeWallet.getAccountId().intValue()
                        , amount, transactionCode , remark, orderNo)){

                    updateMisRechargeDetail(merchant.getMerchantCode(), orderNo, AcsAccountType.CASH_PLEDGE.getId(), amount, operatorName, null, remark);
                    return true;
                }
            }
        }

        return false;
	}
	
	private void updateMisRechargeDetail(String merchantCode, String orderNo, Integer accountType, BigDecimal amount, String operator, FileInfoTo file, String remark) {
		String fileUrl = file == null ? null : file.getFileUrl();
		rechargeService.updateRechargeDetailByAdmin(orderNo, merchantCode, accountType, amount, remark, fileUrl, operator);
	}

	private AccountInfo getDepositAccount(Long customerId, Integer accountType){
        AccountInfo depositAccount = acsClientService.getCustomerAccountInfo(customerId, accountType);
        //新增主钱包
        if(depositAccount == null) {
            if (acsClientService.createCustomerAccountInfo(customerId.toString(), accountType)) {
                depositAccount = acsClientService.getCustomerAccountInfo(customerId, accountType);
            }else{
                throw new MisBaseException(ErrorCode.SYS_ERR, "no account for customerId: " + customerId + ", accountType: " + accountType);
            }
        }
        return depositAccount;
    }
	
	@Override
	public Boolean cashoutMerchantAccountAmount(String merchantCode, BigDecimal amount, Boolean isCashPledge, String remark) {

        Merchant merchant = merchantMapper.findMerchant(merchantCode);
        
        AccountInfo mainWallet = getDepositAccount(merchant.getCustomerId(), AcsAccountType.MAIN.getId());
        
        String orderNo = OrderNoUtil.getOrderNo(OrderNoUtil.OrderTypeEnum.TCG);
        
        if(mainWallet.getBalance().compareTo(amount) < 0) {
        	throw new MisBaseException(ErrorCode.SYS_ERR, "Balance not enough.");
        }
        
        //主钱包扣钱
        if(!isCashPledge) {
        	return acsClientService.lodgeDebitTransaction(mainWallet.getAccountId().intValue()
                    , amount, ACSConstants.WALLET_D_DEPOSIT_MAIN , remark, orderNo);
        }
        
        AccountInfo cashPledgeWallet = getDepositAccount(merchant.getCustomerId(), AcsAccountType.CASH_PLEDGE.getId());
        
        // 押金钱包扣前后主钱包加钱
        Boolean isCashPledgeSuccess = acsClientService.lodgeDebitTransaction(cashPledgeWallet.getAccountId().intValue()
                , amount, ACSConstants.WALLET_D_DEPOSIT_PLEDGE , remark, orderNo);
        
        if(isCashPledgeSuccess) {
        	return acsClientService.lodgeCreditTransaction(mainWallet.getAccountId().intValue()
                    , amount, ACSConstants.WALLET_C_DEPOSIT_MAIN , remark, orderNo);

        }
        
        return false;
	}

	@Override
	public SimplePageResponse<MerchantCashPledgeTo> getMerchantCashPledgeTo(String merchantCode, PaginationAndOrderVO page) {
		List<MerchantCashPledgeTo> list= merchantMapper.findMerchantCashPledgeTo(merchantCode, page);
		return new SimplePageResponse<>(list, page);
	}

}
