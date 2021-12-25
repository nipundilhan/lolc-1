package com.fusionx.lending.product.resources;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CreditAppraisalApproveTaskInstanceRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("credit_appraisal_approve_task_request")
	private CreditAppraisalApproveTaskInstanceBpmRequest bpmRequest ;
	
	@JsonProperty("container_id")
	private String containerId;

	@JsonProperty("task_instance_id")
	private String taskInstanceId;
	
	@JsonProperty("approval_group_id")
	private Long approvalGroupId;
	
	@JsonProperty("approval_group_user_id")
	private Long approvalGroupUserId;
	
	@JsonProperty("comments")
	private String comments;

}
