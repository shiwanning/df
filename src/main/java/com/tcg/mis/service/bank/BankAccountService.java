package com.tcg.mis.service.bank;

import java.util.List;

import com.tcg.mis.common.response.PageResponse;
import com.tcg.mis.to.condition.BankAccountCondition;
import com.tcg.mis.to.request.SaveBankAcctTo;
import com.tcg.mis.to.response.BankAccountTo;
import com.tcg.mis.to.response.ViewBankAcctTo;

public interface BankAccountService {

	PageResponse<BankAccountTo, BankAccountTo> getBankAccountList(BankAccountCondition condition);

	ViewBankAcctTo getBankAccount(Long acctId);
	
	void createBankAcct(SaveBankAcctTo saveBankAcctTo, String operatorName);

	void updateBankAcct(SaveBankAcctTo saveBankAcctTo, String operatorName);

}
