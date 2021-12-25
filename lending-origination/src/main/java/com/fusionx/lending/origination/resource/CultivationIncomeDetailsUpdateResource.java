package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Cultivation Income Details Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021   FXL-115  	 FXL-661       Dilhan       Created
 *    
 ********************************************************************************************************
*/

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CultivationIncomeDetailsUpdateResource {

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String cultivationCategoryId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String cultivationCategoryName;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String landOwnershipId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String landOwnershipName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String plantOwnershipId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String plantOwnershipName;
	
	@Size(max = 350, message = "{common-length01.size}")
	private String description;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String noOfEmployees;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String noOfYears;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 70, message = "{common-name.size}")
	private String sizeOfLand;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "PRIMARY|SECONDARY", message = "{common-source.pattern}")
	private String sourceType;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;
	
	@Valid
	private List<DocumentUpdateResource> cultivationIncomeDocumentList;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCultivationCategoryId() {
		return cultivationCategoryId;
	}

	public void setCultivationCategoryId(String cultivationCategoryId) {
		this.cultivationCategoryId = cultivationCategoryId;
	}

	public String getCultivationCategoryName() {
		return cultivationCategoryName;
	}

	public void setCultivationCategoryName(String cultivationCategoryName) {
		this.cultivationCategoryName = cultivationCategoryName;
	}

	public String getLandOwnershipId() {
		return landOwnershipId;
	}

	public void setLandOwnershipId(String landOwnershipId) {
		this.landOwnershipId = landOwnershipId;
	}

	public String getLandOwnershipName() {
		return landOwnershipName;
	}

	public void setLandOwnershipName(String landOwnershipName) {
		this.landOwnershipName = landOwnershipName;
	}

	public String getPlantOwnershipId() {
		return plantOwnershipId;
	}

	public void setPlantOwnershipId(String plantOwnershipId) {
		this.plantOwnershipId = plantOwnershipId;
	}

	public String getPlantOwnershipName() {
		return plantOwnershipName;
	}

	public void setPlantOwnershipName(String plantOwnershipName) {
		this.plantOwnershipName = plantOwnershipName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(String noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}

	public String getNoOfYears() {
		return noOfYears;
	}

	public void setNoOfYears(String noOfYears) {
		this.noOfYears = noOfYears;
	}

	public String getSizeOfLand() {
		return sizeOfLand;
	}

	public void setSizeOfLand(String sizeOfLand) {
		this.sizeOfLand = sizeOfLand;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DocumentUpdateResource> getCultivationIncomeDocumentList() {
		return cultivationIncomeDocumentList;
	}

	public void setCultivationIncomeDocumentList(List<DocumentUpdateResource> cultivationIncomeDocumentList) {
		this.cultivationIncomeDocumentList = cultivationIncomeDocumentList;
	}

}
