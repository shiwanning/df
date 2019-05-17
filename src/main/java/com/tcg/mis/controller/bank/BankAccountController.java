package com.tcg.mis.controller.bank;

import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.response.BaseResponse;
import com.tcg.mis.common.response.BaseResponseT;
import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.service.bank.BankAccountService;
import com.tcg.mis.service.subsystem.OSService;
import com.tcg.mis.to.condition.BankAccountCondition;
import com.tcg.mis.to.request.CamelPageAndSortTo;
import com.tcg.mis.to.request.SaveBankAcctTo;
import com.tcg.mis.to.response.BankAccountTo;
import com.tcg.mis.to.response.ViewBankAcctTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/bank/account", produces = MediaType.APPLICATION_JSON)
@Api(tags = "bank", description = "银行")
@Validated
public class BankAccountController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountController.class);

	@Autowired
	private OSService ossService;
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@GetMapping(value = "")
    @ApiOperation(value = "支付账号查询")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public PageResponse<BankAccountTo, BankAccountTo> bankAccountList(
    		@ApiParam(value = "银行ID(bankId)",required = false) @RequestParam(value = "bankId", required = false) Integer bankId,
    		@ApiParam(value = "帐号状态(0:启用, 1:停用)",required = false, allowableValues="0,1") @RequestParam(value = "status", required = false) Integer status,
    		@ApiParam(value = "渠道名称(channelName)",required = false) @RequestParam(value = "channelName", required = false) String channelName,
    		@ApiParam(value = "启用支付类型(activePayType)",required = false) @RequestParam(value = "activePayType", required = false) String activePayType,
    		@Validated CamelPageAndSortTo pageAndSortTo) {
		
		BankAccountCondition condition = new BankAccountCondition();
        
		condition.setBankId(bankId);
		condition.setActivePayType(activePayType);
		condition.setChannelName(channelName);
		condition.setStatus(status);
		
		condition.setPageAndSortTO(
			pageAndSortTo
				.withDefaultSortColumn("updateTime")
				.withDefaultSortType("desc")
		);
		
        return bankAccountService.getBankAccountList(condition);
    }
	
	@GetMapping(value = "/view")
    @ApiOperation(value = "支付账号查看")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponseT<ViewBankAcctTo> viewBankAcct(
    		@ApiParam(value = "帐号ID(acctId)",required = true) @RequestParam(value = "acctId", required = true) Long acctId
    		) {
		ViewBankAcctTo view = bankAccountService.getBankAccount(acctId);
		return new BaseResponseT<>(view);
    }
	
	
	@PostMapping(value = "/create")
    @ApiOperation(value = "支付账号创建")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponse saveBankAcct(@RequestBody @Validated SaveBankAcctTo saveBankAcctTo, @RequestHeader("Authorization") String token) {
		bankAccountService.createBankAcct(saveBankAcctTo, ossService.getCurrentUser(token).getOperatorName());
        return BaseResponse.SUCCESS_BASE_RESPONSE;
    }
	
	@PutMapping(value = "/update")
    @ApiOperation(value = "支付账号更新")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponse updateBankAcct(@RequestBody @Validated SaveBankAcctTo saveBankAcctTo, @RequestHeader("Authorization") String token) {
		
		if(saveBankAcctTo.getRid() == null) {
			throw new MisBaseException(ErrorCode.DATA_NOT_FOUND, "no bank acct is null.");
		}
		
		bankAccountService.updateBankAcct(saveBankAcctTo, ossService.getCurrentUser(token).getOperatorName());
        return BaseResponse.SUCCESS_BASE_RESPONSE;
    }
	
}
