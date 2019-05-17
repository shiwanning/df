package com.tcg.mis.mapper;

import com.tcg.mis.model.MerchantPointSummary;

import java.util.List;


public interface MerchantPointSummaryMapper {

    List<MerchantPointSummary> getList(MerchantPointSummary merchantPointSummary);


    int insert(MerchantPointSummary merchantPointSummary);

    int update(MerchantPointSummary merchantPointSummary);


    int delete(String[] ids);

}