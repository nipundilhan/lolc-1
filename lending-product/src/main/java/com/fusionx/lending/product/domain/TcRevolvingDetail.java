package com.fusionx.lending.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.InterestRepaymentMethod;

/**
 * Tc Revolving Detail
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   05-10-2021                       	FXL-994	   Dilhan       Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "tc_revolving_detail")
//@Data
public class TcRevolvingDetail extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 199999L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "initial_disbursement_amount",  nullable=false)
	private BigDecimal initialDisbursementAmount;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "interest_repayment_method", length=20, nullable=false)
	private InterestRepaymentMethod repaymentMethod;
	
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
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	private List<TcRevolvingPaymentSchedule> revolvingPaymentScheduleDetails;

	public List<TcRevolvingPaymentSchedule> getRevolvingPaymentScheduleDetails() {
		return revolvingPaymentScheduleDetails;
	}

	public void setRevolvingPaymentScheduleDetails(List<TcRevolvingPaymentSchedule> revolvingPaymentScheduleDetails) {
		this.revolvingPaymentScheduleDetails = revolvingPaymentScheduleDetails;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public BigDecimal getInitialDisbursementAmount() {
		return initialDisbursementAmount;
	}

	public void setInitialDisbursementAmount(BigDecimal initialDisbursementAmount) {
		this.initialDisbursementAmount = initialDisbursementAmount;
	}

	public InterestRepaymentMethod getRepaymentMethod() {
		return repaymentMethod;
	}

	public void setRepaymentMethod(InterestRepaymentMethod repaymentMethod) {
		this.repaymentMethod = repaymentMethod;
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
