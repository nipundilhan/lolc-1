package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseGuarantorIdentificationListResource {

	private Long identificationType;
	
	private String identificationTypeDesc;
	
	private String pidtIdentificationNo;
	
	private String ppidtId;

	public String getPidtIdentificationNo() {
		return pidtIdentificationNo;
	}

	public void setPidtIdentificationNo(String pidtIdentificationNo) {
		this.pidtIdentificationNo = pidtIdentificationNo;
	}

	public String getPpidtId() {
		return ppidtId;
	}

	public void setPpidtId(String ppidtId) {
		this.ppidtId = ppidtId;
	}

	public Long getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(Long identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationTypeDesc() {
		return identificationTypeDesc;
	}

	public void setIdentificationTypeDesc(String identificationTypeDesc) {
		this.identificationTypeDesc = identificationTypeDesc;
	}
	
	

}
