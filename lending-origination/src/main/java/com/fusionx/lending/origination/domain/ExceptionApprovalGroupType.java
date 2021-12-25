package com.fusionx.lending.origination.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Exception Approval Group Type
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   23-08-2021   FXL-632   	 FX-586		Piyumi      Created
 * <p>
 * *******************************************************************************************************
 */
@Entity
@Table(name = "exception_approval_group_type")
@Data
public class ExceptionApprovalGroupType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4522610539116920797L;

    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "exception_approval_group_id", nullable = false)
    private ExceptionApprovalGroup exceptionApprovalGroup;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "exception_type_id", nullable = false)
    private ExceptionType exceptionType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private CommonStatus status;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;

    @Transient
    private Long exceptionTypesId;

    @Transient
    private String exceptionTypesName;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public ExceptionApprovalGroup getExceptionApprovalGroup() {
        return exceptionApprovalGroup;
    }

    public void setExceptionApprovalGroup(
            ExceptionApprovalGroup exceptionApprovalGroup) {
        this.exceptionApprovalGroup = exceptionApprovalGroup;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
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

    public Long getExceptionTypesId() {
        return exceptionTypesId;
    }

    public void setExceptionTypesId(Long exceptionTypesId) {
        this.exceptionTypesId = exceptionTypesId;
    }

    public String getExceptionTypesName() {
        return exceptionTypesName;
    }

    public void setExceptionTypesName(String exceptionTypesName) {
        this.exceptionTypesName = exceptionTypesName;
    }


}
