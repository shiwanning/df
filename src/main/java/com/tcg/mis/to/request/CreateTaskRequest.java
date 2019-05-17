package com.tcg.mis.to.request;

/**
 * Created by ivan.julius on 2/21/2017.
 */
public class CreateTaskRequest {

    private Integer subsystemTaskId;
    private Integer stateId;
    private String taskDescription;
    private String merchantCode;
    private boolean isSystem;

    public Integer getSubsystemTaskId() {
        return subsystemTaskId;
    }

    public void setSubsystemTaskId(Integer subsystemTaskId) {
        this.subsystemTaskId = subsystemTaskId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }
}
