package com.tcg.mis.client;


import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.http.HttpClientTool;
import com.tcg.mis.common.util.JsonUtils;
import com.tcg.mis.to.response.CreateTaskResponse;
import com.tcg.mis.to.response.TaskBaseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: gls client <br/>
 *
 * @author Eddie
 */
@Service
@Transactional
public class TacClientService {

    @Value("${oss.internal.host}")
    private String ossHost;
    @Value("${oss.internal.address}")
    private String ossServicePath;

    @Autowired
    HttpClientTool httpClientTool;

    private static final String TASK_API_PATH = "/subsystem/workflow/task";

    public Integer createTask(Integer stateId, String merchantCode,
                          String externalTransId, String token, boolean isSystemTask, String description)
           {

        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", token);
        Map<String,String> parameters = new HashMap<>();
        parameters.put("subsystemTaskId", externalTransId);
        parameters.put("stateId", stateId.toString());
        parameters.put("taskDescription", description);
        parameters.put("merchantCode", merchantCode);
        parameters.put("isSystem", isSystemTask+"");

        String url = getOSSUrl()+TASK_API_PATH;

        CreateTaskResponse response = httpClientTool.doPutKeyValue(url, CreateTaskResponse.class , parameters, headers);

        if(!response.isSuccess()){
            throw new MisBaseException(ErrorCode.TAC_CLIENT_ERR, JsonUtils.toJson(response));
        }
        return response.getValue().getTaskId();
    }

    public void closeTask(String token, int taskId) {

        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", token);
        String url = getOSSUrl()+TASK_API_PATH+"/"+taskId;
        TaskBaseResponse response = httpClientTool.doDelete(url, TaskBaseResponse.class, headers, null);

        if(!response.isSuccess()){
            throw new MisBaseException(ErrorCode.TAC_CLIENT_ERR, JsonUtils.toJson(response));
        }
    }

    public void excuteTask(String token, int taskId, Integer stateId){
        lockTask(token, taskId);
        updateTask(token, taskId, stateId);
    }

    public void updateTask(String token, Integer taskId, Integer stateId){

        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", token);
        Map<String,String> parameters = new HashMap<>();
        parameters.put("taskId",taskId.toString());
        parameters.put("stateId",stateId.toString());
        parameters.put("close","true");
        String url = getOSSUrl()+TASK_API_PATH;
        TaskBaseResponse response = httpClientTool.doPostKeyValue(url, TaskBaseResponse.class , parameters, headers);

        if(!response.isSuccess()){
                throw new MisBaseException(ErrorCode.TAC_CLIENT_ERR, JsonUtils.toJson(response));
        }
    }

    public void lockTask(String token, Integer taskId){

        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", token);
        Map<String,String> parameters = new HashMap<>();
        parameters.put("taskId",taskId.toString());
        String url = getOSSUrl() + TASK_API_PATH + "/lock/" + taskId;

        TaskBaseResponse response = httpClientTool.doPostKeyValue(url, TaskBaseResponse.class , parameters, headers);

        if(!response.isSuccess()){
            throw new MisBaseException(ErrorCode.TAC_CLIENT_ERR, JsonUtils.toJson(response));
        }
    }

    public String getOSSUrl() {
        return ossHost + ossServicePath;
    }
}
