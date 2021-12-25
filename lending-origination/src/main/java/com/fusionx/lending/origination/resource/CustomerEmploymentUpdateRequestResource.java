package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 	Customer Employment Update Request Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-115  	 FXL-658       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CustomerEmploymentUpdateRequestResource {
	
	  private Long cueId;
	  private Long cueEmploymentTypeCommonListId;
	  private String cueEmploymentTypeDesc;
	  private Long cueEmploymentCategoryCommonListId;
	  private String cueEmploymentCategoryDesc;
	  private Long cueDesignationId;
	  private String cueDesignationDesc;
	  private String cueEmployerCode;
	  private String cueEmployerName;
	  private Long cueWorkingYears;
	  private Long cueWorkingMonths;
	  private String cueJoinedDate;
	  private String cueConfirmedDate;
	  private String cueClosingDate;
	  private String cueContactNo;
	  private String cueReferenceNo;
	  private String cueStatus;
	  
	public Long getCueId() {
		return cueId;
	}
	public void setCueId(Long cueId) {
		this.cueId = cueId;
	}
	public Long getCueEmploymentTypeCommonListId() {
		return cueEmploymentTypeCommonListId;
	}
	public void setCueEmploymentTypeCommonListId(Long cueEmploymentTypeCommonListId) {
		this.cueEmploymentTypeCommonListId = cueEmploymentTypeCommonListId;
	}
	public String getCueEmploymentTypeDesc() {
		return cueEmploymentTypeDesc;
	}
	public void setCueEmploymentTypeDesc(String cueEmploymentTypeDesc) {
		this.cueEmploymentTypeDesc = cueEmploymentTypeDesc;
	}
	public Long getCueEmploymentCategoryCommonListId() {
		return cueEmploymentCategoryCommonListId;
	}
	public void setCueEmploymentCategoryCommonListId(Long cueEmploymentCategoryCommonListId) {
		this.cueEmploymentCategoryCommonListId = cueEmploymentCategoryCommonListId;
	}
	public String getCueEmploymentCategoryDesc() {
		return cueEmploymentCategoryDesc;
	}
	public void setCueEmploymentCategoryDesc(String cueEmploymentCategoryDesc) {
		this.cueEmploymentCategoryDesc = cueEmploymentCategoryDesc;
	}
	public Long getCueDesignationId() {
		return cueDesignationId;
	}
	public void setCueDesignationId(Long cueDesignationId) {
		this.cueDesignationId = cueDesignationId;
	}
	public String getCueDesignationDesc() {
		return cueDesignationDesc;
	}
	public void setCueDesignationDesc(String cueDesignationDesc) {
		this.cueDesignationDesc = cueDesignationDesc;
	}
	public String getCueEmployerCode() {
		return cueEmployerCode;
	}
	public void setCueEmployerCode(String cueEmployerCode) {
		this.cueEmployerCode = cueEmployerCode;
	}
	public String getCueEmployerName() {
		return cueEmployerName;
	}
	public void setCueEmployerName(String cueEmployerName) {
		this.cueEmployerName = cueEmployerName;
	}
	public Long getCueWorkingYears() {
		return cueWorkingYears;
	}
	public void setCueWorkingYears(Long cueWorkingYears) {
		this.cueWorkingYears = cueWorkingYears;
	}
	public Long getCueWorkingMonths() {
		return cueWorkingMonths;
	}
	public void setCueWorkingMonths(Long cueWorkingMonths) {
		this.cueWorkingMonths = cueWorkingMonths;
	}
	public String getCueJoinedDate() {
		return cueJoinedDate;
	}
	public void setCueJoinedDate(String cueJoinedDate) {
		this.cueJoinedDate = cueJoinedDate;
	}
	public String getCueConfirmedDate() {
		return cueConfirmedDate;
	}
	public void setCueConfirmedDate(String cueConfirmedDate) {
		this.cueConfirmedDate = cueConfirmedDate;
	}
	public String getCueClosingDate() {
		return cueClosingDate;
	}
	public void setCueClosingDate(String cueClosingDate) {
		this.cueClosingDate = cueClosingDate;
	}
	public String getCueContactNo() {
		return cueContactNo;
	}
	public void setCueContactNo(String cueContactNo) {
		this.cueContactNo = cueContactNo;
	}
	public String getCueStatus() {
		return cueStatus;
	}
	public void setCueStatus(String cueStatus) {
		this.cueStatus = cueStatus;
	}
	public String getCueReferenceNo() {
		return cueReferenceNo;
	}
	public void setCueReferenceNo(String cueReferenceNo) {
		this.cueReferenceNo = cueReferenceNo;
	}

}
