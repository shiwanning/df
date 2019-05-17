package com.tcg.mis.mapper;

import com.tcg.mis.common.page.PaginationAndOrderVO;
import com.tcg.mis.model.vo.AcsAccount;
import com.tcg.mis.model.vo.LobbyAccountType;
import com.tcg.mis.model.vo.Merchant;
import com.tcg.mis.model.vo.PrepayTemplateDetailTO;
import com.tcg.mis.model.vo.TemplateProductWallet;
import com.tcg.mis.to.condition.MerchantTemplateCondition;
import com.tcg.mis.to.response.MerchantCashPledgeTo;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MerchantMapper {

    List<TemplateProductWallet> findTemplateProductWallet(@Param("merchantCode")String merchantCode);

    AcsAccount findAcsAccount(@Param("merchantCode") String merchantCode, @Param("accountTypeId") Integer accountTypeId);

    Merchant findMerchant(@Param("merchantCode") String merchantCode);

    List<LobbyAccountType> findLobbyAccountTypes();

    List<PrepayTemplateDetailTO> findPrepayTemplateDetails(@Param("condition") MerchantTemplateCondition condition);

	void updateVirtualCashPledge(@Param("merchantCode") String merchantCode, @Param("virtualCashPledge") BigDecimal virtualCashPledge);

	void updateVirtualCashPledgeAndLeverMultiplier(@Param("merchantCode") String merchantCode, @Param("virtualCashPledge") BigDecimal virtualCashPledge,
			@Param("leverMultiplier") BigDecimal leverMultiplier);

	List<MerchantCashPledgeTo> findMerchantCashPledgeTo(@Param("merchantCode") String merchantCode, @Param("page") PaginationAndOrderVO page);

}