package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * System Generated Document Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-11-2021   FXL-358       FXL-1736   Dilki        Created
 *    
 ********************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SystemGeneratedDocumentTypeDetailsAddResource {
	
	private String id;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	@NotBlank(message = "{common.not-null}")
	private String documentDetailId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String documentDetailName;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{common-enable.pattern}")
	private String mandatory;

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocumentDetailId() {
		return documentDetailId;
	}

	public void setDocumentDetailId(String documentDetailId) {
		this.documentDetailId = documentDetailId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDocumentDetailName() {
		return documentDetailName;
	}

	public void setDocumentDetailName(String documentDetailName) {
		this.documentDetailName = documentDetailName;
	}

}
