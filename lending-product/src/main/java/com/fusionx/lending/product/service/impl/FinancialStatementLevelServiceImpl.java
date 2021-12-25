package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FinancialStatementLevel;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.FinancialStatementLevelRepository;
import com.fusionx.lending.product.resources.FinancialStatementLevelAddResourcess;
import com.fusionx.lending.product.resources.FinancialStatementLevelUpdateResourcess;
import com.fusionx.lending.product.service.FinancialStatementLevelService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Financial Statement Level Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-08-2021     FXL-356 	 FXL-591	Ishan        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class FinancialStatementLevelServiceImpl extends MessagePropertyBase implements FinancialStatementLevelService {

	private FinancialStatementLevelRepository financialStatementLevelRepository;
	private ValidationService validationService;
	
	@Autowired
	public void setFinancialStatementLevelRepository(FinancialStatementLevelRepository FinancialStatementLevelRepository) {
		this.financialStatementLevelRepository = FinancialStatementLevelRepository;
	}
	@Autowired
	public void setValidationService(ValidationService validationService) {
		this.validationService = validationService;
	}
	
	@Override
	public List<FinancialStatementLevel> getAll() {
		return financialStatementLevelRepository.findAll();
	}

	@Override
	public Optional<FinancialStatementLevel> getById(Long id) {
		return financialStatementLevelRepository.findById(id);
	}

	@Override
	public Optional<FinancialStatementLevel> getByCode(String code) {
		return financialStatementLevelRepository.findByCode(code);
	}

	@Override
	public List<FinancialStatementLevel> getByName(String name) {
		return financialStatementLevelRepository.findByNameContaining(name);
	}

	@Override
	public List<FinancialStatementLevel> getByStatus(String status) {
		return financialStatementLevelRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public FinancialStatementLevel addFinancialStatementLevel(String tenantId,
			FinancialStatementLevelAddResourcess request) {
		
		Optional<FinancialStatementLevel> optFinancialStatementLevel = financialStatementLevelRepository.findByCode(request.getCode());
		if(optFinancialStatementLevel.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE, EntityPoint.FINANCIAL_STATEMENT_LEVEL);
		}
		
		FinancialStatementLevel financialStatementLevel = new FinancialStatementLevel();
		financialStatementLevel.setTenantId(tenantId);
		financialStatementLevel.setCode(request.getCode());
		financialStatementLevel.setName(request.getName());
		financialStatementLevel.setDescription(request.getDescription());
		financialStatementLevel.setSequence(validationService.stringToLong(request.getSequence()));
		financialStatementLevel.setStatus(CommonStatus.valueOf(request.getStatus()));
		financialStatementLevel.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		financialStatementLevel.setCreatedDate(validationService.getCreateOrModifyDate());
		financialStatementLevel.setSyncTs(validationService.getCreateOrModifyDate());
		
		return financialStatementLevelRepository.save(financialStatementLevel);
	}

	@Override
	public FinancialStatementLevel updateFinancialStatementLevel(String tenantId, Long id,
			FinancialStatementLevelUpdateResourcess request) {
		
		Optional<FinancialStatementLevel> optFinancialStatementLevel = financialStatementLevelRepository.findById(id);
		if(optFinancialStatementLevel.isPresent()) {
			if (!optFinancialStatementLevel.get().getVersion().equals(Long.parseLong(request.getVersion()))) {
                throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.FINANCIAL_STATEMENT_LEVEL);
			}
            
            FinancialStatementLevel financialStatementLevel = optFinancialStatementLevel.get();
    		financialStatementLevel.setTenantId(tenantId);
    		financialStatementLevel.setCode(request.getCode());
    		financialStatementLevel.setName(request.getName());
    		financialStatementLevel.setDescription(request.getDescription());
    		financialStatementLevel.setSequence(validationService.stringToLong(request.getSequence()));
    		financialStatementLevel.setStatus(CommonStatus.valueOf(request.getStatus()));
    		financialStatementLevel.setSyncTs(validationService.getCreateOrModifyDate());
    		financialStatementLevel.setModifiedDate(validationService.getCreateOrModifyDate());
    		financialStatementLevel.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
    		
    		return financialStatementLevelRepository.saveAndFlush(financialStatementLevel);
    		
		} else {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		}
	}

}
