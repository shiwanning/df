package com.tcg.mis.service.merchant.impl;

import com.tcg.mis.mapper.MerchantPointSummaryMapper;
import com.tcg.mis.model.MerchantPointSummary;
import com.tcg.mis.service.merchant.MerchantPointSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @date
 */
@Service
public class MerchantPointSummaryServiceImpl implements MerchantPointSummaryService {
    @Autowired
    private MerchantPointSummaryMapper merchantPointSummaryMapper;


    @Override
    public List<MerchantPointSummary> getList(MerchantPointSummary merchantPointSummary) {
        return merchantPointSummaryMapper.getList(merchantPointSummary);
    }


    @Override
    public int insert(MerchantPointSummary merchantPointSummary) {
        return merchantPointSummaryMapper.insert(merchantPointSummary);
    }


    @Override
    public int update(MerchantPointSummary merchantPointSummary) {
        return merchantPointSummaryMapper.update(merchantPointSummary);
    }


}

