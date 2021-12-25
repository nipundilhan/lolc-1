package com.fusionx.lending.product.resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MasterDefinitionAccountRuleUpdateResource {


    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    private String masterDefinitionPendingId;


    @Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
    @NotNull(message = "{common.not-null}")
    private String masterDefAccountRuleId;


    @Valid
    private MasterDefAccountRuleReconResource reconciliations;

    @Valid
    private MasterDefAccountRuleGlPostResource glEntryPosting;

    @Valid
    private List<MasterDefAccRuleCommonListUpdateResource> commonListUsageList;


    public String getMasterDefinitionPendingId() {
        return masterDefinitionPendingId;
    }

    public void setMasterDefinitionPendingId(String masterDefinitionPendingId) {
        this.masterDefinitionPendingId = masterDefinitionPendingId;
    }

    public String getMasterDefAccountRuleId() {
        return masterDefAccountRuleId;
    }

    public void setMasterDefAccountRuleId(String masterDefAccountRuleId) {
        this.masterDefAccountRuleId = masterDefAccountRuleId;
    }

    public MasterDefAccountRuleReconResource getReconciliations() {
        return reconciliations;
    }

    public void setReconciliations(MasterDefAccountRuleReconResource reconciliations) {
        this.reconciliations = reconciliations;
    }

    public MasterDefAccountRuleGlPostResource getGlEntryPosting() {
        return glEntryPosting;
    }

    public void setGlEntryPosting(MasterDefAccountRuleGlPostResource glEntryPosting) {
        this.glEntryPosting = glEntryPosting;
    }

    public List<MasterDefAccRuleCommonListUpdateResource> getCommonListUsageList() {
        return commonListUsageList;
    }

    public void setCommonListUsageList(List<MasterDefAccRuleCommonListUpdateResource> commonListUsageList) {
        this.commonListUsageList = commonListUsageList;
    }

}
