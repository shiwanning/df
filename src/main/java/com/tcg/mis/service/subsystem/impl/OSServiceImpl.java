package com.tcg.mis.service.subsystem.impl;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.http.HttpClientTool;
import com.tcg.mis.common.util.JsonUtils;
import com.tcg.mis.configuration.OSSConfig;
import com.tcg.mis.service.subsystem.OSService;
import com.tcg.mis.to.FileInfoTo;
import com.tcg.mis.to.HttpResponse;
import com.tcg.mis.to.OperatorInfo;
import com.tcg.mis.to.OssBaseResponse;
@Service
public class OSServiceImpl implements OSService {

	private static final Logger logger = LoggerFactory.getLogger(OSServiceImpl.class);

    @Autowired
    private OSSConfig ossConfig;
    
    @Autowired
    private HttpClientTool httpClientTool;
    
    private static final String FILE_UPLOAD_PATH = "/file";
    private static final String GET_PROFILE_PATH = "/operators/profile";
    private static final String VERIFY_LOG_PATH = "/subsystem/verification/advanced";
    private static final String GET_MERCHANTS_URL_PATH = "/subsystem/user/merchants";
    
    @Override
    public  boolean hasPermission(String params,String token,Integer menuId,String merchantCode){
    	Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("Content-Type", "application/json");
        Map<String,String> parameters = new HashMap<>();
        parameters.put("menuId",String.valueOf(menuId));
        parameters.put("params",params);
        if(!StringUtils.isBlank(merchantCode)) {
        	parameters.put("merchant",merchantCode);
        }
        
        try {
            String logURL = ossConfig.getOSSUrl() + VERIFY_LOG_PATH;
            logger.info("Connecting to "+logURL+" with params "+ parameters +" with headers "+ headers);
            
            HttpResponse response = httpClientTool.doPost(logURL, HttpResponse.class, parameters, headers);

            return response.isSuccess();
        } catch (Exception e) {
            logger.error("Error connecting in OSS ",e);
            return false;
        }
    }

    @Override
    public OperatorInfo getCurrentUser(String token) {

        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", token);
        try {
            String apiURL = ossConfig.getOSSUrl()+ GET_PROFILE_PATH;
            logger.info("Connecting to "+apiURL+" with headers"+ headers);
            
            OperatorInfo response = httpClientTool.doGet(apiURL, OperatorInfo.class, headers);

            return response.getValue();
        } catch (Exception e) {
            logger.error("Error connecting in OSS ",e);
            throw new MisBaseException(ErrorCode.SYS_ERR, e);
        }
    }

	@Override
	public List<String> getMerchants(String token) {
		List<String> merchantCodes = Lists.newLinkedList();
		Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", token);
        String apiURL = ossConfig.getOSSUrl()+ GET_MERCHANTS_URL_PATH;
        logger.info("Connecting to "+apiURL+ " with headers "+ headers);
        OssBaseResponse response = httpClientTool.doGet(apiURL, OssBaseResponse.class, headers);
        List<Map<String, String>> merchants = ( List ) response.getValue();
        for(Map<String, String> merchant : merchants) {
        	merchantCodes.add(merchant.get("merchantCode"));
        }
        return merchantCodes;
	}

	@Override
	public FileInfoTo uploadFile(MultipartFile file, String moduleName) throws IOException {
		String apiURL = ossConfig.getOSSUrl()+ FILE_UPLOAD_PATH + "/" + moduleName;
		logger.info("Connecting to {}", apiURL);
		OssBaseResponse response = httpClientTool.doFilePost(apiURL, OssBaseResponse.class, file, "file");
		Map<String, String> value = (Map) response.getValue();
		FileInfoTo result = new FileInfoTo();
		
		result.setFileName(value.get("fileName"));
		result.setFileUrl(value.get("fileUrl"));
		return result;
	}

}
