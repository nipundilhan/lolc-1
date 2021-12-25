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
 *    1   23-11-2021      		     FXL-1947	Achini      Created
 *    
 ********************************************************************************************************
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditAppraisalTaskListResponse {

	@JsonFormat(timezone = "Asia/Colombo")
	@JsonProperty("created_at")
	private Date createdAt;
	
	@JsonProperty("lead_no")
	private String leadNo;
	
	@JsonProperty("approval_group_id")
	private String approvalGroupId;
	
	@JsonProperty("approval_group_user_id")
	private String approvalGroupUserId;
	
	@JsonProperty("container_id")
	private String containerId;
	
	@JsonProperty("task_instant_id")
	private Long taskInstantId ;
}
