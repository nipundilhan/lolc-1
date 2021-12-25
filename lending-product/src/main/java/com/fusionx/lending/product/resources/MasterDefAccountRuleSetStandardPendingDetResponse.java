package com.fusionx.lending.product.resources;

import com.fusionx.lending.product.domain.MasterDefAccountRuleSetStandardPending;

public class MasterDefAccountRuleSetStandardPendingDetResponse {

    private Long commonListItemId;
    private MasterDefAccountRuleSetStandardPending masterDefAccountRuleSetStandardPending;


    public Long getCommonListItemId() {
        return commonListItemId;
    }

    public void setCommonListItemId(Long commonItemId) {
        this.commonListItemId = commonItemId;
    }

    public MasterDefAccountRuleSetStandardPending getMasterDefAccountRuleSetStandardPending() {
        return masterDefAccountRuleSetStandardPending;
    }

    public void setMasterDefAccountRuleSetStandardPending(
            MasterDefAccountRuleSetStandardPending masterDefAccountRuleSetStandardPending) {
        this.masterDefAccountRuleSetStandardPending = masterDefAccountRuleSetStandardPending;
    }

}
