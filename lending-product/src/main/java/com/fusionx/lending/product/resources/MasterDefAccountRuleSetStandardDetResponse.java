package com.fusionx.lending.product.resources;

import com.fusionx.lending.product.domain.MasterDefAccountRuleSetStandard;

public class MasterDefAccountRuleSetStandardDetResponse {

    private Long commonListItemId;
    private MasterDefAccountRuleSetStandard masterDefAccountRuleSetStandard;

    public Long getCommonListItemId() {
        return commonListItemId;
    }

    public void setCommonListItemId(Long commonListItemId) {
        this.commonListItemId = commonListItemId;
    }

    public MasterDefAccountRuleSetStandard getMasterDefAccountRuleSetStandard() {
        return masterDefAccountRuleSetStandard;
    }

    public void setMasterDefAccountRuleSetStandard(MasterDefAccountRuleSetStandard masterDefAccountRuleSetStandard) {
        this.masterDefAccountRuleSetStandard = masterDefAccountRuleSetStandard;
    }


}
