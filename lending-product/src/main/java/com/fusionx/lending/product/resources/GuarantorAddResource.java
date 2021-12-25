package com.fusionx.lending.product.resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Guarantor Add Resource
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-10-2021      	-	     	-		Thushan      Created
 *
 ********************************************************************************************************
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GuarantorAddResource {

    @NotBlank(message = "{common.not-null}")
    private String sequence;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-8-2}")
    private String guaranteePercentage;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-38-2}"+" into value")
    private String value;

    @NotBlank(message = "{common.not-null}")
    private String bondNumber;

    @NotBlank(message = "{common.not-null}")
    private String tenantId;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
    private String status;

    @NotBlank(message = "{common.not-null}")
    private String customerId;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 50, message = "{common.account-number.size}")
    private String lendingAccountDetail;

}
