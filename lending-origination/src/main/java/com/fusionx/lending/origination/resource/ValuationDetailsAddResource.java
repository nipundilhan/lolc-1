package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ValuationDetailsAddResource {
	
	private String id;
	
	private String tenantId;
	
	private String valuationDetailsId;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String valuerId;
	
	private String valuerRefCode;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|INTERNAL|EXTERNAL", message = "{valuerType.pattern}")
	private String valuerType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" , message = "{common.invalid-date-pattern}" )
	private String valuationDate;
	
	@Pattern(regexp = "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" , message = "{common.invalid-date-pattern}" )
	private String lastValuationDate;
	
	@Pattern(regexp = "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" , message = "{common.invalid-date-pattern}" )
	private String reValuationDate;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String marketValue;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String forceSaleValue;
	
	@Pattern(regexp = "YES|NO", message = "{common.enable-status.pattern}")
	private String finalValuationFlag;
	
	private String remark;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	private String modifiedUser;
	
	private String createdUser;
	
	private String version;

	public String getValuationDetailsId() {
		return valuationDetailsId;
	}

	public void setValuationDetailsId(String valuationDetailsId) {
		this.valuationDetailsId = valuationDetailsId;
	}

	public String getValuerId() {
		return valuerId;
	}

	public void setValuerId(String valuerId) {
		this.valuerId = valuerId;
	}

	public String getValuerType() {
		return valuerType;
	}

	public void setValuerType(String valuerType) {
		this.valuerType = valuerType;
	}

	public String getValuationDate() {
		return valuationDate;
	}

	public void setValuationDate(String valuationDate) {
		this.valuationDate = valuationDate;
	}

	public String getLastValuationDate() {
		return lastValuationDate;
	}

	public void setLastValuationDate(String lastValuationDate) {
		this.lastValuationDate = lastValuationDate;
	}

	public String getReValuationDate() {
		return reValuationDate;
	}

	public void setReValuationDate(String reValuationDate) {
		this.reValuationDate = reValuationDate;
	}

//	public String getNominalValue() {
//		return nominalValue;
//	}
//
//	public void setNominalValue(String nominalValue) {
//		this.nominalValue = nominalValue;
//	}

	public String getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	public String getForceSaleValue() {
		return forceSaleValue;
	}

	public void setForceSaleValue(String forceSaleValue) {
		this.forceSaleValue = forceSaleValue;
	}

//	public String getHaircut() {
//		return haircut;
//	}
//
//	public void setHaircut(String haircut) {
//		this.haircut = haircut;
//	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getValuerRefCode() {
		return valuerRefCode;
	}

	public void setValuerRefCode(String valuerRefCode) {
		this.valuerRefCode = valuerRefCode;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getFinalValuationFlag() {
		return finalValuationFlag;
	}

	public void setFinalValuationFlag(String finalValuationFlag) {
		this.finalValuationFlag = finalValuationFlag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
