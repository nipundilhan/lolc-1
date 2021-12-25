package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseCustomerOnboardProductUpdatedResource {

	
	private Long leadId;
	
	private Long productId;
				
	private Long onBoardProductId;

	public Long getLeadId() {
		return leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getOnBoardProductId() {
		return onBoardProductId;
	}

	public void setOnBoardProductId(Long onBoardProductId) {
		this.onBoardProductId = onBoardProductId;
	}

	
	
}
