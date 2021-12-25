package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.domain.CreditAppCollateralDetail;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CreditAppCollateralDetailsRepository;
import com.fusionx.lending.origination.resource.ComnSuppliesEntitiesResource;
import com.fusionx.lending.origination.resource.LastValuationDateResponseResource;
import com.fusionx.lending.origination.resource.ValuationAndInsuranceDetListResponseResource;
import com.fusionx.lending.origination.resource.ValuationAndInsuranceDetailsAddResource;
import com.fusionx.lending.origination.resource.ValuationAndInsuranceDetailsUpdateResource;
import com.fusionx.lending.origination.resource.ValuationDetailsAddResource;
import com.fusionx.lending.origination.resource.ValuationDetailsListAddResource;
import com.fusionx.lending.origination.resource.ValuationDetailsRequestResource;
import com.fusionx.lending.origination.resource.ValuationDetailsUpdateResource;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.ValidateService;
import com.fusionx.lending.origination.service.ValuationAndInsuranceDetailsService;

/**
 * Valuation and Insurance Detail Service Implementation
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		            	VenukiR      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ValuationAndInsuranceDetailsServiceImpl implements ValuationAndInsuranceDetailsService {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ValidateService validateService;

	@Autowired
	private RemoteService remoteService;
	
	@Autowired
	private CreditAppCollateralDetailsRepository creditAppCollateralDetailsRepository;
	
	@Value("${comn-currency}")
	protected String comnCurrencyUrl;
	
	@Override
	public void saveValuationAndInsuranceDetails(String tenantId, String createdUser,ValuationAndInsuranceDetailsAddResource valuationAndInsuranceDetailsAddResource ) {
		//validate asset against customer id from micro bpr
		Optional<CreditAppCollateralDetail> creditAppCollateralDetails=creditAppCollateralDetailsRepository.findByCustomerIdAndAssetEntityId(Long.parseLong(valuationAndInsuranceDetailsAddResource.getCustomerId()), 
				Long.parseLong(valuationAndInsuranceDetailsAddResource.getAssetsEntityId()));
				if(!creditAppCollateralDetails.isPresent())
				throw new ValidateRecordException(environment.getProperty("invalid.creApp.info"),"customerId");
		
		LoggerRequest.getInstance().logInfo("ValuationAndInsuranceDetails******************************** Validate Valuation Details *********************************************");
		if(valuationAndInsuranceDetailsAddResource.getValuationDetails()!=null)
		validateValuationDetails(tenantId, createdUser,valuationAndInsuranceDetailsAddResource.getAssetsEntityId(),valuationAndInsuranceDetailsAddResource.getValuationDetails(),
				creditAppCollateralDetails.get());
		
//		LoggerRequest.getInstance().logInfo("ValuationAndInsuranceDetails******************************** Validate Insurance Details *****************************************");
//		if(valuationAndInsuranceDetailsAddResource.getInsuranceDetails()!=null)
//		validateInsuranceDetails(tenantId, createdUser,valuationAndInsuranceDetailsAddResource.getAssetsEntityId(),valuationAndInsuranceDetailsAddResource.getInsuranceDetails(),
//				ActionType.SAVE ,null);
	}

	private void validateValuationDetails(String tenantId, String createdUser, String assetEntityId,
		List<ValuationDetailsAddResource> valuationDetailsAddResource, CreditAppCollateralDetail creditAppCollateralDet) {
		
		Boolean flagUpdateRequired = Boolean.FALSE;
		
		ValuationDetailsListAddResource valuationDetailsAddResourceList = new ValuationDetailsListAddResource();
		//List<ValuationDetailsAddResource> valuationDetailsList =  new ArrayList<>();
		
	
		for(ValuationDetailsAddResource valuationDetails : valuationDetailsAddResource) {
			
		//check existing valuation details
		if(valuationDetails.getFinalValuationFlag().equalsIgnoreCase("YES")) {
			if(creditAppCollateralDet.getFinalValuationId()!=null)
				throw new ValidateRecordException(environment.getProperty("final-valuation-exist"),"finalValuationFlag");
			
			if(creditAppCollateralDet.getFinalValuationId()==null)
				flagUpdateRequired= Boolean.TRUE;
		}
		
		 /* *********************************************** Validate valuation officers ***********************************************/
		if (valuationDetails.getValuerId() != null && !valuationDetails.getValuerId().isEmpty()) {
			ComnSuppliesEntitiesResource comnSuppliesEntitiesResource = validateService.validateValuer(tenantId, Long.parseLong(valuationDetails.getValuerId())
					, valuationDetails.getValuerRefCode());
       	
   		if (!comnSuppliesEntitiesResource.getSupLawyerType().equals(valuationDetails.getValuerType()))
   			throw new ValidateRecordException(environment.getProperty("valuer.invalid"),"valuerId");
		}
		
		/************************************************ Validate Last Valuation Date with Previous Record ***********************************************/
	   LastValuationDateResponseResource lastValuationDateResponseResource = validateService.validateLastValuationDate(tenantId, Long.parseLong(assetEntityId));

		if(lastValuationDateResponseResource!=null && lastValuationDateResponseResource.getValuationsExists().equals(Boolean.TRUE)) {
			valuationDetails.setLastValuationDate(dateToString(lastValuationDateResponseResource.getLastValuationDate()));
		  //valuationDetailsAddResource.setReValuationDate(dateToString(lastValuationDateResponseResource.getReValuationDate()));
		}
		
		/************************************************ Validate Past Date ***********************************************/
		validateService.futureDateValidation(valuationDetails.getValuationDate(), ServiceEntity.VALUATION_DATE);

		//valuationDetailsList.add(valuationDetails);
		
		valuationDetailsAddResourceList.setAssetsEntityId(assetEntityId);
		valuationDetailsAddResourceList.setValuationDetails(valuationDetails);
		LoggerRequest.getInstance().logInfo("ValuationAndInsuranceDetails******************************** Integrate with Collateral and Save Details *****************************************");
		Long valuationId = validateService.valuationDetInteregation(tenantId, createdUser, valuationDetailsAddResourceList, Long.parseLong(assetEntityId));
		
		if(flagUpdateRequired.equals(Boolean.TRUE))
			updateColCreditApp(tenantId, createdUser, valuationId,  valuationDetails.getFinalValuationFlag(),creditAppCollateralDet.getId());
		}
		
//		valuationDetailsAddResourceList.setAssetsEntityId(assetEntityId);
//		valuationDetailsAddResourceList.setValuationDetails(valuationDetailsList);
//		LoggerRequest.getInstance().logInfo("ValuationAndInsuranceDetails******************************** Integrate with Collateral and Save Details *****************************************");
//		validateService.valuationDetInteregation(tenantId, createdUser, valuationDetailsAddResourceList, Long.parseLong(assetEntityId));

	}
	
	private void validateAndUpdateValuationDetails(String tenantId, String createdUser,
			ValuationDetailsUpdateResource valuationDetails, Long id, CreditAppCollateralDetail creditAppCollateralDet) {
		Boolean flagUpdateRequired = Boolean.FALSE;
		
		LoggerRequest.getInstance().logInfo("ValuationAndInsuranceDetails******************************** Validate Valuation Details *********************************************");
		
		if(valuationDetails!=null) {
			
			//check existing valuation details
			if(valuationDetails.getFinalValuationFlag().equalsIgnoreCase("YES") && creditAppCollateralDet.getFinalValuationId()!=null) {
					if(!creditAppCollateralDet.getFinalValuationId().equals(id))
					throw new ValidateRecordException(environment.getProperty("final-valuation-exist"),"finalValuationFlag");			
			}else if(valuationDetails.getFinalValuationFlag().equalsIgnoreCase("YES") && creditAppCollateralDet.getFinalValuationId()==null){
				flagUpdateRequired= Boolean.TRUE;
			}
			
			if(valuationDetails.getFinalValuationFlag().equalsIgnoreCase("NO") && creditAppCollateralDet.getFinalValuationId()!=null) {
				if(creditAppCollateralDet.getFinalValuationId().equals(id)) {
					flagUpdateRequired= Boolean.TRUE;
				}	
			}
		
		 /* *********************************************** Validate valuation officers ***********************************************/
		if (valuationDetails.getValuerId() != null && !valuationDetails.getValuerId().isEmpty()) {
			ComnSuppliesEntitiesResource comnSuppliesEntitiesResource = validateService.validateValuer(tenantId, Long.parseLong(valuationDetails.getValuerId())
					, valuationDetails.getValuerRefCode());
	   	
			if (!comnSuppliesEntitiesResource.getSupLawyerType().equals(valuationDetails.getValuerType()))
				throw new ValidateRecordException(environment.getProperty("valuer.invalid"),"valuerId");
		
		}
		/************************************************ Validate Last Valuation Date with Previous Record ***********************************************/
		LastValuationDateResponseResource lastValuationDateResponseResource = validateService.validateLastValuationDate(tenantId, creditAppCollateralDet.getAssetEntityId());

		if(lastValuationDateResponseResource!=null && lastValuationDateResponseResource.getValuationsExists().equals(Boolean.TRUE)) {
			valuationDetails.setLastValuationDate(dateToString(lastValuationDateResponseResource.getLastValuationDate()));
		  //valuationDetailsAddResource.setReValuationDate(dateToString(lastValuationDateResponseResource.getReValuationDate()));
		}
	
		/************************************************ Validate Past Date ***********************************************/
		validateService.futureDateValidation(valuationDetails.getValuationDate(), ServiceEntity.VALUATION_DATE);

		valuationDetails.setValuationDetailsId(id.toString());
		valuationDetails.setAssetEntityId(creditAppCollateralDet.getAssetEntityId().toString());
		
		LoggerRequest.getInstance().logInfo("ValuationAndInsuranceDetails******************************** Integrate with Collateral and Update Details *****************************************");
		validateService.valuationDetUpdateInteregation(tenantId, createdUser, valuationDetails);	
		if(flagUpdateRequired.equals(Boolean.TRUE))
		updateColCreditApp(tenantId, createdUser, id, valuationDetails.getFinalValuationFlag(), creditAppCollateralDet.getId());
			
		}
		
	}


	private void updateColCreditApp(String tenantId, String createdUser, Long id, String finalValuationFlag, Long creditAppId) {
		
		CreditAppCollateralDetail creditAppCollateralDetail=creditAppCollateralDetailsRepository.getOne(creditAppId);
		if(finalValuationFlag.equalsIgnoreCase("YES"))
		creditAppCollateralDetail.setFinalValuationId(id);
		
		if(finalValuationFlag.equalsIgnoreCase("NO"))
			creditAppCollateralDetail.setFinalValuationId(null);
		
		creditAppCollateralDetail.setModifiedUser(createdUser);
		creditAppCollateralDetail.setModifiedDate(getCreateOrModifyDate());
		creditAppCollateralDetail.setSyncTs(getCreateOrModifyDate());
		creditAppCollateralDetailsRepository.saveAndFlush(creditAppCollateralDetail);
		
	}

	@Override
	public void updateValuationAndInsuranceDetails(String tenantId, String createdUser, Long id, ValuationAndInsuranceDetailsUpdateResource valuationAndInsuranceDetailsUpdateResource ) {
		//validate asset against customer id
		Optional<CreditAppCollateralDetail> creditAppCollateralDetails=creditAppCollateralDetailsRepository.findByCustomerIdAndAssetEntityId(Long.parseLong(valuationAndInsuranceDetailsUpdateResource.getCustomerId()), 
				Long.parseLong(valuationAndInsuranceDetailsUpdateResource.getAssetsEntityId()));
				if(!creditAppCollateralDetails.isPresent())
				throw new ValidateRecordException(environment.getProperty("invalid.creApp.info"),"customerId");
		
		//check for true thing
		
		//cannot perform update fro approved
//		if(!creditAppCollateralDetails.get().getA)
//			throw new ValidateRecordException(environment.getProperty("invalid.creApp.info"),"customerId");
		
		if(valuationAndInsuranceDetailsUpdateResource.getValuationDetails()!=null) {
		validateAndUpdateValuationDetails(tenantId, createdUser,valuationAndInsuranceDetailsUpdateResource.getValuationDetails(), id, creditAppCollateralDetails.get());
		}
//		LoggerRequest.getInstance().logInfo("ValuationAndInsuranceDetails******************************** Validate Insurance Details *****************************************");
//		if(valuationAndInsuranceDetailsUpdateResource.getInsuranceDetails()!=null) {
//			valuationAndInsuranceDetailsUpdateResource.getInsuranceDetails().setInsuId(id.toString());
//			validateInsuranceDetails(tenantId, createdUser,valuationAndInsuranceDetailsUpdateResource.getInsuranceDetails().getAssetsEntity(),valuationAndInsuranceDetailsUpdateResource.getInsuranceDetails(),
//					ActionType.UPDATE ,valuationAndInsuranceDetailsUpdateResource.getInsuranceDetails().getVersion());
//		}
//		if(valuationAndInsuranceDetailsUpdateResource.getValuationDetails()!=null && valuationAndInsuranceDetailsUpdateResource.getInsuranceDetails()!=null)
//			throw new ValidateRecordException(environment.getProperty("no-record-found-update"),"message");
	}

//	private void validateInsuranceDetails(String tenantId, String createdUser, String assetsEntityId, InsuranceDetailsAddResource insuranceDetails, 
//			ActionType actionType, String version) {
//		
//		//lookForExistingInsuranceDetails
//		if(actionType.equals(ActionType.SAVE)) {
//			
//		validateService.validateAssetEntityInsuDetails(tenantId, null, Long.parseLong(assetsEntityId), ActionType.SAVE, insuranceDetails.getStatus());
//			
//		}else if(actionType.equals(ActionType.UPDATE)) {
//			
//			if(insuranceDetails.getStatus().equalsIgnoreCase(CommonStatus.ACTIVE.toString()))
//			   validateService.validateAssetEntityInsuDetails(tenantId, insuranceDetails.getInsuId(), Long.parseLong(assetsEntityId), ActionType.UPDATE, insuranceDetails.getStatus());
//			
//		}
//		//Validate Asset Entity
//		LoggerRequest.getInstance().logInfo("credit-appraisal-collateral-details********************************Validate Asset Entity****************");
//		validateService.validateAssetEntity(tenantId, Long.parseLong(assetsEntityId), ServicePoint.COL_INSURANCE_DETAILS);
//			
//		insuranceDetails.setAssetsEntity(assetsEntityId);
//
//		if(insuranceDetails.getInsuranceSubType().equalsIgnoreCase("COVERNOTE")) {
//			
//			if(insuranceDetails.getCoverNoteNumber()==null || insuranceDetails.getCoverNoteNumber().isEmpty()) {
//				throw new ValidateRecordException(environment.getProperty("coverNoteNumber.not-null"),"coverNoteNumber");
//			}
//			if(insuranceDetails.getCoverNotePeriodFrom()==null || insuranceDetails.getCoverNotePeriodFrom().isEmpty()) {
//				throw new ValidateRecordException(environment.getProperty("coverNotePeriodFrom.not-null"),"coverNotePeriodFrom");
//			}
//			if(insuranceDetails.getCoverNotePeriodTo()==null || insuranceDetails.getCoverNotePeriodTo().isEmpty()) {
//				throw new ValidateRecordException(environment.getProperty("coverNotePeriodTo.not-null"),"coverNotePeriodTo");
//			}
//			if(insuranceDetails.getCoverNoteStatus()==null || insuranceDetails.getCoverNoteStatus().isEmpty()) {
//				throw new ValidateRecordException(environment.getProperty("coverNoteStatus.not-null"),"coverNoteStatus");
//			}
//			
//			Date coverNotePeriodFrom =formatDate(insuranceDetails.getCoverNotePeriodFrom());
//			Date coverNotePeriodTo =formatDate(insuranceDetails.getCoverNotePeriodTo());
//			
//			if( coverNotePeriodTo!=null && (coverNotePeriodTo.before(new Date())|| coverNotePeriodTo.equals(new Date()))) {
//				throw new ValidateRecordException(environment.getProperty("coverNotePeriodTo.past-date"),"coverNotePeriodTo");				
//			}
//			
//			if( coverNotePeriodTo!=null && coverNotePeriodTo.before(coverNotePeriodFrom)) {
//				throw new ValidateRecordException(environment.getProperty("coverNotePeriodTo.before-from-date"),"coverNotePeriodTo");				
//			}
//			
//			insuranceDetails.setCoverNoteNumber(insuranceDetails.getCoverNoteNumber());
//			insuranceDetails.setCoverNotePeriodFrom(insuranceDetails.getCoverNotePeriodFrom());
//			insuranceDetails.setCoverNotePeriodTo(insuranceDetails.getCoverNotePeriodTo());
//			insuranceDetails.setCoverNoteStatus(insuranceDetails.getCoverNoteStatus());
//			
//		}else if(insuranceDetails.getInsuranceSubType().equalsIgnoreCase("POLICY")) {
//			
//			if(insuranceDetails.getPolicyNo()==null || insuranceDetails.getPolicyNo().isEmpty()) {
//				throw new ValidateRecordException(environment.getProperty("policyNo.not-null"),"policyNo");
//			}
//			if(insuranceDetails.getPolicyCoverPeriodFrom()==null || insuranceDetails.getPolicyCoverPeriodFrom().isEmpty()) {
//				throw new ValidateRecordException(environment.getProperty("policyCoverPeriodFrom.not-null"),"policyCoverPeriodFrom");
//			}
//			if(insuranceDetails.getPolicyCoverPeriodTo()==null || insuranceDetails.getPolicyCoverPeriodTo().isEmpty()) {
//				throw new ValidateRecordException(environment.getProperty("policyCoverPeriodTo.not-null"),"policyCoverPeriodTo");
//			}
//			
//			Date policyCoverPeriodFrom =formatDate(insuranceDetails.getPolicyCoverPeriodFrom());
//			Date policyCoverPeriodTo =formatDate(insuranceDetails.getPolicyCoverPeriodTo());
//			
//			
//			if( policyCoverPeriodTo!=null && (policyCoverPeriodTo.before(new Date())|| policyCoverPeriodTo.equals(new Date()))) {
//				throw new ValidateRecordException(environment.getProperty("policyCoverPeriodTo.past-date"),"policyCoverPeriodTo");				
//			}
//			
//			if( policyCoverPeriodTo!=null && policyCoverPeriodTo.before(policyCoverPeriodFrom)) {
//				throw new ValidateRecordException(environment.getProperty("policyCoverPeriodTo.before-from-date"),"policyCoverPeriodTo");				
//			}
//			
//			insuranceDetails.setPolicyNo(insuranceDetails.getPolicyNo());
//			insuranceDetails.setPolicyCoverPeriodFrom(insuranceDetails.getPolicyCoverPeriodFrom());
//			insuranceDetails.setPolicyCoverPeriodTo(insuranceDetails.getPolicyCoverPeriodTo());
//		}
//		
//		//Validate Currency
//		validateService.validateCurrency(tenantId, insuranceDetails.getApplicableCurrencyId(), comnCurrencyUrl, insuranceDetails.getApplicableCurrencyCode(), CurencyResponse.class);
//			
//		insuranceDetails.setPaidAmount("0.00");
//		
//		insuranceDetails.setPaymentStatus("PENDING");
//		insuranceDetails.setPaidBy("CUSTOMER");
//		
//		insuranceDetails.setOutstandingAmount("0.00");
//		insuranceDetails.setTaxes("0.00");
//		insuranceDetails.setCharges("0.00");
//		
//		if(actionType.equals(ActionType.SAVE)) {
//		LoggerRequest.getInstance().logInfo("ValuationAndInsuranceDetails******************************** Integrate with Collateral and save insu Details *****************************************");
//		Long valuationId = validateService.insuranceDetAddColInteregation(tenantId, createdUser, insuranceDetails, ActionType.SAVE);	
//		}else if(actionType.equals(ActionType.UPDATE)) {
//			insuranceDetails.setVersion(version);
//		Long valuationId1 = validateService.insuranceDetAddColInteregation(tenantId, createdUser, insuranceDetails, ActionType.UPDATE);	
//		
//		}
//		
//	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	private String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        return format.format(date);
    }
	
	/**
	 * String value convert to Date
	 * @param String value
     * @return Date value
	 */
	private Date formatDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

//	@Override
//	public ValuationAndInsuranceDetListResponseResource findByAssetsEntityId(String tenantId, Long assetEntityId) {
//
//	}

	@Override
	public ValuationAndInsuranceDetListResponseResource findValuationAndInsuranceDetails(String tenantId,
			Long customerid, Long assetEntityId) {
		ValuationAndInsuranceDetListResponseResource detailList = new ValuationAndInsuranceDetListResponseResource();
		List<ValuationDetailsRequestResource> valuationList = new ArrayList<>();
		//List<InsuranceDetailsRequestResource> insuList = new ArrayList<>();

	    
		//get insuDetails
	    //ValuationAndInsuranceDetListResponseResource insuDetailsList = validateService.getInsuranceForasset(tenantId, assetEntityId);
	    
	    //get final value
	    Optional<CreditAppCollateralDetail> creditAppCollateralDetails = creditAppCollateralDetailsRepository.findByCustomerIdAndAssetEntityId(customerid,assetEntityId);
	    if(creditAppCollateralDetails.isPresent()) {
			//get valuations
		    ValuationAndInsuranceDetListResponseResource valuationDetailsList = validateService.getValuationsForasset(tenantId, assetEntityId);
		    
	    	if(valuationDetailsList.getValuationDetails().size()>0) {
		    	for(ValuationDetailsRequestResource valuationDetails : valuationDetailsList.getValuationDetails()) {
		    		if(creditAppCollateralDetails.get().getFinalValuationId()!=null) {
			    		if(creditAppCollateralDetails.get().getFinalValuationId().equals(Long.parseLong(valuationDetails.getId())))
			    		valuationDetails.setFinalValuation("TRUE");
		    		}else 
		    			valuationDetails.setFinalValuation("FALSE");
		    		
		    	valuationList.add(valuationDetails);
		    	}
		    	
		    	detailList.setValuationDetails(valuationList);
		    }
	    
		} else {
			return null;
		}
	     
	    
	    
//	    if(insuDetailsList.getInsuranceDetails()!=null && insuDetailsList.getInsuranceDetails().size()>0) {
//	    	for( InsuranceDetailsRequestResource insuranceDetailsRequestRes :insuDetailsList.getInsuranceDetails()) {
//	    		insuList.add(insuranceDetailsRequestRes);
//	    	}
//	    	detailList.setInsuranceDetails(insuList);
//	    }
	    
		return detailList;
	}

}