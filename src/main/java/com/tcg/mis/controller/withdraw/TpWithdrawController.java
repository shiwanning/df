package com.tcg.mis.controller.withdraw;

import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.response.BaseResponse;
import com.tcg.mis.common.response.BaseResponseT;
import com.tcg.mis.model.vo.BankAccountInfo;
import com.tcg.mis.service.subsystem.OSService;
import com.tcg.mis.service.withdraw.WithdrawService;
import com.tcg.mis.to.request.TPSWithdrawResult;
import com.tcg.mis.to.response.TpCallbackResponse;
import com.tcg.mis.to.response.WithdrawTpTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/withdraw/tp", produces = MediaType.APPLICATION_JSON)
@Api(tags = "withdraw", description = "第三方轉帳")
@Validated
public class TpWithdrawController {

    @Autowired
    WithdrawService withdrawService;
    @Autowired
    private OSService ossService;

    @PostMapping(value = "/")
    @ApiOperation(value = "第三方轉帳")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponse doTpWithdraw(
            @RequestBody @Validated WithdrawTpTo to,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token){

        if(withdrawService.doTpWithdraw(to, ossService.getCurrentUser(token).getOperatorName())){
            return new BaseResponse(true);
        }else{
            return new BaseResponse(false, ErrorCode.PSS_CLIENT_ERR,"fail");
        }
    }

    @GetMapping(value = "/accounts")
    @ApiOperation(value = "帳戶訊息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponseT<List<BankAccountInfo>> getWithdrawAccounts(){

        return withdrawService.getWithdrawAccounts();
    }

    @PostMapping(value = "/callback",consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "第三方回調")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public TpCallbackResponse doTpCallBack(
            @RequestBody TPSWithdrawResult result){
        try {
            withdrawService.doTpWithdrawCallback(result);
            return new TpCallbackResponse("SUCCESS", "SUCCESS");
        }catch (Exception e){
            return new TpCallbackResponse("SUCCESS", "SUCCESS");
        }
    }



}
