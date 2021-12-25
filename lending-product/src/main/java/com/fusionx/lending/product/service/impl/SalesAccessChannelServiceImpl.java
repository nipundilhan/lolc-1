package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.SalesAccessChannel;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.SalesAccessChannelRepository;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.service.SalesAccessChannelHistoryService;
import com.fusionx.lending.product.service.SalesAccessChannelService;
import com.fusionx.lending.product.service.ValidationService;
/**
 * Sales Access Channel service
 * @author 	Piyumi
 * @Dat     07-07-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021   FXL-1          FXL-3      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class SalesAccessChannelServiceImpl implements SalesAccessChannelService {
	
	@Autowired
	Environment environment;
	
	@Autowired
	private SalesAccessChannelRepository salesAccessChannelRepository;
	
	@Autowired
	private SalesAccessChannelHistoryService salesAccessChannelHistoryService;
	
	@Autowired
	private ValidationService validationService;

	@Override
	public List<SalesAccessChannel> getAll() {
		return salesAccessChannelRepository.findAll();
	}

	@Override
	public Optional<SalesAccessChannel> findById(Long id) {
		Optional<SalesAccessChannel> isPresentSalesAccessChannel= salesAccessChannelRepository.findById(id);
		if (isPresentSalesAccessChannel.isPresent()) {
			return Optional.ofNullable(isPresentSalesAccessChannel.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<SalesAccessChannel> findByStatus(String status) {
		return salesAccessChannelRepository.findByStatus(CommonStatus.valueOf(status));
	}


	@Override
	public Optional<SalesAccessChannel> findByCode(String code) {
		Optional<SalesAccessChannel> isPresentSalesAccessChannel= salesAccessChannelRepository.findByCode(code);
		if (isPresentSalesAccessChannel.isPresent()) {
			return Optional.ofNullable(isPresentSalesAccessChannel.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<SalesAccessChannel> findByName(String name) {
		return salesAccessChannelRepository.findByNameContaining(name);
	}


	@Override
	public SalesAccessChannel addSalesAccessChannel(String tenantId,
			CommonAddResource commonAddResource) {
		 Optional<SalesAccessChannel> isPresentSalesAccessChannel = salesAccessChannelRepository.findByCode(commonAddResource.getCode());
	        
	        if (isPresentSalesAccessChannel.isPresent()) 
	        	throw new InvalidServiceIdException(environment.getProperty("common.duplicate"), ServiceEntity.CODE, EntityPoint.SALES_ACCESS_CHANNEL);
	        
	        SalesAccessChannel salesAccessChannel = new SalesAccessChannel();
	        salesAccessChannel.setTenantId(tenantId);
	        salesAccessChannel.setCode(commonAddResource.getCode());
	        salesAccessChannel.setName(commonAddResource.getName());
	        salesAccessChannel.setDescription(commonAddResource.getDescription());
	        salesAccessChannel.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
	        salesAccessChannel.setCreatedDate(validationService.getCreateOrModifyDate());
	        salesAccessChannel.setSyncTs(validationService.getCreateOrModifyDate());
	        salesAccessChannel.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
	        
	        return salesAccessChannelRepository.save(salesAccessChannel);
	}

	@Override
	public SalesAccessChannel updateSalesAccessChannel(String tenantId, Long id,
			CommonUpdateResource commonUpdateResource) {
		Optional<SalesAccessChannel> isPresentSalesAccessChannel= salesAccessChannelRepository.findById(id);
		
		if(isPresentSalesAccessChannel.isPresent()) {
			
			if(!isPresentSalesAccessChannel.get().getCode().equalsIgnoreCase(commonUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE, EntityPoint.SALES_ACCESS_CHANNEL);
			
			if(!isPresentSalesAccessChannel.get().getVersion().equals(Long.parseLong(commonUpdateResource.getVersion())))
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
			
			SalesAccessChannel salesAccessChannel = isPresentSalesAccessChannel.get();
			salesAccessChannelHistoryService.saveHistory(tenantId, salesAccessChannel, LogginAuthentcation.getInstance().getUserName());
			
			
			salesAccessChannel.setTenantId(tenantId);
			salesAccessChannel.setName(commonUpdateResource.getName());
			salesAccessChannel.setDescription(commonUpdateResource.getDescription());
			salesAccessChannel.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus()));
			salesAccessChannel.setModifiedDate(validationService.getCreateOrModifyDate());
			salesAccessChannel.setSyncTs(validationService.getCreateOrModifyDate());
			salesAccessChannel.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
	        
	        return salesAccessChannelRepository.saveAndFlush(salesAccessChannel);
        
		}
		 else 
			throw new ValidateRecordException(environment.getProperty("record-not-found"),"message");		
	}

}
