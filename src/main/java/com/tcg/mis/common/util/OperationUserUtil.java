package com.tcg.mis.common.util;

import com.tcg.mis.service.subsystem.OSService;
import com.tcg.mis.to.OperatorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * com.tcg.mis.common.util
 *
 * @author lyndon.j
 * @version 1.0
 * @date 2019/5/16 15:55
 */
@Component
public class OperationUserUtil {

    private static OSService ossService;

    public static String getOperatorName() {
        String token = ServletUtil.getRequest().getHeader("Authorization");
        OperatorInfo userInfo = ossService.getCurrentUser(token);
        return userInfo.getOperatorName();
    }

    @Autowired
    public void setOssService(OSService ossService) {
        OperationUserUtil.ossService = ossService;
    }
}
