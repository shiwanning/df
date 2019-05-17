package com.tcg.mis.to.response;

import com.google.common.collect.Lists;
import com.tcg.mis.model.vo.ThirdPartyAccountDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ViewBankAcctTo {
	private Long rid;
	
    private Integer bankId;
	
    private String bankName;
	
    private String acctNumber;
    
    private Integer status;
	
    private Integer tpAcctId;
	
	private Integer vendorId;
	
	private String updateUser;
	
	private Date updateTime;
	
	private ThirdPartyAccountDetail tpInfo;
	
	private List<Offerd> offerds = Lists.newLinkedList();
	
	public String getAcctNumber() {
		return acctNumber;
	}

	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public ThirdPartyAccountDetail getTpInfo() {
		return tpInfo;
	}

	public void setTpInfo(ThirdPartyAccountDetail tpInfo) {
		this.tpInfo = tpInfo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTpAcctId() {
		return tpAcctId;
	}

	public void setTpAcctId(Integer tpAcctId) {
		this.tpAcctId = tpAcctId;
	}

	public List<Offerd> getOfferds() {
		return offerds;
	}

	public void setOfferds(List<Offerd> offerds) {
		this.offerds = offerds;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}


	public static final class Offerd {
		
	    private BigDecimal merchantRate;

	    private BigDecimal merchantMinAmount;

	    private BigDecimal merchantMaxAmount;

	    private BigDecimal bankFeeRate;

	    private BigDecimal bankMinFee;

	    private BigDecimal bankMaxFee;

	    private Integer status;
		
		private String bankType;

        private BigDecimal txMaxAmount;

        private BigDecimal txMinAmount;

        public BigDecimal getTxMinAmount() {
            return txMinAmount;
        }

        public void setTxMinAmount(BigDecimal txMinAmount) {
            this.txMinAmount = txMinAmount;
        }

        public BigDecimal getTxMaxAmount() {
            return txMaxAmount;
        }

        public void setTxMaxAmount(BigDecimal txMaxAmount) {
            this.txMaxAmount = txMaxAmount;
        }

        public BigDecimal getMerchantRate() {
			return merchantRate;
		}

		public void setMerchantRate(BigDecimal merchantRate) {
			this.merchantRate = merchantRate;
		}

		public BigDecimal getMerchantMinAmount() {
			return merchantMinAmount;
		}

		public void setMerchantMinAmount(BigDecimal merchantMinAmount) {
			this.merchantMinAmount = merchantMinAmount;
		}

		public BigDecimal getMerchantMaxAmount() {
			return merchantMaxAmount;
		}

		public void setMerchantMaxAmount(BigDecimal merchantMaxAmount) {
			this.merchantMaxAmount = merchantMaxAmount;
		}

		public BigDecimal getBankFeeRate() {
			return bankFeeRate;
		}

		public void setBankFeeRate(BigDecimal bankFeeRate) {
			this.bankFeeRate = bankFeeRate;
		}

		public BigDecimal getBankMinFee() {
			return bankMinFee;
		}

		public void setBankMinFee(BigDecimal bankMinFee) {
			this.bankMinFee = bankMinFee;
		}

		public BigDecimal getBankMaxFee() {
			return bankMaxFee;
		}

		public void setBankMaxFee(BigDecimal bankMaxFee) {
			this.bankMaxFee = bankMaxFee;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public String getBankType() {
			return bankType;
		}

		public void setBankType(String bankType) {
			this.bankType = bankType;
		}
		
	}
}
