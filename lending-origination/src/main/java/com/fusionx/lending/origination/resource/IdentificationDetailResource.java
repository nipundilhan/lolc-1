package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class IdentificationDetailResource {

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String identificationTypeId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 200, message = "{common-name.size}")
	private String identificationType;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{common-name.size}")
	private String identificationNo;

	@Pattern(regexp = "^$|[0-9]+", message = "{pidtId.pattern}")
    private String pidtId;
   
    @Pattern(regexp = "^$|[0-9]+", message = "{pidtId.pattern}")
    private String ppidtId;

    @Pattern(regexp = "^$|[0-9]+", message = "{perId.pattern}")
    private String perId;
    
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common.invalid-date-pattern}")
	private String issueDate;
	
	@Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common.invalid-date-pattern}")
	private String expiryDate;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

	public String getIdentificationTypeId() {
		return identificationTypeId;
	}

	public void setIdentificationTypeId(String identificationTypeId) {
		this.identificationTypeId = identificationTypeId;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationNo() {
		return identificationNo;
	}

	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}
	
    public String getPidtId() {
		return pidtId;
	}

	public void setPidtId(String pidtId) {
		this.pidtId = pidtId;
	}

	public String getPpidtId() {
		return ppidtId;
	}

	public void setPpidtId(String ppidtId) {
		this.ppidtId = ppidtId;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
