package com.fusionx.lending.origination.service.impl;

/**
 * 	Business Income Details Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   06-09-2021   FXL-115  	 FXL-657       Piyumi       Created
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
import com.fusionx.lending.origination.domain.IncomeSourceDetails;
import com.fusionx.lending.origination.domain.BusinessAdditionalDetails;
import com.fusionx.lending.origination.domain.BusinessIncomeDetails;
import com.fusionx.lending.origination.domain.BusinessIncomeDocuments;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.IncomeSourceDetailsRepository;
import com.fusionx.lending.origination.repository.BusinessIncomeDetailsRepository;
import com.fusionx.lending.origination.repository.BusinessIncomeDocumentsRepository;
import com.fusionx.lending.origination.resource.DocumentAddResource;
import com.fusionx.lending.origination.resource.DocumentUpdateResource;
import com.fusionx.lending.origination.resource.BusinessIncomeDetailsAddResource;
import com.fusionx.lending.origination.resource.BusinessIncomeDetailsUpdateResource;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.BusinessAdditionalDetailsService;
import com.fusionx.lending.origination.service.BusinessIncomeDetailsService;
import com.fusionx.lending.origination.service.ValidateService;


@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessIncomeDetailsServiceImpl extends MessagePropertyBase implements BusinessIncomeDetailsService{
	
	
	private BusinessIncomeDetailsRepository businessIncomeDetailsRepository;
	
	@Autowired
	public void setBusinessIncomeDetailsRepository(BusinessIncomeDetailsRepository businessIncomeDetailsRepository) {
		this.businessIncomeDetailsRepository = businessIncomeDetailsRepository;
	}
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	private IncomeSourceDetailsRepository incomeSourceDetailsRepository;
	
	@Autowired
	public void setIncomeSourceDetailsRepository(IncomeSourceDetailsRepository incomeSourceDetailsRepository) {
		this.incomeSourceDetailsRepository = incomeSourceDetailsRepository;
	}
	
	@Autowired
	RemoteService remoteService;
	
	private BusinessIncomeDocumentsRepository businessIncomeDocumentsRepository;
	
	@Autowired
	public void setBusinessIncomeDocumentsRepository(BusinessIncomeDocumentsRepository businessIncomeDocumentsRepository) {
		this.businessIncomeDocumentsRepository = businessIncomeDocumentsRepository;
	}
	
	@Autowired
	private BusinessAdditionalDetailsService businessAdditionalDetailsService;

	@Override
	public Optional<BusinessIncomeDetails> findById(String tenantId ,Long id) {
		Optional<BusinessIncomeDetails> isPresentBusinessIncomeDetails = businessIncomeDetailsRepository.findById(id);
		
		if (isPresentBusinessIncomeDetails.isPresent()) {
			return Optional.ofNullable(setBusinessIncomeResponseDetails(tenantId,isPresentBusinessIncomeDetails.get()));
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<BusinessIncomeDetails> findAll(String tenantId) {
		List<BusinessIncomeDetails> businessIncomeDetailsList = businessIncomeDetailsRepository.findAll();
		
		for (BusinessIncomeDetails businessIncomeDetails : businessIncomeDetailsList) {
			setBusinessIncomeResponseDetails(tenantId, businessIncomeDetails);
		}
		return businessIncomeDetailsList;
	}


	@Override
	public List<BusinessIncomeDetails> findByStatus(String tenantId,String status) {
		
		List<BusinessIncomeDetails> businessIncomeDetailsList = businessIncomeDetailsRepository.findByStatus(CommonStatus.valueOf(status));
		
		for (BusinessIncomeDetails incomeSourceDetail : businessIncomeDetailsList) {
			setBusinessIncomeResponseDetails( tenantId ,incomeSourceDetail);
		}
		return 	businessIncomeDetailsList;
	}
	
	@Override
	public BusinessIncomeDetails save(String tenantId,BusinessIncomeDetailsAddResource businessIncomeDetailsAddResource) {
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
		
        Optional<IncomeSourceDetails> isPresentIncomeSourceDetails = incomeSourceDetailsRepository.findById(validateService.stringToLong(businessIncomeDetailsAddResource.getIncomeSourceDetailsId()));
		if (!isPresentIncomeSourceDetails.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "incomeSourceDetailId");

		if(businessIncomeDetailsAddResource.getBusinessAdditionalDetails() == null)
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "businessAdditionalDetailsResource");
			
			BusinessAdditionalDetails businessAdditionalDetails = businessAdditionalDetailsService.save(tenantId, businessIncomeDetailsAddResource.getBusinessAdditionalDetails());
			      
			BusinessIncomeDetails businessIncomeDetails = new BusinessIncomeDetails();
			businessIncomeDetails.setTenantId(tenantId);
			businessIncomeDetails.setIncomeSourceDetail(isPresentIncomeSourceDetails.get());
	        businessIncomeDetails.setBusiAdditionalDtl(businessAdditionalDetails);
	        businessIncomeDetails.setStatus(CommonStatus.valueOf(businessIncomeDetailsAddResource.getStatus()));
	        businessIncomeDetails.setSyncTs(validateService.getSyncTs());
	        businessIncomeDetails.setCreatedDate(validateService.getCreateOrModifyDate());
	        businessIncomeDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	        businessIncomeDetails = businessIncomeDetailsRepository.save(businessIncomeDetails);        
			        
	        if(businessIncomeDetailsAddResource.getBusinessIncomeDocumentList() != null && !businessIncomeDetailsAddResource.getBusinessIncomeDocumentList().isEmpty()) {
	        	Integer index = 0;
	        	for(DocumentAddResource documentAddResource : businessIncomeDetailsAddResource.getBusinessIncomeDocumentList()) {

					validateService.validateDocument(tenantId, documentAddResource.getDocumentId(),documentAddResource.getDocumentName(), ServicePoint.BUSINESS_INCOME_DOCUMENTS,Constants.ORIGIN_CUSTOMER, index);
					
					Optional<BusinessIncomeDocuments> isPresentBusinessIncomeDocument = businessIncomeDocumentsRepository.findByBusinessIncomeDtlIdAndDocumentIdAndStatus(businessIncomeDetails.getId(), validateService.stringToLong(documentAddResource.getDocumentId()), CommonStatus.ACTIVE);
					if(isPresentBusinessIncomeDocument.isPresent()) {
						throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.DOCUMENT_ID, ServicePoint.BUSINESS_INCOME_DOCUMENTS, index);						
					}
					
					BusinessIncomeDocuments businessIncomeDocuments = new BusinessIncomeDocuments();
					businessIncomeDocuments.setTenantId(tenantId);
					businessIncomeDocuments.setDocumentId(validateService.stringToLong(documentAddResource.getDocumentId()));
					businessIncomeDocuments.setBusinessIncomeDtl(businessIncomeDetails);
					businessIncomeDocuments.setStatus(CommonStatus.valueOf(documentAddResource.getStatus()));
					businessIncomeDocuments.setCreatedDate(validateService.getCreateOrModifyDate());
					businessIncomeDocuments.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
					businessIncomeDocuments.setSyncTs(validateService.getSyncTs());
					businessIncomeDocumentsRepository.saveAndFlush(businessIncomeDocuments);
					
					index++;
	        	}
	        }
		         
	        return businessIncomeDetails;

	}

	@Override
	public BusinessIncomeDetails update(String tenantId, Long id , BusinessIncomeDetailsUpdateResource businessIncomeDetailsUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<BusinessIncomeDetails> isPresentBusinessIncomeDetails = businessIncomeDetailsRepository.findById(id);
		if (!isPresentBusinessIncomeDetails.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
		
		if(!isPresentBusinessIncomeDetails.get().getVersion().equals(Long.parseLong(businessIncomeDetailsUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
		
		if(businessIncomeDetailsUpdateResource.getBusinessAdditionalDetails() != null)
			businessAdditionalDetailsService.update(tenantId,isPresentBusinessIncomeDetails.get().getBusiAdditionalDtl().getId(), businessIncomeDetailsUpdateResource.getBusinessAdditionalDetails());
				
		    BusinessIncomeDetails businessIncomeDetails = isPresentBusinessIncomeDetails.get();
		    businessIncomeDetails.setTenantId(tenantId);
	        businessIncomeDetails.setStatus(CommonStatus.valueOf(businessIncomeDetailsUpdateResource.getStatus()));
	        businessIncomeDetails.setSyncTs(validateService.getSyncTs());
	        businessIncomeDetails.setModifiedDate(validateService.getCreateOrModifyDate());
	        businessIncomeDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        businessIncomeDetails = businessIncomeDetailsRepository.save(businessIncomeDetails);
	        
	        
	        if(businessIncomeDetailsUpdateResource.getBusinessIncomeDocumentList() != null && !businessIncomeDetailsUpdateResource.getBusinessIncomeDocumentList().isEmpty()) {
	        	Integer index = 0;
	        	for(DocumentUpdateResource documentUpdateResource : businessIncomeDetailsUpdateResource.getBusinessIncomeDocumentList()) {

					validateService.validateDocument(tenantId, documentUpdateResource.getDocumentId(),documentUpdateResource.getDocumentName(),ServicePoint.BUSINESS_INCOME_DOCUMENTS,Constants.ORIGIN_CUSTOMER, index);
					
					// update existing document
					if(documentUpdateResource.getId() != null && !documentUpdateResource.getDocumentId().isEmpty()) {
						
						Optional<BusinessIncomeDocuments> isPresentBusinessIncomeDocument = businessIncomeDocumentsRepository.findById(validateService.stringToLong(documentUpdateResource.getId()));
						if(!isPresentBusinessIncomeDocument.isPresent())
							throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.ID, ServicePoint.BUSINESS_INCOME_DOCUMENTS, index);									
						
						Optional<BusinessIncomeDocuments> isDuplicateBusinessIncomeDocument = businessIncomeDocumentsRepository.findByBusinessIncomeDtlIdAndDocumentIdAndStatusAndIdNotIn(businessIncomeDetails.getId(),
								validateService.stringToLong(documentUpdateResource.getDocumentId()), CommonStatus.ACTIVE ,isPresentBusinessIncomeDocument.get().getId() );
						if(isDuplicateBusinessIncomeDocument.isPresent()) {
							throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.DOCUMENT_ID, ServicePoint.BUSINESS_INCOME_DOCUMENTS, index);						
						}
						
						if(!isPresentBusinessIncomeDocument.get().getVersion().equals(Long.parseLong(documentUpdateResource.getVersion())))
							throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, ServicePoint.BUSINESS_INCOME_DOCUMENTS, index);						
						
							BusinessIncomeDocuments businessIncomeDocuments = isPresentBusinessIncomeDocument.get();
							businessIncomeDetails.setTenantId(tenantId);
							businessIncomeDocuments.setDocumentId(validateService.stringToLong(documentUpdateResource.getDocumentId()));
							businessIncomeDocuments.setBusinessIncomeDtl(businessIncomeDetails);
							businessIncomeDocuments.setStatus(CommonStatus.valueOf(documentUpdateResource.getStatus()));
							businessIncomeDocuments.setModifiedDate(validateService.getCreateOrModifyDate());
							businessIncomeDocuments.setModifiedUser((LogginAuthentcation.getInstance().getUserName()));
							businessIncomeDocuments.setSyncTs(validateService.getSyncTs());
							businessIncomeDocumentsRepository.saveAndFlush(businessIncomeDocuments);
						
					}else {// update with new document
						Optional<BusinessIncomeDocuments> isPresentBusinessIncomeDocument = businessIncomeDocumentsRepository.findByBusinessIncomeDtlIdAndDocumentIdAndStatus(businessIncomeDetails.getId(), 
								validateService.stringToLong(documentUpdateResource.getDocumentId()), CommonStatus.ACTIVE);
						if(isPresentBusinessIncomeDocument.isPresent()) {
							throw new DetailListValidateException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.DOCUMENT_ID, ServicePoint.BUSINESS_INCOME_DOCUMENTS, index);							
						}
						
						BusinessIncomeDocuments businessIncomeDocuments = new BusinessIncomeDocuments();
						businessIncomeDocuments.setTenantId(tenantId);
						businessIncomeDocuments.setDocumentId(validateService.stringToLong(documentUpdateResource.getDocumentId()));
						businessIncomeDocuments.setBusinessIncomeDtl(businessIncomeDetails);
						businessIncomeDocuments.setStatus(CommonStatus.valueOf(documentUpdateResource.getStatus()));
						businessIncomeDocuments.setCreatedDate(validateService.getCreateOrModifyDate());
						businessIncomeDocuments.setCreatedUser((LogginAuthentcation.getInstance().getUserName()));
						businessIncomeDocuments.setSyncTs(validateService.getSyncTs());
						businessIncomeDocumentsRepository.saveAndFlush(businessIncomeDocuments);
					}
					index++;
	        	}
	        }
	        return businessIncomeDetails;
	}


	private BusinessIncomeDetails setBusinessIncomeResponseDetails(String tenantId, BusinessIncomeDetails businessIncomeDetails){	
		
		businessIncomeDetails.setBusinessAdditionalDetailsId(businessIncomeDetails.getBusiAdditionalDtl().getId());
		businessIncomeDetails.setBusinessIncomeDocumentList(businessIncomeDocumentsRepository.findByBusinessIncomeDtlId(businessIncomeDetails.getId()));
		return businessIncomeDetails;
		
	}

	@Override
	public List<BusinessIncomeDetails> findByIncomeSourceDetailsId(String tenantId, Long incomeSourceDetailsId) {
		List<BusinessIncomeDetails> businessIncomeDetailsList = businessIncomeDetailsRepository.findByIncomeSourceDetailId(incomeSourceDetailsId);
		
		for (BusinessIncomeDetails incomeSourceDetail : businessIncomeDetailsList) {
			setBusinessIncomeResponseDetails( tenantId ,incomeSourceDetail);
		}
		return 	businessIncomeDetailsList;
	}
	
	

}
