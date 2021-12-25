package com.fusionx.lending.transaction.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Tax Code Mapping
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   11-10-2021      		     FXL-1130	Dilki      Created
 * <p>
 * *******************************************************************************************************
 */

public class TaxCodeUpdateResource extends TaxCodeAddResource {

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
