package com.fusionx.lending.transaction.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.CommonStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Tax Event
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   12-10-2021   FXL-1234      FXL-1131   Dilki      Created
 * <p>
 * *******************************************************************************************************
 */

@Entity
@Table(name = "tax_event")
public class TaxEvent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6284792929606574115L;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "code", length = 4, nullable = false)
    private String code;

    @Column(name = "name", length = 70, nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "transaction_event_id", nullable = false)
    private TransactionEvent transactionEvent;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private CommonStatus status;

    @Column(name = "created_user", length = 20, nullable = false)
    private String createdUser;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "modified_user", length = 20, nullable = true)
    private String modifiedUser;

    @Column(name = "modified_date", nullable = true)
    private Timestamp modifiedDate;

	@Transient
	private List<TaxEventDetails> taxEventDetails;
	
    public List<TaxEventDetails> getTaxEventDetails() {
		return taxEventDetails;
	}

	public void setTaxEventDetails(List<TaxEventDetails> taxEventDetails) {
		this.taxEventDetails = taxEventDetails;
	}

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

	public TransactionEvent getTransactionEvent() {
		return transactionEvent;
	}

	public void setTransactionEvent(TransactionEvent transactionEvent) {
		this.transactionEvent = transactionEvent;
	}

}
