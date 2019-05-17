package com.tcg.mis.controller.tcg;

import com.tcg.mis.common.constant.ACSConstants;
import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.model.TcgTransaction;
import com.tcg.mis.model.TcgTransactionSummary;
import com.tcg.mis.service.tcg.TransactionService;
import com.tcg.mis.to.condition.AcsTransactionCondition;
import com.tcg.mis.to.request.CamelPageAndSortTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON)
@Api(tags = "transaction", description = "ACS帐变")
@Validated
public class TransactionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
	private TransactionService tcgTransactionService;
	
	@GetMapping(value = "/tcg")
    @ApiOperation(value = "Tcg帐变查询")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public PageResponse<TcgTransaction, TcgTransactionSummary> tcgTransactionList(
            @ApiParam(value = "银行卡(bankAcctRid)",required = false) @RequestParam(value = "bankAcctRid", required = false) Long bankAcctRid,
    		@ApiParam(value = "扣款方式(1:收入, 2:支出)",required = false) @RequestParam(value = "debitType", required = false) Integer debitType,
            @ApiParam(value = "交易类型(type)",required = false) @RequestParam(value = "type", required = false) Integer type,
    		@ApiParam(value = "交易开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "startDate",required = false) Date startDate,
            @ApiParam(value = "交易结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "endDate",required = false) Date endDate,
    		@Validated CamelPageAndSortTo pageAndSortTo) {

        AcsTransactionCondition condition = new AcsTransactionCondition();

        List<Integer> txTypeIds = new ArrayList<>();
        if(debitType == null){
            txTypeIds.addAll(ACSConstants.TCG_CREDIT_LIST);
            txTypeIds.addAll(ACSConstants.TCG_DEBIT_LIST);
        }else if(debitType.intValue() == 1){
            txTypeIds.addAll(ACSConstants.TCG_CREDIT_LIST);
        }else if(debitType.intValue() == 2){
            txTypeIds.addAll(ACSConstants.TCG_DEBIT_LIST);
        }
        condition.setDebitType(debitType);
        condition.setTxTypeIds(txTypeIds);
        condition.setType(type);
        condition.setBankAcctRid(bankAcctRid);
        condition.setStartDate(startDate);
        condition.setEndDate(endDate);
		
		condition.setPageAndSortTO(
			pageAndSortTo
				.withDefaultSortColumn("txTime")
				.withDefaultSortType("desc")
		);
        return tcgTransactionService.getTcgTransactionList(condition);
    }
	
	@GetMapping(value = "/tcg-export")
    @ApiOperation(value = "Tcg帐变查询")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public PageResponse<TcgTransaction, TcgTransactionSummary> tcgTransactionListExport(
            @ApiParam(value = "银行卡(bankAcctRid)",required = false) @RequestParam(value = "bankAcctRid", required = false) Long bankAcctRid,
    		@ApiParam(value = "扣款方式(1:收入, 2:支出)",required = false) @RequestParam(value = "debitType", required = false) Integer debitType,
            @ApiParam(value = "交易类型(type)",required = false) @RequestParam(value = "type", required = false) Integer type,
    		@ApiParam(value = "交易开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "startDate",required = false) Date startDate,
            @ApiParam(value = "交易结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "endDate",required = false) Date endDate,
    		@Validated CamelPageAndSortTo pageAndSortTo) {

        AcsTransactionCondition condition = new AcsTransactionCondition();

        List<Integer> txTypeIds = new ArrayList<>();
        if(debitType == null){
            txTypeIds.addAll(ACSConstants.TCG_CREDIT_LIST);
            txTypeIds.addAll(ACSConstants.TCG_DEBIT_LIST);
        }else if(debitType.intValue() == 1){
            txTypeIds.addAll(ACSConstants.TCG_CREDIT_LIST);
        }else if(debitType.intValue() == 2){
            txTypeIds.addAll(ACSConstants.TCG_DEBIT_LIST);
        }
        condition.setDebitType(debitType);
        condition.setTxTypeIds(txTypeIds);
        condition.setType(type);
        condition.setBankAcctRid(bankAcctRid);
        condition.setStartDate(startDate);
        condition.setEndDate(endDate);
		
		condition.setPageAndSortTO(
			pageAndSortTo
				.withDefaultSortColumn("txTime")
				.withDefaultSortType("desc")
				.pageable(false)
		);
        return tcgTransactionService.getTcgTransactionList(condition);
    }




    @GetMapping(value = {"/merchant","/p/merchant"})
    @ApiOperation(value = "商戶帐变查询")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public PageResponse<TcgTransaction, TcgTransactionSummary> merchantTransactionList(
            @ApiParam(value = "商戶(merchantCode)",required = false) @RequestParam(value = "merchantCode", required = false) String merchantCode,
            @ApiParam(value = "钱包类型(accountTypeId)",required = false) @RequestParam(value = "accountTypeId", required = false) Integer accountTypeId,
            @ApiParam(value = "交易类型(type)",required = false) @RequestParam(value = "type", required = false) Integer type,
            @ApiParam(value = "交易开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "startDate",required = false) Date startDate,
            @ApiParam(value = "交易结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "endDate",required = false) Date endDate,
            @Validated CamelPageAndSortTo pageAndSortTo) {

        AcsTransactionCondition condition = new AcsTransactionCondition();
        condition.setMerchantCode(merchantCode);
        condition.setAccountTypeId(accountTypeId);
        condition.setType(type);
        condition.setStartDate(startDate);
        condition.setEndDate(endDate);

        condition.setPageAndSortTO(
                pageAndSortTo
                        .withDefaultSortColumn("txTime")
                        .withDefaultSortType("desc")
                                  );
        return tcgTransactionService.getMerchantTransactionList(condition);
    }
    
    @GetMapping(value = {"/merchant-export","/p/merchant-export"})
    @ApiOperation(value = "商戶帐变查询")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public PageResponse<TcgTransaction, TcgTransactionSummary> merchantTransactionListExport(
            @ApiParam(value = "商戶(merchantCode)",required = false) @RequestParam(value = "merchantCode", required = false) String merchantCode,
            @ApiParam(value = "钱包类型(accountTypeId)",required = false) @RequestParam(value = "accountTypeId", required = false) Integer accountTypeId,
            @ApiParam(value = "交易类型(type)",required = false) @RequestParam(value = "type", required = false) Integer type,
            @ApiParam(value = "交易开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "startDate",required = false) Date startDate,
            @ApiParam(value = "交易结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(value = "endDate",required = false) Date endDate,
            @Validated CamelPageAndSortTo pageAndSortTo) {

        AcsTransactionCondition condition = new AcsTransactionCondition();
        condition.setMerchantCode(merchantCode);
        condition.setAccountTypeId(accountTypeId);
        condition.setType(type);
        condition.setStartDate(startDate);
        condition.setEndDate(endDate);
        condition.setPageAndSortTO(
                pageAndSortTo
                        .withDefaultSortColumn("txTime")
                        .withDefaultSortType("desc")
                        .pageable(false)
                                  );
        
        return tcgTransactionService.getMerchantTransactionList(condition);
    }
	
    
}
