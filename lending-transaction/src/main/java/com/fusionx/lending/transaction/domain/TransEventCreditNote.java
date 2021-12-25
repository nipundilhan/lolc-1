package com.fusionx.lending.transaction.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.transaction.core.BaseEntity;

import lombok.Data;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity
@Table(name = "trans_event_credit_note")
@Data
public class TransEventCreditNote extends BaseEntity implements Serializable{
	
    private static final long serialVersionUID = 7679586L;


    @Column(name = "tenant_id")
    private String tenantId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "trans_event_id", nullable = true)
    private TransactionEvent transactionEvent;
    
    @Transient
    private Long transEventId;
    
    @Transient
    private String transEventCode;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "credit_note_id", nullable = true)
    private CreditNoteType creditNoteType;
    
    @Transient
    private Long creditNoteId;
    
    @Transient
    private String creditNoteCode;
    
    @Column(name = "status")
    private String status;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Date modifiedDate;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public TransactionEvent getTransactionEvent() {
		return transactionEvent;
	}

	public void setTransactionEvent(TransactionEvent transactionEvent) {
		this.transactionEvent = transactionEvent;
	}

	public Long getTransEventId() {
		if(transactionEvent!= null) {
			return transEventId = transactionEvent.getId();
		}else
		{
			return null;
		}
	}

	public void setTransEventId(Long transEventId) {
		this.transEventId = transEventId;
	}

	public String getTransEventCode() {
		
		if(transactionEvent!= null) {
			return transEventCode = transactionEvent.getCode();
		}else
		{
			return null;
		}
	}

	public void setTransEventCode(String transEventCode) {
		this.transEventCode = transEventCode;
	}

	public CreditNoteType getCreditNoteType() {
		return creditNoteType;
	}

	public void setCreditNoteType(CreditNoteType creditNoteType) {
		this.creditNoteType = creditNoteType;
	}

	public Long getCreditNoteId() {
		
		if(creditNoteType!= null) {
			return creditNoteId = creditNoteType.getId();
		}else
		{
			return null;
		}
	}

	public void setCreditNoteId(Long creditNoteId) {
		this.creditNoteId = creditNoteId;
	}

	public String getCreditNoteCode() {
		
		if(creditNoteType!= null) {
			return creditNoteCode = creditNoteType.getCode();
		}else
		{
			return null;
		}
	}

	public void setCreditNoteCode(String creditNoteCode) {
		this.creditNoteCode = creditNoteCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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
    
    

}
