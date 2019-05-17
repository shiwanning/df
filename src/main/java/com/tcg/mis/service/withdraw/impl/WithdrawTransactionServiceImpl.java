package com.tcg.mis.service.withdraw.impl;

import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.controller.withdraw.WithdrawTransactionController;
import com.tcg.mis.mapper.MisWithdrawTransactionMapper;
import com.tcg.mis.service.withdraw.WithdrawTransactionService;
import com.tcg.mis.to.condition.WithdrawTransactionCondition;
import com.tcg.mis.to.response.MisWithdrawTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WithdrawTransactionServiceImpl implements WithdrawTransactionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawTransactionController.class);
	
	@Autowired
	private MisWithdrawTransactionMapper misWithdrawTransactionMapper;
	
	@Override
	public PageResponse<MisWithdrawTo, MisWithdrawTo> getMisWithdrawTransactionList(WithdrawTransactionCondition condition) {
		
		PaginationAndOrderVO page = condition.generatePaginationAndOrderVO();
		List<MisWithdrawTo> list = misWithdrawTransactionMapper.findByCondition(condition, page);
        MisWithdrawTo footer = misWithdrawTransactionMapper.sumByCondition(condition);

		return new PageResponse<>(list, page, footer);
	}

}
