package com.fusionx.lending.transaction.repo;

/**
 * Standing Order Type service
 *
 * @author Nisalak
 * @Dat 05-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   05-08-2019   FX-1505       FX-1526    Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.FrequencyStatus;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "frequency")
@Data
public class Frequency extends BaseEntity {

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "unit")
    private Long unit;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private FrequencyStatus status;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;

    public Frequency() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public FrequencyStatus getStatus() {
        return status;
    }

    public void setStatus(FrequencyStatus status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = FrequencyStatus.valueOf(status);
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
