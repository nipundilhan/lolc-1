package com.fusionx.lending.origination.service.impl;
/**
 * LinkedPersonServiceImpl
 * 
 ********************************************************************************************************
 * ### 		Date 			Story Point 	Task No 	Author 		Description
 * -------------------------------------------------------------------------------------------------------
 * 1 		03-08-2021 		FXL-381			FXL-417		Piyumi	 	Created
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
import com.fusionx.lending.origination.domain.IdentificationDetail;
import com.fusionx.lending.origination.domain.LinkedPerson;
import com.fusionx.lending.origination.enums.CommonListReferenceCode;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.enums.TransferType;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.InvalidDetailListServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.IdentificationDetailRepository;
import com.fusionx.lending.origination.repository.LinkedPersonRepository;
import com.fusionx.lending.origination.resource.CommonListRemote;
import com.fusionx.lending.origination.resource.IdentificationDetailResource;
import com.fusionx.lending.origination.resource.LinkedPersonResource;
import com.fusionx.lending.origination.service.IdentificationService;
import com.fusionx.lending.origination.service.LinkedPersonService;
import com.fusionx.lending.origination.service.RemoteService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class LinkedPersonServiceImpl extends MessagePropertyBase implements LinkedPersonService {
	
	@Autowired
	ValidateService validateService;
	
	@Autowired
	private LinkedPersonRepository linkedPersonRepository;
	
	@Autowired
	private IdentificationDetailRepository identificationDetailRepository;
	
	@Autowired
	ValidateService service;
	
	@Autowired
	private IdentificationService identificationService;
	
	
	@Autowired
	RemoteService remoteService;


	
	@Override
	public void saveLinkedPerson(String tenantId, Customer customer , List<LinkedPersonResource> linkedPersonResourceList) {
		Integer indexRelation = 0;		
		for(LinkedPersonResource linkedPersonResource: linkedPersonResourceList) {
			
			// validate RelationshipType
			validateService.validatePersonCommonList(tenantId,linkedPersonResource.getRelationshipTypeId(),linkedPersonResource.getRelationshipTypeName(),
					CommonListReferenceCode.RELATIONSHIPTYPE.toString(), indexRelation, ServiceEntity.RELATIONSHIP_TYPE_ID, ServicePoint.LINKED_PERSON, null);
			
			validateService.validatePersonCommonList(tenantId,linkedPersonResource.getGenderId(),linkedPersonResource.getGender(),
						CommonListReferenceCode.GENDER.toString(), indexRelation, ServiceEntity.GENDER_ID, ServicePoint.LINKED_PERSON, null);
				
			validateService.validatePersonCommonList(tenantId,linkedPersonResource.getTitleId(),linkedPersonResource.getTitle(),
					CommonListReferenceCode.TITLE.toString(), indexRelation, ServiceEntity.TITLE, ServicePoint.LINKED_PERSON, null);		
			
			//person validation
			if(linkedPersonResource.getPerId()!=null && !linkedPersonResource.getPerId().isEmpty())
				validateService.validateComnPerson(tenantId,linkedPersonResource.getPerId(),linkedPersonResource.getPerCode(),ServicePoint.LINKED_PERSON);
		
			//penPerson validation
			if(linkedPersonResource.getPenPerId() != null && linkedPersonResource.getPerId().isEmpty())
				validateService.validatePendingPerson(tenantId,linkedPersonResource.getPenPerId(),ServicePoint.LINKED_PERSON);
			
			LinkedPerson linkedPerson = new LinkedPerson();
			
			if(linkedPersonResource.getId()!=null && !linkedPersonResource.getId().isEmpty()) {
				Optional<LinkedPerson> linkedPersonResourceOpt = linkedPersonRepository.findById(Long.parseLong(linkedPersonResource.getId()));
				if(linkedPersonResourceOpt.isPresent()) {
					linkedPerson=linkedPersonResourceOpt.get();
					linkedPerson.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
					linkedPerson.setModifiedDate(validateService.getCreateOrModifyDate());
				}else {
					throw new DetailListValidateException(environment.getProperty(RECORD_NOT_FOUND) ,
							ServiceEntity.ID,ServicePoint.LINKED_PERSON , indexRelation);

				}
			}else {
				linkedPerson.setCreatedDate(validateService.getCreateOrModifyDate());
				linkedPerson.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			}
			
			linkedPerson.setTenantId(tenantId);
			linkedPerson.setLinkedPersonType(linkedPersonResource.getLinkedPersonType());
			linkedPerson.setCustomer(customer);
			linkedPerson.setName(linkedPersonResource.getFullname());
			linkedPerson.setFirstName(linkedPersonResource.getFirstName());
			linkedPerson.setMiddleName(linkedPersonResource.getMiddleName());
			linkedPerson.setLastName(linkedPersonResource.getLastName());
			linkedPerson.setTitleId(validateService.stringToLong(linkedPersonResource.getTitleId()));
			linkedPerson.setTitle(linkedPersonResource.getTitle());
			linkedPerson.setGenderId(validateService.stringToLong(linkedPersonResource.getGenderId()));
			linkedPerson.setGender(linkedPersonResource.getGender());
			linkedPerson.setRelationId(validateService.stringToLong(linkedPersonResource.getRelationshipTypeId()));
			linkedPerson.setRelationshipType(linkedPersonResource.getRelationshipTypeName());
			linkedPerson.setStatus(CommonStatus.valueOf(linkedPersonResource.getStatus()).toString());
			
			if(linkedPersonResource.getPerId() != null && !linkedPersonResource.getPerId().isEmpty()) {
				linkedPerson.setPerId(Long.parseLong(linkedPersonResource.getPerId()));
				linkedPerson.setPerCode(linkedPersonResource.getPerCode());
			}
			
			if(linkedPersonResource.getPenPerId()!=null && !linkedPersonResource.getPenPerId().isEmpty())
				linkedPerson.setPenPerId(Long.parseLong(linkedPersonResource.getPenPerId()));
			
			linkedPerson.setSyncTs(validateService.getCreateOrModifyDate());	
			linkedPerson = linkedPersonRepository.saveAndFlush(linkedPerson);
			
			
				if((linkedPersonResource.getIdentificationDetailList() == null || linkedPersonResource.getIdentificationDetailList().isEmpty()) && linkedPersonResource.getId() == null) 
					throw new DetailListValidateException(environment.getProperty(IDENTIFICATION_LIST_NOT_NULL) ,
							ServiceEntity.ID,ServicePoint.LINKED_PERSON , indexRelation);
					
					for(IdentificationDetailResource identificationDetailResource: linkedPersonResource.getIdentificationDetailList()) {					
					
					//validate identification details
					IdentificationDetailResource identificationDetailResr = new IdentificationDetailResource();
					
					if(identificationDetailResource.getPidtId()!=null && !identificationDetailResource.getPidtId().isEmpty())
						identificationDetailResr.setPidtId(identificationDetailResource.getPidtId());
					
					if(identificationDetailResource.getPpidtId()!=null && !identificationDetailResource.getPpidtId().isEmpty())
						identificationDetailResr.setPpidtId(identificationDetailResource.getPpidtId());
					
					identificationDetailResr.setIdentificationTypeId(identificationDetailResource.getIdentificationTypeId());
					identificationDetailResr.setIdentificationType(identificationDetailResource.getIdentificationType());
					identificationDetailResr.setIdentificationNo(identificationDetailResource.getIdentificationNo());
					identificationService.identificationValidationCrib(identificationDetailResr, tenantId, null, indexRelation);
					
					IdentificationDetail identificationDetail=new IdentificationDetail();
					
					if(identificationDetailResource.getId()!=null && !identificationDetailResource.getId().isEmpty()) {
						Optional<IdentificationDetail> identificationDetailOpt = identificationDetailRepository.findById(Long.parseLong(identificationDetailResource.getId()));
						if(identificationDetailOpt.isPresent()) {
							identificationDetail=identificationDetailOpt.get();
							identificationDetail.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
							identificationDetail.setModifiedDate(validateService.getCreateOrModifyDate());
						}else {
				        	throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "linkedPsnIdentification");

						}
					}else {
						identificationDetail.setCreatedDate(validateService.getCreateOrModifyDate());
						identificationDetail.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
					}
					
					identificationDetail.setTenantId(tenantId);
					identificationDetail.setStatus(CommonStatus.valueOf(identificationDetailResource.getStatus()).toString());
					identificationDetail.setLinkPerson(linkedPerson);
					identificationDetail.setIdentificationTypeId(Long.parseLong(identificationDetailResource.getIdentificationTypeId()));
					identificationDetail.setIdentificationType(identificationDetailResource.getIdentificationType());
					identificationDetail.setIdentificationNo(identificationDetailResource.getIdentificationNo());
					identificationDetail.setSyncTs(validateService.getCreateOrModifyDate());
					
					if(!identificationDetailResource.getExpiryDate().isEmpty())
						identificationDetail.setExpiryDate(validateService.formatDate(identificationDetailResource.getExpiryDate()));						
					
					if(!identificationDetailResource.getIssueDate().isEmpty())
						identificationDetail.setIssueDate(validateService.formatDate(identificationDetailResource.getIssueDate()));
					
						identificationDetail = identificationDetailRepository.saveAndFlush(identificationDetail);
						identificationDetailResource.setId(identificationDetail.getId().toString());
					}		
					
					linkedPersonResource.setId(linkedPerson.getId().toString());		
					indexRelation++;
				
		}
					
	}
	

}
