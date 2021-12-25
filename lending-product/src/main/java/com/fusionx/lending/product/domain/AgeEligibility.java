package com.fusionx.lending.product.domain;

/**
 * Age Eligibility Domain
 * @author 	MenukaJ
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-07-2021  						    MenukaJ      Created
 *    
 ********************************************************************************************************
 */

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;

import lombok.Data;



@Entity
@Table(name = "age_eligibility")
@Data
@JsonSerialize(include = Inclusion.NON_NULL)
public class AgeEligibility extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 0000000001;

	@Column(name="tenant_id" ,length=10, nullable=false)
	private String tenantId;
	
	@Column(name="minimum_age", nullable=false)
	private Long minimumAge;
	
	@Column(name="maximumAge", nullable=false)
	private Long maximumAge;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private CommonStatus status;
	
	@Column(name="created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name="created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name="modified_user", length=255, nullable=true)
	private String modifiedUser;
	
	@Column(name="modified_date", nullable=true)
	private Timestamp modifiedDate;


	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getMinimumAge() {
		return minimumAge;
	}

	public void setMinimumAge(Long minimumAge) {
		this.minimumAge = minimumAge;
	}

	public Long getMaximumAge() {
		return maximumAge;
	}

	public void setMaximumAge(Long maximumAge) {
		this.maximumAge = maximumAge;
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
