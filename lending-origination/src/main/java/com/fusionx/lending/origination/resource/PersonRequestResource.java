package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
@Data
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PersonRequestResource{

	@JsonProperty("penPerId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perId.pattern}")
	private String penPerId;
	
	@JsonProperty("perId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perId.pattern}")
	private String perId;
	
	@JsonProperty("perCode")
	@NotBlank(message = "{perCode.not-null}")
	@Size(max = 15, message = "{perCode.size}")
	private String perCode;

	@JsonProperty("perPreferredLanguageId")
	@Pattern(regexp = "^$|[0-9]+", message = "{perPreferredLanguageId.pattern}")
	private String perPreferredLanguageId;

	private String perPreferredLanguageDesc;

	@JsonProperty("perContactPerson")
	@Size(max = 255, message = "{perContactPerson.size}")
	private String perContactPerson;
	
	@JsonProperty("perComment")
	@Size(max = 255, message = "{perComment.size}")
	private String perComment;
	
	@JsonProperty("perAttribute1")
	@Pattern(regexp = "^$|[0-9]+", message = "{perAttribute1.pattern}")
	private String perAttribute1;
	
	@JsonProperty("perAttribute2")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute2;
	
	@JsonProperty("perAttribute3")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute3;
	
	@JsonProperty("perAttribute4")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute4;
	
	@JsonProperty("perAttribute5")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute5;
	
	@JsonProperty("perAttribute6")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute6;
	
	@JsonProperty("perAttribute7")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute7;
	
	@JsonProperty("perAttribute8")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute8;
	
	@JsonProperty("perAttribute9")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute9;
	
	@JsonProperty("perAttribute10")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute10;
	
	@JsonProperty("perAttribute11")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute11;
	
	@JsonProperty("perAttribute12")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute12;
	
	@JsonProperty("perAttribute13")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute13;
	
	@JsonProperty("perAttribute14")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute14;
	
	@JsonProperty("perAttribute15")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute15;
	
	@JsonProperty("perAttribute16")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute16;
	
	@JsonProperty("perAttribute17")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute17;
	
	@JsonProperty("perAttribute18")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute18;
	
	@JsonProperty("perAttribute19")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute19;
	
	@JsonProperty("perAttribute20")
	@Size(max = 255, message = "{perAttribute.size}")
	private String perAttribute20;
	
	@JsonProperty("perKYCId")
	@Pattern(regexp = "^$|[0-9]+",message="{perKYCId.pattern}")
	private String perKYCId;
	
	@JsonProperty("perRiskProfileId")
	@Pattern(regexp = "^$|[0-9]+",message="{perRiskProfileId.pattern}")
	private String perRiskProfileId;
	
	@JsonProperty("perPepStatus")
	@Pattern(regexp = "^$|YES|NO", message = "{perPepStatus.pattern}")
	private String perPepStatus;
	
	private String perPepComment;
	
	private String perStatus;

	public PersonRequestResource() {
		super();
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

	public String getPerPreferredLanguageId() {
		return perPreferredLanguageId;
	}

	public void setPerPreferredLanguageId(String perPreferredLanguageId) {
		this.perPreferredLanguageId = perPreferredLanguageId;
	}

	public String getPerPreferredLanguageDesc() {
		return perPreferredLanguageDesc;
	}

	public void setPerPreferredLanguageDesc(String perPreferredLanguageDesc) {
		this.perPreferredLanguageDesc = perPreferredLanguageDesc;
	}

	public String getPerContactPerson() {
		return perContactPerson;
	}

	public void setPerContactPerson(String perContactPerson) {
		this.perContactPerson = perContactPerson;
	}

	public String getPerComment() {
		return perComment;
	}

	public void setPerComment(String perComment) {
		this.perComment = perComment;
	}

	public String getPerAttribute1() {
		return perAttribute1;
	}

	public void setPerAttribute1(String perAttribute1) {
		this.perAttribute1 = perAttribute1;
	}

	public String getPerAttribute2() {
		return perAttribute2;
	}

	public void setPerAttribute2(String perAttribute2) {
		this.perAttribute2 = perAttribute2;
	}

	public String getPerAttribute3() {
		return perAttribute3;
	}

	public void setPerAttribute3(String perAttribute3) {
		this.perAttribute3 = perAttribute3;
	}

	public String getPerAttribute4() {
		return perAttribute4;
	}

	public void setPerAttribute4(String perAttribute4) {
		this.perAttribute4 = perAttribute4;
	}

	public String getPerAttribute5() {
		return perAttribute5;
	}

	public void setPerAttribute5(String perAttribute5) {
		this.perAttribute5 = perAttribute5;
	}

	public String getPerAttribute6() {
		return perAttribute6;
	}

	public void setPerAttribute6(String perAttribute6) {
		this.perAttribute6 = perAttribute6;
	}

	public String getPerAttribute7() {
		return perAttribute7;
	}

	public void setPerAttribute7(String perAttribute7) {
		this.perAttribute7 = perAttribute7;
	}

	public String getPerAttribute8() {
		return perAttribute8;
	}

	public void setPerAttribute8(String perAttribute8) {
		this.perAttribute8 = perAttribute8;
	}

	public String getPerAttribute9() {
		return perAttribute9;
	}

	public void setPerAttribute9(String perAttribute9) {
		this.perAttribute9 = perAttribute9;
	}

	public String getPerAttribute10() {
		return perAttribute10;
	}

	public void setPerAttribute10(String perAttribute10) {
		this.perAttribute10 = perAttribute10;
	}

	public String getPerAttribute11() {
		return perAttribute11;
	}

	public void setPerAttribute11(String perAttribute11) {
		this.perAttribute11 = perAttribute11;
	}

	public String getPerAttribute12() {
		return perAttribute12;
	}

	public void setPerAttribute12(String perAttribute12) {
		this.perAttribute12 = perAttribute12;
	}

	public String getPerAttribute13() {
		return perAttribute13;
	}

	public void setPerAttribute13(String perAttribute13) {
		this.perAttribute13 = perAttribute13;
	}

	public String getPerAttribute14() {
		return perAttribute14;
	}

	public void setPerAttribute14(String perAttribute14) {
		this.perAttribute14 = perAttribute14;
	}

	public String getPerAttribute15() {
		return perAttribute15;
	}

	public void setPerAttribute15(String perAttribute15) {
		this.perAttribute15 = perAttribute15;
	}

	public String getPerAttribute16() {
		return perAttribute16;
	}

	public void setPerAttribute16(String perAttribute16) {
		this.perAttribute16 = perAttribute16;
	}

	public String getPerAttribute17() {
		return perAttribute17;
	}

	public void setPerAttribute17(String perAttribute17) {
		this.perAttribute17 = perAttribute17;
	}

	public String getPerAttribute18() {
		return perAttribute18;
	}

	public void setPerAttribute18(String perAttribute18) {
		this.perAttribute18 = perAttribute18;
	}

	public String getPerAttribute19() {
		return perAttribute19;
	}

	public void setPerAttribute19(String perAttribute19) {
		this.perAttribute19 = perAttribute19;
	}

	public String getPerAttribute20() {
		return perAttribute20;
	}

	public void setPerAttribute20(String perAttribute20) {
		this.perAttribute20 = perAttribute20;
	}

	public String getPerKYCId() {
		return perKYCId;
	}

	public void setPerKYCId(String perKYCId) {
		this.perKYCId = perKYCId;
	}

	public String getPerRiskProfileId() {
		return perRiskProfileId;
	}

	public void setPerRiskProfileId(String perRiskProfileId) {
		this.perRiskProfileId = perRiskProfileId;
	}
	
	public String getPerPepStatus() {
		return perPepStatus;
	}

	public void setPerPepStatus(String perPepStatus) {
		this.perPepStatus = perPepStatus;
	}

	public String getPerStatus() {
		return perStatus;
	}

	public void setPerStatus(String perStatus) {
		this.perStatus = perStatus;
	}

	public String getPerPepComment() {
		return perPepComment;
	}

	public void setPerPepComment(String perPepComment) {
		this.perPepComment = perPepComment;
	}

	public String getPenPerId() {
		return penPerId;
	}

	public void setPenPerId(String penPerId) {
		this.penPerId = penPerId;
	}
}
