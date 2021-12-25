package com.fusionx.lending.transaction.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Common Update Resource
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   01-10-2021      		     			PasinduT      Created
 * <p>
 * *******************************************************************************************************
 */

public class CoreTransactionUpdateResource extends CoreTransactionAddResource {

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
