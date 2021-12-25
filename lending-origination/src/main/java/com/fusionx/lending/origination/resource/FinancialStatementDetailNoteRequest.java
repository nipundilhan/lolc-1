package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class FinancialStatementDetailNoteRequest {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String noteId;	

	@NotBlank(message = "{common.not-null}")
	@Size(max = 250, message = "{common-name1.size}")
	private String itemName;	
	
	@Size(max = 355, message = "{common-description.size}")
	private String description;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String noOfItem;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp ="^$|\\d{1,20}\\.\\d{1,5}$", message = "{common-amount.pattern}") 
	private String value;
	

    @Pattern(regexp = "^$|[0-9]", message = "{version.pattern}")
    private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNoOfItem() {
		return noOfItem;
	}

	public void setNoOfItem(String noOfItem) {
		this.noOfItem = noOfItem;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
