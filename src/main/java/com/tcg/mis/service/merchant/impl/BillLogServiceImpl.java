package com.tcg.mis.service.merchant.impl;

import java.util.Collections;
import java.util.List;

import com.tcg.mis.service.merchant.BillLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tcg.mis.mapper.BillLogMapper;
import com.tcg.mis.model.BillLog;


/**
 * @author
 * @date
 */
@Service
public class BillLogServiceImpl implements BillLogService {
    @Autowired
    private BillLogMapper billLogMapper;


    @Override
    public List<BillLog> getList(BillLog billLog) {
        return billLogMapper.getList(billLog);
    }


    @Override
    public int insert(BillLog billLog) {
        return insert(Collections.singletonList(billLog));
    }


    @Override
    public int update(BillLog billLog) {
        return billLogMapper.update(billLog);
    }

    @Override
    public int insert(List<BillLog> logs) {
        //TODO 批量插入
        return 0;
    }
}

