package com.fusionx.lending.origination.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 	Other Income Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-09-2021   FXL-78  	     FXL-777       Dilki        Created
 *    
 ********************************************************************************************************
*/
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
import com.fusionx.lending.origination.enums.SourceTypeEnum;

import lombok.Data;

@Entity
@Data
@Table(name = "other_income_details")
public class OtherIncomeDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4863037973161716898L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "other_income_type_id")
	private OtherIncomeCategory otherIncomeTypeId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "income_source_details_id")
	private IncomeSourceDetails incomeSourceDetailsId;

	@Column(name = "description", length = 350, nullable = false)
	private String description;

	@Column(name = "behaviour_id", nullable = true)
	private Long behaviourId;

	@Column(name = "behaviour_code", nullable = true)
	private String behaviourCode;

	@Column(name = "income_level_id", nullable = true)
	private Long incomeLevelId;

	@Column(name = "income_level_code", nullable = true)
	private String incomeLevelCode;

	@Column(name = "no_of_years_earned", nullable = true)
	private Long noOfYearsEarned;

	@Column(name = "no_of_months_earned", nullable = true)
	private Long noOfMonthsEarned;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "source_type", length = 20, nullable = false)
	private SourceTypeEnum sourceType;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length = 20, nullable = false)
	private CommonStatus status;

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", nullable = true, length = 255)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;
	
	@Transient
	private List<OtherIncomeDetailDocuments> documentList;

	public String getTenantId() {
		return tenantId;
	}

	public OtherIncomeCategory getOtherIncomeTypeId() {
		return otherIncomeTypeId;
	}

	public IncomeSourceDetails getIncomeSourceDetailsId() {
		return incomeSourceDetailsId;
	}

	public String getDescription() {
		return description;
	}

	public Long getBehaviourId() {
		return behaviourId;
	}

	public String getBehaviourCode() {
		return behaviourCode;
	}

	public Long getIncomeLevelId() {
		return incomeLevelId;
	}

	public String getIncomeLevelCode() {
		return incomeLevelCode;
	}

	public Long getNoOfYearsEarned() {
		return noOfYearsEarned;
	}

	public Long getNoOfMonthsEarned() {
		return noOfMonthsEarned;
	}

	public SourceTypeEnum getSourceType() {
		return sourceType;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setOtherIncomeTypeId(OtherIncomeCategory otherIncomeTypeId) {
		this.otherIncomeTypeId = otherIncomeTypeId;
	}

	public void setIncomeSourceDetailsId(IncomeSourceDetails incomeSourceDetailsId) {
		this.incomeSourceDetailsId = incomeSourceDetailsId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBehaviourId(Long behaviourId) {
		this.behaviourId = behaviourId;
	}

	public void setBehaviourCode(String behaviourCode) {
		this.behaviourCode = behaviourCode;
	}

	public void setIncomeLevelId(Long incomeLevelId) {
		this.incomeLevelId = incomeLevelId;
	}

	public void setIncomeLevelCode(String incomeLevelCode) {
		this.incomeLevelCode = incomeLevelCode;
	}

	public void setNoOfYearsEarned(Long noOfYearsEarned) {
		this.noOfYearsEarned = noOfYearsEarned;
	}

	public void setNoOfMonthsEarned(Long noOfMonthsEarned) {
		this.noOfMonthsEarned = noOfMonthsEarned;
	}

	public void setSourceType(SourceTypeEnum sourceType) {
		this.sourceType = sourceType;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<OtherIncomeDetailDocuments> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<OtherIncomeDetailDocuments> documentList) {
		this.documentList = documentList;
	}

}
