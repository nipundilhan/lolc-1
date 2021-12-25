package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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
import com.fusionx.lending.origination.enums.EmploymentTypeEnum;
import com.fusionx.lending.origination.enums.SourceTypeEnum;

import lombok.Data;

/**
 * 	Cultivation Income Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021    	         FXL-661       Dilhan       Created
 *    
 ********************************************************************************************************
*/

@Entity
@Data          
@Table(name = "cultivation_income_details")
public class CultivationIncomeDetails extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -1718460056913670028L;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "income_source_details_id" ,nullable=false)
	private IncomeSourceDetails incomeSourceDetail;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "cultivation_category_id" ,nullable=false)
	private CultivationCategory cultivationCategory;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "land_ownership_id" ,nullable=false)
	private CommonList cmnListLandOwnership;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "plant_ownership_id" ,nullable=false)
	private CommonList cmnListPlantOwnership;
	
	@Column(name = "land_size", length=300, nullable=true)
	private String landSize;
	
	@Column(name = "description", length=300, nullable=true)
	private String description;
	
	@Column(name = "no_of_employees" ,nullable=false)
	private Integer noOfEmployees;
	
	@Column(name = "no_of_years" ,nullable=false)
	private Integer noOfYears;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "cultivation_source_type", length=20, nullable=false)
	private SourceTypeEnum sourceType;
	
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
	private List<CultivationIncomeDocuments> cultivationIncomeDocuments;

	@Transient
	private String cultivationCategoryName;
	
	@Transient
	private String landOwnershipName;
	
	@Transient
	private String plantOwnershipName;
	
	@Transient
	private Long cultivationCategoryId;
	
//	@Transient
//	private Long landOwnershipId;
//	
//	@Transient
//	private Long plantOwnershipId;

	public CommonList getCmnListLandOwnership() {
		return cmnListLandOwnership;
	}

	public void setCmnListLandOwnership(CommonList cmnListLandOwnership) {
		this.cmnListLandOwnership = cmnListLandOwnership;
	}

	public CommonList getCmnListPlantOwnership() {
		return cmnListPlantOwnership;
	}

	public void setCmnListPlantOwnership(CommonList cmnListPlantOwnership) {
		this.cmnListPlantOwnership = cmnListPlantOwnership;
	}

	public Long getCultivationCategoryId() {
		return cultivationCategory.getId();
	}

	public Long getLandOwnershipId() {
		return cmnListLandOwnership.getId();
	}

	public Long getPlantOwnershipId() {
		return cmnListPlantOwnership.getId();
	}

	public String getPlantOwnershipName() {
		return cmnListPlantOwnership.getName();
	}

	public String getCultivationCategoryName() {
		return cultivationCategory.getName();
	}

	public String getLandOwnershipName() {
		return cmnListLandOwnership.getName();
	}

	public List<CultivationIncomeDocuments> getCultivationIncomeDocuments() {
		return cultivationIncomeDocuments;
	}

	public void setCultivationIncomeDocuments(List<CultivationIncomeDocuments> cultivationIncomeDocuments) {
		this.cultivationIncomeDocuments = cultivationIncomeDocuments;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public IncomeSourceDetails getIncomeSourceDetail() {
		return incomeSourceDetail;
	}

	public void setIncomeSourceDetail(IncomeSourceDetails incomeSourceDetail) {
		this.incomeSourceDetail = incomeSourceDetail;
	}

	public CultivationCategory getCultivationCategory() {
		return cultivationCategory;
	}

	public void setCultivationCategory(CultivationCategory cultivationCategory) {
		this.cultivationCategory = cultivationCategory;
	}

	public String getLandSize() {
		return landSize;
	}

	public void setLandSize(String landSize) {
		this.landSize = landSize;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(Integer noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}

	public Integer getNoOfYears() {
		return noOfYears;
	}

	public void setNoOfYears(Integer noOfYears) {
		this.noOfYears = noOfYears;
	}

	public SourceTypeEnum getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceTypeEnum sourceType) {
		this.sourceType = sourceType;
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
	
}
