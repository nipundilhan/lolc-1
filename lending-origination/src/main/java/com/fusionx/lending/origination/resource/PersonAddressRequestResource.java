package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PersonAddressRequestResource {

	@JsonProperty("sulpId")
	@Pattern(regexp = "^$|[0-9]+", message = "{sulpId.pattern}")
	private String sulpId;
	
	@JsonProperty("psulpId")
	@Pattern(regexp = "^$|[0-9]+", message = "{psulpId.pattern}")
	private String psulpId;
	
	@JsonProperty("paddId")
	@Pattern(regexp = "^$|[0-9]+", message = "{paddId.pattern}")
	private String paddId;
	
	@JsonProperty("paddAddressTypeCommonListId")
	@NotBlank(message = "{paddAddressTypeCommonListId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{paddAddressTypeCommonListId.pattern}")
	private String paddAddressTypeCommonListId;
	private String paddAddressTypeDesc;
	
	@JsonProperty("paddAddress01")
	@NotBlank(message = "{paddAddress.not-null}")
	@Size(max = 255, message = "{paddAddress.size}")
	private String paddAddress01;
	
	@JsonProperty("paddAddress02")
	@Size(max = 255, message = "{paddAddress.size}")
	private String paddAddress02;
	
	@JsonProperty("paddAddress03")
	@Size(max = 255, message = "{paddAddress.size}")
	private String paddAddress03;
	
	@JsonProperty("paddAddress04")
	@Size(max = 255, message = "{paddAddress.size}")
	private String paddAddress04;
	
	@JsonProperty("paddAddressGeoLevelId")
	@Pattern(regexp = "^$|[0-9]+", message = "{paddAddressGeoLevelId.pattern}")
	private String paddAddressGeoLevelId;
	private String paddAddressGeoLevelDesc;
	
	@JsonProperty("paddAddressCountryId")
	@Pattern(regexp = "^$|[0-9]+", message = "{paddAddressCountryId.pattern}")
	private String paddAddressCountryId;
	
	private String paddAddressCountryDesc;
	
	@JsonProperty("paddAddressPostalCode")
	@Size(max = 255, message = "{paddAddressPostalCode.size}")
	private String paddAddressPostalCode;
	
	@JsonProperty("paddStatus")
	@NotBlank(message = "{paddStatus.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{paddStatus.pattern}")
	private String paddStatus;
	
	@JsonProperty("paddAttribute1")
	@Size(max = 255, message = "{perAttribute.size}")
	private String paddAttribute1;
	
	@JsonProperty("paddAttribute2")
	@Size(max = 255, message = "{perAttribute.size}")
	private String paddAttribute2;
	
	@JsonProperty("paddAttribute3")
	@Size(max = 255, message = "{perAttribute.size}")
	private String paddAttribute3;
	
	@JsonProperty("paddAttribute4")
	@Size(max = 255, message = "{perAttribute.size}")
	private String paddAttribute4;
	
	@JsonProperty("paddAttribute5")
	@Size(max = 255, message = "{perAttribute.size}")
	private String paddAttribute5;

	public PersonAddressRequestResource() {
		super();
	}

	public String getPaddId() {
		return paddId;
	}

	public void setPaddId(String paddId) {
		this.paddId = paddId;
	}

	public String getPaddAddressTypeCommonListId() {
		return paddAddressTypeCommonListId;
	}

	public void setPaddAddressTypeCommonListId(String paddAddressTypeCommonListId) {
		this.paddAddressTypeCommonListId = paddAddressTypeCommonListId;
	}

	public String getPaddAddressTypeDesc() {
		return paddAddressTypeDesc;
	}

	public void setPaddAddressTypeDesc(String paddAddressTypeDesc) {
		this.paddAddressTypeDesc = paddAddressTypeDesc;
	}

	public String getPaddAddress01() {
		return paddAddress01;
	}

	public void setPaddAddress01(String paddAddress01) {
		this.paddAddress01 = paddAddress01;
	}

	public String getPaddAddress02() {
		return paddAddress02;
	}

	public void setPaddAddress02(String paddAddress02) {
		this.paddAddress02 = paddAddress02;
	}

	public String getPaddAddress03() {
		return paddAddress03;
	}

	public void setPaddAddress03(String paddAddress03) {
		this.paddAddress03 = paddAddress03;
	}

	public String getPaddAddress04() {
		return paddAddress04;
	}

	public void setPaddAddress04(String paddAddress04) {
		this.paddAddress04 = paddAddress04;
	}

	public String getPaddAddressGeoLevelId() {
		return paddAddressGeoLevelId;
	}

	public void setPaddAddressGeoLevelId(String paddAddressGeoLevelId) {
		this.paddAddressGeoLevelId = paddAddressGeoLevelId;
	}

	public String getPaddAddressGeoLevelDesc() {
		return paddAddressGeoLevelDesc;
	}

	public void setPaddAddressGeoLevelDesc(String paddAddressGeoLevelDesc) {
		this.paddAddressGeoLevelDesc = paddAddressGeoLevelDesc;
	}

	public String getPaddAddressCountryId() {
		return paddAddressCountryId;
	}

	public void setPaddAddressCountryId(String paddAddressCountryId) {
		this.paddAddressCountryId = paddAddressCountryId;
	}

	public String getPaddAddressCountryDesc() {
		return paddAddressCountryDesc;
	}

	public void setPaddAddressCountryDesc(String paddAddressCountryDesc) {
		this.paddAddressCountryDesc = paddAddressCountryDesc;
	}

	public String getPaddAddressPostalCode() {
		return paddAddressPostalCode;
	}

	public void setPaddAddressPostalCode(String paddAddressPostalCode) {
		this.paddAddressPostalCode = paddAddressPostalCode;
	}

	public String getPaddStatus() {
		return paddStatus;
	}

	public void setPaddStatus(String paddStatus) {
		this.paddStatus = paddStatus;
	}

	public String getPaddAttribute1() {
		return paddAttribute1;
	}

	public void setPaddAttribute1(String paddAttribute1) {
		this.paddAttribute1 = paddAttribute1;
	}

	public String getPaddAttribute2() {
		return paddAttribute2;
	}

	public void setPaddAttribute2(String paddAttribute2) {
		this.paddAttribute2 = paddAttribute2;
	}

	public String getPaddAttribute3() {
		return paddAttribute3;
	}

	public void setPaddAttribute3(String paddAttribute3) {
		this.paddAttribute3 = paddAttribute3;
	}

	public String getPaddAttribute4() {
		return paddAttribute4;
	}

	public void setPaddAttribute4(String paddAttribute4) {
		this.paddAttribute4 = paddAttribute4;
	}

	public String getPaddAttribute5() {
		return paddAttribute5;
	}

	public void setPaddAttribute5(String paddAttribute5) {
		this.paddAttribute5 = paddAttribute5;
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
