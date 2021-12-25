package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.RepaymentAmountType;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.repository.RepaymentAmountTypeRepository;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.service.RepaymentAmountTypeService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Repayment Amount Type Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   	Task No    	Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-06-2021     FX-6619 		FX-6669     RavishikaS      Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class RepaymentAmountTypeServiceImpl extends MessagePropertyBase implements RepaymentAmountTypeService {
	
	@Autowired
	private RepaymentAmountTypeRepository repaymentAmountTypeRepository;
	
	@Autowired 
	private ValidationService validationService;
	
//	@Autowired
//	private RepaymentAmountTypeHistoryService repaymentAmountTypeHistoryService;

	@Override
	public List<RepaymentAmountType> getAll() {
		return repaymentAmountTypeRepository.findAll();
	}

	@Override
	public Optional<RepaymentAmountType> getById(Long id) {
		Optional<RepaymentAmountType> isPresentRepaymentAmountType= repaymentAmountTypeRepository.findById(id);
		if (isPresentRepaymentAmountType.isPresent()) {
			return Optional.ofNullable(isPresentRepaymentAmountType.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<RepaymentAmountType> getByCode(String code) {
		Optional<RepaymentAmountType> isPresentRepaymentAmountType= repaymentAmountTypeRepository.findByCode(code);
		if (isPresentRepaymentAmountType.isPresent()) {
			return Optional.ofNullable(isPresentRepaymentAmountType.get());
		}
		else {
			return Optional.empty();
		}
	}


	@Override
	public List<RepaymentAmountType> getByStatus(String status) {
		return repaymentAmountTypeRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public RepaymentAmountType addRepaymentAmountType(String tenantId, CommonAddResource commonAddResource) {
        
        Optional<RepaymentAmountType> isPresentRepaymentAmountType = repaymentAmountTypeRepository.findByCode(commonAddResource.getCode());
        
        if (isPresentRepaymentAmountType.isPresent()) 
        	throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE, EntityPoint.REPAYMENTAMOUNTTYPE);
        
        RepaymentAmountType repaymentAmountType = new RepaymentAmountType();
        repaymentAmountType.setTenantId(tenantId);
        repaymentAmountType.setCode(commonAddResource.getCode());
        repaymentAmountType.setName(commonAddResource.getName());
        repaymentAmountType.setDescription(commonAddResource.getDescription());
        repaymentAmountType.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
        repaymentAmountType.setCreatedDate(validationService.getCreateOrModifyDate());
        repaymentAmountType.setSyncTs(validationService.getCreateOrModifyDate());
        repaymentAmountType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        
        return repaymentAmountTypeRepository.save(repaymentAmountType);

	}

	@Override
	public RepaymentAmountType updateRepaymentAmountType(String tenantId, Long id, CommonUpdateResource commonUpdateResource) {
		
		Optional<RepaymentAmountType> isPresentRepaymentAmountType= repaymentAmountTypeRepository.findById(id);
		
		//repaymentAmountTypeHistoryService.saveHistory(tenantId, isPresentRepaymentAmountType.get(), LogginAuthentcation.getInstance().getUserName());
		
		if(!isPresentRepaymentAmountType.get().getVersion().equals(Long.parseLong(commonUpdateResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.REPAYMENTAMOUNTTYPE);
		
		if(!isPresentRepaymentAmountType.get().getCode().equalsIgnoreCase(commonUpdateResource.getCode()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_CODE_UPDATE), ServiceEntity.CODE, EntityPoint.REPAYMENTAMOUNTTYPE);
		
        RepaymentAmountType repaymentAmountType = isPresentRepaymentAmountType.get();
        repaymentAmountType.setTenantId(tenantId);
        repaymentAmountType.setName(commonUpdateResource.getName());
        repaymentAmountType.setDescription(commonUpdateResource.getDescription());
        repaymentAmountType.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus()));
        repaymentAmountType.setModifiedDate(validationService.getCreateOrModifyDate());
        repaymentAmountType.setSyncTs(validationService.getCreateOrModifyDate());
        repaymentAmountType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        
        return repaymentAmountTypeRepository.saveAndFlush(repaymentAmountType);
		
	}

}
