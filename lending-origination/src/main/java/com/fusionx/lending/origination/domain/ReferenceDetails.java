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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

@Entity
@Table(name = "reference_details")
public class ReferenceDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5407674745137767244L;

	@Column(name = "tenant_id", length = 10, nullable = false)
	private String tenantId;

	@Column(name = "name", length = 70, nullable = false)
	private String name;

	@Column(name = "professional_status", length = 70, nullable = false)
	private String professionalStatus;

	@Column(name = "business_employer", length = 70, nullable = false)
	private String businessEmployer;

	@Column(name = "current_address_line1", length = 70, nullable = false)
	private String currentAddressLine1;

	@Column(name = "current_address_line2", length = 70, nullable = false)
	private String currentAddressLine2;

	@Column(name = "current_address_line3", length = 70, nullable = false)
	private String currentAddressLine3;

	@Column(name = "current_address_line4", length = 70, nullable = false)
	private String currentAddressLine4;

	@Column(name = "permanent_address_line1", length = 70, nullable = false)
	private String permanentAddressLine1;

	@Column(name = "permanent_address_line2", length = 70, nullable = false)
	private String permanentAddressLine2;

	@Column(name = "permanent_address_line3", length = 70, nullable = false)
	private String permanentAddressLine3;

	@Column(name = "permanent_address_line4", length = 70, nullable = false)
	private String permanentAddressLine4;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private CommonStatus status;

	@Column(name = "created_user", length = 255, nullable = false)
	private String createdUser;

	@Column(name = "created_date", nullable = false)
	private Timestamp createdDate;

	@Column(name = "modified_user", nullable = true, length = 255)
	private String modifiedUser;

	@Column(name = "modified_date", nullable = true)
	private Timestamp modifiedDate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lead_info_id", nullable = false)
	private LeadInfo leadInfo;

	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<ReferenceDetailsContactInfo> referenceDetailsContactInfo;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfessionalStatus() {
		return professionalStatus;
	}

	public void setProfessionalStatus(String professionalStatus) {
		this.professionalStatus = professionalStatus;
	}

	public String getBusinessEmployer() {
		return businessEmployer;
	}

	public void setBusinessEmployer(String businessEmployer) {
		this.businessEmployer = businessEmployer;
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

	public LeadInfo getLeadInfo() {
		return leadInfo;
	}

	public void setLeadInfo(LeadInfo leadInfo) {
		this.leadInfo = leadInfo;
	}

	public List<ReferenceDetailsContactInfo> getReferenceDetailsContactInfo() {
		return referenceDetailsContactInfo;
	}

	public void setReferenceDetailsContactInfo(List<ReferenceDetailsContactInfo> referenceDetailsContactInfo) {
		this.referenceDetailsContactInfo = referenceDetailsContactInfo;
	}

}
