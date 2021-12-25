package com.fusionx.lending.product.resources;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditAppraisalResponse {

	@JsonProperty("credit_appraisal_task_list")
	private List<CreditAppraisalTaskListResponse> creditAppraisalTaskList;
	
	@JsonProperty("page_size")
	private int pageSize;
	
	@JsonProperty("page_number")
	private int pageNumber;
	
	@JsonProperty("number_of_elements")
	private int numberOfElements;
	
	@JsonProperty("total_pages")
	private int totalPages;
}
