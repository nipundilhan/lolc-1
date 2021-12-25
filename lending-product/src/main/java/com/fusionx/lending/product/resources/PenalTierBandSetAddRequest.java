package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * PenalTierBandSetAddRequest
 * @author 	Dilhan
 * @Date    19-08-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   19-08-2021                            Dilhan       Created
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PenalTierBandSetAddRequest {
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@Size(max = 255, message = "{common-name.size}")
	private String note;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String penalInterestTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String penalInterestTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String gracePeriodLength;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "INBA|INTI|INWH", message = "{common-tierband.method}")
	private String tierBandMethod;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTUAL|DAYS_360|DAYS_365|DAYS_366", message = "{common-rate.method}")
	private String rateCalculationMethod;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "COMPOUND|SIMPLE", message = "{common-interest.type}")
	private String interestCalculationMethod;

	public String getTierBandMethod() {
		return tierBandMethod;
	}

	public void setTierBandMethod(String tierBandMethod) {
		this.tierBandMethod = tierBandMethod;
	}

	public String getRateCalculationMethod() {
		return rateCalculationMethod;
	}

	public void setRateCalculationMethod(String rateCalculationMethod) {
		this.rateCalculationMethod = rateCalculationMethod;
	}

	public String getInterestCalculationMethod() {
		return interestCalculationMethod;
	}

	public void setInterestCalculationMethod(String interestCalculationMethod) {
		this.interestCalculationMethod = interestCalculationMethod;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getPenalInterestTypeId() {
		return penalInterestTypeId;
	}

	public void setPenalInterestTypeId(String penalInterestTypeId) {
		this.penalInterestTypeId = penalInterestTypeId;
	}

	public String getPenalInterestTypeName() {
		return penalInterestTypeName;
	}

	public void setPenalInterestTypeName(String penalInterestTypeName) {
		this.penalInterestTypeName = penalInterestTypeName;
	}
	
	public String getGracePeriodLength() {
		return gracePeriodLength;
	}

	public void setGracePeriodLength(String gracePeriodLength) {
		this.gracePeriodLength = gracePeriodLength;
	}
	
}
