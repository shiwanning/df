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
@RequestMapping("/merchant/bill")
@Validated
@Api(tags = "merchant bill", description = "商户账单列表，详情")
public class BillController extends MerchantBaseController {

    @Resource
    private MerchantBillService merchantBillService;
    @Resource
    private MerchantBillDetailService merchantBillDetailService;


    @GetMapping(value = "/list")
    @ResponseBody
    @ApiOperation(notes = "merchant query bill list", value = "商户查看账单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商户code", name = "merchantCode", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(value = "订单状态", name = "status", paramType = "query", dataType = "int"),
            @ApiImplicitParam(value = "月份", name = "month", paramType = "query", dataType = "string"),
            @ApiImplicitParam(value = "年份", name = "year", paramType = "query", dataType = "string")
    })
    public BaseResponse list(@NotNull String merchantCode, Integer month, Integer year, Integer status, @Validated CamelPageAndSortTo page) {
        //super.hasPermissions();
        List<MerchantBill> result = merchantBillService.merchantGetBillList(merchantCode,year,month,status);
        return new PageResponse(result);
    }


    @GetMapping(value = "/getUnPaidBillMonth")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Authorization", name = "token", paramType = "head", dataType = "string", required = true),
            @ApiImplicitParam(value = "商户code", name = "merchantCode", paramType = "query", dataType = "string", required = true)
    })
    @ApiOperation(notes = "merchant query not all paid bill month", value = "商户查看未支付完毕账单月份")
    public BaseResponse getUnPaidBillMonth(@NotNull String token, @NotNull String merchantCode) {
        //super.hasPermissions();


        return null;
    }

    @GetMapping(value = "/detail")
    @ResponseBody
    @ApiOperation(notes = "merchant query bill detail info.", value = "商户查询账单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商户code", name = "merchantCode", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(value = "账单号", name = "billNo", paramType = "query", dataType = "string", required = true)
    })
    public BaseResponse detail(@NotNull String merchantCode,@NotNull String billNo) {
        //super.hasPermissions();
        MerchantBill bill = merchantBillService.getBillDetailByBillNo(merchantCode,billNo,false);
        return new PageResponse(Collections.singletonList(bill));
    }

    @PostMapping(value="refusal")
    @ResponseBody
    @ApiOperation(notes = "merchant query bill detail info.", value = "商户驳回账单")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商户code", name = "merchantCode", paramType = "body", dataType = "string", required = true),
            @ApiImplicitParam(value = "账单号", name = "billNo", paramType = "body", dataType = "string", required = true),
            @ApiImplicitParam(value = "备注", name = "remark", paramType = "body", dataType = "string", required = true)
    })
    public BaseResponse refusal(@NotNull String merchantCode,@NotNull String billNo,@NotNull String remark) {
        //TODO 当账单为逾期，待支付，部分支付时才可驳回。

        return null;
    }

}
