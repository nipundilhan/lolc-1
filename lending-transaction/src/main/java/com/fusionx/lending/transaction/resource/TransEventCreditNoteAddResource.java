package com.fusionx.lending.transaction.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TransEventCreditNoteAddResource {
	
	@JsonProperty("transEventId")
    @NotBlank(message = "{transEventId.not-null}")
    private String transEventId;

    @JsonProperty("transEventCode")
    @NotBlank(message = "{transEventCode.not-null}")
    private String transEventCode;

    @JsonProperty("creditNoteId")
    @NotBlank(message = "{subCodeId.not-null}")
    private String creditNoteId;

    @JsonProperty("creditNoteCode")
    @NotBlank(message = "{subCode.not-null}")
    private String creditNoteCode;
    
    @JsonProperty("status")
    @NotBlank(message = "{status.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;

	public String getTransEventId() {
		return transEventId;
	}

	public void setTransEventId(String transEventId) {
		this.transEventId = transEventId;
	}

	public String getTransEventCode() {
		return transEventCode;
	}

	public void setTransEventCode(String transEventCode) {
		this.transEventCode = transEventCode;
	}

	public String getCreditNoteId() {
		return creditNoteId;
	}

	public void setCreditNoteId(String creditNoteId) {
		this.creditNoteId = creditNoteId;
	}

	public String getCreditNoteCode() {
		return creditNoteCode;
	}

	public void setCreditNoteCode(String creditNoteCode) {
		this.creditNoteCode = creditNoteCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    

}
