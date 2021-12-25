package com.fusionx.lending.transaction.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.core.LogginAuthentcation;
import com.fusionx.lending.transaction.domain.BankTransactionCode;
import com.fusionx.lending.transaction.domain.BankTransactionSubCode;
import com.fusionx.lending.transaction.domain.WaiveOffTransactionType;
import com.fusionx.lending.transaction.domain.WaiveOffTransactionTypeHistory;
import com.fusionx.lending.transaction.domain.WaiveOffType;
import com.fusionx.lending.transaction.enums.BankTransactionCodeStatus;
import com.fusionx.lending.transaction.enums.BankTransactionSubCodeStatus;
import com.fusionx.lending.transaction.enums.CommonStatus;
import com.fusionx.lending.transaction.exception.ValidateRecordException;
import com.fusionx.lending.transaction.repo.BankTransactionCodeRepository;
import com.fusionx.lending.transaction.repo.BankTransactionSubCodeRepository;
import com.fusionx.lending.transaction.repo.WaiveOffTransactionTypeHistoryRepository;
import com.fusionx.lending.transaction.repo.WaiveOffTransactionTypeRepository;
import com.fusionx.lending.transaction.repo.WaiveOffTypeRepository;
import com.fusionx.lending.transaction.resource.WaiveOffTransactionTypeAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffTransactionTypeUpdateResource;
import com.fusionx.lending.transaction.service.ValidationService;
import com.fusionx.lending.transaction.service.WaiveOffTransactionTypeService;

@Component
@Transactional(rollbackFor=Exception.class)
public class WaiveOffTransactionTypeServiceImpl extends MessagePropertyBase implements WaiveOffTransactionTypeService {

	@Autowired
	private WaiveOffTransactionTypeRepository waiveOffTransactionTypeRepository;
	@Autowired
	private WaiveOffTypeRepository waiveOffTypeRepository;
	@Autowired
	private BankTransactionCodeRepository bankTransactionCodeRepository;
	@Autowired
	private BankTransactionSubCodeRepository bankTransactionSubCodeRepository;
	@Autowired
	private WaiveOffTransactionTypeHistoryRepository waiveOffTransactionTypeHistoryRepository;
	@Autowired
	private ValidationService validationService;
	
	@Override
	public List<WaiveOffTransactionType> findAll() {
		return waiveOffTransactionTypeRepository.findAll();
	}

	@Override
	public Optional<WaiveOffTransactionType> findById(Long waiveOffTransactionTypeId) {
		return waiveOffTransactionTypeRepository.findById(waiveOffTransactionTypeId);
	}

	@Override
	public List<WaiveOffTransactionType> findByStatus(String status) {
		return waiveOffTransactionTypeRepository.findByStatus(CommonStatus.valueOf(status));
	}
	
	@Override
	public List<WaiveOffTransactionType> findByWaiveOffTypeId(Long waiveOffTypeId) {
		return waiveOffTransactionTypeRepository.findByWaiveOffTypeId(waiveOffTypeId);
	}

	@Override
	public WaiveOffTransactionType addWaiveOffTransactionType(String tenantId,WaiveOffTransactionTypeAddResource waiveOffTransactionTypeAddResource) {
		Optional<WaiveOffType> optWaiveOffType = waiveOffTypeRepository.findById(Long.valueOf(waiveOffTransactionTypeAddResource.getWaiveOffTypeId()));
		if(!optWaiveOffType.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "waiveOffTypeId");
		}
		
		Optional<BankTransactionCode> optBankTransactionCode = bankTransactionCodeRepository.findByIdAndStatus(Long.valueOf(waiveOffTransactionTypeAddResource.getTransactionCodeId()),BankTransactionCodeStatus.ACTIVE);
		if(!optBankTransactionCode.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "transactionCodeId");
		}
		
		Optional<BankTransactionSubCode> optBankTransactionSubCode = bankTransactionSubCodeRepository.findByIdAndStatus(Long.valueOf(waiveOffTransactionTypeAddResource.getTransactionSubCodeId()),BankTransactionSubCodeStatus.ACTIVE);
		if(!optBankTransactionSubCode.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "transactionSubCodeId");
		}
		
		WaiveOffTransactionType waiveOffTransactionType = new WaiveOffTransactionType();
		waiveOffTransactionType.setTenantId(tenantId);
		waiveOffTransactionType.setStatus(CommonStatus.valueOf(waiveOffTransactionTypeAddResource.getStatus()));
		waiveOffTransactionType.setWaiveOffType(optWaiveOffType.get());
		waiveOffTransactionType.setTransactionCode(optBankTransactionCode.get());
		waiveOffTransactionType.setTransactionSubCode(optBankTransactionSubCode.get());
		waiveOffTransactionType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		waiveOffTransactionType.setCreatedDate(validationService.getCreateOrModifyDate());
		waiveOffTransactionType.setSyncTs(validationService.getCreateOrModifyDate());
		return waiveOffTransactionTypeRepository.save(waiveOffTransactionType);
	}

	@Override
	public WaiveOffTransactionType updateWaiveOffTransactionType(String tenantId, Long id,WaiveOffTransactionTypeUpdateResource waiveOffTransactionTypeUpdateResource) {
		Optional<WaiveOffTransactionType> optWaiveOffTransactionType = waiveOffTransactionTypeRepository.findById(id);
		if(optWaiveOffTransactionType.isPresent()) {
			if (!optWaiveOffTransactionType.get().getVersion().equals(Long.parseLong(waiveOffTransactionTypeUpdateResource.getVersion()))) {
				throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
			}
			
			Optional<WaiveOffType> optWaiveOffType = waiveOffTypeRepository.findById(Long.valueOf(waiveOffTransactionTypeUpdateResource.getWaiveOffTypeId()));
			if(!optWaiveOffType.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "waiveOffTypeId");
			} else {
				if(!(optWaiveOffType.get().getId().equals(optWaiveOffTransactionType.get().getWaiveOffType().getId()))) {
					throw new ValidateRecordException(environment.getProperty(WAIVE_OFF_TYPE_UPDATE_VALUE), "waiveOffTypeId");
				}
			}
			
			Optional<BankTransactionCode> optBankTransactionCode = bankTransactionCodeRepository.findByIdAndStatus(Long.valueOf(waiveOffTransactionTypeUpdateResource.getTransactionCodeId()),BankTransactionCodeStatus.ACTIVE);
			if(!optBankTransactionCode.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "transactionCodeId");
			}
			
			Optional<BankTransactionSubCode> optBankTransactionSubCode = bankTransactionSubCodeRepository.findByIdAndStatus(Long.valueOf(waiveOffTransactionTypeUpdateResource.getTransactionSubCodeId()),BankTransactionSubCodeStatus.ACTIVE);
			if(!optBankTransactionSubCode.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "transactionSubCodeId");
			} else {
				if(!(optWaiveOffTransactionType.get().getTransactionSubCode().getId().equals(Long.valueOf(waiveOffTransactionTypeUpdateResource.getTransactionSubCodeId())))) {
					throw new ValidateRecordException(environment.getProperty(TRANSACTIONSUBCODE_UPDATE_VALUE), "transactionSubCodeId");
				}	
			}
			
			WaiveOffTransactionType waiveOffTransactionType = optWaiveOffTransactionType.get();
			waiveOffTransactionType.setTenantId(tenantId);
			waiveOffTransactionType.setStatus(CommonStatus.valueOf(waiveOffTransactionTypeUpdateResource.getStatus()));
			waiveOffTransactionType.setWaiveOffType(optWaiveOffType.get());
			waiveOffTransactionType.setTransactionCode(optBankTransactionCode.get());
			waiveOffTransactionType.setTransactionSubCode(optBankTransactionSubCode.get());
			waiveOffTransactionType.setModifiedDate(validationService.getCreateOrModifyDate());
			waiveOffTransactionType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			waiveOffTransactionType = waiveOffTransactionTypeRepository.save(waiveOffTransactionType);
			
			addWaiveOffTransactionTypeHistoryDetails(tenantId, waiveOffTransactionType);
			
			return waiveOffTransactionType;
			
		} else {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		}
	}
	
	private WaiveOffTransactionTypeHistory addWaiveOffTransactionTypeHistoryDetails(String tenantId,WaiveOffTransactionType waiveOffTransactionType) {
		WaiveOffTransactionTypeHistory waiveOffTransactionTypeHistory = new WaiveOffTransactionTypeHistory();
		waiveOffTransactionTypeHistory.setTenantId(tenantId);
		waiveOffTransactionTypeHistory.setWaiveOffTransactionTypeId(waiveOffTransactionType.getId());
		waiveOffTransactionTypeHistory.setStatus(waiveOffTransactionType.getStatus());
		waiveOffTransactionTypeHistory.setCreatedUser(waiveOffTransactionType.getCreatedUser());
		waiveOffTransactionTypeHistory.setCreatedDate(waiveOffTransactionType.getCreatedDate());
		waiveOffTransactionTypeHistory.setModifiedUser(waiveOffTransactionType.getModifiedUser());
		waiveOffTransactionTypeHistory.setModifiedDate(waiveOffTransactionType.getModifiedDate());
		waiveOffTransactionTypeHistory.setSyncTs(validationService.getCreateOrModifyDate());
		waiveOffTransactionTypeHistory.setVersion(waiveOffTransactionType.getVersion());
		waiveOffTransactionTypeHistory.setWaiveOffType(waiveOffTransactionType.getWaiveOffType());
		waiveOffTransactionTypeHistory.setTransactionCode(waiveOffTransactionType.getTransactionCode());
		waiveOffTransactionTypeHistory.setTransactionSubCode(waiveOffTransactionType.getTransactionSubCode());
		waiveOffTransactionTypeHistory.setHistoryCreatedUser(LogginAuthentcation.getInstance().getUserName());
		waiveOffTransactionTypeHistory.setHistoryCreatedDate(validationService.getCreateOrModifyDate());
		return waiveOffTransactionTypeHistoryRepository.save(waiveOffTransactionTypeHistory);
	}

}
