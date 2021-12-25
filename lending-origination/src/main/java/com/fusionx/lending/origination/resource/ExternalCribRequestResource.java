package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ExternalCribRequestResource {
	
//	@Pattern(regexp = "^$|[0-9]+", message = "{pidtId.pattern}")
//    private String pidtId;
//  
//    @Pattern(regexp = "^$|[0-9]+", message = "{pidtId.pattern}")
//    private String ppidtId;
//	
//	@NotBlank(message = "{common.not-null}")
//	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
//	private String identificationTypeId;
	
//	@NotBlank(message = "{common.not-null}")
//	private String identificationtype;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "NEW_NIC|OLD_NIC_V|OLD_NIC_X|OTHER", message = "{common-nic-types.pattern}")
	private String nicType;

	@NotBlank(message = "{common.not-null}")
	//@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String identificationNumber;


//	public String getIdentificationtype() {
//		return identificationtype;
//	}
//
//	public void setIdentificationtype(String identificationtype) {
//		this.identificationtype = identificationtype;
//	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getNicType() {
		return nicType;
	}

	public void setNicType(String nicType) {
		this.nicType = nicType;
	}
	
	

//	public String getIdentificationTypeId() {
//		return identificationTypeId;
//	}
//
//	public void setIdentificationTypeId(String identificationTypeId) {
//		this.identificationTypeId = identificationTypeId;
//	}
//
//	public String getPidtId() {
//		return pidtId;
//	}
//
//	public void setPidtId(String pidtId) {
//		this.pidtId = pidtId;
//	}
//
//	public String getPpidtId() {
//		return ppidtId;
//	}
//
//	public void setPpidtId(String ppidtId) {
//		this.ppidtId = ppidtId;
//	}

	
}
