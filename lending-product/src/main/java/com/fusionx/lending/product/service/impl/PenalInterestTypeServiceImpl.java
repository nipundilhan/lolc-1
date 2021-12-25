package com.fusionx.lending.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.ApplicationFrequency;
import com.fusionx.lending.product.domain.CalculationFrequency;
import com.fusionx.lending.product.domain.PenalInterestType;
import com.fusionx.lending.product.domain.PenalInterestTypeDetails;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.ApplicationFrequencyRepository;
import com.fusionx.lending.product.repository.CalculationFrequencyRepository;
import com.fusionx.lending.product.repository.PenalInterestTypeDetailsRepository;
import com.fusionx.lending.product.repository.PenalInterestTypeRepository;
import com.fusionx.lending.product.resources.BankTransactionSubCodeResponse;
import com.fusionx.lending.product.resources.PenalInterestTypeAddResource;
import com.fusionx.lending.product.resources.PenalInterestTypeDetailsResource;
import com.fusionx.lending.product.resources.PenalInterestTypeUpdateResource;
import com.fusionx.lending.product.service.PenalInterestTypeService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class PenalInterestTypeServiceImpl extends MessagePropertyBase implements PenalInterestTypeService {

	@Autowired
	private PenalInterestTypeRepository penalInterestTypeRepository;

	@Autowired
	private PenalInterestTypeDetailsRepository penalInterestTypeDetailsRepository;

	@Autowired
	private CalculationFrequencyRepository calculationFrequencyRepository;

	@Autowired
	private ApplicationFrequencyRepository applicationFrequencyRepository;

	@Autowired
	private ValidationService validationService;

	@Override
	public List<PenalInterestType> getAll() {

		List<PenalInterestType> allPenalInterestType = penalInterestTypeRepository.findAll();
		List<PenalInterestType> penalInterestType = new ArrayList<>();
		List<PenalInterestTypeDetails> penalInterestTypeDetails = new ArrayList<>();
		if(allPenalInterestType!=null && !allPenalInterestType.isEmpty()) {
			for (PenalInterestType item : allPenalInterestType) {
				penalInterestTypeDetails = penalInterestTypeDetailsRepository.findByPenalInterestTypeId(item.getId());
				item.setPenalInterestTypeDetails(penalInterestTypeDetails);
				penalInterestType.add(item);
			}
		}
		return penalInterestType;
	}

	@Override
	public Optional<PenalInterestType> getById(Long id) {
		Optional<PenalInterestType> isPresentPenalInterestType = penalInterestTypeRepository.findById(id);
		if (isPresentPenalInterestType.isPresent()) {
			PenalInterestType penalInterestType = isPresentPenalInterestType.get();
			List<PenalInterestTypeDetails> penalInterestTypeDetails = penalInterestTypeDetailsRepository
					.findByPenalInterestTypeId(penalInterestType.getId());
			penalInterestType.setPenalInterestTypeDetails(penalInterestTypeDetails);
			return isPresentPenalInterestType;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<PenalInterestType> getByCode(String code) {
		Optional<PenalInterestType> isPenalInterestType = penalInterestTypeRepository.findByCode(code);
		if (isPenalInterestType.isPresent()) {
			PenalInterestType penalInterestType = isPenalInterestType.get();
			List<PenalInterestTypeDetails> penalInterestTypeDetails = penalInterestTypeDetailsRepository
					.findByPenalInterestTypeId(penalInterestType.getId());
			penalInterestType.setPenalInterestTypeDetails(penalInterestTypeDetails);
			return isPenalInterestType;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<PenalInterestType> getByName(String name) {

		List<PenalInterestType> allPenalInterestType = penalInterestTypeRepository.findByNameContaining(name);
		List<PenalInterestType> penalInterestType = new ArrayList<>();
		List<PenalInterestTypeDetails> penalInterestTypeDetails = new ArrayList<>();
		if(allPenalInterestType!=null && !allPenalInterestType.isEmpty()) {
			for (PenalInterestType item : allPenalInterestType) {
				penalInterestTypeDetails = penalInterestTypeDetailsRepository.findByPenalInterestTypeId(item.getId());
				item.setPenalInterestTypeDetails(penalInterestTypeDetails);
				penalInterestType.add(item);
			}
		}
		return penalInterestType;

	}

	@Override
	public List<PenalInterestType> getByStatus(String status) {
		List<PenalInterestType> penalInterestTypeAll = penalInterestTypeRepository
				.findByStatus(CommonStatus.valueOf(status));
		List<PenalInterestType> penalInterestType = new ArrayList<>();
		List<PenalInterestTypeDetails> penalInterestTypeDetails = new ArrayList<>();
		if(penalInterestTypeAll!=null && !penalInterestTypeAll.isEmpty()) {
			for (PenalInterestType item : penalInterestTypeAll) {
				penalInterestTypeDetails = penalInterestTypeDetailsRepository.findByPenalInterestTypeId(item.getId());
				item.setPenalInterestTypeDetails(penalInterestTypeDetails);
				penalInterestType.add(item);
			}
		}
		return penalInterestType;
	}

	@Override
	public PenalInterestType addPenalInterestType(String tenantId,
			PenalInterestTypeAddResource penalInterestTypeAddResource) {

		Optional<PenalInterestType> isPresentPenalInterestType = penalInterestTypeRepository
				.findByCode(penalInterestTypeAddResource.getCode());

		if (isPresentPenalInterestType.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE,
					EntityPoint.PENAL_INTEREST_TYPE);

		Optional<ApplicationFrequency> isApplicationFrequency = applicationFrequencyRepository
				.findByIdAndStatus(validationService.stringToLong(penalInterestTypeAddResource.getApplicationFrequencyId()),CommonStatus.ACTIVE);
		if (isApplicationFrequency.isPresent()) {
			if (!isApplicationFrequency.get().getName()
					.equalsIgnoreCase(penalInterestTypeAddResource.getApplicationFrequencyName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),
						"applicationFrequencyName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "applicationFrequencyId");
		}

		Optional<CalculationFrequency> isCalculationFrequency = calculationFrequencyRepository
				.findByIdAndStatus(validationService.stringToLong(penalInterestTypeAddResource.getCalculationFrequencyId()),CommonStatus.ACTIVE);
		if (isCalculationFrequency.isPresent()) {
			if (!isCalculationFrequency.get().getName()
					.equalsIgnoreCase(penalInterestTypeAddResource.getCalculationFrequencyName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),
						"calculationFrequencyName");

		} else {

			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "calculationFrequencyId");
		}

		BankTransactionSubCodeResponse subCodeResponse = validationService.validateBankTransactionSubCode(tenantId,
				penalInterestTypeAddResource.getTransSubCodeId(),
				penalInterestTypeAddResource.getTransSubCodeDescription(), EntityPoint.PENAL_INTEREST_TYPE);

		PenalInterestType penalInterestType = new PenalInterestType();
		penalInterestType.setTenantId(tenantId);
		penalInterestType.setApplicationFrequency(isApplicationFrequency.get());
		penalInterestType.setCalculationFrequency(isCalculationFrequency.get());
		penalInterestType
				.setTransSubCodeId(validationService.stringToLong(penalInterestTypeAddResource.getTransSubCodeId()));
		//penalInterestType.setTransSubCodeDesc(penalInterestTypeAddResource.getTransSubCodeDescription());
		penalInterestType.setTransSubCode(subCodeResponse.getSubCode());
		penalInterestType.setCode(penalInterestTypeAddResource.getCode());
		penalInterestType.setName(penalInterestTypeAddResource.getName());
		penalInterestType.setDescription(penalInterestTypeAddResource.getDescription());
		penalInterestType.setStatus(CommonStatus.valueOf(penalInterestTypeAddResource.getStatus()));
		penalInterestType.setCreatedDate(validationService.getCreateOrModifyDate());
		penalInterestType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		penalInterestType.setSyncTs(validationService.getCreateOrModifyDate());

		penalInterestType = penalInterestTypeRepository.save(penalInterestType);

		if (penalInterestTypeAddResource.getPenalInterestTypeDetails() != null
				&& !penalInterestTypeAddResource.getPenalInterestTypeDetails().isEmpty()) {
			Integer index = 0;
			for (PenalInterestTypeDetailsResource penalInterestTypeDetailsResource : penalInterestTypeAddResource
					.getPenalInterestTypeDetails()) {
				saveOrUpadatePenalInterestTypeDetails(penalInterestTypeDetailsResource.getId(), tenantId,
						penalInterestTypeDetailsResource.getSubTransTypeId(),
						penalInterestTypeDetailsResource.getSubTransTypeCode(),
						penalInterestTypeDetailsResource.getSubTransTypeDescription(), penalInterestType,
						penalInterestTypeDetailsResource.getStatus(), index);
				index++;
			}
		}

		return penalInterestType;
	}

	private void saveOrUpadatePenalInterestTypeDetails(String id, String tenantId, String subTransTypeId,
			String subTransTypeCode, String subTransTypeDescription, PenalInterestType penalInterestType, String status,
			Integer index) {

		PenalInterestTypeDetails penalInterestTypeDetails = new PenalInterestTypeDetails();
		if (id != null && !id.isEmpty()) {
			Optional<PenalInterestTypeDetails> penalInterestTypeDetailsOptional = penalInterestTypeDetailsRepository
					.findById(validationService.stringToLong(id));
			if (!penalInterestTypeDetailsOptional.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
			} else {
				penalInterestTypeDetails = penalInterestTypeDetailsOptional.get();
				penalInterestTypeDetails.setModifiedDate(validationService.getCreateOrModifyDate());
				penalInterestTypeDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			}
		} else {
			penalInterestTypeDetails.setTenantId(tenantId);
			penalInterestTypeDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			penalInterestTypeDetails.setCreatedDate(validationService.getCreateOrModifyDate());
		}

		BankTransactionSubCodeResponse subCodeResponse = validationService.validateBankTransactionSubCode(tenantId,
				subTransTypeId, subTransTypeDescription, EntityPoint.PENAL_INTEREST_TYPE);

		penalInterestTypeDetails.setSubTransTypeId(validationService.stringToLong(subTransTypeId));
		penalInterestTypeDetails.setSubTransTypeCode(subCodeResponse.getSubCode());
		//penalInterestTypeDetails.setSubTransTypeDesc(subTransTypeDescription);
		penalInterestTypeDetails.setPenalInterestType(penalInterestType);
		penalInterestTypeDetails.setStatus(CommonStatus.valueOf(status));
		penalInterestTypeDetails.setSyncTs(validationService.getCreateOrModifyDate());

		penalInterestTypeDetailsRepository.saveAndFlush(penalInterestTypeDetails);

	}

	@Override
	public PenalInterestType updatePenalInterestType(String tenantId, Long id,
			PenalInterestTypeUpdateResource penalInterestTypeUpdateResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);

		Optional<PenalInterestType> isPresentpenalInterestType = penalInterestTypeRepository.findById(id);

		if (!isPresentpenalInterestType.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);

		if (!isPresentpenalInterestType.get().getVersion()
				.equals(validationService.stringToLong(penalInterestTypeUpdateResource.getVersion())))
		throw new InvalidServiceIdException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION,
				EntityPoint.PENAL_INTEREST_TYPE);
		
		if (!isPresentpenalInterestType.get().getCode().equalsIgnoreCase(penalInterestTypeUpdateResource.getCode()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_CODE_UPDATE), ServiceEntity.CODE,
					EntityPoint.PENAL_INTEREST_TYPE);

		Optional<ApplicationFrequency> isApplicationFrequency = applicationFrequencyRepository
				.findByIdAndStatus(validationService.stringToLong(penalInterestTypeUpdateResource.getApplicationFrequencyId()),CommonStatus.ACTIVE);
		if (isApplicationFrequency.isPresent()) {
			if (!isApplicationFrequency.get().getName()
					.equalsIgnoreCase(penalInterestTypeUpdateResource.getApplicationFrequencyName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),
						"applicationFrequencyName");

		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "applicationFrequencyId");
		}

		Optional<CalculationFrequency> isCalculationFrequency = calculationFrequencyRepository
				.findByIdAndStatus(validationService.stringToLong(penalInterestTypeUpdateResource.getCalculationFrequencyId()),CommonStatus.ACTIVE);
		if (isCalculationFrequency.isPresent()) {
			if (!isCalculationFrequency.get().getName()
					.equalsIgnoreCase(penalInterestTypeUpdateResource.getCalculationFrequencyName()))
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),
						"calculationFrequencyName");

		} else {

			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "calculationFrequencyId");
		}

		BankTransactionSubCodeResponse subCodeResponse = validationService.validateBankTransactionSubCode(tenantId,
				penalInterestTypeUpdateResource.getTransSubCodeId(),
				penalInterestTypeUpdateResource.getTransSubCodeDescription(), EntityPoint.PENAL_INTEREST_TYPE);
		
		PenalInterestType penalInterestType = isPresentpenalInterestType.get();

		penalInterestType.setApplicationFrequency(isApplicationFrequency.get());
		penalInterestType.setCalculationFrequency(isCalculationFrequency.get());
		penalInterestType
				.setTransSubCodeId(validationService.stringToLong(penalInterestTypeUpdateResource.getTransSubCodeId()));
		//penalInterestType.setTransSubCodeDesc(penalInterestTypeUpdateResource.getTransSubCodeDescription());
		penalInterestType.setTransSubCode(subCodeResponse.getSubCode());
		penalInterestType.setName(penalInterestTypeUpdateResource.getName());
		penalInterestType.setDescription(penalInterestTypeUpdateResource.getDescription());
		penalInterestType.setStatus(CommonStatus.valueOf(penalInterestTypeUpdateResource.getStatus()));
		penalInterestType.setModifiedDate(validationService.getCreateOrModifyDate());
		penalInterestType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		penalInterestType.setSyncTs(validationService.getCreateOrModifyDate());

		penalInterestType = penalInterestTypeRepository.saveAndFlush(penalInterestType);

		if (penalInterestTypeUpdateResource.getPenalInterestTypeDetails() != null
				&& !penalInterestTypeUpdateResource.getPenalInterestTypeDetails().isEmpty()) {
			Integer index = 0;
			for (PenalInterestTypeDetailsResource penalInterestTypeDetailsResource : penalInterestTypeUpdateResource
					.getPenalInterestTypeDetails()) {
				saveOrUpadatePenalInterestTypeDetails(penalInterestTypeDetailsResource.getId(), tenantId,
						penalInterestTypeDetailsResource.getSubTransTypeId(),
						penalInterestTypeDetailsResource.getSubTransTypeCode(),
						penalInterestTypeDetailsResource.getSubTransTypeDescription(), penalInterestType,
						penalInterestTypeDetailsResource.getStatus(), index);
				index++;
			}
		}

		return penalInterestType;
	}

}
