package com.fusionx.lending.product.resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


/**
 * Collaterals Add Resource
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-10-2021      		         -  	Thushan      Created
 *
 ********************************************************************************************************
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CollateralsAddResource {

    @NotBlank(message = "{common.not-null}")
    private String sequence;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
    private String status;

    @NotBlank(message = "{common.not-null}")
    private String lendingAccountId;

    @NotBlank(message = "{common.not-null}")
    private String collateralId;
}
