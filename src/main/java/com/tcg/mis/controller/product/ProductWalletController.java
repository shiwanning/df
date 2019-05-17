package com.tcg.mis.controller.product;

import com.google.common.collect.Lists;
import com.tcg.mis.common.constant.ACSConstants;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.response.BaseResponse;
import com.tcg.mis.common.response.BaseResponseT;
import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.controller.recharge.RechargeController;
import com.tcg.mis.model.MisProductTransaction;
import com.tcg.mis.model.vo.LobbyAccountType;
import com.tcg.mis.model.vo.MerchantProductTo;
import com.tcg.mis.service.product.ProductWalletService;
import com.tcg.mis.service.recharge.RechargeTransactionService;
import com.tcg.mis.service.subsystem.OSService;
import com.tcg.mis.to.condition.ProductTransactionCondition;
import com.tcg.mis.to.request.CamelPageAndSortTo;
import com.tcg.mis.to.request.ProductRechargeTo;
import com.tcg.mis.to.request.ProductTransferTo;
import com.tcg.mis.to.request.TransactionApproveTo;

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

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = {"/product/wallet"}, produces = MediaType.APPLICATION_JSON)
@Api(tags = "product", description = "产品")
@Validated
public class ProductWalletController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RechargeController.class);

    @Autowired
    ProductWalletService productWalletService;
    @Autowired
    private OSService ossService;
    @Autowired
    private RechargeTransactionService rechargeTransactionService;

    @GetMapping(value = "/info")
    @ApiOperation(value = "钱包明细")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponseT<MerchantProductTo> getRechargePaymentTypes(
            @ApiParam(value = "merchantCode", required = true) @RequestParam(value = "merchantCode", required = true) String merchantCode
                                                                   ){
        return productWalletService.getMerchantProductWallet(merchantCode);
    }

    @PostMapping(value = "/recharge")
    @ApiOperation(value = "产品买分")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponse doProductRecharge(
            @RequestBody @Validated ProductRechargeTo productTo,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token){

        productWalletService.doProductRecharge(productTo,ossService.getCurrentUser(token).getOperatorName(),token);
        return new BaseResponse(true);
    }

    @PostMapping(value = {"/transfer"})
    @ApiOperation(value = "产品转分")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponse doProductTransfer(
            @RequestBody @Validated ProductTransferTo transferTo,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token){
        if(ACSConstants.PRODUCT_WALLET_MANUAL_ACCOUNT_TYPES.contains(transferTo.getFromAccountTypeId())){
            throw new MisBaseException(ErrorCode.PARAM_ERR, "param_err");
        }
        productWalletService.doProductTransfer(transferTo,ossService.getCurrentUser(token).getOperatorName(),token);
        return new BaseResponse(true);
    }

    @PostMapping(value = {"/p/transfer"})
    @ApiOperation(value = "产品转分")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponse doFundProductTransfer(
            @RequestBody @Validated ProductTransferTo transferTo,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token){

        productWalletService.doProductTransfer(transferTo,ossService.getCurrentUser(token).getOperatorName(),token);
        return new BaseResponse(true);
    }

    @GetMapping(value = "/transaction")
    @ApiOperation(value = "产品钱包交易明细")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public PageResponse<MisProductTransaction,MisProductTransaction> getTransactionDetails(
            @ApiParam(value = "merchantCode", required = false) @RequestParam(value = "merchantCode", required = false) String merchantCode,
            @ApiParam(value = "product", required = false) @RequestParam(value = "product", required = false) String product,
            @ApiParam(value = "subProduct", required = false) @RequestParam(value = "subProduct", required = false) String subProduct,
            @ApiParam(value = "orderNo", required = false) @RequestParam(value = "orderNo", required = false) String orderNo,
            @ApiParam(value = "txType 1:上分 2:下分", required = false) @RequestParam(value = "txType", required = false) Integer txType,
            @ApiParam(value = "status", required = false) @RequestParam(value = "status", required = false) Integer status,
            @ApiParam(value = "开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "startDate", required = false) Date startDate,
            @ApiParam(value = "结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "endDate",required = false) Date endDate,
            @Validated CamelPageAndSortTo pageAndSortTo
                                                                                          ){

        ProductTransactionCondition condition = new ProductTransactionCondition();
        condition.setMerchantCode(merchantCode);
        condition.setOrderNo(orderNo);
        condition.setStatus(status);
        condition.setStartDate(startDate);
        condition.setEndDate(endDate);
        condition.setProduct(product);
        condition.setSubProduct(subProduct);
        condition.setTxType(txType);
        condition.setPageAndSortTO(
                pageAndSortTo
                        .withDefaultSortColumn("createTime")
                        .withDefaultSortType("desc")
                                  );

        return productWalletService.getTransactionDetails(condition);
    }

    @GetMapping("/product/info")
    public BaseResponseT<List<LobbyAccountType>> getWalletInfo() {

        return new BaseResponseT(rechargeTransactionService.getWalletList());
    }

    @GetMapping(value = "/merchant")
    @ApiOperation(value = "商戶钱包")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponseT<MerchantProductTo> getMerchantWallet(
            @ApiParam(value = "merchantCode", required = true) @RequestParam(value = "merchantCode", required = true) String merchantCode
                                                                   ){
        return productWalletService.getMerchantWallet(merchantCode);
    }

    @GetMapping(value = "/fund/transaction")
    @ApiOperation(value = "产品钱包交易明细")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public PageResponse<MisProductTransaction,MisProductTransaction> getFundTransactionDetails(
            @ApiParam(value = "merchantCode", required = false) @RequestParam(value = "merchantCode", required = false) String merchantCode,
            @ApiParam(value = "product", required = false) @RequestParam(value = "product", required = false) String product,
            @ApiParam(value = "subProduct", required = false) @RequestParam(value = "subProduct", required = false) String subProduct,
            @ApiParam(value = "orderNo", required = false) @RequestParam(value = "orderNo", required = false) String orderNo,
            @ApiParam(value = "txType 1:上分 2:下分", required = false) @RequestParam(value = "txType", required = false) Integer txType,
            @ApiParam(value = "status", required = false) @RequestParam(value = "status", required = false) Integer status,
            @ApiParam(value = "开始时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "startDate", required = false) Date startDate,
            @ApiParam(value = "结束时间(yyyy-MM-dd HH:mm:ss)") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @RequestParam(value = "endDate",required = false) Date endDate,
            @Validated CamelPageAndSortTo pageAndSortTo
                                                                                          ){
        ProductTransactionCondition condition = new ProductTransactionCondition();
        condition.setMerchantCode(merchantCode);
        condition.setOrderNo(orderNo);
        condition.setStatus(status);
        condition.setStartDate(startDate);
        condition.setEndDate(endDate);
        condition.setProduct(product);
        condition.setSubProduct(subProduct);
        condition.setTxType(txType);
        condition.setPageAndSortTO(
                pageAndSortTo
                        .withDefaultSortColumn("createTime")
                        .withDefaultSortType("desc")
                                  );
        condition.setProducts(Lists.newArrayList("KY","MG","IBC"));

        return productWalletService.getTransactionDetails(condition);
    }

    @PostMapping(value = {"/fund/approve"})
    @ApiOperation(value = "手工通過")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponse fundApprove(
            @RequestBody @Validated TransactionApproveTo transactionApproveTo,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token){

        productWalletService.doProductWalletManualApprove(transactionApproveTo.getOrderNo(),transactionApproveTo.getRemark(),ossService.getCurrentUser(token).getOperatorName(),token);
        return BaseResponse.SUCCESS_BASE_RESPONSE;
    }


    @PostMapping(value = {"/fund/reject"})
    @ApiOperation(value = "手工拒絕")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponse fundReject(
            @RequestBody @Validated TransactionApproveTo transactionApproveTo,
            @ApiParam(value = "token", required = true) @RequestHeader("Authorization") String token){

        productWalletService.doProductWalletManualReject(transactionApproveTo.getOrderNo(),transactionApproveTo.getRemark(),ossService.getCurrentUser(token).getOperatorName(),token);
        return BaseResponse.SUCCESS_BASE_RESPONSE;
    }

}
