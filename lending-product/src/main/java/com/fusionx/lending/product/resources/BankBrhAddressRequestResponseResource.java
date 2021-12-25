package com.fusionx.lending.product.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankBrhAddressRequestResponseResource {

    private String bbadBankBranchId;
    private String bbadAddress01;
    private String bbadAddress02;
    private String bbadAddress03;
    private String bbadAddress04;
    private String bbadCity;
    private String bbadCountryId;
    private String bbadPostalCode;
    private String bbadStatus;
    private String bbadCreatedUser;
    private String bbadCreatedDate;
    private String bbadModifiedUser;
    private String bbadModifiedDate;
    private String bbadAttribute1;
    private String bbadAttribute2;
    private String bbadAttribute3;
    private String bbadAttribute4;
    private String bbadAttribute5;
    private String id;
    private String syncTs;
    private String version;
    private String bbadTenantId;

}
