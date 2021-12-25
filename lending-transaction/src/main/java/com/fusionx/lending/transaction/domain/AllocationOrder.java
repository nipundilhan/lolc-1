package com.fusionx.lending.transaction.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.transaction.core.BaseEntity;

import lombok.Data;

@Entity
@Data
@Table(name = "allocation_order")
public class AllocationOrder extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 0000000000001;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "reference_code")
    private String referenceCode;

    /*@Enumerated(value = EnumType.STRING)
    @Column(name = "account_status")
    private AccountStatusEnum accountStatus;*/

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "bank_transaction_code_id", nullable = false)
    private BankTransactionCode bankTransaction;
    
    @Transient
    private Long bankTransactionCodeId;

    @Column(name = "bank_transaction_code ", length = 255, nullable = false)
    private String bankTransactionCode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "bank_transaction_sub_code_id", nullable = false)
    private BankTransactionSubCode bankTransactionSub;
    
    @Transient
    private Long bankTransactionSubCodeId;

    @Column(name = "bank_transaction_sub_code ", length = 255, nullable = false)
    private String bankTransactionSubCode;

    @Column(name = "allocation_order")
    private Long allocationOrder;

    @Column(name = "status")
    private String status;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "allocation_template_id", nullable = false)
    private AllocationTemplate allocationTemp;
    
    @Transient
    private Long allocationTemplateId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    /*public AccountStatusEnum getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatusEnum accountStatus) {
        this.accountStatus = accountStatus;
    }*/

    public BankTransactionCode getBankTransaction() {
        return bankTransaction;
    }

    public void setBankTransaction(BankTransactionCode bankTransaction) {
        this.bankTransaction = bankTransaction;
    }
    

    public Long getBankTransactionCodeId() {
    	if(bankTransaction!=null) {
    		return bankTransactionCodeId = bankTransaction.getId();
    	}else {
    		return null;
    	}
	}

	public void setBankTransactionCodeId(Long bankTransactionCodeId) {
		this.bankTransactionCodeId = bankTransactionCodeId;
	}

	public String getBankTransactionCode() {
        return bankTransactionCode;
    }

    public void setBankTransactionCode(String bankTransactionCode) {
        this.bankTransactionCode = bankTransactionCode;
    }

    public BankTransactionSubCode getBankTransactionSub() {
        return bankTransactionSub;
    }

    public void setBankTransactionSub(BankTransactionSubCode bankTransactionSub) {
        this.bankTransactionSub = bankTransactionSub;
    }
    

    public Long getBankTransactionSubCodeId() {
    	if(bankTransactionSub!=null) {
    		return bankTransactionSubCodeId = bankTransactionSub.getId();
    	}else {
    		return null;
    	}
	}

	public void setBankTransactionSubCodeId(Long bankTransactionSubCodeId) {
		this.bankTransactionSubCodeId = bankTransactionSubCodeId;
	}

	public String getBankTransactionSubCode() {
        return bankTransactionSubCode;
    }

    public void setBankTransactionSubCode(String bankTransactionSubCode) {
        this.bankTransactionSubCode = bankTransactionSubCode;
    }

    public Long getAllocationOrder() {
        return allocationOrder;
    }

    public void setAllocationOrder(Long allocationOrder) {
        this.allocationOrder = allocationOrder;
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

	public AllocationTemplate getAllocationTemp() {
		return allocationTemp;
	}

	public void setAllocationTemp(AllocationTemplate allocationTemp) {
		this.allocationTemp = allocationTemp;
	}

	public Long getAllocationTemplateId() {
		if(allocationTemp!=null) {
		return allocationTemplateId =allocationTemp.getId() ;
		}else {
			return null;
		}
	}

	public void setAllocationTemplateId(Long allocationTemplateId) {
		this.allocationTemplateId = allocationTemplateId;
	}
	
	

	
}
