package com.fusionx.lending.origination.service.impl;

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
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.domain.OtherDetails;
import com.fusionx.lending.origination.enums.CommonListReferenceCodes;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.repository.LeadInfoRepository;
import com.fusionx.lending.origination.repository.OtherDetailsRepository;
import com.fusionx.lending.origination.resource.OtherDetailsAddRequestResource;
import com.fusionx.lending.origination.resource.OtherDetailsUpdateRequestResource;
import com.fusionx.lending.origination.resource.SectorResponseResource;
import com.fusionx.lending.origination.resource.SubSectorResponseResource;
import com.fusionx.lending.origination.service.OtherDetailsService;
import com.fusionx.lending.origination.service.ValidateService;

/**
 * Other Detail Service.
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
public class OtherDetailsServiceImpl  extends MessagePropertyBase implements OtherDetailsService{
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private OtherDetailsRepository otherDetailsRepository;
	
	@Autowired
	private LeadInfoRepository leadInfoRepository;
	
	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private CommonListRepository commonListRepository;
	
	@Override
	public List<OtherDetails> findAll(String tenantId) {
		List<OtherDetails> otherDetails = otherDetailsRepository.findAll();
		for (OtherDetails otherDetailsNames : otherDetails) {
			
			setSectorSubSectorName( tenantId,  otherDetailsNames);
			CommonList commonList = getComnListName( otherDetailsNames.getPurpose().getId().toString());
			otherDetailsNames.setPurposeName(commonList.getName());
		}
		
		return otherDetails;
	}

	@Override
	public Optional<OtherDetails> findById(String tenantId, Long id) {
		Optional<OtherDetails> otherDetails = otherDetailsRepository.findById(id);
		if (otherDetails.isPresent()) {
			setSectorSubSectorName( tenantId,  otherDetails.get());
			CommonList commonList = getComnListName( otherDetails.get().getPurpose().getId().toString());
			otherDetails.get().setPurposeName(commonList.getName());
			return Optional.ofNullable(otherDetails.get());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public List<OtherDetails> findByStatus(String tenantId, String status) {
		List<OtherDetails> otherDetails = otherDetailsRepository.findByStatus(status);
		for (OtherDetails otherDetailsNames : otherDetails) {
			
			setSectorSubSectorName( tenantId,  otherDetailsNames);
			CommonList commonList = getComnListName( otherDetailsNames.getPurpose().getId().toString());
			otherDetailsNames.setPurposeName(commonList.getName());
		}
		
		return otherDetails;
	}
	
	@Override
	public Optional<OtherDetails> findByLeadId(String tenantId, Long id) {
		Optional<OtherDetails> otherDetails = otherDetailsRepository.findByLeadInfoId(id);
		if (otherDetails.isPresent()) {
			
			setSectorSubSectorName( tenantId,  otherDetails.get());
			CommonList commonList = getComnListName( otherDetails.get().getPurpose().getId().toString());
			otherDetails.get().setPurposeName(commonList.getName());
			return Optional.ofNullable(otherDetails.get());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public Long saveAndValidateOtherDetails(String tenantId, String createdUser, OtherDetailsAddRequestResource otherDetailsAddRequestResource) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN saveAndValidateOtherDetails>>>>>>******");
		
 		Optional<LeadInfo> relevantLeadInfo = leadInfoRepository.findById(stringToLong(otherDetailsAddRequestResource.getLeadInfoId()));
		if(!relevantLeadInfo.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "leadInfoId");
		}
		
		Optional<OtherDetails> relevantOtherDetails = otherDetailsRepository.findByLeadInfoId(stringToLong(otherDetailsAddRequestResource.getLeadInfoId()));
		if(relevantOtherDetails.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("leadInfoId.duplicate"), "leadInfoId");
		}
		
		LoggerRequest.getInstance().logInfo1("******<<<<<< Validate Sector >>>>>>******");
		validateService.validateSector(tenantId, Long.parseLong(otherDetailsAddRequestResource.getSectorId()), otherDetailsAddRequestResource.getSectorCode());
		
		LoggerRequest.getInstance().logInfo1("******<<<<<< Validate Sub Sector >>>>>>******");
		validateService.validateSubSector(tenantId, Long.parseLong(otherDetailsAddRequestResource.getSectorId()), Long.parseLong(otherDetailsAddRequestResource.getSubSectorId()), otherDetailsAddRequestResource.getSubSectorCode());
		
		LoggerRequest.getInstance().logInfo1("******<<<<<<  Validate Ownership >>>>>>******");
		CommonList loanPurpose = comnListValidation(otherDetailsAddRequestResource.getPurposeId(), otherDetailsAddRequestResource.getPurposeName(), CommonListReferenceCodes.LOAN_PURPOSE, ServiceEntity.PURPOSE_ID, ServicePoint.OTHER_DETAILS);
		
		
		OtherDetails otherDetails=otherDetails(tenantId, createdUser, otherDetailsAddRequestResource,relevantLeadInfo,null, loanPurpose);
		
		return otherDetails.getId();
	}
	
	private OtherDetails otherDetails(String tenantId, String createdUser, OtherDetailsAddRequestResource otherDetailsAddRequestResource,Optional<LeadInfo> leadInfo, Long id ,CommonList loanPurpose) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN otherDetails>>>>>>******");
		OtherDetails otherDetails;
		
		if(id != null) {
			
			Optional<OtherDetails> relevantOtherDetails = otherDetailsRepository.findById(id);
			
			otherDetails=relevantOtherDetails.get();
			otherDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			otherDetails.setModifiedDate(getCreateOrModifyDate());
		}else {
			otherDetails= new OtherDetails();
			otherDetails.setTenantId(tenantId);
			otherDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			otherDetails.setCreatedDate(getCreateOrModifyDate());
			
		}
		
		otherDetails.setLeadInfo(leadInfo.get());
		otherDetails.setSectorId(stringToLong(otherDetailsAddRequestResource.getSectorId()));
		otherDetails.setSectorCode(otherDetailsAddRequestResource.getSectorCode());
		otherDetails.setSubSectorId(stringToLong(otherDetailsAddRequestResource.getSubSectorId()));
		otherDetails.setSubSectorCode(otherDetailsAddRequestResource.getSubSectorCode());
		otherDetails.setPurpose(loanPurpose);
		otherDetails.setPurposeCode(loanPurpose.getCode());
		otherDetails.setSavingsAccRequired(otherDetailsAddRequestResource.getSavingsAccRequired());
		otherDetails.setComment(otherDetailsAddRequestResource.getComment());
		otherDetails.setStatus(otherDetailsAddRequestResource.getStatus());
		
		otherDetails.setSyncTs(getCreateOrModifyDate());
		return otherDetailsRepository.saveAndFlush(otherDetails);
		
		
	}
	
	@Override
	public Long updateAndValidateOtherDetails(String tenantId, String createdUser, OtherDetailsUpdateRequestResource otherDetailsUpdateRequestResource, Long id) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN updateAndValidateOtherDetails>>>>>>******");
		/*Optional<LeadInfo> relevantLeadInfo = leadInfoRepository.findById(stringToLong(otherDetailsUpdateRequestResource.getLeadInfoId()));
		if(!relevantLeadInfo.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "leadInfoId");
		}*/
		
		Optional<OtherDetails> relevantOtherDetails = otherDetailsRepository.findById(id);
		if(!relevantOtherDetails.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "id");
		}
		
		if (relevantOtherDetails.get().getVersion()!= Long.parseLong(otherDetailsUpdateRequestResource.getVersion())) {
			throw new ValidateRecordException(environment.getProperty("common-invalid.version"), "version");
		}
		
		LoggerRequest.getInstance().logInfo1("******<<<<<< Validate Sector >>>>>>******");
		validateService.validateSector(tenantId, Long.parseLong(otherDetailsUpdateRequestResource.getSectorId()), otherDetailsUpdateRequestResource.getSectorCode());
		
		LoggerRequest.getInstance().logInfo1("******<<<<<< Validate Sub Sector >>>>>>******");
		validateService.validateSubSector(tenantId, Long.parseLong(otherDetailsUpdateRequestResource.getSectorId()), Long.parseLong(otherDetailsUpdateRequestResource.getSubSectorId()), otherDetailsUpdateRequestResource.getSubSectorCode());
		
		LoggerRequest.getInstance().logInfo1("******<<<<<<  Validate Ownership >>>>>>******");
		CommonList loanPurpose = comnListValidation(otherDetailsUpdateRequestResource.getPurposeId(), otherDetailsUpdateRequestResource.getPurposeName(), CommonListReferenceCodes.LOAN_PURPOSE, ServiceEntity.PURPOSE_ID, ServicePoint.OTHER_DETAILS);
		
		OtherDetails otherDetails=otherDetailsUpdate(tenantId, createdUser, otherDetailsUpdateRequestResource,id, loanPurpose);
		
		return otherDetails.getId();
	}
	
	private OtherDetails otherDetailsUpdate(String tenantId, String createdUser, OtherDetailsUpdateRequestResource otherDetailsUpdateRequestResource, Long id, CommonList loanPurpose) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN otherDetailsUpdate>>>>>>******");
		OtherDetails otherDetails;
		
		if(id != null) {
			
			Optional<OtherDetails> relevantOtherDetails = otherDetailsRepository.findById(id);
			
			otherDetails=relevantOtherDetails.get();
			otherDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			otherDetails.setModifiedDate(getCreateOrModifyDate());
		}else {
			otherDetails= new OtherDetails();
			otherDetails.setTenantId(tenantId);
			otherDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			otherDetails.setCreatedDate(getCreateOrModifyDate());
			
		}
		
		//otherDetails.setLeadInfo(leadInfo.get());
		otherDetails.setSectorId(stringToLong(otherDetailsUpdateRequestResource.getSectorId()));
		otherDetails.setSectorCode(otherDetailsUpdateRequestResource.getSectorCode());
		otherDetails.setSubSectorId(stringToLong(otherDetailsUpdateRequestResource.getSubSectorId()));
		otherDetails.setSubSectorCode(otherDetailsUpdateRequestResource.getSubSectorCode());
		otherDetails.setPurpose(loanPurpose);
		otherDetails.setPurposeCode(loanPurpose.getCode());
		otherDetails.setSavingsAccRequired(otherDetailsUpdateRequestResource.getSavingsAccRequired());
		otherDetails.setComment(otherDetailsUpdateRequestResource.getComment());
		otherDetails.setStatus(otherDetailsUpdateRequestResource.getStatus());
		
		otherDetails.setSyncTs(getCreateOrModifyDate());
		return otherDetailsRepository.saveAndFlush(otherDetails);
		
		
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
	
	/**
	 * @author Sanatha
	 * */
	private void setSectorSubSectorName(String tenantId, OtherDetails otherDetails) {
		
		SectorResponseResource sectorResponseResource = validateService.getSectorName(tenantId, otherDetails.getSectorId());
		otherDetails.setSectorName(sectorResponseResource.getSectorName());
		
		SubSectorResponseResource subSectorResponseResource = validateService.getSubSectorName(tenantId, otherDetails.getSectorId(), otherDetails.getSubSectorId());
		otherDetails.setSubSectorName(subSectorResponseResource.getCssName());
		
		
	}
	
	/**
	 * @author Sanatha
	 * */
	private CommonList comnListValidation(String id, String name, CommonListReferenceCodes referenceCode, ServiceEntity serviceEntity, ServicePoint servicePoint) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN comnListValidation>>>>>>******");
		if(id !=null && !id.isEmpty()) {
			LoggerRequest.getInstance().logInfo1("******<<<<<<id>>>>>>******"+id);
			LoggerRequest.getInstance().logInfo1("******<<<<<<name>>>>>>******"+name);
			LoggerRequest.getInstance().logInfo1("******<<<<<<referenceCode>>>>>>******"+referenceCode.toString());
			
			Optional<CommonList> commonList=commonListRepository.findById(Long.parseLong(id));
			
			LoggerRequest.getInstance().logInfo1("******<<<<<<commonList.get().getId()>>>>>>******"+commonList.get().getId());
			LoggerRequest.getInstance().logInfo1("******<<<<<<commonList.get().getName()>>>>>>******"+commonList.get().getName());
			LoggerRequest.getInstance().logInfo1("******<<<<<<commonList.get().getReferenceCode()>>>>>>******"+commonList.get().getReferenceCode());
			if(commonList.isPresent() && commonList.get().getName().equalsIgnoreCase(name) && commonList.get().getReferenceCode().equalsIgnoreCase(referenceCode.toString()) 
					&& commonList.get().getStatus().toString().equalsIgnoreCase(CommonStatus.ACTIVE.toString())) {
				return commonList.get();
			}else {
				throw new DetailValidateException(environment.getProperty(COMMON_INVALID_VALUE), serviceEntity, servicePoint);
			}
		}
		return null;
	}

	private CommonList getComnListName(String id) {
		LoggerRequest.getInstance().logInfo1("******<<<<<<#############>>>>>>******");
 		LoggerRequest.getInstance().logInfo1("******<<<<<<IN getComnListName>>>>>>******");
 		
 		Optional<CommonList> commonList=commonListRepository.findById(Long.parseLong(id));
 		if(commonList.isPresent()) {
 			return commonList.get();
 		}else {
 			return null;
 		}
	}

}
