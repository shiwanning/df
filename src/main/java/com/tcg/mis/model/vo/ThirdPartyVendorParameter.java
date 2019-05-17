package com.tcg.mis.model.vo;

public class ThirdPartyVendorParameter {
	
	public static final String PARAM_TYPE_STRING = "S";
	public static final String PARAM_TYPE_FILE = "F";

	private Long id;
	private String paramKeyName;
	private String paramDesc;
	private String paramType;
	private ThirdPartyVendor vendor;
	private String value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParamKeyName() {
		return paramKeyName;
	}

	public void setParamKeyName(String paramKeyName) {
		this.paramKeyName = paramKeyName;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public ThirdPartyVendor getVendor() {
		return vendor;
	}

	public void setVendor(ThirdPartyVendor vendor) {
		this.vendor = vendor;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ThirdPartyVendorParameter other = (ThirdPartyVendorParameter) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
