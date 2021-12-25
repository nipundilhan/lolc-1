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
public class CreditAppraisalApproveTaskInstanceBpmRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("credit_appraisal_approve")
	private boolean creditAppraisalApprove ;
	
	@JsonProperty("credit_appraisal_update")
	private boolean creditAppraisalUpdate ;

}
