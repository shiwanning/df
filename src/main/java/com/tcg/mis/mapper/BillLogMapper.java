package com.tcg.mis.mapper;

import com.tcg.mis.model.BillLog;

import java.util.List;


public interface BillLogMapper {

    List<BillLog> getList(BillLog billLog);

    int insert(BillLog billLog);

    int update(BillLog billLog);

}