package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Brand;
import com.fusionx.lending.product.domain.ServiceAccessChannel;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.repository.ServiceAccessChannelRepository;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.service.ServiceAccessChannelHistoryService;
import com.fusionx.lending.product.service.ServiceAccessChannelService;
import com.fusionx.lending.product.service.ValidationService;

/**
 * Service Access Channel Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021                           Nipun Dilhan        Created
 *    
 ********************************************************************************************************
 */


@Component
@Transactional(rollbackFor=Exception.class)
public class ServiceAccessChannelServiceImpl extends MessagePropertyBase implements ServiceAccessChannelService {
	
	@Autowired
	private ServiceAccessChannelRepository serviceAccessChannelRepository;
	
	@Autowired 
	private ValidationService validationService;
	
	@Autowired
	private ServiceAccessChannelHistoryService serviceAccessChannelHistoryService;

	@Override
	public List<ServiceAccessChannel> getAll() {
		return serviceAccessChannelRepository.findAll();
	}
	
	@Override
	public Optional<ServiceAccessChannel> getByCode(String code) {
		Optional<ServiceAccessChannel> isPresentBrand= serviceAccessChannelRepository.findByCode(code);
		if (isPresentBrand.isPresent()) {
			return Optional.ofNullable(isPresentBrand.get());
		}
		else {
			return Optional.empty();
		}
	}
	
	
	@Override
	public Optional<ServiceAccessChannel> findById(Long id) {
		Optional<ServiceAccessChannel> isPresentBrand= serviceAccessChannelRepository.findById(id);
		if (isPresentBrand.isPresent()) {
			return Optional.ofNullable(isPresentBrand.get());
		}
		else {
			return Optional.empty();
		}
	}
	
	@Override
	public List<ServiceAccessChannel> getByName(String name) {
		return serviceAccessChannelRepository.findByNameContaining(name);
	}
	
	@Override
	public List<ServiceAccessChannel> getByStatus(String status) {
		return serviceAccessChannelRepository.findByStatus(CommonStatus.valueOf(status));
	}

	
	@Override
	public ServiceAccessChannel addServiceAccessChannel(String tenantId, CommonAddResource commonAddResource) {

        Optional<ServiceAccessChannel> isPresentServiceAccessChannel = serviceAccessChannelRepository.findByCode(commonAddResource.getCode());
        //serviceAccessChannelRepository.findByCode(commonAddResource.getCode())
        if (isPresentServiceAccessChannel.isPresent()) 
        	throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.SERVICE_ACCESS_CHANNEL); //should be change to EntityPoint.ServiceAccessChannel
        
        ServiceAccessChannel serviceAccessChannel = new ServiceAccessChannel();
        serviceAccessChannel.setTenantId(tenantId);
        serviceAccessChannel.setCode(commonAddResource.getCode());
        serviceAccessChannel.setName(commonAddResource.getName());
        serviceAccessChannel.setDescription(commonAddResource.getDescription());
        serviceAccessChannel.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
        serviceAccessChannel.setCreatedDate(validationService.getCreateOrModifyDate());
        serviceAccessChannel.setSyncTs(validationService.getCreateOrModifyDate());
        serviceAccessChannel.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        
        return serviceAccessChannelRepository.save(serviceAccessChannel);

	}
	
	
	@Override
	public ServiceAccessChannel updateServiceAccessChannel(String tenantId, Long id, CommonUpdateResource commonUpdateResource) {
		
		Optional<ServiceAccessChannel> isPresentServiceAccessChannel= serviceAccessChannelRepository.findById(id);
		
		if(!isPresentServiceAccessChannel.get().getVersion().equals(Long.parseLong(commonUpdateResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.SERVICE_ACCESS_CHANNEL);
		
		if(!isPresentServiceAccessChannel.get().getCode().equalsIgnoreCase(commonUpdateResource.getCode()))
			throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.SERVICE_ACCESS_CHANNEL);
		
		serviceAccessChannelHistoryService.saveHistory(tenantId, isPresentServiceAccessChannel.get(), LogginAuthentcation.getInstance().getUserName());
		
		ServiceAccessChannel serviceAccessChannel = isPresentServiceAccessChannel.get();
		serviceAccessChannel.setTenantId(tenantId);
		serviceAccessChannel.setName(commonUpdateResource.getName());
		serviceAccessChannel.setDescription(commonUpdateResource.getDescription());
		serviceAccessChannel.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus()));
		serviceAccessChannel.setModifiedDate(validationService.getCreateOrModifyDate());
		serviceAccessChannel.setSyncTs(validationService.getCreateOrModifyDate());
		serviceAccessChannel.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
        
        return serviceAccessChannelRepository.saveAndFlush(serviceAccessChannel);
		
	}


}