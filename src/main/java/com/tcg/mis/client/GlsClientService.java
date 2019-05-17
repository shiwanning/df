package com.tcg.mis.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.tcg.mis.common.http.HttpClientTool;
import com.tcg.mis.common.util.JsonUtils;
import com.tcg.mis.to.MerchantWalletManageTo;
import com.tcg.mis.to.WalletTo;

@Service
@Transactional
public class GlsClientService {

    @Value("${gls.service.host}")
    private String glsHost;
    @Value("${gls.console.host}")
    private String glsConsoleHost;
    
    @Autowired
    HttpClientTool httpClientTool;

    public List<WalletTo> getWalletInfo() {
    	JsonNode jsonNode = httpClientTool.doGet("http://" + glsHost + "/gcs/gamelobby/wallet/info", JsonNode.class);
        return JsonUtils.getListFromJsonNode(jsonNode.get("value"),WalletTo.class);
    }

    public List<MerchantWalletManageTo> getMerchantWalletInfo(String merchantCode) {
        Map<String,String> pairs = new HashMap<>();
        pairs.put("type", String.valueOf(0));
        pairs.put("merchantName",merchantCode);
        pairs.put("page",String.valueOf(1));
        pairs.put("pageSize",String.valueOf(999));
        pairs.put("sortBy","accountTypeName");
        pairs.put("sortOrder","asc");
        String url = "http://" + glsConsoleHost + "/tcg-gcs-console/wallet/list";
        List<NameValuePair> nvpList = new ArrayList<>(pairs.size());
        for (Map.Entry<String, String> entry : pairs.entrySet()) {
          nvpList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        JsonNode jsonNode = httpClientTool.doGet(url, JsonNode.class, nvpList);
        return JsonUtils.getListFromJsonNode(jsonNode.get("list"), MerchantWalletManageTo.class);
    }
    
    
}

