package com.fusionx.lending.product.domain;

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
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;

/**
 * Interest Tier Band Set 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   19-07-2021    FXL_July_2021_2  	FXL-52		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "interest_tier_band_set")
@Data
public class InterestTierBandSet extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 8322697080014706213L;
	
	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "tier_band_method_id", nullable=false)
	private CommonListItem tierBandMethod;
	
	@Transient
    private Long tierBandMethodId;
	
	@Transient
    private String commonListCode;
	
	@Transient
    private String commonListRefCode;
	
	@Column(name = "code", length=4, nullable=false)
	private String code;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "calculation_method_id", nullable=false)
	private CommonListItem calculationMethod;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "interest_template_id", nullable = false)
	private InterestTemplate interestTemplate;
	
	@Transient
    private Long interestTemplatesId;
	
	@Transient
    private String interestTemplatesCode;
	
	@Column(name = "note", length=255, nullable=true)
	private String note;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", length=20, nullable=false)
	private CommonStatus status;
	
	
	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;
	
	@Column(name = "approved_user", nullable=true, length=255)
	private String approvedUser;
	
	@Column(name = "approved_date", nullable=true)
	private Timestamp approvedDate;
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public CommonListItem getTierBandMethod() {
		return tierBandMethod;
	}

	public void setTierBandMethod(CommonListItem tierBandMethod) {
		this.tierBandMethod = tierBandMethod;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public CommonListItem getCalculationMethod() {
		return calculationMethod;
	}

	public void setCalculationMethod(CommonListItem calculationMethod) {
		this.calculationMethod = calculationMethod;
	}
	
	public InterestTemplate getInterestTemplate() {
		return interestTemplate;
	}

	public void setInterestTemplate(InterestTemplate interestTemplate) {
		this.interestTemplate = interestTemplate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
	
	public String getApprovedUser() {
		return approvedUser;
	}

	public void setApprovedUser(String approvedUser) {
		this.approvedUser = approvedUser;
	}

	public Timestamp getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
	}

	
	public Long getTierBandMethodId() {
		return tierBandMethod.getId();
	}

	public String getCommonListCode() {
		return tierBandMethod.getCode();
	}

	public String getCommonListRefCode() {
		return tierBandMethod.getReferenceCode();
	}

	public Long getInterestTemplatesId() {
		return interestTemplate.getId();
	}

	public String getInterestTemplatesCode() {
		return interestTemplate.getCode();
	}

	
}
