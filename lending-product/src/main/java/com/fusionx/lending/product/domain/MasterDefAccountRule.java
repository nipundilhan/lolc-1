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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.YesNo;

import lombok.Data;

@Entity
@Table(name = "master_def_account_rule")
@Data
public class MasterDefAccountRule  extends BaseEntity implements Serializable {


	private static final long serialVersionUID = 961297319632868080L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
		
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "master_definition_id", nullable=false)  
	private MasterDefinition masterDefinition;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "real_time", nullable=false, length=20)
	private YesNo realTime;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "end_of_day", nullable=false, length=20)
	private YesNo endOfDay;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "on_demand", nullable=false, length=20)
	private YesNo onDemand;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "customer_wise", nullable=false, length=20)
	private YesNo customerWise;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "account_wise", nullable=false, length=20)
	private YesNo accountWise;

	@Column(name = "created_user", length=255, nullable=false)
	private String createdUser;
	
	@Column(name = "created_date", nullable=false)
	private Timestamp createdDate;
	
	@Column(name = "modified_user", nullable=true, length=255)
	private String modifiedUser;
	
	@Column(name = "modified_date", nullable=true)
	private Timestamp modifiedDate;


	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public MasterDefinition getMasterDefinition() {
		return masterDefinition;
	}

	public void setMasterDefinitionId(MasterDefinition masterDefinition) {
		this.masterDefinition = masterDefinition;
	}

	public YesNo getRealTime() {
		return realTime;
	}

	public void setRealTime(YesNo realTime) {
		this.realTime = realTime;
	}

	public YesNo getEndOfDay() {
		return endOfDay;
	}

	public void setEndOfDay(YesNo endOfDay) {
		this.endOfDay = endOfDay;
	}

	public YesNo getOnDemand() {
		return onDemand;
	}

	public void setOnDemand(YesNo onDemand) {
		this.onDemand = onDemand;
	}

	public YesNo getCustomerWise() {
		return customerWise;
	}

	public void setCustomerWise(YesNo customerWise) {
		this.customerWise = customerWise;
	}

	public YesNo getAccountWise() {
		return accountWise;
	}

	public void setAccountWise(YesNo accountWise) {
		this.accountWise = accountWise;
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
