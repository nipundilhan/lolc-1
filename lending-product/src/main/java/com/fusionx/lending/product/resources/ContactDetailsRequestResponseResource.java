package com.fusionx.lending.product.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetailsRequestResponseResource {

    private String id;
    private String syncTs;
    private String version;
    private String tenantId;
    private String status;
    private String contactTypeId;
    private String contactType;
    private String contactNo;
    private String createdUser;
    private String createdDate;
    private String ppconId;
    private String modifiedUser;

}