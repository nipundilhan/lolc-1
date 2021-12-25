package com.fusionx.lending.origination.resource;

import com.fusionx.lending.origination.enums.CommonStatus;

public class RepaymentFrequencyResponse {
	
	private Long id;
	private String code;
	private String name;
	private String status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
