package com.fusionx.lending.origination.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.domain.MobileNotebook;
import com.fusionx.lending.origination.enums.MobileNotebookStatusEnum;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.repository.MobileNotebookRepository;
import com.fusionx.lending.origination.resource.AddToLeadResource;
import com.fusionx.lending.origination.resource.AssetTypeSubType;
import com.fusionx.lending.origination.resource.ContactType;
import com.fusionx.lending.origination.resource.IdentificationType;
import com.fusionx.lending.origination.resource.MobileNotebookAddResource;
import com.fusionx.lending.origination.resource.MobileNotebookDeleteResource;
import com.fusionx.lending.origination.resource.MobileNotebookReminderResponseResource;
import com.fusionx.lending.origination.resource.MobileNotebookUpdateResource;
import com.fusionx.lending.origination.service.MobileNotebookService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * Mobile Notebook Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-06-2021   		         FX-6506    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class MobileNotebookServiceImpl extends MessagePropertyBase implements MobileNotebookService {
	
	@Autowired
	private MobileNotebookRepository mobileNotebookRepository;
	
	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private CommonListRepository commonListRepository;

	/**
	 * @author Senitha
	 * 
	 * Find all Mobile Notebook
	 * @return -JSON array of all Mobile Notebook
	 * 
	 * */
	@Override
	public Page<MobileNotebook> getAll(String tenantId, Pageable pageable) {
		
		Page<MobileNotebook> mobileNotebooks =  mobileNotebookRepository.findAll(pageable);
		for(MobileNotebook mobileNotebook : mobileNotebooks) {
			setName(tenantId, mobileNotebook);
		}
		return mobileNotebooks;
		
	}

	/**
	 * @author Senitha
	 * 
	 * Find Mobile Notebook by ID
	 * @return -JSON array of Mobile Notebook
	 * 
	 * */
	@Override
	public Optional<MobileNotebook> getById(String tenantId, Long id) {
		
		Optional<MobileNotebook> isPresentMobileNotebook = mobileNotebookRepository.findById(id);
		if (isPresentMobileNotebook.isPresent()) {
			MobileNotebook mobileNotebook = isPresentMobileNotebook.get();
			setName(tenantId, mobileNotebook);
			return Optional.ofNullable(mobileNotebook);
		}
		else {
			return Optional.empty(); 
		}
		
	}

	/**
	 * @author Senitha
	 * 
	 * Find Mobile Notebook by Customer Name
	 * @return -JSON array of Mobile Notebook
	 * 
	 * */
	@Override
	public List<MobileNotebook> searchByCustomerName(String tenantId, String customerName) {

		List<MobileNotebook> mobileNotebooks =  mobileNotebookRepository.findByCustomerFullNameContaining(customerName);
		for(MobileNotebook mobileNotebook : mobileNotebooks) {
			setName(tenantId, mobileNotebook);
		}
		return mobileNotebooks;
		
	}

	/**
	 * @author Senitha
	 * 
	 * Find Mobile Notebook by NIC Number
	 * @return -JSON array of Mobile Notebook
	 * 
	 * */
	@Override
	public List<MobileNotebook> searchByNicNo(String tenantId, String nicNo) {

		List<MobileNotebook> mobileNotebooks =  mobileNotebookRepository.findByNicNoContaining(nicNo);
		for(MobileNotebook mobileNotebook : mobileNotebooks) {
			setName(tenantId, mobileNotebook);
		}
		return mobileNotebooks;
		
	}

	/**
	 * @author Senitha
	 * 
	 * Find Mobile Notebook by status
	 * @return -JSON array of Mobile Notebook
	 * 
	 * */
	@Override
	public List<MobileNotebook> getByStatus(String tenantId, String status) {

		List<MobileNotebook> mobileNotebooks =  mobileNotebookRepository.findByStatus(MobileNotebookStatusEnum.valueOf(status));
		for(MobileNotebook mobileNotebook : mobileNotebooks) {
			setName(tenantId, mobileNotebook);
		}
		return mobileNotebooks;
		
	}

	/**
	 * @author Senitha
	 * 
	 * Find Mobile Notebook by on boarding status
	 * @return -JSON array of Mobile Notebook
	 * 
	 * */
	@Override
	public List<MobileNotebook> getByOnboardingStatus(String tenantId, String onboardingStatus) {
		
		List<MobileNotebook> mobileNotebooks =  mobileNotebookRepository.findByOnboardingStatus(MobileNotebookStatusEnum.valueOf(onboardingStatus));
		for(MobileNotebook mobileNotebook : mobileNotebooks) {
			setName(tenantId, mobileNotebook);
		}
		return mobileNotebooks;
	}
	
	/**
	 * @author Senitha
	 * 
	 * Generate Notebook Reminder
	 * @return -JSON array of Mobile Notebook Reminder Response Resource
	 * 
	 * */
	@Override
	public List<MobileNotebookReminderResponseResource> generateReminder() {
		
		String formatedSysDate = formatedSysDate(getCreateOrModifyDate()) ;
		return  mobileNotebookRepository.findBySystemDate(formatedSysDate);
		
	}


	/**
	 * @author Senitha
	 * 
	 * Insert Mobile Notebook
	 * @param  - MobileNotebookAddResource
	 * @return - Successfully saved
	 * 
	 * */
	@Override
	public MobileNotebook addMobileNotebook(String tenantId, MobileNotebookAddResource mobileNotebookAddResource) {

		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty("common.not-null"), "username");
        
		LoggerRequest.getInstance().logInfo("/******************************** Validate Input Data *********************************************/");
        validateRecord("ADD", tenantId, mobileNotebookAddResource, new MobileNotebookUpdateResource(), new AddToLeadResource());
        
        MobileNotebook mobileNotebook = new MobileNotebook();
    	mobileNotebook.setTenantId(tenantId);
    	if(mobileNotebookAddResource.getIdentificationTypeId() != null && !mobileNotebookAddResource.getIdentificationTypeId().isEmpty()) {
    		mobileNotebook.setIdentificationTypeId(Long.parseLong(mobileNotebookAddResource.getIdentificationTypeId()));
    	}
    	mobileNotebook.setNicNo(mobileNotebookAddResource.getNicNo());
    	mobileNotebook.setCustomerFullName(mobileNotebookAddResource.getCustomerFullName());
    	if(mobileNotebookAddResource.getRequiredLoanAmount() != null && !mobileNotebookAddResource.getRequiredLoanAmount().isEmpty()) {
    		mobileNotebook.setRequiredLoanAmount(stringToBigDecimal(mobileNotebookAddResource.getRequiredLoanAmount()));
    	}
    	mobileNotebook.setContactTypeId(Long.parseLong(mobileNotebookAddResource.getContactTypeId()));
    	mobileNotebook.setContactNumber(Long.parseLong(mobileNotebookAddResource.getContactNumber()));
    	mobileNotebook.setCustomerLivingArea(mobileNotebookAddResource.getCustomerLivingArea());
    	mobileNotebook.setProductCode(mobileNotebookAddResource.getProductCode());
    	mobileNotebook.setProductName(mobileNotebookAddResource.getProductName());
    	if(mobileNotebookAddResource.getSecurityTypeId() != null && !mobileNotebookAddResource.getSecurityTypeId().isEmpty()) {
        	mobileNotebook.setSecurityTypeId(Long.parseLong(mobileNotebookAddResource.getSecurityTypeId()));
    	}
    	if(mobileNotebookAddResource.getSecuritySubTypeId() != null && !mobileNotebookAddResource.getSecuritySubTypeId().isEmpty()) {
    		mobileNotebook.setSecuritySubTypeId(Long.parseLong(mobileNotebookAddResource.getSecuritySubTypeId()));
    	}
    	if(mobileNotebookAddResource.getSpouseProfessionId() != null && !mobileNotebookAddResource.getSpouseProfessionId().isEmpty()) {
    		mobileNotebook.setSpouseProfessionId(Long.parseLong(mobileNotebookAddResource.getSpouseProfessionId()));
    	}
    	if(mobileNotebookAddResource.getPriorityLevelId() != null && !mobileNotebookAddResource.getPriorityLevelId().isEmpty()) {
    		mobileNotebook.setPriorityLevelId(Long.parseLong(mobileNotebookAddResource.getPriorityLevelId()));
    	}
    	mobileNotebook.setRemarks(mobileNotebookAddResource.getRemarks());
    	mobileNotebook.setNextMeetingDate(formatDate(mobileNotebookAddResource.getNextMeetingDate()));
    	mobileNotebook.setStatus(MobileNotebookStatusEnum.CREATED);
    	mobileNotebook.setOnboardingStatus(MobileNotebookStatusEnum.PENDING);
    	mobileNotebook.setSyncTs(currentTimestamp);
    	mobileNotebook.setCreatedDate(currentTimestamp);
    	mobileNotebook.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
    	mobileNotebook = mobileNotebookRepository.save(mobileNotebook);
    	return mobileNotebook;
        
	}

	/**
	 * @author Senitha
	 * 
	 * Update Mobile Notebook
	 * @param  - MobileNotebookUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	@Override
	public MobileNotebook updateMobileNotebook(String tenantId, MobileNotebookUpdateResource mobileNotebookUpdateResource) {
		
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty("common.not-null"), "username");
        
        Optional<MobileNotebook> isPresentMobileNotebook = mobileNotebookRepository.findById(Long.parseLong(mobileNotebookUpdateResource.getId()));
		if (!isPresentMobileNotebook.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
		
		if (!isPresentMobileNotebook.get().getVersion().equals(Long.parseLong(mobileNotebookUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
		
		LoggerRequest.getInstance().logInfo("/******************************** Validate Input Data *********************************************/");
        validateRecord("UPDATE", tenantId, new MobileNotebookAddResource(), mobileNotebookUpdateResource, new AddToLeadResource());
        
		MobileNotebook mobileNotebook = isPresentMobileNotebook.get();
		mobileNotebook.setTenantId(tenantId);
    	mobileNotebook.setIdentificationTypeId(Long.parseLong(mobileNotebookUpdateResource.getIdentificationTypeId()));
    	mobileNotebook.setNicNo(mobileNotebookUpdateResource.getNicNo());
    	mobileNotebook.setCustomerFullName(mobileNotebookUpdateResource.getCustomerFullName());
    	if(mobileNotebookUpdateResource.getRequiredLoanAmount() != null && !mobileNotebookUpdateResource.getRequiredLoanAmount().isEmpty()) {
    		mobileNotebook.setRequiredLoanAmount(stringToBigDecimal(mobileNotebookUpdateResource.getRequiredLoanAmount()));
    	}
    	if(mobileNotebookUpdateResource.getContactTypeId() != null && !mobileNotebookUpdateResource.getContactTypeId().isEmpty()) {
    		mobileNotebook.setContactTypeId(Long.parseLong(mobileNotebookUpdateResource.getContactTypeId()));
    	}
    	mobileNotebook.setContactNumber(Long.parseLong(mobileNotebookUpdateResource.getContactNumber()));
    	mobileNotebook.setCustomerLivingArea(mobileNotebookUpdateResource.getCustomerLivingArea());
    	mobileNotebook.setProductCode(mobileNotebookUpdateResource.getProductCode());
    	mobileNotebook.setProductName(mobileNotebookUpdateResource.getProductName());
    	if(mobileNotebookUpdateResource.getSecurityTypeId() != null && !mobileNotebookUpdateResource.getSecurityTypeId().isEmpty()) {
        	mobileNotebook.setSecurityTypeId(Long.parseLong(mobileNotebookUpdateResource.getSecurityTypeId()));
    	}
    	if(mobileNotebookUpdateResource.getSecuritySubTypeId() != null && !mobileNotebookUpdateResource.getSecuritySubTypeId().isEmpty()) {
    		mobileNotebook.setSecuritySubTypeId(Long.parseLong(mobileNotebookUpdateResource.getSecuritySubTypeId()));
    	}
    	if(mobileNotebookUpdateResource.getSpouseProfessionId() != null && !mobileNotebookUpdateResource.getSpouseProfessionId().isEmpty()) {
    		mobileNotebook.setSpouseProfessionId(Long.parseLong(mobileNotebookUpdateResource.getSpouseProfessionId()));
    	}
    	if(mobileNotebookUpdateResource.getPriorityLevelId() != null && !mobileNotebookUpdateResource.getPriorityLevelId().isEmpty()) {
    		mobileNotebook.setPriorityLevelId(Long.parseLong(mobileNotebookUpdateResource.getPriorityLevelId()));
    	}
    	mobileNotebook.setRemarks(mobileNotebookUpdateResource.getRemarks());
    	mobileNotebook.setNextMeetingDate(formatDate(mobileNotebookUpdateResource.getNextMeetingDate()));
    	mobileNotebook.setSyncTs(currentTimestamp);
    	mobileNotebook.setVersion(Long.parseLong(mobileNotebookUpdateResource.getVersion()));
    	mobileNotebook.setModifiedDate(currentTimestamp);
    	mobileNotebook.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
    	mobileNotebook = mobileNotebookRepository.saveAndFlush(mobileNotebook);
    	return mobileNotebook;
    	
	}

	/**
	 * @author Senitha
	 * 
	 * Delete Mobile Notebook
	 * @param  - MobileNotebookDeleteResource
	 * @return - Successfully saved
	 * 
	 * */
	@Override
	public MobileNotebook deleteMobileNotebook(String tenantId, MobileNotebookDeleteResource mobileNotebookDeleteResource) {

		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty("common.not-null"), "username");
        
        Optional<MobileNotebook> isPresentMobileNotebook = mobileNotebookRepository.findById(Long.parseLong(mobileNotebookDeleteResource.getId()));
		if (!isPresentMobileNotebook.isPresent())
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
		
		MobileNotebook mobileNotebook = isPresentMobileNotebook.get();
		mobileNotebook.setStatus(MobileNotebookStatusEnum.CANCELED);
		mobileNotebook.setDeletedNote(mobileNotebookDeleteResource.getDeletedNote());
		mobileNotebook.setDeletedUser(LogginAuthentcation.getInstance().getUserName());
		mobileNotebook.setDeletedDate(currentTimestamp);
    	mobileNotebook = mobileNotebookRepository.saveAndFlush(mobileNotebook);
    	return mobileNotebook;
		
	}

	/**
	 * @author Senitha
	 * 
	 * Add To Lead Mobile Notebook
	 * @param  - AddToLeadResource
	 * @return - Successfully saved
	 * 
	 * */
	@Override
	public MobileNotebook addToLead(String tenantId, AddToLeadResource addToLeadResource) {
		
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty("common.not-null"), "username");

        Optional<MobileNotebook> isPresentMobileNotebook = mobileNotebookRepository.findById(Long.parseLong(addToLeadResource.getId()));
		if (!isPresentMobileNotebook.isPresent())
			throw new ValidateRecordException(environment.getProperty("nicNoId.invalid"), "message");
		
		if(isPresentMobileNotebook.get().getNicNo().equals(addToLeadResource.getNicNo())) {
			throw new ValidateRecordException(getEnvironment().getProperty("nicNoId.invalid"), "message");
		}
		
        LoggerRequest.getInstance().logInfo("/******************************** Validate Identification Type *********************************************/");
		if(addToLeadResource.getIdentificationTypeId() != null) {
			validateService.validateIdentificationType(tenantId, Long.parseLong(addToLeadResource.getIdentificationTypeId()), addToLeadResource.getIdentificationTypeCode());
		} else {
			if (!addToLeadResource.getIdentificationTypeCode().equalsIgnoreCase("") || !addToLeadResource.getIdentificationTypeCode().isEmpty()) {
				throw new ValidateRecordException(environment.getProperty("identificationTypeId.not-match"),"identificationTypeId");
			}
		}
		 
        LoggerRequest.getInstance().logInfo("/******************************** Validate NIC Number *********************************************/");
		validateService.validateIdentificationNumber(tenantId,Long.parseLong(addToLeadResource.getIdentificationTypeId()), addToLeadResource.getNicNo());
        
		MobileNotebook mobileNotebook = isPresentMobileNotebook.get();
		mobileNotebook.setOnboardingStatus(MobileNotebookStatusEnum.COMPLETED);
		mobileNotebook.setIdentificationTypeId(Long.parseLong(addToLeadResource.getIdentificationTypeId()));
		mobileNotebook.setNicNo(addToLeadResource.getNicNo());
		mobileNotebook.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		mobileNotebook.setModifiedDate(currentTimestamp);
    	mobileNotebook = mobileNotebookRepository.saveAndFlush(mobileNotebook);
    	return mobileNotebook;
        
	}
	
	/**
	 * @author Senitha
	 * *************************************************************** Validate input data ***************************************************************
	 * */
	private void validateRecord(String action, String tenantId, MobileNotebookAddResource moiMobileNotebookAddResource, MobileNotebookUpdateResource mobileNotebookUpdateResource, AddToLeadResource addToLeadResource) {
		
		Long id = null;
		Long identificationTypeId = null;
		String identificationTypeCode = "";
		String nicNo = null;
		Long contactTypeId = null;
		String contactTypeCode = "";
		String contactNumber = "";
		Long securityTypeId = null;
		String securityTypeName = "";
		Long securitySubTypeId = null;
		String securitySubTypeName = "";
		Long spouseProfessionId = null;
		String spouseProfessionName = "";
		Long priorityLevelId = null;
		String priorityLevelName = "";
		Date nextMeetingDate = null;
		
		if (action.equals("ADD")) {
			
			identificationTypeId = Long.parseLong(moiMobileNotebookAddResource.getIdentificationTypeId());
			identificationTypeCode = moiMobileNotebookAddResource.getIdentificationTypeCode();
			nicNo = moiMobileNotebookAddResource.getNicNo();
			contactTypeId = Long.parseLong(moiMobileNotebookAddResource.getContactTypeId());
			contactTypeCode = moiMobileNotebookAddResource.getContactTypeCode();
			contactNumber = moiMobileNotebookAddResource.getContactNumber();
			if(moiMobileNotebookAddResource.getSecurityTypeId() != null && !moiMobileNotebookAddResource.getSecurityTypeId().isEmpty()) {
				securityTypeId = Long.parseLong(moiMobileNotebookAddResource.getSecurityTypeId());
			}
			securityTypeName = moiMobileNotebookAddResource.getSecurityTypeName();
			if(moiMobileNotebookAddResource.getSecuritySubTypeId() != null && !moiMobileNotebookAddResource.getSecuritySubTypeId().isEmpty()) {
				securitySubTypeId = Long.parseLong(moiMobileNotebookAddResource.getSecuritySubTypeId());
			}
			securitySubTypeName = moiMobileNotebookAddResource.getSecuritySubTypeName();
			if(moiMobileNotebookAddResource.getSpouseProfessionId() != null && !moiMobileNotebookAddResource.getSpouseProfessionId().isEmpty()) {
				spouseProfessionId = Long.parseLong(moiMobileNotebookAddResource.getSpouseProfessionId());
			}
			spouseProfessionName = moiMobileNotebookAddResource.getSpouseProfessionName();
			if(moiMobileNotebookAddResource.getPriorityLevelId() != null && !moiMobileNotebookAddResource.getPriorityLevelId().isEmpty()) {
				priorityLevelId = Long.parseLong(moiMobileNotebookAddResource.getPriorityLevelId());
			}
			priorityLevelName = moiMobileNotebookAddResource.getPriorityLevelName();
			if(moiMobileNotebookAddResource.getNextMeetingDate() != null && !moiMobileNotebookAddResource.getNextMeetingDate().isEmpty()) {
				nextMeetingDate = formatDate(moiMobileNotebookAddResource.getNextMeetingDate());
			}
			
			LoggerRequest.getInstance().logInfo("/******************************** Validate NIC No and OnboardingStatus *********************************************/");
			Optional<MobileNotebook> isPresentMobileNotebook = mobileNotebookRepository.findByNicNoAndOnboardingStatus(nicNo, MobileNotebookStatusEnum.PENDING);
			if(isPresentMobileNotebook.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("already.exist"),"message");
			}
			
		} else if (action.equals("UPDATE")) {
			
			id = Long.parseLong(mobileNotebookUpdateResource.getId());
			identificationTypeId = Long.parseLong(mobileNotebookUpdateResource.getIdentificationTypeId());
			identificationTypeCode = mobileNotebookUpdateResource.getIdentificationTypeCode();
			nicNo = mobileNotebookUpdateResource.getNicNo();
			if(mobileNotebookUpdateResource.getContactTypeId() != null && !mobileNotebookUpdateResource.getContactTypeId().isEmpty()) {
				contactTypeId = Long.parseLong(mobileNotebookUpdateResource.getContactTypeId());
			}
			contactTypeCode = mobileNotebookUpdateResource.getContactTypeCode();
			contactNumber = mobileNotebookUpdateResource.getContactNumber();
			if(mobileNotebookUpdateResource.getSecurityTypeId() != null && !mobileNotebookUpdateResource.getSecurityTypeId().isEmpty()) {
				securityTypeId = Long.parseLong(mobileNotebookUpdateResource.getSecurityTypeId());
			}
			securityTypeName = mobileNotebookUpdateResource.getSecurityTypeName();
			if(mobileNotebookUpdateResource.getSecuritySubTypeId() != null && !mobileNotebookUpdateResource.getSecuritySubTypeId().isEmpty()) {
				securitySubTypeId = Long.parseLong(mobileNotebookUpdateResource.getSecuritySubTypeId());
			}
			securitySubTypeName = mobileNotebookUpdateResource.getSecuritySubTypeName();
			if(mobileNotebookUpdateResource.getSpouseProfessionId() != null && !mobileNotebookUpdateResource.getSpouseProfessionId().isEmpty()) {
				spouseProfessionId = Long.parseLong(mobileNotebookUpdateResource.getSpouseProfessionId());
			}
			spouseProfessionName = mobileNotebookUpdateResource.getSpouseProfessionName();
			if(mobileNotebookUpdateResource.getPriorityLevelId() != null && !mobileNotebookUpdateResource.getPriorityLevelId().isEmpty()) {
				priorityLevelId = Long.parseLong(mobileNotebookUpdateResource.getPriorityLevelId());
			}
			priorityLevelName = mobileNotebookUpdateResource.getPriorityLevelName();
			if(mobileNotebookUpdateResource.getNextMeetingDate() != null && !mobileNotebookUpdateResource.getNextMeetingDate().isEmpty()) {
				nextMeetingDate = formatDate(mobileNotebookUpdateResource.getNextMeetingDate());
			}
			
			LoggerRequest.getInstance().logInfo("/******************************** Validate NIC No and OnboardingStatus *********************************************/");
			Optional<MobileNotebook> isPresentMobileNotebook = mobileNotebookRepository.findByNicNoAndOnboardingStatusAndIdNotIn(nicNo, MobileNotebookStatusEnum.PENDING, id);
			if(isPresentMobileNotebook.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("already.exist"),"message");
			}
			
		}
		
		LoggerRequest.getInstance().logInfo("/******************************** Validate Identification Type *********************************************/");
		if(identificationTypeId != null) {
			validateService.validateIdentificationType(tenantId, identificationTypeId, identificationTypeCode);
		} else {
			if (identificationTypeCode != null && !identificationTypeCode.isEmpty()) {
				throw new ValidateRecordException(environment.getProperty("identificationTypeId.not-match"),"identificationTypeId");
			}
		}
		
		LoggerRequest.getInstance().logInfo("/******************************** Validate NIC Number *********************************************/");
		if(identificationTypeId != null && !nicNo.isEmpty()) {
			validateService.validateIdentificationNumber(tenantId,identificationTypeId, nicNo);
		}
		
		LoggerRequest.getInstance().logInfo("/******************************** Validate Contact Type *********************************************/");
		if(contactTypeId != null) {
			validateService.validateContactType(tenantId, contactTypeId, contactTypeCode);
		}

		LoggerRequest.getInstance().logInfo("/******************************** Validate Contact NUmber *********************************************/");
		if(contactTypeId != null && contactNumber != null) {
			validateService.validateContactNumber(tenantId, contactTypeId, contactNumber);
		}
		
		LoggerRequest.getInstance().logInfo("/******************************** Validate Security Type *********************************************/");
		if(securityTypeId != null) {
			validateService.validateSecurityType(tenantId, securityTypeId, securityTypeName);
		} else {
			if (securityTypeName != null && !securityTypeName.isEmpty()) {
				throw new ValidateRecordException(environment.getProperty("securityTypeId.not-match"),"securityTypeId");
			}
		}
		
		LoggerRequest.getInstance().logInfo("/******************************** Validate Security Sub Type *********************************************/");
		if(securitySubTypeId != null) {
			validateService.validateSecuritySubType(tenantId, securityTypeId, securitySubTypeId, securitySubTypeName);
		} else {
			if (securitySubTypeName != null && !securitySubTypeName.isEmpty()) {
				throw new ValidateRecordException(environment.getProperty("securitySubTypeId.not-match"),"securitySubTypeId");
			}
		}

		LoggerRequest.getInstance().logInfo("/******************************** Validate Type Of Profession List *********************************************/");
		if(spouseProfessionId != null) {
			Optional<CommonList> isPresentProfessionList = commonListRepository.findByIdAndNameAndReferenceCodeAndStatus(spouseProfessionId, spouseProfessionName, "PROFESSION_LIST", "ACTIVE");
			if (!isPresentProfessionList.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("spouseProfessionId.invalid"), "spouseProfessionId");
			}
		} else {
			if (spouseProfessionName != null && !spouseProfessionName.isEmpty()) {
				throw new ValidateRecordException(environment.getProperty("spouseProfessionId.invalid"), "spouseProfessionId");
			}
		}
		
		LoggerRequest.getInstance().logInfo("/******************************** Validate Type Of Priority Level *********************************************/");
		if(priorityLevelId != null) {
			Optional<CommonList> isPresentPriorityLevel = commonListRepository.findByIdAndNameAndReferenceCodeAndStatus(priorityLevelId, priorityLevelName, "NOTE_BOOK_PRIORITY", "ACTIVE");
			if (!isPresentPriorityLevel.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("priorityLevelId.invalid"), "priorityLevelId");
			}
		} else {
			if (priorityLevelName != null && !priorityLevelName.isEmpty()) {
				throw new ValidateRecordException(environment.getProperty("priorityLevelId.invalid"), "priorityLevelId");
			}
		}
		
		LoggerRequest.getInstance().logInfo("/******************************** Validate Next Meeting Date *********************************************/");
		if(nextMeetingDate !=null && nextMeetingDate.before(new Date())) {
			throw new ValidateRecordException(environment.getProperty("nextMeetingDate.past-date"),"nextMeetingDate");				
		}
		
	}

	/**
	 * @author Senitha
	 * 
	 * Validate NIC
	 * @param  - nic
	 * @return - nic
	 * 
	 * */
	/*private String validateNic(String nicNo) {
		int nicLength = nicNo.length();
		if (nicLength == 10 || nicLength == 12) {
			if (nicLength == 10) {
				if (!(nicNo.trim().matches("^[0-9]{9}[vVxX]$"))) {
					throw new ValidateRecordException(environment.getProperty("nicNo.invalid"),"nicNo");
				}
			} else if (nicLength == 12) {
				String newNic = nicNo.substring(7,8);
				if (!newNic.equals("0") || !(nicNo.matches("[0-9]+"))) {
					throw new ValidateRecordException(environment.getProperty("nicNo.invalid"),"nicNo");
				}
			}
		} else {
			throw new ValidateRecordException(environment.getProperty("nicNo.length-invalid"),"nicNo");
		} 
		return nicNo;
	}*/
	
	/**
	 * @author Senitha
	 * 
	 * Set names from validations
	 * 
	 * */
	private void setName(String tenantId, MobileNotebook mobileNotebook) {
		
		if(mobileNotebook.getIdentificationTypeId() != null) {
			IdentificationType identificationType = validateService.getIdentificationTypeName(tenantId, mobileNotebook.getIdentificationTypeId());
			if(identificationType != null) {
				mobileNotebook.setIdentificationTypeDescription(identificationType.getIdtpDesc());
				mobileNotebook.setIdentificationTypeCode(identificationType.getIdtpCode());
			}
		}

		if(mobileNotebook.getContactTypeId() != null) {
			ContactType contactType = validateService.getContactType(tenantId, mobileNotebook.getContactTypeId());
			if(contactType != null) {
				mobileNotebook.setContactTypeDescription(contactType.getCntpDesc());
				mobileNotebook.setContactTypeCode(contactType.getCntpCode());
			}
		}

		if(mobileNotebook.getSecurityTypeId() != null) {
			AssetTypeSubType assetTypeType = validateService.getSecurityType(tenantId, mobileNotebook.getSecurityTypeId());
			if(assetTypeType != null) {
				mobileNotebook.setSecurityTypeName(assetTypeType.getName());
			}
		}
		
		if(mobileNotebook.getSecuritySubTypeId() != null) {
			AssetTypeSubType assetTypeSubType = validateService.getSecuritySubType(tenantId, mobileNotebook.getSecuritySubTypeId());
			if(assetTypeSubType != null) {
				mobileNotebook.setSecurityTypeName(assetTypeSubType.getName());
			}
		}
		
		if(mobileNotebook.getSpouseProfessionId() != null) {
			Optional<CommonList> spouseProfession = commonListRepository.findById(mobileNotebook.getSpouseProfessionId());
			if(spouseProfession.isPresent()) {
				mobileNotebook.setSpouseProfessionName(spouseProfession.get().getName());
			}
		}
		
		if(mobileNotebook.getPriorityLevelId() != null) {
			Optional<CommonList> priorityLevel = commonListRepository.findById(mobileNotebook.getPriorityLevelId());
			if(priorityLevel.isPresent()) {
				mobileNotebook.setPriorityLevelName(priorityLevel.get().getName());
			}
		}
		
	}
	
	/**
	 * @author Senitha
	 * 
	 * String to Big decimal
	 * @param  - String value
	 * @return - BigDecimal
	 * 
	 * */
	private BigDecimal stringToBigDecimal(String value) {
		if (value != null) {
			return new BigDecimal(value);
		} else {
			return BigDecimal.valueOf(0.0);
		}
	}
	
	/**
	 * @author Senitha
	 * 
	 * Format Date
	 * @param  - date
	 * @return - formated date
	 * 
	 * */
	private Date formatDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/** 
	 * @author Senitha
	 * 
	 * Format Date
	 * @param  - date
	 * @return - formated date
	 * 
	 * */
    private String formatedSysDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	public Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}

}
