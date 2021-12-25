package com.fusionx.lending.product.resources;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Credit Appraisal workflow Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-11-2021      		     FXL-1947	Achini      Created
 *    
 ********************************************************************************************************
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditAppraisalMainResponse {

	@JsonFormat(timezone = "Asia/Colombo")
	@JsonProperty("date")
	private Date createdAt;
	
	@JsonProperty("lead_number")
	private String leadNo;
	
	@JsonProperty("customer_reference_code")
	private String customerReferenceCode;
	
	@JsonProperty("customer_name")
	private String customerName;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("container_id")
	private String containerId;
	
	@JsonProperty("task_instant_id")
	private Long taskInstantId ;
}
