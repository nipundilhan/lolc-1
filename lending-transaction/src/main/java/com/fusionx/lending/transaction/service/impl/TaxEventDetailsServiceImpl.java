package com.fusionx.lending.transaction.service.impl;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.domain.TaxEvent;
import com.fusionx.lending.transaction.domain.TaxEventDetails;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.enums.TaxApplicableOnEnum;
import com.fusionx.lending.transaction.exception.BackendDataException;
import com.fusionx.lending.transaction.exception.CodeUniqueException;
import com.fusionx.lending.transaction.exception.NoRecordFoundException;
import com.fusionx.lending.transaction.exception.OptimisticLockException;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.repo.TaxEventDetailsRepository;
import com.fusionx.lending.transaction.repo.TaxEventRepository;
import com.fusionx.lending.transaction.resource.AddTaxEventDetailsRequestResource;
import com.fusionx.lending.transaction.resource.ApplicationFrequencyResponse;
import com.fusionx.lending.transaction.resource.TaxCodeResponse;
import com.fusionx.lending.transaction.resource.UpdateTaxEventDetailsRequestResource;
import com.fusionx.lending.transaction.service.TaxEventDetailsService;
import com.fusionx.lending.transaction.service.ValidateService;
import com.fusionx.lending.transaction.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional(rollbackFor = Exception.class)
public class TaxEventDetailsServiceImpl extends MessagePropertyBase implements TaxEventDetailsService {

	@Autowired
	TaxEventDetailsRepository taxEventDetailsRepository;
	@Autowired
	TaxEventRepository taxEventRepository;
	private ValidationService validationService;
	private ValidateService validateService;

	@Autowired
	public void setValidationService(ValidationService validationService) {
		this.validationService = validationService;
	}

	@Autowired
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	@Override
	public List<TaxEventDetails> findAll() {
		return taxEventDetailsRepository.findAll();
	}

	@Override
	public Optional<TaxEventDetails> findById(Long id) {
		return taxEventDetailsRepository.findById(id);
	}

	@Override
	public List<TaxEventDetails> findByCode(String code) {
		return taxEventDetailsRepository.findByCode(code);
	}

	@Override
	public Optional<TaxEventDetails> findByName(String name) {
		return taxEventDetailsRepository.findByNameContaining(name);
	}

	@Override
	public List<TaxEventDetails> findByStatus(String status) {
		return taxEventDetailsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public List<TaxEventDetails> findByTaxEventId(String taxEventId) {
		return taxEventDetailsRepository.findByTaxEventIdId(Long.parseLong(taxEventId));
	}

	@Override
	public TaxEventDetails addTaxEventDetails(String tenantId,
			AddTaxEventDetailsRequestResource taxEventDetailsAddResource) {

		TaxCodeResponse taxCodeResponse = validateService.validateTaxCode(tenantId,
				taxEventDetailsAddResource.getCodeId(), taxEventDetailsAddResource.getName());
		if (!taxCodeResponse.getTaxCode().equals(taxEventDetailsAddResource.getCode())) {
			throw new BackendDataException(environment.getProperty("common.invalid-value"), "code");
		}

		ApplicationFrequencyResponse applicationFrequencyResponse = validateService
				.validateApplicationFrequency(tenantId, taxEventDetailsAddResource.getApplicationFrequency());

		Optional<TaxEvent> taxEvent = taxEventRepository
				.findById(Long.parseLong(taxEventDetailsAddResource.getTaxEventId()));
		if (!taxEvent.isPresent()) {
			throw new BackendDataException(environment.getProperty("common.not-found"), "taxEventId");
		}

		TaxEventDetails taxEventDetails = new TaxEventDetails();
		taxEventDetails.setTenantId(tenantId);
		taxEventDetails.setCodeId(Long.parseLong(taxCodeResponse.getId().toString()));
		taxEventDetails.setCode(taxCodeResponse.getTaxCode());
		taxEventDetails.setName(taxCodeResponse.getTaxCodeName());
		taxEventDetails.setDescription(taxEventDetailsAddResource.getDescription());
		taxEventDetails.setApplicationFrequency(applicationFrequencyResponse.getId());
		taxEventDetails.setTaxEventId(taxEvent.get());
		taxEventDetails.setSequence(Long.parseLong(taxEventDetailsAddResource.getSequence()));
		taxEventDetails.setAmountType(taxEventDetailsAddResource.getAmountType());
		if (taxEventDetailsAddResource.getApplicableOn() != null) {
			taxEventDetails.setApplicableOn(taxEventDetailsAddResource.getApplicableOn());
		}
		taxEventDetails.setFormula(taxEventDetailsAddResource.getFormula());
		taxEventDetails.setIsFixed(CommonStatus.valueOf(taxEventDetailsAddResource.getIsFixed()));
		taxEventDetails.setStatus(CommonStatus.valueOf(taxEventDetailsAddResource.getStatus()));
		taxEventDetails.setCreatedDate(validationService.getCreateOrModifyDate());
		taxEventDetails.setSyncTs(validationService.getCreateOrModifyDate());
		taxEventDetails.setCreatedUser(taxEventDetailsAddResource.getTaxEventCreatedUser());

		return taxEventDetailsRepository.saveAndFlush(taxEventDetails);
	}

	@Override
	public TaxEventDetails updateTaxEventDetails(String tenantId,
			UpdateTaxEventDetailsRequestResource taxEventDetailsUpdateResource, Long id) {

		Optional<TaxEventDetails> isPresentTaxEventDetails = taxEventDetailsRepository.findById(id);
		if (isPresentTaxEventDetails.isPresent()) {

			TaxCodeResponse taxCodeResponse = validateService.validateTaxCode(tenantId,
					taxEventDetailsUpdateResource.getCodeId(), taxEventDetailsUpdateResource.getName());
			if (!taxCodeResponse.getTaxCode().equals(taxEventDetailsUpdateResource.getCode())) {
				throw new BackendDataException(environment.getProperty("common.invalid-value"), "code");
			}

			ApplicationFrequencyResponse applicationFrequencyResponse = validateService
					.validateApplicationFrequency(tenantId, taxEventDetailsUpdateResource.getApplicationFrequency());

			Optional<TaxEvent> taxEvent = taxEventRepository
					.findById(Long.parseLong(taxEventDetailsUpdateResource.getTaxEventId()));
			if (!taxEvent.isPresent()) {
				throw new BackendDataException(environment.getProperty("common.not-found"), "taxEventId");
			}

			if (!isPresentTaxEventDetails.get().getVersion()
					.equals(Long.parseLong(taxEventDetailsUpdateResource.getVersion()))) {
				throw new OptimisticLockException(environment.getProperty("common.invalid-value"), "version");
			}

			TaxEventDetails taxEventDetails = isPresentTaxEventDetails.get();
			taxEventDetails.setTenantId(tenantId);
			taxEventDetails.setCodeId(Long.parseLong(taxCodeResponse.getId().toString()));
			taxEventDetails.setCode(taxCodeResponse.getTaxCode());
			taxEventDetails.setName(taxCodeResponse.getTaxCodeName());
			taxEventDetails.setDescription(taxEventDetailsUpdateResource.getDescription());
			taxEventDetails.setApplicationFrequency(applicationFrequencyResponse.getId());
			taxEventDetails.setTaxEventId(taxEvent.get());
			taxEventDetails.setSequence(Long.parseLong(taxEventDetailsUpdateResource.getSequence()));
			taxEventDetails.setAmountType(taxEventDetailsUpdateResource.getAmountType());
			taxEventDetails
					.setApplicableOn(taxEventDetailsUpdateResource.getApplicableOn());
			taxEventDetails.setFormula(taxEventDetailsUpdateResource.getFormula());
			taxEventDetails.setIsFixed(CommonStatus.valueOf(taxEventDetailsUpdateResource.getIsFixed()));
			taxEventDetails.setStatus(CommonStatus.valueOf(taxEventDetailsUpdateResource.getStatus()));
			taxEventDetails.setModifiedDate(validationService.getCreateOrModifyDate());
			taxEventDetails.setSyncTs(validationService.getCreateOrModifyDate());
			taxEventDetails.setModifiedUser(taxEventDetailsUpdateResource.getModifiedUser());
			taxEventDetails.setVersion(validationService.stringToLong(taxEventDetailsUpdateResource.getVersion()));

			return taxEventDetailsRepository.saveAndFlush(taxEventDetails);

		} else
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
	}

}
