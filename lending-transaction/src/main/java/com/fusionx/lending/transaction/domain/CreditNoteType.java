package com.fusionx.lending.transaction.domain;

import com.fusionx.lending.transaction.core.BaseEntity;
import com.fusionx.lending.transaction.enums.CommonStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * CreditNoteType Service
 * <p>
 * *******************************************************************************************************
 * ###   	Date         	Story Point   	Task No    		Author       	Description
 * -------------------------------------------------------------------------------------------------------
 * 1   	09-AUG-2021      		     					Sanatha      	Initial Development
 * <p>
 * *******************************************************************************************************
 */

@Entity
@Table(name = "credit_note_type")
@Data
public class CreditNoteType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6570160972603385840L;

    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId;

    @Column(name = "code", length = 4, nullable = false)
    private String code;

    @Column(name = "name", length = 70, nullable = false)
    private String name;

    @Column(name = "description", length = 350, nullable = true)
    private String description;

   
    @Column(name = "status")
    private String status;

    @Column(name = "created_user", length = 255, nullable = false)
    private String createdUser;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "modified_user", nullable = true, length = 255)
    private String modifiedUser;

    @Column(name = "modified_date", nullable = true)
    private Timestamp modifiedDate;

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


}
