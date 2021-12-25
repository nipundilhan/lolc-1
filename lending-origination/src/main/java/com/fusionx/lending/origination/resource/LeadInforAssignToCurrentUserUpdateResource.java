package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LeadInforAssignToCurrentUserUpdateResource {

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String branchId;

    @NotBlank(message = "{common.not-null}")
    @Size(max = 70, message = "{common-name.size}")
    private String branchName;

    @NotBlank(message = "{common.not-null}")
    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String marketingOfficerId;

    @NotBlank(message = "{common.not-null}")
    private String marketingOfficerName;

    @NotNull(message = "{common.not-null}")
    private List<LeadListResource> leadIdList;

    public List<LeadListResource> getLeadIdList() {
        return leadIdList;
    }

    public void setLeadIdList(List<LeadListResource> leadIdList) {
        this.leadIdList = leadIdList;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getMarketingOfficerId() {
        return marketingOfficerId;
    }

    public void setMarketingOfficerId(String marketingOfficerId) {
        this.marketingOfficerId = marketingOfficerId;
    }


    public String getMarketingOfficerName() {
        return marketingOfficerName;
    }

    public void setMarketingOfficerName(String marketingOfficerName) {
        this.marketingOfficerName = marketingOfficerName;
    }
}
