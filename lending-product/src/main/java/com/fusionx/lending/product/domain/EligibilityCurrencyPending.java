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
 * Eligibility Currency Pending Domain
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6891	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@Entity
@Table(name = "eligibility_currency_pending")
@Data
public class EligibilityCurrencyPending extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1534058545939987291L;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_currency_id", nullable = false)
	private EligibilityCurrency eligibilityCurrencys;
	
	@Transient
    private Long eligibilityCurrencyId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lending_workflow_id", nullable=true)
	private LendingWorkflow lendingWorkflow;
	
	@Transient
    private Long workflowId;
	
	@Transient
    private Long workflowProcessId;
	
	@Transient
	private String workflowStatus;
	
	@Column(name = "tenant_id", length=20, nullable=false)
	private String tenantId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "eligibility_id", nullable = false)
	private Eligibility eligibilitys;
	
	@Transient
    private Long eligibilityId;
	
	@Transient
    private String eligibilityCode;
	
	@Transient
    private String eligibilityName;
	
	@Column(name = "currency_id", nullable=false)
	private Long currencyId;
	
	@Transient
    private String currencyCode;
	
	@Transient
    private String currencyName;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status", nullable=false, length=20)
	private CommonStatus status;
	
	@Column(name = "created_user", nullable=false, length=255)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;

	public EligibilityCurrency getEligibilityCurrencys() {
		return eligibilityCurrencys;
	}

	public void setEligibilityCurrencys(EligibilityCurrency eligibilityCurrencys) {
		this.eligibilityCurrencys = eligibilityCurrencys;
	}

	public LendingWorkflow getLendingWorkflow() {
		return lendingWorkflow;
	}

	public void setLendingWorkflow(LendingWorkflow lendingWorkflow) {
		this.lendingWorkflow = lendingWorkflow;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Eligibility getEligibilitys() {
		return eligibilitys;
	}

	public void setEligibilitys(Eligibility eligibilitys) {
		this.eligibilitys = eligibilitys;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
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
	
	public Long getEligibilityCurrencyId() {
		if(eligibilityCurrencys != null) {
			return eligibilityCurrencys.getId();
		} else {
			return null;
		}
	}

	public Long getWorkflowId() {
		if(lendingWorkflow != null) {
			return lendingWorkflow.getId();
		} else {
			return null;
		}
	}

	public Long getWorkflowProcessId() {
		if(lendingWorkflow != null) {
			return lendingWorkflow.getWorkflowProcessId();
		} else {
			return null;
		}
	}

	public String getWorkflowStatus() {
		if(lendingWorkflow != null) {
			return lendingWorkflow.getWorkflowStatus().toString();
		} else {
			return null;
		}
	}

	public Long getEligibilityId() {
		if(eligibilitys != null) {
			return eligibilitys.getId();
		} else {
			return null;
		}
	}
	
	public String getEligibilityCode() {
		if(eligibilitys != null) {
			return eligibilitys.getCode();
		} else {
			return null;
		}
	}

	public String getEligibilityName() {
		if(eligibilitys != null) {
			return eligibilitys.getName();
		} else {
			return null;
		}
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
}
