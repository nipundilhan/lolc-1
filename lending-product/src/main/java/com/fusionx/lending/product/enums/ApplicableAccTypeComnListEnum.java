package com.fusionx.lending.product.enums;

public enum ApplicableAccTypeComnListEnum {
	
	SAVINGS("SAVINGS", "Savings Account"),
	CURRENT_ACCOUNT("CURRENT_ACCOUNT", "Current Account");
	
	private String ApplicableAccTypeComnListId;
	private String ApplicableAccTypeComnListDecs;
	
	private ApplicableAccTypeComnListEnum(String ApplicableAccTypeComnListId, String ApplicableAccTypeComnListDecs) {
		this.ApplicableAccTypeComnListId = ApplicableAccTypeComnListId;
		this.ApplicableAccTypeComnListDecs = ApplicableAccTypeComnListDecs;
	}

	public String getApplicableAccTypeComnListId() {
		return ApplicableAccTypeComnListId;
	}

	public String getApplicableAccTypeComnListDecs() {
		return ApplicableAccTypeComnListDecs;
	}

}
