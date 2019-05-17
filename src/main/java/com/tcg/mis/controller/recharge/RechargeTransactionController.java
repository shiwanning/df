package com.tcg.mis.controller.recharge;

import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.response.BaseResponse;
import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.service.recharge.RechargeTransactionService;
import com.tcg.mis.service.subsystem.OSService;
import com.tcg.mis.to.condition.RechargeTransactionCondition;
import com.tcg.mis.to.request.CamelPageAndSortTo;
import com.tcg.mis.to.request.TransactionApproveTo;
import com.tcg.mis.to.response.MisRechargeTo;

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
@RequestMapping(value = "/recharge", produces = MediaType.APPLICATION_JSON)
@Api(tags = "recharge", description = "充值")
@Validated
public class RechargeTransactionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RechargeTransactionController.class);

	@Autowired
	private RechargeTransactionService rechargeTransactionService;
	
	@Autowired
    private OSService ossService;
	
	@GetMapping(value = {"/transaction", "/p/transaction"})
    @ApiOperation(value = "商户充值查询")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public PageResponse<MisRechargeTo, MisRechargeTo> transactionList(
    		@ApiParam(value = "商户(merchantCode)",required = false) @RequestParam(value = "merchantCode", required = false) String merchantCode,
    		@ApiParam(value = "订单号(orderNo)",required = false) @RequestParam(value = "orderNo", required = false) String orderNo,
    		@ApiParam(value = "交易流水号(rid)",required = false) @RequestParam(value = "rid", required = false) Long rid,
    		@ApiParam(value = "状态(status)", required = false, allowableValues = "1,2,3,4,5,6,7") @RequestParam(value = "status", required = false) Integer status,
    		@ApiParam(value = "存款方式(bankType)", required = false) @RequestParam(value = "bankType", required = false) String bankType,
            @ApiParam(value = "附言(message)",required = false) @RequestParam(value = "message", required = false) String message,
    		@ApiParam(value = "存款金额从(minRequestAmount)",required = false) @RequestParam(value = "minRequestAmount", required = false) BigDecimal minRequestAmount,
    		@ApiParam(value = "存款金额到(maxRequestAmount)",required = false) @RequestParam(value = "maxRequestAmount", required = false) BigDecimal maxRequestAmount,
    		@ApiParam(value = "申请开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "requestStartDate",required = false) Date requestStartDate,
            @ApiParam(value = "申请结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "requestEndDate",required = false) Date requestEndDate,
            @ApiParam(value = "到账开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "payStartDate",required = false) Date payStartDate,
            @ApiParam(value = "到账结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "payEndDate",required = false) Date payEndDate,
            @Validated CamelPageAndSortTo pageAndSortTo) {
		
		RechargeTransactionCondition condition = new RechargeTransactionCondition();
        
		condition.setMerchantCode(merchantCode);
		condition.setBankType(bankType);
		condition.setMaxRequestAmount(maxRequestAmount);
		condition.setMerchantCode(merchantCode);
		condition.setMinRequestAmount(minRequestAmount);
		condition.setOrderNo(orderNo);
		condition.setPayEndDate(payEndDate);
		condition.setPayStartDate(payStartDate);
		condition.setRequestEndDate(requestEndDate);
		condition.setRequestStartDate(requestStartDate);
		condition.setRid(rid);
		condition.setStatus(status);
        condition.setMessage(message);
		condition.setPageAndSortTO(
			pageAndSortTo
				.withDefaultSortColumn("createTime")
				.withDefaultSortType("desc")
		);
		
        return rechargeTransactionService.getMisRechargeTransactionList(condition);
    }
	
	@GetMapping(value = {"/transaction-export", "/p/transaction-export"})
    @ApiOperation(value = "商户充值查询")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public PageResponse<MisRechargeTo, MisRechargeTo> transactionListExport(
    		@ApiParam(value = "商户(merchantCode)",required = false) @RequestParam(value = "merchantCode", required = false) String merchantCode,
    		@ApiParam(value = "订单号(orderNo)",required = false) @RequestParam(value = "orderNo", required = false) String orderNo,
    		@ApiParam(value = "交易流水号(rid)",required = false) @RequestParam(value = "rid", required = false) Long rid,
    		@ApiParam(value = "状态(status)", required = false, allowableValues = "1,2,3,4,5,6,7") @RequestParam(value = "status", required = false) Integer status,
    		@ApiParam(value = "存款方式(bankType)", required = false) @RequestParam(value = "bankType", required = false) String bankType,
            @ApiParam(value = "附言(message)",required = false) @RequestParam(value = "message", required = false) String message,
    		@ApiParam(value = "存款金额从(minRequestAmount)",required = false) @RequestParam(value = "minRequestAmount", required = false) BigDecimal minRequestAmount,
    		@ApiParam(value = "存款金额到(maxRequestAmount)",required = false) @RequestParam(value = "maxRequestAmount", required = false) BigDecimal maxRequestAmount,
    		@ApiParam(value = "申请开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "requestStartDate",required = false) Date requestStartDate,
            @ApiParam(value = "申请结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "requestEndDate",required = false) Date requestEndDate,
            @ApiParam(value = "到账开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "payStartDate",required = false) Date payStartDate,
            @ApiParam(value = "到账结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "payEndDate",required = false) Date payEndDate,
            @Validated CamelPageAndSortTo pageAndSortTo) {
		
		RechargeTransactionCondition condition = new RechargeTransactionCondition();
        
		condition.setMerchantCode(merchantCode);
		condition.setBankType(bankType);
		condition.setMaxRequestAmount(maxRequestAmount);
		condition.setMerchantCode(merchantCode);
		condition.setMinRequestAmount(minRequestAmount);
		condition.setOrderNo(orderNo);
		condition.setPayEndDate(payEndDate);
		condition.setPayStartDate(payStartDate);
		condition.setRequestEndDate(requestEndDate);
		condition.setRequestStartDate(requestStartDate);
		condition.setRid(rid);
		condition.setStatus(status);
        condition.setMessage(message);
		condition.setPageAndSortTO(
			pageAndSortTo
				.withDefaultSortColumn("createTime")
				.withDefaultSortType("desc")
				.pageable(false)
		);
		
        return rechargeTransactionService.getMisRechargeTransactionList(condition);
    }
	
	
	@PostMapping(value = "/transaction/approve")
    @ApiOperation(value = "商户充值查询 - 手工处理")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponse approve(
    		@RequestBody @Validated TransactionApproveTo transactionApproveTo,
    		@ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token ) {
		
		if(transactionApproveTo.getApprove() && (transactionApproveTo.getAmount() == null || transactionApproveTo.getAmount().signum() != 1)) {
			throw new MisBaseException(ErrorCode.PARAM_ERR, "Amount must bigger than 0 when approve.");
		}
		
		rechargeTransactionService.manual(transactionApproveTo, ossService.getCurrentUser(token).getOperatorName());
		
        return BaseResponse.SUCCESS_BASE_RESPONSE;
    }
	
}
