package com.fusionx.lending.product.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityOtherProductsRequestResponseResource {

    private String id;
    private String syncTs;
    private String version;
    private String tenantId;
    private String productCategoryCodeId;
    private String status;
    private String createdUser;
    private String createdDate;
    private String modifiedUser;
    private String modifiedDate;
    private String onboardProductId;

}
