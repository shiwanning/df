package com.tcg.mis.to.response;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class MerchantCreditDetailTo {

    @NotNull
    private String merchantCode;
    @NotNull
    private LeverMultiplier leverMultiplier;
    @NotNull
    private BigDecimal virtualCashPledge;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public LeverMultiplier getLeverMultiplier() {
        return leverMultiplier;
    }

    public void setLeverMultiplier(LeverMultiplier leverMultiplier) {
        this.leverMultiplier = leverMultiplier;
    }

    public BigDecimal getVirtualCashPledge() {
        return virtualCashPledge;
    }

    public void setVirtualCashPledge(BigDecimal virtualCashPledge) {
        this.virtualCashPledge = virtualCashPledge;
    }
    
    public static class LeverMultiplier {
    	
    	private boolean approved;
    	private BigDecimal currentValue;
    	private BigDecimal applyValue;
    	
		public boolean isApproved() {
			return approved;
		}
		public void setApproved(boolean approved) {
			this.approved = approved;
		}
		public BigDecimal getCurrentValue() {
			return currentValue;
		}
		public void setCurrentValue(BigDecimal currentValue) {
			this.currentValue = currentValue;
		}
		public BigDecimal getApplyValue() {
			return applyValue;
		}
		public void setApplyValue(BigDecimal applyValue) {
			this.applyValue = applyValue;
		}
    }
}
