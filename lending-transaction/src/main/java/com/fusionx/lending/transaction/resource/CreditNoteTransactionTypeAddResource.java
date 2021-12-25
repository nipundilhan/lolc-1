package com.fusionx.lending.transaction.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CreditNoteTransactionTypeAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String creditNoteTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String transactionSubCodeId;		

    @NotBlank(message = "{common.not-null}")
    @Size(max = 35, message = "{subCode.size}")
    private String transactionSubCode;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "CREDIT|DEBIT", message = "{creditDebitIndicator.pattern}")
    private String postingType;
    
    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{common.status-pattern}")
    private String status;
    
    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "YES|NO", message = "{common.yes-no-pattern}")
    private String isDebitBalanceEnable;


    
	public String getCreditNoteTypeId() {
		return creditNoteTypeId;
	}


	public void setCreditNoteTypeId(String creditNoteTypeId) {
		this.creditNoteTypeId = creditNoteTypeId;
	}


	public String getTransactionSubCodeId() {
		return transactionSubCodeId;
	}


	public void setTransactionSubCodeId(String transactionSubCodeId) {
		this.transactionSubCodeId = transactionSubCodeId;
	}


	public String getTransactionSubCode() {
		return transactionSubCode;
	}


	public void setTransactionSubCode(String transactionSubCode) {
		this.transactionSubCode = transactionSubCode;
	}


	public String getPostingType() {
		return postingType;
	}


	public void setPostingType(String postingType) {
		this.postingType = postingType;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getIsDebitBalanceEnable() {
		return isDebitBalanceEnable;
	}


	public void setIsDebitBalanceEnable(String isDebitBalanceEnable) {
		this.isDebitBalanceEnable = isDebitBalanceEnable;
	}
    
    
    
    
    


}
