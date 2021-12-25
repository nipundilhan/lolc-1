package com.fusionx.lending.product.resources;

import java.util.List;

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
public class CreditAppraisalTaskListRequest {

	@JsonProperty("task_status_list")
    private List<String> taskStatusList;

	@JsonProperty("task_name")
    private String taskName;
	
	@JsonProperty("priority")
    private Integer priority;
	
	@JsonProperty("task_Owner")
    private String taskOwner;
	
	@JsonProperty("assign_task")
    private boolean assignTask;
}
