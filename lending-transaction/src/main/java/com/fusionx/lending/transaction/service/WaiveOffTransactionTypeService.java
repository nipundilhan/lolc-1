package com.fusionx.lending.transaction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.transaction.domain.WaiveOffTransactionType;
import com.fusionx.lending.transaction.resource.WaiveOffTransactionTypeAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffTransactionTypeUpdateResource;

@Service
public interface WaiveOffTransactionTypeService {

	List<WaiveOffTransactionType> findAll();
	
	Optional<WaiveOffTransactionType> findById(Long waiveOffTransactionTypeId);
	
	List<WaiveOffTransactionType> findByStatus(String status);
	
	List<WaiveOffTransactionType> findByWaiveOffTypeId(Long waiveOffTypeId);
	
	WaiveOffTransactionType addWaiveOffTransactionType(String tenantId, WaiveOffTransactionTypeAddResource waiveOffTransactionTypeAddResource);
	
	WaiveOffTransactionType updateWaiveOffTransactionType(String tenantId, Long id, WaiveOffTransactionTypeUpdateResource waiveOffTransactionTypeUpdateResource);
	
}
