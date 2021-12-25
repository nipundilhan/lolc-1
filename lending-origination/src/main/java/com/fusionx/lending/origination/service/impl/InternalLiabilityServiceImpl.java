package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.ExternalLiability;
import com.fusionx.lending.origination.domain.InternalLiability;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CustomerRepository;
import com.fusionx.lending.origination.repository.InternalLiabilityRepository;
import com.fusionx.lending.origination.resource.InternalLiabilityAddResource;
import com.fusionx.lending.origination.resource.InternalLiabilityUpdateResource;
import com.fusionx.lending.origination.service.InternalLiabilityService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class InternalLiabilityServiceImpl  extends MessagePropertyBase
												implements InternalLiabilityService {
		
	@Autowired
	private InternalLiabilityRepository internalLiabilityRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ValidateService validateService;
	
	
	@Override
	public Customer saveInternalLiabilityList(String tenantId,Long customerId ,List<InternalLiabilityAddResource> internalLiabilityAddResourceList) {
		
		LoggerRequest.getInstance().logInfo1("-----------validate customer-----------------------------");
		
		Optional<Customer> customerOptional = customerRepository.findById(customerId);
		if(!customerOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("invalid.customer"), "message");
		}
		
		LoggerRequest.getInstance().logInfo1("-----------start of save internal liabilities list -----------------------------");
		
		if((internalLiabilityAddResourceList != null) && (!internalLiabilityAddResourceList.isEmpty())) {
			for(InternalLiabilityAddResource resource : internalLiabilityAddResourceList) {
				saveInternalLiability(tenantId ,customerOptional.get() ,resource );
			}
		}
		
		return customerOptional.get();
		
	}
	

	/**
 *    need to be add validations in service layer and finalize the the task . at the moment of developing
 *    there was a unresolved dependancy get internal liabilities details
	 */
	public InternalLiability saveInternalLiability(String tenantId,Customer customer ,InternalLiabilityAddResource internalLiabilityAddResource) {
		
		
		InternalLiability internalLiability = new InternalLiability();
		internalLiability.setTenantId(tenantId);
		internalLiability.setCustomer(customer);
		internalLiability.setFacilityTypeId(validationServiceStringToLong(internalLiabilityAddResource.getFacilityTypeId()));
		internalLiability.setFacilityTypeCode(internalLiabilityAddResource.getFacilityTypeCode());
		internalLiability.setFacilityTypeName(internalLiabilityAddResource.getFacilityType());
		internalLiability.setCustomerTypeId(validationServiceStringToLong(internalLiabilityAddResource.getCustomerTypeId()));
		internalLiability.setCustomerTypeCode(internalLiabilityAddResource.getCustomerTypeCode());
		internalLiability.setCustomerTypeName(internalLiabilityAddResource.getCustomerType());
		internalLiability.setSecurityTypeId(validationServiceStringToLong(internalLiabilityAddResource.getAssetTypeId()));
		internalLiability.setSecurityTypeCode(internalLiabilityAddResource.getAssetTypeCode());
		internalLiability.setSecurityTypeName(internalLiabilityAddResource.getAssetType());
		internalLiability.setRepaymentTypeId(validationServiceStringToLong(internalLiabilityAddResource.getRepaymentTypeId()));
		internalLiability.setRepaymentTypeCode(internalLiabilityAddResource.getRepaymentTypeCode());
		internalLiability.setRepaymentTypeName(internalLiabilityAddResource.getRepaymentType());
		//added because of qa bug
		internalLiability.setFacilityAccountNo(internalLiabilityAddResource.getAccountNo());
		internalLiability.setFacilityStatus(internalLiabilityAddResource.getStatus());
		internalLiability.setFacilityCreatedUser(internalLiabilityAddResource.getFacilityCreatedUser());
		internalLiability.setFacilityCreatedDate(validationServiceStringToDate(internalLiabilityAddResource.getFacilityIssueDate()));
		internalLiability.setFacilityAmount(validationServiceStringToBigDecimal(internalLiabilityAddResource.getFacilityAmount()));
		internalLiability.setCurrentInstallment(validationServiceStringToBigDecimal(internalLiabilityAddResource.getCurrentInstallment()));
		internalLiability.setCurrentDue(validationServiceStringToBigDecimal(internalLiabilityAddResource.getCurrentDue()));
		internalLiability.setOverDue(validationServiceStringToBigDecimal(internalLiabilityAddResource.getOverDue()));
		internalLiability.setAvaliableBalanceLimit(validationServiceStringToBigDecimal(internalLiabilityAddResource.getAvaliableBalance()));
		internalLiability.setNoOfRentalPaid(validationServiceStringToLong(internalLiabilityAddResource.getNoOfRentalPaid()));
		internalLiability.setWriteOff(internalLiabilityAddResource.getWriteOff());
		internalLiability.setCompany(internalLiabilityAddResource.getCompany());
		internalLiability.setGroupCompany(internalLiabilityAddResource.getGroupCompany());
		internalLiability.setStatus(CommonStatus.ACTIVE.toString());
		internalLiability.setNote(internalLiabilityAddResource.getNote());
		internalLiability.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		internalLiability.setCreatedDate(validateService.getCreateOrModifyDate());
//		internalLiability.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
//		internalLiability.setModifiedDate(validateService.getCreateOrModifyDate());
		internalLiability.setSyncTs(validateService.getCreateOrModifyDate());
		internalLiability = internalLiabilityRepository.save(internalLiability);
		
		LoggerRequest.getInstance().logInfo1("-----------save internal liability-----------------------------");
		
		return 	internalLiability;
	}

	@Override
	public Customer updateInternalLiabilityList(Long customerId, List<InternalLiabilityUpdateResource> internalLiabilityUpdateResourceList) {

		LoggerRequest.getInstance().logInfo1("----------- validate customer -----------------------------");
		Optional<Customer> customerOptional = customerRepository.findById(customerId);
		if(!customerOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("invalid.customer"), "message");
		}
		
		LoggerRequest.getInstance().logInfo1("----------- start update internal liabilities list-----------------------------");
		
		if((internalLiabilityUpdateResourceList != null) && (!internalLiabilityUpdateResourceList.isEmpty())) {
			for(InternalLiabilityUpdateResource resource : internalLiabilityUpdateResourceList) {
				udpateInternalLiability(resource);
			}
		}
		
		return customerOptional.get();
		
		
	}
	
	
	/**
 *    need to be add validations in service layer and finalize the the task . at the moment of developing
 *    there was a unresolved dependancy get internal liabilities details
	 */
	public void udpateInternalLiability(InternalLiabilityUpdateResource internalLiabilityUpdateResource) {
		
		Optional<InternalLiability> internalLiabilityOptional = internalLiabilityRepository.findById(validationServiceStringToLong(internalLiabilityUpdateResource.getId()));
		if(!internalLiabilityOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("internalliabilty.not-found"), "message");
		}
		
		InternalLiability internalLiability = internalLiabilityOptional.get();
		
		if(internalLiability.getVersion() != validationServiceStringToLong((internalLiabilityUpdateResource.getVersion()))){
			throw new ValidateRecordException(environment.getProperty("common-invalid.versionV"), "version");
		}
		
		
		
		internalLiability.setFacilityTypeId(validationServiceStringToLong(internalLiabilityUpdateResource.getFacilityTypeId()));
		internalLiability.setFacilityTypeCode(internalLiabilityUpdateResource.getFacilityTypeCode());
		internalLiability.setFacilityTypeName(internalLiabilityUpdateResource.getFacilityType());
		internalLiability.setCustomerTypeId(validationServiceStringToLong(internalLiabilityUpdateResource.getCustomerTypeId()));
		internalLiability.setCustomerTypeCode(internalLiabilityUpdateResource.getCustomerTypeCode());
		internalLiability.setCustomerTypeName(internalLiabilityUpdateResource.getCustomerType());
		internalLiability.setSecurityTypeId(validationServiceStringToLong(internalLiabilityUpdateResource.getAssetTypeId()));
		internalLiability.setSecurityTypeCode(internalLiabilityUpdateResource.getAssetTypeCode());
		internalLiability.setSecurityTypeName(internalLiabilityUpdateResource.getAssetType());
		internalLiability.setRepaymentTypeId(validationServiceStringToLong(internalLiabilityUpdateResource.getRepaymentTypeId()));
		internalLiability.setRepaymentTypeCode(internalLiabilityUpdateResource.getRepaymentTypeCode());
		internalLiability.setRepaymentTypeName(internalLiabilityUpdateResource.getRepaymentType());
		internalLiability.setFacilityCreatedUser(internalLiabilityUpdateResource.getFacilityCreatedUser());
		internalLiability.setFacilityCreatedDate(validationServiceStringToDate(internalLiabilityUpdateResource.getFacilityIssueDate()));
		internalLiability.setFacilityAccountNo(internalLiabilityUpdateResource.getAccountNo());
		internalLiability.setFacilityStatus(internalLiabilityUpdateResource.getStatus());
		internalLiability.setFacilityAmount(validationServiceStringToBigDecimal(internalLiabilityUpdateResource.getFacilityAmount()));
		internalLiability.setCurrentInstallment(validationServiceStringToBigDecimal(internalLiabilityUpdateResource.getCurrentInstallment()));
		internalLiability.setCurrentDue(validationServiceStringToBigDecimal(internalLiabilityUpdateResource.getCurrentDue()));
		internalLiability.setOverDue(validationServiceStringToBigDecimal(internalLiabilityUpdateResource.getOverDue()));
		internalLiability.setAvaliableBalanceLimit(validationServiceStringToBigDecimal(internalLiabilityUpdateResource.getAvaliableBalance()));
		internalLiability.setNoOfRentalPaid(validationServiceStringToLong(internalLiabilityUpdateResource.getNoOfRentalPaid()));
		internalLiability.setWriteOff(internalLiabilityUpdateResource.getWriteOff());
		internalLiability.setCompany(internalLiabilityUpdateResource.getCompany());
		internalLiability.setGroupCompany(internalLiabilityUpdateResource.getGroupCompany());
		internalLiability.setStatus(CommonStatus.ACTIVE.toString());
		internalLiability.setNote(internalLiabilityUpdateResource.getNote());
		internalLiability.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		internalLiability.setModifiedDate(validateService.getCreateOrModifyDate());
		internalLiability.setSyncTs(validateService.getCreateOrModifyDate());
		internalLiability = internalLiabilityRepository.save(internalLiability);
		
		LoggerRequest.getInstance().logInfo1("-----------updated internal liability-----------------------------");
	
	
	}
	
	@Override
	public List<InternalLiability> findByCustomerId(Long customerId) {
		
		Optional<Customer> customerOptional = customerRepository.findById(customerId);
		if(!customerOptional.isPresent()) {
			List<InternalLiability> internalLiabilityList = new ArrayList<>();
			return internalLiabilityList;
		}
		
		List<InternalLiability> internalLiabilityList = internalLiabilityRepository.findAllByCustomer(customerOptional.get());
		return internalLiabilityList;
	}
	
	
	public BigDecimal validationServiceStringToBigDecimal(String value){
		if(value != null) {
			return validateService.stringToBigDecimal(value);
		}else {
			return null;
		}
	}
	
	
	public Long validationServiceStringToLong(String value){
		if(value != null) {
			return validateService.stringToLong(value);
		}else {
			return null;
		}
	}
	
	public Date validationServiceStringToDate(String date){
		if(date != null) {
			Date formatDate = validateService.formatDate(date);			
			formatDate = new Date(formatDate.getTime()+(1000 * 60 * 60 * 24));
			return formatDate;
		}else {
			return null;
		}
	}

}
