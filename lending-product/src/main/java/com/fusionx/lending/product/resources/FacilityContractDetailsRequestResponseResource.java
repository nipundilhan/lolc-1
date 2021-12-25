package com.fusionx.lending.product.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityContractDetailsRequestResponseResource {
    private String contractNo;
    private String contractStatus;
    private String contractStatusDes;
    private String createdDate;
    private String createdUser;
    private String id;
    private String leseCode;
    private String modifiedDate;
    private String modifiedUser;
    private String status;
    private String syncTs;
    private String tenantId;
    private String version;
}
