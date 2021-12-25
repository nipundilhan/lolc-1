package com.fusionx.lending.transaction.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.BankTransactionCode;
import com.fusionx.lending.transaction.domain.BankTransactionSubCode;
import com.fusionx.lending.transaction.domain.CoreTransactionMethod;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.enums.BankTransactionSubCodeStatus;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.enums.CoreTransactionMethodCode;
import com.fusionx.lending.transaction.enums.ServiceEntity;
import com.fusionx.lending.transaction.exception.InvalidServiceIdException;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.repo.BankTransactionCodeRepository;
import com.fusionx.lending.transaction.repo.BankTransactionSubCodeRepository;
import com.fusionx.lending.transaction.repo.CoreTransactionMethodRepository;
import com.fusionx.lending.transaction.resource.CoreTransactionAddResource;
import com.fusionx.lending.transaction.resource.CoreTransactionUpdateResource;
import com.fusionx.lending.transaction.service.CoreTransactionMethodHistoryService;
import com.fusionx.lending.transaction.service.CoreTransactionMethodService;
import com.fusionx.lending.transaction.service.ValidationService;

/**
 * Core Transaction Service Impl
 * <p>
 * *******************************************************************************************************
 * ### Date Story Point Task No Author Description
 * -------------------------------------------------------------------------------------------------------
 * 1 01-10-2021 FXL-1052 FXL-1001 Pasindu Thanthree Created
 * <p>
 * *******************************************************************************************************
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class CoreTransactionMethodServiceImpl extends MessagePropertyBase implements CoreTransactionMethodService {

    @Autowired
    private CoreTransactionMethodRepository coreTransactionRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CoreTransactionMethodHistoryService coreTransactionHistoryService;
    
    @Autowired
    private BankTransactionCodeRepository bankTransactionCodeRepository;
    
    
    @Autowired
    private BankTransactionSubCodeRepository bankTransactionSubCodeRepository;

    @Override
    public List<CoreTransactionMethod> getAll() {
        return coreTransactionRepository.findAll();
    }

    @Override
    public Optional<CoreTransactionMethod> findByCode(String code) {
        Optional<CoreTransactionMethod> isPresentBrand = coreTransactionRepository.findByCode(code);
        if (isPresentBrand.isPresent()) {
            return Optional.ofNullable(isPresentBrand.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<CoreTransactionMethod> findById(Long id) {
        Optional<CoreTransactionMethod> isPresentBrand = coreTransactionRepository.findById(id);
        if (isPresentBrand.isPresent()) {
            return Optional.ofNullable(isPresentBrand.get());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<CoreTransactionMethod> findByName(String name) {
        return coreTransactionRepository.findByNameContaining(name);
    }

    @Override
    public List<CoreTransactionMethod> findByStatus(String status) {
        return coreTransactionRepository.findByStatus(CommonStatus.valueOf(status));
    }

    @Override
	public CoreTransactionMethod addCoreTransaction(String tenantId, CoreTransactionAddResource commonAddResource) {

		CoreTransactionMethod coreTransaction = new CoreTransactionMethod();
		coreTransaction.setTenantId(tenantId);
		coreTransaction.setCode(CoreTransactionMethodCode.valueOf(commonAddResource.getCode()));
		coreTransaction.setName(commonAddResource.getName());
		coreTransaction.setDescription(commonAddResource.getDescription());
		coreTransaction.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));

		Optional<BankTransactionCode> isbankTransactionCode = bankTransactionCodeRepository.findByIdAndStatus(
				new Integer(commonAddResource.getTransactionCodeId()).longValue(), BankTransactionCodeStatus.ACTIVE);
		if (!isbankTransactionCode.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(BANKTRANSACTIONCODE_INVALID_VALUE),
					ServiceEntity.BANKTRANSACTIONSUB);
		} else {
			coreTransaction.setBankTransactionCode(isbankTransactionCode.get());
		}

		// validation for BankTransactionSubCode
		Optional<BankTransactionSubCode> isbankTransactionSubCode = bankTransactionSubCodeRepository
				.findByIdAndBankTransactionCodeIdAndStatus(
						new Integer(commonAddResource.getSubTransactionCodeId()).longValue(),
						new Integer(commonAddResource.getTransactionCodeId()).longValue(),
						BankTransactionSubCodeStatus.ACTIVE);
		if (!isbankTransactionSubCode.isPresent()) {

			throw new InvalidServiceIdException(environment.getProperty(BANKTRANSACTIONSUBCODE_INVALID_VALUE),
					ServiceEntity.BANKTRANSACTIONSUB);

		} else {
			coreTransaction.setBankTransactionSubCode(isbankTransactionSubCode.get());
		}

		coreTransaction.setCreatedDate(validationService.getCreateOrModifyDate());
		coreTransaction.setSyncTs(validationService.getCreateOrModifyDate());
		coreTransaction.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return coreTransactionRepository.save(coreTransaction);

	}

    @Override
	public CoreTransactionMethod updateCoreTransaction(String tenantId, Long id,
			CoreTransactionUpdateResource coreTransactionUpdateResource) {

		Optional<CoreTransactionMethod> isPresentCoreTransaction = coreTransactionRepository.findById(id);

		if (isPresentCoreTransaction.isPresent()) {

			if (!isPresentCoreTransaction.get().getVersion()
					.equals(Long.parseLong(coreTransactionUpdateResource.getVersion())))
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");

			CoreTransactionMethod coreTransaction = isPresentCoreTransaction.get();

			coreTransactionHistoryService.saveHistory(tenantId, coreTransaction,
					LogginAuthentcation.getInstance().getUserName());

			if (!isPresentCoreTransaction.get().getVersion()
					.equals(Long.parseLong(coreTransactionUpdateResource.getVersion()))) {

				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);
			}

			coreTransaction.setTenantId(tenantId);
			coreTransaction.setName(coreTransactionUpdateResource.getName());
			coreTransaction.setDescription(coreTransactionUpdateResource.getDescription());
			

			Optional<BankTransactionCode> isbankTransactionCode = bankTransactionCodeRepository.findByIdAndStatus(
					new Integer(coreTransactionUpdateResource.getTransactionCodeId()).longValue(),
					BankTransactionCodeStatus.ACTIVE);

			
			if (!isbankTransactionCode.isPresent()) {
				throw new InvalidServiceIdException(environment.getProperty(BANKTRANSACTIONCODE_INVALID_VALUE),
						ServiceEntity.BANKTRANSACTIONSUB);
			} else {
				coreTransaction.setBankTransactionCode(isbankTransactionCode.get());
			}

			

			Optional<BankTransactionSubCode> isbankTransactionSubCode = bankTransactionSubCodeRepository
					.findByIdAndBankTransactionCodeIdAndStatus(
							new Integer(coreTransactionUpdateResource.getSubTransactionCodeId()).longValue(),
							new Integer(coreTransactionUpdateResource.getTransactionCodeId()).longValue(),
							BankTransactionSubCodeStatus.ACTIVE);

			if (!isbankTransactionSubCode.isPresent()) {

				throw new InvalidServiceIdException(environment.getProperty(BANKTRANSACTIONSUBCODE_INVALID_VALUE),
						ServiceEntity.BANKTRANSACTIONSUB);

			} else {
				coreTransaction.setBankTransactionSubCode(isbankTransactionSubCode.get());
			}

			coreTransaction.setStatus(CommonStatus.valueOf(coreTransactionUpdateResource.getStatus()));
			coreTransaction.setModifiedDate(validationService.getCreateOrModifyDate());
			coreTransaction.setSyncTs(validationService.getCreateOrModifyDate());
			coreTransaction.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

			return coreTransactionRepository.saveAndFlush(coreTransaction);

		} else {
			throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");
		}

	}
}