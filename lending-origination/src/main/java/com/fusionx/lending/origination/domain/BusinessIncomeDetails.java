package com.fusionx.lending.origination.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.CommonStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * Business Income Details
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No       Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   06-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 * <p>
 * *******************************************************************************************************
 */

@Entity
@Table(name = "business_income_details")
public class BusinessIncomeDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4421531745647484311L;

    @Column(name = "tenant_id")
    private String tenantId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "income_source_details_id", nullable = false)
    private IncomeSourceDetails incomeSourceDetail;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "busi_additional_dtl_id", nullable = false)
    private BusinessAdditionalDetails busiAdditionalDtl;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
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
    private List<BusinessIncomeDocuments> businessIncomeDocumentList;

    @Transient
    private Long BusinessAdditionalDetailsId;


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public IncomeSourceDetails getIncomeSourceDetail() {
        return incomeSourceDetail;
    }

    public void setIncomeSourceDetail(IncomeSourceDetails incomeSourceDetail) {
        this.incomeSourceDetail = incomeSourceDetail;
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

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public List<BusinessIncomeDocuments> getBusinessIncomeDocumentList() {
        return businessIncomeDocumentList;
    }

    public void setBusinessIncomeDocumentList(List<BusinessIncomeDocuments> businessIncomeDocumentList) {
        this.businessIncomeDocumentList = businessIncomeDocumentList;
    }

    public BusinessAdditionalDetails getBusiAdditionalDtl() {
        return busiAdditionalDtl;
    }

    public void setBusiAdditionalDtl(BusinessAdditionalDetails busiAdditionalDtl) {
        this.busiAdditionalDtl = busiAdditionalDtl;
    }

    public Long getBusinessAdditionalDetailsId() {
        return BusinessAdditionalDetailsId;
    }

    public void setBusinessAdditionalDetailsId(Long businessAdditionalDetailsId) {
        BusinessAdditionalDetailsId = businessAdditionalDetailsId;
    }


}
