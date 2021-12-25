package com.fusionx.lending.origination.enums;

public enum FieldSetUpDescEnum {
	BIG_DECIMAL("big decimal type", "^$|\\d{1,20}\\.\\d{1,5}$"),
	LONG("long type", "^$|[0-9]+");
	
	private final String description;
	private final String pattern;
	
	private FieldSetUpDescEnum(String description, String pattern) {
		this.description = description;
		this.pattern = pattern;
	}

	public String getDescription() {
		return description;
	}

	public String getPattern() {
		return pattern;
	}
}
