# 备注

如果有自行方便测试的代码，请放到 src/test/ignore 底下
例如以下代码就是自行测试，但不方便放到版控的

``` java
package com.tcg.ods.be.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcg.common.log.TcgLogFactory;
import com.tcg.common.response.SimplePageResponse;
import com.tcg.ods.be.service.mcs.McsService;
import com.tcg.ods.be.test.IntegrationTest;
import com.tcg.ods.be.to.condition.BaseCondition;
import com.tcg.ods.be.to.response.LabelInfoTO;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
public class McsServiceTest {

    private static final Logger LOGGER = TcgLogFactory.getLogger(McsServiceTest.class);
    
    @Autowired
    private McsService mcsService;
    
    @Autowired
    private ObjectMapper mapper;
    
    @Test
    public void test() throws JsonProcessingException {
        
        BaseCondition condition = new BaseCondition();
        condition.setMerchantCode("2000cai");
        //condition.setSortColumn(sortColumn);
        // condition.setSortType(sortType);
        condition.setPage(1);
        condition.setSize(3);
        //condition.setNeedPagination(isNeedPagination);
        SimplePageResponse<LabelInfoTO> model = mcsService.getMcsLebelInfo(condition);
        LOGGER.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model));
    }
}
```

# sonarQube

windows 请直接执行 `sonarqube.bat`

需要修改 jdk8 path.