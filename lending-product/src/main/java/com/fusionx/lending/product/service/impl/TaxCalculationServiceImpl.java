package com.fusionx.lending.product.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.TaxProfile;
import com.fusionx.lending.product.domain.TaxProfileDetails;
import com.fusionx.lending.product.enums.RateOrAmount;
import com.fusionx.lending.product.enums.TaxApplicableLevel;
import com.fusionx.lending.product.enums.TaxApplicableOnEnum;
import com.fusionx.lending.product.enums.TaxProfileStatus;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.TaxProfileDetailsRepository;
import com.fusionx.lending.product.repository.TaxProfileRepository;
import com.fusionx.lending.product.resources.CustomerDetResource;
import com.fusionx.lending.product.resources.CustomerPendDetResource;
import com.fusionx.lending.product.resources.CustomerRequestResponseResource;
import com.fusionx.lending.product.resources.LeadInfoRequestResponseResource;
import com.fusionx.lending.product.resources.TaxCalculationResource;
import com.fusionx.lending.product.resources.TaxCustomerResponse;
import com.fusionx.lending.product.resources.TaxDeclarationCustomerTypeResponse;
import com.fusionx.lending.product.resources.TaxDetailsResponse;
import com.fusionx.lending.product.resources.TaxEventDetailResponse;
import com.fusionx.lending.product.resources.TaxEventResponse;
import com.fusionx.lending.product.service.RemoteService;
import com.fusionx.lending.product.service.TaxCalculationService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class TaxCalculationServiceImpl  extends MessagePropertyBase  implements TaxCalculationService{
	
	@Autowired
	private TaxProfileRepository taxProfileRepository;
	
	@Autowired
	private TaxProfileDetailsRepository taxProfileDetailsRepository;
	
	@Autowired
	private ValidationService validationService;
	
	@Autowired
	private RemoteService remoteService;
	
	
	@Override
	public List<TaxDetailsResponse> calculateTaxDetailsResponse(String tenantId,TaxCalculationResource taxCalculationResource) {
		
		Long subProductId = taxCalculationResource.getSubProductId();	
		String taxTypeCode = taxCalculationResource.getTaxTypeCode();		
		BigDecimal loanAmount= taxCalculationResource.getLoanAmount();
		BigDecimal installmentAmount= taxCalculationResource.getInstallmentAmount();
				
		BigDecimal considerAmount = BigDecimal.ZERO;
		List<TaxDetailsResponse> taxDetailsResponseList =  new ArrayList<>();		

		
		TaxEventResponse taxEvent = validationService.getTaxEvent(tenantId, taxTypeCode);
		
		
		if(taxEvent != null) {
			List<TaxEventDetailResponse> taxEventDetailsList = taxEvent.getTaxEventDetails();
			
			if((taxEventDetailsList!= null) && (!taxEventDetailsList.isEmpty())) {
								
				for(TaxEventDetailResponse eventDetResponse :taxEventDetailsList) {
					
					TaxDetailsResponse taxDetailsResponse  = new TaxDetailsResponse();
					
					Long taxCodeId = eventDetResponse.getCodeId();
					String amountType = eventDetResponse.getAmountType();
					String applicableOn = eventDetResponse.getApplicableOn();
					Long applicationFrequencyId = eventDetResponse.getApplicationFrequency();
					
					
					if((TaxApplicableOnEnum.LOAN_AMOUNT.toString()).equals(applicableOn)) {
						considerAmount = loanAmount;
					}else if((TaxApplicableOnEnum.INSTALMENT.toString()).equals(applicableOn)) {
						considerAmount = installmentAmount;
					}
					
					
					BigDecimal taxAmount = BigDecimal.ZERO;
					TaxProfile taxProfile = null;
					
					Optional<TaxProfile> taxProfileOptional = findReleventTaxProfile(tenantId,subProductId,taxCodeId ,considerAmount , taxCalculationResource.getLeadId());
	
					if (taxProfileOptional.isPresent()) {
						
						taxProfile = taxProfileOptional.get();

						TaxProfileDetails taxProfDet = taxProfile.getTaxProfDet();
						BigDecimal taxAmountRate = BigDecimal.ZERO;
						amountType = taxProfile.getTaxAmountType().toString();

						if (taxProfDet != null) {
							if ((RateOrAmount.AMOUNT.toString()).equals(amountType)) {
								if(taxProfDet.getTaxAmount() != null) {
									taxAmountRate = taxProfDet.getTaxAmount();
									taxAmount = taxAmountRate;
								}else {
									throw new ValidateRecordException(environment.getProperty("feeCharge-detail-option-amount-not-null"),"message");
								}
							} else {
								if (taxProfDet.getTaxAmount() != null) {
									taxAmountRate = taxProfDet.getTaxRate();
									BigDecimal ratePercentage = (taxAmountRate).multiply(BigDecimal.valueOf(0.01));
									taxAmount = considerAmount.multiply(ratePercentage);
								}else {
									throw new ValidateRecordException(environment.getProperty("feeCharge-detail-option-rate-not-null"),"message");
								}
							}

							if ((taxProfile.getTaxApplicableMinValue()).compareTo(taxAmount) > 0) {
								taxAmount = taxProfile.getTaxApplicableMinValue();
							}
							if ((taxAmount).compareTo(taxProfile.getTaxApplicableMaxValue()) > 0) {
								taxAmount = taxProfile.getTaxApplicableMaxValue();
							}

							taxDetailsResponse.setTaxType(eventDetResponse.getCode());
							taxDetailsResponse.setTaxCodeId(taxCodeId);
							taxDetailsResponse.setTaxApplicableOn(applicableOn);
							taxDetailsResponse.setTaxAmountRate(taxAmountRate);
							taxDetailsResponse.setTotalTaxAmount(taxAmount);
							taxDetailsResponse.setApplicationFrequencyId(applicationFrequencyId);
							taxDetailsResponse.setTaxAmountType(amountType);
							
							taxDetailsResponseList.add(taxDetailsResponse);

						}
					}					
				}				
			}
		}
				
		return taxDetailsResponseList;		
	}
	
	
	
	public Optional<TaxProfile> findReleventTaxProfile(String tenantId , Long subProdtId , Long txCodeId ,BigDecimal considerAmount ,Long leadId) {
		

		Long taxCodeId = txCodeId;
		Long subProductId = subProdtId;
		
		TaxProfile latestTaxProfile = null;
		
		Optional<TaxCustomerResponse> taxCustomerResponseOptional = findCustomerDetails(leadId.toString() ,  tenantId);
		
		TaxCustomerResponse taxCustomerResponse = null;
		if (taxCustomerResponseOptional.isPresent()) {
			taxCustomerResponse = taxCustomerResponseOptional.get();
		} else {
			taxCustomerResponse = new TaxCustomerResponse();
		}

		
		List<TaxProfile> taxProfileList = taxProfileRepository.getTaxProfileUsingCustomerInfo(taxCodeId,TaxProfileStatus.ACTIVE, subProductId, taxCustomerResponse.getDeclarationType(), taxCustomerResponse.getCustomerCategory(),
				taxCustomerResponse.getCustomerSubType(), taxCustomerResponse.getCustomerResidentTypeId());

		if ((taxProfileList != null) && (!taxProfileList.isEmpty())) {
			latestTaxProfile = null;

			for (TaxProfile tp : taxProfileList) {

				if ((TaxApplicableLevel.CUSTOMER).equals(tp.getTaxApplicableLevel())) {
					String ageEffectiveDateType = tp.getAgeEffectiveDateType();

					Optional<Long> ageIsPresent = Optional.empty();

					if (("ACT_DATE").equals(ageEffectiveDateType)) {
						ageIsPresent = calAge(taxCustomerResponse.getBirthDay(), new Date());
					}

					if (ageIsPresent.isPresent()) {
						Long age = ageIsPresent.get();
						if ((tp.getTaxApplicableMinAge() != null) && (tp.getTaxApplicableMaxAge() != null)) {
							if ((tp.getTaxApplicableMinAge() <= age) && (tp.getTaxApplicableMaxAge() >= age)) {
								latestTaxProfile = tp;
							}
						} else if (tp.getTaxApplicableMinAge() != null && (tp.getTaxApplicableMinAge() <= age)) {
							latestTaxProfile = tp;

						} else if (tp.getTaxApplicableMaxAge() != null && (tp.getTaxApplicableMaxAge() >= age)) {
							latestTaxProfile = tp;
						} else if (tp.getTaxApplicableMaxAge() == null && tp.getTaxApplicableMinAge() == null) {
							latestTaxProfile = tp;
						}

					}
				} else {
					latestTaxProfile = taxProfileList.get(0);					
				}
			}

			if(latestTaxProfile != null) {
				List<TaxProfileDetails> taxProfileDetailsList = taxProfileDetailsRepository
						.findAllByTaxProfileIdId(latestTaxProfile.getId());
	
				for (TaxProfileDetails tpd : taxProfileDetailsList) {
					if ((considerAmount.compareTo(tpd.getTierMinValue()) >= 0) && (considerAmount.compareTo(tpd.getTierMaxValue()) < 0)) {
						latestTaxProfile.setTaxProfDet(tpd);
						break;
					}
				}
			}
		}

		return Optional.ofNullable(latestTaxProfile);
	}
	
	
	@Override
	public Optional<TaxCustomerResponse> findCustomerDetails(String leadId , String tenantId){
		
		TaxCustomerResponse response =  null;
		
		LeadInfoRequestResponseResource leadInfoRequestResponseResource = validationService.getLeadInfoById( tenantId,  leadId);		
		
		if (leadInfoRequestResponseResource != null) {
			List<CustomerRequestResponseResource> customersList = leadInfoRequestResponseResource.getCustomers();

			if ((customersList != null) && (!customersList.isEmpty())) {

				CustomerRequestResponseResource customerResponse = customersList.stream()
						.filter(n -> n.getCustomerMainType().equalsIgnoreCase("MAIN")).findFirst().orElse(null);

				if (customerResponse != null) {
					if (customerResponse.getCustomerId() != null) {
						CustomerDetResource customerDet = validationService.getCustomerDet(tenantId,
								customerResponse.getCustomerId());

						TaxDeclarationCustomerTypeResponse declarationTypeResp = validationService
								.getCustomerTaxDeclarationDet(tenantId, customerResponse.getCustomerId(),
										"tax-profile");
						if (customerDet != null) {



							response = new TaxCustomerResponse();
							response.setCustomerCategory(customerDet.getCusOrganizationTypeCode());
							if(("ORIN").equals(customerDet.getCusOrganizationTypeCode())) {
								response.setCustomerSubType(customerDet.getCusPersonTypeCommonListId());
							}else {
								response.setCustomerSubType(customerDet.getPerCorporateCategoryCommonListId());
							}
							
							
							response.setCustomerResidentTypeId(customerDet.getPerResidentStatusCommonListId());
							response.setDeclarationType(
									declarationTypeResp != null ? declarationTypeResp.getCtpTaxDeclarationType(): null);
							response.setBirthDay(customerDet.getPerDateOfBirth());
						}
					} else if (customerResponse.getPendingCustomerId() != null) {
						
						CustomerPendDetResource pendingCustomerDet = validationService.getPendingCustomerDet(tenantId,
								customerResponse.getPendingCustomerId());

						TaxDeclarationCustomerTypeResponse declarationTypeResp = validationService
								.getCustomerTaxDeclarationDet(tenantId, customerResponse.getPendingCustomerId(),"pending-tax-profile");

					
						if (pendingCustomerDet != null) {

							Optional<Long> ageOptional = Optional.empty();

							response = new TaxCustomerResponse();
							response.setCustomerCategory(pendingCustomerDet.getPcusOrganizationTypeCode());
							if(("ORIN").equals(pendingCustomerDet.getPcusOrganizationTypeCode())) {
								response.setCustomerSubType(pendingCustomerDet.getPcusPersonTypeCommonListId());
							}else {
								response.setCustomerSubType(pendingCustomerDet.getPperCorporateCategoryCommonListId());
							}
							
							
							response.setCustomerResidentTypeId(pendingCustomerDet.getPperResidentStatusCommonListId());
							response.setDeclarationType(
									declarationTypeResp != null ? declarationTypeResp.getCtpTaxDeclarationType()
											: null);
							response.setBirthDay(pendingCustomerDet.getPperDateOfBirth());
						}
					}
				}
			}
		}
		
		
		return Optional.ofNullable(response);
		
	}
	
	public Optional<Long> calAge(Date bd , Date cd) {
		try {
			
			LocalDate birthday = bd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate considerDate = cd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			Period p = Period.between(birthday, considerDate);
			return Optional.of(Long.valueOf(p.getYears()));
			
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	public Optional<Long> calAgeString(String bd) {
		try {
			
			// string format should be - "2018-11-02xxxxxxxxxxxxxxxx"
			String timestampAsString = bd;
			String substring = timestampAsString.substring(0, 10);

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			Date date = simpleDateFormat.parse(substring);

			LocalDate today = LocalDate.now();
			LocalDate birthday = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			Period p = Period.between(birthday, today);
			return Optional.of(Long.valueOf(p.getYears()));
			
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	
	
	
	
	
}
