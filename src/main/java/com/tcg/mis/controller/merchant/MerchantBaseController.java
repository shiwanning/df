package com.tcg.mis.controller.merchant;

import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.util.ServletUtil;
import com.tcg.mis.service.subsystem.OSService;
import com.tcg.mis.to.OperatorInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * com.tcg.mis.controller.merchant
 *
 * @author lyndon.j
 * @version 1.0
 * @date 2019/5/15 14:11
 */
public class MerchantBaseController {

    @Resource
    private OSService ossService;
    protected void hasPermissions() {
        String token = ServletUtil.getRequest().getHeader("Authorization");
        String merchantCode = ServletUtil.getParameter("merchantCode");
        OperatorInfo userInfo = ossService.getCurrentUser(token);
        List<String> merchantCodes = userInfo.getMerchants();
        if (!merchantCodes.contains(merchantCode)) {
            throw new MisBaseException(ErrorCode.SYS_ERR, "You don't has permissions query this merchant.");
        }
    }


}
