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
 * 1   30-11-2021      		     FXL-1390	    Ishan      Created
 * <p>
 * *******************************************************************************************************
 */

@Entity
@Table(name = "waive_off_type")
public class WaiveOffType extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1575868417769264416L;

	@Column(name = "tenant_id", length=10, nullable=false)
	private String tenantId;
	
	@Column(name = "code", length = 4, nullable = false)
    private String code;
	
	@Column(name = "name", length = 70, nullable = false)
	private String name;
	
	@Column(name = "description", length = 350, nullable = true)
	private String description;

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
    @JoinColumn(name = "credit_note_type_id", nullable = false)
    private CreditNoteType creditNoteType;

    @Transient
    private Long creditNoteTypeId;
    
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public CreditNoteType getCreditNoteType() {
		return creditNoteType;
	}

	public void setCreditNoteType(CreditNoteType creditNoteType) {
		this.creditNoteType = creditNoteType;
	}

	public Long getCreditNoteTypeId() {
		return creditNoteType.getId();
	}

	public void setCreditNoteTypeId(Long creditNoteTypeId) {
		this.creditNoteTypeId = creditNoteTypeId;
	} 
	
	
	
}
