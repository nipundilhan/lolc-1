package com.fusionx.lending.transaction.domain;

import java.io.Serializable;
import java.util.Date;

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
import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.CommonStatus;

/**
 * Tax Code Mapping
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   01-12-2021      		     FXL-2110	    Ishan      Created
 * <p>
 * *******************************************************************************************************
 */

@Entity
@Table(name = "waive_off_transaction_type")
public class WaiveOffTransactionType  extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1933311446453453995L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status",nullable = false)
    private CommonStatus status;
	
	@Column(name = "created_user" , length = 255, nullable = false)
    private String createdUser;

    @Column(name = "created_date",nullable = false)
    private Date createdDate;

    @Column(name = "modified_user", length = 255, nullable = true)
    private String modifiedUser;

    @Column(name = "modified_date",nullable = true)
    private Date modifiedDate;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "waive_off_type_id", nullable = false)
    private WaiveOffType waiveOffType;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_code_id", nullable = false)
    private BankTransactionCode transactionCode;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_sub_code_id", nullable = false)
    private BankTransactionSubCode transactionSubCode;

    @Transient
    private Long typeId;
    
    @Transient
    private Long bnkTransactionCodeId;
    
    @Transient
    private Long bnkTransactionSubCodeId;
    
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public WaiveOffType getWaiveOffType() {
		return waiveOffType;
	}

	public void setWaiveOffType(WaiveOffType waiveOffType) {
		this.waiveOffType = waiveOffType;
	}

	public BankTransactionCode getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(BankTransactionCode transactionCode) {
		this.transactionCode = transactionCode;
	}

	public BankTransactionSubCode getTransactionSubCode() {
		return transactionSubCode;
	}

	public void setTransactionSubCode(BankTransactionSubCode transactionSubCode) {
		this.transactionSubCode = transactionSubCode;
	}

	public Long getTypeId() {
		return waiveOffType.getId();
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getBnkTransactionCodeId() {
		return transactionCode.getId();
	}

	public void setBnkTransactionCodeId(Long bnkTransactionCodeId) {
		this.bnkTransactionCodeId = bnkTransactionCodeId;
	}

	public Long getBnkTransactionSubCodeId() {
		return transactionSubCode.getId();
	}

	public void setBnkTransactionSubCodeId(Long bnkTransactionSubCodeId) {
		this.bnkTransactionSubCodeId = bnkTransactionSubCodeId;
	}
	
}
