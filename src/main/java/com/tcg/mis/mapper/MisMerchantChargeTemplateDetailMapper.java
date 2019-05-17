package com.tcg.mis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcg.mis.model.MisMerchantChargeTemplateDetail;

public interface MisMerchantChargeTemplateDetailMapper {
	
    void insert(MisMerchantChargeTemplateDetail entity);
    void update(MisMerchantChargeTemplateDetail entity);
    
     List<MisMerchantChargeTemplateDetail> findProductByMerchantCode(@Param("merchantCode") String merchantCode);

     List<MisMerchantChargeTemplateDetail> findByDetailTypeAndCode(@Param("detailType") int detailType, @Param("code") String code);
	 
     List<MisMerchantChargeTemplateDetail> findByTemplateRid(@Param("templateRid") Long templateRid);
     
     MisMerchantChargeTemplateDetail findFirstByTemplateRid(@Param("templateRid") Long templateRid);
     
	 void deleteByTemplateRid(@Param("templateRid") Long rid);
}
