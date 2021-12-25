package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PersonIndividualInfoRequestResource {

	@JsonProperty("perTitleCommonListId")
	@NotBlank(message = "{perTitleCommonListId.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{perTitleCommonListId.pattern}")
	private String perTitleCommonListId;

	private String perTitleDesc;

	@JsonProperty("perFirstName")
	@NotBlank(message = "{perFirstName.not-null}")
	@Size(max = 255, message = "{perFirstName.size}")
	private String perFirstName;

	@JsonProperty("perMiddleName")
	@Size(max = 255, message = "{perMiddleName.size}")
	private String perMiddleName;

	@JsonProperty("perLastName")
	@NotBlank(message = "{perLastName.not-null}")
	@Size(max = 255, message = "{perLastName.size}")
	private String perLastName;

	@JsonProperty("perFullName")
	@Size(max = 255, message = "{perFullName.size}")
	private String perFullName;

	@JsonProperty("perInitials")
	@Size(max = 255, message = "{perInitials.size}")
	private String perInitials;

	@JsonProperty("perPreferredName")
	@Size(max = 255, message = "{perPreferredName.size}")
	private String perPreferredName;

	@JsonProperty("perOtherName")
	@Size(max = 255, message = "{perOtherName.size}")
	private String perOtherName;

	@JsonProperty("perGenderCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perGenderCommonListId.pattern}")
	private String perGenderCommonListId;

	private String perGenderDesc;

	@JsonProperty("perBirthPlace")
	@Size(max = 255, message = "{perBirthPlace.size}")
	private String perBirthPlace;

	@JsonProperty("perDateOfBirth")
	@Pattern(regexp = "^$|\\d{4}(\\/)(((0)[0-9])|((1)[0-2]))(\\/)([0-2][0-9]|(3)[0-1])$", message = "{perDateOfBirth.pattern}")
	private String perDateOfBirth;

	@JsonProperty("perBirthCountryId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perBirthCountryId.pattern}")
	private String perBirthCountryId;

	private String perBirthCountryDesc;

	@JsonProperty("perNationality1CommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perNationalityCommonListId.pattern}")
	private String perNationality1CommonListId;
	private String perNationality1Desc;
	
	@JsonProperty("perNationality2CommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perNationalityCommonListId.pattern}")
	private String perNationality2CommonListId;
	private String perNationality2Desc;
	
	@JsonProperty("perNationality3CommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perNationalityCommonListId.pattern}")
	private String perNationality3CommonListId;
	private String perNationality3Desc;

	@JsonProperty("perNationality1CountryId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perNationalityCountryId.pattern}")
	private String perNationality1CountryId;
	private String perNationality1CountryDesc;
	
	@JsonProperty("perNationality2CountryId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perNationalityCountryId.pattern}")
	private String perNationality2CountryId;
	private String perNationality2CountryDesc;
	
	@JsonProperty("perNationality3CountryId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perNationalityCountryId.pattern}")
	private String perNationality3CountryId;
	private String perNationality3CountryDesc;

	@JsonProperty("perMaritalStatusCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perMaritalStatusCommonListId.pattern}")
	private String perMaritalStatusCommonListId;
	private String perMaritalStatusDesc;

	@JsonProperty("perResidentStatusCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perResidentStatusCommonListId.pattern}")
	private String perResidentStatusCommonListId;
	private String perResidentStatusDesc;
	
	@JsonProperty("perResident1CountryId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perResidentCountryId.pattern}")
	private String perResident1CountryId;
	private String perResident1CountryDesc;
	
	@JsonProperty("perResident2CountryId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perResidentCountryId.pattern}")
	private String perResident2CountryId;
	private String perResident2CountryDesc;
	
	@JsonProperty("perResident3CountryId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perResidentCountryId.pattern}")
	private String perResident3CountryId;
	private String perResident3CountryDesc;

	@JsonProperty("perEducationalLevelCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perEducationalLevelCommonListId.pattern}")
	private String perEducationalLevelCommonListId;
	private String perEducationalLevelDesc;
	
	public PersonIndividualInfoRequestResource() {
		super();
	}

	public String getPerTitleCommonListId() {
		return perTitleCommonListId;
	}

	public void setPerTitleCommonListId(String perTitleCommonListId) {
		this.perTitleCommonListId = perTitleCommonListId;
	}

	public String getPerTitleDesc() {
		return perTitleDesc;
	}

	public void setPerTitleDesc(String perTitleDesc) {
		this.perTitleDesc = perTitleDesc;
	}

	public String getPerFirstName() {
		return perFirstName;
	}

	public void setPerFirstName(String perFirstName) {
		this.perFirstName = perFirstName;
	}

	public String getPerMiddleName() {
		return perMiddleName;
	}

	public void setPerMiddleName(String perMiddleName) {
		this.perMiddleName = perMiddleName;
	}

	public String getPerLastName() {
		return perLastName;
	}

	public void setPerLastName(String perLastName) {
		this.perLastName = perLastName;
	}

	public String getPerFullName() {
		return perFullName;
	}

	public void setPerFullName(String perFullName) {
		this.perFullName = perFullName;
	}

	public String getPerInitials() {
		return perInitials;
	}

	public void setPerInitials(String perInitials) {
		this.perInitials = perInitials;
	}

	public String getPerPreferredName() {
		return perPreferredName;
	}

	public void setPerPreferredName(String perPreferredName) {
		this.perPreferredName = perPreferredName;
	}

	public String getPerOtherName() {
		return perOtherName;
	}

	public void setPerOtherName(String perOtherName) {
		this.perOtherName = perOtherName;
	}

	public String getPerGenderCommonListId() {
		return perGenderCommonListId;
	}

	public void setPerGenderCommonListId(String perGenderCommonListId) {
		this.perGenderCommonListId = perGenderCommonListId;
	}

	public String getPerGenderDesc() {
		return perGenderDesc;
	}

	public void setPerGenderDesc(String perGenderDesc) {
		this.perGenderDesc = perGenderDesc;
	}

	public String getPerBirthPlace() {
		return perBirthPlace;
	}

	public void setPerBirthPlace(String perBirthPlace) {
		this.perBirthPlace = perBirthPlace;
	}

	public String getPerDateOfBirth() {
		return perDateOfBirth;
	}

	public void setPerDateOfBirth(String perDateOfBirth) {
		this.perDateOfBirth = perDateOfBirth;
	}

	public String getPerBirthCountryId() {
		return perBirthCountryId;
	}

	public void setPerBirthCountryId(String perBirthCountryId) {
		this.perBirthCountryId = perBirthCountryId;
	}

	public String getPerBirthCountryDesc() {
		return perBirthCountryDesc;
	}

	public void setPerBirthCountryDesc(String perBirthCountryDesc) {
		this.perBirthCountryDesc = perBirthCountryDesc;
	}

	public String getPerMaritalStatusCommonListId() {
		return perMaritalStatusCommonListId;
	}

	public void setPerMaritalStatusCommonListId(String perMaritalStatusCommonListId) {
		this.perMaritalStatusCommonListId = perMaritalStatusCommonListId;
	}

	public String getPerMaritalStatusDesc() {
		return perMaritalStatusDesc;
	}

	public void setPerMaritalStatusDesc(String perMaritalStatusDesc) {
		this.perMaritalStatusDesc = perMaritalStatusDesc;
	}

	public String getPerResidentStatusCommonListId() {
		return perResidentStatusCommonListId;
	}

	public void setPerResidentStatusCommonListId(String perResidentStatusCommonListId) {
		this.perResidentStatusCommonListId = perResidentStatusCommonListId;
	}

	public String getPerResidentStatusDesc() {
		return perResidentStatusDesc;
	}

	public void setPerResidentStatusDesc(String perResidentStatusDesc) {
		this.perResidentStatusDesc = perResidentStatusDesc;
	}

	public String getPerEducationalLevelCommonListId() {
		return perEducationalLevelCommonListId;
	}

	public void setPerEducationalLevelCommonListId(String perEducationalLevelCommonListId) {
		this.perEducationalLevelCommonListId = perEducationalLevelCommonListId;
	}

	public String getPerEducationalLevelDesc() {
		return perEducationalLevelDesc;
	}

	public void setPerEducationalLevelDesc(String perEducationalLevelDesc) {
		this.perEducationalLevelDesc = perEducationalLevelDesc;
	}

	public String getPerNationality1CommonListId() {
		return perNationality1CommonListId;
	}

	public void setPerNationality1CommonListId(String perNationality1CommonListId) {
		this.perNationality1CommonListId = perNationality1CommonListId;
	}

	public String getPerNationality1Desc() {
		return perNationality1Desc;
	}

	public void setPerNationality1Desc(String perNationality1Desc) {
		this.perNationality1Desc = perNationality1Desc;
	}

	public String getPerNationality2CommonListId() {
		return perNationality2CommonListId;
	}

	public void setPerNationality2CommonListId(String perNationality2CommonListId) {
		this.perNationality2CommonListId = perNationality2CommonListId;
	}

	public String getPerNationality2Desc() {
		return perNationality2Desc;
	}

	public void setPerNationality2Desc(String perNationality2Desc) {
		this.perNationality2Desc = perNationality2Desc;
	}

	public String getPerNationality3CommonListId() {
		return perNationality3CommonListId;
	}

	public void setPerNationality3CommonListId(String perNationality3CommonListId) {
		this.perNationality3CommonListId = perNationality3CommonListId;
	}

	public String getPerNationality3Desc() {
		return perNationality3Desc;
	}

	public void setPerNationality3Desc(String perNationality3Desc) {
		this.perNationality3Desc = perNationality3Desc;
	}

	public String getPerNationality1CountryId() {
		return perNationality1CountryId;
	}

	public void setPerNationality1CountryId(String perNationality1CountryId) {
		this.perNationality1CountryId = perNationality1CountryId;
	}

	public String getPerNationality1CountryDesc() {
		return perNationality1CountryDesc;
	}

	public void setPerNationality1CountryDesc(String perNationality1CountryDesc) {
		this.perNationality1CountryDesc = perNationality1CountryDesc;
	}

	public String getPerNationality2CountryId() {
		return perNationality2CountryId;
	}

	public void setPerNationality2CountryId(String perNationality2CountryId) {
		this.perNationality2CountryId = perNationality2CountryId;
	}

	public String getPerNationality2CountryDesc() {
		return perNationality2CountryDesc;
	}

	public void setPerNationality2CountryDesc(String perNationality2CountryDesc) {
		this.perNationality2CountryDesc = perNationality2CountryDesc;
	}

	public String getPerNationality3CountryId() {
		return perNationality3CountryId;
	}

	public void setPerNationality3CountryId(String perNationality3CountryId) {
		this.perNationality3CountryId = perNationality3CountryId;
	}

	public String getPerNationality3CountryDesc() {
		return perNationality3CountryDesc;
	}

	public void setPerNationality3CountryDesc(String perNationality3CountryDesc) {
		this.perNationality3CountryDesc = perNationality3CountryDesc;
	}

	public String getPerResident1CountryId() {
		return perResident1CountryId;
	}

	public void setPerResident1CountryId(String perResident1CountryId) {
		this.perResident1CountryId = perResident1CountryId;
	}

	public String getPerResident1CountryDesc() {
		return perResident1CountryDesc;
	}

	public void setPerResident1CountryDesc(String perResident1CountryDesc) {
		this.perResident1CountryDesc = perResident1CountryDesc;
	}

	public String getPerResident2CountryId() {
		return perResident2CountryId;
	}

	public void setPerResident2CountryId(String perResident2CountryId) {
		this.perResident2CountryId = perResident2CountryId;
	}

	public String getPerResident2CountryDesc() {
		return perResident2CountryDesc;
	}

	public void setPerResident2CountryDesc(String perResident2CountryDesc) {
		this.perResident2CountryDesc = perResident2CountryDesc;
	}

	public String getPerResident3CountryId() {
		return perResident3CountryId;
	}

	public void setPerResident3CountryId(String perResident3CountryId) {
		this.perResident3CountryId = perResident3CountryId;
	}

	public String getPerResident3CountryDesc() {
		return perResident3CountryDesc;
	}

	public void setPerResident3CountryDesc(String perResident3CountryDesc) {
		this.perResident3CountryDesc = perResident3CountryDesc;
	}
}
