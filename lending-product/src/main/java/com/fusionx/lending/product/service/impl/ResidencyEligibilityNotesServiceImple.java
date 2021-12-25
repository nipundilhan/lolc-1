package com.fusionx.lending.product.service.impl;

/**
 * Residency Eligibility Notes service
 * @author 	Rangana
 * @Dat     30-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-06-2021   FX-6       FX-6819     Rangana      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.ResidencyEligibility;
import com.fusionx.lending.product.domain.ResidencyEligibilityNotes;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.DuplicateRecordException;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.ResidencyEligibilityNotesRepository;
import com.fusionx.lending.product.repository.ResidencyEligibilityRepository;
import com.fusionx.lending.product.resources.AddNotesRequest;
import com.fusionx.lending.product.resources.UpdateNotesRequest;
import com.fusionx.lending.product.service.ResidencyEligibilityNotesService;
import com.fusionx.lending.product.service.ValidationService;


@Component
@Transactional(rollbackFor=Exception.class)
public class ResidencyEligibilityNotesServiceImple implements ResidencyEligibilityNotesService {
	
	@Autowired
	private ResidencyEligibilityNotesRepository residencyEligibilityNotesRepository;
	
	@Autowired
	private ResidencyEligibilityRepository residencyEligibilityRepository;
	
	@Autowired
	private ValidationService validationService;

	@Override
	public List<ResidencyEligibilityNotes> findAll() {
		return residencyEligibilityNotesRepository.findAll();
	}
	
	@Autowired
	Environment environment;

	@Override
	public Optional<ResidencyEligibilityNotes> findById(Long id) {
		Optional<ResidencyEligibilityNotes> residencyEligibilityNotes = residencyEligibilityNotesRepository.findById(id);
		if(residencyEligibilityNotes.isPresent())
			return Optional.ofNullable(residencyEligibilityNotes.get());
		else
			return Optional.empty();
	}

	@Override
	public List<ResidencyEligibilityNotes> findByResidencyEligiId(Long residencyEligibilityId) {
		List<ResidencyEligibilityNotes> residencyEligibilityNotes = residencyEligibilityNotesRepository.findByResidencyEligiId(residencyEligibilityId);
		return residencyEligibilityNotes;
	}

	@Override
	public List<ResidencyEligibilityNotes> findByStatus(String status) {
		return residencyEligibilityNotesRepository.findByStatus(status);
	}

	@Override
	public ResidencyEligibilityNotes addResidencyEligibilityNotes(String tenantId, Long residencyEligibilityId,AddNotesRequest addNotesRequest, String username) {
		Optional<ResidencyEligibility> isPresentResidencyEligibility = residencyEligibilityRepository.findByIdAndStatus(residencyEligibilityId,CommonStatus.ACTIVE);
		if(isPresentResidencyEligibility.isPresent()) 
			throw new DuplicateRecordException(environment.getProperty("common.duplicate"), "residencyEligibilityId");
			
			ResidencyEligibilityNotes residencyEligibilityNotes = new ResidencyEligibilityNotes();
			residencyEligibilityNotes.setTenantId(tenantId);
			residencyEligibilityNotes.setResidencyEligi(isPresentResidencyEligibility.get());
			residencyEligibilityNotes.setNotes(addNotesRequest.getNotes());
			residencyEligibilityNotes.setStatus(addNotesRequest.getStatus());
			residencyEligibilityNotes.setCreatedUser(username);
			residencyEligibilityNotes.setCreatedDate(validationService.getCreateOrModifyDate());
			residencyEligibilityNotes.setSyncTs(validationService.getCreateOrModifyDate());
			residencyEligibilityNotes = residencyEligibilityNotesRepository.saveAndFlush(residencyEligibilityNotes);
			return residencyEligibilityNotes;
		//}
	//	else
		//	return null;
	}

	@Override
	public ResidencyEligibilityNotes updateResidencyEligibilityNotes(String tenantId,Long id, UpdateNotesRequest updateNotesRequest, String username) {
		Optional<ResidencyEligibilityNotes> isPresentResidencyEligibilityNotes = residencyEligibilityNotesRepository.findById(id);
		if(isPresentResidencyEligibilityNotes.isPresent()) {
			
			if(!isPresentResidencyEligibilityNotes.get().getVersion().equals(Long.parseLong(updateNotesRequest.getVersion())))
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
			
			ResidencyEligibilityNotes residencyEligibilityNotes = isPresentResidencyEligibilityNotes.get();
			residencyEligibilityNotes.setNotes(updateNotesRequest.getNotes());
			residencyEligibilityNotes.setStatus(updateNotesRequest.getStatus());
			residencyEligibilityNotes.setModifiedUser(username);
			residencyEligibilityNotes.setModifiedDate(validationService.getCreateOrModifyDate());
			residencyEligibilityNotes.setSyncTs(validationService.getCreateOrModifyDate());
			residencyEligibilityNotes.setVersion(validationService.stringToLong(updateNotesRequest.getVersion()));
			residencyEligibilityNotes = residencyEligibilityNotesRepository.saveAndFlush(residencyEligibilityNotes);
			return residencyEligibilityNotes;
		}
		else
			return null;
	}
	

}
