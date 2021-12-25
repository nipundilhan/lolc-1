package com.fusionx.lending.product.resources;
  
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AddBaseEligibilityRequest {

	private String tenantId;
	 
	@NotBlank(message = "{typeId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{typeId.pattern}")
	private String typeId;
	
	@NotBlank(message = "{typeName.not-null}")
	private String typeName;
	
	@NotBlank(message = "{name.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;

	@Size(max = 350, message = "{common-description.size}")
	private String description;

	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{amount.pattern}") 
	private String amount;

	@Size(max = 500, message = "{texual.size}")
	private String texual;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{periodId.pattern}")
	private String periodId;

	private String period;
		
	@Pattern(regexp = "TRUE|FALSE|true|false", message = "{indicator.pattern}")
	private String indicator;
	
	@NotBlank(message = "{common-status.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message="{code.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;

	//@NotBlank(message = "{createdUser.not-null}")
	private String createdUser;

	public String getTenantId() {
		return tenantId;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTexual() {
		return texual;
	}

	public void setTexual(String texual) {
		this.texual = texual;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
