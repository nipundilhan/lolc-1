package com.fusionx.lending.product.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankRequestResponseResource {

            private String bankCode;
            private String bankName;
            private String bankSwiftcode;
            private String bankCeftCode;
            private String bankStatus;
            private String bankCreatedUser;
            private String bankCreatedDate;
            private String bankModifiedUser;
            private String bankModifiedDate;
            private String bankAttribute1;
            private String bankAttribute2;
            private String bankAttribute3;
            private String bankAttribute4;
            private String bankAttribute5;
            private String id;
            private String syncTs;
            private String version;
            private String bankTenantId;
            private String category;
            private String bankAddress;
            private String bankContact;

}
