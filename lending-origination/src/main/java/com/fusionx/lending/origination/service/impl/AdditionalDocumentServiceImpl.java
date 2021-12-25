package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.AdditionalDocument;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.AdditionalDocumentRepository;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.resource.AdditionalDocumentAddRequestResource;
import com.fusionx.lending.origination.resource.AdditionalDocumentRequestResource;
import com.fusionx.lending.origination.service.AdditionalDocumentService;

/**
 * Additional Document Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6484    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class AdditionalDocumentServiceImpl extends MessagePropertyBase implements  AdditionalDocumentService{
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private AdditionalDocumentRepository additionalDocumentRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private LeadInfoRepository leadInfoRepository;
	
	@Override
	public void saveAndValidateAdditionalDocuments(String tenantId, String createdUser, AdditionalDocumentAddRequestResource additionalDocumentAddRequestResource) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveAndValidateAdditionalDocuments>>>>>>******");
		Optional<Customer> relevantCustomerInfo = customerRepository.findById(stringToLong(additionalDocumentAddRequestResource.getCustomerId()));
		if(!relevantCustomerInfo.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "customerId");
		}
		
		
		additionalDocuments(tenantId, createdUser, additionalDocumentAddRequestResource.getAdditionalDocuments(),relevantCustomerInfo.get(),null);
		
		
	}
	
	private void additionalDocuments(String tenantId, String createdUser, List<AdditionalDocumentRequestResource> additionalDocumentRequestResources,Customer customer, Long id) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN additionalDocuments>>>>>>******");
 		AdditionalDocument additionalDocument;
		
		
		Integer index=0;
		for(AdditionalDocumentRequestResource additionalDocumentRequestResource : additionalDocumentRequestResources) {	
			if(additionalDocumentRequestResource.getId() != null && !additionalDocumentRequestResource.getId().isEmpty()) {
				// checking Version
	 			if(additionalDocumentRequestResource.getVersion() != null && !additionalDocumentRequestResource.getVersion().isEmpty()) {	
					Optional<AdditionalDocument> relevantAdditionalDocument = additionalDocumentRepository.findById(stringToLong(additionalDocumentRequestResource.getId()));
					
					if(!relevantAdditionalDocument.isPresent()) {
		 				
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.ADDITIONAL_DOCUMENT_ID, ServicePoint.ADDITIONAL_DOCUMENT,index);
		 			}
					
					//checking Disbursement details related to Customer 
		 			if(!relevantAdditionalDocument.get().getCustomer().getId().equals(customer.getId())) {
		 				
		 				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_ADDITIONAL_DOCUMENTS), ServiceEntity.ADDITIONAL_DOCUMENT_ID, ServicePoint.ADDITIONAL_DOCUMENT,index);
		 			}
		 			
		 			//checking Version mismatch
		 			if (!relevantAdditionalDocument.get().getVersion().equals(stringToLong(additionalDocumentRequestResource.getVersion()))) {
		 				
		 				throw new DetailListValidateException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, ServicePoint.ADDITIONAL_DOCUMENT,index);
		 			}
		 			
		 			additionalDocument=relevantAdditionalDocument.get();
		 			additionalDocument.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		 			additionalDocument.setModifiedDate(getCreateOrModifyDate());
	 			}else {
	 				throw new DetailListValidateException(environment.getProperty(BLANK_VERSION), ServiceEntity.VERSION, ServicePoint.ADDITIONAL_DOCUMENT,index);
	 			}
	 			
			}else {
				
				additionalDocument= new AdditionalDocument();
				additionalDocument.setTenantId(tenantId);
				additionalDocument.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
				additionalDocument.setCreatedDate(getCreateOrModifyDate());
				
			}
		
			
			LoggerRequest.getInstance().logInfo1("******<<<<<< Validate Lead Info ID  >>>>>>******");
			Optional<LeadInfo> relevantLeanInfo = leadInfoRepository.findById(stringToLong(additionalDocumentRequestResource.getLeadInfoId()));
			if(!relevantLeanInfo.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.LEAD_INFO_ID, ServicePoint.ADDITIONAL_DOCUMENT,index);
			}
			
			LoggerRequest.getInstance().logInfo1("******<<<<<< Validate Lead Info ID and Customer >>>>>>******");
			if(!customer.getLead().getId().equals(stringToLong(additionalDocumentRequestResource.getLeadInfoId()))){
				throw new DetailListValidateException(environment.getProperty(CUSTOMER_ID_LEAD_ID_INVALID), ServiceEntity.CUSTOMER_ID, ServicePoint.ADDITIONAL_DOCUMENT,index);
			}
			
			additionalDocument.setCustomer(customer);
			additionalDocument.setLead(relevantLeanInfo.get());
			additionalDocument.setDocumentId(stringToLong(additionalDocumentRequestResource.getDocumentId()));
			additionalDocument.setDocumentType(additionalDocumentRequestResource.getDocumentType());
			additionalDocument.setDescription(additionalDocumentRequestResource.getDescription());
			additionalDocument.setMandatory(additionalDocumentRequestResource.getMandatory());
			additionalDocument.setDocumentStatus(additionalDocumentRequestResource.getDocumentStatus());
			additionalDocument.setSelect(additionalDocumentRequestResource.getSelect());
			additionalDocument.setStatus(additionalDocumentRequestResource.getStatus());
		
			additionalDocument.setSyncTs(getCreateOrModifyDate());
	
			additionalDocumentRepository.saveAndFlush(additionalDocument);
	
			index++;
		
		}
	}
	
	// String to Long
	private Long stringToLong(String value){
		return Long.parseLong(value);
	}
	
	// Get a created and modified date
	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	// String to BigDecimal
	private BigDecimal stringToBigDecimal(String value) {
		if (value != null) {
			return new BigDecimal(value);
		} else {
			return BigDecimal.valueOf(0.0);
		}
	}


}
