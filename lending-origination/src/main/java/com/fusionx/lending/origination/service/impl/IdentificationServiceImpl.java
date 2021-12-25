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
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ContactDetail;
import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.IdentificationDetail;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.TransferType;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.IdentificationDetailRepository;
import com.fusionx.lending.origination.repository.LinkedPersonRepository;
import com.fusionx.lending.origination.resource.IdentificationDetailResource;
import com.fusionx.lending.origination.resource.IdentificationDetailResponse;
import com.fusionx.lending.origination.resource.IdentificationType;
import com.fusionx.lending.origination.resource.IdentificationValidateResource;
import com.fusionx.lending.origination.service.IdentificationService;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class IdentificationServiceImpl  extends MessagePropertyBase implements IdentificationService{

	@Value("${comn-person-identification-type}")
    private String urlPersonIdentificationType;
	
	@Autowired
	IdentificationDetailRepository identificationDetailRepository;
	
	@Autowired
	LinkedPersonRepository linkedPersonRepository;
	
	@Autowired
	RemoteService remoteService;
	
	@Autowired
	ValidateService validateService;
	@Override
	public void setIdentification(List<IdentificationDetailResource> identifications, String tenantId, String userName,
			Customer customer, Guarantor guarantor) {
		
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		for(IdentificationDetailResource identification:identifications) {
			IdentificationDetail identificationDetail=new IdentificationDetail();
			List<IdentificationDetail> identificationDetails;
//			Optional<IdentificationDetail> identificationDetailOpt = identificationDetailRepository.findByGuarantorIdAndIdentificationTypeAndIdentificationNo(guarantor.getId(),identification.getIdentificationType(),identification.getIdentificationNo());
//			if(identificationDetailOpt.isPresent()) {
//				throw new ValidateRecordException(environment.getProperty("identification.exists"), "identification");
//			}
	
			//IdentificationDetailResponse identificationDetailResponse = validateService.validateIdentificationDetail(tenantId, identification.getIdentificationTypeId(), identification.getIdentificationType(), identification.getIdentificationNo());
			
			
		if(identification.getId()!=null && !identification.getId().isEmpty() ) {
			Optional<IdentificationDetail> opIdentificationDetail = identificationDetailRepository.findByIdAndStatus(Long.parseLong(identification.getId()), CommonStatus.ACTIVE.toString());
			if(opIdentificationDetail.isPresent()) {
				identificationDetail=opIdentificationDetail.get();
				identificationDetail.setModifiedUser(userName);
				identificationDetail.setModifiedDate(getCreateOrModifyDate());
			}else {
	        	throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "identification");

			}
		}
		identificationDetail.setTenantId(tenantId);
		identificationDetail.setStatus("ACTIVE");
		if(customer!=null) {
			serviceValidationPerIdentification(identification, tenantId,customer.getPenPerId(),customer.getPerId());
			identificationDetail.setCustomer(customer);
		}
		
		if(guarantor!=null) {
			if(identification.getId()!=null && !identification.getId().isEmpty()) {
				
				identificationDetails = identificationDetailRepository.findByGuarantorIdAndIdentificationTypeIdAndIdNotIn(guarantor.getId(), validateService.stringToLong(identification.getIdentificationTypeId()), validateService.stringToLong(identification.getId()));
				if(!identificationDetails.isEmpty()) {
					throw new ValidateRecordException(environment.getProperty("identificationType.duplicate"), "identificationType");
				}
			}else {
				identificationDetails = identificationDetailRepository.findByGuarantorIdAndIdentificationTypeId(guarantor.getId(), validateService.stringToLong(identification.getIdentificationTypeId()));
				if(!identificationDetails.isEmpty()) {
					throw new ValidateRecordException(environment.getProperty("identificationType.duplicate"), "identificationType");
				}
			}
			
			serviceValidationPerIdentification(identification, tenantId,guarantor.getPenPerId(),guarantor.getPerId());
			identificationDetail.setGuarantor(guarantor);
		}

		
		if(!identification.getExpiryDate().isEmpty())
		identificationDetail.setExpiryDate(validateService.formatDate(identification.getExpiryDate()));
		
		if(!identification.getIssueDate().isEmpty())
		identificationDetail.setIssueDate(validateService.formatDate(identification.getIssueDate()));
		if(identification.getIdentificationTypeId()!=null && !identification.getIdentificationTypeId().isEmpty()) {
		identificationDetail.setIdentificationTypeId(Long.parseLong(identification.getIdentificationTypeId()));
		}
		identificationDetail.setIdentificationType(identification.getIdentificationType());
		identificationDetail.setIdentificationNo(identification.getIdentificationNo());
		
		identificationDetail.setCreatedUser(userName);
		identificationDetail.setCreatedDate(getCreateOrModifyDate());
		identificationDetail.setSyncTs(getCreateOrModifyDate());
		
		if(identification.getPidtId()!=null && !identification.getPidtId().isEmpty() ) {

		identificationDetail.setPidtId(Long.parseLong(identification.getPidtId()));
		}
		
		if(identification.getPpidtId()!=null && !identification.getPpidtId().isEmpty() ) {
		identificationDetail.setPpidtId(Long.parseLong(identification.getPpidtId()));
		}
		identificationDetailRepository.save(identificationDetail);
		}
	}

	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	private void serviceValidationPerIdentification(IdentificationDetailResource perIdentification, String tenantId,String penPersonId, String personId) {

		
		if(perIdentification.getPidtId()!=null && !perIdentification.getPidtId().isEmpty() &&
				(personId==null || !remoteService.existsPersonIdentification(tenantId, Long.parseLong(personId), 
						remoteService.stringToLong(perIdentification.getPidtId())))) {
				throw new InvalidServiceIdException(environment.getProperty("common.not-match"), ServiceEntity.IDENTIFICATION_ID);
		}
		if(perIdentification.getPpidtId()!=null && !perIdentification.getPpidtId().isEmpty() &&
				!remoteService.existsPendingPersonIdentification(tenantId, remoteService.stringToLong(perIdentification.getPpidtId()))) {
				throw new InvalidServiceIdException(environment.getProperty("common.not-match"), ServiceEntity.PENDING_IDENTIFICATION_ID);
		}
		if(perIdentification.getIdentificationTypeId()!=null && !perIdentification.getIdentificationTypeId().isEmpty()) {
			IdentificationType identificationType=(IdentificationType)remoteService.checkIsExist(tenantId, perIdentification.getIdentificationTypeId(), urlPersonIdentificationType, IdentificationType.class);
			if(identificationType==null || identificationType.getServiceStatus()==null) {
				if(identificationType==null || !identificationType.getIdtpDesc().equalsIgnoreCase(perIdentification.getIdentificationType()))
					throw new InvalidServiceIdException(environment.getProperty("common.not-match"), ServiceEntity.IDENTIFICATION_TYPE);
				
				if(!identificationType.getIdtpStatus().equals("ACTIVE")) {
					throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.IDENTIFICATION_TYPE);
				}
			}else {
				remoteService.serviceValidationExceptionHadle(identificationType.getServiceStatus(), ServiceEntity.IDENTIFICATION_TYPE,ServicePoint.IDENTIFICATION);
			}
			IdentificationValidateResource identificationValidateResource=new IdentificationValidateResource();
			identificationValidateResource.setIdtpCode(identificationType.getIdtpCode());
			identificationValidateResource.setInput(perIdentification.getIdentificationNo());
			if(!remoteService.validatePersonIdentification(tenantId, identificationValidateResource))
				throw new InvalidServiceIdException(environment.getProperty("common.invalid-value"), ServiceEntity.IDENTIFICATION_NO);
			
			
		}
		Long pendingPersonId = null;
		Long perId = null;
		if(penPersonId!=null && !penPersonId.isEmpty()) {
			pendingPersonId = Long.parseLong(penPersonId);
		}
			
		if(personId!=null && !personId.isEmpty()) {
			perId = Long.parseLong(personId);
		}
			
		if(pendingPersonId!=null || perId!=null)
		serviceValidationPerIdentificationType(perIdentification, tenantId, pendingPersonId, perId);
		
		serviceValidationPerIdentificationNumber(tenantId, perIdentification);
		
	}

	@Override
	public void identificationValidationCrib(IdentificationDetailResource identificationDetailRes, String tenantId, TransferType transferType, Integer index) {

		
		if(identificationDetailRes.getPidtId()!=null || identificationDetailRes.getPpidtId()!=null) {
			if(identificationDetailRes.getPidtId()!=null && !identificationDetailRes.getPidtId().isEmpty() &&
					(identificationDetailRes.getPerId()==null || !remoteService.existsPersonIdentification(tenantId, Long.parseLong(identificationDetailRes.getPerId()), remoteService.stringToLong(identificationDetailRes.getPidtId())))) {
					throw new InvalidServiceIdException(environment.getProperty("common.not-match"), ServiceEntity.IDENTIFICATION_ID);
			}
			if(identificationDetailRes.getPpidtId()!=null && !identificationDetailRes.getPpidtId().isEmpty() &&
					!remoteService.existsPendingPersonIdentification(tenantId, remoteService.stringToLong(identificationDetailRes.getPpidtId()))) {
					throw new InvalidServiceIdException(environment.getProperty("common.not-match"), ServiceEntity.PENDING_IDENTIFICATION_ID);
			}
		}
		
		if(identificationDetailRes.getIdentificationTypeId()!=null && !identificationDetailRes.getIdentificationTypeId().isEmpty()) {
			IdentificationType identificationType=(IdentificationType)remoteService.checkIsExist(tenantId, identificationDetailRes.getIdentificationTypeId(), urlPersonIdentificationType, IdentificationType.class);
			if(identificationType==null || identificationType.getServiceStatus()==null) {
				if(identificationType==null || !identificationType.getIdtpDesc().equalsIgnoreCase(identificationDetailRes.getIdentificationType()))
					throw new InvalidServiceIdException(environment.getProperty("common.not-match"), ServiceEntity.IDENTIFICATION_TYPE);
					//throw new InvalidDetailListServiceIdException(environment.getProperty("common.not-match"), ServiceEntity.IDENTIFICATION_TYPE, transferType,index);
					
			}else {
				
				remoteService.serviceValidationExceptionHadle(identificationType.getServiceStatus(), ServiceEntity.IDENTIFICATION_TYPE,ServicePoint.IDENTIFICATION);
				//remoteService.serviceValidationExceptionHadle(identificationType.getServiceStatus(), ServiceEntity.IDENTIFICATION_TYPE,ServicePoint.IDENTIFICATION);
			}
			IdentificationValidateResource identificationValidateResource=new IdentificationValidateResource();
			identificationValidateResource.setIdtpCode(identificationType.getIdtpCode());
			identificationValidateResource.setInput(identificationDetailRes.getIdentificationNo());
			if(!remoteService.validatePersonIdentification(tenantId, identificationValidateResource))
				throw new InvalidServiceIdException(environment.getProperty("common.invalid-value"), ServiceEntity.IDENTIFICATION_NO);
				//throw new InvalidDetailListServiceIdException(environment.getProperty("common.invalid-value"), ServiceEntity.IDENTIFICATION_NO, transferType,index);
				
			
		}
	}
	
	private void serviceValidationPerIdentificationType(IdentificationDetailResource perIdentification, String tenantId, Long penPersonId, Long personId) { 
		Long pidtId = null;
		Long ppidtId = null;
		
		if(perIdentification.getPidtId()!=null && !perIdentification.getPidtId().isEmpty())
			pidtId = Long.parseLong(perIdentification.getPidtId());
		
		if(perIdentification.getPpidtId()!=null && !perIdentification.getPpidtId().isEmpty())
			ppidtId = Long.parseLong(perIdentification.getPpidtId());
		
		Boolean status=remoteService.validatePersonIdentificationType(tenantId, personId, penPersonId, pidtId,ppidtId, remoteService.stringToLong(perIdentification.getIdentificationTypeId()));
			if(status) {
				throw new InvalidServiceIdException(environment.getProperty("common.unique"), ServiceEntity.IDENTIFICATION_TYPE);
			}
		}




	private void serviceValidationPerIdentificationNumber(String tenantId, IdentificationDetailResource perIdentification) { 
		Long pidtId = null;
		Long ppidtId = null;
		
		if(perIdentification.getPidtId()!=null && !perIdentification.getPidtId().isEmpty())
			pidtId = Long.parseLong(perIdentification.getPidtId());
		
		if(perIdentification.getPpidtId()!=null && !perIdentification.getPpidtId().isEmpty())
			ppidtId = Long.parseLong(perIdentification.getPpidtId());
		
		Boolean status=remoteService.validatePersonIdentificationNumber(tenantId, pidtId,ppidtId, Long.parseLong(perIdentification.getIdentificationTypeId()), perIdentification.getIdentificationNo());
			if(status) {
				throw new InvalidServiceIdException(environment.getProperty("common.unique"), ServiceEntity.IDENTIFICATION_NO);
			}
		}

	
	
}
