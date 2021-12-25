package com.fusionx.lending.product.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentificationDetailsRequestResponseResource {
    private String id;
    private String syncTs;
    private String version;
    private String tenantId;
    private String status;
    private String identificationTypeId;
    private String identificationType;
    private String identificationNo;
    private String createdUser;
    private String createdDate;
    private String pidtId;
    private String ppidtId;
    private String issueDate;
    private String expiryDate;
    private String modifiedUser;
    private String modifiedDate;
}
