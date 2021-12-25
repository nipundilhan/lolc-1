package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class FinancialStatementDetailUpdateResource {

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String financailStatementDetailId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String financailStatementTemplateDetailId;
	
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{common-amount.pattern}") 
	private String amount;
	

    @Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}")
    private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFinancailStatementDetailId() {
		return financailStatementDetailId;
	}

	public void setFinancailStatementDetailId(String financailStatementDetailId) {
		this.financailStatementDetailId = financailStatementDetailId;
	}

	public String getFinancailStatementTemplateDetailId() {
		return financailStatementTemplateDetailId;
	}

	public void setFinancailStatementTemplateDetailId(String financailStatementTemplateDetailId) {
		this.financailStatementTemplateDetailId = financailStatementTemplateDetailId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
