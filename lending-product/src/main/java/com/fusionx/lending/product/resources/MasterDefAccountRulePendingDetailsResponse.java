package com.fusionx.lending.product.resources;

import java.util.List;

import com.fusionx.lending.product.domain.MasterDefAccountRule;
import com.fusionx.lending.product.domain.MasterDefAccountRulePending;
import com.fusionx.lending.product.domain.MasterDefAccountRuleSetStandardPending;

public class MasterDefAccountRulePendingDetailsResponse {
	

	private Long masterDefinitionId;
	private Long masterDefinitionPendingId;
	private Long masterDefAccountRuleId;
	private MasterDefAccountRulePending masterDefAccountRulePending;
	
	private List<MasterDefAccountRuleSetStandardPendingDetResponse> masterDefAccountRuleSetStandardPendingList;
	

	public Long getMasterDefinitionId() {
		return masterDefinitionId;
	}
	public void setMasterDefinitionId(Long masterDefinitionId) {
		this.masterDefinitionId = masterDefinitionId;
	}
	public Long getMasterDefinitionPendingId() {
		return masterDefinitionPendingId;
	}
	public void setMasterDefinitionPendingId(Long masterDefinitionPendingId) {
		this.masterDefinitionPendingId = masterDefinitionPendingId;
	}
	public Long getMasterDefAccountRuleId() {
		return masterDefAccountRuleId;
	}
	public void setMasterDefAccountRuleId(Long masterDefAccountRuleId) {
		this.masterDefAccountRuleId = masterDefAccountRuleId;
	}
	public MasterDefAccountRulePending getMasterDefAccountRulePending() {
		return masterDefAccountRulePending;
	}
	public void setMasterDefAccountRulePending(MasterDefAccountRulePending masterDefAccountRulePending) {
		this.masterDefAccountRulePending = masterDefAccountRulePending;
	}
	public List<MasterDefAccountRuleSetStandardPendingDetResponse> getMasterDefAccountRuleSetStandardPendingList() {
		return masterDefAccountRuleSetStandardPendingList;
	}
	public void setMasterDefAccountRuleSetStandardPendingList(
			List<MasterDefAccountRuleSetStandardPendingDetResponse> masterDefAccountRuleSetStandardPendingList) {
		this.masterDefAccountRuleSetStandardPendingList = masterDefAccountRuleSetStandardPendingList;
	}


}
