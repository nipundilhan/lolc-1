package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Tax Event
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   12-10-2021   FXL-1234      FXL-1131   Dilki      Created
 * <p>
 * *******************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TaxEventUpdateResource extends TaxEventAddResource {

    @NotBlank(message = "{version.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}")
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
