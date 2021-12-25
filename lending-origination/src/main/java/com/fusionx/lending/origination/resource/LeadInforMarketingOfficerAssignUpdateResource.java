package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LeadInforMarketingOfficerAssignUpdateResource {

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String marketingOfficerId;

    @NotBlank(message = "{common.not-null}")
    private String marketingOfficerName;

    @NotNull(message = "{common.not-null}")
    private List<LeadListResource> leadIdList;

    public String getMarketingOfficerId() {
        return marketingOfficerId;
    }

    public void setMarketingOfficerId(String marketingOfficerId) {
        this.marketingOfficerId = marketingOfficerId;
    }

    public List<LeadListResource> getLeadIdList() {
        return leadIdList;
    }

    public void setLeadIdList(List<LeadListResource> leadIdList) {
        this.leadIdList = leadIdList;
    }

    public String getMarketingOfficerName() {
        return marketingOfficerName;
    }

    public void setMarketingOfficerName(String marketingOfficerName) {
        this.marketingOfficerName = marketingOfficerName;
    }
}
