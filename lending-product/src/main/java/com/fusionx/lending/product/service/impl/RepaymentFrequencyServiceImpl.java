package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.RepaymentFrequency;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.RepaymentFrequencyRepository;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.resources.RepaymentFrequencyAddResource;
import com.fusionx.lending.product.resources.RepaymentFrequencyUpdateResource;
import com.fusionx.lending.product.service.RepaymentFrequencyHistoryService;
import com.fusionx.lending.product.service.RepaymentFrequencyService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Repayment Frequency Service Impl
 * 
 ********************************************************************************************************
 * ### Date Story Point Task No Author Description
 * -------------------------------------------------------------------------------------------------------
 * 1 07-07-2021 Dilki Created
 * 
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class RepaymentFrequencyServiceImpl extends MessagePropertyBase implements RepaymentFrequencyService {

	@Autowired
	private RepaymentFrequencyRepository repaymentFrequencyRepository;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private RepaymentFrequencyHistoryService repaymentFrequencyHistoryService;

	@Override
	public List<RepaymentFrequency> getAll() {
		return repaymentFrequencyRepository.findAll();
	}

	@Override
	public Optional<RepaymentFrequency> getById(Long id) {
		Optional<RepaymentFrequency> isPresentRepaymentFrequency = repaymentFrequencyRepository.findById(id);
		if (isPresentRepaymentFrequency.isPresent()) {
			return Optional.ofNullable(isPresentRepaymentFrequency.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<RepaymentFrequency> getByCode(String code) {
		Optional<RepaymentFrequency> isPresentRepaymentFrequency = repaymentFrequencyRepository.findByCode(code);
		if (isPresentRepaymentFrequency.isPresent()) {
			return Optional.ofNullable(isPresentRepaymentFrequency.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<RepaymentFrequency> getByName(String name) {
		return repaymentFrequencyRepository.findByNameContaining(name);
	}

	@Override
	public List<RepaymentFrequency> getByStatus(String status) {
		return repaymentFrequencyRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public RepaymentFrequency addRepaymentFrequency(String tenantId, RepaymentFrequencyAddResource repaymentFrequencyAddResource) {

		Optional<RepaymentFrequency> isPresentRepaymentFrequency = repaymentFrequencyRepository
				.findByCode(repaymentFrequencyAddResource.getCode());

		if (isPresentRepaymentFrequency.isPresent())
			throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE,
					EntityPoint.REPAYMENT_FREQUENCY);
		if (repaymentFrequencyAddResource.getOccurrencePerYear() != null && repaymentFrequencyAddResource.getOccurrencePerYear().equals("0"))
			throw new InvalidServiceIdException(environment.getProperty("repayment.frequency.invalid"), ServiceEntity.CODE,
					EntityPoint.REPAYMENT_FREQUENCY);
			
		RepaymentFrequency repaymentFrequency = new RepaymentFrequency();
		repaymentFrequency.setTenantId(tenantId);
		repaymentFrequency.setCode(repaymentFrequencyAddResource.getCode());
		repaymentFrequency.setName(repaymentFrequencyAddResource.getName());
		repaymentFrequency.setDescription(repaymentFrequencyAddResource.getDescription());
		repaymentFrequency.setStatus(CommonStatus.valueOf(repaymentFrequencyAddResource.getStatus()));
		repaymentFrequency.setOccurrencePerYear(new Integer(repaymentFrequencyAddResource.getOccurrencePerYear()).longValue());
		repaymentFrequency.setCreatedDate(validationService.getCreateOrModifyDate());
		repaymentFrequency.setSyncTs(validationService.getCreateOrModifyDate());
		repaymentFrequency.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return repaymentFrequencyRepository.save(repaymentFrequency);

	}

	@Override
	public RepaymentFrequency updateRepaymentFrequency(String tenantId, Long id,
			RepaymentFrequencyUpdateResource repaymentFrequencyUpdateResource) {

		Optional<RepaymentFrequency> isPresentRepaymentFrequency = repaymentFrequencyRepository.findById(id);

		if (isPresentRepaymentFrequency.isPresent()) {
			repaymentFrequencyHistoryService.saveHistory(tenantId, isPresentRepaymentFrequency.get(),
					LogginAuthentcation.getInstance().getUserName());

			if (!isPresentRepaymentFrequency.get().getVersion()
					.equals(Long.parseLong(repaymentFrequencyUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION, EntityPoint.REPAYMENT_FREQUENCY);

			if (!isPresentRepaymentFrequency.get().getCode().equalsIgnoreCase(repaymentFrequencyUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE,
						EntityPoint.REPAYMENT_FREQUENCY);
			
			if (repaymentFrequencyUpdateResource.getOccurrencePerYear() != null && repaymentFrequencyUpdateResource.getOccurrencePerYear().equals("0") )
				throw new InvalidServiceIdException(environment.getProperty("repayment.frequency.invalid"), ServiceEntity.CODE,
						EntityPoint.REPAYMENT_FREQUENCY);

			RepaymentFrequency repaymentFrequency = isPresentRepaymentFrequency.get();
			repaymentFrequency.setTenantId(tenantId);
			repaymentFrequency.setName(repaymentFrequencyUpdateResource.getName());
			repaymentFrequency.setDescription(repaymentFrequencyUpdateResource.getDescription());
			repaymentFrequency.setOccurrencePerYear(new Integer(repaymentFrequencyUpdateResource.getOccurrencePerYear()).longValue());
			repaymentFrequency.setStatus(CommonStatus.valueOf(repaymentFrequencyUpdateResource.getStatus()));
			repaymentFrequency.setModifiedDate(validationService.getCreateOrModifyDate());
			repaymentFrequency.setSyncTs(validationService.getCreateOrModifyDate());
			repaymentFrequency.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

			return repaymentFrequencyRepository.saveAndFlush(repaymentFrequency);
		} else
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");

	}

}
