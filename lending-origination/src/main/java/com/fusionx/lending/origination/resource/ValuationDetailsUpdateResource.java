package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * Valuation Details Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   									    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ValuationDetailsUpdateResource {
	
	private String id;
	
	private String tenantId;
	
	private String valuationDetailsId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String assetEntityId;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String valuerId;
	
//	@NotBlank(message = "{common.not-null}")
	private String valuerRefCode;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|INTERNAL|EXTERNAL", message = "{officerType.pattern}")
	private String valuerType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" , message = "{common.invalid-date-pattern}" )
	private String valuationDate;
	
	@Pattern(regexp = "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" , message = "{common.invalid-date-pattern}" )
	private String lastValuationDate;
	
//	@NotBlank(message = "{common.not-null}")
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
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getValuerId() {
		return valuerId;
	}

	public void setValuerId(String valuerId) {
		this.valuerId = valuerId;
	}

	public String getValuerRefCode() {
		return valuerRefCode;
	}

	public void setValuerRefCode(String valuerRefCode) {
		this.valuerRefCode = valuerRefCode;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getValuationDetailsId() {
		return valuationDetailsId;
	}

	public void setValuationDetailsId(String valuationDetailsId) {
		this.valuationDetailsId = valuationDetailsId;
	}

	public String getAssetEntityId() {
		return assetEntityId;
	}

	public void setAssetEntityId(String assetEntityId) {
		this.assetEntityId = assetEntityId;
	}

	public String getFinalValuationFlag() {
		return finalValuationFlag;
	}

	public void setFinalValuationFlag(String finalValuationFlag) {
		this.finalValuationFlag = finalValuationFlag;
	}

}
