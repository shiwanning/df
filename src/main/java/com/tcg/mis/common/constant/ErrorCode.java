package com.tcg.mis.common.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Description: Error code <br/>
 *
 * @author Eddie
 */
public enum ErrorCode {
    // used
    SYS_ERR("unknown_err"),
    // param error
    PARAM_ERR("param_err"),
    // 子系統API HTTP 呼叫超時
    NETWORK_TIMEOUT("network_timeout"),
    // 參數錯誤
    REQ_ERR("req_param_err"),
    // 下級不屬於此代理
    AGENT_NOT_DOWNLINE("agent_not_downline"),
    // 沒有簽約
    CONTRACT_NOT_SIGNED("contract_not_signed"),
    // 不合法的分紅操作
    DIVIDEND_OPERATE_ILLEGAL("dividend_operate_illegal"),
    // 余额不足
    NOT_ENOUGH_AMOUNT("not_enough_amount"),
    // 沒有標籤
    LABEL_NOT_SIGNED("label_not_signed"),
    // 查无数据
    DATA_NOT_FOUND("data_not_found"),
    // 数据已存在
    DATA_HAS_EXISTED("data_has_existed"),
    // 新增失敗
    INSERT_FAILED("insert_failed"),
    // 更新失敗
    UPDATE_FAILED("update_failed"),
    // 删除失敗
    DELETE_FAILED("delete_failed"),

    // 呼叫 ACS 发生错误
    ACS_CLIENT_ERR("acs_client_err"),

    // 呼叫 TAC 发生错误
    TAC_CLIENT_ERR("tac_client_err"),

    ACS_CREDIT_NOT_ENOUGH("acs_account_credit_not_enough"),
    
    // 呼叫 USS 发生错误
    USS_CLIENT_ERR("uss_client_err"),
    
    // 呼叫 PSS 发生错误
    PSS_CLIENT_ERR("pss_client_err"),
    
    STATUS_ERR("status_err"),
    
    JSON_ERR("json_err"),
    
    AUTH_ERR("auth_err"),
    
    SIGN_ERR("sign_err"),
    FREQUENCY_ERR("frequency_err"),
    BANK_ACCT_CLOSED("bank_acct_closed"),
    OVER_TX_MAX_AMOUNT("over_tx_max_amount"),
    LOWER_TX_MIN_AMOUNT("lower_tx_min_amount"),
    REVIEW_TEMPLATE_EXIST("REVIEW_TEMPLATE_EXIST"),
    REVIEW_TEMPLATE_NON_EXIST("REVIEW_TEMPLATE_NON_EXIST"),
    TEMPLATE_CONTENT_DUPLICATE("TEMPLATE_CONTENT_DUPLICATE"),
    TEMPLATE_DETAIL_EMPTY("TEMPLATE_DETAIL_EMPTY"),
    TEMPLATE_NAME_DUPLICATE("TEMPLATE_NAME_DUPLICATE"), 
    AMOUNT_INVALID("AMOUNT_INVALID"),
    ;

    private final String displayCode;

    ErrorCode(String errorCode) {
        this.displayCode = errorCode;
    }

    @JsonValue
    public String getCode() {
        return this.displayCode;
    }

    @Override
    public String toString() {
        return this.displayCode;
    }

}
