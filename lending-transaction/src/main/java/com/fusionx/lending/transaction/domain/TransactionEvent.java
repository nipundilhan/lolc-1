package com.fusionx.lending.transaction.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.transaction.core.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Entity
@Table(name = "transaction_event")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TransactionEvent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6570160972603385840L;

    @Column(name = "tenantId")
    private String tenantId;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

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
