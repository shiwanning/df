package com.tcg.mis.service.recharge.impl;

import com.google.common.collect.Lists;
import com.tcg.mis.client.AcsClientService;
import com.tcg.mis.common.constant.ACSConstants;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.constant.RechareType;
import com.tcg.mis.common.constant.TransactionStatus;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.mapper.AcsAccountMapper;
import com.tcg.mis.mapper.MerchantMapper;
import com.tcg.mis.mapper.MisAccountOfferedBanksMapper;
import com.tcg.mis.mapper.MisBankAcctsMapper;
import com.tcg.mis.mapper.MisRechargeDetailMapper;
import com.tcg.mis.mapper.MisRechargeTransactionMapper;
import com.tcg.mis.model.MisAccountOfferedBanks;
import com.tcg.mis.model.MisBankAccts;
import com.tcg.mis.model.MisRechargeDetail;
import com.tcg.mis.model.MisRechargeTransaction;
import com.tcg.mis.model.vo.AcsAccount;
import com.tcg.mis.model.vo.LobbyAccountType;
import com.tcg.mis.model.vo.Merchant;
import com.tcg.mis.service.recharge.RechargeTransactionService;
import com.tcg.mis.to.condition.RechargeTransactionCondition;
import com.tcg.mis.to.request.TransactionApproveTo;
import com.tcg.mis.to.response.MisRechargeTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RechargeTransactionServiceImpl implements RechargeTransactionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RechargeTransactionServiceImpl.class);

	@Autowired
	private MisRechargeTransactionMapper misRechargeTransactionMapper;

	@Autowired
	private MisRechargeDetailMapper misRechargeDetailMapper;

	@Autowired
	private MisAccountOfferedBanksMapper misAccountOfferedBanksMapper;
	
	@Autowired
	private MisBankAcctsMapper misBankAcctsMapper;
	
	@Autowired
	private AcsClientService acsClientService;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private AcsAccountMapper acsAccountMapper;

	@Override
	public PageResponse<MisRechargeTo, MisRechargeTo> getMisRechargeTransactionList(
			RechargeTransactionCondition condition) {

		PaginationAndOrderVO page = condition.generatePaginationAndOrderVO();

		List<MisRechargeTo> list = misRechargeTransactionMapper.findByCondition(condition, page);

        MisRechargeTo footer = misRechargeTransactionMapper.sumByCondition(condition);

		return new PageResponse<>(list, page, footer);
	}

	@Override
	public MisRechargeTransaction getTransaction(String orderNo) {
		return misRechargeTransactionMapper.findByOrderNo(orderNo);
	}

	@Override
	public void manual(TransactionApproveTo transactionApproveTo, String updateOperatorName) {

		MisRechargeTransaction transaction = getTransaction(transactionApproveTo.getOrderNo());
		if(transaction == null || !TransactionStatus.canManualStatus(transaction.getStatus().intValue())) {
			throw new MisBaseException(ErrorCode.STATUS_ERR, "状态错误，无法手工");
		}


        MisAccountOfferedBanks rechargeOfferedBanks =  misAccountOfferedBanksMapper.findByOrderNo(transactionApproveTo.getOrderNo());

        if(rechargeOfferedBanks.getTxMaxAmount().intValue() > 0 && transactionApproveTo.getAmount().compareTo(rechargeOfferedBanks.getTxMaxAmount()) == 1){
            throw new MisBaseException(ErrorCode.OVER_TX_MAX_AMOUNT, "超出单笔最大限额");
        }

        if(rechargeOfferedBanks.getTxMinAmount().intValue() > 0 && transactionApproveTo.getAmount().compareTo(rechargeOfferedBanks.getTxMinAmount()) == -1){
            throw new MisBaseException(ErrorCode.LOWER_TX_MIN_AMOUNT, "低于单笔最低限额");
        }

		if(transactionApproveTo.getApprove()) {
			this.rechargeSuccessProcess(transactionApproveTo.getOrderNo(), transactionApproveTo.getAmount(), transactionApproveTo.getRemark(), updateOperatorName, true);
		} else {
			this.rejectRecharge(transactionApproveTo.getOrderNo(), transactionApproveTo.getRemark(), updateOperatorName);
		}
	}

	private void rejectRecharge(String orderNo, String remark, String updateOperatorName) {
		Date now = new Date();

		MisRechargeTransaction transaction = getTransaction(orderNo);

		transaction.setStatus(TransactionStatus.MANUAL_REJECT);
		transaction.setRemark(remark);
		transaction.setUpdateOperatorName(updateOperatorName);
		transaction.setUpdateTime(now);

		misRechargeTransactionMapper.updateByPrimaryKey(transaction);
	}

	/**
	 * 充值成功处理
	 */
	@Override
	public void rechargeSuccessProcess(String orderNo, BigDecimal actualAmount, String remark, String updateOperatorName, Boolean isManual) {

		MisRechargeDetail detail = misRechargeDetailMapper.findByOrderNo(orderNo);

		if(detail == null) {
			throw new MisBaseException(ErrorCode.DATA_NOT_FOUND, "No detail data for orderNo: " + orderNo);
		}

        //更新 transaction 主表
        updateTransactionSuccess(detail, actualAmount, updateOperatorName, remark, isManual);

		//主钱包充钱
        Boolean isMainSuccess = doRechargeMainWallet(detail, actualAmount);

        if(isMainSuccess && detail.getRechargeType().intValue() == RechareType.WALLET_MAIN){
            doBankAcctBilling(detail, actualAmount);
        }

        if(isMainSuccess && detail.getRechargeType().intValue() == RechareType.WALLET_CASH_PLEDGE){
            if(doRechargeCashPledgeWallet(detail, actualAmount)){
                doBankAcctBilling(detail, actualAmount);
            }
        }
	}

	private Integer getBankAccountId(BigDecimal bankAcctRid) {
		MisBankAccts bank = bankAcctRid == null ? null : misBankAcctsMapper.selectByPrimaryKey(bankAcctRid.longValue());
		return bank == null ? null : bank.getAcsAcctId() == null ? null : bank.getAcsAcctId().intValue();
	}

	private void updateTransactionSuccess(MisRechargeDetail detail, BigDecimal actualAmount, String updateOperatorName, String remark, Boolean isManual) {
		Date now = new Date();

		MisRechargeTransaction transaction = getTransaction(detail.getOrderNo());

		transaction.setStatus(isManual ? TransactionStatus.MANUAL_SUCCESS : TransactionStatus.CALLBACK_SUCCESS);
		transaction.setActualAmount(actualAmount);
		transaction.setRemark(remark);
		transaction.setUpdateOperatorName(updateOperatorName);
		transaction.setUpdateTime(now);
		transaction.setGetTime(now);

		misRechargeTransactionMapper.updateByPrimaryKey(transaction);
	}

    private Boolean doRechargeMainWallet(MisRechargeDetail detail, BigDecimal amount){
        // 主錢包 加钱
        Integer txType = ACSConstants.WALLET_C_DEPOSIT_MAIN;
        AcsAccount mainWallet = merchantMapper.findAcsAccount(detail.getMerchantCode(), ACSConstants.ACCT_TYPE_MAIN);

        Integer accountId = mainWallet.getAccountId().intValue();

        boolean acsTransactionSuccess = acsClientService.lodgeCreditTransaction(accountId , amount,
                                                                                txType, ACSConstants.getTxCode(txType), detail.getOrderNo());
        if(!acsTransactionSuccess) {
            throw new MisBaseException(ErrorCode.ACS_CLIENT_ERR, "acs transaction error.");
        }
        //主錢包扣 手續費
        if(detail.getRechargeCharge()!= null && detail.getRechargeCharge().signum() == 1) {
            txType = ACSConstants.WALLET_D_DEPOSIT_MAIN_CHARGE;
            acsClientService.lodgeDebitTransaction(accountId, detail.getRechargeCharge(), txType, ACSConstants.getTxCode(txType), detail.getOrderNo());
        }
        return true;
    }


    private void doBankAcctBilling(MisRechargeDetail detail, BigDecimal amount){
        Integer bankAcctId = getBankAccountId(detail.getBankAcctRid());

        if(bankAcctId == null) {
            LOGGER.error("bank account not found for rid: " + detail.getBankAcctRid());
            return;
        }

        acsClientService.lodgeCreditTransaction(bankAcctId, amount, ACSConstants.TCG_C_DEPOSIT, ACSConstants.getTxCode(ACSConstants.TCG_C_DEPOSIT), detail.getOrderNo());
        if(detail.getRechargeFee() != null && detail.getRechargeFee().signum() == 1) {
            acsClientService.lodgeDebitTransaction(bankAcctId, detail.getRechargeFee(), ACSConstants.TCG_D_DEPOSIT_CHARGE, ACSConstants.getTxCode(ACSConstants.TCG_D_DEPOSIT_CHARGE), detail.getOrderNo());
        }
    }



    private Boolean doRechargeCashPledgeWallet(MisRechargeDetail detail, BigDecimal amount){

	    //主錢包扣錢
        Integer txType = ACSConstants.WALLET_D_DEPOSIT_MAIN;
        AcsAccount mainWallet = merchantMapper.findAcsAccount(detail.getMerchantCode(), ACSConstants.ACCT_TYPE_MAIN);
        acsClientService.lodgeDebitTransaction(mainWallet.getAccountId().intValue() , amount,
                                               txType, ACSConstants.getTxCode(txType), detail.getOrderNo());

        // 押金錢包 加钱
        txType = ACSConstants.WALLET_C_DEPOSIT_PLEDGE;
        boolean acsTransactionSuccess = acsClientService.lodgeCreditTransaction(detail.getAccountId().intValue() , amount,
                                                                                txType, ACSConstants.getTxCode(txType), detail.getOrderNo());
        if(!acsTransactionSuccess) {
            throw new MisBaseException(ErrorCode.ACS_CLIENT_ERR, "acs transaction error.");
        }

        AcsAccount pledgeAccount = merchantMapper.findAcsAccount(detail.getMerchantCode(), ACSConstants.ACCT_TYPE_PLEDGE);

        BigDecimal leverMultiplier = getMerchant(detail.getMerchantCode()).getLeverMultiplier();

        //當前信用額度
        BigDecimal totalCredit = leverMultiplier.multiply(pledgeAccount.getBalance());
        //get用户原有总信用额度
        BigDecimal originalTotalCreditLimit = acsClientService.getTotalCreditLimit(pledgeAccount.getCustomerId().intValue());
        //庚新用户总信用额度
        acsClientService.addorupdateTotalCreditLimit(pledgeAccount.getCustomerId().intValue(),totalCredit);

        List<AcsAccount> acsAccounts = acsAccountMapper.findAcsAccounts(pledgeAccount.getCustomerId());

        List productWalletAccountTypes = Lists.newArrayList(getWalletMap().values());

        for(AcsAccount acsAccount : acsAccounts){
            if(!productWalletAccountTypes.contains(acsAccount.getAccountTypeId())){
                continue;
            }
            if(acsAccount.getCreditLimit().signum() == 0 || acsAccount.getCreditLimit().compareTo(originalTotalCreditLimit) == 0){
                //更新信用额度
                acsClientService.updateCreditLimit(acsAccount.getAccountId().intValue(), totalCredit);
            }
        }
        return true;
    }

    private Map<String,Integer> getWalletMap(){
        Map<String,Integer> map = new HashMap<>();
        List<LobbyAccountType> list = merchantMapper.findLobbyAccountTypes();
        for(LobbyAccountType wallet: list){
            map.put(wallet.getAccountTypeName(),wallet.getAccountTypeId());
        }
        return map;
    }

    @Override
    public List<LobbyAccountType> getWalletList(){
        List<LobbyAccountType> list = merchantMapper.findLobbyAccountTypes();
        return list;
    }

    @Override
    public Merchant getMerchant(String merchantCode){
	    return merchantMapper.findMerchant(merchantCode);
    }


}
