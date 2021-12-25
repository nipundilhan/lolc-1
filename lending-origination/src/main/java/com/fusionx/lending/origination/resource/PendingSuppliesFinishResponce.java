package com.fusionx.lending.origination.resource;

import org.springframework.http.HttpStatus;

public class PendingSuppliesFinishResponce {
	
	private Long penSupId;
	
	private Long suppliesId;

	public Long getPenSupId() {
		return penSupId;
	}

	public void setPenSupId(Long penSupId) {
		this.penSupId = penSupId;
	}

	public Long getSuppliesId() {
		return suppliesId;
	}

	public void setSuppliesId(Long suppliesId) {
		this.suppliesId = suppliesId;
	}

}
