package com.tcg.mis.service.merchant.impl;

import com.google.common.collect.Lists;
import com.tcg.mis.common.constant.BillStatus;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.util.DateUtil;
import com.tcg.mis.common.util.OperationUserUtil;
import com.tcg.mis.mapper.MerchantBillDetailMapper;
import com.tcg.mis.mapper.MerchantBillMapper;
import com.tcg.mis.mapper.MisRechargeTransactionMapper;
import com.tcg.mis.model.MerchantBill;
import com.tcg.mis.model.MerchantBillDetail;
import com.tcg.mis.service.merchant.MerchantBillService;
import com.tcg.mis.to.condition.RechargeTransactionCondition;
import com.tcg.mis.to.request.CamelPageAndSortTo;
import com.tcg.mis.to.response.MisRechargeTo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author lyndon.j
 * @date
 */
@Service
public class MerchantBillServiceImpl implements MerchantBillService {

    private static final List<Integer> merchantBillStatus = Lists.newArrayList(BillStatus.ALL_PAID.getCode(),
                                                                        BillStatus.PARTIAL_PAID.getCode(),
                                                                        BillStatus.UNPAID.getCode(),
                                                                        BillStatus.OVERDUE.getCode());
    @Autowired
    private MerchantBillMapper merchantBillMapper;
    @Autowired
    private MerchantBillDetailMapper merchantBillDetailMapper;
    @Autowired
    private MisRechargeTransactionMapper misRechargeTransactionMapper;


    @Override
    public List<MerchantBill> getList(MerchantBill merchantBill) {
        return null;
    }

    @Override
    public List<MerchantBill> merchantGetBillList(String merchantCode, Integer year, Integer month, Integer status) {
        List<Integer> statusList = this.merchantBillStatus;
        //财务能看的订单状态跟商户不一样
        if (null != status && merchantBillStatus.contains(status)) {
            statusList = Collections.singletonList(status);
        }
        return this.getBillList(merchantCode,getBillDate(year,month), statusList,null);
    }

    @Override
    public List<MerchantBill> financeGetBillList(String merchantCode, Integer year, Integer month, Integer status) {
        return this.getBillList(merchantCode,getBillDate(year,month), null != status ? Collections.singletonList(status) : null,null);
    }

    private List<MerchantBill> getBillList(String merchantCode, Date billDate, List<Integer> status, String billNo) {
        List<MerchantBill> bills = merchantBillMapper.getList(merchantCode,billDate,status,billNo);

        return bills;
    }

    @Override
    public int insert(MerchantBill merchantBill) {
        return merchantBillMapper.insert(merchantBill);
    }


    @Override
    public int update(MerchantBill merchantBill) {
        return merchantBillMapper.update(merchantBill);
    }


    @Override
    public MerchantBill getBillDetailByBillNo(String merchantCode, String billNo,boolean isFinance) {

        return getMerchantBillAndDetail(merchantCode, billNo,isFinance);
    }

    @Override
    public void discount(String billNo, Double amount) {
        String operatorName = OperationUserUtil.getOperatorName();
        //TODO 插入LOG
        //TODO 插入充值记录，
        //TODO 更新记录
    }

    @Override
    public void badDebt(String billNo) {
        String operatorName = OperationUserUtil.getOperatorName();
        //TODO 插入LOG
        //TODO 插入充值记录，
        //TODO 更新记录
    }

    private MerchantBill getMerchantBillAndDetail(String merchantCode, String billNo,boolean isFinance) {

        List<MerchantBill> bills = getBillList(merchantCode, billNo, isFinance);

        if (CollectionUtils.isEmpty(bills)) {
            throw new MisBaseException(ErrorCode.DATA_NOT_FOUND, "Data not found.");
        }

        MerchantBill bill = bills.iterator().next();

        List<MisRechargeTo> paymentInfoSuccess = getPaymentInfoSuccess(bill);

        List<MerchantBillDetail> details = merchantBillDetailMapper.getList(bill.getBillMasterId().toString());

        bill.setDetails(details);

        bill.setRechargeTos(paymentInfoSuccess);

        return bill;
    }

    @Override
    public  MerchantBill getBill(String merchantCode, String billNo, boolean isFinance) {
        List<MerchantBill>list = getBillList(merchantCode,billNo,isFinance);
        if (CollectionUtils.isEmpty(list)) {
            throw new MisBaseException(ErrorCode.DATA_NOT_FOUND, "Data not found.");
        }
        return list.iterator().next();
    }


    private  List<MerchantBill> getBillList(String merchantCode, String billNo, boolean isFinance) {
        //财务能看的订单状态跟商户不一样
        if (isFinance) {
            return this.getBillList(merchantCode,null,null,billNo);
        }

        return this.getBillList(merchantCode, null, this.merchantBillStatus, billNo);
    }

    private List<MisRechargeTo> getPaymentInfoSuccess(MerchantBill bill){
        CamelPageAndSortTo pageAndSortTo = new CamelPageAndSortTo();
        pageAndSortTo
                .withDefaultSortColumn("createTime")
                .withDefaultSortType("desc")
                .pageable(false);
        RechargeTransactionCondition condition = new RechargeTransactionCondition();
        condition.setRechargeType(2);
        condition.setBillingId(bill.getBillMasterId());

        return misRechargeTransactionMapper.findByCondition(condition, condition.generatePaginationAndOrderVO());
    }
    private Date getBillDate(Integer year, Integer month) {
        Date billDate = null;
        if (null != month || null != year) {
            billDate= DateUtil.getDate(year,month);
        }
        return billDate;
    }

}

