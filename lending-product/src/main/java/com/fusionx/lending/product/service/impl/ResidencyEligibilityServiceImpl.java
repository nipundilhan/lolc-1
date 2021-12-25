package com.fusionx.lending.product.service.impl;

/**
 * Residency eligibility service
 * @author 	Rangana
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019     FX-6        FX-6523    Rangana      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.domain.ResidencyEligibility;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.ResidencyEligibilityRepository;
import com.fusionx.lending.product.resources.ResidencyEligibilityResource;
import com.fusionx.lending.product.resources.ResidencyEligibilityUpdateResource;
import com.fusionx.lending.product.resources.ResidencyType;
import com.fusionx.lending.product.service.ResidencyEligibilityHistoryService;
import com.fusionx.lending.product.service.ResidencyEligibilityService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class ResidencyEligibilityServiceImpl extends MessagePropertyBase implements ResidencyEligibilityService{

	@Autowired
	Environment environment;
	
	@Autowired
	private ResidencyEligibilityRepository residencyEligibilityRepository;
	
	@Autowired
	private ResidencyEligibilityHistoryService residencyEligibilityHistoryService;
	
	
	@Autowired
	private ValidationService validationService;

	@Override
	public List<ResidencyEligibility> findAll(String tenantId) {
		List<ResidencyEligibility> residencyEligibilities = residencyEligibilityRepository.findAll();
		for (ResidencyEligibility residencyEligibility : residencyEligibilities) {
			setResidencyTypeName(tenantId, residencyEligibility);
		}
		return residencyEligibilities;
	}
	
	@Override
	public List<ResidencyEligibility> getAll() {
		return residencyEligibilityRepository.findAll();
	}

	@Override
	public Optional<ResidencyEligibility> findById(String tenantId, Long residencyEligibilityId) {
		Optional<ResidencyEligibility> residencyEligibility = residencyEligibilityRepository.findById(residencyEligibilityId);
		if(residencyEligibility.isPresent()) {
			ResidencyEligibility residencyEligi = residencyEligibility.get();
			setResidencyTypeName(tenantId, residencyEligi);
			return Optional.ofNullable(residencyEligi);
		}else {
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<ResidencyEligibility> findResidencyEligibilityDetailById(String tenantId, Long residencyEligibilityId) {
		Optional<ResidencyEligibility> residencyEligibility = residencyEligibilityRepository.findById(residencyEligibilityId);
		if(residencyEligibility.isPresent()) {
			ResidencyEligibility residencyEligibilityDetail = residencyEligibility.get();
			setResidencyTypeName(tenantId, residencyEligibilityDetail);
			//ResidencyEligibility residencyEligibilityDetail = setResidencyEligibility(residencyEligibility.get());
			return Optional.ofNullable(residencyEligibilityDetail);
		}
		else
			return Optional.empty();
	}
	
	/*protected ResidencyEligibility setResidencyEligibility(ResidencyEligibility residencyEligibility) {
		List<ResidencyEligibilityNotes> residencyEligibilityNotes = residencyEligibilityNotesRepository.findByResidencyEligiId(residencyEligibility.getId());
		if(!residencyEligibilityNotes.isEmpty())
			residencyEligibility.setResidencyEligibilityNotes(residencyEligibilityNotes);
		
		List<ResidencyInclude> residencyInclude = residencyIncludeRepository.findByResidencyEligiId(residencyEligibility.getId());
		if(!residencyInclude.isEmpty())
			residencyEligibility.setResidencyInclude(residencyInclude);
		
		return residencyEligibility;
	}*/

	@Override
	public List<ResidencyEligibility> findByStatus(String tenantId, String status) {
		List<ResidencyEligibility> residencyEligibilities = residencyEligibilityRepository.findByStatus(CommonStatus.valueOf(status));
		for (ResidencyEligibility residencyEligibility : residencyEligibilities) {
			setResidencyTypeName(tenantId, residencyEligibility);
		}
		return residencyEligibilities;
	}

	/**
	 * @author Rangana
	 * Get Residency Eligibility by ResidencyEligibilityCode
	 * */
	@Override
	public Optional<ResidencyEligibility> getResidencyEligibilityByCode(String residencyEligibilityCode) {
		Optional<ResidencyEligibility> residencyEligibilityEligibility = residencyEligibilityRepository.findByCode(residencyEligibilityCode);
		if(residencyEligibilityEligibility.isPresent())
			return Optional.ofNullable(residencyEligibilityEligibility.get());
		else
			return Optional.empty();
	}

	@Override
	public ResidencyEligibility addResidencyEligibility(String tenantId,ResidencyEligibilityResource residencyEligibilityAddResource, String userName) {
		
		ResidencyEligibility residencyEligibility = new ResidencyEligibility();
		
		Optional<ResidencyEligibility>isPresentResidencyEligibilityCode = residencyEligibilityRepository.findByCode(residencyEligibilityAddResource.getCode());
		if (isPresentResidencyEligibilityCode.isPresent()) 
			throw new InvalidServiceIdException(environment.getProperty("common.code-duplicate"), ServiceEntity.CODE, EntityPoint.RESIDENCY_ELIGIBILITY);
		
		Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityRepository.findByResidencyTypeId(validationService.stringToLong(residencyEligibilityAddResource.getResidencyTypeId()));
		if(isPresentResidencyEligibility.isPresent())
			throw new InvalidServiceIdException(environment.getProperty("common.already-exists"), ServiceEntity.RESIDENCYTYPE, EntityPoint.RESIDENCY_ELIGIBILITY);
		
		residencyEligibility.setTenantId(tenantId);
		
		validationService.validateResidencyType(tenantId, residencyEligibilityAddResource.getResidencyTypeId(), residencyEligibilityAddResource.getResidencyTypeName(), EntityPoint.RESIDENCY_ELIGIBILITY);
		residencyEligibility.setResidencyTypeId(validationService.stringToLong(residencyEligibilityAddResource.getResidencyTypeId()));
		
		residencyEligibility.setCode(residencyEligibilityAddResource.getCode());
		residencyEligibility.setStatus(residencyEligibilityAddResource.getStatus());
		residencyEligibility.setCreatedUser(userName);
		residencyEligibility.setCreatedDate(validationService.getCreateOrModifyDate());
		residencyEligibility.setSyncTs(validationService.getCreateOrModifyDate());
		residencyEligibility = residencyEligibilityRepository.saveAndFlush(residencyEligibility);
		return residencyEligibility;
	}

	@Override
	public ResidencyEligibility updateResidencyEligibility(String tenantId, Long id,ResidencyEligibilityUpdateResource residencyEligibilityUpdateResource, String userName) {
	
		Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityRepository.findById(id);
		if(isPresentResidencyEligibility.isPresent()) {
			
			Optional<ResidencyEligibility>isPresentResidencyEligibilityCode = residencyEligibilityRepository.findByCodeAndId(residencyEligibilityUpdateResource.getCode(), id);
			if (!isPresentResidencyEligibilityCode.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("residencyEligibilityCode.update"), ServiceEntity.CODE, EntityPoint.RESIDENCY_ELIGIBILITY);
			
			Optional<ResidencyEligibility> isPresentResidencyEligi = residencyEligibilityRepository.findByResidencyTypeIdAndIdNotIn(validationService.stringToLong(residencyEligibilityUpdateResource.getResidencyTypeId()),id);
			if(isPresentResidencyEligi.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("common.already-exists"), ServiceEntity.RESIDENCYTYPE, EntityPoint.RESIDENCY_ELIGIBILITY);
			
			if (!isPresentResidencyEligibility.get().getVersion().equals(Long.parseLong(residencyEligibilityUpdateResource.getVersion())))
                throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.RESIDENCY_ELIGIBILITY);
			
			ResidencyEligibility residencyEligibility = isPresentResidencyEligibility.get();
			residencyEligibilityHistoryService.insertResidencyEligibilityHistory(tenantId, residencyEligibility, userName);
			
			validationService.validateResidencyType(tenantId, residencyEligibilityUpdateResource.getResidencyTypeId(), residencyEligibilityUpdateResource.getResidencyTypeName(), EntityPoint.RESIDENCY_ELIGIBILITY);
			residencyEligibility.setResidencyTypeId(validationService.stringToLong(residencyEligibilityUpdateResource.getResidencyTypeId()));

			
			residencyEligibility.setCode(residencyEligibilityUpdateResource.getCode());
			residencyEligibility.setStatus(residencyEligibilityUpdateResource.getStatus());
			residencyEligibility.setModifiedUser(userName);
			residencyEligibility.setModifiedDate(validationService.getCreateOrModifyDate());
			residencyEligibility.setSyncTs(validationService.getCreateOrModifyDate());
			residencyEligibility.setVersion(validationService.stringToLong(residencyEligibilityUpdateResource.getVersion()));
			residencyEligibility = residencyEligibilityRepository.saveAndFlush(residencyEligibility);
			return residencyEligibility;
		}
		else
			throw new ValidateRecordException(environment.getProperty("record-not-found"),"message");
	}
	 private void setResidencyTypeName(String tenantId, ResidencyEligibility residencyEligibility) {
		 ResidencyType residencyType =validationService.getResidencyType(tenantId, residencyEligibility.getResidencyTypeId());
		 residencyEligibility.setResidencyTypeName(residencyType.getName());
	 }
	
}
