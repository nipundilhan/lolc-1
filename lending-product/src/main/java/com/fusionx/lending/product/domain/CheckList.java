package com.fusionx.lending.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.YesNo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *Check List
 *<p>
 ********************************************************************************************************
 *###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *1   26-10-2021      		                    Rohan        Created
 *<p>
 ********************************************************************************************************
 */

@Entity
@Table(name = "lending_checklist")
public class CheckList extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1765102592762727587L;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "check_mark")
    private YesNo checkMark;

    @Column(name = "remarks", length = 1000)
    private String remarks;

    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private CommonStatus status;

    @Column(name = "created_user", nullable = false)
    private String createdUser;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "lending_account_detail_id", nullable = false)
    private LendingAccountDetail lendingAccountDetail;

    @Column(name = "checklist_id", nullable = false)
    private Long documentCheckListDetailId;

    public YesNo getCheckMark() {
        return checkMark;
    }

    public void setCheckMark(YesNo checkMark) {
        this.checkMark = checkMark;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public LendingAccountDetail getLendingAccountDetail() {
        return lendingAccountDetail;
    }

    public void setLendingAccountDetail(LendingAccountDetail lendingAccountDetail) {
        this.lendingAccountDetail = lendingAccountDetail;
    }

    public Long getDocumentCheckListDetailId() {
        return documentCheckListDetailId;
    }

    public void setDocumentCheckListDetailId(Long documentCheckListDetailId) {
        this.documentCheckListDetailId = documentCheckListDetailId;
    }
}
