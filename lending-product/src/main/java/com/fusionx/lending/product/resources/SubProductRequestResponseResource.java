package com.fusionx.lending.product.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubProductRequestResponseResource {

    private String id;
    private String version;
    private String syncTs;
    private String tenantId;
    private String productId;
    private String productCode;
    private String productName;
    private String code;
    private String name;
    private String predecessorId;
    private String predecessorCode;
    private String predecessorName;
    private String marketingStateId;
    private String marketingStateReferenceCode;
    private String marketingStateCode;
    private String marketingStateName;
    private String firstMarketedDate;
    private String lastMarketedDate;
    private String stateTenureLength;
    private String stateTenurePeriodId;
    private String stateTenurePeriodCode;
    private String stateTenurePeriodName;
    private String stateTenurePeriodDescription;
    private String featureBenifitTemplateId;
    private String featureBenifitTemplateCode;
    private String featureBenifitTemplateName;
    private String eligibilityId;
    private String eligibilityCode;
    private String eligibilityName;
    private String interestTemplateId;
    private String interestTemplateCode;
    private String interestTemplateName;
    private String repaymentId;
    private String repaymentCode;
    private String repaymentName;
    private String coreProductId;
    private String status;
    private String approveStatus;
    private String createdUser;
    private String createdDate;
    private String modifiedUser;
    private String modifiedDate;
    private String pendingApprovedUser;
    private String pendingApprovedDate;
    private String pendingRejectedUser;
    private String pendingRejectedDate;

}