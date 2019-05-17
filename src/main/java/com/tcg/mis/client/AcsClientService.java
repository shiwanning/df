package com.tcg.mis.client;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.tcg.mis.common.constant.ACSConstants;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.http.HttpClientTool;
import com.tcg.mis.common.log.TcgLogFactory;
import com.tcg.mis.common.util.JsonUtils;
import com.tcg.mis.to.client.AccountInfo;
import com.tcg.mis.to.response.AcsBaseResponse;
import com.yx.acs.client.ACServiceFactory;
import com.yx.acs.model.AcsAccountTotalCredit;
import com.yx.acs.model.BaseTransaction;
import com.yx.acs.service.AccountChangeService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description: acs client <br/>
 *
 * @author Eddie
 */
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Service
public class AcsClientService {

    private static final Logger LOGGER = TcgLogFactory.getLogger(AcsClientService.class);

    @Value("${acs.service.host}")
    private String acsHost;

    @Value("${acs.service.address}")
    private String basePath;

    @Autowired
    HttpClientTool httpClientTool;

    private final ACServiceFactory factory = ACServiceFactory.getInstance();
    private AccountChangeService service;

    private AccountChangeService getAccountChangeService() {
        if (service == null) {
            service = factory.createAccountChangeService();
        }
        return service;
    }

    public boolean lodgeDebitTransaction(Integer accountId, BigDecimal amount, int transTypeId,
                                         String remarks, String orderNo) {

        String url = "http://" + acsHost + basePath + "/transactions/debit";

        BaseTransaction baseTransaction = new BaseTransaction();
        baseTransaction.setAccountId(accountId);
        baseTransaction.setDebit(ACSConstants.DEBIT);

        baseTransaction.setAmount(amount.negate());// 金额

        baseTransaction.setBookTxId(0L);
        baseTransaction.setOrderNo(orderNo);
        baseTransaction.setRemark(remarks);
        baseTransaction.setTxTypeId(transTypeId);

        AcsBaseResponse response = httpClientTool.doPost(url, AcsBaseResponse.class, baseTransaction);

        if (response == null) {
            LOGGER.error("Acs LodgeDebitTransaction fail, reponse is null.");
            throw new MisBaseException(ErrorCode.ACS_CLIENT_ERR, "asc debit error");
        } else if (!response.getSuccess()) {
            LOGGER.error("Acs LodgeDebitTransaction fail, errorCode: " + response.getErrorCode() + ", message:" + response.getMessage());
            if (ErrorCode.ACS_CREDIT_NOT_ENOUGH.toString().equalsIgnoreCase(response.getErrorCode())) {
                throw new MisBaseException(ErrorCode.ACS_CREDIT_NOT_ENOUGH, "asc debit error");
            } else {
                throw new MisBaseException(ErrorCode.ACS_CLIENT_ERR, "asc debit error");
            }
        }
        return true;

    }

    public boolean lodgeCreditTransaction(Integer accountId, BigDecimal amount, int transTypeId,
                                          String remarks, String orderNo) {
        try {
            String url = "http://" + acsHost + basePath + "/transactions/credit";

            BaseTransaction baseTransaction = new BaseTransaction();
            baseTransaction.setAccountId(accountId);
            baseTransaction.setDebit(ACSConstants.CREDIT);

            baseTransaction.setAmount(amount);// 金额

            baseTransaction.setBookTxId(0L);
            baseTransaction.setOrderNo(orderNo);
            baseTransaction.setRemark(remarks);
            baseTransaction.setTxTypeId(transTypeId);

            AcsBaseResponse response = httpClientTool.doPost(url, AcsBaseResponse.class, baseTransaction);

            if (response == null) {
                LOGGER.error("Acs LodgeCreditTransaction fail, reponse is null.");
                return false;
            }
            if (!response.getSuccess()) {
                LOGGER.error("Acs LodgeCreditTransaction fail, errorCode: " + response.getErrorCode() + ", message:" + response.getMessage());
                return false;
            }

            return true;
        } catch (Exception e) {
            LOGGER.error("Acs lodgeCreditTransaction fail.", e);
            return false;
        }

    }

    private BaseTransaction getBaseTransaction(Integer accountId, BigDecimal amount, int transTypeId,
                                               String remarks, String orderNo) {
        BaseTransaction trans = new BaseTransaction();
        trans.setAccountId(accountId);
        trans.setAmount(amount);
        trans.setRemark(remarks);
        trans.setTxTypeId(transTypeId);
        trans.setOrderNo(orderNo);
        return trans;
    }

    public Boolean createCustomerAccountInfo(String customerId, Integer accountTypeId) {
        String apiUrl = "/accounts/customer/%s/accountType/%d";
        JsonNode response = httpClientTool
                .doPost("http://" + acsHost + "/ac-service-service/resources" + String.format(apiUrl, customerId, accountTypeId), JsonNode.class,
                        null, null);
        return response.get("success").asBoolean();
    }

    public BigDecimal getTotalCreditLimit(Integer customerId) {
        AcsAccountTotalCredit accountTotalCredit = getAccountChangeService().getAccountTotalCredit(customerId);
        if (accountTotalCredit != null) {
            return accountTotalCredit.getCreditLimit();
        } else {
            return BigDecimal.ZERO;
        }
    }
    
    public AccountInfo getCustomerAccountInfo(Long customerId, Integer accountTypeId) {
        String apiUrl = "/accounts/customer/%s/accountType/%d";
        AccountInfo account = httpClientTool.doGet("http://" + acsHost + "/ac-service-service/resources" + String.format(apiUrl,customerId,accountTypeId), AccountInfo.class);
        
        // 没资料或有错误
        if(account.getAccountId() == null){
        	LOGGER.error("no account for customerId: " + customerId + ", accountTypeId: " + accountTypeId);
            return null;
        } else {
        	return account;
        }
    }

    public void addorupdateTotalCreditLimit(Integer customerId, BigDecimal totalCreditLimit) {
        getAccountChangeService().setAccountTotalCredit(customerId, totalCreditLimit);
    }

    public void updateCreditLimit(Integer accountId, BigDecimal creditLimit) {
        getAccountChangeService().updateCreditLimit(accountId, creditLimit);
    }

	public List<AccountInfo> getCustomerAccountList(Long customerId) {
		String apiUrl = "/accounts/customer/%s";
        String responseString = httpClientTool.doGet("http://" + acsHost + "/ac-service-service/resources" + String.format(apiUrl,customerId), String.class);
        responseString = "{ \"list\": " + responseString + " }";
        
        LOGGER.info("getCustomerAccountList response: {}", responseString);
        
        JsonNode responseNode = JsonUtils.getJsonNodeFromJson(responseString);
        if(responseNode != null && responseNode.get("success") != null && StringUtils.equals("false",responseNode.get("success").asText())){
            return Lists.newLinkedList();
        }else {
            return JsonUtils.getListFromJsonNode(responseNode.get("list"),AccountInfo.class);
        }
	}
}
