package com.fusionx.lending.origination.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class LeadListResource {

    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String leadId;

    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String version;


    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
