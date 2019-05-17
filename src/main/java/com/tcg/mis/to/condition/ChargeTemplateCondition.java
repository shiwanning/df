package com.tcg.mis.to.condition;

public class ChargeTemplateCondition extends OperatorBaseCondition {
	
	private Integer templateType;
	private String templateName;
	
	public Integer getTemplateType() {
		return templateType;
	}
	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
}
