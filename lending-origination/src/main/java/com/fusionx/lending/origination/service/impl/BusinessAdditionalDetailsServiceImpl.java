package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 	Business Additional Details Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.Constants;
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessAdditionalDetails;
import com.fusionx.lending.origination.domain.BusinessAssetDetails;
import com.fusionx.lending.origination.domain.BusinessClearanceDetails;
import com.fusionx.lending.origination.domain.BusinessDocumentDetails;
import com.fusionx.lending.origination.domain.BusinessEmploymentDetails;
import com.fusionx.lending.origination.domain.BusinessRiskDetails;
import com.fusionx.lending.origination.domain.BusinessRiskType;
import com.fusionx.lending.origination.domain.BusinessSubType;
import com.fusionx.lending.origination.domain.BusinessType;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.domain.LinkedPerson;
import com.fusionx.lending.origination.domain.RiskAuthorities;
import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.domain.RiskRatingLevels;
import com.fusionx.lending.origination.enums.CommonListReferenceCode;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.SourceTypeEnum;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessAdditionalDetailsRepository;
import com.fusionx.lending.origination.repository.BusinessAssetDetailsRepository;
import com.fusionx.lending.origination.repository.BusinessClearanceDetailsRepository;
import com.fusionx.lending.origination.repository.BusinessDocumentDetailsRepository;
import com.fusionx.lending.origination.repository.BusinessEmploymentDetailsRepository;
import com.fusionx.lending.origination.repository.BusinessRiskDetailsRepository;
import com.fusionx.lending.origination.repository.BusinessRiskTypeRepository;
import com.fusionx.lending.origination.repository.BusinessSubTypeRepository;
import com.fusionx.lending.origination.repository.BusinessTypeRepository;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.repository.LinkedPersonRepository;
import com.fusionx.lending.origination.repository.RiskAuthoritiesRepository;
import com.fusionx.lending.origination.repository.RiskGradingRepository;
import com.fusionx.lending.origination.repository.RiskRatingLevelsRepository;
import com.fusionx.lending.origination.resource.DocumentAddResource;
import com.fusionx.lending.origination.resource.DocumentUpdateResource;
import com.fusionx.lending.origination.resource.PerCommonList;
import com.fusionx.lending.origination.resource.BusinessAdditionalDetailsAddResource;
import com.fusionx.lending.origination.resource.BusinessAdditionalDetailsUpdateResource;
import com.fusionx.lending.origination.resource.BusinessAssetDetailResource;
import com.fusionx.lending.origination.resource.BusinessClearanceDetailResource;
import com.fusionx.lending.origination.resource.BusinessEmploymentDetailResource;
import com.fusionx.lending.origination.resource.BusinessRiskDetailResource;
import com.fusionx.lending.origination.service.BusinessAdditionalDetailsService;
import com.fusionx.lending.origination.service.ValidateService;


@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessAdditionalDetailsServiceImpl extends MessagePropertyBase implements BusinessAdditionalDetailsService{
	
	
	private BusinessAdditionalDetailsRepository businessAdditionalDetailsRepository;
	
	@Autowired
	public void setBusinessAdditionalDetailsRepository(BusinessAdditionalDetailsRepository businessAdditionalDetailsRepository) {
		this.businessAdditionalDetailsRepository = businessAdditionalDetailsRepository;
	}
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	@Autowired
	private BusinessSubTypeRepository businessSubTypeRepository;
	
	@Autowired
	private CommonListRepository commonListRepository;
	
	@Autowired
	private BusinessDocumentDetailsRepository businessDocumentDetailsRepository;
	
	@Autowired
	private BusinessRiskTypeRepository businessRiskTypeRepository;
	
	@Autowired
	private BusinessRiskDetailsRepository businessRiskDetailsRepository;
	
	@Autowired
	private BusinessAssetDetailsRepository businessAssetDetailsRepository;
	
	@Autowired
	private BusinessEmploymentDetailsRepository businessEmploymentDetailsRepository;
	
	@Autowired
	private BusinessClearanceDetailsRepository businessClearanceDetailsRepository;

	private LeadInfoRepository leadInfoRepository;
	
	@Autowired
	public void setLeadInfoRepository(LeadInfoRepository leadInfoRepository) {
		this.leadInfoRepository = leadInfoRepository;
	}
	
	private CustomerRepository customerRepository;
	
	@Autowired
	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	private LinkedPersonRepository linkedPersonRepository;
	
	@Autowired
	public void setLinkedPersonRepository(LinkedPersonRepository linkedPersonRepository) {
		this.linkedPersonRepository = linkedPersonRepository;
	}
	
	@Autowired
	private RiskGradingRepository riskGradingRepository;
	
	@Autowired
	private RiskRatingLevelsRepository riskRatingLevelsRepository;
	
	@Autowired
	private RiskAuthoritiesRepository riskAuthoritiesRepository;

	@Override
	public Optional<BusinessAdditionalDetails> findById(String tenantId ,Long id) {
		Optional<BusinessAdditionalDetails> isPresentBusinessAdditionalDetails = businessAdditionalDetailsRepository.findById(id);
		
		if (isPresentBusinessAdditionalDetails.isPresent()) {
			return Optional.ofNullable(setBusinessAdditionalDetails(tenantId,isPresentBusinessAdditionalDetails.get()));
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<BusinessAdditionalDetails> findAll(String tenantId) {
		List<BusinessAdditionalDetails> businessAdditionalDetailsList = businessAdditionalDetailsRepository.findAll();
		
		for (BusinessAdditionalDetails incomeSourceDetail : businessAdditionalDetailsList) {
			setBusinessAdditionalDetails(tenantId, incomeSourceDetail);
		}
		return businessAdditionalDetailsList;
	}

	@Override
	public List<BusinessAdditionalDetails> findByStatus(String tenantId,String status) {
		
		List<BusinessAdditionalDetails> businessAdditionalDetailsList = businessAdditionalDetailsRepository.findByStatus(CommonStatus.valueOf(status));
		
		for (BusinessAdditionalDetails incomeSourceDetail : businessAdditionalDetailsList) {
			setBusinessAdditionalDetails(tenantId ,incomeSourceDetail);
		}
		return 	businessAdditionalDetailsList;
	}
	
	@Override
	public BusinessAdditionalDetails save(String tenantId,BusinessAdditionalDetailsAddResource businessAdditionalDetailsAddResource) {
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
	      
        	Date operationStartDate = null;
	        Date businessRegiDate = null;
	        Optional<LeadInfo> isPresentLeadInfo =  Optional.empty();
	        Optional<Customer> isPresentCustomer = Optional.empty();
	        Optional<LinkedPerson> isPresentLinkedPerson = Optional.empty();
            
	        BusinessType businessType = validateBusinessType(validateService.stringToLong(businessAdditionalDetailsAddResource.getBusinessTypeId()) , businessAdditionalDetailsAddResource.getBusinessTypeName());
            BusinessSubType businessSubType = validateBusinessSubType(validateService.stringToLong(businessAdditionalDetailsAddResource.getBusinessSubTypeId()) , businessAdditionalDetailsAddResource.getBusinessSubTypeName());
            CommonList ownerShipCommonItem = validateOwnership(validateService.stringToLong(businessAdditionalDetailsAddResource.getOwnershipId()) , businessAdditionalDetailsAddResource.getOwnershipName());
            CommonList businessSizeCommonItem = validateBusinessSize(validateService.stringToLong(businessAdditionalDetailsAddResource.getBusinessSizeId()) , businessAdditionalDetailsAddResource.getBusinessSizeName());
	        
            if(businessAdditionalDetailsAddResource.getLeadId() != null && !businessAdditionalDetailsAddResource.getLeadId().isEmpty()) {	
	            isPresentLeadInfo = leadInfoRepository.findById(validateService.stringToLong(businessAdditionalDetailsAddResource.getLeadId()));
		        if(!isPresentLeadInfo.isPresent())
		        	throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "leadId");
			}
            
            if(businessAdditionalDetailsAddResource.getCustomerId() != null && !businessAdditionalDetailsAddResource.getCustomerId().isEmpty()) {	
		        isPresentCustomer = customerRepository.findById(validateService.stringToLong(businessAdditionalDetailsAddResource.getCustomerId()));
		        if(!isPresentCustomer.isPresent())
		        	throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "customerId");
			}
            
	        if(businessAdditionalDetailsAddResource.getLinkedPersonId() != null && !businessAdditionalDetailsAddResource.getLinkedPersonId().isEmpty()) {	
		        isPresentLinkedPerson = linkedPersonRepository.findById(validateService.stringToLong(businessAdditionalDetailsAddResource.getLinkedPersonId()));
		        if(!isPresentLinkedPerson.isPresent())
		        	throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "linkedPersonId");
	        }
	        
            if(businessAdditionalDetailsAddResource.getBusiOpertaionStartDate() != null && !businessAdditionalDetailsAddResource.getBusiOpertaionStartDate().isEmpty()) {
	             operationStartDate=validateService.formatDate(businessAdditionalDetailsAddResource.getBusiOpertaionStartDate());
	    		if( operationStartDate!=null && operationStartDate.after(new Date())) 
	    			throw new ValidateRecordException(environment.getProperty(FUTURE_DATE_NOT_ALLOWED),"busiOpertaionStartDate");
            }
            if(businessAdditionalDetailsAddResource.getBusinessRegiDate() != null && !businessAdditionalDetailsAddResource.getBusinessRegiDate().isEmpty()) {
	    		 businessRegiDate=validateService.formatDate(businessAdditionalDetailsAddResource.getBusinessRegiDate());
	     		if( businessRegiDate!=null && businessRegiDate.after(new Date())) 
	     			throw new ValidateRecordException(environment.getProperty(FUTURE_DATE_NOT_ALLOWED),"businessRegiDate");
	        }
    		
	        BusinessAdditionalDetails businessAdditionalDetails = new BusinessAdditionalDetails();
	        businessAdditionalDetails.setTenantId(tenantId);
	        businessAdditionalDetails.setBusinessType(businessType);
	        businessAdditionalDetails.setBusinessSubType(businessSubType);
	        businessAdditionalDetails.setOwnership(ownerShipCommonItem);
	        businessAdditionalDetails.setBusinessName(businessAdditionalDetailsAddResource.getBusinessName());
	        businessAdditionalDetails.setBusinessSize(businessSizeCommonItem);
	        businessAdditionalDetails.setNoOfYearsInBusiness(businessAdditionalDetailsAddResource.getNoOfYearsInBusiness());
	        businessAdditionalDetails.setBusiOpertaionStartDate(operationStartDate != null ? new Timestamp(operationStartDate.getTime()):null);
	        businessAdditionalDetails.setBusinessRegiNo(businessAdditionalDetailsAddResource.getBusinessRegiNo());
	        businessAdditionalDetails.setBusinessRegiDate(businessRegiDate != null ? new Timestamp(businessRegiDate.getTime()):null);
	        businessAdditionalDetails.setDescription(businessAdditionalDetailsAddResource.getDescription());
	        businessAdditionalDetails.setProfitMargin(businessAdditionalDetailsAddResource.getProfitMargin() != null && !businessAdditionalDetailsAddResource.getProfitMargin().isEmpty() ? validateService.stringToDouble(businessAdditionalDetailsAddResource.getProfitMargin()):null);
	        businessAdditionalDetails.setComment(businessAdditionalDetailsAddResource.getComment());
	        businessAdditionalDetails.setSourceType(businessAdditionalDetailsAddResource.getSourceType() != null && !businessAdditionalDetailsAddResource.getSourceType().isEmpty() ? SourceTypeEnum.valueOf(businessAdditionalDetailsAddResource.getSourceType()):null);
	        businessAdditionalDetails.setNoOfBranches(businessAdditionalDetailsAddResource.getNoOfBranches());
	        businessAdditionalDetails.setSkillsOfKeyPerson(businessAdditionalDetailsAddResource.getSkillsOfKeyPerson());
	        businessAdditionalDetails.setPreviousBusiHistory(businessAdditionalDetailsAddResource.getPreviousBusiHistory());
	        businessAdditionalDetails.setBusiContinuityPlan(businessAdditionalDetailsAddResource.getBusiContinuityPlan());
	        businessAdditionalDetails.setCustomer(isPresentCustomer.isPresent() ? isPresentCustomer.get():null);
	        businessAdditionalDetails.setLead(isPresentLeadInfo.isPresent() ? isPresentLeadInfo.get():null);
	        businessAdditionalDetails.setLinkedPerson(isPresentLinkedPerson.isPresent() ? isPresentLinkedPerson.get():null);
	        businessAdditionalDetails.setStatus(CommonStatus.valueOf(businessAdditionalDetailsAddResource.getStatus()));
	        businessAdditionalDetails.setSyncTs(validateService.getSyncTs());
	        businessAdditionalDetails.setCreatedDate(validateService.getCreateOrModifyDate());
	        businessAdditionalDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	        businessAdditionalDetails = businessAdditionalDetailsRepository.saveAndFlush(businessAdditionalDetails);
	        
	        //Business Document Details Save
	        if(businessAdditionalDetailsAddResource.getBusinessDocumentList() != null && !businessAdditionalDetailsAddResource.getBusinessDocumentList().isEmpty()) {
	        	Integer index=0;
	        	for(DocumentAddResource documentAddResource : businessAdditionalDetailsAddResource.getBusinessDocumentList() ) {				
						saveBusinessDocumentDetails(tenantId,documentAddResource,businessAdditionalDetails, index);
					
					index++;
	        	}
	        }
	        //Business Risk Details Save
	        if(businessAdditionalDetailsAddResource.getBusinessRiskdetailList() != null && !businessAdditionalDetailsAddResource.getBusinessRiskdetailList().isEmpty()) {
	        	Integer index=0;
	        	for(BusinessRiskDetailResource businessRiskDetailResource : businessAdditionalDetailsAddResource.getBusinessRiskdetailList() ) {							
						saveBusinessRiskDetails(tenantId,businessRiskDetailResource,businessAdditionalDetails,index);
					
					index++;
	        	}
	        }
	        //Business Asset Details Save
	        if(businessAdditionalDetailsAddResource.getBusinessAssetdetailList() != null && !businessAdditionalDetailsAddResource.getBusinessAssetdetailList().isEmpty()) {
	        	Integer index=0;
	        	for(BusinessAssetDetailResource businessAssetDetailResource : businessAdditionalDetailsAddResource.getBusinessAssetdetailList() ) {							
						saveBusinessAssetDetails(tenantId,businessAssetDetailResource,businessAdditionalDetails,index);
					
					index++;
	        	}
	        }  
	        //Business Employment Details Save
	        if(businessAdditionalDetailsAddResource.getBusinessEmploymentdetailList() != null && !businessAdditionalDetailsAddResource.getBusinessEmploymentdetailList().isEmpty()) {
	        	Integer index=0;
	        	for(BusinessEmploymentDetailResource businessEmploymentDetailResource : businessAdditionalDetailsAddResource.getBusinessEmploymentdetailList() ) {							
						saveBusinessEmploymentDetails(tenantId,businessEmploymentDetailResource,businessAdditionalDetails,index);
					
					index++;
	        	}
	        }   
	        //Business Clearance Details Save
	        if(businessAdditionalDetailsAddResource.getBusinessClearancedetailList() != null && !businessAdditionalDetailsAddResource.getBusinessClearancedetailList().isEmpty()) {
	        	Integer index=0;
	        	for(BusinessClearanceDetailResource businessClearanceDetailResource : businessAdditionalDetailsAddResource.getBusinessClearancedetailList() ) {							
						saveBusinessClearanceDetails(tenantId,businessClearanceDetailResource,businessAdditionalDetails,index);
					
					index++;
	        	}
	        }   
	        
        return businessAdditionalDetails;	 
	}	
	
	public BusinessType validateBusinessType(Long id, String name) {
		
		Optional<BusinessType> isPresentBusinessType = businessTypeRepository.findById(id);
		if(!isPresentBusinessType.isPresent())
			throw new ValidateRecordException(environment.getProperty(NOT_FOUND), "businessTypeId");
		
		else if(!CommonStatus.ACTIVE.toString().equalsIgnoreCase(isPresentBusinessType.get().getStatus()))
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "businessTypeId");
		
		else if(!isPresentBusinessType.get().getName().equalsIgnoreCase(name)) 
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "businessTypeName");
		
		return isPresentBusinessType.get();
	}
	
	public BusinessSubType validateBusinessSubType(Long id, String name) {
		
		Optional<BusinessSubType> isPresentBusinessSubType = businessSubTypeRepository.findById(id);
		if(!isPresentBusinessSubType.isPresent())
			throw new ValidateRecordException(environment.getProperty(NOT_FOUND), "businessSubTypeId");
		
		else if(!CommonStatus.ACTIVE.toString().equalsIgnoreCase(isPresentBusinessSubType.get().getStatus()))
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "businessSubTypeId");
		
		else if(!isPresentBusinessSubType.get().getName().equalsIgnoreCase(name)) 
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "businessSubTypeName");
		
		return isPresentBusinessSubType.get();
	}

	public CommonList validateOwnership(Long id, String name) {
		
		Optional<CommonList> commonListItem = commonListRepository.findById(id);
		if(!commonListItem.isPresent())
			throw new ValidateRecordException(environment.getProperty(NOT_FOUND), "ownershipId");
		
		else if(!CommonStatus.ACTIVE.toString().equalsIgnoreCase(commonListItem.get().getStatus()))
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "ownershipId");
	
		else if(!CommonListReferenceCode.OWNERSHIP.toString().equalsIgnoreCase(commonListItem.get().getReferenceCode()) ||!commonListItem.get().getName().equalsIgnoreCase(name)) 
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "ownershipName");	
		
		return commonListItem.get();
	}
	
	public CommonList validateAssetOwnershipList(Long id, String name, Integer index) {
		
		Optional<CommonList> commonListItem = commonListRepository.findById(id);
		if(!commonListItem.isPresent())
			throw new DetailListValidateException(environment.getProperty(NOT_FOUND), ServiceEntity.ASSET_OWNERSHIP_ID, ServicePoint.BUSINESS_ASSET_DETAILS, index);
		
		else if(!CommonStatus.ACTIVE.toString().equalsIgnoreCase(commonListItem.get().getStatus()))
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.ASSET_OWNERSHIP_ID, ServicePoint.BUSINESS_ASSET_DETAILS, index);
	
		else if(!CommonListReferenceCode.ASSET_OWNERSHIP.toString().equalsIgnoreCase(commonListItem.get().getReferenceCode()) ||!commonListItem.get().getName().equalsIgnoreCase(name)) 
			throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.ASSET_OWNERSHIP_NAME, ServicePoint.BUSINESS_ASSET_DETAILS, index);
		
		return commonListItem.get();
	}
	
	public CommonList validateAssetTypeList(Long id, String name, Integer index) {
		
		Optional<CommonList> commonListItem = commonListRepository.findById(id);
		if(!commonListItem.isPresent())
			throw new DetailListValidateException(environment.getProperty(NOT_FOUND), ServiceEntity.ASSET_TYPE_ID, ServicePoint.BUSINESS_ASSET_DETAILS, index);
		
		else if(!CommonStatus.ACTIVE.toString().equalsIgnoreCase(commonListItem.get().getStatus()))
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.ASSET_TYPE_ID, ServicePoint.BUSINESS_ASSET_DETAILS, index);
	
		else if(!CommonListReferenceCode.ASSET_TYPE.toString().equalsIgnoreCase(commonListItem.get().getReferenceCode()) ||!commonListItem.get().getName().equalsIgnoreCase(name)) 
			throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.ASSET_TYPE_NAME, ServicePoint.BUSINESS_ASSET_DETAILS, index);
		
		return commonListItem.get();
	}
	
	public CommonList validateBusinessSize(Long id, String name) {
		
		Optional<CommonList> commonListItem = commonListRepository.findById(id);
		if(!commonListItem.isPresent())
			throw new ValidateRecordException(environment.getProperty(NOT_FOUND), "businessSizeId");
		
		else if(!CommonStatus.ACTIVE.toString().equalsIgnoreCase(commonListItem.get().getStatus()))
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "businessSizeId");
	
		else if(!CommonListReferenceCode.BUSINESS_SIZE.toString().equalsIgnoreCase(commonListItem.get().getReferenceCode()) ||!commonListItem.get().getName().equalsIgnoreCase(name)) 
				throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "businessSizeName");	
		
		return commonListItem.get();	
	}
	
	public BusinessRiskType validateRiskType(Long id, String name, Integer index) {
		
		Optional<BusinessRiskType> isPresentRiskType = businessRiskTypeRepository.findById(id);
		if(!isPresentRiskType.isPresent())
			throw new DetailListValidateException(environment.getProperty(NOT_FOUND), ServiceEntity.RISK_TYPE_ID, ServicePoint.BUSINESS_RISK_DETAILS, index);
		
		else if(!CommonStatus.ACTIVE.toString().equalsIgnoreCase(isPresentRiskType.get().getStatus()))
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.RISK_TYPE_ID, ServicePoint.BUSINESS_RISK_DETAILS, index);
		
		else if(!isPresentRiskType.get().getName().equalsIgnoreCase(name)) 
			throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.RISK_TYPE_NAME, ServicePoint.BUSINESS_RISK_DETAILS, index);
		
		return isPresentRiskType.get();
	}
	
	public RiskGrading validateRiskGrading(Long id, String name, Integer index) {
			
		Optional<RiskGrading> isPresentRiskGrading = riskGradingRepository.findById(id);
		if(!isPresentRiskGrading.isPresent())
			throw new DetailListValidateException(environment.getProperty(NOT_FOUND), ServiceEntity.RISK_GRADING_ID, ServicePoint.BUSINESS_RISK_DETAILS, index);
			
		else if(!CommonStatus.ACTIVE.toString().equalsIgnoreCase(isPresentRiskGrading.get().getStatus()))
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.RISK_GRADING_ID, ServicePoint.BUSINESS_RISK_DETAILS, index);
			
		else if(!isPresentRiskGrading.get().getName().equalsIgnoreCase(name)) 
			throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.RISK_GRADING_NAME, ServicePoint.BUSINESS_RISK_DETAILS, index);
			
		return isPresentRiskGrading.get();
	}
	

	public RiskRatingLevels validateRiskLevel(Long id, String type, Integer index) {
		
		Optional<RiskRatingLevels> isPresentRiskLevel = riskRatingLevelsRepository.findById(id);
		if(!isPresentRiskLevel.isPresent())
			throw new DetailListValidateException(environment.getProperty(NOT_FOUND), ServiceEntity.RISK_LEVEL_ID, ServicePoint.BUSINESS_RISK_DETAILS, index);
		
		else if(!CommonStatus.ACTIVE.toString().equalsIgnoreCase(isPresentRiskLevel.get().getStatus().toString()))
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.RISK_LEVEL_ID, ServicePoint.BUSINESS_RISK_DETAILS, index);
		
		else if(!isPresentRiskLevel.get().getRiskRatingType().equalsIgnoreCase(type))
			throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.RISK_LEVEL_NAME, ServicePoint.BUSINESS_RISK_DETAILS, index);
		
		return isPresentRiskLevel.get();
	}


	public RiskAuthorities validateRiskAuthority(Long id, String name, Integer index) {
		
		Optional<RiskAuthorities> isPresentRiskAuthority = riskAuthoritiesRepository.findById(id);
		if(!isPresentRiskAuthority.isPresent())
			throw new DetailListValidateException(environment.getProperty(NOT_FOUND), ServiceEntity.RISK_AUTHORITY_ID, ServicePoint.BUSINESS_RISK_DETAILS, index);
		
		else if(!CommonStatus.ACTIVE.toString().equalsIgnoreCase(isPresentRiskAuthority.get().getStatus().toString()))
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.RISK_AUTHORITY_ID, ServicePoint.BUSINESS_RISK_DETAILS, index);
		
		else if(!isPresentRiskAuthority.get().getName().equalsIgnoreCase(name)) 
			throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.RISK_AUTHORITY_NAME, ServicePoint.BUSINESS_RISK_DETAILS, index);
		
		return isPresentRiskAuthority.get();
	}

	public CommonList validateClearanceTypeList(Long id, String name,Integer index) {
		
		Optional<CommonList> commonListItem = commonListRepository.findById(id);
		if(!commonListItem.isPresent())
			throw new DetailListValidateException(environment.getProperty(NOT_FOUND), ServiceEntity.CLEARANCE_TYPE_ID, ServicePoint.BUSINESS_CLEARANCE_DETAILS, index);
		
		else if(!CommonStatus.ACTIVE.toString().equalsIgnoreCase(commonListItem.get().getStatus()))
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.CLEARANCE_TYPE_ID, ServicePoint.BUSINESS_CLEARANCE_DETAILS, index);
	
		else if(!CommonListReferenceCode.CLEARENCE_TYPE.toString().equalsIgnoreCase(commonListItem.get().getReferenceCode()) ||!commonListItem.get().getName().equalsIgnoreCase(name)) 
			throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.CLEARANCE_TYPE_NAME, ServicePoint.BUSINESS_CLEARANCE_DETAILS, index);
		
		return commonListItem.get();
	}
	
	public CommonList validateClearanceAuthorityList(Long id, String name,Integer index) {
		
		Optional<CommonList> commonListItem = commonListRepository.findById(id);
		if(!commonListItem.isPresent())
			throw new DetailListValidateException(environment.getProperty(NOT_FOUND), ServiceEntity.CLEARANCE_AUTHORITY_ID, ServicePoint.BUSINESS_CLEARANCE_DETAILS, index);
		
		else if(!CommonStatus.ACTIVE.toString().equalsIgnoreCase(commonListItem.get().getStatus()))
			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.CLEARANCE_AUTHORITY_ID, ServicePoint.BUSINESS_CLEARANCE_DETAILS, index);
	
		else if(!CommonListReferenceCode.CLARENCE_AUTHORITY.toString().equalsIgnoreCase(commonListItem.get().getReferenceCode()) ||!commonListItem.get().getName().equalsIgnoreCase(name)) 
			throw new DetailListValidateException(environment.getProperty(COMMON_NOT_MATCH), ServiceEntity.CLEARANCE_AUTHORITY_NAME, ServicePoint.BUSINESS_CLEARANCE_DETAILS, index);
		
		return commonListItem.get();	
	}
	
	public void saveBusinessDocumentDetails(String tenantId, DocumentAddResource documentAddResource , BusinessAdditionalDetails businessAdditionalDetails,Integer index) {
		
		validateService.validateDocument(tenantId, documentAddResource.getDocumentId(),documentAddResource.getDocumentName(), ServicePoint.BUSINESS_DOCUMENT_DETAILS,Constants.ORIGIN_CUSTOMER, index);
		
		Optional<BusinessDocumentDetails> isPresentBusinessDocumentDetail = businessDocumentDetailsRepository.findByBusinessAdditionalDtlIdAndDocumentIdAndStatus(businessAdditionalDetails.getId(), validateService.stringToLong(documentAddResource.getDocumentId()), CommonStatus.ACTIVE);
		if(isPresentBusinessDocumentDetail.isPresent()) {
			throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.DOCUMENT_ID, ServicePoint.BUSINESS_DOCUMENT_DETAILS, index);					
		}	
		
			BusinessDocumentDetails businessDocumentDetails = new BusinessDocumentDetails();
			businessDocumentDetails.setTenantId(tenantId);
			businessDocumentDetails.setDocumentId(validateService.stringToLong(documentAddResource.getDocumentId()));
			businessDocumentDetails.setBusinessAdditionalDtl(businessAdditionalDetails);
			businessDocumentDetails.setStatus(CommonStatus.valueOf(documentAddResource.getStatus()));
			businessDocumentDetails.setCreatedDate(validateService.getCreateOrModifyDate());
			businessDocumentDetails.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
			businessDocumentDetails.setSyncTs(validateService.getSyncTs());
			businessDocumentDetailsRepository.saveAndFlush(businessDocumentDetails);
	}
	
	public void updateBusinessDocumentDetails(String tenantId, DocumentUpdateResource documentUpdateResource , BusinessAdditionalDetails businessAdditionalDetails,Integer index) {
			
		Optional<BusinessDocumentDetails> isPresentBusinessDocumentDetail = businessDocumentDetailsRepository.findByBusinessAdditionalDtlIdAndId(businessAdditionalDetails.getId(), validateService.stringToLong(documentUpdateResource.getId()));
	    if(!isPresentBusinessDocumentDetail.isPresent())
	    	throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.ID, ServicePoint.BUSINESS_DOCUMENT_DETAILS, index);	
	     
		validateService.validateDocument(tenantId, documentUpdateResource.getDocumentId(),documentUpdateResource.getDocumentName(), ServicePoint.BUSINESS_DOCUMENT_DETAILS,Constants.ORIGIN_CUSTOMER, index);
			
		Optional<BusinessDocumentDetails> isDuplicateBusinessDocumentDetail = businessDocumentDetailsRepository.findByBusinessAdditionalDtlIdAndDocumentIdAndStatusAndIdNotIn(
				businessAdditionalDetails.getId(), validateService.stringToLong(documentUpdateResource.getDocumentId()), CommonStatus.ACTIVE ,isPresentBusinessDocumentDetail.get().getId());
		if(isDuplicateBusinessDocumentDetail.isPresent()) {
			throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.DOCUMENT_ID, ServicePoint.BUSINESS_DOCUMENT_DETAILS, index);					
		}	
		
		if(!isPresentBusinessDocumentDetail.get().getVersion().toString().equals(documentUpdateResource.getVersion()))
			throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.BUSINESS_DOCUMENT_DETAILS, index);	
				
			BusinessDocumentDetails businessDocumentDetails = isPresentBusinessDocumentDetail.get();
			businessDocumentDetails.setTenantId(tenantId);
			businessDocumentDetails.setDocumentId(validateService.stringToLong(documentUpdateResource.getDocumentId()));
			businessDocumentDetails.setStatus(CommonStatus.valueOf(documentUpdateResource.getStatus()));
			businessDocumentDetails.setModifiedDate(validateService.getCreateOrModifyDate());
			businessDocumentDetails.setModifiedUser((LogginAuthentcation.getInstance().getUserName()));
			businessDocumentDetails.setSyncTs(validateService.getSyncTs());
			businessDocumentDetailsRepository.saveAndFlush(businessDocumentDetails);
	}
	
	private void saveBusinessRiskDetails(String tenantId, BusinessRiskDetailResource businessRiskDetailResource,
			BusinessAdditionalDetails businessAdditionalDetails,Integer index) {
		
		RiskRatingLevels riskRatingLevel = null;
		RiskAuthorities riskAuthority = null;
		
		BusinessRiskType riskType = validateRiskType(validateService.stringToLong(businessRiskDetailResource.getRiskTypeId()),businessRiskDetailResource.getRiskTypeName(),index);	
		RiskGrading riskGrading = validateRiskGrading(validateService.stringToLong(businessRiskDetailResource.getRiskGradingId()),businessRiskDetailResource.getRiskGradingName(),index);	
		
		if(businessRiskDetailResource.getRiskRatingLevelId() != null && !businessRiskDetailResource.getRiskRatingLevelId().isEmpty()) 
			 riskRatingLevel = validateRiskLevel(validateService.stringToLong(businessRiskDetailResource.getRiskRatingLevelId()),businessRiskDetailResource.getRiskRatingType(),index);	
		
		if(businessRiskDetailResource.getRiskAuthorityId() != null && !businessRiskDetailResource.getRiskAuthorityId().isEmpty()) 
			 riskAuthority = validateRiskAuthority(validateService.stringToLong(businessRiskDetailResource.getRiskAuthorityId()),businessRiskDetailResource.getRiskAuthorityName(),index);		
		
		if(businessRiskDetailResource.getDocumentId() != null && !businessRiskDetailResource.getDocumentId().isEmpty()){
			validateService.validateDocument(tenantId, businessRiskDetailResource.getDocumentId(),businessRiskDetailResource.getDocumentName(), ServicePoint.BUSINESS_RISK_DETAILS,Constants.ORIGIN_CUSTOMER, index);
		}	
		
        Date calculatedDate = validateService.formatDate(businessRiskDetailResource.getCalculatedDate());
    	if( calculatedDate !=null && calculatedDate.after(new Date())) 
    		throw new DetailListValidateException(environment.getProperty(FUTURE_DATE_NOT_ALLOWED),ServiceEntity.CALCULATED_DATE, ServicePoint.BUSINESS_RISK_DETAILS, index);	

    	if(businessRiskDetailResource.getId() != null && !businessRiskDetailResource.getId().isEmpty()) {
    		
    		Optional<BusinessRiskDetails> isPresentBusinessRiskDetail = businessRiskDetailsRepository.findByBusinessAdditionalDtlIdAndId(businessAdditionalDetails.getId(), validateService.stringToLong(businessRiskDetailResource.getId()));
    	    if(!isPresentBusinessRiskDetail.isPresent())
    	    	throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.ID, ServicePoint.BUSINESS_RISK_DETAILS, index);	
    	     
    		
	    	Optional<BusinessRiskDetails> isDuplicateBusinessRiskDetail = businessRiskDetailsRepository.findByBusinessAdditionalDtlIdAndRiskTypeIdAndStatusAndIdNotIn(
	    			businessAdditionalDetails.getId(), validateService.stringToLong(businessRiskDetailResource.getRiskTypeId()), CommonStatus.ACTIVE ,isPresentBusinessRiskDetail.get().getId());
			if(isDuplicateBusinessRiskDetail.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.RISK_TYPE_ID, ServicePoint.BUSINESS_RISK_DETAILS, index);					
			}
			
			if(!isPresentBusinessRiskDetail.get().getVersion().toString().equals(businessRiskDetailResource.getVersion()))
				throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.BUSINESS_RISK_DETAILS, index);	
			
				BusinessRiskDetails businessRiskDetail = isPresentBusinessRiskDetail.get();
				businessRiskDetail.setTenantId(tenantId);
				businessRiskDetail.setRiskType(riskType);
				businessRiskDetail.setRiskGrading(riskGrading);
				businessRiskDetail.setRiskRatingLevel(riskRatingLevel);
				businessRiskDetail.setRiskAuthority(riskAuthority);
				businessRiskDetail.setCalculatedDate(calculatedDate != null ? new Timestamp(calculatedDate.getTime()):null);
				businessRiskDetail.setCertificatedDetails(businessRiskDetailResource.getCertificatedDetails());
				businessRiskDetail.setDocumentId(businessRiskDetailResource.getDocumentId() != null && !businessRiskDetailResource.getDocumentId().isEmpty() ? validateService.stringToLong(businessRiskDetailResource.getDocumentId()):null);
				businessRiskDetail.setStatus(CommonStatus.valueOf(businessRiskDetailResource.getStatus()));
				businessRiskDetail.setModifiedDate(validateService.getCreateOrModifyDate());
				businessRiskDetail.setModifiedUser((LogginAuthentcation.getInstance().getUserName()));
				businessRiskDetail.setSyncTs(validateService.getSyncTs());
				businessRiskDetailsRepository.saveAndFlush(businessRiskDetail);	
    	}else {
    		
    		Optional<BusinessRiskDetails> isPresentBusinessRiskDetail = businessRiskDetailsRepository.findByBusinessAdditionalDtlIdAndRiskTypeIdAndStatus(businessAdditionalDetails.getId(), validateService.stringToLong(businessRiskDetailResource.getRiskTypeId()), CommonStatus.ACTIVE);
			if(isPresentBusinessRiskDetail.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.RISK_TYPE_ID, ServicePoint.BUSINESS_RISK_DETAILS, index);					
			}
				
				BusinessRiskDetails businessRiskDetail = new BusinessRiskDetails();
				businessRiskDetail.setTenantId(tenantId);
				businessRiskDetail.setRiskType(riskType);
				businessRiskDetail.setRiskGrading(riskGrading);
				businessRiskDetail.setRiskRatingLevel(riskRatingLevel);
				businessRiskDetail.setRiskAuthority(riskAuthority);
				businessRiskDetail.setCalculatedDate(calculatedDate != null ? new Timestamp(calculatedDate.getTime()):null);
				businessRiskDetail.setCertificatedDetails(businessRiskDetailResource.getCertificatedDetails());
				businessRiskDetail.setDocumentId(businessRiskDetailResource.getDocumentId() != null && !businessRiskDetailResource.getDocumentId().isEmpty() ? validateService.stringToLong(businessRiskDetailResource.getDocumentId()):null);
				businessRiskDetail.setBusinessAdditionalDtl(businessAdditionalDetails);
				businessRiskDetail.setStatus(CommonStatus.valueOf(businessRiskDetailResource.getStatus()));
				businessRiskDetail.setCreatedDate(validateService.getCreateOrModifyDate());
				businessRiskDetail.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
				businessRiskDetail.setSyncTs(validateService.getSyncTs());
				businessRiskDetailsRepository.saveAndFlush(businessRiskDetail);	
    	}
		
	}
	
	private void saveBusinessAssetDetails(String tenantId, BusinessAssetDetailResource businessAssetDetailResource,
			BusinessAdditionalDetails businessAdditionalDetails,Integer index) {
		
		CommonList assetTypeCommonItem = validateAssetTypeList(validateService.stringToLong(businessAssetDetailResource.getAssetTypeId()),businessAssetDetailResource.getAssetTypeName(),index);	

		CommonList ownerShipCommonItem = validateAssetOwnershipList(validateService.stringToLong(businessAssetDetailResource.getOwnershipId()),businessAssetDetailResource.getOwnershipName(),index);	
		
		if(businessAssetDetailResource.getId() != null && !businessAssetDetailResource.getId().isEmpty()) {
    		
    		Optional<BusinessAssetDetails> isPresentBusinessAssetDetail = businessAssetDetailsRepository.findByBusinessAdditionalDtlIdAndId(businessAdditionalDetails.getId(), validateService.stringToLong(businessAssetDetailResource.getId()));
    	    if(!isPresentBusinessAssetDetail.isPresent())
    	    	throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.ID, ServicePoint.BUSINESS_ASSET_DETAILS, index);	
			
			if(!isPresentBusinessAssetDetail.get().getVersion().toString().equals(businessAssetDetailResource.getVersion()))
				throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.BUSINESS_ASSET_DETAILS, index);	
			
				BusinessAssetDetails businessAssetDetails = isPresentBusinessAssetDetail.get();
				businessAssetDetails.setTenantId(tenantId);
				businessAssetDetails.setAssetType(assetTypeCommonItem);
				businessAssetDetails.setOwnership(ownerShipCommonItem);
				businessAssetDetails.setLocation(businessAssetDetailResource.getLocation());
				businessAssetDetails.setDescription(businessAssetDetailResource.getDescription());
				businessAssetDetails.setNoOfAssets(businessAssetDetailResource.getNoOfAssets());
				businessAssetDetails.setStatus(CommonStatus.valueOf(businessAssetDetailResource.getStatus()));
				businessAssetDetails.setModifiedDate(validateService.getCreateOrModifyDate());
				businessAssetDetails.setModifiedUser((LogginAuthentcation.getInstance().getUserName()));
				businessAssetDetails.setSyncTs(validateService.getSyncTs());
				businessAssetDetailsRepository.saveAndFlush(businessAssetDetails);	
		}else {
				BusinessAssetDetails businessAssetDetails = new BusinessAssetDetails();
				businessAssetDetails.setTenantId(tenantId);
				businessAssetDetails.setAssetType(assetTypeCommonItem);
				businessAssetDetails.setOwnership(ownerShipCommonItem);
				businessAssetDetails.setBusinessAdditionalDtl(businessAdditionalDetails);
				businessAssetDetails.setLocation(businessAssetDetailResource.getLocation());
				businessAssetDetails.setDescription(businessAssetDetailResource.getDescription());
				businessAssetDetails.setNoOfAssets(businessAssetDetailResource.getNoOfAssets());
				businessAssetDetails.setStatus(CommonStatus.valueOf(businessAssetDetailResource.getStatus()));
				businessAssetDetails.setCreatedDate(validateService.getCreateOrModifyDate());
				businessAssetDetails.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
				businessAssetDetails.setSyncTs(validateService.getSyncTs());
				businessAssetDetailsRepository.saveAndFlush(businessAssetDetails);	
		}
		
	}
	
	private void saveBusinessEmploymentDetails(String tenantId, BusinessEmploymentDetailResource businessEmploymentDetailResource,
			BusinessAdditionalDetails businessAdditionalDetails,Integer index) {
	
		validateService.validatePersonCommonList(tenantId, businessEmploymentDetailResource.getEmploymentTypeId(), businessEmploymentDetailResource.getEmploymentTypeName(), 
				CommonListReferenceCode.EMPLOYMENTCATEGORY.toString() ,index ,ServiceEntity.EMPLOYMENT_TYPE_ID, ServicePoint.BUSINESS_EMPLOYMENT_DETAILS,null);
		
		if(businessEmploymentDetailResource.getId() != null && !businessEmploymentDetailResource.getId().isEmpty()) {
    		
    		Optional<BusinessEmploymentDetails> isPresentBusinessEmploymentDetail = businessEmploymentDetailsRepository.findByBusinessAdditionalDtlIdAndId(businessAdditionalDetails.getId(), validateService.stringToLong(businessEmploymentDetailResource.getId()));
    	    if(!isPresentBusinessEmploymentDetail.isPresent())
    	    	throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.ID, ServicePoint.BUSINESS_EMPLOYMENT_DETAILS, index);		     
    		
	    	Optional<BusinessEmploymentDetails> isDuplicateBusinessEmploymentDetail = businessEmploymentDetailsRepository.findByBusinessAdditionalDtlIdAndEmploymentTypeIdAndStatusAndIdNotIn(
	    			businessAdditionalDetails.getId(), validateService.stringToLong(businessEmploymentDetailResource.getEmploymentTypeId()), CommonStatus.ACTIVE ,isPresentBusinessEmploymentDetail.get().getId());
			if(isDuplicateBusinessEmploymentDetail.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.EMPLOYMENT_TYPE_ID, ServicePoint.BUSINESS_EMPLOYMENT_DETAILS, index);					
			}
			
			if(!isPresentBusinessEmploymentDetail.get().getVersion().toString().equals(businessEmploymentDetailResource.getVersion()))
				throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.BUSINESS_EMPLOYMENT_DETAILS, index);	
		
			BusinessEmploymentDetails businessEmploymentDetails = isPresentBusinessEmploymentDetail.get();
			businessEmploymentDetails.setTenantId(tenantId);
			businessEmploymentDetails.setEmploymentTypeId(validateService.stringToLong(businessEmploymentDetailResource.getEmploymentTypeId()));
			businessEmploymentDetails.setNoOfEmployees(businessEmploymentDetailResource.getNoOfEmployees());
			businessEmploymentDetails.setStatus(CommonStatus.valueOf(businessEmploymentDetailResource.getStatus()));
			businessEmploymentDetails.setModifiedDate(validateService.getCreateOrModifyDate());
			businessEmploymentDetails.setModifiedUser((LogginAuthentcation.getInstance().getUserName()));
			businessEmploymentDetails.setSyncTs(validateService.getSyncTs());
			businessEmploymentDetailsRepository.saveAndFlush(businessEmploymentDetails);	
			
		}else {	
			Optional<BusinessEmploymentDetails> isPresentBusinessEmploymentDetail = businessEmploymentDetailsRepository.findByBusinessAdditionalDtlIdAndEmploymentTypeIdAndStatus(businessAdditionalDetails.getId(), validateService.stringToLong(businessEmploymentDetailResource.getEmploymentTypeId()), CommonStatus.ACTIVE);
			if(isPresentBusinessEmploymentDetail.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.EMPLOYMENT_TYPE_ID, ServicePoint.BUSINESS_EMPLOYMENT_DETAILS, index);					
			}	
	
			BusinessEmploymentDetails businessEmploymentDetails = new BusinessEmploymentDetails();
			businessEmploymentDetails.setTenantId(tenantId);
			businessEmploymentDetails.setEmploymentTypeId(validateService.stringToLong(businessEmploymentDetailResource.getEmploymentTypeId()));
			businessEmploymentDetails.setNoOfEmployees(businessEmploymentDetailResource.getNoOfEmployees());
			businessEmploymentDetails.setBusinessAdditionalDtl(businessAdditionalDetails);
			businessEmploymentDetails.setStatus(CommonStatus.valueOf(businessEmploymentDetailResource.getStatus()));
			businessEmploymentDetails.setCreatedDate(validateService.getCreateOrModifyDate());
			businessEmploymentDetails.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
			businessEmploymentDetails.setSyncTs(validateService.getSyncTs());
			businessEmploymentDetailsRepository.saveAndFlush(businessEmploymentDetails);	
		}
		
	}
	
	private void saveBusinessClearanceDetails(String tenantId, BusinessClearanceDetailResource businessClearanceDetailResource,
			BusinessAdditionalDetails businessAdditionalDetails,Integer index) {	
		Date obtainDate =null;
		Date expireDate =null;		
		if(businessClearanceDetailResource.getObtainDate() != null && !businessClearanceDetailResource.getObtainDate().isEmpty()) {
			obtainDate = validateService.formatDate(businessClearanceDetailResource.getObtainDate());
            	if( obtainDate !=null && obtainDate.after(new Date())) 
            		throw new DetailListValidateException(environment.getProperty(FUTURE_DATE_NOT_ALLOWED),ServiceEntity.OBTAIN_DATE, ServicePoint.BUSINESS_CLEARANCE_DETAILS, index);
       }
       if(businessClearanceDetailResource.getExpireDate() != null && !businessClearanceDetailResource.getExpireDate().isEmpty()) {
    	   expireDate = validateService.formatDate(businessClearanceDetailResource.getExpireDate());
   		 		if( expireDate!=null && expireDate.before(new Date())) 
   		 			throw new DetailListValidateException(environment.getProperty(PAST_DATE_NOT_ALLOWED),ServiceEntity.EXPIRE_DATE, ServicePoint.BUSINESS_CLEARANCE_DETAILS, index);
       }
       
        CommonList clearanceTypeCommonItem = validateClearanceTypeList(validateService.stringToLong(businessClearanceDetailResource.getClearanceTypeId()),businessClearanceDetailResource.getClearanceTypeName(),index);	

		CommonList clearanceAuthorityCommonItem = validateClearanceAuthorityList(validateService.stringToLong(businessClearanceDetailResource.getAuthorityId()),businessClearanceDetailResource.getAuthorityName(),index);	
		
		
		if(businessClearanceDetailResource.getId() != null && !businessClearanceDetailResource.getId().isEmpty()) {
			
			Optional<BusinessClearanceDetails> isPresentBusinessClearanceDetail = businessClearanceDetailsRepository.findByBusinessAdditionalDtlIdAndId(businessAdditionalDetails.getId(), validateService.stringToLong(businessClearanceDetailResource.getId()));
    	    if(!isPresentBusinessClearanceDetail.isPresent())
    	    	throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.ID, ServicePoint.BUSINESS_CLEARANCE_DETAILS, index);	
			
			Optional<BusinessClearanceDetails> isDuplicateBusinessClearanceDetail = businessClearanceDetailsRepository.findByBusinessAdditionalDtlIdAndClearanceTypeIdAndStatusAndIdNotIn(
					businessAdditionalDetails.getId(), validateService.stringToLong(businessClearanceDetailResource.getClearanceTypeId()), CommonStatus.ACTIVE, isPresentBusinessClearanceDetail.get().getId());
			if(isDuplicateBusinessClearanceDetail.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CLEARANCE_TYPE_ID, ServicePoint.BUSINESS_CLEARANCE_DETAILS, index);					
			}	
			
			if(!isPresentBusinessClearanceDetail.get().getVersion().toString().equals(businessClearanceDetailResource.getVersion()))
				throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.BUSINESS_CLEARANCE_DETAILS, index);	
	       
			BusinessClearanceDetails businessClearanceDetails = isPresentBusinessClearanceDetail.get();
			businessClearanceDetails.setTenantId(tenantId);
			businessClearanceDetails.setClearanceType(clearanceTypeCommonItem);
			businessClearanceDetails.setAuthority(clearanceAuthorityCommonItem);
			businessClearanceDetails.setObtainDate(obtainDate != null ? new Timestamp(obtainDate.getTime()):null);
			businessClearanceDetails.setExpireDate(expireDate != null ? new Timestamp(expireDate.getTime()):null);
			businessClearanceDetails.setMemo(businessClearanceDetailResource.getMemo());
			businessClearanceDetails.setStatus(CommonStatus.valueOf(businessClearanceDetailResource.getStatus()));
			businessClearanceDetails.setModifiedDate(validateService.getCreateOrModifyDate());
			businessClearanceDetails.setModifiedUser((LogginAuthentcation.getInstance().getUserName()));
			businessClearanceDetails.setSyncTs(validateService.getSyncTs());
			businessClearanceDetailsRepository.saveAndFlush(businessClearanceDetails);	
		}else{
			
			Optional<BusinessClearanceDetails> isPresentBusinessClearanceDetail = businessClearanceDetailsRepository.findByBusinessAdditionalDtlIdAndClearanceTypeIdAndStatus(businessAdditionalDetails.getId(), validateService.stringToLong(businessClearanceDetailResource.getClearanceTypeId()), CommonStatus.ACTIVE);
			if(isPresentBusinessClearanceDetail.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CLEARANCE_TYPE_ID, ServicePoint.BUSINESS_CLEARANCE_DETAILS, index);					
			}	
	       
			BusinessClearanceDetails businessClearanceDetails = new BusinessClearanceDetails();
			businessClearanceDetails.setTenantId(tenantId);
			businessClearanceDetails.setClearanceType(clearanceTypeCommonItem);
			businessClearanceDetails.setAuthority(clearanceAuthorityCommonItem);
			businessClearanceDetails.setObtainDate(obtainDate != null ? new Timestamp(obtainDate.getTime()):null);
			businessClearanceDetails.setExpireDate(expireDate != null ? new Timestamp(expireDate.getTime()):null);
			businessClearanceDetails.setMemo(businessClearanceDetailResource.getMemo());
			businessClearanceDetails.setBusinessAdditionalDtl(businessAdditionalDetails);
			businessClearanceDetails.setStatus(CommonStatus.valueOf(businessClearanceDetailResource.getStatus()));
			businessClearanceDetails.setCreatedDate(validateService.getCreateOrModifyDate());
			businessClearanceDetails.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
			businessClearanceDetails.setSyncTs(validateService.getSyncTs());
			businessClearanceDetailsRepository.saveAndFlush(businessClearanceDetails);	
		}
		
	}

	private BusinessAdditionalDetails setBusinessAdditionalDetails(String tenantId, BusinessAdditionalDetails businessAdditionalDetails){	
		
		Optional<BusinessType> isPresentBusinessType = businessTypeRepository.findById(businessAdditionalDetails.getBusinessType().getId());
		if(isPresentBusinessType.isPresent()) {
			businessAdditionalDetails.setBusinessTypesId(isPresentBusinessType.get().getId());
			businessAdditionalDetails.setBusinessTypesName(isPresentBusinessType.get().getName());
		}
		
		Optional<BusinessSubType> isPresentBusinessSubType = businessSubTypeRepository.findById(businessAdditionalDetails.getBusinessSubType().getId());
		if(isPresentBusinessSubType.isPresent()) {
			businessAdditionalDetails.setBusinessSubTypesId(isPresentBusinessSubType.get().getId());
			businessAdditionalDetails.setBusinessSubTypesName(isPresentBusinessSubType.get().getName());
		}
		
		Optional<CommonList> ownershipCommonListItem = commonListRepository.findById(businessAdditionalDetails.getOwnership().getId());
		if(ownershipCommonListItem.isPresent()) {
			businessAdditionalDetails.setOwnershipsId(ownershipCommonListItem.get().getId());
			businessAdditionalDetails.setOwnershipsName(ownershipCommonListItem.get().getName());
		}
			
		Optional<CommonList> businessSizeCommonListItem = commonListRepository.findById(businessAdditionalDetails.getBusinessSize().getId());
		if(businessSizeCommonListItem.isPresent()) {
			businessAdditionalDetails.setBusinessSizesId(businessSizeCommonListItem.get().getId());
			businessAdditionalDetails.setBusinessSizeName(businessSizeCommonListItem.get().getName());
		}
		
		businessAdditionalDetails.setLeadsId(businessAdditionalDetails.getLead() !=null ? businessAdditionalDetails.getLead().getId():null);
		
		if(businessAdditionalDetails.getCustomer() != null) {
			businessAdditionalDetails.setCustomersId(businessAdditionalDetails.getCustomer().getId());
			
			Optional<Customer> customer = customerRepository.findById(businessAdditionalDetails.getCustomer().getId());
			businessAdditionalDetails.setCustomersFullname(customer.isPresent()? customer.get().getFullName():null);
		}
		
		if(businessAdditionalDetails.getLinkedPerson() != null) {
			businessAdditionalDetails.setLinkedPersonsId(businessAdditionalDetails.getLinkedPerson().getId());
			
			Optional<LinkedPerson> linkedPerson = linkedPersonRepository.findById(businessAdditionalDetails.getLinkedPerson().getId());
			businessAdditionalDetails.setLinkedPersonsName(linkedPerson.isPresent() ? linkedPerson.get().getName():null);
		}
		
		businessAdditionalDetails.setBusinessDocumentDetails(businessDocumentDetailsRepository.findByBusinessAdditionalDtlId(businessAdditionalDetails.getId()));
		
		List<BusinessRiskDetails> businessRiskDetailsList = businessRiskDetailsRepository.findByBusinessAdditionalDtlId(businessAdditionalDetails.getId());
		if(!businessRiskDetailsList.isEmpty()){
			for(BusinessRiskDetails businessRiskDetail : businessRiskDetailsList) {			
				Optional<BusinessRiskType> isPresentRiskType = businessRiskTypeRepository.findById(businessRiskDetail.getRiskType().getId());
				if(isPresentRiskType.isPresent()) {
					businessRiskDetail.setRiskTypesId(isPresentRiskType.get().getId());
					businessRiskDetail.setRiskTypesName(isPresentRiskType.get().getName());
				}
				Optional<RiskGrading> isPresentRiskGrading = riskGradingRepository.findById(businessRiskDetail.getRiskGrading().getId());
				if(isPresentRiskGrading.isPresent()) {
					businessRiskDetail.setRiskGradingsId(isPresentRiskGrading.get().getId());
					businessRiskDetail.setRiskGradingsName(isPresentRiskGrading.get().getName());
				}
				if(businessRiskDetail.getRiskRatingLevel() != null) {
					Optional<RiskRatingLevels> isPresentRiskLevel = riskRatingLevelsRepository.findById(businessRiskDetail.getRiskRatingLevel().getId());
					if(isPresentRiskLevel.isPresent()) {
						businessRiskDetail.setRiskRatingLevelsId(isPresentRiskLevel.get().getId());
						businessRiskDetail.setRiskRatingType(isPresentRiskLevel.get().getRiskRatingType());
					}
				}
				if(businessRiskDetail.getRiskAuthority() != null) {
					Optional<RiskAuthorities> isPresentRiskAuthority = riskAuthoritiesRepository.findById(businessRiskDetail.getRiskAuthority().getId());
					if(isPresentRiskAuthority.isPresent()) {	
						businessRiskDetail.setRiskAuthoritysId(isPresentRiskAuthority.get().getId());
						businessRiskDetail.setRiskAuthoritysName(isPresentRiskAuthority.get().getName());
					}
				}
			}		
		}
		businessAdditionalDetails.setBusinessRiskDetails(businessRiskDetailsList);
		
		List<BusinessAssetDetails> businessAssetDetailsList = businessAssetDetailsRepository.findByBusinessAdditionalDtlId(businessAdditionalDetails.getId());
		if(!businessAssetDetailsList.isEmpty()){
			for(BusinessAssetDetails businessAssetDetail : businessAssetDetailsList) {			
				
				Optional<CommonList> assetCommonListItem = commonListRepository.findById(businessAssetDetail.getAssetType().getId());
				if(assetCommonListItem.isPresent()) {
					businessAssetDetail.setAssetTypesId(assetCommonListItem.get().getId());
					businessAssetDetail.setAssetTypesName(assetCommonListItem.get().getName());
				}
				
				Optional<CommonList> assetOwnershipCommonListItem = commonListRepository.findById(businessAssetDetail.getOwnership().getId());
				if(assetOwnershipCommonListItem.isPresent()){
					businessAssetDetail.setOwnershipsId(assetOwnershipCommonListItem.get().getId());
					businessAssetDetail.setOwnershipsName(assetOwnershipCommonListItem.get().getName());
				}		
			}
		}
		businessAdditionalDetails.setBusinessAssetDetails(businessAssetDetailsList);
		
		List<BusinessEmploymentDetails> businessEmploymentDetailsList = businessEmploymentDetailsRepository.findByBusinessAdditionalDtlId(businessAdditionalDetails.getId());
		if(!businessEmploymentDetailsList.isEmpty()){
			for(BusinessEmploymentDetails businessEmploymentDetail : businessEmploymentDetailsList) {			
				
				PerCommonList perCommonList= validateService.validatePersonCommonList(tenantId, businessEmploymentDetail.getEmploymentTypeId().toString() , null, 
						CommonListReferenceCode.EMPLOYMENTCATEGORY.toString() , null ,ServiceEntity.EMPLOYMENT_TYPE_ID, ServicePoint.BUSINESS_EMPLOYMENT_DETAILS,null);
				if(perCommonList != null) {
					businessEmploymentDetail.setEmploymentTypeDesc(perCommonList.getCmlsDesc());
				}
			}
		}
		businessAdditionalDetails.setBusinessEmploymentDetails(businessEmploymentDetailsList);
		
		List<BusinessClearanceDetails> businessClearanceDetailsList = businessClearanceDetailsRepository.findByBusinessAdditionalDtlId(businessAdditionalDetails.getId());
		if(!businessClearanceDetailsList.isEmpty()){
			for(BusinessClearanceDetails businessClearanceDetail : businessClearanceDetailsList) {			
				
				Optional<CommonList> clearanceTypeCommonListItem = commonListRepository.findById(businessClearanceDetail.getClearanceType().getId());
				if(clearanceTypeCommonListItem.isPresent()) {
					businessClearanceDetail.setClearanceTypesId(clearanceTypeCommonListItem.get().getId());
					businessClearanceDetail.setClearanceTypesName(clearanceTypeCommonListItem.get().getName());
				}
				
				Optional<CommonList> authorityCommonListItem = commonListRepository.findById(businessClearanceDetail.getAuthority().getId());
				if(authorityCommonListItem.isPresent()){
					businessClearanceDetail.setAuthoritysId(authorityCommonListItem.get().getId());
					businessClearanceDetail.setAuthoritysName(authorityCommonListItem.get().getName());
				}		
			}
		}
		businessAdditionalDetails.setBusinessClearanceDetails(businessClearanceDetailsList);
			
		return businessAdditionalDetails;
		
	}

	@Override
	public BusinessAdditionalDetails update(String tenantId, Long id,
			BusinessAdditionalDetailsUpdateResource businessAdditionalDetailsUpdateResource) {
		
		 if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
	        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
		 
		 Optional<BusinessAdditionalDetails> isPresentBusinessAdditionalDetail = businessAdditionalDetailsRepository.findById(id);
	     if(!isPresentBusinessAdditionalDetail.isPresent())
	        	throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
	     
	     Date operationStartDate = null;
	     Date businessRegiDate = null;
         
	     BusinessType businessType = validateBusinessType(validateService.stringToLong(businessAdditionalDetailsUpdateResource.getBusinessTypeId()) , businessAdditionalDetailsUpdateResource.getBusinessTypeName());
         BusinessSubType businessSubType = validateBusinessSubType(validateService.stringToLong(businessAdditionalDetailsUpdateResource.getBusinessSubTypeId()) , businessAdditionalDetailsUpdateResource.getBusinessSubTypeName());
         CommonList ownerShipCommonItem = validateOwnership(validateService.stringToLong(businessAdditionalDetailsUpdateResource.getOwnershipId()) , businessAdditionalDetailsUpdateResource.getOwnershipName());
         CommonList businessSizeCommonItem = validateBusinessSize(validateService.stringToLong(businessAdditionalDetailsUpdateResource.getBusinessSizeId()) , businessAdditionalDetailsUpdateResource.getBusinessSizeName());
	        
         if(businessAdditionalDetailsUpdateResource.getBusiOpertaionStartDate() != null && !businessAdditionalDetailsUpdateResource.getBusiOpertaionStartDate().isEmpty()) {
	             operationStartDate=validateService.formatDate(businessAdditionalDetailsUpdateResource.getBusiOpertaionStartDate());
	    		if( operationStartDate!=null && operationStartDate.after(new Date())) 
	    			throw new ValidateRecordException(environment.getProperty(FUTURE_DATE_NOT_ALLOWED),"busiOpertaionStartDate");
         }
         if(businessAdditionalDetailsUpdateResource.getBusinessRegiDate() != null && !businessAdditionalDetailsUpdateResource.getBusinessRegiDate().isEmpty()) {
	    		 businessRegiDate=validateService.formatDate(businessAdditionalDetailsUpdateResource.getBusinessRegiDate());
	     		if( businessRegiDate!=null && businessRegiDate.after(new Date())) 
	     			throw new ValidateRecordException(environment.getProperty(FUTURE_DATE_NOT_ALLOWED),"businessRegiDate");
	     }
         
         if(!isPresentBusinessAdditionalDetail.get().getVersion().toString().equals(businessAdditionalDetailsUpdateResource.getVersion()))
        	 	throw new ValidateRecordException(environment.getProperty(INVALID_VERSION),"version");
 		
	        BusinessAdditionalDetails businessAdditionalDetails = isPresentBusinessAdditionalDetail.get();
	        businessAdditionalDetails.setTenantId(tenantId);
	        businessAdditionalDetails.setBusinessType(businessType);
	        businessAdditionalDetails.setBusinessSubType(businessSubType);
	        businessAdditionalDetails.setOwnership(ownerShipCommonItem);
	        businessAdditionalDetails.setBusinessName(businessAdditionalDetailsUpdateResource.getBusinessName());
	        businessAdditionalDetails.setBusinessSize(businessSizeCommonItem);
	        businessAdditionalDetails.setNoOfYearsInBusiness(businessAdditionalDetailsUpdateResource.getNoOfYearsInBusiness());
	        businessAdditionalDetails.setBusiOpertaionStartDate(operationStartDate != null ? new Timestamp(operationStartDate.getTime()):null);
	        businessAdditionalDetails.setBusinessRegiNo(businessAdditionalDetailsUpdateResource.getBusinessRegiNo());
	        businessAdditionalDetails.setBusinessRegiDate(businessRegiDate != null ? new Timestamp(businessRegiDate.getTime()):null);
	        businessAdditionalDetails.setDescription(businessAdditionalDetailsUpdateResource.getDescription());
	        businessAdditionalDetails.setProfitMargin(businessAdditionalDetailsUpdateResource.getProfitMargin() != null && !businessAdditionalDetailsUpdateResource.getProfitMargin().isEmpty() ? validateService.stringToDouble(businessAdditionalDetailsUpdateResource.getProfitMargin()):null);
	        businessAdditionalDetails.setComment(businessAdditionalDetailsUpdateResource.getComment());
	        businessAdditionalDetails.setSourceType(businessAdditionalDetailsUpdateResource.getSourceType() != null && !businessAdditionalDetailsUpdateResource.getSourceType().isEmpty() ? SourceTypeEnum.valueOf(businessAdditionalDetailsUpdateResource.getSourceType()):null);
	        businessAdditionalDetails.setNoOfBranches(businessAdditionalDetailsUpdateResource.getNoOfBranches());
	        businessAdditionalDetails.setSkillsOfKeyPerson(businessAdditionalDetailsUpdateResource.getSkillsOfKeyPerson());
	        businessAdditionalDetails.setPreviousBusiHistory(businessAdditionalDetailsUpdateResource.getPreviousBusiHistory());
	        businessAdditionalDetails.setBusiContinuityPlan(businessAdditionalDetailsUpdateResource.getBusiContinuityPlan());
	        businessAdditionalDetails.setStatus(CommonStatus.valueOf(businessAdditionalDetailsUpdateResource.getStatus()));
	        businessAdditionalDetails.setSyncTs(validateService.getSyncTs());
	        businessAdditionalDetails.setModifiedDate(validateService.getCreateOrModifyDate());
	        businessAdditionalDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        businessAdditionalDetails = businessAdditionalDetailsRepository.saveAndFlush(businessAdditionalDetails);
	        
	        //Business Document Details Save
	        if(businessAdditionalDetailsUpdateResource.getBusinessDocumentList() != null && !businessAdditionalDetailsUpdateResource.getBusinessDocumentList().isEmpty()) {
	        	Integer index=0;
	        	for(DocumentUpdateResource documentUpdateResource : businessAdditionalDetailsUpdateResource.getBusinessDocumentList() ) {	
	        		
	        		if(documentUpdateResource.getId() != null && !documentUpdateResource.getId().isEmpty())
	        			updateBusinessDocumentDetails(tenantId,documentUpdateResource,businessAdditionalDetails, index);	
	        		else
	        			saveBusinessDocumentDetails(tenantId,documentUpdateResource,businessAdditionalDetails, index);
	        					
					index++;
	        	}
	        }
	        //Business Risk Details Save
	        if(businessAdditionalDetailsUpdateResource.getBusinessRiskdetailList() != null && !businessAdditionalDetailsUpdateResource.getBusinessRiskdetailList().isEmpty()) {
	        	Integer index=0;
	        	for(BusinessRiskDetailResource businessRiskDetailResource : businessAdditionalDetailsUpdateResource.getBusinessRiskdetailList() ) {							
						saveBusinessRiskDetails(tenantId,businessRiskDetailResource,businessAdditionalDetails,index);
					
					index++;
	        	}
	        }
	        //Business Asset Details Save
	        if(businessAdditionalDetailsUpdateResource.getBusinessAssetdetailList() != null && !businessAdditionalDetailsUpdateResource.getBusinessAssetdetailList().isEmpty()) {
	        	Integer index=0;
	        	for(BusinessAssetDetailResource businessAssetDetailResource : businessAdditionalDetailsUpdateResource.getBusinessAssetdetailList() ) {							
						saveBusinessAssetDetails(tenantId,businessAssetDetailResource,businessAdditionalDetails,index);
					
					index++;
	        	}
	        }  
	        //Business Employment Details Save
	        if(businessAdditionalDetailsUpdateResource.getBusinessEmploymentdetailList() != null && !businessAdditionalDetailsUpdateResource.getBusinessEmploymentdetailList().isEmpty()) {
	        	Integer index=0;
	        	for(BusinessEmploymentDetailResource businessEmploymentDetailResource : businessAdditionalDetailsUpdateResource.getBusinessEmploymentdetailList() ) {							
						saveBusinessEmploymentDetails(tenantId,businessEmploymentDetailResource,businessAdditionalDetails,index);
					
					index++;
	        	}
	        }   
	        //Business Clearance Details Save
	        if(businessAdditionalDetailsUpdateResource.getBusinessClearancedetailList() != null && !businessAdditionalDetailsUpdateResource.getBusinessClearancedetailList().isEmpty()) {
	        	Integer index=0;
	        	for(BusinessClearanceDetailResource businessClearanceDetailResource : businessAdditionalDetailsUpdateResource.getBusinessClearancedetailList() ) {							
						saveBusinessClearanceDetails(tenantId,businessClearanceDetailResource,businessAdditionalDetails,index);
					
					index++;
	        	}
	        }   
	        
     return businessAdditionalDetails;	 
	}

}
