package com.tcg.mis.controller.recharge;

import com.tcg.mis.common.constant.Currency;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.response.BaseResponse;
import com.tcg.mis.common.response.BaseResponseT;
import com.tcg.mis.service.recharge.RechargeService;
import com.tcg.mis.service.recharge.RechargeTransactionService;
import com.tcg.mis.service.subsystem.OSService;
import com.tcg.mis.to.request.PgRechargeTo;
import com.tcg.mis.to.request.TPSRechargeResult;
import com.tcg.mis.to.response.BanksTo;
import com.tcg.mis.to.response.PgBanksTo;
import com.tcg.mis.to.response.TpCallbackResponse;
import com.tcg.mis.to.response.TpMtTo;
import com.tcg.mis.to.response.TpPgTo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

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
public class RechargeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RechargeController.class);

    @Autowired
    RechargeService rechargeService;
    @Autowired
    RechargeTransactionService rechargeTransactionService;
    @Autowired
    private OSService ossService;

    @GetMapping(value = "/payment/types")
    @ApiOperation(value = "充值方式")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponseT<List<String>> getRechargePaymentTypes(){

        return rechargeService.getPaymentTypes();
    }

    @GetMapping(value = "/payment/type/banks")
    @ApiOperation(value = "PG充值银行讯息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponseT<PgBanksTo> getRechargeTypeBanks(
            @ApiParam(value = "merchantCode", required = true) @RequestParam(value = "merchantCode", required = true) String merchantCode)
    {
        if(!StringUtils.equals(Currency.CNY.toString(),rechargeTransactionService.getMerchant(merchantCode).getCurrency())){
            return new BaseResponseT(ErrorCode.BANK_ACCT_CLOSED);
        }
        return rechargeService.getPgBanks();
    }

    @GetMapping(value = "/mt/bank")
    @ApiOperation(value = "銀行訊息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponseT<BanksTo> getRechargeBankInfo(
            @ApiParam(value = "merchantCode", required = true) @RequestParam(value = "merchantCode", required = true) String merchantCode)
    {
        if(!StringUtils.equals(Currency.CNY.toString(),rechargeTransactionService.getMerchant(merchantCode).getCurrency())){
            return new BaseResponseT(ErrorCode.BANK_ACCT_CLOSED);
        }
        return rechargeService.getMtBanks();
    }

    @PostMapping(value = "/mt")
    @ApiOperation(value = "手工充值")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponseT<TpMtTo> doBankRecharge
            (@RequestBody @Validated PgRechargeTo rechargeTo,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token){

        return rechargeService.doMtRecharge(rechargeTo.getMerchantCode(), rechargeTo.getBankAcctRid(), rechargeTo.getRechargeType(), rechargeTo.getAmount(), ossService.getCurrentUser(token).getOperatorName());
    }

    @PostMapping(value = "/pg")
    @ApiOperation(value = "网关充值")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponseT<TpPgTo> doPgRecharge(
            @RequestBody @Validated PgRechargeTo rechargeTo,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token){

        return rechargeService.doPgRecharge(rechargeTo.getMerchantCode(),rechargeTo.getBankAcctRid(), rechargeTo.getBankId(), rechargeTo.getRechargeType(), null, rechargeTo.getAmount(), ossService.getCurrentUser(token).getOperatorName());
    }

    @PostMapping(value = "/pg/callback",consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "第三方回調")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public TpCallbackResponse doPgCallBack(
            @RequestBody TPSRechargeResult result){
        try {
            rechargeService.doPgRechargeCallback(result);
            return new TpCallbackResponse("SUCCESS","SUCCESS");
        }catch (Exception e){
            LOGGER.error("fail",e);
            return new TpCallbackResponse("SUCCESS","SUCCESS");
        }
    }

    @PostMapping(value = "/detail/update")
    @ApiOperation(value = "主钱包金额设定回調")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponse doPgCallBack(
            @ApiParam(value = "商户(merchantCode)", required = true) @RequestParam(value = "merchantCode", required = true) String merchantCode,
            @ApiParam(value = "交易編號", required = true) @RequestParam(value = "orderNo", required = true) String orderNo,
            @ApiParam(value = "錢包類型", required = true) @RequestParam(value = "accountType", required = true) Integer accountType,
            @ApiParam(value = "金額", required = true) @RequestParam(value = "amount", required = true) BigDecimal amount,
            @ApiParam(value = "备注", required = true) @RequestParam(value = "remark", required = false) String remark,
            @ApiParam(value = "附件url", required = true) @RequestParam(value = "fileUrl", required = false) String fileUrl,
            @ApiParam(value = "操作人", required = true) @RequestParam(value = "operator", required = true) String operator
            ){
        try {
            rechargeService.updateRechargeDetailByAdmin(orderNo, merchantCode, accountType, amount, remark, fileUrl, operator);
            return new BaseResponse(true);
        }catch (Exception e){
            LOGGER.error("fail",e);
            return new BaseResponse(false, ErrorCode.INSERT_FAILED, e.getMessage());
        }
    }

}
