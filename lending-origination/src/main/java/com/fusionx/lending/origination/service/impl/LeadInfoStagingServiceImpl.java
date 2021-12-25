package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.core.LoggerRequest;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.domain.LeadInfoStaging;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.repository.LeadInfoStagingRepository;
import com.fusionx.lending.origination.resource.LeadInfoStagingAddRequestResource;
import com.fusionx.lending.origination.resource.LeadInfoStagingUpdateRequestResource;
import com.fusionx.lending.origination.resource.LeadInfoStagingWithAdditionalDetailsResource;
import com.fusionx.lending.origination.service.LeadInfoStagingService;

/**
 * Lead Info Staging Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-JUN-2021   		      FX-6676    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class LeadInfoStagingServiceImpl implements LeadInfoStagingService{
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private LeadInfoStagingRepository leadInfoStagingRepository;
	
	@Autowired
	private LeadInfoRepository leadInfoRepository;
	
	@Override
	public List<LeadInfoStaging> findAll() {
		return leadInfoStagingRepository.findAll();
	}

	@Override
	public Optional<LeadInfoStaging> findById(Long id) {
		Optional<LeadInfoStaging> leadInfoStaging = leadInfoStagingRepository.findById(id);
		if (leadInfoStaging.isPresent()) {
			return Optional.ofNullable(leadInfoStaging.get());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public List<LeadInfoStaging> findByStatus(String status) {
		return leadInfoStagingRepository.findByStatus(status);
	}
	
	@Override
	public List<LeadInfoStaging> findByStagingStatus(String stagingStatus) {
		return leadInfoStagingRepository.findByStagingStatus(stagingStatus);
	}
	
	@Override
	public List<LeadInfoStaging> findByLeadId(Long id) {
		return leadInfoStagingRepository.findByLeadInfoId(id);
	}
	
	@Override
	public Long saveAndValidateLeadInfoStagingDetails(String tenantId, String createdUser, LeadInfoStagingAddRequestResource leadInfoStagingAddRequestResource) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveAndValidateLeadInfoStagingDetails>>>>>>******");
		Optional<LeadInfo> relevantLeadInfo = leadInfoRepository.findById(stringToLong(leadInfoStagingAddRequestResource.getLeadId()));
		if(!relevantLeadInfo.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "leadInfoId");
		}
		
		
		LeadInfoStaging leadInfoStaging=saveLeadInfoStaging(tenantId, createdUser, leadInfoStagingAddRequestResource,relevantLeadInfo);
		
		return leadInfoStaging.getId();
	}
	
	private LeadInfoStaging saveLeadInfoStaging(String tenantId, String createdUser, LeadInfoStagingAddRequestResource leadInfoStagingAddRequestResource,Optional<LeadInfo> leadInfo) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveLeadInfoStaging>>>>>>******");
 		
 		LeadInfoStaging leadInfoStaging;
		leadInfoStaging= new LeadInfoStaging();
 		leadInfoStaging.setTenantId(tenantId);
 		leadInfoStaging.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
 		leadInfoStaging.setCreatedDate(getCreateOrModifyDate());
		leadInfoStaging.setLeadInfo(leadInfo.get());
 		leadInfoStaging.setStagingStatus(leadInfoStagingAddRequestResource.getStagingStatus());
		leadInfoStaging.setComment(leadInfoStagingAddRequestResource.getComment());
		leadInfoStaging.setStatus(leadInfoStagingAddRequestResource.getStatus());
		leadInfoStaging.setSyncTs(getCreateOrModifyDate());
		return leadInfoStagingRepository.saveAndFlush(leadInfoStaging);
		
		
	}
	
	@Override
	public Long updateAndValidateLeadInfoStagingDetails(String tenantId, String modifiedUser, LeadInfoStagingUpdateRequestResource leadInfoStagingUpdateRequestResource, Long id) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN updateAndValidateLeadInfoStagingDetails>>>>>>******");
				
		Optional<LeadInfoStaging> relevantLeadInfoStaging = leadInfoStagingRepository.findById(id);
		if(!relevantLeadInfoStaging.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "id");
		}
		
		LeadInfoStaging leadInfoStaging=updateLeadInfoStaging(tenantId, modifiedUser, leadInfoStagingUpdateRequestResource,id);
		
		return leadInfoStaging.getId();
		
		
	}
	
	private LeadInfoStaging updateLeadInfoStaging(String tenantId, String modifiedUser, LeadInfoStagingUpdateRequestResource leadInfoStagingUpdateRequestResource, Long id) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN updateLeadInfoStaging>>>>>>******");
 		LeadInfoStaging leadInfoStaging;
		
		Optional<LeadInfoStaging> relevantLeadInfoStaging = leadInfoStagingRepository.findById(id);
		
		if (relevantLeadInfoStaging.get().getVersion()!= stringToLong(leadInfoStagingUpdateRequestResource.getVersion())) {
			throw new ValidateRecordException(environment.getProperty("common-invalid.version"), "version");
			
		}
			
		leadInfoStaging=relevantLeadInfoStaging.get();
		leadInfoStaging.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		leadInfoStaging.setModifiedDate(getCreateOrModifyDate());
		leadInfoStaging.setStagingStatus(leadInfoStagingUpdateRequestResource.getStagingStatus());
		leadInfoStaging.setComment(leadInfoStagingUpdateRequestResource.getComment());
		leadInfoStaging.setStatus(leadInfoStagingUpdateRequestResource.getStatus());
		leadInfoStaging.setSyncTs(getCreateOrModifyDate());
		return leadInfoStagingRepository.saveAndFlush(leadInfoStaging);
		
		
	}
	
	@Override
	public List<LeadInfoStagingWithAdditionalDetailsResource> findByStagingStausWithAdditionalDetails(String tenantId, String stagingStatus,String user) {
		if(stagingStatus==null || stagingStatus.isEmpty())
			throw new ValidateRecordException(environment.getProperty("value.not-null"), "stagingStatus");
		
		if(user==null || user.isEmpty())
			throw new ValidateRecordException(environment.getProperty("value.not-null"), "user");
		
		List<LeadInfoStagingWithAdditionalDetailsResource> listLeadInfoStagingWithAdditionalDetailsResource = null;
		listLeadInfoStagingWithAdditionalDetailsResource = leadInfoStagingRepository.leadInfoStagingWithAdditionalDetails(stagingStatus,user);  
		
		
		
		return listLeadInfoStagingWithAdditionalDetailsResource;
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

}
