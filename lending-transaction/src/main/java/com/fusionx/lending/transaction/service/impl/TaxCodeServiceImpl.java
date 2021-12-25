package com.fusionx.lending.transaction.service.impl;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.domain.BankTransactionCode;
import com.fusionx.lending.transaction.domain.BankTransactionSubCode;
import com.fusionx.lending.transaction.domain.TaxCode;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.exception.BackendDataException;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.repo.BankTransactionCodeRepository;
import com.fusionx.lending.transaction.repo.BankTransactionSubCodeRepository;
import com.fusionx.lending.transaction.repo.TaxCodeRepository;
import com.fusionx.lending.transaction.resource.AddTaxCodeRequestResource;
import com.fusionx.lending.transaction.resource.TaxCodeResponse;
import com.fusionx.lending.transaction.resource.UpdateTaxCodeRequestResource;
import com.fusionx.lending.transaction.service.TaxCodeService;
import com.fusionx.lending.transaction.service.ValidateService;
import com.fusionx.lending.transaction.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional(rollbackFor = Exception.class)
public class TaxCodeServiceImpl extends MessagePropertyBase implements TaxCodeService {

	@Autowired
	TaxCodeRepository taxCodeRepository;
	
	@Autowired
	BankTransactionCodeRepository bankTransactionCodeRepository;
	
	@Autowired
	BankTransactionSubCodeRepository bankTransactionSubCodeRepository;
	
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
	public List<TaxCode> findAll() {
		return taxCodeRepository.findAll();
	}

	@Override
	public Optional<TaxCode> findById(Long id) {
		return taxCodeRepository.findById(id);
	}

	@Override
	public Optional<TaxCode> findByCode(String code) {
		return taxCodeRepository.findByCode(code);
	}

	@Override
	public Optional<TaxCode> findByName(String name) {
		return taxCodeRepository.findByNameContaining(name);
	}

	@Override
	public List<TaxCode> findByStatus(String status) {
		return taxCodeRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public List<TaxCode> findByBankTransactionCodeId(String bankTransactionCode) {
		return taxCodeRepository.findByBankTransactionCodeNameContaining(bankTransactionCode);
	}

	@Override
	public List<TaxCode> findByBankTransactionSubCode(String bankTransactionSubCode) {
		return taxCodeRepository.findByBankTransactionSubCodeNameContaining(bankTransactionSubCode);
	}

	@Override
	public TaxCode addTaxCode(String tenantId, AddTaxCodeRequestResource taxCodeAddResource) {

		TaxCodeResponse taxCodeResponse = validateService.validateTaxCode(tenantId, taxCodeAddResource.getCodeId(),
				taxCodeAddResource.getName());
		if (!taxCodeResponse.getTaxCode().equals(taxCodeAddResource.getCode())) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-code"), "code");
		}

		Optional<BankTransactionCode> bankTransactionCode = bankTransactionCodeRepository
				.findById(Long.parseLong(taxCodeAddResource.getTransactionTypeId()));
		if (!bankTransactionCode.isPresent()) {
			throw new BackendDataException("transactionTypeId", environment.getProperty("common.record-not-found"));
		}
		if (!bankTransactionCode.get().getCode().equals(taxCodeAddResource.getTransactionTypeCode())) {
			throw new BackendDataException("transactionTypeCode", environment.getProperty("common.invalid-value"));
		}

		Optional<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeRepository
				.findById(Long.parseLong(taxCodeAddResource.getSubTransactionTypeId()));
		if (!bankTransactionSubCode.isPresent()) {
			throw new BackendDataException("subTransactionTypeId", environment.getProperty("common.record-not-found"));
		}
		if (!bankTransactionSubCode.get().getSubCode().equals(taxCodeAddResource.getSubTransactionTypeCode())) {
			throw new BackendDataException("subTransactionTypeCode", environment.getProperty("common.invalid-value"));
		}

		TaxCode taxCode = new TaxCode();
		taxCode.setTenantId(tenantId);
		taxCode.setCodeId(taxCodeResponse.getId().toString());
		taxCode.setCode(taxCodeResponse.getTaxCode());
		taxCode.setName(taxCodeResponse.getTaxCodeName());
		taxCode.setDescription(taxCodeResponse.getTaxCodeDescription());
		taxCode.setBankTransactionCode(bankTransactionCode.get());
		taxCode.setBankTransactionCodeName(bankTransactionCode.get().getCode());
		taxCode.setBankTransactionSubCode(bankTransactionSubCode.get());
		taxCode.setBankTransactionSubCodeName(taxCodeAddResource.getSubTransactionTypeCode());
		taxCode.setStatus(CommonStatus.valueOf(taxCodeAddResource.getStatus()));
		taxCode.setCreatedDate(validationService.getCreateOrModifyDate());
		taxCode.setSyncTs(validationService.getCreateOrModifyDate());
		taxCode.setCreatedUser(taxCodeAddResource.getTaxCodeCreatedUser());

		return taxCodeRepository.saveAndFlush(taxCode);
	}

	@Override
	public TaxCode updateTaxCode(String tenantId, UpdateTaxCodeRequestResource taxCodeUpdateResource, Long id) {

		Optional<TaxCode> isPresentTaxCode = taxCodeRepository.findById(id);
		if (!isPresentTaxCode.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
		}

		TaxCodeResponse taxCodeResponse = validateService.validateTaxCode(tenantId, taxCodeUpdateResource.getCodeId(),
				taxCodeUpdateResource.getName());
		if (!taxCodeResponse.getTaxCode().equals(taxCodeUpdateResource.getCode())) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-code"), "code");
		}

		Optional<BankTransactionCode> bankTransactionCode = bankTransactionCodeRepository
				.findById(Long.parseLong(taxCodeUpdateResource.getTransactionTypeId()));
		if (!bankTransactionCode.isPresent()) {
			throw new BackendDataException("transactionTypeId", environment.getProperty("common.record-not-found"));
		}
		if (!bankTransactionCode.get().getCode().equals(taxCodeUpdateResource.getTransactionTypeCode())) {
			throw new BackendDataException("transactionTypeCode", environment.getProperty("common.invalid-value"));
		}

		Optional<BankTransactionSubCode> bankTransactionSubCode = bankTransactionSubCodeRepository
				.findById(Long.parseLong(taxCodeUpdateResource.getSubTransactionTypeId()));
		if (!bankTransactionSubCode.isPresent()) {
			throw new BackendDataException("subTransactionTypeId", environment.getProperty("common.record-not-found"));
		}
		if (!bankTransactionSubCode.get().getSubCode().equals(taxCodeUpdateResource.getSubTransactionTypeCode())) {
			throw new BackendDataException("subTransactionTypeCode", environment.getProperty("common.invalid-value"));
		}

		if (!isPresentTaxCode.get().getVersion().equals(Long.parseLong(taxCodeUpdateResource.getVersion()))) {
			throw new ValidateRecordException(environment.getProperty("common-invalid.version"), "version");
		}

		TaxCode taxCode = isPresentTaxCode.get();
		taxCode.setTenantId(tenantId);
		taxCode.setCodeId(taxCodeResponse.getId().toString());
		taxCode.setCode(taxCodeResponse.getTaxCode());
		taxCode.setName(taxCodeResponse.getTaxCodeName());
		taxCode.setDescription(taxCodeResponse.getTaxCodeDescription());
		taxCode.setBankTransactionCode(bankTransactionCode.get());
		taxCode.setBankTransactionCodeName(bankTransactionCode.get().getCode());
		taxCode.setBankTransactionSubCode(bankTransactionSubCode.get());
		taxCode.setBankTransactionSubCodeName(bankTransactionSubCode.get().getCode());
		taxCode.setStatus(CommonStatus.valueOf(taxCodeUpdateResource.getStatus()));
		taxCode.setModifiedDate(validationService.getCreateOrModifyDate());
		taxCode.setSyncTs(validationService.getCreateOrModifyDate());
		taxCode.setModifiedUser(taxCodeUpdateResource.getModifiedUser());
		taxCode.setVersion(validationService.stringToLong(taxCodeUpdateResource.getVersion()));

		return taxCodeRepository.saveAndFlush(taxCode);

	}

}
