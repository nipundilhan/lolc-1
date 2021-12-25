package com.fusionx.lending.product.resources;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CreateCreditAppraisalProcessInstanceRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("credit_appraisal_lead_no")
	private String creditAppraisalLeadNo ;
	
	@JsonProperty("credit_appraisal_approve_url")
	private String creditAppraisalApproveUrl ;
	
	@JsonProperty("process_create_user")
	private String processCreateUser ;
	
	@JsonProperty("credit_appraisal_level_detail")
	private CreditAppraisalLevelDetail creditAppraisalLevels ;

}
