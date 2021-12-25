package com.fusionx.lending.product.resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * lending transaction Add Resource
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   28-10-2021      		         -  	Thushan      Created
 *
 ********************************************************************************************************
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LendingTransactionAddResource {

    @NotBlank(message = "{common.not-null}")
    private String tenantId;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common.invalid-date-pattern}" + " into transaction date")
    private String transactionDate;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{4}(\\-)(((0)[0-9])|((1)[0-2]))(\\-)([0-2][0-9]|(3)[0-1])$", message = "{common.invalid-date-pattern}" + " into value date")
    private String valueDate;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 20, message = "transaction sub code" + "{common.size.grater.than}" + "20 characters")
    private String transactionSubCode;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "DUES|ALLOCATE", message = "{posting-type.pattern}")
    private String postingType;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$", message = "{common.invalid-amount-pattern-38-2}" + " into amount")
    private String amount;

    @Column(name = "currency_code_numeric", length = 3)
    private String currencyCodeNumeric;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 20, message = "Currency code" + "{common.size.grater.than}" + "20 characters")
    private String currencyCode;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 4, message = "Sequence order" + "{common.size.grater.than}" + "4 characters")
    private String sequenceOrder;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 4, message = "Reference Id" + "{common.size.grater.than}" + "4 characters")
    private String referenceId;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;

    @NotBlank(message = "{common.not-null}")
    private String currencyId;

    @NotBlank(message = "{common.not-null}")
    private String lendingAccountId;
}
