package com.fusionx.lending.product.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDetailsRequestResponseResource {

    private String id;
    private String syncTs;
    private String version;
    private String tenantId;
    private String status;
    private String addressTypeId;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String geoLevelId;
    private String countryGeoId;
    private String postalCode;
    private String createdUser;
    private String createdDate;
    private String ppaddId;
    private String modifiedUser;
    private String modifiedDate;

}
