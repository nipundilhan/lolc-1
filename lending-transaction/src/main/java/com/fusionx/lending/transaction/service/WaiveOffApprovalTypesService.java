package com.fusionx.lending.transaction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.transaction.domain.WaiveOffApprovalTypes;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalTypesAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalTypesUpdateResource;

@Service
public interface WaiveOffApprovalTypesService {

	List<WaiveOffApprovalTypes> findAll();
	
	Optional<WaiveOffApprovalTypes> findById(Long waiveOffTransactionTypeId);
	
	List<WaiveOffApprovalTypes> findByStatus(String status);
	
	List<WaiveOffApprovalTypes> findByWaiveOffTypeId(Long waiveOffTypeId);
	
	List<WaiveOffApprovalTypes> findByWaiveOffApprovalGroupId(Long waiveOffApprovalGroupId);
	
	WaiveOffApprovalTypes addWaiveOffApprovalTypes(String tenantId, WaiveOffApprovalTypesAddResource waiveOffApprovalTypesAddResource);
	
	WaiveOffApprovalTypes updateWaiveOffApprovalTypes(String tenantId, Long id, WaiveOffApprovalTypesUpdateResource waiveOffApprovalTypesUpdateResource);
	
}
