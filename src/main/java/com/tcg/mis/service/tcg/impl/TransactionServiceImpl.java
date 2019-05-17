package com.tcg.mis.service.tcg.impl;

import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.mapper.AcsTransactionMapper;
import com.tcg.mis.model.TcgTransaction;
import com.tcg.mis.model.TcgTransactionSummary;
import com.tcg.mis.service.tcg.TransactionService;
import com.tcg.mis.to.condition.AcsTransactionCondition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);
	
	@Autowired
	private AcsTransactionMapper acsTransactionMapper;
	
	@Override
	public PageResponse<TcgTransaction, TcgTransactionSummary> getTcgTransactionList(AcsTransactionCondition condition) {

		PaginationAndOrderVO page = condition.generatePaginationAndOrderVO();
		List<TcgTransaction> list = acsTransactionMapper.findTcgTransaction(condition, page);
        TcgTransactionSummary footer = acsTransactionMapper.sumByTcgTransaction(condition);

		return new PageResponse<>(list, page, footer);
	}

    @Override
    public PageResponse<TcgTransaction, TcgTransactionSummary> getMerchantTransactionList(AcsTransactionCondition condition) {

        PaginationAndOrderVO page = condition.generatePaginationAndOrderVO();
        List<TcgTransaction> list = acsTransactionMapper.findMerchantTransaction(condition, page);
        TcgTransactionSummary footer = acsTransactionMapper.sumByMerchantTransaction(condition);

        return new PageResponse<>(list, page, footer);
    }

}
