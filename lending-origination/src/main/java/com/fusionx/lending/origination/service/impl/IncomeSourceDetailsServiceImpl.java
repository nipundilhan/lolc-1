package com.fusionx.lending.origination.service.impl;

/**
 * 	Income Source Details Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   31-08-2021   FXL-115  	 FXL-656       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.IncomeSourceDetails;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.domain.LinkedPerson;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.IncomeTypeEnum;
import com.fusionx.lending.origination.enums.SourceTypeEnum;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.repository.IncomeSourceDetailsRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.repository.LinkedPersonRepository;
import com.fusionx.lending.origination.resource.IncomeSourceDetailsAddResource;
import com.fusionx.lending.origination.resource.IncomeSourceDetailsUpdateResource;
import com.fusionx.lending.origination.service.IncomeSourceDetailsService;
import com.fusionx.lending.origination.service.ValidateService;



@Component
@Transactional(rollbackFor = Exception.class)
public class IncomeSourceDetailsServiceImpl extends MessagePropertyBase implements IncomeSourceDetailsService{
	
	
	private IncomeSourceDetailsRepository incomeSourceDetailsRepository;
	
	@Autowired
	public void setIncomeSourceDetailsRepository(IncomeSourceDetailsRepository incomeSourceDetailsRepository) {
		this.incomeSourceDetailsRepository = incomeSourceDetailsRepository;
	}
	
	private ValidateService validateService;
	
	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
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

	@Override
	public Optional<IncomeSourceDetails> findById(Long id) {
		Optional<IncomeSourceDetails> isPresentIncomeSourceDetails = incomeSourceDetailsRepository.findById(id);
		
		if (isPresentIncomeSourceDetails.isPresent()) {
			return Optional.ofNullable(setIncomeSourceDetails(isPresentIncomeSourceDetails.get()));
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<IncomeSourceDetails> findAll() {
		List<IncomeSourceDetails> incomeSourceDetailsList = incomeSourceDetailsRepository.findAll();
		
		for (IncomeSourceDetails incomeSourceDetail : incomeSourceDetailsList) {
			setIncomeSourceDetails( incomeSourceDetail);
		}
		return incomeSourceDetailsList;
	}


	@Override
	public List<IncomeSourceDetails> findByStatus(String status) {
		
		List<IncomeSourceDetails> incomeSourceDetailsList = incomeSourceDetailsRepository.findByStatus(CommonStatus.valueOf(status));
		
		for (IncomeSourceDetails incomeSourceDetail : incomeSourceDetailsList) {
			setIncomeSourceDetails( incomeSourceDetail);
		}
		return 	incomeSourceDetailsList;
	}
	
	@Override
	public IncomeSourceDetails save(String tenantId,IncomeSourceDetailsAddResource incomeSourceDetailsAddResource) {
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
        
        Optional<LeadInfo> isPresentLeadInfo =  Optional.empty();
        Optional<Customer> isPresentCustomer = Optional.empty();
        Optional<LinkedPerson> isPresentLinkedPerson = Optional.empty();
        
        if(incomeSourceDetailsAddResource.getLeadId() != null && !incomeSourceDetailsAddResource.getLeadId().isEmpty()) {
	        isPresentLeadInfo = leadInfoRepository.findById(validateService.stringToLong(incomeSourceDetailsAddResource.getLeadId()));
	        if(!isPresentLeadInfo.isPresent())
	        	throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "leadId");
	        
	        if(SourceTypeEnum.PRIMARY.toString().equals(incomeSourceDetailsAddResource.getSourceType())){
	        	 Optional<IncomeSourceDetails> isPrimaryPresent = incomeSourceDetailsRepository.findByLeadIdAndSourceTypeAndStatus(isPresentLeadInfo.get().getId(), SourceTypeEnum.PRIMARY, CommonStatus.ACTIVE);
	        	 if(isPrimaryPresent.isPresent())
	        		 throw new ValidateRecordException(getEnvironment().getProperty(COMMON_DUPLICATE), "sourceType");
	        }
	    }
        
        if(incomeSourceDetailsAddResource.getCustomerId() != null && !incomeSourceDetailsAddResource.getCustomerId().isEmpty()) {
	        isPresentCustomer = customerRepository.findById(validateService.stringToLong(incomeSourceDetailsAddResource.getCustomerId()));
	        if(!isPresentCustomer.isPresent())
	        	throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "customerId");
	        
	        if(!isPresentCustomer.get().getFullName().equalsIgnoreCase(incomeSourceDetailsAddResource.getCustomerFullname())) 
	        	throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_MATCH), "customerFullName");
	        
	        if(SourceTypeEnum.PRIMARY.toString().equals(incomeSourceDetailsAddResource.getSourceType())){
	        	Optional<IncomeSourceDetails> isPrimaryPresent = incomeSourceDetailsRepository.findByCustomerIdAndSourceTypeAndStatus(isPresentCustomer.get().getId(), SourceTypeEnum.PRIMARY, CommonStatus.ACTIVE);
	        	 if(isPrimaryPresent.isPresent())
	        		 throw new ValidateRecordException(getEnvironment().getProperty(COMMON_DUPLICATE), "sourceType");
	        }
        }
        
        if(incomeSourceDetailsAddResource.getLinkedPersonId() != null && !incomeSourceDetailsAddResource.getLinkedPersonId().isEmpty()) {	
	        isPresentLinkedPerson = linkedPersonRepository.findById(validateService.stringToLong(incomeSourceDetailsAddResource.getLinkedPersonId()));
	        if(!isPresentLinkedPerson.isPresent())
	        	throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "linkedPersonId");
	        
	        if(!isPresentLinkedPerson.get().getName().equalsIgnoreCase(incomeSourceDetailsAddResource.getLinkedPersonName())) 
	        	throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_MATCH), "linkedPersonname");
	        
	        if(SourceTypeEnum.PRIMARY.toString().equals(incomeSourceDetailsAddResource.getSourceType())){
	        	Optional<IncomeSourceDetails> isPrimaryPresent = incomeSourceDetailsRepository.findByLinkedPersonIdAndSourceTypeAndStatus(isPresentLinkedPerson.get().getId(), SourceTypeEnum.PRIMARY, CommonStatus.ACTIVE);
	        	 if(isPrimaryPresent.isPresent())
	        		 throw new ValidateRecordException(getEnvironment().getProperty(COMMON_DUPLICATE), "sourceType");
	        }
        }
        
        IncomeSourceDetails incomeSourceDetails = new IncomeSourceDetails();
        incomeSourceDetails.setTenantId(tenantId);
        incomeSourceDetails.setIncomeType(IncomeTypeEnum.valueOf(incomeSourceDetailsAddResource.getIncomeType()));
        incomeSourceDetails.setSourceType(SourceTypeEnum.valueOf(incomeSourceDetailsAddResource.getSourceType()));
        incomeSourceDetails.setLead(isPresentLeadInfo.isPresent() ? isPresentLeadInfo.get(): null);
        incomeSourceDetails.setCustomer(isPresentCustomer.isPresent() ? isPresentCustomer.get(): null);
        incomeSourceDetails.setLinkedPerson(isPresentLinkedPerson.isPresent() ? isPresentLinkedPerson.get(): null);
        incomeSourceDetails.setStatus(CommonStatus.valueOf(incomeSourceDetailsAddResource.getStatus()));
        incomeSourceDetails.setSyncTs(validateService.getSyncTs());
        incomeSourceDetails.setCreatedDate(validateService.getCreateOrModifyDate());
        incomeSourceDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        incomeSourceDetails = incomeSourceDetailsRepository.save(incomeSourceDetails);
    	return incomeSourceDetails;
	}

	@Override
	public IncomeSourceDetails update(String tenantId, Long id , IncomeSourceDetailsUpdateResource incomeSourceDetailsUpdateResource) {
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<IncomeSourceDetails> isPresentIncomeSourceDetails = incomeSourceDetailsRepository.findById(id);
		if (!isPresentIncomeSourceDetails.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
        
		
		if(!isPresentIncomeSourceDetails.get().getVersion().equals(Long.parseLong(incomeSourceDetailsUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
		
		if(SourceTypeEnum.PRIMARY.toString().equals(incomeSourceDetailsUpdateResource.getSourceType())) {
			
			if(isPresentIncomeSourceDetails.get().getLead() != null ) {
		         Optional<IncomeSourceDetails> isPrimaryPresent = incomeSourceDetailsRepository.findByLeadIdAndSourceTypeAndStatusAndIdNotIn(isPresentIncomeSourceDetails.get().getLead().getId(), SourceTypeEnum.PRIMARY, CommonStatus.ACTIVE, id);
		         if(isPrimaryPresent.isPresent())
		        		throw new ValidateRecordException(getEnvironment().getProperty(COMMON_DUPLICATE), "sourceType");
		   
			}else if(isPresentIncomeSourceDetails.get().getCustomer() != null ) {
	        	Optional<IncomeSourceDetails> isPrimaryPresent = incomeSourceDetailsRepository.findByCustomerIdAndSourceTypeAndStatusAndIdNotIn(isPresentIncomeSourceDetails.get().getCustomer().getId(), SourceTypeEnum.PRIMARY, CommonStatus.ACTIVE, id);
		        	 if(isPrimaryPresent.isPresent())
		        		 throw new ValidateRecordException(getEnvironment().getProperty(COMMON_DUPLICATE), "sourceType");
		        	 
	        }else if(isPresentIncomeSourceDetails.get().getLinkedPerson() != null ) {
	        	Optional<IncomeSourceDetails> isPrimaryPresent = incomeSourceDetailsRepository.findByLinkedPersonIdAndSourceTypeAndStatusAndIdNotIn(isPresentIncomeSourceDetails.get().getLinkedPerson().getId(), SourceTypeEnum.PRIMARY, CommonStatus.ACTIVE, id);
		        	 if(isPrimaryPresent.isPresent())
		        		 throw new ValidateRecordException(getEnvironment().getProperty(COMMON_DUPLICATE), "sourceType");
		        }
	    }
		

		    IncomeSourceDetails incomeSourceDetails = isPresentIncomeSourceDetails.get();
	        incomeSourceDetails.setTenantId(tenantId);
	        incomeSourceDetails.setSourceType(SourceTypeEnum.valueOf(incomeSourceDetailsUpdateResource.getSourceType()));
	        incomeSourceDetails.setStatus(CommonStatus.valueOf(incomeSourceDetailsUpdateResource.getStatus()));
	        incomeSourceDetails.setSyncTs(validateService.getSyncTs());
			incomeSourceDetails.setModifiedDate(validateService.getCreateOrModifyDate());
			incomeSourceDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			incomeSourceDetails = incomeSourceDetailsRepository.saveAndFlush(incomeSourceDetails);
	    	return incomeSourceDetails;
	}

	@Override
	public List<IncomeSourceDetails> findByLeadId(Long leadId) {
		List<IncomeSourceDetails> incomeSourceDetailsList = incomeSourceDetailsRepository.findByLeadId(leadId);
		
		for (IncomeSourceDetails incomeSourceDetail : incomeSourceDetailsList) {
			setIncomeSourceDetails( incomeSourceDetail);
		}
		return 	incomeSourceDetailsList;
	}

	@Override
	public List<IncomeSourceDetails> findByCustomerId(Long customerId) {
		List<IncomeSourceDetails> incomeSourceDetailsList = incomeSourceDetailsRepository.findByCustomerId(customerId);
		
		for (IncomeSourceDetails incomeSourceDetail : incomeSourceDetailsList) {
			setIncomeSourceDetails( incomeSourceDetail);
		}
		return 	incomeSourceDetailsList;
	}

	@Override
	public List<IncomeSourceDetails> findByLinkedPersonId(Long linkedPersonId) {
		List<IncomeSourceDetails> incomeSourceDetailsList = incomeSourceDetailsRepository.findByLinkedPersonId(linkedPersonId);
		
		for (IncomeSourceDetails incomeSourceDetail : incomeSourceDetailsList) {
			setIncomeSourceDetails( incomeSourceDetail);
		}
		return 	incomeSourceDetailsList;
	}

	private IncomeSourceDetails setIncomeSourceDetails(IncomeSourceDetails incomeSourceDetails){		
		
		incomeSourceDetails.setLeadsId(incomeSourceDetails.getLead() !=null ? incomeSourceDetails.getLead().getId():null);
		
		if(incomeSourceDetails.getCustomer() != null) {
			incomeSourceDetails.setCustomersId(incomeSourceDetails.getCustomer().getId());
			incomeSourceDetails.setCustomersFullname(customerRepository.findById(incomeSourceDetails.getCustomer().getId()).get().getFullName());
		}
		
		if(incomeSourceDetails.getLinkedPerson() != null) {
			incomeSourceDetails.setLinkedPersonsId(incomeSourceDetails.getLinkedPerson().getId());
			incomeSourceDetails.setLinkedPersonsName(linkedPersonRepository.findById(incomeSourceDetails.getLinkedPerson().getId()).get().getName());
		}
		
		return incomeSourceDetails;
		
	}

}
