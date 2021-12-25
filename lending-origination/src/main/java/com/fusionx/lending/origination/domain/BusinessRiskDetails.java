package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;


 /**
 * Business Risk Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   06-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
 */

@Entity
@Table(name = "business_risk_details")
public class BusinessRiskDetails extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 2673861277874403970L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "business_additional_dtl_id" ,nullable=false)
	private BusinessAdditionalDetails businessAdditionalDtl;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "risk_type_id" ,nullable=false)
	private BusinessRiskType riskType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "risk_grading_id" ,nullable=false)
	private RiskGrading riskGrading;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "risk_level_id" )
	private RiskRatingLevels riskRatingLevel;
	
	@Column(name = "calculated_date",nullable=false)
	private Timestamp calculatedDate;

	@Column(name = "certificated_details")
	private String certificatedDetails;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "risk_authority_id")
	private RiskAuthorities riskAuthority;
	
	@Column(name = "document_id")
	private Long documentId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	@Column(name = "created_user")
	private String createdUser;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "modified_user")
	private String modifiedUser;

	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Transient
	private Long riskTypesId;
	
	@Transient
	private String riskTypesName;

	@Transient
	private Long riskGradingsId;

	@Transient
	private String riskGradingsName;

	@Transient
	private Long riskRatingLevelsId;

	@Transient
	private String riskRatingType;

	@Transient
	private Long riskAuthoritysId;

	@Transient
	private String riskAuthoritysName;
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public BusinessAdditionalDetails getBusinessAdditionalDtl() {
		return businessAdditionalDtl;
	}

	public void setBusinessAdditionalDtl(BusinessAdditionalDetails businessAdditionalDtl) {
		this.businessAdditionalDtl = businessAdditionalDtl;
	}

	public BusinessRiskType getRiskType() {
		return riskType;
	}

	public void setRiskType(BusinessRiskType riskType) {
		this.riskType = riskType;
	}

	public RiskGrading getRiskGrading() {
		return riskGrading;
	}

	public void setRiskGrading(RiskGrading riskGrading) {
		this.riskGrading = riskGrading;
	}

	public Timestamp getCalculatedDate() {
		return calculatedDate;
	}

	public void setCalculatedDate(Timestamp calculatedDate) {
		this.calculatedDate = calculatedDate;
	}

	public String getCertificatedDetails() {
		return certificatedDetails;
	}

	public void setCertificatedDetails(String certificatedDetails) {
		this.certificatedDetails = certificatedDetails;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getRiskTypesId() {
		return riskTypesId;
	}

	public void setRiskTypesId(Long riskTypesId) {
		this.riskTypesId = riskTypesId;
	}

	public String getRiskTypesName() {
		return riskTypesName;
	}

	public void setRiskTypesName(String riskTypesName) {
		this.riskTypesName = riskTypesName;
	}

	public String getRiskGradingsName() {
		return riskGradingsName;
	}

	public void setRiskGradingsName(String riskGradingsName) {
		this.riskGradingsName = riskGradingsName;
	}

	public Long getRiskGradingsId() {
		return riskGradingsId;
	}

	public void setRiskGradingsId(Long riskGradingsId) {
		this.riskGradingsId = riskGradingsId;
	}

	public Long getRiskRatingLevelsId() {
		return riskRatingLevelsId;
	}

	public void setRiskRatingLevelsId(Long riskRatingLevelsId) {
		this.riskRatingLevelsId = riskRatingLevelsId;
	}

	public Long getRiskAuthoritysId() {
		return riskAuthoritysId;
	}

	public void setRiskAuthoritysId(Long riskAuthoritysId) {
		this.riskAuthoritysId = riskAuthoritysId;
	}

	public String getRiskAuthoritysName() {
		return riskAuthoritysName;
	}

	public void setRiskAuthoritysName(String riskAuthoritysName) {
		this.riskAuthoritysName = riskAuthoritysName;
	}

	public RiskAuthorities getRiskAuthority() {
		return riskAuthority;
	}

	public void setRiskAuthority(RiskAuthorities riskAuthority) {
		this.riskAuthority = riskAuthority;
	}

	public RiskRatingLevels getRiskRatingLevel() {
		return riskRatingLevel;
	}

	public void setRiskRatingLevel(RiskRatingLevels riskRatingLevel) {
		this.riskRatingLevel = riskRatingLevel;
	}

	public String getRiskRatingType() {
		return riskRatingType;
	}

	public void setRiskRatingType(String riskRatingType) {
		this.riskRatingType = riskRatingType;
	}


	
	
}
