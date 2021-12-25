package com.fusionx.lending.product.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkedPersonsRequestResponseResource {

         private String id;
         private String syncTs;
         private String version;
         private String tenantId;
         private String linkedPersonType;
         private String name;
         private String firstName;
         private String middleName;
         private String lastName;
         private String genderId;
         private String gender;
         private String titleId;
         private String title;
         private String status;
         private String createdUser;
         private String createdDate;
         private String modifiedUser;
         private String modifiedDate;
         private String culpId;
         private String pculpId;
         private String penPerId;
         private String perId;
         private String perCode;
         private String commonCustomerLinkPersonId;
         private String relationId;
         private String relationshipType;
         private String internalCribStatusId;
         private String externalCribStatusId;
         private String internalCribStatus;
         private String externalCribStatus;
         private IdentificationDetailsRequestResponseResource identificationDetails;
         private String contactDetails;
         private String addressDetails;


}
