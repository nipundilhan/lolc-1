package com.fusionx.lending.transaction.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.CommonStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Tax Code Mapping
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   11-10-2021      		     FXL-1130	Dilki      Created
 * <p>
 * *******************************************************************************************************
 */

@Entity
@Table(name = "tax_trans_code_mapping")
public class TaxCode extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6903457046426039452L;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "tax_code_id", length = 4, nullable = false)
    private String codeId;

    @Column(name = "tax_code", length = 20, nullable = false)
    private String code;

    @Column(name = "tax_name", length = 70, nullable = false)
    private String name;

    @Column(name = "description", length = 300, nullable = true)
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "transaction_code_id", nullable = false)
    private BankTransactionCode bankTransactionCode;

    @Column(name = "transaction_code_name", length = 70, nullable = true)
    private String bankTransactionCodeName;
    
    @Transient
    private Long transactionCodeId;
    
    @Transient
    private Long transactionSubCodeId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "transaction_sub_code_id", nullable = false)
    private BankTransactionSubCode bankTransactionSubCode;

    @Column(name = "transaction_sub_code_name", length = 70, nullable = true)
    private String bankTransactionSubCodeName;

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

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
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

	public BankTransactionCode getBankTransactionCode() {
		return bankTransactionCode;
	}

	public void setBankTransactionCode(BankTransactionCode bankTransactionCode) {
		this.bankTransactionCode = bankTransactionCode;
	}

	public String getBankTransactionCodeName() {
		return bankTransactionCodeName;
	}

	public void setBankTransactionCodeName(String bankTransactionCodeName) {
		this.bankTransactionCodeName = bankTransactionCodeName;
	}

	public BankTransactionSubCode getBankTransactionSubCode() {
		return bankTransactionSubCode;
	}

	public void setBankTransactionSubCode(BankTransactionSubCode bankTransactionSubCode) {
		this.bankTransactionSubCode = bankTransactionSubCode;
	}

	public String getBankTransactionSubCodeName() {
		return bankTransactionSubCodeName;
	}

	public void setBankTransactionSubCodeName(String bankTransactionSubCodeName) {
		this.bankTransactionSubCodeName = bankTransactionSubCodeName;
	}

	public Long getTransactionCodeId() {
		return bankTransactionCode.getId();
	}

	public Long getTransactionSubCodeId() {
		return bankTransactionSubCode.getId();
	}

}
