package com.tcg.mis.service.bank.impl;

import com.google.common.collect.Lists;
import com.tcg.mis.client.PssClientService;
import com.tcg.mis.common.constant.ACSConstants;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.mapper.MisAccountOfferedBanksMapper;
import com.tcg.mis.mapper.MisBankAcctsMapper;
import com.tcg.mis.model.MisAccountOfferedBanks;
import com.tcg.mis.model.MisBankAccts;
import com.tcg.mis.service.bank.BankAccountService;
import com.tcg.mis.to.condition.BankAccountCondition;
import com.tcg.mis.to.request.FileResource;
import com.tcg.mis.to.request.SaveBankAcctTo;
import com.tcg.mis.to.request.SaveBankAcctTo.Offerd;
import com.tcg.mis.to.request.ThirdPartyAccountRequest;
import com.tcg.mis.to.response.BankAccountTo;
import com.tcg.mis.to.response.ThirdPartyAccountResponse;
import com.tcg.mis.to.response.ViewBankAcctTo;
import com.yx.acs.client.ACServiceFactory;
import com.yx.acs.model.AcsAccount;
import com.yx.acs.service.AccountChangeService;
import com.yx.us.client.USServiceFactory;
import com.yx.us.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServiceImpl.class);

	@Autowired
	private MisBankAcctsMapper misBankAcctsMapper;
	
	@Autowired
	private PssClientService pssClientService;

	private USServiceFactory ussFactory = new USServiceFactory();
	
	@Autowired
	private MisAccountOfferedBanksMapper misAccountOfferedBanksMapper;
	
	@Override
	public PageResponse<BankAccountTo, BankAccountTo> getBankAccountList(BankAccountCondition condition) {
		PaginationAndOrderVO page = condition.generatePaginationAndOrderVO();
		
		List<BankAccountTo> list = misBankAcctsMapper.findByCondition(condition, page);
		
		return new PageResponse<>(list, page, null);
	}

	@Override
	public void createBankAcct(SaveBankAcctTo saveBankAcctTo, String operatorName) {

		if(!misBankAcctsMapper.findByBankId(saveBankAcctTo.getBankId()).isEmpty()) {
			throw new MisBaseException(ErrorCode.DATA_HAS_EXISTED, "already have bank id: " + saveBankAcctTo.getBankId());
		}
		
		MisBankAccts entity = convertToMisBankAccts(saveBankAcctTo);
		List<MisAccountOfferedBanks> offerds = convertToMisAccountOffered(saveBankAcctTo);
		
		// call pss 取得 tpAcctId
		Integer tpAcctId = getTpAcctIdFromPss(saveBankAcctTo.getVendorId(), saveBankAcctTo.getParams(), saveBankAcctTo.getFileResource());
		entity.setTpAcctId(tpAcctId);
		
		Integer customerId = createCustomer();
		Integer accountId = createAccount(customerId);
		
		entity.setAcsAcctId(accountId);
		entity.setAcsCustId(customerId);
		
		Date createTime = new Date();
		
		entity.setUpdateOperatorName(operatorName);
		entity.setCreateOperatorName(operatorName);
		entity.setCreateTime(createTime);
		entity.setUpdateTime(createTime);
		
		misBankAcctsMapper.insert(entity);
		
		for(MisAccountOfferedBanks offerd : offerds) {
			offerd.setBankAcctRid(entity.getRid());
			offerd.setUpdateOperatorName(operatorName);
			offerd.setCreateOperatorName(operatorName);
			offerd.setCreateTime(createTime);
			offerd.setUpdateTime(createTime);
			misAccountOfferedBanksMapper.insertSelective(offerd);
		}
		
	}

	private Integer createAccount(Integer customerId) {
		AccountChangeService accountChangeService = ACServiceFactory.getInstance().createAccountChangeService();
		accountChangeService.openAccount(customerId, ACSConstants.BANK_ACCT_TYPE_ID);
		AcsAccount acsAccount = accountChangeService.getAccountInfo(customerId, ACSConstants.BANK_ACCT_TYPE_ID);
		if(acsAccount == null) {
			LOGGER.error("create account error: " + customerId + ", " + ACSConstants.BANK_ACCT_TYPE_ID);
			throw new MisBaseException(ErrorCode.ACS_CLIENT_ERR, "create account error.");
		}
		LOGGER.info("create account id: {}", acsAccount.getAccountId());
		return acsAccount.getAccountId();
	}

	private Integer createCustomer() {
		CustomerService customerService = ussFactory.createCustomerService();		
		Integer newCustomeId = customerService.reserveCustomerID();
		LOGGER.info("create customer id: {}", newCustomeId);
		return newCustomeId;
	}

	private Integer getTpAcctIdFromPss(Integer tpId, Map<Long, String> stringTypeParams, Map<Long, FileResource> fileResource) {
		ThirdPartyAccountRequest pssRequest = new ThirdPartyAccountRequest();
		
		pssRequest.setVendorId(tpId.longValue());
		pssRequest.setRemarks("-");
		pssRequest.setStringTypeParams(stringTypeParams);
		pssRequest.setFileTypeParams(fileResource);
		
		ThirdPartyAccountResponse response = pssClientService.doPostThirdPartyRequest(pssRequest);
		if (response == null) {
		  LOGGER.error("No response from PSS request.");
          throw new MisBaseException(ErrorCode.PSS_CLIENT_ERR, "No response from PSS request.");
        }
        if (!response.isStatus()) {
          String pssErrorMsg = "pss response: " + response.getErrorDesc();
          LOGGER.error(pssErrorMsg);
          throw new MisBaseException(ErrorCode.PSS_CLIENT_ERR, pssErrorMsg);
        }
		return response.getData().getAccount().getId().intValue();
	}

	private List<MisAccountOfferedBanks> convertToMisAccountOffered(SaveBankAcctTo saveBankAcctTo) {
		List<MisAccountOfferedBanks> result = Lists.newLinkedList();
		
		for(Offerd offed : saveBankAcctTo.getOfferds()) {
			MisAccountOfferedBanks record = new MisAccountOfferedBanks();
			record.setBankType(offed.getBankType());
			record.setBankFeeRate(offed.getBankFeeRate() == null ? BigDecimal.ZERO : offed.getBankFeeRate() );
			record.setBankMaxFee(offed.getBankMaxFee() == null ? BigDecimal.ZERO : offed.getBankMaxFee() );
			record.setBankMinFee(offed.getBankMinFee() == null ? BigDecimal.ZERO : offed.getBankMinFee() );
			record.setMerchantMaxAmount(offed.getMerchantMaxAmount() == null ? BigDecimal.ZERO : offed.getMerchantMaxAmount() );
			record.setMerchantMinAmount(offed.getMerchantMinAmount() == null ? BigDecimal.ZERO : offed.getMerchantMinAmount() );
			record.setMerchantRate(offed.getMerchantRate() == null ? BigDecimal.ZERO : offed.getMerchantRate() );
			record.setStatus(offed.getStatus());
            record.setTxMaxAmount(offed.getTxMaxAmount() == null ? BigDecimal.ZERO : offed.getTxMaxAmount() );
            record.setTxMinAmount(offed.getTxMinAmount() == null ? BigDecimal.ZERO : offed.getTxMinAmount() );
			result.add(record);
		}
		
		return result;
	}

	private MisBankAccts convertToMisBankAccts(SaveBankAcctTo saveBankAcctTo) {
		MisBankAccts result = new MisBankAccts();
		
		result.setAcctNumber(saveBankAcctTo.getAcctNumber());
		result.setBankId(saveBankAcctTo.getBankId());
		result.setBankName(saveBankAcctTo.getBankName());
		result.setStatus(saveBankAcctTo.getStatus());
		result.setVendorId(saveBankAcctTo.getVendorId());
		
		return result;
	}

	@Override
	public ViewBankAcctTo getBankAccount(Long acctId) {
		ViewBankAcctTo result = new ViewBankAcctTo();
		MisBankAccts bankAcct = misBankAcctsMapper.selectByPrimaryKey(acctId);
		
		if(bankAcct == null) {
			throw new MisBaseException(ErrorCode.DATA_NOT_FOUND, "no bank acct from id: " + acctId);
		}
		
		result.setBankId(bankAcct.getBankId());
		result.setBankName(bankAcct.getBankName());
		result.setStatus(bankAcct.getStatus());
		result.setAcctNumber(bankAcct.getAcctNumber());
		result.setRid(bankAcct.getRid());
		result.setTpAcctId(bankAcct.getTpAcctId());
		result.setVendorId(bankAcct.getVendorId());
		result.setUpdateTime(bankAcct.getUpdateTime());
		result.setUpdateUser(bankAcct.getUpdateOperatorName());
		
		if(bankAcct.getTpAcctId() != null) {
			ThirdPartyAccountResponse tp = pssClientService.getThirdPartyAccountInfo(bankAcct.getTpAcctId().longValue());
			if(tp!=null) {
				result.setTpInfo(tp.getData());
			}
		}
		
		List<MisAccountOfferedBanks> offerds = misAccountOfferedBanksMapper.findByAcctId(acctId,null);
		
		for(MisAccountOfferedBanks offerd : offerds) {
			ViewBankAcctTo.Offerd resultOfferd = new ViewBankAcctTo.Offerd();
			resultOfferd.setBankType(offerd.getBankType());
			resultOfferd.setBankFeeRate(offerd.getBankFeeRate());
			resultOfferd.setBankMaxFee(offerd.getBankMaxFee());
			resultOfferd.setBankMinFee(offerd.getBankMinFee());
			resultOfferd.setMerchantMaxAmount(offerd.getMerchantMaxAmount());
			resultOfferd.setMerchantMinAmount(offerd.getMerchantMinAmount());
			resultOfferd.setMerchantRate(offerd.getMerchantRate());
			resultOfferd.setStatus(offerd.getStatus());
            resultOfferd.setTxMaxAmount(offerd.getTxMaxAmount());
            resultOfferd.setTxMinAmount(offerd.getTxMinAmount());
			result.getOfferds().add(resultOfferd);
		}
		
		
		
		return result;
	}

	@Override
	public void updateBankAcct(SaveBankAcctTo saveBankAcctTo, String operatorName) {
		MisBankAccts bankAcct = misBankAcctsMapper.selectByPrimaryKey(saveBankAcctTo.getRid());
		
		if(bankAcct == null) {
			throw new MisBaseException(ErrorCode.DATA_NOT_FOUND, "no bank acct from id: " + saveBankAcctTo.getRid());
		}
		
		Date now = new Date();
		
		convertToMisBankAccts(bankAcct, saveBankAcctTo);
		
		// call pss update
		updateTpAcctIdFromPss(bankAcct.getTpAcctId(), saveBankAcctTo.getVendorId(), saveBankAcctTo.getParams(), saveBankAcctTo.getFileResource());
		
		bankAcct.setUpdateOperatorName(operatorName);
		bankAcct.setUpdateTime(now);
		
		misBankAcctsMapper.updateByPrimaryKey(bankAcct);
		
		
		List<MisAccountOfferedBanks> offerds = misAccountOfferedBanksMapper.findByAcctId(bankAcct.getRid(),null);
		
		List<MisAccountOfferedBanks> newOfferds = convertToMisAccountOffered(saveBankAcctTo);
		
		for(MisAccountOfferedBanks newOfferd : newOfferds) {
			MisAccountOfferedBanks offerd = popOldOfferd(offerds, newOfferd);
			convertToMisBankAcctsOffered(newOfferd, offerd);
			
			newOfferd.setUpdateOperatorName(operatorName);
			newOfferd.setUpdateTime(now);
			
			if(newOfferd.getRid() == null) {
				newOfferd.setCreateTime(now);
				newOfferd.setCreateOperatorName(operatorName);
				newOfferd.setBankAcctRid(bankAcct.getRid());
				
				misAccountOfferedBanksMapper.insert(newOfferd);
			} else {
				misAccountOfferedBanksMapper.updateByPrimaryKey(newOfferd);
			}
		}
		
	}

	private void updateTpAcctIdFromPss(Integer acctId, Integer tpId, Map<Long, String> stringTypeParams, Map<Long, FileResource> fileResource) {
		ThirdPartyAccountRequest pssRequest = new ThirdPartyAccountRequest();
		
		pssRequest.setAccountId(acctId.longValue());
		pssRequest.setVendorId(tpId.longValue());
		pssRequest.setRemarks("-");
		pssRequest.setStringTypeParams(stringTypeParams);
		pssRequest.setFileTypeParams(fileResource);
		
		ThirdPartyAccountResponse response = pssClientService.doUpdateThirdPartyRequest(pssRequest);
		if (response == null) {
		  LOGGER.error("No response from PSS request.");
          throw new MisBaseException(ErrorCode.PSS_CLIENT_ERR, "No response from PSS request.");
        }
        if (!response.isStatus()) {
          String pssErrorMsg = "pss response: " + response.getErrorDesc();
          LOGGER.error(pssErrorMsg);
          throw new MisBaseException(ErrorCode.PSS_CLIENT_ERR, pssErrorMsg);
        }
	}

	private void convertToMisBankAcctsOffered(MisAccountOfferedBanks newOfferd, MisAccountOfferedBanks offerd) {
		if(offerd == null) {
			return;
		}
        newOfferd.setBankAcctRid(offerd.getBankAcctRid());
        newOfferd.setRid(offerd.getRid());
		newOfferd.setBankType(offerd.getBankType());
        newOfferd.setCreateOperatorName(offerd.getCreateOperatorName());
        newOfferd.setCreateTime(offerd.getCreateTime());
	}

	private MisAccountOfferedBanks popOldOfferd(List<MisAccountOfferedBanks> offerds, MisAccountOfferedBanks newOfferd) {
		MisAccountOfferedBanks result = null;
		for(MisAccountOfferedBanks record : offerds) {
			if(newOfferd.getBankType().equals(record.getBankType())) {
				result = record;
				break;
			}
		}
		if(result != null) {
		    offerds.remove(result);
		}
		return result;
	}

	private void convertToMisBankAccts(MisBankAccts bankAcct, SaveBankAcctTo saveBankAcctTo) {
		bankAcct.setBankId(saveBankAcctTo.getBankId());
		bankAcct.setBankName(saveBankAcctTo.getBankName());
		bankAcct.setStatus(saveBankAcctTo.getStatus());
		bankAcct.setAcctNumber(saveBankAcctTo.getAcctNumber());
	}

}
