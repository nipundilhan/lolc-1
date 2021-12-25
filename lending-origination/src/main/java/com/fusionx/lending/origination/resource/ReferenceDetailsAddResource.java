package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ReferenceDetailsAddResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{common-name.size}")
	private String currentAddressLine1;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{common-name.size}")
	private String currentAddressLine2;

	@Size(max = 255, message = "{common-name.size}")
	private String currentAddressLine3;

	@Size(max = 255, message = "{common-name.size}")
	private String currentAddressLine4;

	@Size(max = 255, message = "{common-name.size}")
	private String permanentAddressLine1;

	@Size(max = 255, message = "{common-name.size}")
	private String permanentAddressLine2;

	@Size(max = 255, message = "{common-name.size}")
	private String permanentAddressLine3;

	@Size(max = 255, message = "{common-name.size}")
	private String permanentAddressLine4;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{common-name.size}")
	private String name;

	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{common-name.size}")
	private String businessEmployer;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{value.pattern}")
	private String professionalStatus;

	private List<ReferenceDetailsContactInfoAddResource> contactDetails;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrentAddressLine1() {
		return currentAddressLine1;
	}

	public void setCurrentAddressLine1(String currentAddressLine1) {
		this.currentAddressLine1 = currentAddressLine1;
	}

	public String getCurrentAddressLine2() {
		return currentAddressLine2;
	}

	public void setCurrentAddressLine2(String currentAddressLine2) {
		this.currentAddressLine2 = currentAddressLine2;
	}

	public String getCurrentAddressLine3() {
		return currentAddressLine3;
	}

	public void setCurrentAddressLine3(String currentAddressLine3) {
		this.currentAddressLine3 = currentAddressLine3;
	}

	public String getCurrentAddressLine4() {
		return currentAddressLine4;
	}

	public void setCurrentAddressLine4(String currentAddressLine4) {
		this.currentAddressLine4 = currentAddressLine4;
	}

	public String getPermanentAddressLine1() {
		return permanentAddressLine1;
	}

	public void setPermanentAddressLine1(String permanentAddressLine1) {
		this.permanentAddressLine1 = permanentAddressLine1;
	}

	public String getPermanentAddressLine2() {
		return permanentAddressLine2;
	}

	public void setPermanentAddressLine2(String permanentAddressLine2) {
		this.permanentAddressLine2 = permanentAddressLine2;
	}

	public String getPermanentAddressLine3() {
		return permanentAddressLine3;
	}

	public void setPermanentAddressLine3(String permanentAddressLine3) {
		this.permanentAddressLine3 = permanentAddressLine3;
	}

	public String getPermanentAddressLine4() {
		return permanentAddressLine4;
	}

	public void setPermanentAddressLine4(String permanentAddressLine4) {
		this.permanentAddressLine4 = permanentAddressLine4;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusinessEmployer() {
		return businessEmployer;
	}

	public void setBusinessEmployer(String businessEmployer) {
		this.businessEmployer = businessEmployer;
	}

	public String getProfessionalStatus() {
		return professionalStatus;
	}

	public void setProfessionalStatus(String professionalStatus) {
		this.professionalStatus = professionalStatus;
	}

	public List<ReferenceDetailsContactInfoAddResource> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<ReferenceDetailsContactInfoAddResource> contactDetails) {
		this.contactDetails = contactDetails;
	}

}
