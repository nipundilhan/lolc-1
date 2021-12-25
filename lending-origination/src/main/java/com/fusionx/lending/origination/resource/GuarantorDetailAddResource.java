package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Guarantor Details Add Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-04-2021   		         FX-6157    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GuarantorDetailAddResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{common-yesno.pattern}")
	private String basedOnNetIncome;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{common-yesno.pattern}")
	private String basedOnNetWorth;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "YES|NO", message = "{common-yesno.pattern}")
	private String asAnAdditionalSecurity;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String totalAssetValue;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String totalLiabilityValue;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String networth;
	
	@Valid
	private List<NetWorthAssetAddResource> assets;
	
	@Valid
	private List<NetWorthLiabilityAddResource> liabilities;

	public String getBasedOnNetIncome() {
		return basedOnNetIncome;
	}

	public void setBasedOnNetIncome(String basedOnNetIncome) {
		this.basedOnNetIncome = basedOnNetIncome;
	}

	public String getBasedOnNetWorth() {
		return basedOnNetWorth;
	}

	public void setBasedOnNetWorth(String basedOnNetWorth) {
		this.basedOnNetWorth = basedOnNetWorth;
	}

	public String getAsAnAdditionalSecurity() {
		return asAnAdditionalSecurity;
	}

	public void setAsAnAdditionalSecurity(String asAnAdditionalSecurity) {
		this.asAnAdditionalSecurity = asAnAdditionalSecurity;
	}

	public String getTotalAssetValue() {
		return totalAssetValue;
	}

	public void setTotalAssetValue(String totalAssetValue) {
		this.totalAssetValue = totalAssetValue;
	}

	public String getTotalLiabilityValue() {
		return totalLiabilityValue;
	}

	public void setTotalLiabilityValue(String totalLiabilityValue) {
		this.totalLiabilityValue = totalLiabilityValue;
	}

	public String getNetworth() {
		return networth;
	}

	public void setNetworth(String networth) {
		this.networth = networth;
	}

	public List<NetWorthAssetAddResource> getAssets() {
		return assets;
	}

	public void setAssets(List<NetWorthAssetAddResource> assets) {
		this.assets = assets;
	}

	public List<NetWorthLiabilityAddResource> getLiabilities() {
		return liabilities;
	}

	public void setLiabilities(List<NetWorthLiabilityAddResource> liabilities) {
		this.liabilities = liabilities;
	}

}
