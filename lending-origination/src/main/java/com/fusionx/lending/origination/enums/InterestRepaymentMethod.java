package com.fusionx.lending.origination.enums;

public enum InterestRepaymentMethod {
	END_OF_MONTH("END_OF_MONTH", "End Of Month"),
	END_OF_TERM("END_OF_TERM", "End Of Term");
	private String code;
	private String name;
	
	private InterestRepaymentMethod(String code, String name) {
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
