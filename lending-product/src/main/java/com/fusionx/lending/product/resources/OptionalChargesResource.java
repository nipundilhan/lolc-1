package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OptionalChargesResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String chargeTypeId;
	
	@NotBlank(message = "{common.not-null}")
	private String chargeTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String criteriaId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "AFIM|AFUP|AFFC", message = "{tc-calculation-criteriaName.invalid}")
	private String criteriaName;
	
	@Valid
	private List<OptionalChargesListResource> optionalChargesList;

	
	public String getCriteriaId() {
		return criteriaId;
	}

	public void setCriteriaId(String criteriaId) {
		this.criteriaId = criteriaId;
	}

	public String getCriteriaName() {
		return criteriaName;
	}

	public void setCriteriaName(String criteriaName) {
		this.criteriaName = criteriaName;
	}

	public String getChargeTypeId() {
		return chargeTypeId;
	}

	public void setChargeTypeId(String chargeTypeId) {
		this.chargeTypeId = chargeTypeId;
	}

	public String getChargeTypeName() {
		return chargeTypeName;
	}

	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OptionalChargesListResource> getOptionalChargesList() {
		return optionalChargesList;
	}

	public void setOptionalChargesList(List<OptionalChargesListResource> optionalChargesList) {
		this.optionalChargesList = optionalChargesList;
	}
}
