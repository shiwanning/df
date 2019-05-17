package com.tcg.mis.service.withdraw.impl;

import com.tcg.mis.client.AcsClientService;
import com.tcg.mis.client.PssClientService;
import com.tcg.mis.common.constant.ACSConstants;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.constant.TransactionStatus;
import com.tcg.mis.common.constant.WithdrawType;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.response.BaseResponseT;
import com.tcg.mis.common.util.JsonUtils;
import com.tcg.mis.common.util.OrderNoUtil;
import com.tcg.mis.mapper.AcsAccountMapper;
import com.tcg.mis.mapper.MisAccountOfferedBanksMapper;
import com.tcg.mis.mapper.MisBankAcctsMapper;
import com.tcg.mis.mapper.MisWithdrawDetailMapper;
import com.tcg.mis.mapper.MisWithdrawTransactionCallbackMapper;
import com.tcg.mis.mapper.MisWithdrawTransactionMapper;
import com.tcg.mis.mapper.MisWithdrawTransactionRequestMapper;
import com.tcg.mis.model.MisAccountOfferedBanks;
import com.tcg.mis.model.MisBankAccts;
import com.tcg.mis.model.MisWithdrawDetail;
import com.tcg.mis.model.MisWithdrawTransaction;
import com.tcg.mis.model.MisWithdrawTransactionCallback;
import com.tcg.mis.model.MisWithdrawTransactionRequest;
import com.tcg.mis.model.vo.AcsAccount;
import com.tcg.mis.model.vo.BankAccountInfo;
import com.tcg.mis.service.withdraw.WithdrawService;
import com.tcg.mis.to.condition.AcsAccountCondition;
import com.tcg.mis.to.condition.BankAccountCondition;
import com.tcg.mis.to.request.TPSWithdrawRequest;
import com.tcg.mis.to.request.TPSWithdrawResult;
import com.tcg.mis.to.request.TransactionApproveTo;
import com.tcg.mis.to.response.PSSBaseResponse;
import com.tcg.mis.to.response.WithdrawTpTo;
import com.yx.acs.client.ACServiceFactory;
import com.yx.acs.service.AccountChangeService;
import com.yx.commons.constant.AcsDebitTypeConstant;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class WithdrawServiceImpl implements WithdrawService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawServiceImpl.class);

    @Autowired
    private MisBankAcctsMapper misBankAcctsMapper;
    @Autowired
    private MisWithdrawDetailMapper detailMapper;
    @Autowired
    private MisWithdrawTransactionMapper transactionMapper;
    @Autowired
    private MisWithdrawTransactionRequestMapper transactionReqMapper;
    @Autowired
    private MisWithdrawTransactionCallbackMapper transactionCallbackMapper;
    @Autowired
    private AcsAccountMapper acsAccountMapper;
    @Autowired
    private PssClientService pssClientService;
    @Autowired
    private AcsClientService acsClientService;
    @Autowired
    private MisAccountOfferedBanksMapper misAccountOfferedBanksMapper;

    @Override
    public BaseResponseT<List<BankAccountInfo>> getWithdrawAccounts() {

        BankAccountCondition condition = new BankAccountCondition();
        List<BankAccountInfo> list = misBankAcctsMapper.findBankAccountList(condition);
        return new BaseResponseT<>(list);
    }


    @Override
    public Boolean doTpWithdraw(WithdrawTpTo to, String operator){

        MisBankAccts withdrawAcct = misBankAcctsMapper.selectByPrimaryKey(to.getBankAccountId().longValue());

        MisAccountOfferedBanks withdrawOfferBank = misAccountOfferedBanksMapper.findByAcctId(to.getBankAccountId(), "WD").get(0);

        if(withdrawOfferBank.getTxMaxAmount().intValue() > 0 && to.getAmount().compareTo(withdrawOfferBank.getTxMaxAmount()) == 1){
            throw new MisBaseException(ErrorCode.OVER_TX_MAX_AMOUNT, "超出单笔最大限额");
        }
        if(withdrawOfferBank.getTxMinAmount().intValue() > 0 && to.getAmount().compareTo(withdrawOfferBank.getTxMinAmount()) == -1){
            throw new MisBaseException(ErrorCode.LOWER_TX_MIN_AMOUNT, "低于单笔最低限额");
        }

        String orderNo = OrderNoUtil.getOrderNo(OrderNoUtil.OrderTypeEnum.TP_WITHDRAW);

        createWithdrawDetail(to, orderNo, operator);

        createWithdrawTransaction(to, orderNo, operator, withdrawAcct);

        TPSWithdrawRequest request = getWithdrawRequest(to, orderNo, withdrawAcct);

        createWithdrawTransactionRequest(pssClientService.getWithdrawUrl(), JsonUtils.toJson(request), orderNo, operator);
        PSSBaseResponse response = null;
        try{
            response = pssClientService.doWithdraw(request);
            updateWithdrawRequest(orderNo, JsonUtils.toJson(response));
            return response.isStatus();
        }catch (Exception e){
            updateWithdrawRequest(orderNo, "Exception:"+e.getMessage());
            doWithdrawTransactionFail(orderNo, TransactionStatus.REQUEST_FAIL);
            LOGGER.error("doTpWithdraw:",e);
            return false;
        }
    }

    @Override
    public void doTpWithdrawCallback(TPSWithdrawResult result){
        createWithdrawTransactionCallback(result.getId(), JsonUtils.toJson(result));
        boolean isSuccess = StringUtils.equalsIgnoreCase("success",result.getStatus());

        MisWithdrawTransaction transaction = transactionMapper.findByOrderNo(result.getId());
        if(transaction.getStatus().intValue() != TransactionStatus.REQUEST ){
        	LOGGER.info("orderNo: {} already have request status, abort acs transaction. ", result.getId());
            return;
        }

        if(!isSuccess){
        	LOGGER.info("orderNo: {} is fail on callback, response: {}", result.getId(), JsonUtils.toJson(result));
            transaction.setStatus(new BigDecimal(TransactionStatus.CALLBACK_FAIL));
            transaction.setUpdateTime(new Date());
            transaction.setUpdateOperatorName("PSS");
            transactionMapper.updateByPrimaryKeySelective(transaction);
            return;
        }
        cancelBooked(transaction.getBookTxId());
        if(doAcsDebitTransaction(result.getId(), result.getAmount())){
            updateWithdrawTransaction(result.getId(), result.getAmount(), TransactionStatus.CALLBACK_SUCCESS,null,"PSS");
        }
    }

    @Override
    public void doTpWithdrawApprove(TransactionApproveTo transactionApproveTo, String operator){
    	MisWithdrawTransaction transaction = transactionMapper.findByOrderNo(transactionApproveTo.getOrderNo());
    	
    	if(transaction == null || !TransactionStatus.canManualStatus(transaction.getStatus().intValue())) {
    		throw new MisBaseException(ErrorCode.STATUS_ERR, "状态错误，无法手工");
    	}
    	
        if(transactionApproveTo.getApprove()){
        	cancelBooked(transaction.getBookTxId());
            if(doAcsDebitTransaction(transactionApproveTo.getOrderNo(), transaction.getTxAmount())){
                updateWithdrawTransaction(transactionApproveTo.getOrderNo(), transaction.getTxAmount(), TransactionStatus.MANUAL_SUCCESS,transactionApproveTo.getRemark(),operator);
            }
        }else{
            transaction.setStatus(new BigDecimal(TransactionStatus.MANUAL_REJECT));
            transaction.setUpdateTime(new Date());
            transaction.setUpdateOperatorName(operator);
            transactionMapper.updateByPrimaryKeySelective(transaction);
            //取消凍結
            cancelBooked(transaction.getBookTxId());
        }
    }

    private Boolean doAcsDebitTransaction(String orderNo, BigDecimal amount){
        Integer accountId = detailMapper.findWithdrawBankAccountAcsAcctId(orderNo);
        BigDecimal fee = detailMapper.findWithdrawCharge(orderNo);
        if(acsClientService.lodgeDebitTransaction(accountId, amount, ACSConstants.TCG_D_WITHDRAW,
                                                  ACSConstants.getTxCode(ACSConstants.TCG_D_WITHDRAW), orderNo)){
            if(fee.signum() > 0){
                acsClientService.lodgeDebitTransaction(accountId, fee, ACSConstants.TCG_D_WITHDRAW_FEE,
                                                       ACSConstants.getTxCode(ACSConstants.TCG_D_WITHDRAW_FEE), orderNo);
            }
            return true;
        }
        return false;
    }

    private TPSWithdrawRequest getWithdrawRequest(WithdrawTpTo to, String orderNo, MisBankAccts withdrawAcct){

        TPSWithdrawRequest withdrawRequest = new TPSWithdrawRequest();
        withdrawRequest.setWithdrawId(orderNo);
        withdrawRequest.setAmount(to.getAmount());
        withdrawRequest.setIpAddress("127.0.0.1");
        withdrawRequest.setAcctId(withdrawAcct.getTpAcctId().toString());
        withdrawRequest.setBankCode(to.getPssPgCode());
        withdrawRequest.setCustomerId(to.getDepositAccountName());
        withdrawRequest.setCustomerAcctName(to.getDepositAccountName());
        withdrawRequest.setCustomerAcctNum(to.getDepositAccount());
        withdrawRequest.setBankProvince(StringUtils.isNotBlank(to.getDepositStat())?to.getDepositStat():StringUtils.EMPTY);
        withdrawRequest.setBankCity(to.getDepositBankCity());
        withdrawRequest.setBankBranch(StringUtils.isNotBlank(to.getDepositBranch())?to.getDepositBranch():StringUtils.EMPTY);
        return withdrawRequest;
    }

    private void updateWithdrawRequest(String orderNo, String response){
        MisWithdrawTransactionRequest request = transactionReqMapper.findByOrderNo(orderNo);
        request.setResponse(response);
        request.setUpdateTime(new Date());
        transactionReqMapper.updateByPrimaryKeySelective(request);
    }

    private void updateWithdrawTransaction(String orderNo,BigDecimal resultAmount, Integer status, String remark,String operator){
        MisWithdrawTransaction transaction = transactionMapper.findByOrderNo(orderNo);
        transaction.setStatus(new BigDecimal(status));
        transaction.setRemark(remark);
        transaction.setActualAmount(resultAmount);
        transaction.setGetTime(new Date());
        transaction.setUpdateTime(new Date());
        transaction.setUpdateOperatorName(operator);
        transactionMapper.updateByPrimaryKeySelective(transaction);
    }

    private void doWithdrawTransactionFail(String orderNo, Integer status){
        MisWithdrawTransaction transaction = transactionMapper.findByOrderNo(orderNo);
        transaction.setStatus(new BigDecimal(status));
        transaction.setUpdateTime(new Date());
        transactionMapper.updateByPrimaryKeySelective(transaction);
    }

    private void createWithdrawTransaction(WithdrawTpTo to,String orderNo,String operator,MisBankAccts withdrawAcct){

        AcsAccountCondition condition = new AcsAccountCondition();
        condition.setCustomerId(withdrawAcct.getAcsCustId().longValue());
        condition.setAccountId(withdrawAcct.getAcsAcctId().longValue());
        AcsAccount acsBankAccount = acsAccountMapper.findAcsAccount(condition).get(0);
        BigDecimal fee = getWithdrawFee(to.getBankAccountId(), to.getAmount());
        
        //冻结金额
        Long bookTxId = getAcsChangeService().bookTransaction(acsBankAccount.getAccountId().intValue(), to.getAmount().add(fee), "商户提现金额(含手续费)").getTxId();

        MisWithdrawTransaction transaction = new MisWithdrawTransaction();
        transaction.setOrderNo(orderNo);
        transaction.setWithdrawBankId(new BigDecimal(withdrawAcct.getBankId()));
        transaction.setWithdrawBankAccount(withdrawAcct.getAcctNumber());
        transaction.setWithdrawBankName(withdrawAcct.getBankName());
        transaction.setWithdrawAccountAmount(acsBankAccount.getBalance());
        transaction.setDepositBankAccount(to.getDepositAccount());
        transaction.setDepositBankId(new BigDecimal(to.getDepositBankId()));
        transaction.setDepositBankName(to.getDepositBankName());
        transaction.setDepositBankCity(to.getDepositBankCity());
        transaction.setDepositBankStat(to.getDepositStat());
        transaction.setDepositBankBranch(to.getDepositBranch());
        transaction.setDepositAccountName(to.getDepositAccountName());
        transaction.setStatus(new BigDecimal(TransactionStatus.REQUEST));
        transaction.setTxAmount(to.getAmount());
        transaction.setBookTxId(bookTxId);
        transaction.setCreateOperatorName(operator);
        transaction.setUpdateOperatorName(operator);
        transactionMapper.insertSelective(transaction);
    }

    private void createWithdrawTransactionRequest(String url, String requestPar, String orderNo, String operator){
        MisWithdrawTransactionRequest request = new MisWithdrawTransactionRequest();
        request.setUrl(url);
        request.setRequest(requestPar);
        request.setOrderNo(orderNo);
        request.setCreateOperatorName(operator);
        request.setUpdateOperatorName(operator);
        transactionReqMapper.insertSelective(request);
    }

    private void createWithdrawDetail(WithdrawTpTo to,String orderNo,String operator){

        MisWithdrawDetail detail = new MisWithdrawDetail();
        detail.setOrderNo(orderNo);
        detail.setBankAcctRid(new BigDecimal(to.getBankAccountId()));
        detail.setTxType(new BigDecimal(WithdrawType.TX_TP));
        detail.setTxAmount(to.getAmount());
        detail.setWithdrawCharge(getWithdrawFee(to.getBankAccountId(), to.getAmount()));
        detail.setRemark(to.getRemark());
        detail.setCreateOperatorName(operator);
        detail.setUpdateOperatorName(operator);
        detailMapper.insertSelective(detail);
    }

    private void createWithdrawTransactionCallback(String orderNo, String response){
        MisWithdrawTransactionCallback callback = new MisWithdrawTransactionCallback();
        callback.setResponse(response);
        callback.setOrderNo(orderNo);
        callback.setCreateOperatorName("PSS");
        callback.setUpdateOperatorName("PSS");
        transactionCallbackMapper.insertSelective(callback);
    }

    private BigDecimal getWithdrawFee(Long bankAccountId, BigDecimal txAmount){

        MisAccountOfferedBanks withdrawOfferBank = misAccountOfferedBanksMapper.findByAcctId(bankAccountId, "WD").get(0);
        if(withdrawOfferBank.getBankFeeRate() == null || withdrawOfferBank.getBankFeeRate().signum() <= 0){
            return new BigDecimal(0);
        }
        BigDecimal fee = txAmount.multiply(withdrawOfferBank.getBankFeeRate()).divide(new BigDecimal(100)).setScale(2, RoundingMode.DOWN);
        //小于最低费用取最低费用
        if(withdrawOfferBank.getBankMinFee() != null && withdrawOfferBank.getBankMinFee().signum() > 0
           && withdrawOfferBank.getBankMinFee().compareTo(fee) > 0){
            return withdrawOfferBank.getBankMinFee();
        }

        //高于最高费用取最高费用
        if(withdrawOfferBank.getBankMaxFee() != null && withdrawOfferBank.getBankMaxFee().signum() > 0
           && withdrawOfferBank.getBankMaxFee().compareTo(fee) < 0){
            return withdrawOfferBank.getBankMaxFee();
        }
        return fee;
    }

    private AccountChangeService getAcsChangeService(){
        return ACServiceFactory.getInstance().createAccountChangeService();
    }

    @Override
    public void confirmBooked(Long transactionId,Integer txTypeId, AcsDebitTypeConstant debitType) {
        getAcsChangeService().confirmBookedTransaction(transactionId, txTypeId, debitType);
    }
    
    private void cancelBooked(Long transactionId) {
        getAcsChangeService().cancelBookedTransaction(transactionId, "解冻");
    }
}

