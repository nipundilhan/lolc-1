package com.fusionx.lending.transaction.service.impl;

/**
 * Tax Event
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   12-10-2021   FXL-1234      FXL-1131   Dilki      Created
 * <p>
 * *******************************************************************************************************
 */
import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.TaxEvent;
import com.fusionx.lending.transaction.domain.TaxEventDetails;
import com.fusionx.lending.transaction.domain.TransactionEvent;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.exception.CodeUniqueException;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.repo.TaxEventDetailsRepository;
import com.fusionx.lending.transaction.repo.TaxEventRepository;
import com.fusionx.lending.transaction.repo.TransactionEventRepository;
import com.fusionx.lending.transaction.resource.AddTaxEventRequestResource;
import com.fusionx.lending.transaction.resource.TaxEventUpdateResource;
import com.fusionx.lending.transaction.resource.UpdateTaxEventRequestResource;
import com.fusionx.lending.transaction.service.TaxEventService;
import com.fusionx.lending.transaction.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional(rollbackFor = Exception.class)
public class TaxEventServiceImpl extends MessagePropertyBase implements TaxEventService {

	@Autowired
	TaxEventRepository taxEventRepository;

	@Autowired
	TaxEventDetailsRepository taxEventDetailsRepository;

	@Autowired
	TransactionEventRepository transactionEventRepository;

	private ValidationService validationService;

	@Autowired
	public void setValidationService(ValidationService validationService) {
		this.validationService = validationService;
	}

	@Override
	public List<TaxEvent> findAll() {
		List<TaxEvent> isPresentTaxEvent = taxEventRepository.findAll();
		for (TaxEvent taxEvent : isPresentTaxEvent) {
			List<TaxEventDetails> taxEventDetail = taxEventDetailsRepository.findByTaxEventIdId(taxEvent.getId());
			taxEvent.setTaxEventDetails(taxEventDetail);
		}
		return isPresentTaxEvent;
	}

	@Override
	public Optional<TaxEvent> findById(Long id) {
		Optional<TaxEvent> isPresentTaxEvent = taxEventRepository.findById(id);
		if (isPresentTaxEvent.isPresent()) {
			TaxEvent taxEvent = isPresentTaxEvent.get();
			List<TaxEventDetails> taxEventDetail = taxEventDetailsRepository.findByTaxEventIdId(taxEvent.getId());
			taxEvent.setTaxEventDetails(taxEventDetail);
			return isPresentTaxEvent;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<TaxEvent> findByCode(String code) {
		Optional<TaxEvent> isPresentTaxEvent = taxEventRepository.findByCode(code);
		if (isPresentTaxEvent.isPresent()) {
			TaxEvent taxEvent = isPresentTaxEvent.get();
			List<TaxEventDetails> taxEventDetail = taxEventDetailsRepository.findByTaxEventIdId(taxEvent.getId());
			taxEvent.setTaxEventDetails(taxEventDetail);
			return isPresentTaxEvent;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<TaxEvent> findByName(String name) {
		Optional<TaxEvent> isPresentTaxEvent = taxEventRepository.findByNameContaining(name);
		if (isPresentTaxEvent.isPresent()) {
			TaxEvent taxEvent = isPresentTaxEvent.get();
			List<TaxEventDetails> taxEventDetail = taxEventDetailsRepository.findByTaxEventIdId(taxEvent.getId());
			taxEvent.setTaxEventDetails(taxEventDetail);
			return isPresentTaxEvent;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<TaxEvent> findByStatus(String status) {
		List<TaxEvent> isPresentTaxEvent = taxEventRepository.findByStatus(CommonStatus.valueOf(status));
		for (TaxEvent taxEvent : isPresentTaxEvent) {
			List<TaxEventDetails> taxEventDetail = taxEventDetailsRepository.findByTaxEventIdId(taxEvent.getId());
			taxEvent.setTaxEventDetails(taxEventDetail);
		}
		return isPresentTaxEvent;
	}

	@Override
	public TaxEvent addTaxEvent(String tenantId, AddTaxEventRequestResource taxEventAddResource) {

		Optional<TransactionEvent> transactionEvent = transactionEventRepository
				.findByCode(taxEventAddResource.getCode());
		if (!transactionEvent.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "code");
		} else {

			Optional<TaxEvent> isPresentTaxEvent = this.taxEventRepository.findByCodeAndStatus(
					taxEventAddResource.getCode(), CommonStatus.valueOf(taxEventAddResource.getStatus()));
			if (isPresentTaxEvent.isPresent()) {
				throw new CodeUniqueException(environment.getProperty("common.recorde-duplicate"));
			}
			TransactionEvent isPresentTransactionEvent = transactionEvent.get();
			if (!isPresentTransactionEvent.getDescription().equals(taxEventAddResource.getName())) {
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "name");
			}

			TaxEvent taxEvent = new TaxEvent();
			taxEvent.setTenantId(tenantId);
			taxEvent.setTransactionEvent(isPresentTransactionEvent);
			taxEvent.setCode(isPresentTransactionEvent.getCode());
			taxEvent.setName(isPresentTransactionEvent.getDescription());
			taxEvent.setStatus(CommonStatus.valueOf(taxEventAddResource.getStatus()));
			taxEvent.setCreatedDate(validationService.getCreateOrModifyDate());
			taxEvent.setSyncTs(validationService.getCreateOrModifyDate());
			taxEvent.setCreatedUser(taxEventAddResource.getTaxEventCreatedUser());

			return taxEventRepository.saveAndFlush(taxEvent);
		}
	}

	@Override
	public TaxEvent updateTaxEvent(String tenantId, UpdateTaxEventRequestResource taxEventUpdateResource, Long id) {

		Optional<TaxEvent> isPresentTaxEvent = taxEventRepository.findById(id);
		if (isPresentTaxEvent.isPresent()) {

			Optional<TransactionEvent> transactionEvent = transactionEventRepository
					.findByCode(taxEventUpdateResource.getCode());
			if (!transactionEvent.isPresent()) {
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "code");

			} else {

				Optional<TaxEvent> isPresentTaxEventByCode = this.taxEventRepository.findByCodeAndStatus(
						taxEventUpdateResource.getCode(), CommonStatus.valueOf(taxEventUpdateResource.getStatus()));
				if (isPresentTaxEventByCode.isPresent()) {
					throw new CodeUniqueException(environment.getProperty("common.recorde-duplicate"));
				}

				if (!isPresentTaxEvent.get().getVersion().equals(Long.parseLong(taxEventUpdateResource.getVersion()))) {
					throw new ValidateRecordException(environment.getProperty("common-invalid.version"), "version");
				}

				TransactionEvent isPresentTransactionEvent = transactionEvent.get();
				if (!isPresentTransactionEvent.getDescription().equals(taxEventUpdateResource.getName())) {
					throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "name");
				}

				TaxEvent taxEvent = isPresentTaxEvent.get();
				taxEvent.setTenantId(tenantId);
				taxEvent.setTransactionEvent(isPresentTransactionEvent);
				taxEvent.setCode(isPresentTransactionEvent.getCode());
				taxEvent.setName(isPresentTransactionEvent.getDescription());
				taxEvent.setStatus(CommonStatus.valueOf(taxEventUpdateResource.getStatus()));
				taxEvent.setModifiedDate(validationService.getCreateOrModifyDate());
				taxEvent.setSyncTs(validationService.getCreateOrModifyDate());
				taxEvent.setModifiedUser(taxEventUpdateResource.getModifiedUser());
				taxEvent.setVersion(validationService.stringToLong(taxEventUpdateResource.getVersion()));

				return taxEventRepository.saveAndFlush(taxEvent);
			}
		} else
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
	}

}
