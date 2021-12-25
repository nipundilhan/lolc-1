package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeTempNotes;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.repository.FeeChargeTempNotesRepository;
import com.fusionx.lending.product.repository.FeeChargeRepository;
import com.fusionx.lending.product.resources.AddNotesRequest;
import com.fusionx.lending.product.resources.UpdateNotesRequest;
import com.fusionx.lending.product.service.FeeChargeTempNotesService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class FeeChargeTempNotesServiceImpl implements FeeChargeTempNotesService{

	@Autowired
	private FeeChargeTempNotesRepository feeChargeTempNotesRepository;
	
	@Autowired
	private FeeChargeRepository feeChargeRepository;
	
	@Autowired
	private ValidationService validationService;

	@Override
	public List<FeeChargeTempNotes> findAll() {
		return feeChargeTempNotesRepository.findAll();
	}

	@Override
	public Optional<FeeChargeTempNotes> findById(Long id) {
		Optional<FeeChargeTempNotes> feeChargeCapTempNotes = feeChargeTempNotesRepository.findById(id);
		if(feeChargeCapTempNotes.isPresent())
			return Optional.ofNullable(feeChargeCapTempNotes.get());
		else
			return Optional.empty();
	}
	
	@Override
	public List<FeeChargeTempNotes> findByFeeChargeId(Long feeChargeId) {
		List<FeeChargeTempNotes> feeChargeTempNotes = feeChargeTempNotesRepository.findByFeeCharge_Id(feeChargeId);
		return feeChargeTempNotes;
	}

	@Override
	public List<FeeChargeTempNotes> findByStatus(String status) {
		return feeChargeTempNotesRepository.findByStatus(status);
	}

	@Override
	public FeeChargeTempNotes addFeeChargeCapTempNotes(String tenantId, Long feeChargeId,
			AddNotesRequest addNotesRequest, String username) {
		Optional<FeeCharge> isPresentFeeCharge = feeChargeRepository.findByIdAndStatus(feeChargeId,CommonStatus.ACTIVE);
		if(isPresentFeeCharge.isPresent()) {
			FeeChargeTempNotes feeChargeCapTempNotes = new FeeChargeTempNotes();
			feeChargeCapTempNotes.setTenantId(tenantId);
			feeChargeCapTempNotes.setFeeCharge(isPresentFeeCharge.get());
			feeChargeCapTempNotes.setNotes(addNotesRequest.getNotes());
			feeChargeCapTempNotes.setStatus(addNotesRequest.getStatus());
			feeChargeCapTempNotes.setCreatedUser(username);
			feeChargeCapTempNotes.setCreatedDate(validationService.getCreateOrModifyDate());
			feeChargeCapTempNotes.setSyncTs(validationService.getCreateOrModifyDate());
			feeChargeCapTempNotes = feeChargeTempNotesRepository.saveAndFlush(feeChargeCapTempNotes);
			return feeChargeCapTempNotes;
		}
		else
			return null;
	}

	@Override
	public FeeChargeTempNotes updateFeeChargeCapTempNotes(String tenantId, Long id,
			UpdateNotesRequest updateNotesRequest, String username) {
		Optional<FeeChargeTempNotes> isPresentFeeChargeCapTempNotes = feeChargeTempNotesRepository.findById(id);
		if(isPresentFeeChargeCapTempNotes.isPresent()) {
			FeeChargeTempNotes feeChargeCapTempNotes = isPresentFeeChargeCapTempNotes.get();
			feeChargeCapTempNotes.setNotes(updateNotesRequest.getNotes());
			feeChargeCapTempNotes.setStatus(updateNotesRequest.getStatus());
			feeChargeCapTempNotes.setModifiedUser(username);
			feeChargeCapTempNotes.setModifiedDate(validationService.getCreateOrModifyDate());
			feeChargeCapTempNotes.setSyncTs(validationService.getCreateOrModifyDate());
			feeChargeCapTempNotes.setVersion(validationService.stringToLong(updateNotesRequest.getVersion()));
			feeChargeCapTempNotes = feeChargeTempNotesRepository.saveAndFlush(feeChargeCapTempNotes);
			return feeChargeCapTempNotes;
		}
		else
			return null;
	}

}
