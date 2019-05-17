package com.tcg.mis.controller.withdraw;

import com.tcg.mis.common.response.BaseResponse;
import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.service.subsystem.OSService;
import com.tcg.mis.service.withdraw.WithdrawService;
import com.tcg.mis.service.withdraw.WithdrawTransactionService;
import com.tcg.mis.to.condition.WithdrawTransactionCondition;
import com.tcg.mis.to.request.CamelPageAndSortTo;
import com.tcg.mis.to.request.TransactionApproveTo;
import com.tcg.mis.to.response.MisWithdrawTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/withdraw", produces = MediaType.APPLICATION_JSON)
@Api(tags = "withdraw", description = "提現轉帳")
@Validated
public class WithdrawTransactionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawTransactionController.class);
	
	@Autowired
	private WithdrawTransactionService withdrawTransactionService;
    @Autowired
    WithdrawService withdrawService;
    @Autowired
    private OSService ossService;
	
	@GetMapping(value = "/transaction")
    @ApiOperation(value = "商户提现查询")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public PageResponse<MisWithdrawTo, MisWithdrawTo> transactionList(
            @ApiParam(value = "流水号",required = false) @RequestParam(value = "rid", required = false) Long rid,
    		@ApiParam(value = "订单号(orderNo)",required = false) @RequestParam(value = "orderNo", required = false) String orderNo,
    		@ApiParam(value = "状态(status)", required = false, allowableValues = "1,2,3,4") @RequestParam(value = "status", required = false) Integer status,
    		@ApiParam(value = "转入银行ID(depositBankId)",required = false) @RequestParam(value = "depositBankId", required = false) Integer depositBankId,
    		@ApiParam(value = "转入银行卡号(depositBankAccount)",required = false) @RequestParam(value = "depositBankAccount", required = false) String depositBankAccount,
    		@ApiParam(value = "转出银行ID(withdrawBankId)", required = false) @RequestParam(value = "withdrawBankId", required = false) Integer withdrawBankId,
    		@ApiParam(value = "转出银行ID(withdrawBankName)", required = false) @RequestParam(value = "withdrawBankName", required = false) String withdrawBankName,
    		@ApiParam(value = "转出银行卡号(withdrawBankAccount)", required = false) @RequestParam(value = "withdrawBankAccount", required = false) String withdrawBankAccount,
    		@ApiParam(value = "到账金额从(minTxAmount)",required = false) @RequestParam(value = "minTxAmount", required = false) BigDecimal minTxAmount,
    		@ApiParam(value = "到账金额到(maxTxAmount)",required = false) @RequestParam(value = "maxTxAmount", required = false) BigDecimal maxTxAmount,
    		@ApiParam(value = "申请开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "requestStartDate",required = false) Date requestStartDate,
            @ApiParam(value = "申请结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "requestEndDate",required = false) Date requestEndDate,
            @ApiParam(value = "出款开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "payStartDate",required = false) Date payStartDate,
            @ApiParam(value = "出款结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "payEndDate",required = false) Date payEndDate,
    		@Validated CamelPageAndSortTo pageAndSortTo) {
		
		WithdrawTransactionCondition condition = new WithdrawTransactionCondition();
        condition.setRid(rid);
		condition.setOrderNo(orderNo);
		condition.setStatus(status);
		condition.setDepositBankId(depositBankId);
		condition.setDepositBankAccount(depositBankAccount);
		condition.setWithdrawBankId(withdrawBankId);
		condition.setWithdrawBankName(withdrawBankName);
		condition.setWithdrawBankAccount(withdrawBankAccount);
		condition.setMinTxAmount(minTxAmount);
		condition.setMaxTxAmount(maxTxAmount);
		condition.setRequestStartDate(requestStartDate);
		condition.setRequestEndDate(requestEndDate);
		condition.setPayStartDate(payStartDate);
		condition.setPayEndDate(payEndDate);
		
		condition.setPageAndSortTO(
			pageAndSortTo
				.withDefaultSortColumn("createTime")
				.withDefaultSortType("desc")
		);
		
        return withdrawTransactionService.getMisWithdrawTransactionList(condition);
    }
	
	@GetMapping(value = "/transaction-export")
    @ApiOperation(value = "商户提现查询")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public PageResponse<MisWithdrawTo, MisWithdrawTo> transactionListExport(
            @ApiParam(value = "流水号",required = false) @RequestParam(value = "rid", required = false) Long rid,
    		@ApiParam(value = "订单号(orderNo)",required = false) @RequestParam(value = "orderNo", required = false) String orderNo,
    		@ApiParam(value = "状态(status)", required = false, allowableValues = "1,2,3,4") @RequestParam(value = "status", required = false) Integer status,
    		@ApiParam(value = "转入银行ID(depositBankId)",required = false) @RequestParam(value = "depositBankId", required = false) Integer depositBankId,
    		@ApiParam(value = "转入银行卡号(depositBankAccount)",required = false) @RequestParam(value = "depositBankAccount", required = false) String depositBankAccount,
    		@ApiParam(value = "转出银行ID(withdrawBankId)", required = false) @RequestParam(value = "withdrawBankId", required = false) Integer withdrawBankId,
    		@ApiParam(value = "转出银行ID(withdrawBankName)", required = false) @RequestParam(value = "withdrawBankName", required = false) String withdrawBankName,
    		@ApiParam(value = "转出银行卡号(withdrawBankAccount)", required = false) @RequestParam(value = "withdrawBankAccount", required = false) String withdrawBankAccount,
    		@ApiParam(value = "到账金额从(minTxAmount)",required = false) @RequestParam(value = "minTxAmount", required = false) BigDecimal minTxAmount,
    		@ApiParam(value = "到账金额到(maxTxAmount)",required = false) @RequestParam(value = "maxTxAmount", required = false) BigDecimal maxTxAmount,
    		@ApiParam(value = "申请开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "requestStartDate",required = false) Date requestStartDate,
            @ApiParam(value = "申请结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "requestEndDate",required = false) Date requestEndDate,
            @ApiParam(value = "出款开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "payStartDate",required = false) Date payStartDate,
            @ApiParam(value = "出款结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "payEndDate",required = false) Date payEndDate,
    		@Validated CamelPageAndSortTo pageAndSortTo) {
		
		WithdrawTransactionCondition condition = new WithdrawTransactionCondition();
        condition.setRid(rid);
		condition.setOrderNo(orderNo);
		condition.setStatus(status);
		condition.setDepositBankId(depositBankId);
		condition.setDepositBankAccount(depositBankAccount);
		condition.setWithdrawBankId(withdrawBankId);
		condition.setWithdrawBankName(withdrawBankName);
		condition.setWithdrawBankAccount(withdrawBankAccount);
		condition.setMinTxAmount(minTxAmount);
		condition.setMaxTxAmount(maxTxAmount);
		condition.setRequestStartDate(requestStartDate);
		condition.setRequestEndDate(requestEndDate);
		condition.setPayStartDate(payStartDate);
		condition.setPayEndDate(payEndDate);
		
		condition.setPageAndSortTO(
			pageAndSortTo
				.withDefaultSortColumn("createTime")
				.withDefaultSortType("desc")
				.pageable(false)
		);
		
        return withdrawTransactionService.getMisWithdrawTransactionList(condition);
    }

    @PostMapping(value = "/transaction/approve")
    @ApiOperation(value = "通過")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponse approve(
            @RequestBody @Validated TransactionApproveTo transactionApproveTo,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token){
    	
        withdrawService.doTpWithdrawApprove(transactionApproveTo, ossService.getCurrentUser(token).getOperatorName());
        return BaseResponse.SUCCESS_BASE_RESPONSE;
    }
	
}
