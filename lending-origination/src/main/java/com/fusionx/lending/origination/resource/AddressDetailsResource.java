package com.fusionx.lending.origination.resource;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AddressDetailsResource {

	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String addressTypeId;
	@NotBlank(message = "{common.not-null}")
	private String addressTypeDesc;

	@NotBlank(message = "{common.not-null}")
	private String address1;
	
	@NotBlank(message = "{common.not-null}")
	private String address2;
	
	private String address3;
	
	private String address4;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String geoLevelId;
	@NotBlank(message = "{common.not-null}")
	private String geoLevelDesc;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String countryGeoId;
	@NotBlank(message = "{common.not-null}")
	private String countryGeoDesc;
	
	private String postalCode;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String paddId;
	private String ppaddId;

	public String getAddressTypeId() {
		return addressTypeId;
	}

	public void setAddressTypeId(String addressTypeId) {
		this.addressTypeId = addressTypeId;
	}

	public String getAddressTypeDesc() {
		return addressTypeDesc;
	}

	public void setAddressTypeDesc(String addressTypeDesc) {
		this.addressTypeDesc = addressTypeDesc;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getGeoLevelId() {
		return geoLevelId;
	}

	public void setGeoLevelId(String geoLevelId) {
		this.geoLevelId = geoLevelId;
	}

	public String getGeoLevelDesc() {
		return geoLevelDesc;
	}

	public void setGeoLevelDesc(String geoLevelDesc) {
		this.geoLevelDesc = geoLevelDesc;
	}

	public String getCountryGeoId() {
		return countryGeoId;
	}

	public void setCountryGeoId(String countryGeoId) {
		this.countryGeoId = countryGeoId;
	}

	public String getCountryGeoDesc() {
		return countryGeoDesc;
	}

	public void setCountryGeoDesc(String countryGeoDesc) {
		this.countryGeoDesc = countryGeoDesc;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPaddId() {
		return paddId;
	}

	public void setPaddId(String paddId) {
		this.paddId = paddId;
	}

	public String getPpaddId() {
		return ppaddId;
	}

	public void setPpaddId(String ppaddId) {
		this.ppaddId = ppaddId;
	}
	
	
}
