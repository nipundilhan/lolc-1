package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.domain.ContactDetail;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.IdentificationDetail;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ContactDetailRepository;
import com.fusionx.lending.origination.resource.ContactDetailsResource;
import com.fusionx.lending.origination.resource.ContactType;
import com.fusionx.lending.origination.resource.ContactValidateResource;
import com.fusionx.lending.origination.service.ContactDetailService;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ContactDetailServiceImpl extends MessagePropertyBase implements ContactDetailService{

	@Autowired
	ContactDetailRepository contactDetailRepository;

	@Autowired
	ValidateService validateService;
	
	@Autowired
	RemoteService remoteService;
	
	@Value("${comn-person-contact-type}")
    private String urlPersonContactType;
	
	@Override
	public void setContact(List<ContactDetailsResource> contacts, String tenantId, String userName,
			Customer customer, Guarantor guarantor) {
		for(ContactDetailsResource contact:contacts) {
			ContactDetail contactDetail=new ContactDetail();
			List<ContactDetail> contactDetails;
			if(contact.getId()!=null && !contact.getId().isEmpty() ) {
				Optional<ContactDetail> opIdentificationDetail = contactDetailRepository.findById(Long.parseLong(contact.getId()));
				if(opIdentificationDetail.isPresent()) {
					contactDetail=opIdentificationDetail.get();
					contactDetail.setModifiedUser(userName);
					contactDetail.setModifiedDate(getCreateOrModifyDate());
				}else {
		        	throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "contact");

				}
			}
			serviceValidationPerContact(contact, tenantId);
			
			contactDetail.setTenantId(tenantId);
			contactDetail.setStatus("ACTIVE"); 
			
			if(customer!=null) {
				contactDetail.setCustomer(customer);
			}
			
			if(guarantor!=null) {
				
				if(contact.getId()!=null && !contact.getId().isEmpty()) {
					
					contactDetails = contactDetailRepository.findByGuarantorIdAndContactTypeIdAndIdNotIn(guarantor.getId(), validateService.stringToLong(contact.getContactTypeId()), validateService.stringToLong(contact.getId()));
					if(!contactDetails.isEmpty()) {
						throw new ValidateRecordException(environment.getProperty("contactType.duplicate"), "contactType");
					}
				}else {
					  contactDetails = contactDetailRepository.findByGuarantorIdAndContactTypeId(guarantor.getId(), validateService.stringToLong(contact.getContactTypeId()));
					  if(!contactDetails.isEmpty()) {
						 throw new ValidateRecordException(environment.getProperty("contactType.duplicate"), "contactType");
					  }
				}
				
				contactDetail.setGuarantor(guarantor);
			}
			
			
			if(contact.getContactTypeId()!=null)
			contactDetail.setContactTypeId(Long.parseLong(contact.getContactTypeId()));
			contactDetail.setContactType(contact.getContactType());
			contactDetail.setContactNo(contact.getContactNo());
			
			contactDetail.setCreatedUser(userName);
			contactDetail.setCreatedDate(getCreateOrModifyDate());
			contactDetail.setSyncTs(getCreateOrModifyDate());
			contactDetailRepository.save(contactDetail);
			}
	}
	
	
	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	private void serviceValidationPerContact(ContactDetailsResource perContact, String tenantId) {
	/*	if(perContact.getPconId()!=null && !perContact.getPconId().isEmpty() &&
				(perContact.get==null || !remoteService.existsPersonContact(tenantId, personId, remoteService.stringToLong(perContact.getPconId())))) {
				throw new InvalidServiceIdException(environment.getProperty("pconContactTypeId.not-match"), ServiceEntity.CONTACT_ID);
		}*/
		if(perContact.getContactTypeId()!=null && !perContact.getContactTypeId().isEmpty()) {
			ContactType contactType=(ContactType)remoteService.checkIsExist(tenantId, perContact.getContactTypeId(), urlPersonContactType, ContactType.class);
			if(contactType==null || contactType.getServiceStatus()==null) {
				if(contactType==null || !contactType.getCntpDesc().equalsIgnoreCase(perContact.getContactType())) {
					throw new InvalidServiceIdException(environment.getProperty("common.not-match"), ServiceEntity.CONTACT_TYPE);
				}
				if(!contactType.getCntpStatus().equals("ACTIVE")) {
					throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.CONTACT_TYPE);
				}
			}else {
				remoteService.serviceValidationExceptionHadle(contactType.getServiceStatus(), ServiceEntity.CONTACT_TYPE, ServicePoint.CONTACT);
			}
			ContactValidateResource contactValidateResource=new ContactValidateResource();
			contactValidateResource.setCntpCode(contactType.getCntpCode());
			contactValidateResource.setInput(perContact.getContactNo());
			if(!remoteService.validatePersonContact(tenantId, contactValidateResource))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.CONTACT_VALUE);
		}
	}

}
