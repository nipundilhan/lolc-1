package com.fusionx.lending.origination.enums;

public enum RepaymentCriteria {

	IN_ADVANCE("IN_ADVANCE", "In Advance"),
	IN_ARREARS("IN_ARREARS", "In Arrears");
	private String code;
	private String name;
	
	private RepaymentCriteria(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
