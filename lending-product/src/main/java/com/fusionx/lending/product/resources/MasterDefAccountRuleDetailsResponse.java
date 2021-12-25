package com.fusionx.lending.product.resources;

import java.util.List;

import com.fusionx.lending.product.domain.MasterDefAccountRule;

public class MasterDefAccountRuleDetailsResponse {
	
	private Long masterDefinitionId;
	private MasterDefAccountRule masterDefAccountRule;
	
	private List<MasterDefAccountRuleSetStandardDetResponse> MasterDefAccountRuleSetStandardDetResponseList;

	public Long getMasterDefinitionId() {
		return masterDefinitionId;
	}

	public void setMasterDefinitionId(Long masterDefinitionId) {
		this.masterDefinitionId = masterDefinitionId;
	}

	public MasterDefAccountRule getMasterDefAccountRule() {
		return masterDefAccountRule;
	}

	public void setMasterDefAccountRule(MasterDefAccountRule masterDefAccountRule) {
		this.masterDefAccountRule = masterDefAccountRule;
	}

	public List<MasterDefAccountRuleSetStandardDetResponse> getMasterDefAccountRuleSetStandardDetResponseList() {
		return MasterDefAccountRuleSetStandardDetResponseList;
	}

	public void setMasterDefAccountRuleSetStandardDetResponseList(
			List<MasterDefAccountRuleSetStandardDetResponse> masterDefAccountRuleSetStandardDetResponseList) {
		MasterDefAccountRuleSetStandardDetResponseList = masterDefAccountRuleSetStandardDetResponseList;
	}

	
	
}
