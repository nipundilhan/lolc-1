package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Repayment  Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   	Task No    	Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-07-2021     FX-6620 		FX-6803     RavishikaS      Created
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RepaymentAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String name;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String repaymentTypeId;
	
	@NotBlank(message = "{common.not-null}")
	private String repaymentTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String repaymentFrequencyId;
	
	@NotBlank(message = "{common.not-null}")
	private String repaymentFrequencyName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String repaymentAmountTypeId;
	
	@NotBlank(message = "{common.not-null}")
	private String repaymentAmountTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

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

	public String getRepaymentTypeId() {
		return repaymentTypeId;
	}

	public void setRepaymentTypeId(String repaymentTypeId) {
		this.repaymentTypeId = repaymentTypeId;
	}

	public String getRepaymentTypeName() {
		return repaymentTypeName;
	}

	public void setRepaymentTypeName(String repaymentTypeName) {
		this.repaymentTypeName = repaymentTypeName;
	}

	public String getRepaymentFrequencyId() {
		return repaymentFrequencyId;
	}

	public void setRepaymentFrequencyId(String repaymentFrequencyId) {
		this.repaymentFrequencyId = repaymentFrequencyId;
	}

	public String getRepaymentFrequencyName() {
		return repaymentFrequencyName;
	}

	public void setRepaymentFrequencyName(String repaymentFrequencyName) {
		this.repaymentFrequencyName = repaymentFrequencyName;
	}

	public String getRepaymentAmountTypeId() {
		return repaymentAmountTypeId;
	}

	public void setRepaymentAmountTypeId(String repaymentAmountTypeId) {
		this.repaymentAmountTypeId = repaymentAmountTypeId;
	}

	public String getRepaymentAmountTypeName() {
		return repaymentAmountTypeName;
	}

	public void setRepaymentAmountTypeName(String repaymentAmountTypeName) {
		this.repaymentAmountTypeName = repaymentAmountTypeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	

}
