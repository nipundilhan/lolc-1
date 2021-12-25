package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Revolving Details Resource
 * @author 	Dilhan
 * @Date    05-OCT-2021
 * 
 ********************************************************************************************************
 *  ###   	Date         	Story Point   	Task No    		Author      	 Description
 *-------------------------------------------------------------------------------------------------------
 *    1   	05-OCT-2021  		            FXL-994   		Dilhan     	     Initial Development.
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RevolvingDetailsResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String initialDisbursement;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "END_OF_THE_PERIOD|END_OF_THE_TERM", message = "{common-calculation.pattern}")
	private String interestPaymentMethod;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@Valid
	List<RevolvingPaymentScheduleResource> revolvingPaymentSchedule;

	public List<RevolvingPaymentScheduleResource> getRevolvingPaymentSchedule() {
		return revolvingPaymentSchedule;
	}

	public void setRevolvingPaymentSchedule(List<RevolvingPaymentScheduleResource> revolvingPaymentSchedule) {
		this.revolvingPaymentSchedule = revolvingPaymentSchedule;
	}

	public String getInitialDisbursement() {
		return initialDisbursement;
	}

	public void setInitialDisbursement(String initialDisbursement) {
		this.initialDisbursement = initialDisbursement;
	}

	public String getInterestPaymentMethod() {
		return interestPaymentMethod;
	}

	public void setInterestPaymentMethod(String interestPaymentMethod) {
		this.interestPaymentMethod = interestPaymentMethod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
