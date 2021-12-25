package com.fusionx.lending.product.resources;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditAppraisalUpdateTaskInstanceBpmRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("credit_appraisal_level_detail")	
	private CreditAppraisalLevelDetail CreditAppraisalLevelDetail;

}
