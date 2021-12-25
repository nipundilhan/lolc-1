package com.fusionx.lending.origination.enums;

public enum CustomerMainType {
	
	MAIN("MAIN"),
	CO_BORROWER("CO-BORROWER");
	
	private String name;
	
	private CustomerMainType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
