package com.tcg.mis.service.merchant.impl;

import com.tcg.mis.mapper.MerchantBillDiscountLogMapper;
import com.tcg.mis.model.MerchantBillDiscountLog;
import com.tcg.mis.service.merchant.MerchantBillDiscountLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author
 * @date
 */
@Service
public class MerchantBillDiscountLogServiceImpl implements MerchantBillDiscountLogService {
    @Autowired
    private MerchantBillDiscountLogMapper merchantBillDiscountLogMapper;


    @Override
    public List<MerchantBillDiscountLog> getList(MerchantBillDiscountLog merchantBillDiscountLog) {
        return merchantBillDiscountLogMapper.getList(merchantBillDiscountLog);
    }


    @Override
    public int insert(MerchantBillDiscountLog merchantBillDiscountLog) {
        return merchantBillDiscountLogMapper.insert(merchantBillDiscountLog);
    }


    @Override
    public List<MerchantBillDiscountLog> getListByBillId(String billMasterId) {
        MerchantBillDiscountLog log = new MerchantBillDiscountLog();
        log.setBillMasterId(new BigDecimal(billMasterId));
        return getList(log);
    }
}

