package com.fusionx.lending.product.resources;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditAppraisalUpdateTaskInstanceRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("bpm_task_detail")
	private BpmTaskRequest bpmTaskRequest;
	
	@JsonProperty("credit_appraisal_level_detail")	
	private CreditAppraisalLevelDetail CreditAppraisalLevelDetail;
	
	@JsonProperty("approval_group_id")
	private Long approvalGroupId;
	
	@JsonProperty("approval_group_user_id")
	private Long approvalGroupUserId;
	
	@JsonProperty("comments")
	private String comments;
}
