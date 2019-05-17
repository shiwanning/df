package com.tcg.mis.controller.merchant;

import com.tcg.mis.common.response.BaseResponse;
import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.model.MerchantBill;
import com.tcg.mis.service.merchant.MerchantBillDetailService;
import com.tcg.mis.service.merchant.MerchantBillService;
import com.tcg.mis.to.request.CamelPageAndSortTo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import java.util.Collections;
import java.util.List;

/**
 * com.tcg.mis.controller.merchant
 *
 * @author lyndon.j
 * @version 1.0
 * @date 2019/5/9 18:17
 */
@RestController
@RequestMapping("/finance/bill")
@Validated
@Api(value = "finance bill",description = "天成财务操作商户账单")
public class FinanceBillController {


    @Resource
    private MerchantBillService merchantBillService;
    @Resource
    private MerchantBillDetailService merchantBillDetailService;

    @GetMapping(value = "/list")
    @ResponseBody
    @ApiOperation(notes = "merchant query bill list", value = "天成财务查看账单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商户code", name = "merchantCode", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(value = "订单状态", name = "status", paramType = "query", dataType = "int"),
            @ApiImplicitParam(value = "月份", name = "month", paramType = "query", dataType = "string"),
            @ApiImplicitParam(value = "年份", name = "year", paramType = "query", dataType = "string")
    })
    public BaseResponse list(@NotNull String merchantCode, Integer month, Integer year, Integer status, @Validated CamelPageAndSortTo page) {
        List<MerchantBill> result = merchantBillService.financeGetBillList(merchantCode,year,month,status);
        return new PageResponse(result);
    }


    @GetMapping(value = "/detail")
    @ResponseBody
    @ApiOperation(notes = "finance query bill detail info.", value = "天成财务查看账单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "账单号", name = "billNo", paramType = "query", dataType = "string", required = true)
    })
    public BaseResponse detail(@NotNull String billNo) {
        MerchantBill bill = merchantBillService.getBillDetailByBillNo(null,billNo,true);
        return new PageResponse(Collections.singletonList(bill));
    }


    @PostMapping(value = "/detail/edit")
    @ResponseBody
    public BaseResponse detailEdit() {
        merchantBillService.update(null);
        return null;
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    public BaseResponse billEdit() {
        merchantBillService.update(null);
        return null;
    }

    @PostMapping(value = "/offlinePaid")
    public BaseResponse offlinePaid() {
        //TODO 线下支付
        return null;
    }
}
