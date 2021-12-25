package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PersonContactRequestResource {

	@JsonProperty("sulpId")
	@Pattern(regexp = "^$|[0-9]+", message = "{sulpId.pattern}")
	private String sulpId;
	
	@JsonProperty("psulpId")
	@Pattern(regexp = "^$|[0-9]+", message = "{psulpId.pattern}")
	private String psulpId;
	
	@JsonProperty("pconId")
	@Pattern(regexp = "^$|[0-9]+", message = "{pconId.pattern}")
	private String pconId;
	
	@JsonProperty("pconContactTypeId")
	@NotBlank(message = "{pconContactTypeId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{pconContactTypeId.pattern}")
	private String pconContactTypeId;
	private String pconContactTypeDesc;
	
	@JsonProperty("pconValue")
	@NotBlank(message = "{pconValue.not-null}")
	@Size(max = 255, message = "{pconValue.size}")
	private String pconValue;
	
	@JsonProperty("pconStatus")
	@NotBlank(message = "{pconStatus.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{pconStatus.pattern}")
	private String pconStatus;
	
	@JsonProperty("pconAttribute1")
	@Size(max = 255, message = "{perAttribute.size}")
	private String pconAttribute1;
	
	@JsonProperty("pconAttribute2")
	@Size(max = 255, message = "{perAttribute.size}")
	private String pconAttribute2;
	
	@JsonProperty("pconAttribute3")
	@Size(max = 255, message = "{perAttribute.size}")
	private String pconAttribute3;
	
	@JsonProperty("pconAttribute4")
	@Size(max = 255, message = "{perAttribute.size}")
	private String pconAttribute4;
	
	public PersonContactRequestResource() {
		super();
	}

	public String getPconId() {
		return pconId;
	}

	public void setPconId(String pconId) {
		this.pconId = pconId;
	}

	public String getPconContactTypeId() {
		return pconContactTypeId;
	}

	public void setPconContactTypeId(String pconContactTypeId) {
		this.pconContactTypeId = pconContactTypeId;
	}

	public String getPconContactTypeDesc() {
		return pconContactTypeDesc;
	}

	public void setPconContactTypeDesc(String pconContactTypeDesc) {
		this.pconContactTypeDesc = pconContactTypeDesc;
	}

	public String getPconValue() {
		return pconValue;
	}

	public void setPconValue(String pconValue) {
		this.pconValue = pconValue;
	}

	public String getPconStatus() {
		return pconStatus;
	}

	public void setPconStatus(String pconStatus) {
		this.pconStatus = pconStatus;
	}

	public String getPconAttribute1() {
		return pconAttribute1;
	}

	public void setPconAttribute1(String pconAttribute1) {
		this.pconAttribute1 = pconAttribute1;
	}

	public String getPconAttribute2() {
		return pconAttribute2;
	}

	public void setPconAttribute2(String pconAttribute2) {
		this.pconAttribute2 = pconAttribute2;
	}

	public String getPconAttribute3() {
		return pconAttribute3;
	}

	public void setPconAttribute3(String pconAttribute3) {
		this.pconAttribute3 = pconAttribute3;
	}

	public String getPconAttribute4() {
		return pconAttribute4;
	}

	public void setPconAttribute4(String pconAttribute4) {
		this.pconAttribute4 = pconAttribute4;
	}

	public String getSulpId() {
		return sulpId;
	}

	public void setSulpId(String sulpId) {
		this.sulpId = sulpId;
	}

	public String getPsulpId() {
		return psulpId;
	}

	public void setPsulpId(String psulpId) {
		this.psulpId = psulpId;
	}
}
