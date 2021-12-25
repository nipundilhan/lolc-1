package com.fusionx.lending.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.product.core.BaseEntity;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * API Service related to Eligibility Collateral.
 *
 * @author Miyuru Wijesinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        01-07-2021    	-               FX-6889             Miyuru Wijesinghe          Created
 * <p>
 */
@Entity
@Table(name = "eligibility_collateral")
@Data
public class EligibilityCollateral extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3333902986411422769L;

    @Column(name = "tenant_id", length = 20, nullable = false)
    private String tenantId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "eligibility_id", nullable = false)
    private Eligibility eligibilities;

    @Transient
    private Long eligibilityId;

    @Transient
    private String eligibilityCode;

    @Transient
    private String eligibilityName;

    @Column(name = "asset_type_id", nullable = false)
    private Long assetTypeId;

    @Transient
    private String assetTypeCode;

    @Transient
    private String assetTypeName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CommonStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "approve_status", nullable = true, length = 30)
    private CommonApproveStatus approveStatus;

    @Column(name = "created_user", nullable = false, length = 255)
    private String createdUser;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "modified_user", nullable = true, length = 255)
    private String modifiedUser;

    @Column(name = "modified_date", nullable = true)
    private Timestamp modifiedDate;

    @Column(name = "pen_approved_user", nullable = true, length = 255)
    private String penApprovedUser;

    @Column(name = "pen_approved_date", nullable = true)
    private Timestamp penApprovedDate;

    @Column(name = "pen_rejected_user", nullable = true, length = 255)
    private String penRejectedUser;

    @Column(name = "pen_rejected_date", nullable = true)
    private Timestamp penRejectedDate;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Eligibility getEligibilities() {
        return eligibilities;
    }

    public void setEligibilities(Eligibility eligibilities) {
        this.eligibilities = eligibilities;
    }

    public Long getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(Long assetTypeId) {
        this.assetTypeId = assetTypeId;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public CommonApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(CommonApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
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

    public String getPenApprovedUser() {
        return penApprovedUser;
    }

    public void setPenApprovedUser(String penApprovedUser) {
        this.penApprovedUser = penApprovedUser;
    }

    public Timestamp getPenApprovedDate() {
        return penApprovedDate;
    }

    public void setPenApprovedDate(Timestamp penApprovedDate) {
        this.penApprovedDate = penApprovedDate;
    }

    public String getPenRejectedUser() {
        return penRejectedUser;
    }

    public void setPenRejectedUser(String penRejectedUser) {
        this.penRejectedUser = penRejectedUser;
    }

    public Timestamp getPenRejectedDate() {
        return penRejectedDate;
    }

    public void setPenRejectedDate(Timestamp penRejectedDate) {
        this.penRejectedDate = penRejectedDate;
    }

    public Long getEligibilityId() {
        if (eligibilities != null) {
            return eligibilities.getId();
        } else {
            return null;
        }
    }

    public String getEligibilityCode() {
        if (eligibilities != null) {
            return eligibilities.getCode();
        } else {
            return null;
        }
    }

    public String getEligibilityName() {
        if (eligibilities != null) {
            return eligibilities.getName();
        } else {
            return null;
        }
    }

    public String getAssetTypeCode() {
        return assetTypeCode;
    }

    public void setAssetTypeCode(String assetTypeCode) {
        this.assetTypeCode = assetTypeCode;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }

}
