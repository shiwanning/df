package com.tcg.mis.controller.merchant;

import com.tcg.mis.common.response.BaseResponse;
import com.tcg.mis.to.request.CamelPageAndSortTo;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.tcg.mis.controller.merchant
 *
 * @author lyndon.j
 * @version 1.0
 * @date 2019/5/9 17:54
 */
@Validated
@RestController
@RequestMapping("/merchant/point")
@ResponseBody
@Api(value = "Merchant Point",tags = "商户分数报表")
public class PointController {

    //TODO 所有操作跟用户绑定，需要先获取用户的品牌


    @GetMapping("/buyRecord")
    public BaseResponse buyRecord() {

        return null;
    }

    @GetMapping("/usedRecord")
    @ApiOperation(value = "商户分数消耗报表",tags = "Used Record")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantCode",value = "",paramType = "",dataType = "",required = true),
            @ApiImplicitParam(name = "gameCategory",value = "",paramType = "",dataType = "",required = true),
            @ApiImplicitParam(name = "currency",value = "",paramType = "",dataType = "",required = true),
            @ApiImplicitParam(name = "startDate",value = "",paramType = "",dataType = "",required = true),
            @ApiImplicitParam(name = "endDate",value = "",paramType = "",dataType = "",required = true),
            @ApiImplicitParam(name = "camelPageAndSortTo",value = "",paramType = "",dataType = "",required = true),
    })
    public BaseResponse usedRecord(@Validated CamelPageAndSortTo pageAndSortTo) {

        return null;
    }
}
