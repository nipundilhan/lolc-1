package com.fusionx.lending.product.resources;

public class TaxEventDetailResponse {
	
	private Long id;
	private String code;
	private Long codeId;
	private String name;
	private String applicableOn;
	private String amountType;
	private Long applicationFrequency;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApplicableOn() {
		return applicableOn;
	}
	public void setApplicableOn(String applicableOn) {
		this.applicableOn = applicableOn;
	}
	public String getAmountType() {
		return amountType;
	}
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	public Long getCodeId() {
		return codeId;
	}
	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}
	public Long getApplicationFrequency() {
		return applicationFrequency;
	}
	public void setApplicationFrequency(Long applicationFrequency) {
		this.applicationFrequency = applicationFrequency;
	}
	

	
	

}
