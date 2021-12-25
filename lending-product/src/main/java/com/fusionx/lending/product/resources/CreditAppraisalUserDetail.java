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
public class CreditAppraisalUserDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("num_users_for_approval")
	private Integer numUsersForApproval ;
	
	@JsonProperty("user")
	private String user ;
	
	@JsonProperty("approval_group_id")
	private String approvalGroupId;
	
	@JsonProperty("approval_group_user_id")
	private String approvalGroupUserId;

}
