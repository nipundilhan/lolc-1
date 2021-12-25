package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LinkedPersonResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "RELATION|KEYPERSON", message = "{customer-linkedPersonType.pattern}")	
	private String linkedPersonType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String relationshipTypeId;
	
	@NotBlank(message = "{common.not-null}")
	private String relationCode;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String relationshipTypeName;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String fullname;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String titleId;
	
	@NotBlank(message = "{common.not-null}")
	private String title;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String genderId;

	@NotBlank(message = "{common.not-null}")
	private String gender;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{common-name.size}")
	private String firstName;
	
	@Size(max = 255, message = "{common-name.size}")
	private String middleName;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 255, message = "{common-name.size}")
	private String lastName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{perId.pattern}")
	private String pculpId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{perId.pattern}")
	private String penPerId;

	@Pattern(regexp = "^$|[0-9]+", message = "{perId.pattern}")
	private String perId;

	@Size(max = 15, message = "{perCode.size}")
	private String perCode;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

	private List<IdentificationDetailResource> identificationDetailList;

	public String getRelationshipTypeId() {
		return relationshipTypeId;
	}

	public void setRelationshipTypeId(String relationshipTypeId) {
		this.relationshipTypeId = relationshipTypeId;
	}
	
	public String getRelationshipTypeName() {
		return relationshipTypeName;
	}

	public void setRelationshipTypeName(String relationshipTypeName) {
		this.relationshipTypeName = relationshipTypeName;
	}

	public String getLinkedPersonType() {
		return linkedPersonType;
	}

	public void setLinkedPersonType(String linkedPersonType) {
		this.linkedPersonType = linkedPersonType;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<IdentificationDetailResource> getIdentificationDetailList() {
		return identificationDetailList;
	}

	public void setIdentificationDetailList(List<IdentificationDetailResource> identificationDetailList) {
		this.identificationDetailList = identificationDetailList;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getGenderId() {
		return genderId;
	}

	public void setGenderId(String genderId) {
		this.genderId = genderId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


	public String getPenPerId() {
		return penPerId;
	}

	public void setPenPerId(String penPerId) {
		this.penPerId = penPerId;
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}

	public String getPerCode() {
		return perCode;
	}

	public void setPerCode(String perCode) {
		this.perCode = perCode;
	}

	public String getRelationCode() {
		return relationCode;
	}

	public void setRelationCode(String relationCode) {
		this.relationCode = relationCode;
	}

	public String getPculpId() {
		return pculpId;
	}

	public void setPculpId(String pculpId) {
		this.pculpId = pculpId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	

}
