package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import lombok.Data;

/**
 * Eligibility Officer Type History Domain
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6888	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "eligibility_officer_type_hist")
@Data
public class EligibilityOfficerTypeHistory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 796168372208868361L;

	@Column(name = "eligibility_officer_type_id", nullable = false)
	private Long eligibilityOfficerTypeId;
	
	@Column(name = "tenant_id", length=20, nullable=false)
	private String tenantId;
	
	@Column(name = "eligibility_id", nullable = false)
	private Long eligibilityId;
	
	@Column(name = "officer_type_id", nullable = false)
	private Long officerTypeId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "approve_status",  nullable=true, length=30)
	private CommonApproveStatus approveStatus;
	
	@Column(name = "created_user", nullable=false, length=255)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;

	@Column(name = "pen_approved_user", nullable=true, length=255)
	private String penApprovedUser;
	
	@Column(name = "pen_approved_date", nullable=true)
	private Timestamp penApprovedDate;
	
	@Column(name = "pen_rejected_user", nullable=true, length=255)
	private String penRejectedUser;
	
	@Column(name = "pen_rejected_date", nullable=true)
	private Timestamp penRejectedDate;
	
	@Column(name = "his_created_user", nullable=true, length=255)
	private String hisCreatedUser;
	
	@Column(name = "his_created_date", nullable=true)
	private Timestamp hisCreatedDate;

	public Long getEligibilityOfficerTypeId() {
		return eligibilityOfficerTypeId;
	}

	public void setEligibilityOfficerTypeId(Long eligibilityOfficerTypeId) {
		this.eligibilityOfficerTypeId = eligibilityOfficerTypeId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getEligibilityId() {
		return eligibilityId;
	}

	public void setEligibilityId(Long eligibilityId) {
		this.eligibilityId = eligibilityId;
	}

	public Long getOfficerTypeId() {
		return officerTypeId;
	}

	public void setOfficerTypeId(Long officerTypeId) {
		this.officerTypeId = officerTypeId;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public CommonApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(CommonApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
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

	public String getPenApprovedUser() {
		return penApprovedUser;
	}

	public void setPenApprovedUser(String penApprovedUser) {
		this.penApprovedUser = penApprovedUser;
	}

	public Timestamp getPenApprovedDate() {
		return penApprovedDate;
	}

	public void setPenApprovedDate(Timestamp penApprovedDate) {
		this.penApprovedDate = penApprovedDate;
	}

	public String getPenRejectedUser() {
		return penRejectedUser;
	}

	public void setPenRejectedUser(String penRejectedUser) {
		this.penRejectedUser = penRejectedUser;
	}

	public Timestamp getPenRejectedDate() {
		return penRejectedDate;
	}

	public void setPenRejectedDate(Timestamp penRejectedDate) {
		this.penRejectedDate = penRejectedDate;
	}

	public String getHisCreatedUser() {
		return hisCreatedUser;
	}

	public void setHisCreatedUser(String hisCreatedUser) {
		this.hisCreatedUser = hisCreatedUser;
	}

	public Timestamp getHisCreatedDate() {
		return hisCreatedDate;
	}

	public void setHisCreatedDate(Timestamp hisCreatedDate) {
		this.hisCreatedDate = hisCreatedDate;
	}
	
}
