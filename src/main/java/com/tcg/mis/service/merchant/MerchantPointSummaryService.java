package com.tcg.mis.service.merchant;

import com.tcg.mis.model.MerchantPointSummary;

import java.util.List;

public interface MerchantPointSummaryService {

    List<MerchantPointSummary> getList(MerchantPointSummary merchantPointSummary);


    int insert(MerchantPointSummary merchantPointSummary);


    int update(MerchantPointSummary merchantPointSummary);

}
