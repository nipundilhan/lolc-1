package com.fusionx.lending.origination.resource;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fusionx.lending.origination.enums.CommonYesNoStatus;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GuarantorDetailResponseRecource {
	
	private CommonYesNoStatus basedOnNetIncome;
	private CommonYesNoStatus basedOnNetWorth;
	private CommonYesNoStatus asAnAdditionalSecurity;
	private BigDecimal totalAssetValue;
	private BigDecimal totalLiabilityValue;
	private BigDecimal networth;
	private String createdUser;
	private Timestamp createdDate;
	private String modifiedUser;
	private Timestamp modifiedDate;
	
	List<NetWorthAssetResponseResource> assets;
	
	List<NetWorthLiabilityResponseResource> liabilities;

	public CommonYesNoStatus getBasedOnNetIncome() {
		return basedOnNetIncome;
	}

	public void setBasedOnNetIncome(CommonYesNoStatus basedOnNetIncome) {
		this.basedOnNetIncome = basedOnNetIncome;
	}

	public CommonYesNoStatus getBasedOnNetWorth() {
		return basedOnNetWorth;
	}

	public void setBasedOnNetWorth(CommonYesNoStatus basedOnNetWorth) {
		this.basedOnNetWorth = basedOnNetWorth;
	}

	public CommonYesNoStatus getAsAnAdditionalSecurity() {
		return asAnAdditionalSecurity;
	}

	public void setAsAnAdditionalSecurity(CommonYesNoStatus asAnAdditionalSecurity) {
		this.asAnAdditionalSecurity = asAnAdditionalSecurity;
	}

	public BigDecimal getTotalAssetValue() {
		return totalAssetValue;
	}

	public void setTotalAssetValue(BigDecimal totalAssetValue) {
		this.totalAssetValue = totalAssetValue;
	}

	public BigDecimal getTotalLiabilityValue() {
		return totalLiabilityValue;
	}

	public void setTotalLiabilityValue(BigDecimal totalLiabilityValue) {
		this.totalLiabilityValue = totalLiabilityValue;
	}

	public BigDecimal getNetworth() {
		return networth;
	}

	public void setNetworth(BigDecimal networth) {
		this.networth = networth;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<NetWorthAssetResponseResource> getAssets() {
		return assets;
	}

	public void setAssets(List<NetWorthAssetResponseResource> assets) {
		this.assets = assets;
	}

	public List<NetWorthLiabilityResponseResource> getLiabilities() {
		return liabilities;
	}

	public void setLiabilities(List<NetWorthLiabilityResponseResource> liabilities) {
		this.liabilities = liabilities;
	}
	
}
