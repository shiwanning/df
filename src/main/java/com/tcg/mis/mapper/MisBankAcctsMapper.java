package com.tcg.mis.mapper;

import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.model.MisBankAccts;
import com.tcg.mis.model.MisBankAcctsExample;
import com.tcg.mis.model.vo.BankAccountInfo;
import com.tcg.mis.to.condition.BankAccountCondition;
import com.tcg.mis.to.response.BankAccountTo;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MisBankAcctsMapper {
    long countByExample(MisBankAcctsExample example);

    int deleteByExample(MisBankAcctsExample example);

    int deleteByPrimaryKey(BigDecimal rid);

    int insert(MisBankAccts record);

    int insertSelective(MisBankAccts record);

    List<MisBankAccts> selectByExample(MisBankAcctsExample example);

    MisBankAccts selectByPrimaryKey(Long rid);

    int updateByExampleSelective(@Param("record") MisBankAccts record, @Param("example") MisBankAcctsExample example);

    int updateByExample(@Param("record") MisBankAccts record, @Param("example") MisBankAcctsExample example);

    int updateByPrimaryKeySelective(MisBankAccts record);

    int updateByPrimaryKey(MisBankAccts record);

    List<BankAccountTo> findByCondition(@Param("condition") BankAccountCondition condition, @Param("page") PaginationAndOrderVO page);

    List<BankAccountInfo> findBankAccountList(@Param("condition") BankAccountCondition condition);

    List<MisBankAccts> findAccsByPaymentType(@Param("paymentType") String paymentType);

	List<MisBankAccts> findByBankId(@Param("bankId") Integer bankId);

}