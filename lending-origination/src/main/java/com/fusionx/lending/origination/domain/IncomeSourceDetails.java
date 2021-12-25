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
import com.fusionx.lending.origination.enums.IncomeTypeEnum;
import com.fusionx.lending.origination.enums.SourceTypeEnum;

import lombok.Data;

/**
 * 	Income Source Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   31-08-2021   FXL-115  	 FXL-656       Piyumi       Created
 *    
 ********************************************************************************************************
*/

@Entity
@Data
@Table(name = "income_source_details")
public class IncomeSourceDetails extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 3917920522993434886L;

	@Column(name = "tenant_id")
	private String tenantId;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "income_type", length=50, nullable=false)
	private IncomeTypeEnum incomeType;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "source_type", length=20, nullable=false)
	private SourceTypeEnum sourceType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "lead_id")
	private LeadInfo lead;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "linked_person_id")
	private LinkedPerson linkedPerson;
	
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
	private Long leadsId;
	
	@Transient
	private Long customersId;
	
	@Transient
	private String customersFullname;
	
	@Transient
	private Long linkedPersonsId;
	
	@Transient
	private String linkedPersonsName;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public IncomeTypeEnum getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(IncomeTypeEnum incomeType) {
		this.incomeType = incomeType;
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
	
	public LeadInfo getLead() {
		return lead;
	}

	public void setLead(LeadInfo lead) {
		this.lead = lead;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LinkedPerson getLinkedPerson() {
		return linkedPerson;
	}

	public void setLinkedPerson(LinkedPerson linkedPerson) {
		this.linkedPerson = linkedPerson;
	}

	public Long getLeadsId() {
		return leadsId;
	}

	public void setLeadsId(Long leadsId) {
		this.leadsId = leadsId;
	}

	public Long getCustomersId() {
		return customersId;
	}

	public void setCustomersId(Long customersId) {
		this.customersId = customersId;
	}

	public String getCustomersFullname() {
		return customersFullname;
	}

	public void setCustomersFullname(String customersFullname) {
		this.customersFullname = customersFullname;
	}

	public Long getLinkedPersonsId() {
		return linkedPersonsId;
	}

	public void setLinkedPersonsId(Long linkedPersonsId) {
		this.linkedPersonsId = linkedPersonsId;
	}

	public String getLinkedPersonsName() {
		return linkedPersonsName;
	}

	public void setLinkedPersonsName(String linkedPersonsName) {
		this.linkedPersonsName = linkedPersonsName;
	}
}
