package com.fusionx.lending.transaction.resource;

/**
 * Bank Transaction Code service
 *
 * @author Nisalak
 * @Dat 29-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   29-08-2019   FX-1678       FX-825     Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BankTransactionSubCodeUpdateResource extends BankTransactionSubCodeResource {

    @JsonProperty("version")
    @NotBlank(message = "{version.not-null}")
    @Pattern(regexp = "^$|[0-9]", message = "{version.pattern}")
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
