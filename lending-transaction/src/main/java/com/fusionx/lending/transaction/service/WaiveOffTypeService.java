package com.fusionx.lending.transaction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.transaction.domain.WaiveOffType;
import com.fusionx.lending.transaction.resource.WaiveOffTypeAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffTypeUpdateResource;

@Service
public interface WaiveOffTypeService {

	List<WaiveOffType> findAll();
	
	Optional<WaiveOffType> findById(Long waiveOffTypeId);
	
	List<WaiveOffType> findByStatus(String status);
	
	List<WaiveOffType> findByName(String name);
	
	List<WaiveOffType> findByCode(String code);
	
	WaiveOffType addWaiveOffType(String tenantId, WaiveOffTypeAddResource waiveOffTypeAddResource);
	
	WaiveOffType updateWaiveOffType(String tenantId, Long id, WaiveOffTypeUpdateResource waiveOffTypeUpdateResource);
	
}
