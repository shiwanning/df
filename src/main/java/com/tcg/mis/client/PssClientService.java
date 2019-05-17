package com.tcg.mis.client;


import com.google.common.collect.Maps;
import com.tcg.mis.common.http.HttpClientTool;
import com.tcg.mis.to.request.TPSWithdrawRequest;
import com.tcg.mis.to.request.ThirdPartyAccountRequest;
import com.tcg.mis.to.response.PSSBaseResponse;
import com.tcg.mis.to.response.TPSRawBankList;
import com.tcg.mis.to.response.ThirdPartyAccountResponse;
import com.tcg.mis.to.response.ThirdPartyRequiredParameter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Description: gls client <br/>
 *
 * @author Eddie
 */
@Service
@Transactional
public class PssClientService {

    @Value("${pss.internal.host}")
    private String pssHost;

    @Value("${pss.internal.address}")
    private String pssPath;

    @Autowired
    HttpClientTool httpClientTool;


    public PSSBaseResponse doWithdraw(TPSWithdrawRequest request) {
        return httpClientTool.doPost(getWithdrawUrl(), PSSBaseResponse.class, request);
    }

    public ThirdPartyAccountResponse doPostThirdPartyRequest(ThirdPartyAccountRequest request) {
        String url = "http://" + pssHost + pssPath + "/addThirdPartyAccount";
        return httpClientTool.doPost(url, ThirdPartyAccountResponse.class, request);
    }
    
    public ThirdPartyAccountResponse getThirdPartyAccountInfo(Long acctId) {
    	String url = "http://" + pssHost + pssPath + "/getThirdPartyAccountInfo";
        Map<String, String> map = Maps.newHashMap();
        
        map.put("accountId", String.valueOf(acctId));
        return httpClientTool.doPost(url, ThirdPartyAccountResponse.class, getNameValuePairs(map));
    }

    public ThirdPartyAccountResponse doUpdateThirdPartyRequest(ThirdPartyAccountRequest request) {
        String url = "http://" + pssHost + pssPath + "/updateThirdPartyAccount";
        return httpClientTool.doPost(url, ThirdPartyAccountResponse.class, request);
    }

    public ThirdPartyRequiredParameter getThirdPartyRequiredParameters(Long vendorId) {
        String url = "http://" + pssHost + pssPath + "/getThirdPartyRequiredParameters";
        Map<String, String> map = Maps.newHashMap();
        
        map.put("vendorId", String.valueOf(vendorId));
        return httpClientTool.doPost(url, ThirdPartyRequiredParameter.class, map);
    }

    public TPSRawBankList getThirdPartyBankList(Integer acctId) {
        String url = "http://" + pssHost + pssPath + "/getThirdPartyBankList";
        Map<String, String> map = Maps.newHashMap();

        map.put("acctId", String.valueOf(acctId));

        return httpClientTool.doPost(url,TPSRawBankList.class,map);
    }

    public Map<String, Object> getThirdPartyDeposit(String url,Object request) {
        return httpClientTool.doPost(url, Map.class, request);
    }


    public String getWithdrawUrl(){
        return "http://" + pssHost + pssPath + "/withdraw";
    }

    public String getPgDepositUrl(){
        return "http://" + pssHost + pssPath + "/deposit";
    }

    public String getMtDepositUrl(){
        return "http://" + pssHost + pssPath + "/depositMT";
    }

    private NameValuePair[] getNameValuePairs(Map<String, String> params) {
        NameValuePair[] nvpArray = new NameValuePair[params.size()];
        int idx = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            nvpArray[idx++] = new BasicNameValuePair(entry.getKey(), entry.getValue());
        }
        return nvpArray;
    }
}
