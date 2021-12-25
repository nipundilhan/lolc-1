package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Analyst Exception Details Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-08-2021   FXL-117  	 FXL-543       Piyumi     Created
 *    
 ********************************************************************************************************
*/

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class AnalystExceptionDetailsResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String exceptionTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String exceptionTypeName;
	
	@NotBlank(message = "{common.not-null}")
	private String authorizedUserId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String authorizedUserName;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 1500, message = "{exception-detail.size}")
	private String exceptionDetail;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;
	
	@Valid
	private List<DocumentUpdateResource> analystExceptionDocumentList;
	
	public String getExceptionTypeId() {
		return exceptionTypeId;
	}
	public void setExceptionTypeId(String exceptionTypeId) {
		this.exceptionTypeId = exceptionTypeId;
	}
	
	public String getExceptionTypeName() {
		return exceptionTypeName;
	}
	public void setExceptionTypeName(String exceptionTypeName) {
		this.exceptionTypeName = exceptionTypeName;
	}
	
	public String getAuthorizedUserId() {
		return authorizedUserId;
	}
	public void setAuthorizedUserId(String authorizedUserId) {
		this.authorizedUserId = authorizedUserId;
	}
	
	public String getAuthorizedUserName() {
		return authorizedUserName;
	}
	public void setAuthorizedUserName(String authorizedUserName) {
		this.authorizedUserName = authorizedUserName;
	}
	
	public String getExceptionDetail() {
		return exceptionDetail;
	}
	public void setExceptionDetail(String exceptionDetail) {
		this.exceptionDetail = exceptionDetail;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public List<DocumentUpdateResource> getAnalystExceptionDocumentList() {
		return analystExceptionDocumentList;
	}
	public void setAnalystExceptionDocumentList(List<DocumentUpdateResource> analystExceptionDocumentList) {
		this.analystExceptionDocumentList = analystExceptionDocumentList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
