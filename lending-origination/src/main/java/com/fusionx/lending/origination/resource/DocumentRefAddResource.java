package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class DocumentRefAddResource  extends DocumentAddResource{
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String documentRefId;
	

	@Pattern(regexp = "^$|[0-9]+", message = "{common.invalid-number-format}")
	private String version;
	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocumentRefId() {
		return documentRefId;
	}

	public void setDocumentRefId(String documentRefId) {
		this.documentRefId = documentRefId;
	}
	
	
	

}
