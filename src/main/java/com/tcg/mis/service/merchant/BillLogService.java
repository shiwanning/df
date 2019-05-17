package com.tcg.mis.service.merchant;

import com.tcg.mis.model.BillLog;

import java.util.List;


public interface BillLogService {

    List<BillLog> getList(BillLog billLog);

    int insert(BillLog billLog);

    int insert(List<BillLog> logs);

    int update(BillLog billLog);

}
