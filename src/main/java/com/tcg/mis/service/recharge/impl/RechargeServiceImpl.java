package com.tcg.mis.service.recharge.impl;

import com.tcg.mis.client.AcsClientService;
import com.tcg.mis.client.PssClientService;
import com.tcg.mis.common.constant.ACSConstants;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.constant.RechareType;
import com.tcg.mis.common.constant.TpBankType;
import com.tcg.mis.common.constant.TransactionStatus;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.response.BaseResponseT;
import com.tcg.mis.common.util.JsonUtils;
import com.tcg.mis.common.util.OrderNoUtil;
import com.tcg.mis.common.util.Security;
import com.tcg.mis.mapper.AcsAccountMapper;
import com.tcg.mis.mapper.MerchantMapper;
import com.tcg.mis.mapper.MisAccountOfferedBanksMapper;
import com.tcg.mis.mapper.MisBankAcctsMapper;
import com.tcg.mis.mapper.MisRechargeDetailMapper;
import com.tcg.mis.mapper.MisRechargeTransactionCallbackMapper;
import com.tcg.mis.mapper.MisRechargeTransactionMapper;
import com.tcg.mis.mapper.MisRechargeTransactionRequestMapper;
import com.tcg.mis.model.MisAccountOfferedBanks;
import com.tcg.mis.model.MisBankAccts;
import com.tcg.mis.model.MisRechargeDetail;
import com.tcg.mis.model.MisRechargeTransaction;
import com.tcg.mis.model.MisRechargeTransactionCallback;
import com.tcg.mis.model.MisRechargeTransactionRequest;
import com.tcg.mis.model.vo.AcsAccount;
import com.tcg.mis.model.vo.Merchant;
import com.tcg.mis.service.bank.BankAccountService;
import com.tcg.mis.service.recharge.RechargeService;
import com.tcg.mis.service.recharge.RechargeTransactionService;
import com.tcg.mis.to.condition.AcsAccountCondition;
import com.tcg.mis.to.request.PssMtDepositTo;
import com.tcg.mis.to.request.PssPgDepositTo;
import com.tcg.mis.to.request.TPSRechargeResult;
import com.tcg.mis.to.response.BanksTo;
import com.tcg.mis.to.response.PgBanksTo;
import com.tcg.mis.to.response.TPSRawBankList;
import com.tcg.mis.to.response.TpMtTo;
import com.tcg.mis.to.response.TpPgTo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class RechargeServiceImpl implements RechargeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RechargeServiceImpl.class);

    @Autowired
    private MisBankAcctsMapper misBankAcctsMapper;
    @Autowired
    private MerchantMapper merchantMapper;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private MisAccountOfferedBanksMapper misAccountOfferedBanksMapper;
    @Autowired
    private MisRechargeDetailMapper misRechargeDetailMapper;
    @Autowired
    private MisRechargeTransactionMapper misRechargeTransactionMapper;
    @Autowired
    private AcsAccountMapper acsAccountMapper;
    @Autowired
    private PssClientService pssClientService;
    @Autowired
    private MisRechargeTransactionRequestMapper transactionReqMapper;
    @Autowired
    private MisRechargeTransactionCallbackMapper transactionCallbackMapper;
    @Autowired
    private RechargeTransactionService rechargeTransactionService;
    @Autowired
    private AcsClientService acsClientService;

    @Override
    public BaseResponseT<List<String>> getPaymentTypes() {
        List<String> list = misAccountOfferedBanksMapper.findAllPaymentTypes();
        return new BaseResponseT<>(list);
    }

    @Override
    public BaseResponseT<PgBanksTo> getPgBanks() {
        List<MisBankAccts> accs = misBankAcctsMapper.findAccsByPaymentType(TpBankType.PG.toString());
        if(accs == null || accs.isEmpty()){
            return new BaseResponseT(ErrorCode.BANK_ACCT_CLOSED);
        }
        Random random = new Random();
        MisBankAccts acct = accs.get(random.nextInt(accs.size()));
        MisAccountOfferedBanks offeredBanks = misAccountOfferedBanksMapper.findByAcctId(acct.getRid(),TpBankType.PG.toString()).get(0);

        TPSRawBankList tpBankList =pssClientService.getThirdPartyBankList(acct.getTpAcctId());
        PgBanksTo pgBanksTo = new PgBanksTo();
        if(tpBankList.isStatus()){
            pgBanksTo.setTxMaxAmount(offeredBanks.getTxMaxAmount());
            pgBanksTo.setTxMinAmount(offeredBanks.getTxMinAmount());
            pgBanksTo.setBankAcctRid(acct.getRid());
            pgBanksTo.setData(tpBankList.getData());
        }

        return new BaseResponseT<>(pgBanksTo);
    }

    @Override
    public BaseResponseT<BanksTo> getMtBanks() {
        List<MisBankAccts> accs = misBankAcctsMapper.findAccsByPaymentType(TpBankType.MT.toString());
        if(accs == null || accs.isEmpty()){
            return new BaseResponseT(ErrorCode.BANK_ACCT_CLOSED);
        }
        Random random = new Random();
        MisBankAccts acct = accs.get(random.nextInt(accs.size()));
        MisAccountOfferedBanks offeredBanks = misAccountOfferedBanksMapper.findByAcctId(acct.getRid(),TpBankType.MT.toString()).get(0);

        BanksTo banksTo = new BanksTo();
        banksTo.setTxMaxAmount(offeredBanks.getTxMaxAmount());
        banksTo.setTxMinAmount(offeredBanks.getTxMinAmount());
        banksTo.setBankAcctRid(acct.getRid());
        return new BaseResponseT<>(banksTo);
    }


    @Override
    public BaseResponseT<TpMtTo> doMtRecharge(String merchantCode, Integer bankAcctId, Integer rechargeType, BigDecimal amount,String operator){

        Integer accountType = rechargeType == RechareType.WALLET_MAIN ? ACSConstants.ACCT_TYPE_MAIN : ACSConstants.ACCT_TYPE_PLEDGE;

        MisBankAccts rechargeBankAcct =  misBankAcctsMapper.selectByPrimaryKey(Long.parseLong(bankAcctId.toString()));

        MisAccountOfferedBanks rechargeOfferedBanks =  misAccountOfferedBanksMapper.findByAcctId(rechargeBankAcct.getRid(),TpBankType.MT.toString()).get(0);

        validateTxAmount(rechargeOfferedBanks.getTxMaxAmount(), rechargeOfferedBanks.getTxMinAmount(), amount);

        AcsAccount merchantAccount = getDepositAccount(merchantCode, accountType);

        String orderNo = OrderNoUtil.getOrderNo(OrderNoUtil.OrderTypeEnum.MT_RECHARGE);

        //附言
        String message = generateNote(5);

        PssMtDepositTo request = getMtDepositRequest(rechargeBankAcct.getTpAcctId(), orderNo, message, merchantCode, amount);

        //新增充值明细
        createRechargeDetail(orderNo, merchantCode, rechargeBankAcct, rechargeOfferedBanks,  merchantAccount, rechargeType, null, amount,operator);
        //新增充值交易
        createRechargeTransaction(orderNo, rechargeBankAcct, amount,message,operator);
        //新增三方request
        createRechargeTransactionRequest(pssClientService.getMtDepositUrl(), JsonUtils.toJson(request), orderNo, operator);
        TpMtTo to = new TpMtTo();
        try {
            Map<String, Object> response = pssClientService.getThirdPartyDeposit(pssClientService.getMtDepositUrl(),request);
            updateRechargeRequest(orderNo, JsonUtils.toJson(response));

            if((Boolean)response.get("status")){

                Map<String,Object> payeeCardInfo = (Map<String, Object>) response.get("data");
                to.setAmount(new BigDecimal(payeeCardInfo.get("amount").toString()));
                to.setBankRefNo(payeeCardInfo.get("bankRefNo").toString());

                Map<String,String> payeeBankInfo = (Map<String, String>) payeeCardInfo.get("payeeBankInfo");
                to.setAccountName(payeeBankInfo.get("accountName"));
                to.setAccountNumber(payeeBankInfo.get("accountNumber"));
                to.setBankCode(payeeBankInfo.get("bankCode"));
                to.setBranch(payeeBankInfo.get("branch"));
                to.setCity(payeeBankInfo.get("city"));
                to.setProvince(payeeBankInfo.get("province"));
                to.setState(payeeBankInfo.get("state"));
                to.setEmail(payeeBankInfo.get("email"));
            }else{
                throw new MisBaseException(ErrorCode.PSS_CLIENT_ERR, "pss_client_err");
            }

        }catch (Exception e){
            LOGGER.error("doTpWithdraw:",e);
            throw new MisBaseException(ErrorCode.PSS_CLIENT_ERR, e);
        }
        return new BaseResponseT(to);
    }


    private void validateTxAmount(BigDecimal txMaxAmount, BigDecimal txMinAmount, BigDecimal amount){

        if(txMaxAmount.intValue() > 0 && amount.compareTo(txMaxAmount) == 1){
            throw new MisBaseException(ErrorCode.OVER_TX_MAX_AMOUNT, "超出单笔最大限额");
        }

        if(txMinAmount.intValue() > 0 && amount.compareTo(txMinAmount) == -1){
            throw new MisBaseException(ErrorCode.LOWER_TX_MIN_AMOUNT, "低于单笔最低限额");
        }
    }

    private AcsAccount getDepositAccount(String merchantCode, Integer accountType){

        AcsAccount depositAccount = merchantMapper.findAcsAccount(merchantCode, ACSConstants.ACCT_TYPE_MAIN);
        Merchant merchant = merchantMapper.findMerchant(merchantCode);
        //沒有主錢包则建立主钱包
        if(depositAccount == null){
            acsClientService.createCustomerAccountInfo(merchant.getCustomerId().toString(), ACSConstants.ACCT_TYPE_MAIN);
            depositAccount = merchantMapper.findAcsAccount(merchantCode, ACSConstants.ACCT_TYPE_MAIN);
        }

        if(accountType == ACSConstants.ACCT_TYPE_MAIN){
            return depositAccount;
        }

        if(accountType == ACSConstants.ACCT_TYPE_PLEDGE){
            depositAccount = merchantMapper.findAcsAccount(merchantCode, ACSConstants.ACCT_TYPE_PLEDGE);
            //沒有押金则建立押金钱包
            if(depositAccount == null){
                acsClientService.createCustomerAccountInfo(merchant.getCustomerId().toString(), ACSConstants.ACCT_TYPE_PLEDGE);
                depositAccount = merchantMapper.findAcsAccount(merchantCode, ACSConstants.ACCT_TYPE_PLEDGE);
            }
            return depositAccount;
        }
        return null;
    }

    @Override
    public BaseResponseT<TpPgTo> doPgRecharge(String merchantCode, Integer bankAcctId, String bankId, Integer rechargeType,Long billingId, BigDecimal amount,String operator){

        Integer accountType = rechargeType == RechareType.WALLET_MAIN ? ACSConstants.ACCT_TYPE_MAIN : ACSConstants.ACCT_TYPE_PLEDGE;

        MisBankAccts rechargeBankAcct =  misBankAcctsMapper.selectByPrimaryKey(Long.parseLong(bankAcctId.toString()));

        MisAccountOfferedBanks rechargeOfferedBanks =  misAccountOfferedBanksMapper.findByAcctId(rechargeBankAcct.getRid(),TpBankType.PG.toString()).get(0);

        validateTxAmount(rechargeOfferedBanks.getTxMaxAmount(), rechargeOfferedBanks.getTxMinAmount(), amount);

        AcsAccount merchantAccount = getDepositAccount(merchantCode, accountType);

        String orderNo = OrderNoUtil.getOrderNo(OrderNoUtil.OrderTypeEnum.PG_RECHARGE);

        PssPgDepositTo request = getPgDepositRequest(rechargeBankAcct.getTpAcctId(), orderNo, bankId, merchantCode, amount);

        //新增充值明细
        createRechargeDetail(orderNo, merchantCode, rechargeBankAcct, rechargeOfferedBanks,  merchantAccount, rechargeType, billingId, amount,operator);
        //新增充值交易
        createRechargeTransaction(orderNo, rechargeBankAcct, amount,null,operator);
        //新增三方request
        createRechargeTransactionRequest(pssClientService.getPgDepositUrl(), JsonUtils.toJson(request), orderNo, operator);
        TpPgTo to = new TpPgTo();
        try {
            Map<String, Object> response = pssClientService.getThirdPartyDeposit(pssClientService.getPgDepositUrl(),request);
            updateRechargeRequest(orderNo, JsonUtils.toJson(response));

            if((Boolean)response.get("status")){
                to.setPgUrl(((Map<String, Object>) response.get("data")).get("pgUrl").toString());
            }else{
                throw new MisBaseException(ErrorCode.PSS_CLIENT_ERR, "pss_client_err");
            }

        }catch (Exception e){
            LOGGER.error("doTpWithdraw:",e);
            throw new MisBaseException(ErrorCode.PSS_CLIENT_ERR, e);
        }
        return new BaseResponseT(to);
    }

    @Override
    public void doPgRechargeCallback(TPSRechargeResult result){
        createRechargeTransactionCallback(result.getOrderNo(), JsonUtils.toJson(result));

        MisRechargeTransaction transaction = misRechargeTransactionMapper.findByOrderNo(result.getOrderNo());

        if(transaction.getStatus().intValue() != TransactionStatus.REQUEST){
        	LOGGER.info("orderNo: {} already have request status, abort acs transaction. ", result.getOrderNo());
            return;
        }

        boolean isSuccess = StringUtils.equalsIgnoreCase("success", result.getStatus());
        if(!isSuccess){
        	LOGGER.info("orderNo: {} is fail on callback, response: {}", result.getOrderNo(), JsonUtils.toJson(result));
            updateRechargeTransaction(result.getOrderNo(), result.getAmount(), TransactionStatus.CALLBACK_FAIL,null,null,"PSS");
            return;
        }
        rechargeTransactionService.rechargeSuccessProcess(result.getOrderNo(), result.getAmount(), result.getDealId(),"PSS",false);
    }

    private PssPgDepositTo getPgDepositRequest(Integer tpId, String depositId, String bankId, String customer, BigDecimal amount){
        PssPgDepositTo pgDepositTo = new PssPgDepositTo();
        pgDepositTo.setAcctId(tpId);
        pgDepositTo.setDepositId(depositId);
        pgDepositTo.setCustomerId(customer);
        pgDepositTo.setAmount(amount);
        pgDepositTo.setIpAddress("10.10.10.10");
        pgDepositTo.setBankCode(bankId);
        return pgDepositTo;
    }

    private PssMtDepositTo getMtDepositRequest(Integer tpId, String depositId, String message, String customer, BigDecimal amount){
        PssMtDepositTo pgDepositTo = new PssMtDepositTo();
        pgDepositTo.setAcctId(tpId);
        pgDepositTo.setDepositId(depositId);
        pgDepositTo.setCustomerId(customer);
        pgDepositTo.setAmount(amount);
        pgDepositTo.setBankRefNo(message);
        return pgDepositTo;
    }


    private void createRechargeTransaction(String orderNo, MisBankAccts bankAccount,
                                           BigDecimal amount, String message, String operator){

        AcsAccountCondition condition = new AcsAccountCondition();
        condition.setCustomerId(bankAccount.getAcsCustId().longValue());
        condition.setAccountId(bankAccount.getAcsAcctId().longValue());
        AcsAccount acsBankAccount = acsAccountMapper.findAcsAccount(condition).get(0);

        MisRechargeTransaction transaction = new MisRechargeTransaction();
        transaction.setOrderNo(orderNo);
        transaction.setRechargeBankId(new BigDecimal(bankAccount.getBankId()));
        transaction.setRechargeBankName(bankAccount.getBankName());
        transaction.setRechargeBankAccount(bankAccount.getAcctNumber());
        transaction.setRechargeAccountAmount(acsBankAccount.getBalance());
        transaction.setStatus(TransactionStatus.REQUEST);
        transaction.setTxAmount(amount);
        transaction.setMessage(message);
        transaction.setCreateOperatorName(operator);
        transaction.setUpdateOperatorName(operator);
        misRechargeTransactionMapper.insertSelective(transaction);
    }



    private void createRechargeDetail(String orderNo, String merchantCode, MisBankAccts bankAccount, MisAccountOfferedBanks offeredBanks,
                                      AcsAccount account, Integer rechargeType, Long billingId, BigDecimal amount,String operator){
        MisRechargeDetail detail = new MisRechargeDetail();
        detail.setOrderNo(orderNo);
        detail.setMerchantCode(merchantCode);
        detail.setBankType(offeredBanks.getBankType());
        detail.setBankAcctRid(new BigDecimal(bankAccount.getRid()));
        detail.setTxType(new BigDecimal(RechareType.TX_MERCHANT_RECHARGE));
        detail.setRechargeType(new BigDecimal(rechargeType));
        detail.setAccountId(new BigDecimal(account.getAccountId()));
        detail.setBillingId(billingId==null ? null : new BigDecimal(billingId));
        detail.setOriginalAmount(account.getBalance());
        detail.setTxAmount(amount);
        detail.setRechargeCharge(getCharge(amount, offeredBanks.getMerchantRate(), offeredBanks.getMerchantMinAmount(), offeredBanks.getMerchantMaxAmount()));
        detail.setRechargeFee(getCharge(amount, offeredBanks.getBankFeeRate(), offeredBanks.getBankMinFee(), offeredBanks.getBankMaxFee()));
        detail.setCreateOperatorName(operator);
        detail.setUpdateOperatorName(operator);
        misRechargeDetailMapper.insertSelective(detail);
    }

    private static String generateNote(int length) {
        String note = UUID.randomUUID().toString();
        note = Security.hash(note, "sha-256");
        note = note.substring(0, length).toUpperCase();
        note = note.replaceAll("O", randomValidChar()).replaceAll("I", randomValidChar()).replace("0",
                                                                                                  randomValidChar());
        return note;
    }

    private static String randomValidChar() {
        String clearCode = "123456789ABCDEFGHJKLMNPQRSTUVWXY";
        char[] clear = clearCode.toCharArray();
        Random rng = new Random();
        return clear[rng.nextInt(clear.length)] + "";
    }

    @Override
    public BigDecimal getCharge(BigDecimal txAmount, BigDecimal chargeRate, BigDecimal minAmount, BigDecimal maxAmount){

        if(chargeRate == null || chargeRate.signum() <= 0){
            return new BigDecimal(0);
        }
        BigDecimal fee = txAmount.multiply(chargeRate).divide(new BigDecimal(100)).setScale(2, RoundingMode.DOWN);
        //小于最低费用取最低费用
        if(minAmount != null && minAmount.signum() > 0
           && minAmount.compareTo(fee) > 0){
            return minAmount;
        }

        //高于最高费用取最高费用
        if(maxAmount != null && maxAmount.signum() > 0
           && maxAmount.compareTo(fee) < 0){
            return maxAmount;
        }
        return fee;
    }

    private void createRechargeTransactionRequest(String url, String requestPar, String orderNo, String operator){
        MisRechargeTransactionRequest request = new MisRechargeTransactionRequest();
        request.setUrl(url);
        request.setRequest(requestPar);
        request.setOrderNo(orderNo);
        request.setCreateOperatorName(operator);
        request.setUpdateOperatorName(operator);
        transactionReqMapper.insertSelective(request);
    }

    private void updateRechargeRequest(String orderNo, String response){
        MisRechargeTransactionRequest request = transactionReqMapper.findByOrderNo(orderNo);
        request.setResponse(response);
        request.setUpdateTime(new Date());
        transactionReqMapper.updateByPrimaryKeySelective(request);
    }

    private void updateRechargeTransaction(String orderNo,BigDecimal resultAmount, Integer status, String remark,String tpOrderNo,String operator){
        MisRechargeTransaction transaction = misRechargeTransactionMapper.findByOrderNo(orderNo);
        transaction.setStatus(status);
        transaction.setRemark(remark);
        transaction.setActualAmount(resultAmount);
        transaction.setGetTime(new Date());
        transaction.setTpOrderNo(tpOrderNo);
        transaction.setUpdateTime(new Date());
        transaction.setUpdateOperatorName(operator);
        misRechargeTransactionMapper.updateByPrimaryKeySelective(transaction);
    }

    private void createRechargeTransactionCallback(String orderNo, String response){
        MisRechargeTransactionCallback callback = new MisRechargeTransactionCallback();
        callback.setResponse(response);
        callback.setOrderNo(orderNo);
        callback.setCreateOperatorName("PSS");
        callback.setUpdateOperatorName("PSS");
        transactionCallbackMapper.insertSelective(callback);
    }

    @Override
    public void updateRechargeDetailByAdmin(String orderNo, String merchantCode, Integer accountType, BigDecimal amount, String remark, String fileUrl, String operator){

        AcsAccount merchantAccount = merchantMapper.findAcsAccount(merchantCode, accountType);

        MisRechargeDetail detail = new MisRechargeDetail();
        detail.setOrderNo(orderNo);
        detail.setMerchantCode(merchantCode);
        detail.setBankType("TCG");
        detail.setBankAcctRid(new BigDecimal(0));
        detail.setTxType(new BigDecimal(RechareType.TX_WALLET_SETTING));
        detail.setRechargeType(accountType == ACSConstants.ACCT_TYPE_MAIN ? new BigDecimal(RechareType.WALLET_MAIN) : new BigDecimal(RechareType.WALLET_CASH_PLEDGE));
        detail.setAccountId(new BigDecimal(merchantAccount.getAccountId()));
        detail.setBillingId(null);
        detail.setOriginalAmount(merchantAccount.getBalance());
        detail.setTxAmount(amount);
        detail.setRechargeCharge(new BigDecimal(0));
        detail.setRechargeFee(new BigDecimal(0));
        detail.setRemark(remark);
        detail.setFileUrl(fileUrl);
        detail.setCreateOperatorName(operator);
        detail.setUpdateOperatorName(operator);
        misRechargeDetailMapper.insertSelective(detail);
    }
}

