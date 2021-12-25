package com.fusionx.lending.transaction.domain;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.PostingType;
import com.fusionx.lending.transaction.enums.CommonStatus;

import lombok.Data;

@Entity
@Table(name = "credit_note_transaction_type")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CreditNoteTransactionType  extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6570160972603385840L;

    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId;
    
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "posting_type", length = 10, nullable = false)
    private PostingType PostingType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private CommonStatus status;   
    
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "transaction_sub_code_id", nullable = true)
    private BankTransactionSubCode bankTransactionSubCode;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_note_type_id", nullable = true)
    private CreditNoteType creditNoteType;
   
    @Enumerated(value = EnumType.STRING)
    @Column(name = "is_debit_balance_enable", length = 10, nullable = false)
    private CommonStatus isDebitBalanceEnable;

    @Column(name = "created_user", length = 255, nullable = false)
    private String createdUser;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "modified_user", nullable = true, length = 255)
    private String modifiedUser;

    @Column(name = "modified_date", nullable = true)
    private Timestamp modifiedDate;
    
    
	@Transient
	private Long creditNoteTypeValId;
    
	public Long getCreditNoteTypeValId() {
		if(creditNoteType != null) {
			return creditNoteType.getId();
		} else {
			return null;
		}
	}

	public void setCreditNoteTypeValId(Long creditNoteTypeId) {
		this.creditNoteTypeValId = creditNoteTypeId;
	}
	
	@Transient
	private String creditNoteTypeName;
    
	public String getCreditNoteTypeName() {
		if(creditNoteType != null) {
			return creditNoteType.getName();
		} else {
			return null;
		}
	}

	public void setCreditNoteTypeName(String creditNoteTypeName) {
		this.creditNoteTypeName = creditNoteTypeName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public PostingType getPostingType() {
		return PostingType;
	}

	public void setPostingType(PostingType postingType) {
		PostingType = postingType;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public BankTransactionSubCode getBankTransactionSubCode() {
		return bankTransactionSubCode;
	}

	public void setBankTransactionSubCode(BankTransactionSubCode bankTransactionSubCode) {
		this.bankTransactionSubCode = bankTransactionSubCode;
	}

	public CreditNoteType getCreditNoteType() {
		return creditNoteType;
	}

	public void setCreditNoteType(CreditNoteType creditNoteType) {
		this.creditNoteType = creditNoteType;
	}

	public CommonStatus getIsDebitBalanceEnable() {
		return isDebitBalanceEnable;
	}

	public void setIsDebitBalanceEnable(CommonStatus isDebitBalanceEnable) {
		this.isDebitBalanceEnable = isDebitBalanceEnable;
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
