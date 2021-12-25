package com.fusionx.lending.transaction.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Bank Transaction Code service
 *
 * @author Nisalak
 * @Dat 28-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   28-08-2019   FX-1678       FX-825     Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

@Entity
@Table(name = "bank_transaction_code")
@Data
public class BankTransactionCode extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 9990000001L;

    @Column(name = "tenant_id")
    private String tenantId;

    @JsonProperty("bankTransCode")
    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private BankTransactionCodeStatus status;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;

    public BankTransactionCode() {
        super();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BankTransactionCodeStatus getStatus() {
        return status;
    }

    public void setStatus(BankTransactionCodeStatus status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = BankTransactionCodeStatus.valueOf(status);
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
