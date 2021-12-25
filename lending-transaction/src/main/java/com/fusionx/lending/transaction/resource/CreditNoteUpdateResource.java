package com.fusionx.lending.transaction.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * CreditNoteType Service
 * <p>
 * *******************************************************************************************************
 * ###   	Date         	Story Point   	Task No    		Author       	Description
 * -------------------------------------------------------------------------------------------------------
 * 1   	09-AUG-2021      		     					Sanatha      	Initial Development
 * <p>
 * *******************************************************************************************************
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CreditNoteUpdateResource {

    @NotBlank(message = "{common.not-null}")
    @Size(max = 4, min = 4, message = "{code.size}")
    @Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{code.pattern}")
    private String code;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 70, message = "{common-name.size}")
    private String name;

    @Size(max = 350, message = "{common-description.size}")
    private String description;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "{status.pattern}")
    private String status;

    @NotBlank(message = "{version.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}")
    private String version;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


}
