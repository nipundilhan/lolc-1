package com.fusionx.lending.origination.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Guarantor Update Resource
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-04-2021   		         FX-6157    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GuarantorDetailUpdateResource {
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String totalAssetValue;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String totalLiabilityValue;

	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^\\d*[0-9](\\.\\d{1,2})?$", message = "{common.invalid-amount-pattern}")
	private String networth;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;

	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;

	@Valid
	private List<NetWorthAssetUpdateResource> assets;
	
	@Valid
	private List<NetWorthLiabilityUpdateResource> liabilities;

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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<NetWorthAssetUpdateResource> getAssets() {
		return assets;
	}

	public void setAssets(List<NetWorthAssetUpdateResource> assets) {
		this.assets = assets;
	}

	public List<NetWorthLiabilityUpdateResource> getLiabilities() {
		return liabilities;
	}

	public void setLiabilities(List<NetWorthLiabilityUpdateResource> liabilities) {
		this.liabilities = liabilities;
	}
}
