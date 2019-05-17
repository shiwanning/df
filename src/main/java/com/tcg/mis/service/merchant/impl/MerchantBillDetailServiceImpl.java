package com.tcg.mis.service.merchant.impl;

import com.tcg.mis.mapper.MerchantBillDetailMapper;
import com.tcg.mis.model.MerchantBillDetail;
import com.tcg.mis.service.merchant.MerchantBillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author
 * @date
 */
@Service
public class MerchantBillDetailServiceImpl implements MerchantBillDetailService {
    @Autowired
    private MerchantBillDetailMapper merchantBillDetailMapper;

    @Override
    public List<MerchantBillDetail> getList(String billMasterId) {
        return merchantBillDetailMapper.getList(billMasterId);
    }

    @Override
    public int insert(MerchantBillDetail merchantBillDetail) {
        return merchantBillDetailMapper.insert(merchantBillDetail);
    }


    @Override
    public int update(MerchantBillDetail merchantBillDetail) {
        return merchantBillDetailMapper.update(Collections.singletonList(merchantBillDetail));
    }

    public void doSomething () {
        //TODO 写日志
        //TODO update详情
        //TODO COPY 主账单 billBackup
        //TODO update 主账单为弃用
        //TODO 从新计算Detail详情
        //TODO 更新billBackup对象数值，
        //TODO 修改状态为待审核，
        //TODO 插入主表
    }

    @Override
    public int updateDetail(MerchantBillDetail merchantBillDetail) {
        this.updateDetails(Collections.singletonList(merchantBillDetail));
        return 0;
    }


    @Override
    public int updateDetails(List<MerchantBillDetail> details) {
        merchantBillDetailMapper.update(details);
        return 0;
    }
}

