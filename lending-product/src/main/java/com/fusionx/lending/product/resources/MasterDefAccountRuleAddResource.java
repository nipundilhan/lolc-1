package com.fusionx.lending.product.resources;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MasterDefAccountRuleAddResource {
	
	@NotNull(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String masterDefinitionId;
	
	@Valid
	private MasterDefAccountRuleReconResource reconciliations;
	
	@Valid
	private MasterDefAccountRuleGlPostResource glEntryPosting;
	
	@Valid
	private List<CommonListUsageResource> commonListUsageList;


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

	public List<CommonListUsageResource> getCommonListUsageList() {
		return commonListUsageList;
	}

	public void setCommonListUsageList(List<CommonListUsageResource> commonListUsageList) {
		this.commonListUsageList = commonListUsageList;
	}

	public String getMasterDefinitionId() {
		return masterDefinitionId;
	}

	public void setMasterDefinitionId(String masterDefinitionId) {
		this.masterDefinitionId = masterDefinitionId;
	}





}
