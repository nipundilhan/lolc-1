package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.PayModes;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.PayModesRepository;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.service.PayModesService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class PayModesServiceImpl extends MessagePropertyBase implements PayModesService{
	
	@Autowired
	private PayModesRepository payModesRepository;
	
	@Autowired
	private ValidationService validationService;

	@Override
	public List<PayModes> getAll() {
		return payModesRepository.findAll();
	}

	@Override
	public Optional<PayModes> findById(Long id) {
		Optional<PayModes> isPresentPayModes= payModesRepository.findById(id);
		if (isPresentPayModes.isPresent()) {
			return Optional.ofNullable(isPresentPayModes.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<PayModes> getByCode(String code) {
		Optional<PayModes> isPresentPayModes= payModesRepository.findByCode(code);
		if (isPresentPayModes.isPresent()) {
			return Optional.ofNullable(isPresentPayModes.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<PayModes> getByName(String name) {
		return payModesRepository.findByNameContaining(name);
	}

	@Override
	public List<PayModes> getByStatus(String status) {
		return payModesRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public PayModes addPayModes(String tenantId, CommonAddResource commonAddResource) {
		 Optional<PayModes> isPresentPayModes = payModesRepository.findByCode(commonAddResource.getCode());
	        
	        if (isPresentPayModes.isPresent()) 
	        	throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE, EntityPoint.PAY_MODES);
	        
	        PayModes payModes = new PayModes();
	        payModes.setTenantId(tenantId);
	        payModes.setCode(commonAddResource.getCode());
	        payModes.setName(commonAddResource.getName());
	        payModes.setDescription(commonAddResource.getDescription());
	        payModes.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
	        payModes.setCreatedDate(validationService.getCreateOrModifyDate());
	        payModes.setSyncTs(validationService.getCreateOrModifyDate());
	        payModes.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	        
	        return payModesRepository.save(payModes);
	}

	@Override
	public PayModes updatePayModes(String tenantId, Long id, CommonUpdateResource commonUpdateResource) {
		
        Optional<PayModes> isPresentPayModes= payModesRepository.findById(id);
		
		if(isPresentPayModes.isPresent()) {
			
			if(!isPresentPayModes.get().getCode().equalsIgnoreCase(commonUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_CODE_UPDATE), ServiceEntity.CODE, EntityPoint.PAY_MODES);
			
			if(!isPresentPayModes.get().getVersion().equals(Long.parseLong(commonUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(INVALID_VERSION), ServiceEntity.VERSION, EntityPoint.PAY_MODES);
			
			PayModes payModes = isPresentPayModes.get();
			
			payModes.setTenantId(tenantId);
			payModes.setName(commonUpdateResource.getName());
			payModes.setDescription(commonUpdateResource.getDescription());
			payModes.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus()));
			payModes.setModifiedDate(validationService.getCreateOrModifyDate());
			payModes.setSyncTs(validationService.getCreateOrModifyDate());
			payModes.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        
	        return payModesRepository.saveAndFlush(payModes);
        
		}
		 else 
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND),MESSAGE);	
	}

}
