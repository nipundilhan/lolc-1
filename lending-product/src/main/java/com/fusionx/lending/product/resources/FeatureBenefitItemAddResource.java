package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Feature Benefit Item 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-06-2021     FX-6614     FX-6550    RavishikaS     Created
 *    
 ********************************************************************************************************
*/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FeatureBenefitItemAddResource {

	private String tenantId;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common.invalid-value}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;
	
	@Size(max = 350, message = "{common-length01.size}")
	private String description;
	 
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String typeId;
	
	@NotBlank(message = "{common.not-null}")
	private String typeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|YES|NO", message = "{common-enable.pattern}")
	private String indicator;

	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,5}$", message = "{common.invalid-amount-pattern}") 
	private String amount;

	@Size(max = 500, message = "{textual.size}")
	private String textual;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTextual() {
		return textual;
	}

	public void setTextual(String textual) {
		this.textual = textual;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
