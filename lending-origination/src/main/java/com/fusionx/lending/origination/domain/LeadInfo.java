package com.fusionx.lending.origination.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fusionx.lending.origination.core.BaseEntity;
import com.fusionx.lending.origination.enums.FacilityTypes;
import com.fusionx.lending.origination.enums.LeadStatus;
import lombok.Data;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lead_info")
@Transactional
@Data
public class LeadInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4537474747L;

    @Column(name = "tenant_id")
    private String tenantId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "facility_type", length = 30, nullable = false)
    private FacilityTypes facilityType;

    @Column(name = "total_due_amount")
    private BigDecimal totalDueAmount;

    @Column(name = "total_settlement_amount")
    private BigDecimal totalSettlementAmount;

    @Column(name = "new_loan_amount")
    private BigDecimal newLoanAmount;

    @Column(name = "available_balance")
    private BigDecimal availableBalance;

    @Column(name = "status")
    private String status;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;


    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "onboard_req_id")
    private Long onboardRequestId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "lead_status")
    private LeadStatus leadStatus;

    @Column(name = "marketing_officer_id")
    private Long marketingOfficerId;

    @Column(name = "version")
    private Long version;

    @Transient
    @JsonInclude(Include.NON_NULL)
    private List<Customer> customers;


    @Transient
    @JsonInclude(Include.NON_NULL)
    private String leadStatusDesc;

    @Transient
    @JsonInclude(Include.NON_NULL)
    private List<FacilityContractDetails> facilityContractDetails;

    @Transient
    @JsonInclude(Include.NON_NULL)
    private List<FacilityOtherProducts> facilityOtherProducts;

    @Transient
    @JsonInclude(Include.NON_NULL)
    private FacilityParameter facilityParameter;

    @Transient
    @JsonInclude(Include.NON_NULL)
    private List<Guarantor> guarantors;

    @Transient
    @JsonInclude(Include.NON_NULL)
    private List<AdditionalDocument> additionalDocuments;

    @Transient
    @JsonInclude(Include.NON_NULL)
    private List<DisbursementDetails> disbursementDetails;

    @Transient
    @JsonInclude(Include.NON_NULL)
    private List<ReferenceDetails> referenceDetails;

    @Transient
    @JsonInclude(Include.NON_NULL)
    private List<AnalystDetails> analystDetails;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public FacilityTypes getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(FacilityTypes facilityType) {
        this.facilityType = facilityType;
    }

    public BigDecimal getTotalDueAmount() {
        return totalDueAmount;
    }

    public void setTotalDueAmount(BigDecimal totalDueAmount) {
        this.totalDueAmount = totalDueAmount;
    }

    public BigDecimal getTotalSettlementAmount() {
        return totalSettlementAmount;
    }

    public void setTotalSettlementAmount(BigDecimal totalSettlementAmount) {
        this.totalSettlementAmount = totalSettlementAmount;
    }

    public BigDecimal getNewLoanAmount() {
        return newLoanAmount;
    }

    public void setNewLoanAmount(BigDecimal newLoanAmount) {
        this.newLoanAmount = newLoanAmount;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
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

    public List<FacilityContractDetails> getFacilityContractDetails() {
        return facilityContractDetails;
    }

    public void setFacilityContractDetails(List<FacilityContractDetails> facilityContractDetails) {
        this.facilityContractDetails = facilityContractDetails;
    }

    public List<FacilityOtherProducts> getFacilityOtherProducts() {
        return facilityOtherProducts;
    }

    public void setFacilityOtherProducts(List<FacilityOtherProducts> facilityOtherProducts) {
        this.facilityOtherProducts = facilityOtherProducts;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public FacilityParameter getFacilityParameter() {
        return facilityParameter;
    }

    public void setFacilityParameter(FacilityParameter facilityParameter) {
        this.facilityParameter = facilityParameter;
    }

    public List<Guarantor> getGuarantors() {
        return guarantors;
    }

    public void setGuarantors(List<Guarantor> guarantors) {
        this.guarantors = guarantors;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Long getOnboardRequestId() {
        return onboardRequestId;
    }

    public void setOnboardRequestId(Long onboardRequestId) {
        this.onboardRequestId = onboardRequestId;
    }

    public List<AdditionalDocument> getAdditionalDocuments() {
        return additionalDocuments;
    }

    public void setAdditionalDocuments(List<AdditionalDocument> additionalDocuments) {
        this.additionalDocuments = additionalDocuments;
    }

    public List<DisbursementDetails> getDisbursementDetails() {
        return disbursementDetails;
    }

    public void setDisbursementDetails(List<DisbursementDetails> disbursementDetails) {
        this.disbursementDetails = disbursementDetails;
    }

    public LeadStatus getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(LeadStatus leadStatus) {
        this.leadStatus = leadStatus;
    }

    public Long getMarketingOfficerId() {
        return marketingOfficerId;
    }

    public void setMarketingOfficerId(Long marketingOfficerId) {
        this.marketingOfficerId = marketingOfficerId;
    }

    public String getLeadStatusDesc() {
        return leadStatusDesc;
    }

    public void setLeadStatusDesc(String leadStatusDesc) {
        this.leadStatusDesc = leadStatusDesc;
    }

    public List<ReferenceDetails> getReferenceDetails() {
        return referenceDetails;
    }

    public void setReferenceDetails(List<ReferenceDetails> referenceDetails) {
        this.referenceDetails = referenceDetails;
    }

    public List<AnalystDetails> getAnalystDetails() {
        return analystDetails;
    }

    public void setAnalystDetails(List<AnalystDetails> analystDetails) {
        this.analystDetails = analystDetails;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
