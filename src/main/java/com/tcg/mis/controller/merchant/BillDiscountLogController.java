/*
 * Copyright(c) 2019 WishLand All rights reserved.
 * distributed with this file and available online at
 */
package com.tcg.mis.controller.merchant;


import com.tcg.mis.common.response.BaseResponse;
import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.model.MerchantBillDiscountLog;
import com.tcg.mis.service.merchant.MerchantBillDiscountLogService;
import com.tcg.mis.to.request.CamelPageAndSortTo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @version 1.0
 * @author
 */
@RestController
@RequestMapping("/merchant/billDiscount")
@Validated
@Api(tags = "merchant bill discount,bad debt list", description = "账单减免坏账记录")
public class BillDiscountLogController  {

    @Resource
    private MerchantBillDiscountLogService merchantBillDiscountLogService;

	@GetMapping(value = "/list")
	@ResponseBody
	@ApiOperation(notes = "merchant query bill list", value = "商户查看账单列表")
	@ApiImplicitParams({
			@ApiImplicitParam(value = "订单Id", name = "billMasterId", paramType = "query", dataType = "string", required = true),
	})
	public BaseResponse list(@NotNull String billMasterId, @Validated CamelPageAndSortTo page) {
		List<MerchantBillDiscountLog> result = merchantBillDiscountLogService.getListByBillId(billMasterId);
		return new PageResponse(result);
	}
}
