package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Business Risk Detail Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BusinessRiskDetailResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String riskTypeId;	
	
	@NotBlank(message = "{common.not-null}")
	private String riskTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String riskGradingId;	
	
	@NotBlank(message = "{common.not-null}")
	private String riskGradingName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String riskRatingLevelId;	
	private String riskRatingType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" , message = "{common.invalid-date-pattern}" )
	private String calculatedDate;

	@Size(max = 150, message = "{certificated-details.size}")
	private String certificatedDetails;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String riskAuthorityId;		
	private String riskAuthorityName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String documentId;		
	private String documentName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|ACTIVE|INACTIVE",message="{common-status.pattern}")
	private String status;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

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


	public String getRiskTypeId() {
		return riskTypeId;
	}

	public void setRiskTypeId(String riskTypeId) {
		this.riskTypeId = riskTypeId;
	}

	public String getRiskTypeName() {
		return riskTypeName;
	}

	public void setRiskTypeName(String riskTypeName) {
		this.riskTypeName = riskTypeName;
	}

	public String getRiskGradingId() {
		return riskGradingId;
	}

	public void setRiskGradingId(String riskGradingId) {
		this.riskGradingId = riskGradingId;
	}

	public String getRiskGradingName() {
		return riskGradingName;
	}

	public void setRiskGradingName(String riskGradingName) {
		this.riskGradingName = riskGradingName;
	}

	public String getCalculatedDate() {
		return calculatedDate;
	}

	public void setCalculatedDate(String calculatedDate) {
		this.calculatedDate = calculatedDate;
	}

	public String getCertificatedDetails() {
		return certificatedDetails;
	}

	public void setCertificatedDetails(String certificatedDetails) {
		this.certificatedDetails = certificatedDetails;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRiskRatingType() {
		return riskRatingType;
	}

	public void setRiskRatingType(String riskRatingType) {
		this.riskRatingType = riskRatingType;
	}

	public String getRiskRatingLevelId() {
		return riskRatingLevelId;
	}

	public void setRiskRatingLevelId(String riskRatingLevelId) {
		this.riskRatingLevelId = riskRatingLevelId;
	}

	public String getRiskAuthorityName() {
		return riskAuthorityName;
	}

	public void setRiskAuthorityName(String riskAuthorityName) {
		this.riskAuthorityName = riskAuthorityName;
	}

	public String getRiskAuthorityId() {
		return riskAuthorityId;
	}

	public void setRiskAuthorityId(String riskAuthorityId) {
		this.riskAuthorityId = riskAuthorityId;
	}

}
