package com.fusionx.lending.transaction.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.enums.TaxApplicableOnEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Tax Event Details
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   13-10-2021   FXL-1234      FXL-1131	Dilki      Created
 * <p>
 * *******************************************************************************************************
 */

@Entity
@Table(name = "tax_event_details")
public class TaxEventDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1694739055678161104L;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "tax_code_id", nullable = false)
    private Long codeId;

    @Column(name = "tax_code", length = 4, nullable = false)
    private String code;

    @Column(name = "tax_name", length = 20, nullable = false)
    private String name;

    @Column(name = "description", length = 200, nullable = true)
    private String description;

    @Column(name = "formula", length = 100, nullable = true)
    private String formula;

    @Column(name = "sequence", nullable = false)
    private Long sequence;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "tax_event_id", nullable = false)
    private TaxEvent taxEventId;
    
    @Transient
    private Long taxEvent;
    
//    @Enumerated(value = EnumType.STRING)
    @Column(name = "applicable_on")
    private String applicableOn;

    @Column(name = "amount_type", length = 10, nullable = false)
    private String amountType;

    @Column(name = "application_frequency", nullable = false)
    private Long applicationFrequency;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private CommonStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "is_fixed")
    private CommonStatus isFixed;

    @Column(name = "created_user", length = 20, nullable = false)
    private String createdUser;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "modified_user", length = 20, nullable = true)
    private String modifiedUser;

    @Column(name = "modified_date", nullable = true)
    private Timestamp modifiedDate;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public TaxEvent getTaxEventId() {
        return taxEventId;
    }

    public void setTaxEventId(TaxEvent taxEventId) {
        this.taxEventId = taxEventId;
    }



    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }


    public CommonStatus getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(CommonStatus isFixed) {
        this.isFixed = isFixed;
    }

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public String getApplicableOn() {
		return applicableOn;
	}

	public void setApplicableOn(String applicableOn) {
		this.applicableOn = applicableOn;
	}

	public Long getApplicationFrequency() {
		return applicationFrequency;
	}

	public void setApplicationFrequency(Long applicationFrequency) {
		this.applicationFrequency = applicationFrequency;
	}

	public Long getTaxEvent() {
		return taxEventId.getId();
	}
}
