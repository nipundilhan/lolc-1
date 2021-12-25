package com.fusionx.lending.origination.resource;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RelationshipBasicInfoRequestResource extends PersonRequestResource{

	@JsonProperty("curStatus")
	@NotBlank(message = "{curStatus.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{curStatus.pattern}")
	private String curStatus;
	
	@JsonProperty("curRelationshipTypeCommonListId")
	@Pattern(regexp = "^$|[0-9]+", message = "{curRelationshipTypeCommonListId.pattern}")
	private String curRelationshipTypeCommonListId;
	private String curRelationshipTypeDesc;
	
	@JsonProperty("curDependentStatus")
	@NotBlank(message = "{curDependentStatus.not-null}")
	@Pattern(regexp = "YES|NO", message = "{curDependentStatus.pattern}")
	private String curDependentStatus;
	
	@JsonProperty("curNomineeStatus")
	@NotBlank(message = "{curNomineeStatus.not-null}")
	@Pattern(regexp = "YES|NO", message = "{curNomineeStatus.pattern}")
	private String curNomineeStatus;
	
	@JsonProperty("curProportionForTheNominee")
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{curProportionForTheNominee.pattern}")
	private String curProportionForTheNominee;
	
	@JsonProperty("curExisitingCustomerId")
	@Pattern(regexp = "^$|[0-9]+", message = "{curExisitingCustomerId.pattern}")
	private String curExisitingCustomerId;
	
	@JsonProperty("curExisitingCustomerReferenceCode")
	@Size(max = 15, message = "{cusReferenceCode.size}")
	private String curExisitingCustomerReferenceCode;
	
	@JsonProperty("perIndividualInf")
	private String perIndividualInf;
	
	@JsonProperty("perIndividualInfo")
	@NotNull(message = "{perIndividualInfo.not-null}")
	@Valid
	private PersonIndividualInfoRequestResource perIndividualInfo;

	public RelationshipBasicInfoRequestResource() {
		super();
	}

	public String getCurStatus() {
		return curStatus;
	}

	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}

	public String getCurRelationshipTypeCommonListId() {
		return curRelationshipTypeCommonListId;
	}

	public void setCurRelationshipTypeCommonListId(String curRelationshipTypeCommonListId) {
		this.curRelationshipTypeCommonListId = curRelationshipTypeCommonListId;
	}

	public String getCurRelationshipTypeDesc() {
		return curRelationshipTypeDesc;
	}

	public void setCurRelationshipTypeDesc(String curRelationshipTypeDesc) {
		this.curRelationshipTypeDesc = curRelationshipTypeDesc;
	}

	public String getCurDependentStatus() {
		return curDependentStatus;
	}

	public void setCurDependentStatus(String curDependentStatus) {
		this.curDependentStatus = curDependentStatus;
	}

	public String getCurNomineeStatus() {
		return curNomineeStatus;
	}

	public void setCurNomineeStatus(String curNomineeStatus) {
		this.curNomineeStatus = curNomineeStatus;
	}

	public String getCurProportionForTheNominee() {
		return curProportionForTheNominee;
	}

	public void setCurProportionForTheNominee(String curProportionForTheNominee) {
		this.curProportionForTheNominee = curProportionForTheNominee;
	}
	
	public String getCurExisitingCustomerId() {
		return curExisitingCustomerId;
	}

	public void setCurExisitingCustomerId(String curExisitingCustomerId) {
		this.curExisitingCustomerId = curExisitingCustomerId;
	}

	public String getCurExisitingCustomerReferenceCode() {
		return curExisitingCustomerReferenceCode;
	}

	public void setCurExisitingCustomerReferenceCode(String curExisitingCustomerReferenceCode) {
		this.curExisitingCustomerReferenceCode = curExisitingCustomerReferenceCode;
	}

	public String getPerIndividualInf() {
		return perIndividualInf;
	}

	public void setPerIndividualInf(String perIndividualInf) {
		this.perIndividualInf = perIndividualInf;
	}

	public PersonIndividualInfoRequestResource getPerIndividualInfo() {
		return perIndividualInfo;
	}

	public void setPerIndividualInfo(PersonIndividualInfoRequestResource perIndividualInfo) {
		this.perIndividualInfo = perIndividualInfo;
	}
}
