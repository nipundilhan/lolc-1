package com.fusionx.lending.product.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Tax Profile Service
 * @author 	KilasiG
 * @Date     05-11-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-11-2019   FX-1545       FX-2175    KilasiG      Created
 *    
 ********************************************************************************************************
 */

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.TaxCodeResponse;
import com.fusionx.lending.product.domain.TaxProfile;
import com.fusionx.lending.product.domain.TaxProfileDetails;
import com.fusionx.lending.product.enums.ApplicableAccTypeComnListEnum;
import com.fusionx.lending.product.enums.ApplicableProductStatus;
import com.fusionx.lending.product.enums.BaredBy;
import com.fusionx.lending.product.enums.OtherInterestIncome;
import com.fusionx.lending.product.enums.TaxAmountType;
import com.fusionx.lending.product.enums.TaxApplicableLevel;
import com.fusionx.lending.product.enums.TaxCodeStatus;
import com.fusionx.lending.product.enums.TaxProfileStatus;
import com.fusionx.lending.product.exception.NoRecordFoundException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.TaxProfileDetailsRepository;
import com.fusionx.lending.product.repository.TaxProfileRepository;
import com.fusionx.lending.product.resources.AddTaxProfileRequestResource;
import com.fusionx.lending.product.resources.TaxProfileDetailsRequestResource;
import com.fusionx.lending.product.resources.UpdateTaxProfileRequestResource;
import com.fusionx.lending.product.service.TaxProfileService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class TaxProfileServiceImpl implements TaxProfileService {

	@Autowired
	Environment environment;

	@Autowired
	private TaxProfileRepository taxProfileRepository;

	@Autowired
	private TaxProfileDetailsRepository taxProfileDetailsRepository;

	@Autowired
	private ValidationService validationService;

	@Value("${comn-common-individual-type}")
	protected String personServiceUrl;

	@Value("${legal-structure}")
	protected String commonServiceUrl;

	@Override
	public Page<TaxProfile> findAll(Pageable pageable) {
		return taxProfileRepository.findAll(pageable);
	}

	@Override
	public Optional<TaxProfile> findById(Long taxProfileId) {
		Optional<TaxProfile> taxProfiles = taxProfileRepository.findById(taxProfileId);
		if (taxProfiles.isPresent())
			return Optional.ofNullable(taxProfiles.get());
		else
			return Optional.empty();
	}

	@Override
	public Optional<Collection<TaxProfile>> findByStatus(String status) {
		Optional<Collection<TaxProfile>> taxProfiles = taxProfileRepository
				.findByTaxProfileStatus(TaxProfileStatus.valueOf(status));
		if (taxProfiles.isPresent())
			return Optional.ofNullable(taxProfiles.get());
		else
			return Optional.empty();
	}

	@Override
	public TaxProfile saveTaxProfile(String tenantId, AddTaxProfileRequestResource addTaxProfileRequestResource) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		// Added By Senitha 01-MAY-2020
		Optional<TaxProfile> isRecordPresent = taxProfileRepository
				.findByTaxCodeIdAndTaxApplicableLevelAndProductCategoryComnListIdAndProductCategoryDescAndApplicableAccTypeComnListIdAndApplicableProductStatusAndApplicableProductIdAndDeclarationTypeComnListIdAndCustomerCategoryIdAndCustomerSubTypeIndividualIdAndCustomerSubTypeNonIndividualIdAndCustomerResidentTypeIdAndTaxApplicableMinAgeAndTaxApplicableMaxAgeAndAgeEffectiveDateTypeAndTaxAmountTypeAndTaxApplicableMinValueAndTaxApplicableMaxValueAndCeilingFloorfMinValueAndCeilingFloorfMaxValueAndEffectiveDate(
						stringToLong(addTaxProfileRequestResource.getTaxCodeId()),
						TaxApplicableLevel.valueOf(addTaxProfileRequestResource.getTaxApplicableLevel()),
						stringToLong(addTaxProfileRequestResource.getProductCategoryComnListId()),
						addTaxProfileRequestResource.getProductCategoryDesc(),
						ApplicableAccTypeComnListEnum
								.valueOf(addTaxProfileRequestResource.getApplicableAccTypeComnListId()),
						ApplicableProductStatus.valueOf(addTaxProfileRequestResource.getApplicableProductStatus()),
						stringToLong(addTaxProfileRequestResource.getApplicableProductId()),
						addTaxProfileRequestResource.getDeclarationTypeComnListId(),
						addTaxProfileRequestResource.getCustomerCategoryId(),
						stringToLong(addTaxProfileRequestResource.getCustomerSubTypeIndividualId()),
						stringToLong(addTaxProfileRequestResource.getCustomerSubTypeNonIndividualId()),
						stringToLong(addTaxProfileRequestResource.getCustomerResidentTypeId()),
						addTaxProfileRequestResource.getTaxApplicableMinAge() != null
								&& !addTaxProfileRequestResource.getTaxApplicableMinAge().equals("")
										? stringToLong(addTaxProfileRequestResource.getTaxApplicableMinAge())
										: null,
						addTaxProfileRequestResource.getTaxApplicableMaxAge() != null
								&& !addTaxProfileRequestResource.getTaxApplicableMaxAge().equals("")
										? stringToLong(addTaxProfileRequestResource.getTaxApplicableMaxAge())
										: null,
						addTaxProfileRequestResource.getAgeEffectiveDateType(),
						TaxAmountType.valueOf(addTaxProfileRequestResource.getTaxAmountType()),
						addTaxProfileRequestResource.getTaxApplicableMinValue() != null
								&& !addTaxProfileRequestResource.getTaxApplicableMinValue().equals("")
										? stringToBigDecimal(addTaxProfileRequestResource.getTaxApplicableMinValue())
										: null,
						addTaxProfileRequestResource.getTaxApplicableMaxValue() != null
								&& !addTaxProfileRequestResource.getTaxApplicableMaxValue().equals("")
										? stringToBigDecimal(addTaxProfileRequestResource.getTaxApplicableMaxValue())
										: null,
						addTaxProfileRequestResource.getCeilingFloorfMinValue() != null
								&& !addTaxProfileRequestResource.getCeilingFloorfMinValue().equals("")
										? stringToBigDecimal(addTaxProfileRequestResource.getCeilingFloorfMinValue())
										: null,
						addTaxProfileRequestResource.getCeilingFloorfMaxValue() != null
								&& !addTaxProfileRequestResource.getCeilingFloorfMaxValue().equals("")
										? stringToBigDecimal(addTaxProfileRequestResource.getCeilingFloorfMaxValue())
										: null,
						formatDate(addTaxProfileRequestResource.getTaxEffectiveDate()));

		if (isRecordPresent.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.unique"), "message");
		}

		// Added By Senitha 03-APR-2020
		List<TaxProfile> isPresentTaxProfleCodeId = taxProfileRepository.findBytaxCodeIdAndTaxProfileStatus(
				stringToLong(addTaxProfileRequestResource.getTaxCodeId()),
				TaxProfileStatus.valueOf(addTaxProfileRequestResource.getTaxProfileStatus()));

		if (!isPresentTaxProfleCodeId.isEmpty()) {
			for (TaxProfile taxProfile : isPresentTaxProfleCodeId) {
				if (!taxProfile.getTaxApplicableLevel()
						.equals(TaxApplicableLevel.valueOf(addTaxProfileRequestResource.getTaxApplicableLevel()))) {
					throw new ValidateRecordException(environment.getProperty("taxApplicableLevel.duplicate.update"),
							"taxApplicableLevel");
				}
				break;
			}
		}

		if (!isPresentTaxProfleCodeId.isEmpty()) {
			for (TaxProfile taxProfile : isPresentTaxProfleCodeId) {
				if (!taxProfile.getTaxAmountType()
						.equals(TaxAmountType.valueOf(addTaxProfileRequestResource.getTaxAmountType()))) {
					throw new ValidateRecordException(environment.getProperty("taxAmountType.invalid.update"),
							"taxAmountType");
				}
				break;
			}
		}

		// Added by Senitha on 01-MAY-2020
		if (!isPresentTaxProfleCodeId.isEmpty()) {
			for (TaxProfile taxProfile : isPresentTaxProfleCodeId) {
				if (!taxProfile.getApplicableProductStatus().equals(
						ApplicableProductStatus.valueOf(addTaxProfileRequestResource.getApplicableProductStatus()))) {
					throw new ValidateRecordException(
							environment.getProperty("applicableProductStatus.duplicate.update"),
							"applicableProductStatus");
				}
				break;
			}
		}

		/*
		 * Optional<TaxProfile> isPresentTaxCodeId =
		 * taxProfileRepository.findBytaxCodeIdAndTaxApplicableLevel(stringToLong(
		 * addTaxProfileRequestResource.getTaxCodeId()),
		 * TaxApplicableLevel.valueOf(addTaxProfileRequestResource.getTaxApplicableLevel
		 * ()));
		 * 
		 * if(isPresentTaxCodeId.isPresent()) {
		 * if(TaxApplicableLevel.valueOf(addTaxProfileRequestResource.
		 * getTaxApplicableLevel()).CUSTOMER ||) }
		 */
		TaxCodeResponse taxCode = validationService.validateTaxCode(tenantId,
				addTaxProfileRequestResource.getTaxCodeId());

		validateRecord("ADD", tenantId, addTaxProfileRequestResource);

		TaxProfile taxProfile = new TaxProfile();
		taxProfile.setTaxProfileTenantId(tenantId);
		taxProfile.setEffectiveDate(formatDate(addTaxProfileRequestResource.getTaxEffectiveDate()));
		taxProfile.setTaxCode(taxCode.getId());
		taxProfile.setTaxApplicableLevel(
				TaxApplicableLevel.valueOf(addTaxProfileRequestResource.getTaxApplicableLevel()));
		taxProfile.setProductCategoryComnListId(
				stringToLong(addTaxProfileRequestResource.getProductCategoryComnListId()));
		taxProfile.setSubProductId(validationService.stringToLong(addTaxProfileRequestResource.getSubProductId()));
		taxProfile.setApplicableAccTypeComnListId(
				ApplicableAccTypeComnListEnum.valueOf(addTaxProfileRequestResource.getApplicableAccTypeComnListId()));
		taxProfile.setApplicableAccTypeDesc(addTaxProfileRequestResource.getApplicableAccTypeDesc());
		taxProfile.setApplicableProductStatus(
				ApplicableProductStatus.valueOf(addTaxProfileRequestResource.getApplicableProductStatus()));
		taxProfile.setApplicableProductId(stringToLong(addTaxProfileRequestResource.getApplicableProductId()));
		taxProfile.setApplicableProductName(addTaxProfileRequestResource.getApplicableProductName());
		taxProfile.setDeclarationTypeComnListId(addTaxProfileRequestResource.getDeclarationTypeComnListId());
		/*
		 * if(!addTaxProfileRequestResource.getDeclarationTypeComnListId().equals("")) {
		 * taxProfile.setDeclarationTypeComnListId(DeclarationTypeComnListEnum.valueOf(
		 * addTaxProfileRequestResource.getDeclarationTypeComnListId())); }
		 */
//		taxProfile.setDeclarationTypeDesc(addTaxProfileRequestResource.getDeclarationTypeDesc());
		if (!addTaxProfileRequestResource.getTaxApplicableLevel().equals("GLOBAL")) {
			taxProfile.setCustomerCategory(addTaxProfileRequestResource.getCustomerCategory());
			taxProfile.setCustomerCategoryId(addTaxProfileRequestResource.getCustomerCategoryId());
			taxProfile.setCustomerCategoryCode(addTaxProfileRequestResource.getCustomerCategoryCode());

			if (addTaxProfileRequestResource.getCustomerCategory().equals("Individual")) {
				if ((addTaxProfileRequestResource.getCustomerSubTypeIndividualId() != null
						&& !addTaxProfileRequestResource.getCustomerSubTypeIndividualId().equals(""))
						&& (addTaxProfileRequestResource.getCustomerSubTypeIndividualDesc() != null
								&& !addTaxProfileRequestResource.getCustomerSubTypeIndividualDesc().equals(""))) {

					try {
						JSONObject personTypeObj = invokeService(tenantId, personServiceUrl + tenantId + "/"
								+ addTaxProfileRequestResource.getCustomerSubTypeIndividualId());

						if (personTypeObj != null && !personTypeObj.isNull("personTypeDescription")
								&& (personTypeObj.getString("personTypeDescription").equalsIgnoreCase(
										addTaxProfileRequestResource.getCustomerSubTypeIndividualDesc()))) {
							taxProfile.setCustomerSubTypeIndividualId(
									stringToLong(addTaxProfileRequestResource.getCustomerSubTypeIndividualId()));
							taxProfile
									.setCustomerSubTypeIndividualDesc(personTypeObj.getString("personTypeDescription"));
						} else {
							throw new ValidateRecordException(
									environment.getProperty("invalid-customersubtypeindividual"),
									"customerSubTypeIndividualId");
						}
					} catch (IOException | JSONException e) {
						throw new ValidateRecordException(environment.getProperty("invalid-customersubtypeindividual"),
								"customerSubTypeIndividualId");
					}
				} else {
					throw new ValidateRecordException(environment.getProperty("invalid-customersubtypeindividual"),
							"customerSubTypeIndividualId");
				}

			} else if (addTaxProfileRequestResource.getCustomerCategory().equals("Non Individual")) {
				if ((addTaxProfileRequestResource.getCustomerSubTypeNonIndividualId() != null
						&& !addTaxProfileRequestResource.getCustomerSubTypeNonIndividualId().equals(""))
						&& (addTaxProfileRequestResource.getCustomerSubTypeNonIndividualDesc() != null
								&& !addTaxProfileRequestResource.getCustomerSubTypeNonIndividualDesc().equals(""))) {

					try {
						JSONObject personTypeObj = invokeService(tenantId, commonServiceUrl + tenantId + "/"
								+ addTaxProfileRequestResource.getCustomerSubTypeNonIndividualId());

						if (personTypeObj != null && !personTypeObj.isNull("description")
								&& (personTypeObj.getString("description").equalsIgnoreCase(
										addTaxProfileRequestResource.getCustomerSubTypeNonIndividualDesc()))) {

							taxProfile.setCustomerSubTypeNonIndividualId(
									stringToLong(addTaxProfileRequestResource.getCustomerSubTypeNonIndividualId()));
							taxProfile.setCustomerSubTypeNonIndividualDesc(personTypeObj.getString("description"));
						} else {
							throw new ValidateRecordException(
									environment.getProperty("invalid-customersubtypenonindividual"),
									"customerSubTypeNonIndividualId");
						}
					} catch (IOException | JSONException e) {
						throw new ValidateRecordException(
								environment.getProperty("invalid-customersubtypenonindividual"),
								"customerSubTypeNonIndividualId");
					}
				} else {
					throw new ValidateRecordException(environment.getProperty("invalid-customersubtypenonindividual"),
							"customerSubTypeNonIndividualId");
				}
			}

			taxProfile
					.setCustomerResidentTypeId(stringToLong(addTaxProfileRequestResource.getCustomerResidentTypeId()));
			taxProfile.setCustomerResidentTypeDesc(addTaxProfileRequestResource.getCustomerResidentTypeDesc());
			if (addTaxProfileRequestResource.getTaxApplicableMinAge() != "")
				taxProfile.setTaxApplicableMinAge(stringToLong(addTaxProfileRequestResource.getTaxApplicableMinAge()));
			if (addTaxProfileRequestResource.getTaxApplicableMaxAge() != "")
				taxProfile.setTaxApplicableMaxAge(stringToLong(addTaxProfileRequestResource.getTaxApplicableMaxAge()));
			if (addTaxProfileRequestResource.getAgeEffectiveDateType() != null
					&& !addTaxProfileRequestResource.getAgeEffectiveDateType().isEmpty()) {
				taxProfile.setAgeEffectiveDateType(addTaxProfileRequestResource.getAgeEffectiveDateType());
			}
		}
		taxProfile.setTaxAmountType(TaxAmountType.valueOf(addTaxProfileRequestResource.getTaxAmountType()));
//		if (addTaxProfileRequestResource.getTaxAmountType().equals(TaxAmountType.AMOUNT.toString())
//				|| addTaxProfileRequestResource.getTaxAmountType().equals(TaxAmountType.AMOUNTWITHTIER.toString())) {
//			taxProfile.setTaxAmount(stringToBigDecimal(addTaxProfileRequestResource.getTaxAmount()));
//		} else if (addTaxProfileRequestResource.getTaxAmountType().equals(TaxAmountType.RATE.toString())
//				|| addTaxProfileRequestResource.getTaxAmountType().equals(TaxAmountType.RATEWITHTIER.toString())) {
//			taxProfile.setTaxRate(stringToBigDecimal(addTaxProfileRequestResource.getTaxRate()));
//		}
		if (addTaxProfileRequestResource.getTaxAmountType().equals(TaxAmountType.AMOUNTWITHTIER.toString())
				|| addTaxProfileRequestResource.getTaxAmountType().equals(TaxAmountType.RATEWITHTIER.toString())) {
			if (!addTaxProfileRequestResource.getTaxApplicableMinValue().equals(""))
				taxProfile.setTaxApplicableMinValue(
						stringToBigDecimal(addTaxProfileRequestResource.getTaxApplicableMinValue()));
			if (!addTaxProfileRequestResource.getTaxApplicableMaxValue().equals(""))
				taxProfile.setTaxApplicableMaxValue(
						stringToBigDecimal(addTaxProfileRequestResource.getTaxApplicableMaxValue()));
		}
		if (addTaxProfileRequestResource.getCeilingFloorfMinValue() != "")
			taxProfile.setCeilingFloorfMinValue(
					stringToBigDecimal(addTaxProfileRequestResource.getCeilingFloorfMinValue()));
		if (addTaxProfileRequestResource.getCeilingFloorfMaxValue() != "")
			taxProfile.setCeilingFloorfMaxValue(
					stringToBigDecimal(addTaxProfileRequestResource.getCeilingFloorfMaxValue()));
		taxProfile.setOtherInterestIncome(
				OtherInterestIncome.valueOf(addTaxProfileRequestResource.getOtherInterestIncome()));
		taxProfile.setTaxProfileStatus(TaxProfileStatus.valueOf(addTaxProfileRequestResource.getTaxProfileStatus()));

		/// Optional<Collection<TaxProfile>> taxProfiles =
		/// taxProfileRepository.findByTaxCodeIdAndTaxProfileStatus(stringToLong(addTaxProfileRequestResource.getTaxCodeId()),
		/// TaxProfileStatus.valueOf(addTaxProfileRequestResource.getTaxProfileStatus()));
		/*
		 * if (taxProfiles.isPresent()) { for (TaxProfile taxProfileRec :
		 * taxProfiles.get()) { if(taxProfileRec.equals(taxProfile)) { throw new
		 * UniqueRecordException(environment.getProperty("common.record-duplicate"),
		 * TaxType.TAXPROFILE); } } }
		 * 
		 * if (taxProfiles.isPresent()) { for (TaxProfile taxProfileRec :
		 * taxProfiles.get()) { if(taxProfileRec.equalsFunction(taxProfile)) {
		 * if(taxProfileRec.getTaxRate() != null &&
		 * taxProfileRec.getTaxRate().doubleValue() !=
		 * taxProfile.getTaxRate().doubleValue()) { throw new
		 * UniqueRecordException(environment.getProperty(
		 * "common.taxCode-duplicate-record"), TaxType.TAXPROFILE); }
		 * if(taxProfileRec.getTaxAmount() != null &&
		 * taxProfileRec.getTaxAmount().doubleValue() !=
		 * taxProfile.getTaxAmount().doubleValue()) { throw new
		 * UniqueRecordException(environment.getProperty(
		 * "common.taxCode-duplicate-record"), TaxType.TAXPROFILE); } } } }
		 */

//		if(addTaxProfileRequestResource.getDeductIndicator()!= "" || !addTaxProfileRequestResource.getDeductIndicator().isEmpty()) {
		taxProfile.setDeductIndicator(OtherInterestIncome.valueOf(addTaxProfileRequestResource.getDeductIndicator()));
//		}

//		if(addTaxProfileRequestResource.getBaredBy()!= "" || !addTaxProfileRequestResource.getBaredBy().isEmpty()) {
		taxProfile.setBaredBy(BaredBy.valueOf(addTaxProfileRequestResource.getBaredBy()));
//		}

		taxProfile.setCreatedUser(addTaxProfileRequestResource.getTaxProfileCreatedUser());
		taxProfile.setCreatedDate(currentTimestamp);
		taxProfile.setSyncTs(currentTimestamp);
		taxProfile = taxProfileRepository.saveAndFlush(taxProfile);

		for (TaxProfileDetailsRequestResource taxProfileDetailsResource : addTaxProfileRequestResource
				.getTaxProfileDetailsList()) {
			TaxProfileDetails taxProfileDetails = new TaxProfileDetails();
			taxProfileDetails.setTaxProfileId(taxProfile);
			if (addTaxProfileRequestResource.getTaxAmountType().equals(TaxAmountType.AMOUNT.toString())
					|| addTaxProfileRequestResource.getTaxAmountType()
							.equals(TaxAmountType.AMOUNTWITHTIER.toString())) {
				taxProfileDetails.setTaxAmount(stringToBigDecimal(taxProfileDetailsResource.getTaxAmount()));
			} else if (addTaxProfileRequestResource.getTaxAmountType().equals(TaxAmountType.RATE.toString())
					|| addTaxProfileRequestResource.getTaxAmountType().equals(TaxAmountType.RATEWITHTIER.toString())) {
				taxProfileDetails.setTaxRate(stringToBigDecimal(taxProfileDetailsResource.getTaxRate()));
			}
			if (addTaxProfileRequestResource.getTaxAmountType().equals(TaxAmountType.AMOUNTWITHTIER.toString())
					|| addTaxProfileRequestResource.getTaxAmountType().equals(TaxAmountType.RATEWITHTIER.toString())) {
				taxProfileDetails.setTierMinValue(stringToBigDecimal(taxProfileDetailsResource.getTierMinValue()));
				taxProfileDetails.setTierMaxValue(stringToBigDecimal(taxProfileDetailsResource.getTierMaxValue()));
			}

			taxProfileDetails = taxProfileDetailsRepository.saveAndFlush(taxProfileDetails);
		}

		return taxProfile;

	}

	@Override
	public TaxProfile updateTaxProfile(String tenantId,
			UpdateTaxProfileRequestResource updateTaxProfileRequestResource) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		// validateRecord("UPDATE", tenantId, new AddTaxProfileRequestResource(),
		// updateTaxProfileRequestResource);

		// Added by Senitha on 01-05-2020
		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "username");

		Optional<TaxProfile> isPresentTaxProfile = taxProfileRepository
				.findById(stringToLong(updateTaxProfileRequestResource.getId()));
		if (isPresentTaxProfile.isPresent()) {

			if (!isPresentTaxProfile.get().getVersion()
					.equals(Long.parseLong(updateTaxProfileRequestResource.getVersion())))
				throw new ValidateRecordException(environment.getProperty("version.not-match"), "version");

			TaxProfile taxProfile = isPresentTaxProfile.get();
			taxProfile.setTaxProfileStatus(
					TaxProfileStatus.valueOf(updateTaxProfileRequestResource.getTaxProfileStatus()));
			taxProfile.setVersion(stringToLong(updateTaxProfileRequestResource.getVersion()));
			taxProfile.setModifiedDate(currentTimestamp);
			taxProfile.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			taxProfile = taxProfileRepository.saveAndFlush(taxProfile);
			return taxProfile;

		} else {
			throw new NoRecordFoundException(environment.getProperty("common.record-not-found"));
		}
	}

	/**
	 * String value convert to Long
	 * 
	 * @param String value
	 * @return Long value
	 */
	private Long stringToLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return null;
		}
	}

	private Integer stringToInt(String str) {
		if (str != null) {
			return new Integer(str);
		} else {
			return Integer.valueOf(0);
		}
	}

	private BigDecimal stringToBigDecimal(String value) {
		if (value != null) {
			return new BigDecimal(value);
		} else {
			return BigDecimal.valueOf(0.0);
		}
	}

	private Float stringToFloat(String str) {
		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
			return null;
		}
	}

	private Double stringToDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return null;
		}
	}

	private Date formatDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}

	}

	/*
	 * * *********************************************** Validate
	 * InputData****************************************************
	 */
	private void validateRecord(String action, String tenantId,
			AddTaxProfileRequestResource addTaxProfileRequestResource) {
		Long taxCodeId = -1L;
		String effectiveDate = "";
		String applicableLevel = "";
		Long prodCategoryId = -1L;
		String prodCategoryDesc = "";
		String subProductId = "";
		String applicableAccTypeId = "";
		String applicableAccTypeDesc = "";
		String applicableProdStatus = "";
		Long applicableProdId = 1L;
		String applicableProdName = "";
		String declarationTypeId = "";
//		String declarationTypeDesc = "";
		Long customerCategoryId = -1L;
		String customerCategoryCode = "";
		String customerCategory = "";
		Long customerSubCategoryIndId = 1L;
		String customerSubCategoryIndDesc = "";
		Long customerSubCategoryNonIndId = 1L;
		String customerSubCategoryNonIndDesc = "";
		Long customerResidentTypeId = 1L;
		String customerResidentTypeDesc = "";
		int taxApplicableMinAge = 0;
		int taxApplicableMaxAge = 0;
		String ageEffectiveDateType = "";
		String taxAmountType = "";
		Double taxAmount = 0.00;
		Float taxRate = (float) 0.00;

		Double taxApplicableMinValue = 0.00;
		Double taxApplicableMaxValue = 0.00;

		String otherInterestIncome = "";
		String taxProfileStatus = "";

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime nowIs = LocalDateTime.now();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curr_date = null;
		try {
			curr_date = formatter.parse(dtf.format(nowIs));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (action.equals("ADD")) {
			effectiveDate = addTaxProfileRequestResource.getTaxEffectiveDate();
			taxCodeId = stringToLong(addTaxProfileRequestResource.getTaxCodeId());
			applicableLevel = addTaxProfileRequestResource.getTaxApplicableLevel();
			prodCategoryId = stringToLong(addTaxProfileRequestResource.getProductCategoryComnListId());
			subProductId = addTaxProfileRequestResource.getSubProductId();
			applicableAccTypeId = addTaxProfileRequestResource.getApplicableAccTypeComnListId();
			applicableAccTypeDesc = addTaxProfileRequestResource.getApplicableAccTypeDesc();
			applicableProdStatus = addTaxProfileRequestResource.getApplicableProductStatus();
			applicableProdId = stringToLong(addTaxProfileRequestResource.getApplicableProductId());
			applicableProdName = addTaxProfileRequestResource.getApplicableProductName();
			declarationTypeId = addTaxProfileRequestResource.getDeclarationTypeComnListId();
//			declarationTypeDesc = addTaxProfileRequestResource.getDeclarationTypeDesc();
			customerCategoryId = stringToLong(addTaxProfileRequestResource.getCustomerCategoryId());
			customerCategoryCode = addTaxProfileRequestResource.getCustomerCategoryCode();
			customerCategory = addTaxProfileRequestResource.getCustomerCategory();
			customerSubCategoryNonIndId = stringToLong(
					addTaxProfileRequestResource.getCustomerSubTypeNonIndividualId());
			customerSubCategoryNonIndDesc = addTaxProfileRequestResource.getCustomerSubTypeNonIndividualDesc();
			customerSubCategoryIndId = stringToLong(addTaxProfileRequestResource.getCustomerSubTypeIndividualId());
			customerSubCategoryIndDesc = addTaxProfileRequestResource.getCustomerSubTypeIndividualDesc();
			customerResidentTypeId = stringToLong(addTaxProfileRequestResource.getCustomerResidentTypeId());
			customerResidentTypeDesc = addTaxProfileRequestResource.getCustomerResidentTypeDesc();
			if (addTaxProfileRequestResource.getTaxApplicableMinAge() != null
					&& addTaxProfileRequestResource.getTaxApplicableMinAge() != "")
				taxApplicableMinAge = Integer.parseInt(addTaxProfileRequestResource.getTaxApplicableMinAge());
			if (addTaxProfileRequestResource.getTaxApplicableMaxAge() != null
					&& addTaxProfileRequestResource.getTaxApplicableMaxAge() != "")
				taxApplicableMaxAge = Integer.parseInt(addTaxProfileRequestResource.getTaxApplicableMaxAge());
			ageEffectiveDateType = addTaxProfileRequestResource.getAgeEffectiveDateType();
			taxAmountType = addTaxProfileRequestResource.getTaxAmountType();
//			if (addTaxProfileRequestResource.getTaxAmount() != null
//					&& addTaxProfileRequestResource.getTaxAmount() != "")
//				taxAmount = stringToDouble(addTaxProfileRequestResource.getTaxAmount());
//			if (addTaxProfileRequestResource.getTaxRate() != null && addTaxProfileRequestResource.getTaxRate() != "")
//				taxRate = stringToFloat(addTaxProfileRequestResource.getTaxRate());
			if (addTaxProfileRequestResource.getTaxApplicableMinValue() != null
					&& addTaxProfileRequestResource.getTaxApplicableMinValue() != "")
				taxApplicableMinValue = stringToDouble(addTaxProfileRequestResource.getTaxApplicableMinValue());
			if (addTaxProfileRequestResource.getTaxApplicableMaxValue() != null
					&& addTaxProfileRequestResource.getTaxApplicableMaxValue() != "")
				taxApplicableMaxValue = stringToDouble(addTaxProfileRequestResource.getTaxApplicableMaxValue());
			otherInterestIncome = addTaxProfileRequestResource.getOtherInterestIncome();
			taxProfileStatus = addTaxProfileRequestResource.getTaxProfileStatus();
		} /*
			 * else if (action.equals("UPDATE")) { effectiveDate =
			 * updateTaxProfileRequestResource.getTaxEffectiveDate(); taxCodeId =
			 * stringToLong(updateTaxProfileRequestResource.getTaxCodeId()); applicableLevel
			 * = updateTaxProfileRequestResource.getTaxApplicableLevel(); prodCategoryId =
			 * stringToLong(updateTaxProfileRequestResource.getProductCategoryComnListId());
			 * prodCategoryDesc = updateTaxProfileRequestResource.getProductCategoryDesc();
			 * applicableAccTypeId =
			 * updateTaxProfileRequestResource.getApplicableAccTypeComnListId();
			 * applicableAccTypeDesc =
			 * updateTaxProfileRequestResource.getApplicableAccTypeDesc();
			 * applicableProdStatus =
			 * updateTaxProfileRequestResource.getApplicableProductStatus();
			 * applicableProdId =
			 * stringToLong(updateTaxProfileRequestResource.getApplicableProductId());
			 * applicableProdName =
			 * updateTaxProfileRequestResource.getApplicableProductName(); declarationTypeId
			 * = updateTaxProfileRequestResource.getDeclarationTypeComnListId();
			 * declarationTypeDesc =
			 * updateTaxProfileRequestResource.getDeclarationTypeDesc(); customerCategoryId
			 * = stringToLong(updateTaxProfileRequestResource.getCustomerCategoryId());
			 * customerCategoryCode =
			 * updateTaxProfileRequestResource.getCustomerCategoryCode(); customerCategory =
			 * updateTaxProfileRequestResource.getCustomerCategory();
			 * customerSubCategoryNonIndId = stringToLong(
			 * updateTaxProfileRequestResource.getCustomerSubTypeNonIndividualId());
			 * customerSubCategoryNonIndDesc =
			 * updateTaxProfileRequestResource.getCustomerSubTypeNonIndividualDesc();
			 * customerSubCategoryIndId =
			 * stringToLong(updateTaxProfileRequestResource.getCustomerSubTypeIndividualId()
			 * ); customerSubCategoryIndDesc =
			 * updateTaxProfileRequestResource.getCustomerSubTypeIndividualDesc();
			 * customerResidentTypeId =
			 * stringToLong(updateTaxProfileRequestResource.getCustomerResidentTypeId());
			 * customerResidentTypeDesc =
			 * updateTaxProfileRequestResource.getCustomerResidentTypeDesc();
			 * if(updateTaxProfileRequestResource.getTaxApplicableMinAge() != "")
			 * taxApplicableMinAge =
			 * Integer.parseInt(updateTaxProfileRequestResource.getTaxApplicableMinAge());
			 * if(updateTaxProfileRequestResource.getTaxApplicableMaxAge() != "")
			 * taxApplicableMaxAge =
			 * Integer.parseInt(updateTaxProfileRequestResource.getTaxApplicableMaxAge());
			 * ageEffectiveDateType =
			 * updateTaxProfileRequestResource.getAgeEffectiveDateType(); taxAmountType =
			 * updateTaxProfileRequestResource.getTaxAmountType();
			 * if(addTaxProfileRequestResource.getTaxAmount() != "") taxAmount =
			 * stringToDouble(updateTaxProfileRequestResource.getTaxAmount());
			 * if(addTaxProfileRequestResource.getTaxRate() != "") taxRate =
			 * stringToFloat(updateTaxProfileRequestResource.getTaxRate());
			 * if(addTaxProfileRequestResource.getTaxApplicableMinValue() != "")
			 * taxApplicableMinValue =
			 * stringToDouble(updateTaxProfileRequestResource.getTaxApplicableMinValue());
			 * if(addTaxProfileRequestResource.getTaxApplicableMaxValue() != "")
			 * taxApplicableMaxValue =
			 * stringToDouble(updateTaxProfileRequestResource.getTaxApplicableMaxValue());
			 * otherInterestIncome =
			 * updateTaxProfileRequestResource.getOtherInterestIncome(); taxProfileStatus =
			 * updateTaxProfileRequestResource.getTaxProfileStatus(); }
			 */

		/* Validate tax effective date */
		Date effectiveDateIs = null;
		try {
			effectiveDateIs = formatter.parse(effectiveDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (effectiveDateIs.compareTo(curr_date) < 0) {
			throw new ValidateRecordException(environment.getProperty("taxEffectiveDate.invalid"), "taxEffectiveDate");
		}

		/* Validate Tax Code ID */
//		Optional<TaxCode> taxCode = taxCodeRepository.findById(taxCodeId);
		TaxCodeResponse taxCode = validationService.validateTaxCode(tenantId,
				addTaxProfileRequestResource.getTaxCodeId());
		if (taxCodeId != null) {
			if (taxCode != null) {
				if (!taxCode.getTaxCodeStatus().equals(TaxCodeStatus.ACTIVE)) {
					throw new ValidateRecordException(environment.getProperty("taxCodeId.invalid"), "taxCodeId");
				}
			} else {
				throw new ValidateRecordException(environment.getProperty("taxCodeId.invalid"), "taxCodeId");
			}
		}

		/*
		 * Validate Product Category: common list if (prodCategoryId != null) {
		 * validationService.prodCategoryExistValidation(tenantId, prodCategoryId,
		 * prodCategoryDesc); }
		 */

		if (prodCategoryId != null) {
			validationService.prodCategoryValidation(tenantId, prodCategoryId, prodCategoryDesc);
		}

		if (customerCategoryId != null) {
			validationService.customerCategoryExistValidation(tenantId, customerCategoryId, customerCategoryCode,
					customerCategory);
		}

		/*
		 * Validate Tax Applicable Account Type: common list if (applicableAccTypeId !=
		 * null) { validationService.applicableAccTypeExistValidation(tenantId,
		 * applicableAccTypeId, applicableAccTypeDesc); }
		 */

		/*
		 * Validate Declaration Type: common list if (declarationTypeId != null) {
		 * validationService.declarationTypeExistValidation(tenantId, declarationTypeId,
		 * declarationTypeDesc); }
		 */

		/* Validate Applicable Product Status and Applicable Products */
		if (applicableProdStatus.equals(ApplicableProductStatus.YES.toString())) {
			if (applicableProdId == null || applicableProdName == null) {
				throw new ValidateRecordException(environment.getProperty("applicableProductId.not-null"),
						"applicableProductId");
			} else {
				validationService.applicableProductValidation(tenantId, applicableProdId, applicableProdName);
			}
			/*
			 * if (applicableProdId == null || applicableProdName.equals("")) { throw new
			 * ValidateRecordException(environment.getProperty(
			 * "applicableProductId.not-null"), "applicableProductId"); }
			 */
		}

		/* Customer Level Validations */
		if (applicableLevel.equals(TaxApplicableLevel.CUSTOMER.toString())) {
			if (customerCategoryId == null) {
				throw new ValidateRecordException(environment.getProperty("customerCategory.not-null"),
						"customerCategoryId");
			}

			if (customerCategory.equals("Non Individual") && customerSubCategoryNonIndId == null) {
				throw new ValidateRecordException(environment.getProperty("customerSubTypeNonIndividualId.not-null"),
						"customerSubTypeNonIndividualId");
			} else if (customerCategory.equals("Non Individual") && customerSubCategoryNonIndId != null) {
				validationService.customerSubTypeNonIndExistValidation(tenantId, customerSubCategoryNonIndId,
						customerSubCategoryNonIndDesc);
			}

			if (customerCategory.equals("Individual") && customerSubCategoryIndId == null) {
				throw new ValidateRecordException(environment.getProperty("customerSubTypeIndividualId.not-null"),
						"customerSubTypeIndividualId");
			} else if (customerCategory.equals("Individual") && customerSubCategoryIndId != null) {
				validationService.customerSubTypeIndExistValidation(tenantId, customerSubCategoryIndId,
						customerSubCategoryIndDesc);
			}

			if (customerResidentTypeId == null) {
				throw new ValidateRecordException(environment.getProperty("customerResidentTypeId.not-null"),
						"customerResidentTypeId");
			} else if (customerResidentTypeId != null) {
				validationService.customerResidentTypeExistValidation(tenantId, customerResidentTypeId,
						customerResidentTypeDesc);
			}

			if (taxApplicableMinAge != 0 || taxApplicableMaxAge != 0) {
				if (ageEffectiveDateType == null || ageEffectiveDateType.isEmpty()) {
					throw new ValidateRecordException(environment.getProperty("ageEffectiveDateType.not-null"),
							"ageEffectiveDateType");
				}
			}

			/*
			 * if (otherInterestIncome.equals("") || otherInterestIncome == null) { throw
			 * new ValidateRecordException(environment.getProperty(
			 * "otherInterestIncome.not-null"),"otherInterestIncome"); }
			 */
		}

		// Added by Senitha on 01-MAY-2020 for Global and Customer
		if (otherInterestIncome == null || otherInterestIncome.isEmpty()) {
			throw new ValidateRecordException(environment.getProperty("otherInterestIncome.not-null"),
					"otherInterestIncome");
		}

		/* Tax Value Validation */
		if (taxAmountType.equals(TaxAmountType.AMOUNT.toString())
				|| taxAmountType.equals(TaxAmountType.AMOUNTWITHTIER.toString())) {
			if (taxAmount == 0) {
				throw new ValidateRecordException(environment.getProperty("taxAmount.not-null"), "taxAmount");
			}
		} else if (taxAmountType.equals(TaxAmountType.RATE.toString())
				|| taxAmountType.equals(TaxAmountType.AMOUNT.RATEWITHTIER.toString())) {
			if (taxRate > 100) {
				throw new ValidateRecordException(environment.getProperty("taxRateRange"), "taxRate");
			}
			if (taxRate == 0) {
				throw new ValidateRecordException(environment.getProperty("taxRate.not-null"), "taxRate");
			}
		}

		/* Tax Applicable Range Validation */
		if (taxAmountType.equals(TaxAmountType.AMOUNTWITHTIER.toString())
				|| taxAmountType.equals(TaxAmountType.RATEWITHTIER.toString())) {
			if (taxApplicableMinValue == 0 && taxApplicableMaxValue == 0) {
				throw new ValidateRecordException(environment.getProperty("taxApplicableMaxValue.not-null"),
						"taxApplicableMaxValue");
			}
		}

		Optional<Collection<TaxProfile>> taxProfiles = null;
		if (action.equals("ADD")) {
			taxProfiles = taxProfileRepository.findByTaxCodeIdAndTaxProfileStatus(taxCodeId,
					TaxProfileStatus.valueOf(taxProfileStatus));
		} else {
			taxProfiles = taxProfileRepository.findByTaxCodeIdAndTaxProfileStatusNotIn(taxCodeId,
					TaxProfileStatus.valueOf(taxProfileStatus));
		}

		if (taxProfiles.isPresent()) {

			for (TaxProfile taxProfileRec : taxProfiles.get()) {

				/* Validate Tax Applicable Level */
				if (taxProfileRec.getEffectiveDate().equals(effectiveDate)
						&& !taxProfileRec.getTaxApplicableLevel().equals(TaxApplicableLevel.valueOf(applicableLevel))) {
					throw new ValidateRecordException(environment.getProperty("taxApplicableLevel.invalid"),
							"taxApplicableLevel");
				}

				/* Validate Product Category: cross validations */
				if (taxProfileRec.getProductCategoryDesc().equals("ALL")
						&& !taxProfileRec.getProductCategoryDesc().equals(prodCategoryDesc)) {
					throw new ValidateRecordException(environment.getProperty("productCategoryDesc.invalid"),
							"productCategoryDesc");
				} else if (!taxProfileRec.getProductCategoryDesc().equals("ALL") && prodCategoryDesc.equals("ALL")) {
					throw new ValidateRecordException(environment.getProperty("productCategoryDesc.invalid"),
							"productCategoryDesc");
				}

				/* Validate Tax Applicable Account Type: cross validation */
				if (taxProfileRec.getApplicableAccTypeDesc().equals("ALL")
						&& !taxProfileRec.getApplicableAccTypeDesc().equals(applicableAccTypeDesc)) {
					throw new ValidateRecordException(environment.getProperty("applicableAccTypeDesc.invalid"),
							"applicableAccTypeDesc");
				} else if (!taxProfileRec.getApplicableAccTypeDesc().equals("ALL")
						&& applicableAccTypeDesc.equals("ALL")) {
					throw new ValidateRecordException(environment.getProperty("applicableAccTypeDesc.invalid"),
							"applicableAccTypeDesc");
				}

//				/* Validate Declaration Type: cross validation */
//				if (taxProfileRec.getDeclarationTypeDesc().equals(notApplicable)
//						&& !taxProfileRec.getDeclarationTypeDesc().equals(declarationTypeDesc)) {
//					throw new ValidateRecordException(environment.getProperty("declarationTypeDesc.invalid"),
//							"declarationTypeDesc");
//				} else if (!taxProfileRec.getDeclarationTypeDesc().equals(notApplicable)
//						&& declarationTypeDesc.equals(notApplicable)) {
//					throw new ValidateRecordException(environment.getProperty("declarationTypeDesc.invalid"),
//							"declarationTypeDesc");
//				}

				/* Customer Level Cross Validations */
				if (taxProfileRec.getTaxApplicableLevel().equals(TaxApplicableLevel.CUSTOMER)) {
					/*
					 * if (!taxProfileRec.getCustomerCategory().equals(CustomerCategory.valueOf(
					 * customerCategory))) { throw new
					 * ValidateRecordException(environment.getProperty("customerCategory.invalid"),
					 * "customerCategory"); }
					 */

					if (!taxProfileRec.getOtherInterestIncome()
							.equals(OtherInterestIncome.valueOf(otherInterestIncome))) {
						throw new ValidateRecordException(environment.getProperty("otherInterestIncome.invalid"),
								"otherInterestIncome");
					}
				}

				/* Tax Amount Type Validation */
				if (taxProfileRec.getEffectiveDate().equals(effectiveDate)
						&& !taxProfileRec.getTaxAmountType().equals(TaxAmountType.valueOf(taxAmountType))) {
					throw new ValidateRecordException(environment.getProperty("taxAmountType.invalid"),
							"taxAmountType");
				}

				/* Tax Amount/Tax Rate Validation */
				/* Requirement changed in QA cycle */
				/*
				 * if (taxAmount != 0) { if (taxProfileRec.getTaxAmount() != null &&
				 * taxProfileRec.getTaxAmount().doubleValue() != taxAmount) { throw new
				 * ValidateRecordException(environment.getProperty("taxAmount.invalid"),
				 * "taxAmount"); } } else if (taxRate != 0) { if(taxProfileRec.getTaxRate() !=
				 * null && taxProfileRec.getTaxRate().floatValue() != taxRate) { throw new
				 * ValidateRecordException(environment.getProperty("taxRate.invalid"),
				 * "taxRate"); } }
				 */

				/* Tax Applicable Range Validation */
				/*
				 * Two Fields Added from Requirement
				 * Change:getProductCategoryComnListId,getApplicableAccTypeComnListId
				 */
				if (taxProfileRec.getTaxApplicableLevel().equals(TaxApplicableLevel.CUSTOMER)
						&& taxProfileRec.getProductCategoryComnListId() == prodCategoryId
						&& taxProfileRec.getApplicableAccTypeComnListId()
								.equals(ApplicableAccTypeComnListEnum.valueOf(applicableAccTypeId))
						&& taxProfileRec.getApplicableProductId() == applicableProdId
						&& taxProfileRec.getDeclarationTypeComnListId().equals(declarationTypeId)
						&& taxProfileRec.getCustomerCategory().equals(customerCategory)
						&& (taxProfileRec.getCustomerSubTypeIndividualId() == customerSubCategoryIndId
								|| taxProfileRec.getCustomerSubTypeNonIndividualId() == customerSubCategoryNonIndId)
						&& taxProfileRec.getCustomerResidentTypeId() == customerResidentTypeId
						&& taxProfileRec.getEffectiveDate().equals(effectiveDate)
						&& (taxProfileRec.getTaxApplicableMinValue() != null
								&& taxProfileRec.getTaxApplicableMaxValue() != null)
						&& !(taxApplicableMinValue == 0 && taxApplicableMaxValue == 0)) {
					if ((taxProfileRec.getTaxApplicableMinValue().doubleValue() == taxApplicableMinValue
							&& taxProfileRec.getTaxApplicableMaxValue().doubleValue() == taxApplicableMaxValue)
							|| (taxApplicableMaxValue >= taxProfileRec.getTaxApplicableMinValue().doubleValue()
									&& taxApplicableMaxValue <= taxProfileRec.getTaxApplicableMaxValue().doubleValue())
							|| (taxApplicableMinValue >= taxProfileRec.getTaxApplicableMinValue().doubleValue()
									&& taxApplicableMinValue <= taxProfileRec.getTaxApplicableMaxValue()
											.doubleValue())) {
						throw new ValidateRecordException(environment.getProperty("taxApplicableMaxValue.invalid"),
								"taxApplicableMaxValue");
					}
				} else if (taxProfileRec.getTaxApplicableLevel().equals(TaxApplicableLevel.GLOBAL)
						&& taxProfileRec.getProductCategoryComnListId() == prodCategoryId
						&& taxProfileRec.getApplicableAccTypeComnListId()
								.equals(ApplicableAccTypeComnListEnum.valueOf(applicableAccTypeId))
						&& taxProfileRec.getApplicableProductId() == applicableProdId
						&& taxProfileRec.getDeclarationTypeComnListId() == declarationTypeId
						&& taxProfileRec.getEffectiveDate().equals(effectiveDate)
						&& (taxProfileRec.getTaxApplicableMinValue() != null
								&& taxProfileRec.getTaxApplicableMaxValue() != null)
						&& !(taxApplicableMinValue == 0 && taxApplicableMaxValue == 0)) {
					if ((taxProfileRec.getTaxApplicableMinValue().doubleValue() == taxApplicableMinValue
							&& taxProfileRec.getTaxApplicableMaxValue().doubleValue() == taxApplicableMaxValue)
							|| (taxApplicableMaxValue >= taxProfileRec.getTaxApplicableMinValue().doubleValue()
									&& taxApplicableMaxValue <= taxProfileRec.getTaxApplicableMaxValue().doubleValue())
							|| (taxApplicableMinValue >= taxProfileRec.getTaxApplicableMinValue().doubleValue()
									&& taxApplicableMinValue <= taxProfileRec.getTaxApplicableMaxValue()
											.doubleValue())) {
						throw new ValidateRecordException(environment.getProperty("taxApplicableMaxValue.invalid"),
								"taxApplicableMaxValue");
					}
				}

			}
		}

	}

	protected JSONObject invokeService(String tenantId, String contextUrl) throws IOException, JSONException {
		HttpURLConnection conn = null;
		String output = "";
		String msg = "";
		JSONObject obj = null;

		try {
			URL urlObj = new URL(contextUrl);

			conn = (HttpURLConnection) urlObj.openConnection();
			conn.setRequestMethod(HttpMethod.GET.toString());
			conn.setRequestProperty(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());

			if (conn.getResponseCode() == HttpStatus.OK.value()) {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				while ((output = br.readLine()) != null) {
					msg = msg + output;
				}
				obj = new JSONObject(msg);
			}

			return obj;
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}

}
