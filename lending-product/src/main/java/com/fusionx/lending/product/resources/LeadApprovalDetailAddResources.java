package com.fusionx.lending.product.resources;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Account Status Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-12-2021      		            	Achini      Created
 *    
 ********************************************************************************************************
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeadApprovalDetailAddResources implements Serializable {

	private static final long serialVersionUID = 1L;
	
//	@NotBlank(message = "{common.not-null}")
//	@JsonProperty("action_taken_date")
//	private Date actionTakenDate;

//	@JsonProperty("acton_taken_by")
//	private String actonTakenBy;

	@NotBlank(message = "{common.not-null}")
	@JsonProperty("approval_group_id")
	private Long approvalGroupId;

	@NotBlank(message = "{common.not-null}")
	@JsonProperty("approval_group_user_id")
	private Long approvalGroupUserId;

	@JsonProperty("comments")
	private String comments;

//	@JsonProperty("created_date")
//	private Date createdDate;
//
//	@JsonProperty("created_user")
//	private String createdUser;

	@JsonProperty("cycle")
	private int cycle;

	@NotBlank(message = "{common.not-null}")
	@JsonProperty("lead_id")
	private Long leadId;

//	@JsonProperty("modified_date")
//	private Date modifiedDate;
//
//	@JsonProperty("modified_user")
//	private String modifiedUser;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	@JsonProperty("status")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "APPROVE|REJECT|UPDATE", message = "{lead.action.pattern}")
	@JsonProperty("action")
	private String action;

//	@JsonProperty("tenant_id")
//	private String tenantId;
//
	@JsonProperty("version")
	private int version;

}
