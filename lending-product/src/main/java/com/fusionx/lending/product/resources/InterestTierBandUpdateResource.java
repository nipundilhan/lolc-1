package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * InterestTierBandUpdateResource 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   26-07-2021    FXL_July_2021_2  	FXL-53		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class InterestTierBandUpdateResource {
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String id;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String interestTempPendingId;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String tierBandSetPendingId;
	
	@NotBlank(message = "{common.not-null}")
	@Size(max = 4, min = 4, message = "{common-code.size}")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]+$", message = "{common.code-pattern}")
	private String code;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String tierValueMinimum;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String tierValueMaximum;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String tierValueMinTermId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String tierValueMinTermName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String minTermPeriodId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String minTermPeriodName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String tierValueMaxTermId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String tierValueMaxTermName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String maxTermPeriodId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String maxTermPeriodName;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String interestRateTypeId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String interestRateTypeName;
	
	@Pattern(regexp = "^$|\\d{1,20}\\.\\d{1,2}$",message="{common-amount.pattern}")
	private String repArp;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String loanPrInterestRateTypeId;
	
	@Size(max = 70, message = "{common-name.size}")
	private String loanPrInterestRateTypeName;
	
	@Pattern(regexp = "^$|^-?[0-9]{0,2}(\\.[0-9]{1,2})?$|^-?(100)(\\.[0]{1,2})?$",message="{rate.pattern}")
	private String loanPrInterestRate;
	
	@Size(max = 255, message = "{common-name.size}")
	private String note;
	
	@NotBlank(message = "{common.not-null}")
	@Pattern(regexp = "ACTIVE|INACTIVE", message = "{common-status.pattern}")
	private String status;
	
	@Pattern(regexp = "^$|[0-9]+", message = "{common-numeric.pattern}")
	private String version;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getInterestTempPendingId() {
		return interestTempPendingId;
	}

	public void setInterestTempPendingId(String interestTempPendingId) {
		this.interestTempPendingId = interestTempPendingId;
	}
	
	public String getTierBandSetPendingId() {
		return tierBandSetPendingId;
	}

	public void setTierBandSetPendingId(String tierBandSetPendingId) {
		this.tierBandSetPendingId = tierBandSetPendingId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getTierValueMinimum() {
		return tierValueMinimum;
	}

	public void setTierValueMinimum(String tierValueMinimum) {
		this.tierValueMinimum = tierValueMinimum;
	}
	
	public String getTierValueMaximum() {
		return tierValueMaximum;
	}

	public void setTierValueMaximum(String tierValueMaximum) {
		this.tierValueMaximum = tierValueMaximum;
	}
	
	public String getTierValueMinTermId() {
		return tierValueMinTermId;
	}

	public void setTierValueMinTermId(String tierValueMinTermId) {
		this.tierValueMinTermId = tierValueMinTermId;
	}
	
	public String getTierValueMinTermName() {
		return tierValueMinTermName;
	}

	public void setTierValueMinTermName(String tierValueMinTermName) {
		this.tierValueMinTermName = tierValueMinTermName;
	}
	
	public String getMinTermPeriodId() {
		return minTermPeriodId;
	}

	public void setMinTermPeriodId(String minTermPeriodId) {
		this.minTermPeriodId = minTermPeriodId;
	}
	
	public String getMinTermPeriodName() {
		return minTermPeriodName;
	}

	public void setMinTermPeriodName(String minTermPeriodName) {
		this.minTermPeriodName = minTermPeriodName;
	}
	
	public String getTierValueMaxTermId() {
		return tierValueMaxTermId;
	}

	public void setTierValueMaxTermId(String tierValueMaxTermId) {
		this.tierValueMaxTermId = tierValueMaxTermId;
	}
	
	public String getTierValueMaxTermName() {
		return tierValueMaxTermName;
	}

	public void setTierValueMaxTermName(String tierValueMaxTermName) {
		this.tierValueMaxTermName = tierValueMaxTermName;
	}
	
	public String getMaxTermPeriodId() {
		return maxTermPeriodId;
	}

	public void setMaxTermPeriodId(String maxTermPeriodId) {
		this.maxTermPeriodId = maxTermPeriodId;
	}
	
	public String getMaxTermPeriodName() {
		return maxTermPeriodName;
	}

	public void setMaxTermPeriodName(String maxTermPeriodName) {
		this.maxTermPeriodName = maxTermPeriodName;
	}
	
	public String getInterestRateTypeId() {
		return interestRateTypeId;
	}

	public void setInterestRateTypeId(String interestRateTypeId) {
		this.interestRateTypeId = interestRateTypeId;
	}
	
	public String getInterestRateTypeName() {
		return interestRateTypeName;
	}

	public void setInterestRateTypeName(String interestRateTypeName) {
		this.interestRateTypeName = interestRateTypeName;
	}
	
	public String getLoanPrInterestRateTypeId() {
		return loanPrInterestRateTypeId;
	}

	public void setLoanPrInterestRateTypeId(String loanPrInterestRateTypeId) {
		this.loanPrInterestRateTypeId = loanPrInterestRateTypeId;
	}
	
	public String getLoanPrInterestRateTypeName() {
		return loanPrInterestRateTypeName;
	}

	public void setLoanPrInterestRateTypeName(String loanPrInterestRateTypeName) {
		this.loanPrInterestRateTypeName = loanPrInterestRateTypeName;
	}
	
	public String getRepArp() {
		return repArp;
	}

	public void setRepArp(String repArp) {
		this.repArp = repArp;
	}
	
	public String getLoanPrInterestRate() {
		return loanPrInterestRate;
	}

	public void setLoanPrInterestRate(String loanPrInterestRate) {
		this.loanPrInterestRate = loanPrInterestRate;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


}
