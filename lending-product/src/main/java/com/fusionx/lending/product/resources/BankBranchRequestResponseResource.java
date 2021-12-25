package com.fusionx.lending.product.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankBranchRequestResponseResource {

    private String bbrhCode;
    private String bbrhName;
    private String bbrhBankId;
    private String bbrhCategory;
    private String bbrhSwiftcode;
    private String bbrhCeftCode;
    private String bbrhStatus;
    private String bbrhCreatedUser;
    private String bbrhCreatedDate;
    private String bbrhModifiedUser;
    private String bbrhModifiedDate;
    private String bbrhAttribute1;
    private String bbrhAttribute2;
    private String bbrhAttribute3;
    private String bbrhAttribute4;
    private String bbrhAttribute5;
    private String bbrhContact;
    private String id;
    private String syncTs;
    private String version;
    private String bbrhTenantId;

}
