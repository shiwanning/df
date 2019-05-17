package com.tcg.mis.to.request;

import com.google.common.collect.Maps;

import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

public class SaveBankAcctTo {

	private Long rid;
	
	@NotNull
    private String acctNumber;
	
	@NotNull
    private Integer bankId;
	
	@NotNull
    private String bankName;
	
	@NotNull
    private Integer status;
	
	@NotNull
	private Integer vendorId;
	
	@NotEmpty
	private List<Offerd> offerds;
	
	private Map<Long, String> params = Maps.newHashMap();
	
	private Map<Long, FileResource> fileResource = Maps.newHashMap();
	
	public String getAcctNumber() {
		return acctNumber;
	}

	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}

	public void setFileResource(Map<Long, FileResource> fileResource) {
		this.fileResource = fileResource;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public List<Offerd> getOfferds() {
		return offerds;
	}

	public void setOfferds(List<Offerd> offerds) {
		this.offerds = offerds;
	}

	public Map<Long, String> getParams() {
		return params;
	}

	public void setParams(Map<Long, String> params) {
		this.params = params;
	}
	
	public Map<Long, FileResource> getFileResource() {
		return fileResource;
	}

	public void setFileParams(Map<Long, FileResource> fileResource) {
		this.fileResource = fileResource;
	}

	public static final class Offerd {
		
		@NotNull
		private BigDecimal merchantRate;

		@NotNull
	    private BigDecimal merchantMinAmount;

		@NotNull
	    private BigDecimal merchantMaxAmount;

		@NotNull
	    private BigDecimal bankFeeRate;

		@NotNull
	    private BigDecimal bankMinFee;

		@NotNull
	    private BigDecimal bankMaxFee;

		@NotNull
	    private String riskLevel;

		@NotNull
	    private Integer status;
		
		@NotNull
		private String bankType;

        @NotNull
		private BigDecimal txMaxAmount;

        @NotNull
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

        public String getBankType() {
			return bankType;
		}

		public void setBankType(String bankType) {
			this.bankType = bankType;
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

		public String getRiskLevel() {
			return riskLevel;
		}

		public void setRiskLevel(String riskLevel) {
			this.riskLevel = riskLevel;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}
		
	}
	
}
