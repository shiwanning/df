package com.tcg.mis.service.product.impl;

import com.tcg.mis.client.AcsClientService;
import com.tcg.mis.client.TacClientService;
import com.tcg.mis.common.constant.ACSConstants;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.constant.ProductRechargeStatus;
import com.tcg.mis.common.constant.TacTaskStats;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.common.response.BaseResponseT;
import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.common.util.OrderNoUtil;
import com.tcg.mis.mapper.AcsAccountMapper;
import com.tcg.mis.mapper.MerchantMapper;
import com.tcg.mis.mapper.MisProductTransactionMapper;
import com.tcg.mis.model.MisProductTransaction;
import com.tcg.mis.model.vo.AcsAccount;
import com.tcg.mis.model.vo.MerchantProductTo;
import com.tcg.mis.model.vo.MerchantProductWalletTo;
import com.tcg.mis.model.vo.PrepayTemplateDetailTO;
import com.tcg.mis.service.product.ProductWalletService;
import com.tcg.mis.to.condition.AcsAccountCondition;
import com.tcg.mis.to.condition.MerchantTemplateCondition;
import com.tcg.mis.to.condition.ProductTransactionCondition;
import com.tcg.mis.to.request.CamelPageAndSortTo;
import com.tcg.mis.to.request.ProductRechargeTo;
import com.tcg.mis.to.request.ProductTransferTo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class ProductWalletServiceImpl implements ProductWalletService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductWalletServiceImpl.class);

    @Autowired
    private MisProductTransactionMapper productTransactionMapper;
    @Autowired
    private AcsAccountMapper acsAccountMapper;
    @Autowired
    private AcsClientService acsClientService;
    @Autowired
    private MerchantMapper merchantMapper;
    @Autowired
    private TacClientService tacClientService;

    @Override
    public BaseResponseT<MerchantProductTo> getMerchantProductWallet(String merchantCode){

        AcsAccountCondition condition = new AcsAccountCondition();
        condition.setCustomerName(merchantCode);
        condition.setAccountTypeId(ACSConstants.ACCT_TYPE_MAIN.longValue());
        List<AcsAccount> list = acsAccountMapper.findAcsAccount(condition);
        MerchantProductWalletTo mainWallet = new MerchantProductWalletTo();
        if(list == null || list.isEmpty()){
            mainWallet.setAccountTypeId(ACSConstants.ACCT_TYPE_MAIN);
            mainWallet.setBalance(new BigDecimal(0));
        }else {
            mainWallet.setAccountTypeId(ACSConstants.ACCT_TYPE_MAIN);
            mainWallet.setBalance(list.get(0).getBalance());
        }

        MerchantProductTo productTo = new MerchantProductTo();
        productTo.setMainWallet(mainWallet);

        List<MerchantProductWalletTo> productWallets = acsAccountMapper.findMerchantProductWalletInfo(merchantCode);

        MerchantTemplateCondition merchantTemplateCondition = getMerchantTemplateCondition(merchantCode, null, null);

        List<PrepayTemplateDetailTO> prepayDetails = merchantMapper.findPrepayTemplateDetails(merchantTemplateCondition);
        convertProductWallet(prepayDetails, productWallets);
        productTo.setProductWalletToList(productWallets);

        return new BaseResponseT(productTo);
    }

    @Override
    public BaseResponseT<MerchantProductTo> getMerchantWallet(String merchantCode){

        List<MerchantProductWalletTo> list = acsAccountMapper.findMerchantWallet(merchantCode);
        return new BaseResponseT(list);
    }

    private MerchantTemplateCondition getMerchantTemplateCondition(String merchantCode, String product, String subProduct){
        MerchantTemplateCondition condition = new MerchantTemplateCondition();
        condition.setMerchantCode(merchantCode);
        condition.setProduct(product);
        condition.setSubProduct(StringUtils.isBlank(subProduct) ? null : subProduct);
        return condition;
    }


    private void convertProductWallet(List<PrepayTemplateDetailTO> detailList, List<MerchantProductWalletTo> productWalletList){

        for(MerchantProductWalletTo merchantProductWallet : productWalletList){

            for(PrepayTemplateDetailTO detail : detailList){
                if(!merchantProductWallet.getAccountTypeName().equals(detail.getProduct())){
                    continue;
                }
                merchantProductWallet.getDetails().add(detail);
            }
        }
    }

    @Override
    public void doProductRecharge(ProductRechargeTo to, String operator, String token){

        MerchantTemplateCondition merchantTemplateCondition = getMerchantTemplateCondition(to.getMerchantCode(), to.getProduct(), to.getSubProduct());

        PrepayTemplateDetailTO detailTO = merchantMapper.findPrepayTemplateDetails(merchantTemplateCondition).get(0);

        BigDecimal mainAmount;
        BigDecimal productAmount;

        //0:金額
        if(to.getAmountType() == 0){
            mainAmount = to.getAmount();
            BigDecimal rate = detailTO.getRate().divide(new BigDecimal(100));
            productAmount = mainAmount.divide(rate,4, RoundingMode.DOWN);
        //1:分數
        }else {
            productAmount = to.getAmount();
            BigDecimal rate = detailTO.getRate().divide(new BigDecimal(100));
            mainAmount = productAmount.multiply(rate).setScale(4, RoundingMode.DOWN);;
        }

        AcsAccountCondition condition = new AcsAccountCondition();
        condition.setCustomerName(to.getMerchantCode());
        condition.setAccountTypeId(ACSConstants.ACCT_TYPE_MAIN.longValue());

        AcsAccount mainAccount = acsAccountMapper.findAcsAccount(condition).get(0);

        condition.setAccountTypeId(to.getToAccountTypeId().longValue());
        AcsAccount productAccount = acsAccountMapper.findAcsAccount(condition).get(0);

        String orderNo = OrderNoUtil.getOrderNo(OrderNoUtil.OrderTypeEnum.MERCHANT_PRODUCT_RECHARGE);

        //手工產品錢包上分
        if( ACSConstants.PRODUCT_WALLET_MANUAL_ACCOUNT_TYPES.contains(to.getToAccountTypeId())){

            String description = to.getMerchantCode() + "|" + productAccount.getAccountTypeName() + "|" + mainAmount;

            Integer taskId = tacClientService.createTask(TacTaskStats.PRODUCT_DEPOSIT_REQUEST.getId(),to.getMerchantCode(),orderNo,token,true,description);

            createProductTransaction(to.getMerchantCode(), orderNo, productAmount, mainAmount, detailTO.getRate(), to.getProduct(), to.getSubProduct()
                    , ACSConstants.CREDIT, productAccount, mainAccount, ProductRechargeStatus.REQUEST , operator,taskId);

        }else {
            if(doProductWalletCredit(mainAccount.getAccountId().intValue(), productAccount.getAccountId().intValue(), mainAmount, productAmount, orderNo)){
                mainAccount = acsAccountMapper.findAcsAccount(condition).get(0);
                productAccount = acsAccountMapper.findAcsAccount(condition).get(0);
                createProductTransaction(to.getMerchantCode(), orderNo, productAmount, mainAmount, detailTO.getRate(), to.getProduct(), to.getSubProduct()
                        , ACSConstants.CREDIT, productAccount, mainAccount, ProductRechargeStatus.SUCCESS , operator,null);
            }
        }

    }


    @Override
    public void doProductTransfer(ProductTransferTo to, String operator, String token){
        //手工產品錢包上分
        if( ACSConstants.PRODUCT_WALLET_MANUAL_ACCOUNT_TYPES.contains(to.getToAccountTypeId())){
            doProductWalletManual(to, operator, token);
        }else {
            doProductWalletTransfer(to, operator);
        }
    }

    private void doProductWalletTransfer(ProductTransferTo to, String operator){
        MerchantTemplateCondition merchantTemplateCondition = getMerchantTemplateCondition(to.getMerchantCode(), to.getFromProduct(), to.getFromSubProduct());

        PrepayTemplateDetailTO detailTO = merchantMapper.findPrepayTemplateDetails(merchantTemplateCondition).get(0);

        AcsAccount mainAccount = acsAccountMapper.findAcsAccount(
                getAcsCondition(to.getMerchantCode(),ACSConstants.ACCT_TYPE_MAIN.longValue())).get(0);

        AcsAccount productAccount = acsAccountMapper.findAcsAccount(
                getAcsCondition(to.getMerchantCode(),to.getFromAccountTypeId().longValue())).get(0);

        //檢查金額
        isVaildTransAmount(to.getAmount(), productAccount.getAvailBalance());

        BigDecimal productAmount = to.getAmount();
        BigDecimal rate = detailTO.getRate().divide(new BigDecimal(100));
        BigDecimal mainAmount = productAmount.multiply(rate).setScale(4, RoundingMode.DOWN);

        String orderNo = OrderNoUtil.getOrderNo(OrderNoUtil.OrderTypeEnum.MERCHANT_PRODUCT_TRANSFER);

        //產品錢包下分
        if(doProductWalletDebit(mainAccount.getAccountId().intValue(), productAccount.getAccountId().intValue(), mainAmount, productAmount, orderNo)){

            mainAccount = acsAccountMapper.findAcsAccount(
                    getAcsCondition(to.getMerchantCode(),ACSConstants.ACCT_TYPE_MAIN.longValue())).get(0);
            productAccount = acsAccountMapper.findAcsAccount(
                    getAcsCondition(to.getMerchantCode(),to.getFromAccountTypeId().longValue())).get(0);

            createProductTransaction(to.getMerchantCode(), orderNo, productAmount, mainAmount, detailTO.getRate(), to.getFromProduct(), to.getFromSubProduct()
                    , ACSConstants.DEBIT, productAccount, mainAccount, ProductRechargeStatus.SUCCESS , operator,null);
        }

        //產品錢包上分
        if(to.getToAccountTypeId() != ACSConstants.ACCT_TYPE_MAIN){

            merchantTemplateCondition = getMerchantTemplateCondition(to.getMerchantCode(), to.getToProduct(), to.getToSubProduct());
            detailTO = merchantMapper.findPrepayTemplateDetails(merchantTemplateCondition).get(0);

            mainAccount = acsAccountMapper.findAcsAccount(
                    getAcsCondition(to.getMerchantCode(),ACSConstants.ACCT_TYPE_MAIN.longValue())).get(0);

            productAccount = acsAccountMapper.findAcsAccount(
                    getAcsCondition(to.getMerchantCode(),to.getToAccountTypeId().longValue())).get(0);

            rate = detailTO.getRate().divide(new BigDecimal(100));
            productAmount = mainAmount.divide(rate,4, RoundingMode.DOWN);

            if(doProductWalletCredit(mainAccount.getAccountId().intValue(), productAccount.getAccountId().intValue(), mainAmount, productAmount, orderNo)){

                mainAccount = acsAccountMapper.findAcsAccount(
                        getAcsCondition(to.getMerchantCode(),ACSConstants.ACCT_TYPE_MAIN.longValue())).get(0);

                productAccount = acsAccountMapper.findAcsAccount(
                        getAcsCondition(to.getMerchantCode(),to.getToAccountTypeId().longValue())).get(0);

                createProductTransaction(to.getMerchantCode(), orderNo, productAmount, mainAmount, detailTO.getRate(), to.getToProduct(), to.getToSubProduct()
                        , ACSConstants.CREDIT, productAccount, mainAccount, ProductRechargeStatus.SUCCESS , operator,null);
            }

        }
    }

    private void doProductWalletManual(ProductTransferTo to, String operator, String token){

        PrepayTemplateDetailTO fromDetail = merchantMapper.findPrepayTemplateDetails(
                getMerchantTemplateCondition(to.getMerchantCode(), to.getFromProduct(), to.getFromSubProduct())).get(0);

        PrepayTemplateDetailTO toDetail = merchantMapper.findPrepayTemplateDetails(
                getMerchantTemplateCondition(to.getMerchantCode(),  to.getToProduct(), to.getToSubProduct())).get(0);

        AcsAccount mainAccount = acsAccountMapper.findAcsAccount(
                getAcsCondition(to.getMerchantCode(),ACSConstants.ACCT_TYPE_MAIN.longValue())).get(0);

        AcsAccount fromProductAccount = acsAccountMapper.findAcsAccount(
                getAcsCondition(to.getMerchantCode(),to.getFromAccountTypeId().longValue())).get(0);

        AcsAccount toProductAccount = acsAccountMapper.findAcsAccount(
                getAcsCondition(to.getMerchantCode(),to.getToAccountTypeId().longValue())).get(0);

        //檢查金額
        isVaildTransAmount(to.getAmount(), fromProductAccount.getAvailBalance());

        BigDecimal fromProductAmount = to.getAmount();
        BigDecimal fromRate = fromDetail.getRate().divide(new BigDecimal(100));
        BigDecimal mainAmount = fromProductAmount.multiply(fromRate).setScale(4, RoundingMode.DOWN);

        BigDecimal toRate = toDetail.getRate().divide(new BigDecimal(100));
        BigDecimal toProductAmount = mainAmount.divide(toRate,4, RoundingMode.DOWN);

        String orderNo = OrderNoUtil.getOrderNo(OrderNoUtil.OrderTypeEnum.MERCHANT_PRODUCT_TRANSFER);

        String description = to.getMerchantCode() + "|" + toProductAccount.getAccountTypeName() + "|" + mainAmount;

        Integer taskId = tacClientService.createTask(TacTaskStats.PRODUCT_DEPOSIT_REQUEST.getId(),to.getMerchantCode(),orderNo,token,true,description);

        createProductTransaction(to.getMerchantCode(), orderNo, fromProductAmount, mainAmount, fromDetail.getRate(), to.getFromProduct(), to.getFromSubProduct()
                , ACSConstants.DEBIT, fromProductAccount, mainAccount, ProductRechargeStatus.REQUEST , operator,taskId);

        createProductTransaction(to.getMerchantCode(), orderNo, toProductAmount, mainAmount, toDetail.getRate(), to.getToProduct(), to.getToSubProduct()
                , ACSConstants.CREDIT, toProductAccount, mainAccount, ProductRechargeStatus.REQUEST , operator,taskId);

    }

    private void isVaildTransAmount(BigDecimal txAmount, BigDecimal accountBalance){
        if(txAmount.compareTo(accountBalance) > 1){
            throw new MisBaseException(ErrorCode.SYS_ERR,"餘額不足");
        }
    }

    @Override
    public PageResponse<MisProductTransaction,MisProductTransaction> getTransactionDetails(ProductTransactionCondition condition) {

        PaginationAndOrderVO page = condition.generatePaginationAndOrderVO();

        List<MisProductTransaction> list = productTransactionMapper.findByCondition(condition, page);

        return new PageResponse<>(list, page, null);
    }

    private Boolean doProductWalletCredit(Integer mainAccountId,Integer productAccountId, BigDecimal amount, BigDecimal productAmount,String orderNo){
        //主钱包扣钱
        Boolean isMainSuccess = acsClientService.lodgeDebitTransaction(mainAccountId, amount, ACSConstants.WALLET_D_DEPOSIT_MAIN,
                                                                       ACSConstants.getTxCode(ACSConstants.WALLET_D_DEPOSIT_MAIN), orderNo);
        //产品钱包上分
        Boolean isProductSuccess = acsClientService.lodgeCreditTransaction(productAccountId, productAmount, ACSConstants.WALLET_C_DEPOSIT_PRODUCT,
                                                                           ACSConstants.getTxCode(ACSConstants.WALLET_C_DEPOSIT_PRODUCT), orderNo);
        return isMainSuccess && isProductSuccess;
    }

    private Boolean doProductWalletDebit(Integer mainAccountId,Integer productAccountId, BigDecimal amount, BigDecimal productAmount,String orderNo){
        //产品钱包扣分
        Boolean isProductSuccess = acsClientService.lodgeDebitTransaction(productAccountId, productAmount, ACSConstants.WALLET_D_DEPOSIT_PRODUCT,
                                                                           ACSConstants.getTxCode(ACSConstants.WALLET_D_DEPOSIT_PRODUCT), orderNo);
        //主钱包加钱
        Boolean isMainSuccess = acsClientService.lodgeCreditTransaction(mainAccountId, amount, ACSConstants.WALLET_C_DEPOSIT_MAIN,
                                                                       ACSConstants.getTxCode(ACSConstants.WALLET_C_DEPOSIT_MAIN), orderNo);
        return isMainSuccess && isProductSuccess;
    }


    private void createProductTransaction(String merchantCode, String orderNo, BigDecimal productAmount, BigDecimal txAmount, BigDecimal rate, String product, String subProduct
                                          , Integer txType,AcsAccount productAccount, AcsAccount mainAccount, Integer status, String operator, Integer taskId){
        MisProductTransaction transaction = new MisProductTransaction();
        transaction.setMerchantCode(merchantCode);
        transaction.setOrderNo(orderNo);
        transaction.setProduct(product);
        transaction.setSubProduct(subProduct);
        transaction.setProductAccountId(productAccount.getAccountId());
        transaction.setProductBalance(productAccount.getBalance());
        transaction.setProcuctAmount(productAmount);
        transaction.setMainAccountId(mainAccount.getAccountId());
        transaction.setMainBalance(mainAccount.getBalance());
        transaction.setMainAmount(txAmount);
        transaction.setRate(rate);
        transaction.setTxType(txType);
        transaction.setStatus(status);
        transaction.setTaskId(taskId);
        transaction.setCreateOperatorName(operator);
        transaction.setUpdateOperatorName(operator);
        productTransactionMapper.insertSelective(transaction);

    }

    @Override
    public Boolean doProductWalletManualReject(String orderNo,String remark, String operator, String token){

        ProductTransactionCondition condition = new ProductTransactionCondition();
        condition.setOrderNo(orderNo);
        PaginationAndOrderVO page = condition.generatePaginationAndOrderVO();

        List<MisProductTransaction> list = productTransactionMapper.findByCondition(condition, page);
        Integer taskId = list.get(0).getTaskId();
        for(MisProductTransaction transaction: list){
            transaction.setStatus(ProductRechargeStatus.CANCEL);
            transaction.setRemark(remark);
            transaction.setUpdateOperatorName(operator);
            transaction.setUpdateTime(new Date());
            productTransactionMapper.updateByPrimaryKeySelective(transaction);

        }
        tacClientService.excuteTask(token, taskId, TacTaskStats.PRODUCT_DEPOSIT_REJECT.getId());

        return true;
    }

    @Override
    public Boolean doProductWalletManualApprove(String orderNo,String remark, String operator, String token){

        CamelPageAndSortTo pageAndSortTo = new CamelPageAndSortTo();
        ProductTransactionCondition condition = new ProductTransactionCondition();
        condition.setOrderNo(orderNo);
        condition.setPageAndSortTO(
                pageAndSortTo
                        .withDefaultSortColumn("txType")
                        .withDefaultSortType("desc")
                                  );
        PaginationAndOrderVO page = condition.generatePaginationAndOrderVO();

        List<MisProductTransaction> list = productTransactionMapper.findByCondition(condition, page);
        Integer taskId = list.get(0).getTaskId();

        for(MisProductTransaction transaction: list){

            boolean isSuccess = false;

            //上分
            if(transaction.getTxType() == 1){
                isSuccess = doProductWalletCredit(transaction.getMainAccountId().intValue(), transaction.getProductAccountId().intValue(),
                                                 transaction.getMainAmount(), transaction.getProcuctAmount(),transaction.getOrderNo());
            //下分
            }
            if(transaction.getTxType() == 2){
                isSuccess = doProductWalletDebit(transaction.getMainAccountId().intValue(), transaction.getProductAccountId().intValue(),
                                     transaction.getMainAmount(), transaction.getProcuctAmount(),transaction.getOrderNo());
            }

            if(isSuccess){
                AcsAccount mainAccount = acsAccountMapper.findAcsAccount(
                        getAcsCondition(transaction.getMainAccountId())).get(0);

                AcsAccount productAccount = acsAccountMapper.findAcsAccount(
                        getAcsCondition(transaction.getProductAccountId())).get(0);

                transaction.setMainBalance(mainAccount.getBalance());
                transaction.setProductBalance(productAccount.getBalance());
                transaction.setStatus(ProductRechargeStatus.SUCCESS);
                transaction.setRemark(remark);
                transaction.setUpdateTime(new Date());
                transaction.setUpdateOperatorName(operator);
                productTransactionMapper.updateByPrimaryKeySelective(transaction);

            }else {
                throw new MisBaseException(ErrorCode.ACS_CLIENT_ERR,"手工上分失敗");
            }
        }

        tacClientService.excuteTask(token, taskId, TacTaskStats.PRODUCT_DEPOSIT_APPROVE.getId());
        return true;
    }

    private AcsAccountCondition getAcsCondition(String merchantCode,Long accountTypeId){
        AcsAccountCondition condition = new AcsAccountCondition();
        condition.setCustomerName(merchantCode);
        condition.setAccountTypeId(accountTypeId);
        return condition;
    }

    private AcsAccountCondition getAcsCondition(Long accountId){
        AcsAccountCondition condition = new AcsAccountCondition();
        condition.setAccountId(accountId);
        return condition;
    }

}

