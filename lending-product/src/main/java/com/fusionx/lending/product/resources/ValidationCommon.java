package com.fusionx.lending.product.resources;
 
/**
 * Service - Validation Class
 * @author     Amal
 * @Dat     21-08-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1    21-08-2019   FX-1508     FX-1600    Amal      Created
 *    
 ********************************************************************************************************
 */
 
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ValidationCommon{
 
    @JsonProperty("message")
    private String message;
 
    @JsonProperty("overdraftbalanceamount")
    private String overdraftbalanceamount;
 
    @JsonProperty("includedInMonthlyChargeIndicator")
    private String includedInMonthlyChargeIndicator;
 
    @JsonProperty("code")
    private String code;
 
    @JsonProperty("name")
    private String name;
 
    @JsonProperty("description")
    private String description;
 
    @JsonProperty("createdUser")
    private String createdUser;
 
    @JsonProperty("modifiedUser")
    private String modifiedUser;
 
    @JsonProperty("status")
    private String status;
 
    @JsonProperty("referenceCode")
    private String referenceCode;
 
    @JsonProperty("tierBandMethodId")
    private String tierBandMethodId;
 
    @JsonProperty("tierBandMethod")
    private String tierBandMethod;
 
    @JsonProperty("calculationMethodId")
    private String calculationMethodId;
 
    @JsonProperty("calculationMethod")
    private String calculationMethod;
 
    @JsonProperty("destinationId")
    private String destinationId;
 
    @JsonProperty("destination")
    private String destination;
 
    @JsonProperty("feeCategoryId")
    private String feeCategoryId;
 
    @JsonProperty("feeCategory")
    private String feeCategory;
 
    @JsonProperty("maxAllowedNoOfWithdrawals")
    private String maxAllowedNoOfWithdrawals;
 
    @JsonProperty("maxAllowedLimitForWithdrawals")
    private String maxAllowedLimitForWithdrawals;
 
    @JsonProperty("maxAllowedNoOfWithdrawalsPeriodId")
    private String maxAllowedNoOfWithdrawalsPeriodId ;
 
    @JsonProperty("maxAllowedNoOfWithdrawalsPeriod")
    private String maxAllowedNoOfWithdrawalsPeriod ;
 
    @JsonProperty("maxAllowedLimitForWithdrawalsPeriodId")
    private String maxAllowedLimitForWithdrawalsPeriodId ;
 
    @JsonProperty("maxAllowedLimitForWithdrawalsPeriod")
    private String maxAllowedLimitForWithdrawalsPeriod ;
 
    @JsonProperty("minBalanceForInterest")
    private String minBalanceForInterest;
 
    @JsonProperty("identification")
    private String identification;
 
    @JsonProperty("effectiveDate")
    private String effectiveDate;
 
    @JsonProperty("tierValueMinimum")
    private String tierValueMinimum;
 
    @JsonProperty("tierValueMaximum")
    private String tierValueMaximum; 
 
    @JsonProperty("overdraftFeesChargesId")
    private String overdraftFeesChargesId; 
 
    @JsonProperty("calculationFrequencyId")
    private String calculationFrequencyId;
 
    @JsonProperty("calculationFrequency")
    private String calculationFrequency;
 
    @JsonProperty("applicationFrequencyId")
    private String applicationFrequencyId;
 
    @JsonProperty("applicationFrequency")
    private String applicationFrequency;
 
    @JsonProperty("depositInterestAppliedCoverageId")
    private String depositInterestAppliedCoverageId;
 
    @JsonProperty("depositInterestAppliedCoverage")
    private String depositInterestAppliedCoverage;
 
    @JsonProperty("authorisedIndicator")
    private String authorisedIndicator;
 
    @JsonProperty("aer")
    private String aer;
 
    @JsonProperty("ear")
    private String ear;
 
    @JsonProperty("bankInterestRateTypeId")
    private String bankInterestRateTypeId;    
 
    @JsonProperty("bankInterestRateType")
    private String bankInterestRateType;
 
    @JsonProperty("bankInterestRate")
    private String bankInterestRate;
 
    @JsonProperty("depositInterestApplicableBaseId")
    private String depositInterestApplicableBaseId;
 
    @JsonProperty("depositInterestApplicableBase")
    private String depositInterestApplicableBase;
 
    @JsonProperty("bankInterestCalBasisId")
    private String bankInterestCalBasisId;    
 
    @JsonProperty("bankInterestCalBasis")
    private String bankInterestCalBasis;    
 
    @JsonProperty("fixedVariableInterestRateTypeId")
    private String fixedVariableInterestRateTypeId;
 
    @JsonProperty("fixedVariableInterestRateType")
    private String fixedVariableInterestRateType;
 
    @JsonProperty("segmentId")
    private String segmentId;
 
    @JsonProperty("segment")
    private String segment;
 
    @JsonProperty("minimumBalance")
    private String minimumBalance;
 
    @JsonProperty("minimumAmountforAccountOpening")
    private String minimumAmountforAccountOpening;
 
    @JsonProperty("borrowerLimit")
    private String borrowerLimit;
 
    @JsonProperty("dormantPeriod")
    private String dormantPeriod;
 
    @JsonProperty("schemeName")
    private String schemeName;
 
    @JsonProperty("cardName")
    private String cardName;
 
    @JsonProperty("dormantPeriodTypeId")
    private String dormantPeriodTypeId;
 
    @JsonProperty("dormantPeriodType")
    private String dormantPeriodType;
 
    @JsonProperty("feeFreeLength")
    private String feeFreeLength;
 
    @JsonProperty("feeFreeLengthPeriodId")
    private String feeFreeLengthPeriodId;
 
    @JsonProperty("feeFreeLengthPeriod")
    private String feeFreeLengthPeriod;
 
    @JsonProperty("withdrawalsAllowedAfter")
    private String withdrawalsAllowedAfter;
 
    @JsonProperty("withdrawalsAllowedAfterPeriodId")
    private String withdrawalsAllowedAfterPeriodId;
 
    @JsonProperty("withdrawalsAllowedAfterPeriod")
    private String withdrawalsAllowedAfterPeriod;
 
    @JsonProperty("feeCategoryTypeId")
    private String feeCategoryTypeId;
 
    @JsonProperty("version")
    private String version;
 
    @JsonProperty("feeCategoryType")
    private String feeCategoryType;
 
    @JsonProperty("feeType")
    private String feeType;
 
    @JsonProperty("feeTypeId")
    private String feeTypeId;
 
    @JsonProperty("feeRateType")
    private String feeRateType;
 
    @JsonProperty("overdraftFeeChargeCapId")
    private String overdraftFeeChargeCapId;
 
    @JsonProperty(" overdraftControlIndicator")
    private String overdraftControlIndicator;
 
    @JsonProperty("feeRateTypeId")
    private String feeRateTypeId;
 
    @JsonProperty("feeRate")
    private String feeRate;
 
    @JsonProperty("feeAmount")
    private String feeAmount;
 
    @JsonProperty("negotiableIndicator")
    private String negotiableIndicator;
 
    @JsonProperty("minimumAmount")
    private String minimumAmount;
 
    @JsonProperty("maximumAmount")
    private String maximumAmount;
 
    @JsonProperty("minimumRate")
    private String minimumRate;
 
    @JsonProperty("maximumRate")
    private String maximumRate;
 
    @JsonProperty("minimumArrangedOverdraftAmount")
    private String minimumArrangedOverdraftAmount;
 
    @JsonProperty("attribute1")
    private String attribute1;
 
    @JsonProperty("attribute2")
    private String attribute2;
 
    @JsonProperty("attribute3")
    private String attribute3;
 
    @JsonProperty("attribute4")
    private String attribute4;
 
    @JsonProperty("attribute5")
    private String attribute5;
 
    @JsonProperty("notes")
    private String notes;
 
    @JsonProperty("tariffTypeId")
    private String tariffTypeId;
 
    @JsonProperty("tariffType")
    private String tariffType;
 
    @JsonProperty("typeName")
    private String typeName;
 
    @JsonProperty("typeId")
    private String typeId;
 
    @JsonProperty("amount")
    private String amount;
 
    @JsonProperty("periodId")
    private String periodId;
 
    @JsonProperty("period")
    private String period;
 
    @JsonProperty("indicator")
    private String indicator;
 
    @JsonProperty("tierBandSetId")
    private String tierBandSetId;
 
    @JsonProperty("creditInterestEligibilityId")
    private String creditInterestEligibilityId;
    
    @JsonProperty("penalTierBandSetId")
    private String penalTierBandSetId;
 
    @JsonProperty("penalInterestId")
    private String penalInterestId;

    @JsonProperty("prdCalStartPoint")
    private String prdCalStartPoint;
    
    @JsonProperty("pnlTbsName")
    private String pnlTbsName;
    
    @JsonProperty("gracePeriodLength")
    private String gracePeriodLength;
    
    @JsonProperty("penalTierBandOverLap")
    private String penalTierBandOverLap;
    
    @JsonProperty("penalInterestEligibilityId")
    private String penalInterestEligibilityId;
 
    @JsonProperty("onSaleIndicator")
    private String onSaleIndicator;
 
    @JsonProperty("productCategoryId")
    private String productCategoryId;
 
    @JsonProperty("productCategory")
    private String productCategory;
 
    @JsonProperty("brandId")
    private String brandId;
 
    @JsonProperty("tcsAndCsUrl")
    private String tcsAndCsUrl;
 
    @JsonProperty("monthlyCharge")
    private String monthlyCharge;
 
    @JsonProperty("currencyId")
    private String currencyId;
 
    @JsonProperty("currencyCode")
    private String currencyCode;
 
    @JsonProperty("productDescription")
    private String productDescription;
 
    @JsonProperty("productUrl")
    private String productUrl;
 
    @JsonProperty("servicingChannelId")
    private String servicingChannelId;
 
    @JsonProperty("servicingChannel")
    private String servicingChannel;
 
    @JsonProperty("salesAccessChannelId")
    private String salesAccessChannelId;
 
    @JsonProperty("salesAccessChannel")
    private String salesAccessChannel;
 
    @JsonProperty("coreProductId")
    private String coreProductId;
 
    @JsonProperty("creditInterestId")
    private String creditInterestId;
 
    @JsonProperty("marketingState")
    private String marketingState;
 
    @JsonProperty("feeChargeCapId")
    private String feeChargeCapId;
 
    @JsonProperty("marketingStateId")
    private String marketingStateId;
 
    @JsonProperty("stateTenurePeriod")
    private String stateTenurePeriod;
 
    @JsonProperty("stateTenurePeriodId")
    private String stateTenurePeriodId;
 
    @JsonProperty("accountTypeId")
    private String accountTypeId;
 
    @JsonProperty("accountType")
    private String accountType;
 
    @JsonProperty("lastMarketedDate")
    private String lastMarketedDate;
 
    @JsonProperty("firstMarketedDate")
    private String firstMarketedDate;
 
    @JsonProperty("restrictedStatus")
    private String restrictedStatus;
 
    @JsonProperty("stateTenureLength")
    private String stateTenureLength;
 
    @JsonProperty("productId")
    private String productId;
 
    @JsonProperty("subProductId")
    private String subProductId;
    
    @JsonProperty("departmentId")
    private String departmentId;
    
    @JsonProperty("departmentDes")
    private String departmentDes;
    
 
    @JsonProperty("otherFeesChargesId")
    private String otherFeesChargesId;
 
    @JsonProperty("overdraftTierBandId")
    private String overdraftTierBandId;
 
    @JsonProperty("deductIndicator")
    private String deductIndicator;
 
    @JsonProperty("feeIndicator")
    private String feeIndicator; 
 
    @JsonProperty("chargesId")
    private String chargesId; 
 
    @JsonProperty("minimumAge")
    private String minimumAge;
 
    @JsonProperty("maximumAge")
    private String maximumAge;
 
    @JsonProperty("predecessorId")
    private String predecessorId;
 
    @JsonProperty("featureBenefitItemId")
    private String featureBenefitItemId;
 
    @JsonProperty("featureBenefitEligibilityId")
    private String featureBenefitEligibilityId;
 
    @JsonProperty("url")
    private String url;
 
    @JsonProperty("benefitGroupNominalValue")
    private String benefitGroupNominalValue;
 
    @JsonProperty("fee")
    private String fee;
 
    @JsonProperty("featuresBenefitsId")
    private String featuresBenefitsId;
 
    @JsonProperty("featureBenefitGroupId")
    private String featureBenefitGroupId;
 
    @JsonProperty("type")
    private String type;
 
    @JsonProperty("unit")
    private String unit;
 
    @JsonProperty("ageEligibilityId")
    private String ageEligibilityId;
 
    @JsonProperty("idEligibilityId")
    private String idEligibilityId;
 
    @JsonProperty("creditCheckEligibilityId")
    private String creditCheckEligibilityId;
 
    @JsonProperty("industryEligibilityId")
    private String industryEligibilityId;
 
    @JsonProperty("otherEligibilityTypeId")
    private String otherEligibilityTypeId;
 
    @JsonProperty("textual")
    private String textual;
 
    @JsonProperty("eligibilityId")
    private String eligibilityId;
 
    @JsonProperty("otherEligibilityId")
    private String otherEligibilityId;
 
    @JsonProperty("residencyTypeId")
    private String residencyTypeId;
 
    @JsonProperty("residencyEligibilityId")
    private String residencyEligibilityId;
 
    @JsonProperty("residencyIncludeCountryId")
    private String residencyIncludeCountryId;
 
    @JsonProperty("countryName")
    private String countryName;
 
    @JsonProperty("otherSciCode")
    private String otherSciCode;
 
    @JsonProperty("transactionCodeId")
    private String transactionCodeId;
 
    @JsonProperty("transactionSubCodeId")
    private String transactionSubCodeId;
 
    @JsonProperty("transactionSubCodeDescription")
    private String transactionSubCodeDescription;
 
    @JsonProperty("scoringType")
    private String scoringType;
 
    @JsonProperty("calculationFrequencyCode")
    private String calculationFrequencyCode;
 
    @JsonProperty("applicationFrequencyCode")
    private String applicationFrequencyCode;
 
    @JsonProperty("subProductCode")
    private String subProductCode;
 
    @JsonProperty("chargeBaseAmount")
    private String chargeBaseAmount;
 
    @JsonProperty("legalStructureId")
    private String legalStructureId;
 
    @JsonProperty("overdraftId")
    private String overdraftId;
 
    @JsonProperty("officerTypeDesignationId")
    private String officerTypeDesignationId;
 
    @JsonProperty("designationName")
    private String designationName;
 
    @JsonProperty("minAmount")
    private String minAmount;
 
    @JsonProperty("overdraftTypeId")
    private String overdraftTypeId;
 
    @JsonProperty("maxAmount")
    private String maxAmount;
 
    @JsonProperty("bankGuaranteedIndicator")
    private String bankGuaranteedIndicator;
 
    @JsonProperty("featureBenefitsMobileWalletTypeId")
    private String featureBenefitsMobileWalletTypeId;
 
    @JsonProperty("featureBenefitsMobileWalletId")
    private String featureBenefitsMobileWalletId;
 
    @JsonProperty("featureBenefitsCardTypeId")
    private String featureBenefitsCardTypeId;
 
    @JsonProperty("featureBenefitsCardSchemeId")
    private String featureBenefitsCardSchemeId;
 
    @JsonProperty("agreementLengthMaximum")
    private String agreementLengthMaximum;
 
    @JsonProperty("agreementLengthMinimum")
    private String agreementLengthMinimum;
 
    @JsonProperty("overdraftTierBandSetId")
    private String overdraftTierBandSetId;
 
    @JsonProperty("overdraftType")
    private String overdraftType;
 
    @JsonProperty("contactlessIndicator")
    private String contactlessIndicator;
 
    @JsonProperty("maxDailyCardWithdrawalLimit")
    private String maxDailyCardWithdrawalLimit;
 
    @JsonProperty("officerEligibilityId")
    private String officerEligibilityId;
 
    @JsonProperty("trdingTypeId")
    private String trdingTypeId;
 
    @JsonProperty("minMaxType")
    private String minMaxType;
 
    @JsonProperty("tradingEligibilityId")
    private String tradingEligibilityId;
 
    @JsonProperty("stoFullPeriodType")
    private String stoFullPeriodType;
 
    @JsonProperty("stoFullPeriodLength")
    private String stoFullPeriodLength;
 
    @JsonProperty("stoFullPeriodTypeId")
    private String stoFullPeriodTypeId;
 
    @JsonProperty("stoPartialPeriodTypeId")
    private String stoPartialPeriodTypeId;
 
    @JsonProperty("stoPartialPeriodType")
    private String stoPartialPeriodType;
 
    @JsonProperty("stoPartialPeriodLength")
    private String stoPartialPeriodLength;
 
    @JsonProperty("periodName")
    private String periodName;
 
    @JsonProperty("legalStructureName")
    private String legalStructureName;
 
    @JsonProperty("residencyTypeName")
    private String residencyTypeName;
 
    @JsonProperty("cappingPeriodId")
    private String cappingPeriodId;
 
    @JsonProperty("cappingPeriod")
    private String cappingPeriod;
 
    @JsonProperty("feeCapOccurance")
    private String feeCapOccurance;
 
    @JsonProperty("feeCapAmount")
    private String feeCapAmount;
 
    @JsonProperty("cardTypeId")
    private String cardTypeId;
 
    @JsonProperty("cardSchemeId")
    private String cardSchemeId;
 
    @JsonProperty("schemeId")
    private String schemeId;
 
    @JsonProperty("walletTypeId")
    private String walletTypeId;
 
    @JsonProperty("featureBenefitMobileWalletId")
    private String featureBenefitMobileWalletId;
 
    @JsonProperty("featureBenefitCardId")
    private String featureBenefitCardId;
 
    @JsonProperty("featureBenefitMobileWalletMapId")
    private String featureBenefitMobileWalletMapId;
 
    @JsonProperty("featureBenefitCardMapId")
    private String featureBenefitCardMapId;
 
    @JsonProperty("featureBenefitCardSchemeId")
    private String featureBenefitCardSchemeId;
 
    @JsonProperty("bonusInterestEligibilityId")
    private String bonusInterestEligibilityId;
 
    @JsonProperty("bonusTierBandSetId")
    private String bonusTierBandSetId;
 
    @JsonProperty("bonusInterestId")
    private String bonusInterestId;
 
    @JsonProperty("frquencyTypeId")
    private String frquencyTypeId;
 
    @JsonProperty("rateType")
    private String rateType;
 
    @JsonProperty("rate")
    private String rate;
    
    @JsonProperty("productType")
    private String productType;
 
   /* @JsonProperty("accountType")
    private String accountType;*/
 
    @JsonProperty("texual")
    private String texual;

    @JsonProperty("chargeRecoveryMethod")
    private String chargeRecoveryMethod;
    
    // Added by Senitha
    @JsonProperty("otherEligibilityCode")
    private String otherEligibilityCode;
    
    @JsonProperty("eligibilityCode")
    private String eligibilityCode;
    
    @JsonProperty("residencyEligibilityCode")
    private String residencyEligibilityCode;
    
    @JsonProperty("tradingEligibilityHistoryCode")
    private String tradingEligibilityHistoryCode;
    
    @JsonProperty("legalStructureEligibility")
    private String legalStructureEligibility;
    
    @JsonProperty("officerEligibilityCode")
    private String officerEligibilityCode;
    
    @JsonProperty("otherFeesChargesCode")
    private String otherFeesChargesCode;
    
    @JsonProperty("feeChargeDetailCode")
    private String feeChargeDetailCode;
    
    @JsonProperty("feeChargeCapCode")
    private String feeChargeCapCode;
    
    @JsonProperty("templateName")
    private String templateName;
    
    
    
    public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getFeeChargeCapCode() {
		return feeChargeCapCode;
	}

	public void setFeeChargeCapCode(String feeChargeCapCode) {
		this.feeChargeCapCode = feeChargeCapCode;
	}

	public String getOtherFeesChargesCode() {
		return otherFeesChargesCode;
	}

	public void setOtherFeesChargesCode(String otherFeesChargesCode) {
		this.otherFeesChargesCode = otherFeesChargesCode;
	}

	public String getFeeChargeDetailCode() {
		return feeChargeDetailCode;
	}

	public void setFeeChargeDetailCode(String feeChargeDetailCode) {
		this.feeChargeDetailCode = feeChargeDetailCode;
	}

	public String getLegalStructureEligibility() {
		return legalStructureEligibility;
	}

	public void setLegalStructureEligibility(String legalStructureEligibility) {
		this.legalStructureEligibility = legalStructureEligibility;
	}

	public String getOfficerEligibilityCode() {
		return officerEligibilityCode;
	}

	public void setOfficerEligibilityCode(String officerEligibilityCode) {
		this.officerEligibilityCode = officerEligibilityCode;
	}

	public String getTradingEligibilityHistoryCode() {
		return tradingEligibilityHistoryCode;
	}

	public void setTradingEligibilityHistoryCode(String tradingEligibilityHistoryCode) {
		this.tradingEligibilityHistoryCode = tradingEligibilityHistoryCode;
	}

	public String getResidencyEligibilityCode() {
		return residencyEligibilityCode;
	}

	public void setResidencyEligibilityCode(String residencyEligibilityCode) {
		this.residencyEligibilityCode = residencyEligibilityCode;
	}

	public String getEligibilityCode() {
		return eligibilityCode;
	}

	public void setEligibilityCode(String eligibilityCode) {
		this.eligibilityCode = eligibilityCode;
	}

	public String getOtherEligibilityCode() {
		return otherEligibilityCode;
	}

	public void setOtherEligibilityCode(String otherEligibilityCode) {
		this.otherEligibilityCode = otherEligibilityCode;
	}
	
	/////////////////////////////

	public String getOverdraftFeesChargesId() {
        return overdraftFeesChargesId;
    }
 
    public String getProductType() {
        return productType;
    }
 
    public void setProductType(String productType) {
        this.productType = productType;
    }
 
    public void setOverdraftFeesChargesId(String overdraftFeesChargesId) {
        this.overdraftFeesChargesId = overdraftFeesChargesId;
    }
 
    public String getOverdraftFeeChargeCapId() {
        return overdraftFeeChargeCapId;
    }
 
    public void setOverdraftFeeChargeCapId(String overdraftFeeChargeCapId) {
        this.overdraftFeeChargeCapId = overdraftFeeChargeCapId;
    }
 
    public String getAgreementLengthMaximum() {
        return agreementLengthMaximum;
    }
 
    public void setAgreementLengthMaximum(String agreementLengthMaximum) {
        this.agreementLengthMaximum = agreementLengthMaximum;
    }
 
    public String getEar() {
        return ear;
    }
 
    public void setEar(String ear) {
        this.ear = ear;
    }
 
 
    public String getAgreementLengthMinimum() {
        return agreementLengthMinimum;
    }
 
    public void setAgreementLengthMinimum(String agreementLengthMinimum) {
        this.agreementLengthMinimum = agreementLengthMinimum;
    }
 
    public String getBankGuaranteedIndicator() {
        return bankGuaranteedIndicator;
    }
 
    public void setBankGuaranteedIndicator(String bankGuaranteedIndicator) {
        this.bankGuaranteedIndicator = bankGuaranteedIndicator;
    }
 
    public String getOverdraftTierBandSetId() {
        return overdraftTierBandSetId;
    }
 
    public void setOverdraftTierBandSetId(String overdraftTierBandSetId) {
        this.overdraftTierBandSetId = overdraftTierBandSetId;
    }
 
 
 
 
    public String getFeatureBenefitCardSchemeId() {
        return featureBenefitCardSchemeId;
    }
 
    public void setFeatureBenefitCardSchemeId(String featureBenefitCardSchemeId) {
        this.featureBenefitCardSchemeId = featureBenefitCardSchemeId;
    }
 
    public String getFeatureBenefitMobileWalletId() {
        return featureBenefitMobileWalletId;
    }
 
    public void setFeatureBenefitMobileWalletId(String featureBenefitMobileWalletId) {
        this.featureBenefitMobileWalletId = featureBenefitMobileWalletId;
    }
 
    public String getFeatureBenefitCardId() {
        return featureBenefitCardId;
    }
 
    public void setFeatureBenefitCardId(String featureBenefitCardId) {
        this.featureBenefitCardId = featureBenefitCardId;
    }
 
    public String getFeatureBenefitMobileWalletMapId() {
        return featureBenefitMobileWalletMapId;
    }
 
    public void setFeatureBenefitMobileWalletMapId(String featureBenefitMobileWalletMapId) {
        this.featureBenefitMobileWalletMapId = featureBenefitMobileWalletMapId;
    }
 
    public String getFeatureBenefitCardMapId() {
        return featureBenefitCardMapId;
    }
 
    public void setFeatureBenefitCardMapId(String featureBenefitCardMapId) {
        this.featureBenefitCardMapId = featureBenefitCardMapId;
    }
 
    public String getStoFullPeriodType() {
        return stoFullPeriodType;
    }
 
    public void setStoFullPeriodType(String stoFullPeriodType) {
        this.stoFullPeriodType = stoFullPeriodType;
    }
 
    public String getStoFullPeriodLength() {
        return stoFullPeriodLength;
    }
 
    public void setStoFullPeriodLength(String stoFullPeriodLength) {
        this.stoFullPeriodLength = stoFullPeriodLength;
    }
 
    public String getStoFullPeriodTypeId() {
        return stoFullPeriodTypeId;
    }
 
    public void setStoFullPeriodTypeId(String stoFullPeriodTypeId) {
        this.stoFullPeriodTypeId = stoFullPeriodTypeId;
    }
 
    public String getStoPartialPeriodTypeId() {
        return stoPartialPeriodTypeId;
    }
 
    public void setStoPartialPeriodTypeId(String stoPartialPeriodTypeId) {
        this.stoPartialPeriodTypeId = stoPartialPeriodTypeId;
    }
 
    public String getStoPartialPeriodType() {
        return stoPartialPeriodType;
    }
 
    public void setStoPartialPeriodType(String stoPartialPeriodType) {
        this.stoPartialPeriodType = stoPartialPeriodType;
    }
 
    public String getStoPartialPeriodLength() {
        return stoPartialPeriodLength;
    }
 
    public void setStoPartialPeriodLength(String stoPartialPeriodLength) {
        this.stoPartialPeriodLength = stoPartialPeriodLength;
    }
 
    public String getSchemeId() {
        return schemeId;
    }
 
    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }
 
    public String getFeeChargeCapId() {
        return feeChargeCapId;
    }
 
    public void setFeeChargeCapId(String feeChargeCapId) {
        this.feeChargeCapId = feeChargeCapId;
    }
 
    public String getWalletTypeId() {
        return walletTypeId;
    }
 
    public void setWalletTypeId(String walletTypeId) {
        this.walletTypeId = walletTypeId;
    }
 
    public String getMinimumArrangedOverdraftAmount() {
        return minimumArrangedOverdraftAmount;
    }
 
    public void setMinimumArrangedOverdraftAmount(String minimumArrangedOverdraftAmount) {
        this.minimumArrangedOverdraftAmount = minimumArrangedOverdraftAmount;
    }
 
    public String getFeatureBenefitsMobileWalletTypeId() {
        return featureBenefitsMobileWalletTypeId;
    }
 
    public void setFeatureBenefitsMobileWalletTypeId(String featureBenefitsMobileWalletTypeId) {
        this.featureBenefitsMobileWalletTypeId = featureBenefitsMobileWalletTypeId;
    }
 
    public String getFeatureBenefitsMobileWalletId() {
        return featureBenefitsMobileWalletId;
    }
 
    public void setFeatureBenefitsMobileWalletId(String featureBenefitsMobileWalletId) {
        this.featureBenefitsMobileWalletId = featureBenefitsMobileWalletId;
    }
 
    public String getFeatureBenefitsCardTypeId() {
        return featureBenefitsCardTypeId;
    }
 
    public void setFeatureBenefitsCardTypeId(String featureBenefitsCardTypeId) {
        this.featureBenefitsCardTypeId = featureBenefitsCardTypeId;
    }
 
    public String getFeatureBenefitsCardSchemeId() {
        return featureBenefitsCardSchemeId;
    }
 
    public void setFeatureBenefitsCardSchemeId(String featureBenefitsCardSchemeId) {
        this.featureBenefitsCardSchemeId = featureBenefitsCardSchemeId;
    }
 
 
    public String getContactlessIndicator() {
        return contactlessIndicator;
    }
 
    public void setContactlessIndicator(String contactlessIndicator) {
        this.contactlessIndicator = contactlessIndicator;
    }
 
    public String getMaxDailyCardWithdrawalLimit() {
        return maxDailyCardWithdrawalLimit;
    }
 
    public void setMaxDailyCardWithdrawalLimit(String maxDailyCardWithdrawalLimit) {
        this.maxDailyCardWithdrawalLimit = maxDailyCardWithdrawalLimit;
    }
 
    public String getOverdraftId() {
        return overdraftId;
    }
 
    public void setOverdraftId(String overdraftId) {
        this.overdraftId = overdraftId;
    }
 
 
    public String getCardTypeId() {
        return cardTypeId;
    }
 
    public void setCardTypeId(String cardTypeId) {
        this.cardTypeId = cardTypeId;
    }
 
    public String getCardSchemeId() {
        return cardSchemeId;
    }
 
    public void setCardSchemeId(String cardSchemeId) {
        this.cardSchemeId = cardSchemeId;
    }
 
 
    @JsonProperty("legalStructureEligibilityId")
    private String legalStructureEligibilityId;
 
 
    public String getCalculationFrequencyCode() {
        return calculationFrequencyCode;
    }
 
    public void setCalculationFrequencyCode(String calculationFrequencyCode) {
        this.calculationFrequencyCode = calculationFrequencyCode;
    }
 
    public String getApplicationFrequencyCode() {
        return applicationFrequencyCode;
    }
 
    public void setApplicationFrequencyCode(String applicationFrequencyCode) {
        this.applicationFrequencyCode = applicationFrequencyCode;
    }
 
    public String getSubProductCode() {
        return subProductCode;
    }
 
    public void setSubProductCode(String subProductCode) {
        this.subProductCode = subProductCode;
    }
 
    public String getChargeBaseAmount() {
        return chargeBaseAmount;
    }
 
    public void setChargeBaseAmount(String chargeBaseAmount) {
        this.chargeBaseAmount = chargeBaseAmount;
    }
 
    public String getTransactionCodeId() {
        return transactionCodeId;
    }
 
    public void setTransactionCodeId(String transactionCodeId) {
        this.transactionCodeId = transactionCodeId;
    }
 
    public String getTransactionSubCodeId() {
        return transactionSubCodeId;
    }
 
    public void setTransactionSubCodeId(String transactionSubCodeId) {
        this.transactionSubCodeId = transactionSubCodeId;
    }
 
    public String getTransactionSubCodeDescription() {
        return transactionSubCodeDescription;
    }
 
    public void setTransactionSubCodeDescription(String transactionSubCodeDescription) {
        this.transactionSubCodeDescription = transactionSubCodeDescription;
    }
 
    public String getIncludedInMonthlyChargeIndicator() {
        return includedInMonthlyChargeIndicator;
    }
 
    public void setIncludedInMonthlyChargeIndicator(String includedInMonthlyChargeIndicator) {
        this.includedInMonthlyChargeIndicator = includedInMonthlyChargeIndicator;
    }
 
    public String getFeatureBenefitGroupId() {
        return featureBenefitGroupId;
    }
 
    public void setFeatureBenefitGroupId(String featureBenefitGroupId) {
        this.featureBenefitGroupId = featureBenefitGroupId;
    }
 
    public String getFeaturesBenefitsId() {
        return featuresBenefitsId;
    }
 
    public void setFeaturesBenefitsId(String featuresBenefitsId) {
        this.featuresBenefitsId = featuresBenefitsId;
    }
 
    public String getCappingPeriodId() {
        return cappingPeriodId;
    }
 
    public void setCappingPeriodId(String cappingPeriodId) {
        this.cappingPeriodId = cappingPeriodId;
    }
 
    public String getCappingPeriod() {
        return cappingPeriod;
    }
 
    public void setCappingPeriod(String cappingPeriod) {
        this.cappingPeriod = cappingPeriod;
    }
 
    public String getFeeCapOccurance() {
        return feeCapOccurance;
    }
 
    public void setFeeCapOccurance(String feeCapOccurance) {
        this.feeCapOccurance = feeCapOccurance;
    }
 
    public String getFeeCapAmount() {
        return feeCapAmount;
    }
 
    public void setFeeCapAmount(String feeCapAmount) {
        this.feeCapAmount = feeCapAmount;
    }
 
    public String getBenefitGroupNominalValue() {
        return benefitGroupNominalValue;
    }
 
    public void setBenefitGroupNominalValue(String benefitGroupNominalValue) {
        this.benefitGroupNominalValue = benefitGroupNominalValue;
    }
 
    public String getFee() {
        return fee;
    }
 
    public void setFee(String fee) {
        this.fee = fee;
    }
 
    public String getFeatureBenefitItemId() {
        return featureBenefitItemId;
    }
 
    public void setFeatureBenefitItemId(String featureBenefitItemId) {
        this.featureBenefitItemId = featureBenefitItemId;
    }
 
    public String getFeatureBenefitEligibilityId() {
        return featureBenefitEligibilityId;
    }
 
    public void setFeatureBenefitEligibilityId(String featureBenefitEligibilityId) {
        this.featureBenefitEligibilityId = featureBenefitEligibilityId;
    }
 
 
 
    public String getChargesId() {
        return chargesId;
    }
 
    public void setChargesId(String chargesId) {
        this.chargesId = chargesId;
    }
 
    public String getProductId() {
        return productId;
    }
 
    public void setProductId(String productId) {
        this.productId = productId;
    }
 
    public String getDeductIndicator() {
        return deductIndicator;
    }
 
    public void setDeductIndicator(String deductIndicator) {
        this.deductIndicator = deductIndicator;
    }
 
    public String getFeeIndicator() {
        return feeIndicator;
    }
 
    public void setFeeIndicator(String feeIndicator) {
        this.feeIndicator = feeIndicator;
    }
 
    public String getSubProductId() {
        return subProductId;
    }
 
    public void setSubProductId(String subProductId) {
        this.subProductId = subProductId;
    }
 
    public String getOtherFeesChargesId() {
        return otherFeesChargesId;
    }
 
    public void setOtherFeesChargesId(String otherFeesChargesId) {
        this.otherFeesChargesId = otherFeesChargesId;
    }
 
    public String getRestrictedStatus() {
        return restrictedStatus;
    }
 
    public void setRestrictedStatus(String restrictedStatus) {
        this.restrictedStatus = restrictedStatus;
    }
 
    public String getLastMarketedDate() {
        return lastMarketedDate;
    }
 
    public void setLastMarketedDate(String lastMarketedDate) {
        this.lastMarketedDate = lastMarketedDate;
    }
 
    public String getFirstMarketedDate() {
        return firstMarketedDate;
    }
 
    public void setFirstMarketedDate(String firstMarketedDate) {
        this.firstMarketedDate = firstMarketedDate;
    }
 
    public String getAccountTypeId() {
        return accountTypeId;
    }
 
    public void setAccountTypeId(String accountTypeId) {
        this.accountTypeId = accountTypeId;
    }
 
    public String getSchemeName() {
        return schemeName;
    }
 
    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }
 
    public String getCardName() {
        return cardName;
    }
 
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
 
    public String getAccountType() {
        return accountType;
    }
 
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
 
    public String getStateTenurePeriod() {
        return stateTenurePeriod;
    }
 
    public void setStateTenurePeriod(String stateTenurePeriod) {
        this.stateTenurePeriod = stateTenurePeriod;
    }
 
    public String getStateTenurePeriodId() {
        return stateTenurePeriodId;
    }
 
    public void setStateTenurePeriodId(String stateTenurePeriodId) {
        this.stateTenurePeriodId = stateTenurePeriodId;
    }
 
 
    public String getMarketingState() {
        return marketingState;
    }
 
    public void setMarketingState(String marketingState) {
        this.marketingState = marketingState;
    }
 
    public String getMarketingStateId() {
        return marketingStateId;
    }
 
    public String getStateTenureLength() {
        return stateTenureLength;
    }
 
    public void setStateTenureLength(String stateTenureLength) {
        this.stateTenureLength = stateTenureLength;
    }
 
    public void setMarketingStateId(String marketingStateId) {
        this.marketingStateId = marketingStateId;
    }
 
    public String getCreditInterestId() {
        return creditInterestId;
    }
 
    public void setCreditInterestId(String creditInterestId) {
        this.creditInterestId = creditInterestId;
    }
 
    public String getServicingChannelId() {
        return servicingChannelId;
    }
 
    public void setServicingChannelId(String servicingChannelId) {
        this.servicingChannelId = servicingChannelId;
    }
 
    public String getServicingChannel() {
        return servicingChannel;
    }
 
    public void setServicingChannel(String servicingChannel) {
        this.servicingChannel = servicingChannel;
    }
 
    public String getSalesAccessChannelId() {
        return salesAccessChannelId;
    }
 
    public void setSalesAccessChannelId(String salesAccessChannelId) {
        this.salesAccessChannelId = salesAccessChannelId;
    }
 
    public String getSalesAccessChannel() {
        return salesAccessChannel;
    }
 
    public void setSalesAccessChannel(String salesAccessChannel) {
        this.salesAccessChannel = salesAccessChannel;
    }
 
    public String getCoreProductId() {
        return coreProductId;
    }
 
    public void setCoreProductId(String coreProductId) {
        this.coreProductId = coreProductId;
    }
 
    public String getTcsAndCsUrl() {
        return tcsAndCsUrl;
    }
 
    public void setTcsAndCsUrl(String tcsAndCsUrl) {
        this.tcsAndCsUrl = tcsAndCsUrl;
    }
 
    public String getMonthlyCharge() {
        return monthlyCharge;
    }
 
    public void setMonthlyCharge(String monthlyCharge) {
        this.monthlyCharge = monthlyCharge;
    }
 
    public String getCurrencyId() {
        return currencyId;
    }
 
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }
 
    public String getCurrencyCode() {
        return currencyCode;
    }
 
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
 
    public String getProductDescription() {
        return productDescription;
    }
 
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
 
    public String getProductUrl() {
        return productUrl;
    }
 
    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
 
    public String getBrandId() {
        return brandId;
    }
 
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }
 
    public String getProductCategoryId() {
        return productCategoryId;
    }
 
    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
 
    public String getProductCategory() {
        return productCategory;
    }
 
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
 
    public String getOnSaleIndicator() {
        return onSaleIndicator;
    }
 
    public void setOnSaleIndicator(String onSaleIndicator) {
        this.onSaleIndicator = onSaleIndicator;
    }
 
    public String getTierBandSetId() {
        return tierBandSetId;
    }
 
    public void setTierBandSetId(String tierBandSetId) {
        this.tierBandSetId = tierBandSetId;
    }
 
    public String getCreditInterestEligibilityId() {
        return creditInterestEligibilityId;
    }
 
    public void setCreditInterestEligibilityId(String creditInterestEligibilityId) {
        this.creditInterestEligibilityId = creditInterestEligibilityId;
    }
 
    public String getTypeName() {
        return typeName;
    }
 
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
 
    public String getTypeId() {
        return typeId;
    }
 
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
 
    public String getAmount() {
        return amount;
    }
 
    public void setAmount(String amount) {
        this.amount = amount;
    }
 
    public String getPeriodId() {
        return periodId;
    }
 
    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }
 
    public String getPeriod() {
        return period;
    }
 
    public void setPeriod(String period) {
        this.period = period;
    }
 
    public String getIndicator() {
        return indicator;
    }
 
    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }
 
    public String getTariffTypeId() {
        return tariffTypeId;
    }
 
    public void setTariffTypeId(String tariffTypeId) {
        this.tariffTypeId = tariffTypeId;
    }
 
    public String getTariffType() {
        return tariffType;
    }
 
    public void setTariffType(String tariffType) {
        this.tariffType = tariffType;
    }
 
    public String getFeeCategoryTypeId() {
        return feeCategoryTypeId;
    }
 
    public void setFeeCategoryTypeId(String feeCategoryTypeId) {
        this.feeCategoryTypeId = feeCategoryTypeId;
    }
 
    public String getFeeCategoryType() {
        return feeCategoryType;
    }
 
    public void setFeeCategoryType(String feeCategoryType) {
        this.feeCategoryType = feeCategoryType;
    }
 
    public String getFeeType() {
        return feeType;
    }
 
    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }
 
    public String getFeeTypeId() {
        return feeTypeId;
    }
 
    public void setFeeTypeId(String feeTypeId) {
        this.feeTypeId = feeTypeId;
    }
 
    public String getFeeRateType() {
        return feeRateType;
    }
 
    public void setFeeRateType(String feeRateType) {
        this.feeRateType = feeRateType;
    }
 
    public String getFeeRateTypeId() {
        return feeRateTypeId;
    }
 
    public void setFeeRateTypeId(String feeRateTypeId) {
        this.feeRateTypeId = feeRateTypeId;
    }
 
    public String getFeeRate() {
        return feeRate;
    }
 
    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }
 
    public String getFeeAmount() {
        return feeAmount;
    }
 
    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }
 
    public String getNegotiableIndicator() {
        return negotiableIndicator;
    }
 
    public void setNegotiableIndicator(String negotiableIndicator) {
        this.negotiableIndicator = negotiableIndicator;
    }
 
    public String getMinimumAmount() {
        return minimumAmount;
    }
 
    public void setMinimumAmount(String minimumAmount) {
        this.minimumAmount = minimumAmount;
    }
 
    public String getMaximumAmount() {
        return maximumAmount;
    }
 
    public void setMaximumAmount(String maximumAmount) {
        this.maximumAmount = maximumAmount;
    }
 
    public String getMinimumRate() {
        return minimumRate;
    }
 
    public void setMinimumRate(String minimumRate) {
        this.minimumRate = minimumRate;
    }
 
    public String getMaximumRate() {
        return maximumRate;
    }
 
    public void setMaximumRate(String maximumRate) {
        this.maximumRate = maximumRate;
    }
 
    public String getAer() {
        return aer;
    }
 
    public void setAer(String aer) {
        this.aer = aer;
    }
 
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
    public String getCreatedUser() {
        return createdUser;
    }
 
    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }
 
    public String getModifiedUser() {
        return modifiedUser;
    }
 
    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
    public String getReferenceCode() {
        return referenceCode;
    }
 
    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }
 
    public String getTierBandMethodId() {
        return tierBandMethodId;
    }
 
    public void setTierBandMethodId(String tierBandMethodId) {
        this.tierBandMethodId = tierBandMethodId;
    }
 
    public String getTierBandMethod() {
        return tierBandMethod;
    }
 
    public void setTierBandMethod(String tierBandMethod) {
        this.tierBandMethod = tierBandMethod;
    }
 
    public String getCalculationMethodId() {
        return calculationMethodId;
    }
 
    public void setCalculationMethodId(String calculationMethodId) {
        this.calculationMethodId = calculationMethodId;
    }
 
    public String getCalculationMethod() {
        return calculationMethod;
    }
 
    public void setCalculationMethod(String calculationMethod) {
        this.calculationMethod = calculationMethod;
    }
 
    public String getDestinationId() {
        return destinationId;
    }
 
    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }
 
    public String getDestination() {
        return destination;
    }
 
    public void setDestination(String destination) {
        this.destination = destination;
    }
 
    public String getFeeCategoryId() {
        return feeCategoryId;
    }
 
    public void setFeeCategoryId(String feeCategoryId) {
        this.feeCategoryId = feeCategoryId;
    }
 
    public String getFeeCategory() {
        return feeCategory;
    }
 
    public void setFeeCategory(String feeCategory) {
        this.feeCategory = feeCategory;
    }
 
    public String getMaxAllowedNoOfWithdrawals() {
        return maxAllowedNoOfWithdrawals;
    }
 
    public void setMaxAllowedNoOfWithdrawals(String maxAllowedNoOfWithdrawals) {
        this.maxAllowedNoOfWithdrawals = maxAllowedNoOfWithdrawals;
    }
 
    public String getMaxAllowedLimitForWithdrawals() {
        return maxAllowedLimitForWithdrawals;
    }
 
    public void setMaxAllowedLimitForWithdrawals(String maxAllowedLimitForWithdrawals) {
        this.maxAllowedLimitForWithdrawals = maxAllowedLimitForWithdrawals;
    }
 
    public String getMaxAllowedNoOfWithdrawalsPeriodId() {
        return maxAllowedNoOfWithdrawalsPeriodId;
    }
 
    public void setMaxAllowedNoOfWithdrawalsPeriodId(String maxAllowedNoOfWithdrawalsPeriodId) {
        this.maxAllowedNoOfWithdrawalsPeriodId = maxAllowedNoOfWithdrawalsPeriodId;
    }
 
    public String getMaxAllowedLimitForWithdrawalsPeriodId() {
        return maxAllowedLimitForWithdrawalsPeriodId;
    }
 
    public void setMaxAllowedLimitForWithdrawalsPeriodId(String maxAllowedLimitForWithdrawalsPeriodId) {
        this.maxAllowedLimitForWithdrawalsPeriodId = maxAllowedLimitForWithdrawalsPeriodId;
    }
 
    public String getMaxAllowedNoOfWithdrawalsPeriod() {
        return maxAllowedNoOfWithdrawalsPeriod;
    }
 
    public void setMaxAllowedNoOfWithdrawalsPeriod(String maxAllowedNoOfWithdrawalsPeriod) {
        this.maxAllowedNoOfWithdrawalsPeriod = maxAllowedNoOfWithdrawalsPeriod;
    }
 
    public String getMaxAllowedLimitForWithdrawalsPeriod() {
        return maxAllowedLimitForWithdrawalsPeriod;
    }
 
    public void setMaxAllowedLimitForWithdrawalsPeriod(String maxAllowedLimitForWithdrawalsPeriod) {
        this.maxAllowedLimitForWithdrawalsPeriod = maxAllowedLimitForWithdrawalsPeriod;
    }
 
    public String getMinBalanceForInterest() {
        return minBalanceForInterest;
    }
 
    public void setMinBalanceForInterest(String minBalanceForInterest) {
        this.minBalanceForInterest = minBalanceForInterest;
    }
 
    public String getIdentification() {
        return identification;
    }
 
    public void setIdentification(String identification) {
        this.identification = identification;
    }
 
    public String getEffectiveDate() {
        return effectiveDate;
    }
 
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
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
 
    public String getCalculationFrequencyId() {
        return calculationFrequencyId;
    }
 
    public void setCalculationFrequencyId(String calculationFrequencyId) {
        this.calculationFrequencyId = calculationFrequencyId;
    }
 
    public String getCalculationFrequency() {
        return calculationFrequency;
    }
 
    public void setCalculationFrequency(String calculationFrequency) {
        this.calculationFrequency = calculationFrequency;
    }
 
    public String getApplicationFrequencyId() {
        return applicationFrequencyId;
    }
 
    public void setApplicationFrequencyId(String applicationFrequencyId) {
        this.applicationFrequencyId = applicationFrequencyId;
    }
 
    public String getApplicationFrequency() {
        return applicationFrequency;
    }
 
    public void setApplicationFrequency(String applicationFrequency) {
        this.applicationFrequency = applicationFrequency;
    }
    public String getDepositInterestAppliedCoverage() {
        return depositInterestAppliedCoverage;
    }
 
    public void setDepositInterestAppliedCoverage(String depositInterestAppliedCoverage) {
        this.depositInterestAppliedCoverage = depositInterestAppliedCoverage;
    }
 
    public String getBankInterestRateTypeId() {
        return bankInterestRateTypeId;
    }
 
    public void setBankInterestRateTypeId(String bankInterestRateTypeId) {
        this.bankInterestRateTypeId = bankInterestRateTypeId;
    }
 
    public String getBankInterestRateType() {
        return bankInterestRateType;
    }
 
    public void setBankInterestRateType(String bankInterestRateType) {
        this.bankInterestRateType = bankInterestRateType;
    }
 
    public String getBankInterestRate() {
        return bankInterestRate;
    }
 
    public void setBankInterestRate(String bankInterestRate) {
        this.bankInterestRate = bankInterestRate;
    }
 
    public String getDepositInterestApplicableBase() {
        return depositInterestApplicableBase;
    }
 
    public void setDepositInterestApplicableBase(String depositInterestApplicableBase) {
        this.depositInterestApplicableBase = depositInterestApplicableBase;
    }
 
    public String getBankInterestCalBasisId() {
        return bankInterestCalBasisId;
    }
 
    public void setBankInterestCalBasisId(String bankInterestCalBasisId) {
        this.bankInterestCalBasisId = bankInterestCalBasisId;
    }
 
    public String getBankInterestCalBasis() {
        return bankInterestCalBasis;
    }
 
    public void setBankInterestCalBasis(String bankInterestCalBasis) {
        this.bankInterestCalBasis = bankInterestCalBasis;
    }
 
    public String getDepositInterestAppliedCoverageId() {
        return depositInterestAppliedCoverageId;
    }
 
    public void setDepositInterestAppliedCoverageId(String depositInterestAppliedCoverageId) {
        this.depositInterestAppliedCoverageId = depositInterestAppliedCoverageId;
    }
 
    public String getDepositInterestApplicableBaseId() {
        return depositInterestApplicableBaseId;
    }
 
    public void setDepositInterestApplicableBaseId(String depositInterestApplicableBaseId) {
        this.depositInterestApplicableBaseId = depositInterestApplicableBaseId;
    }
 
    public String getFixedVariableInterestRateTypeId() {
        return fixedVariableInterestRateTypeId;
    }
 
    public void setFixedVariableInterestRateTypeId(String fixedVariableInterestRateTypeId) {
        this.fixedVariableInterestRateTypeId = fixedVariableInterestRateTypeId;
    }
 
    public String getFixedVariableInterestRateType() {
        return fixedVariableInterestRateType;
    }
 
    public void setFixedVariableInterestRateType(String fixedVariableInterestRateType) {
        this.fixedVariableInterestRateType = fixedVariableInterestRateType;
    }
 
    public String getVersion() {
        return version;
    }
 
    public void setVersion(String version) {
        this.version = version;
    }
 
    public String getSegmentId() {
        return segmentId;
    }
 
    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }
 
    public String getSegment() {
        return segment;
    }
 
    public void setSegment(String segment) {
        this.segment = segment;
    }
 
    public String getMinimumBalance() {
        return minimumBalance;
    }
 
    public void setMinimumBalance(String minimumBalance) {
        this.minimumBalance = minimumBalance;
    }
 
    public String getMinimumAmountforAccountOpening() {
        return minimumAmountforAccountOpening;
    }
 
    public void setMinimumAmountforAccountOpening(String minimumAmountforAccountOpening) {
        this.minimumAmountforAccountOpening = minimumAmountforAccountOpening;
    }
 
    public String getWithdrawalsAllowedAfter() {
        return withdrawalsAllowedAfter;
    }
 
    public void setWithdrawalsAllowedAfter(String withdrawalsAllowedAfter) {
        this.withdrawalsAllowedAfter = withdrawalsAllowedAfter;
    }
 
    public String getWithdrawalsAllowedAfterPeriodId() {
        return withdrawalsAllowedAfterPeriodId;
    }
 
    public void setWithdrawalsAllowedAfterPeriodId(String withdrawalsAllowedAfterPeriodId) {
        this.withdrawalsAllowedAfterPeriodId = withdrawalsAllowedAfterPeriodId;
    }
 
    public String getBorrowerLimit() {
        return borrowerLimit;
    }
 
    public void setBorrowerLimit(String borrowerLimit) {
        this.borrowerLimit = borrowerLimit;
    }
 
    public String getDormantPeriod() {
        return dormantPeriod;
    }
 
    public void setDormantPeriod(String dormantPeriod) {
        this.dormantPeriod = dormantPeriod;
    }
 
    public String getDormantPeriodTypeId() {
        return dormantPeriodTypeId;
    }
 
    public void setDormantPeriodTypeId(String dormantPeriodTypeId) {
        this.dormantPeriodTypeId = dormantPeriodTypeId;
    }
 
    public String getDormantPeriodType() {
        return dormantPeriodType;
    }
 
    public void setDormantPeriodType(String dormantPeriodType) {
        this.dormantPeriodType = dormantPeriodType;
    }
 
    public String getFeeFreeLength() {
        return feeFreeLength;
    }
 
    public void setFeeFreeLength(String feeFreeLength) {
        this.feeFreeLength = feeFreeLength;
    }
 
    public String getFeeFreeLengthPeriodId() {
        return feeFreeLengthPeriodId;
    }
 
    public void setFeeFreeLengthPeriodId(String feeFreeLengthPeriodId) {
        this.feeFreeLengthPeriodId = feeFreeLengthPeriodId;
    }
 
    public String getFeeFreeLengthPeriod() {
        return feeFreeLengthPeriod;
    }
 
    public void setFeeFreeLengthPeriod(String feeFreeLengthPeriod) {
        this.feeFreeLengthPeriod = feeFreeLengthPeriod;
    }
 
    public String getWithdrawalsAllowedAfterPeriod() {
        return withdrawalsAllowedAfterPeriod;
    }
 
    public void setWithdrawalsAllowedAfterPeriod(String withdrawalsAllowedAfterPeriod) {
        this.withdrawalsAllowedAfterPeriod = withdrawalsAllowedAfterPeriod;
    }
 
    public String getAttribute1() {
        return attribute1;
    }
 
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }
 
    public String getAttribute2() {
        return attribute2;
    }
 
    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }
 
    public String getAttribute3() {
        return attribute3;
    }
 
    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }
 
    public String getAttribute4() {
        return attribute4;
    }
 
    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }
 
    public String getAttribute5() {
        return attribute5;
    }
 
    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }
 
    public String getNotes() {
        return notes;
    }
 
    public void setNotes(String notes) {
        this.notes = notes;
    }
 
    public String getMinimumAge() {
        return minimumAge;
    }
 
    public void setMinimumAge(String minimumAge) {
        this.minimumAge = minimumAge;
    }
 
    public String getMaximumAge() {
        return maximumAge;
    }
 
    public void setMaximumAge(String maximumAge) {
        this.maximumAge = maximumAge;
    }
 
    public String getUrl() {
        return url;
    }
 
    public void setUrl(String url) {
        this.url = url;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public String getScoringType() {
        return scoringType;
    }
 
    public void setScoringType(String scoringType) {
        this.scoringType = scoringType;
    }
 
    public String getType() {
        return type;
    }
 
    public void setType(String type) {
        this.type = type;
    }
 
    public String getUnit() {
        return unit;
    }
 
    public void setUnit(String unit) {
        this.unit = unit;
    }
 
    public String getAgeEligibilityId() {
        return ageEligibilityId;
    }
 
    public void setAgeEligibilityId(String ageEligibilityId) {
        this.ageEligibilityId = ageEligibilityId;
    }
 
    public String getIdEligibilityId() {
        return idEligibilityId;
    }
 
    public void setIdEligibilityId(String idEligibilityId) {
        this.idEligibilityId = idEligibilityId;
    }
 
    public String getCreditCheckEligibilityId() {
        return creditCheckEligibilityId;
    }
 
    public void setCreditCheckEligibilityId(String creditCheckEligibilityId) {
        this.creditCheckEligibilityId = creditCheckEligibilityId;
    }
 
    public String getIndustryEligibilityId() {
        return industryEligibilityId;
    }
 
    public void setIndustryEligibilityId(String industryEligibilityId) {
        this.industryEligibilityId = industryEligibilityId;
    }
 
    public String getOtherEligibilityTypeId() {
        return otherEligibilityTypeId;
    }
 
    public void setOtherEligibilityTypeId(String otherEligibilityTypeId) {
        this.otherEligibilityTypeId = otherEligibilityTypeId;
    }
 
    public String getTextual() {
        return textual;
    }
 
    public void setTextual(String textual) {
        this.textual = textual;
    }
 
    public String getEligibilityId() {
        return eligibilityId;
    }
 
    public void setEligibilityId(String eligibilityId) {
        this.eligibilityId = eligibilityId;
    }
 
    public String getOtherEligibilityId() {
        return otherEligibilityId;
    }
 
    public void setOtherEligibilityId(String otherEligibilityId) {
        this.otherEligibilityId = otherEligibilityId;
    }
 
    public String getResidencyTypeId() {
        return residencyTypeId;
    }
 
    public void setResidencyTypeId(String residencyTypeId) {
        this.residencyTypeId = residencyTypeId;
    }
 
    public String getResidencyEligibilityId() {
        return residencyEligibilityId;
    }
 
    public void setResidencyEligibilityId(String residencyEligibilityId) {
        this.residencyEligibilityId = residencyEligibilityId;
    }
 
    public String getResidencyIncludeCountryId() {
        return residencyIncludeCountryId;
    }
 
    public void setResidencyIncludeCountryId(String residencyIncludeCountryId) {
        this.residencyIncludeCountryId = residencyIncludeCountryId;
    }
 
    public String getCountryName() {
        return countryName;
    }
 
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
 
    public String getPredecessorId() {
        return predecessorId;
    }
 
    public void setPredecessorId(String predecessorId) {
        this.predecessorId = predecessorId;
    }
 
    public String getOtherSciCode() {
        return otherSciCode;
    }
 
    public void setOtherSciCode(String otherSciCode) {
        this.otherSciCode = otherSciCode;
    }
 
    public String getLegalStructureId() {
        return legalStructureId;
    }
 
    public void setLegalStructureId(String legalStructureId) {
        this.legalStructureId = legalStructureId;
    }
 
    public String getLegalStructureEligibilityId() {
        return legalStructureEligibilityId;
    }
 
    public void setLegalStructureEligibilityId(String legalStructureEligibilityId) {
        this.legalStructureEligibilityId = legalStructureEligibilityId;
    }
 
    public String getOfficerTypeDesignationId() {
        return officerTypeDesignationId;
    }
 
    public void setOfficerTypeDesignationId(String officerTypeDesignationId) {
        this.officerTypeDesignationId = officerTypeDesignationId;
    }
 
    public String getDesignationName() {
        return designationName;
    }
 
    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }
 
    public String getMinAmount() {
        return minAmount;
    }
 
    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }
 
    public String getMaxAmount() {
        return maxAmount;
    }
 
    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }
 
    public String getOfficerEligibilityId() {
        return officerEligibilityId;
    }
 
    public void setOfficerEligibilityId(String officerEligibilityId) {
        this.officerEligibilityId = officerEligibilityId;
    }
 
    public String getTrdingTypeId() {
        return trdingTypeId;
    }
 
    public void setTrdingTypeId(String trdingTypeId) {
        this.trdingTypeId = trdingTypeId;
    }
 
    public String getMinMaxType() {
        return minMaxType;
    }
 
    public void setMinMaxType(String minMaxType) {
        this.minMaxType = minMaxType;
    }
 
    public String getTradingEligibilityId() {
        return tradingEligibilityId;
    }
 
    public void setTradingEligibilityId(String tradingEligibilityId) {
        this.tradingEligibilityId = tradingEligibilityId;
    }
 
    public String getPeriodName() {
        return periodName;
    }
 
    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }
 
    public String getLegalStructureName() {
        return legalStructureName;
    }
 
    public void setLegalStructureName(String legalStructureName) {
        this.legalStructureName = legalStructureName;
    }
 
    public String getResidencyTypeName() {
        return residencyTypeName;
    }
 
    public void setResidencyTypeName(String residencyTypeName) {
        this.residencyTypeName = residencyTypeName;
    }
 
    public String getOverdraftTypeId() {
        return overdraftTypeId;
    }
 
    public void setOverdraftTypeId(String overdraftTypeId) {
        this.overdraftTypeId = overdraftTypeId;
    }
 
    public String getOverdraftType() {
        return overdraftType;
    }
 
    public void setOverdraftType(String overdraftType) {
        this.overdraftType = overdraftType;
    }
 
    public String getOverdraftTierBandId() {
        return overdraftTierBandId;
    }
 
    public void setOverdraftTierBandId(String overdraftTierBandId) {
        this.overdraftTierBandId = overdraftTierBandId;
    }
 
    public String getOverdraftControlIndicator() {
        return overdraftControlIndicator;
    }
 
    public void setOverdraftControlIndicator(String overdraftControlIndicator) {
        this.overdraftControlIndicator = overdraftControlIndicator;
    }
 
    public String getBonusInterestEligibilityId() {
        return bonusInterestEligibilityId;
    }
 
    public void setBonusInterestEligibilityId(String bonusInterestEligibilityId) {
        this.bonusInterestEligibilityId = bonusInterestEligibilityId;
    }
 
    public String getBonusTierBandSetId() {
        return bonusTierBandSetId;
    }
 
    public void setBonusTierBandSetId(String bonusTierBandSetId) {
        this.bonusTierBandSetId = bonusTierBandSetId;
    }
 
    public String getBonusInterestId() {
        return bonusInterestId;
    }
 
    public void setBonusInterestId(String bonusInterestId) {
        this.bonusInterestId = bonusInterestId;
    }
 
    public String getFrquencyTypeId() {
        return frquencyTypeId;
    }
 
    public void setFrquencyTypeId(String frquencyTypeId) {
        this.frquencyTypeId = frquencyTypeId;
    }
 
    public String getAuthorisedIndicator() {
        return authorisedIndicator;
    }
 
    public void setAuthorisedIndicator(String authorisedIndicator) {
        this.authorisedIndicator = authorisedIndicator;
    }
 
    public String getRateType() {
        return rateType;
    }
 
    public void setRateType(String rateType) {
        this.rateType = rateType;
    }
 
    public String getRate() {
        return rate;
    }
 
    public void setRate(String rate) {
        this.rate = rate;
    }
 
    public String getTexual() {
        return texual;
    }
 
    public void setTexual(String texual) {
        this.texual = texual;
    }
 
    public String getOverdraftbalanceamount() {
        return overdraftbalanceamount;
    }
 
    public void setOverdraftbalanceamount(String overdraftbalanceamount) {
        this.overdraftbalanceamount = overdraftbalanceamount;
    }

	public String getChargeRecoveryMethod() {
		return chargeRecoveryMethod;
	}

	public void setChargeRecoveryMethod(String chargeRecoveryMethod) {
		this.chargeRecoveryMethod = chargeRecoveryMethod;
	}

	public String getPenalTierBandSetId() {
		return penalTierBandSetId;
	}

	public void setPenalTierBandSetId(String penalTierBandSetId) {
		this.penalTierBandSetId = penalTierBandSetId;
	}

	public String getPenalInterestId() {
		return penalInterestId;
	}

	public void setPenalInterestId(String penalInterestId) {
		this.penalInterestId = penalInterestId;
	}

	public String getPrdCalStartPoint() {
		return prdCalStartPoint;
	}

	public void setPrdCalStartPoint(String prdCalStartPoint) {
		this.prdCalStartPoint = prdCalStartPoint;
	}

	public String getPnlTbsName() {
		return pnlTbsName;
	}

	public void setPnlTbsName(String pnlTbsName) {
		this.pnlTbsName = pnlTbsName;
	}

	public String getGracePeriodLength() {
		return gracePeriodLength;
	}

	public void setGracePeriodLength(String gracePeriodLength) {
		this.gracePeriodLength = gracePeriodLength;
	}

	public String getPenalTierBandOverLap() {
		return penalTierBandOverLap;
	}

	public void setPenalTierBandOverLap(String penalTierBandOverLap) {
		this.penalTierBandOverLap = penalTierBandOverLap;
	}

	public String getPenalInterestEligibilityId() {
		return penalInterestEligibilityId;
	}

	public void setPenalInterestEligibilityId(String penalInterestEligibilityId) {
		this.penalInterestEligibilityId = penalInterestEligibilityId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentDes() {
		return departmentDes;
	}

	public void setDepartmentDes(String departmentDes) {
		this.departmentDes = departmentDes;
	}
	
	
    
 }