package com.fusionx.lending.product.resources;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Guarantor Response Resource
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-10-2021      -		         -	    Thushan      Created
 *
 ********************************************************************************************************
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuarantorResponseResource {

    private String id;
    private String sequence;
    private String value;
    private String bondNumber;
    private String tenantId;
    private String status;
    private String lendingAccountDetail;

}
