package com.fusionx.lending.transaction.service;

import java.util.List;
import java.util.Optional;

import com.fusionx.lending.transaction.domain.WaiveOffApprovalGroup;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalGroupAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalGroupUpdateResource;

public interface WaiveOffApprovalGroupService {

	List<WaiveOffApprovalGroup> findAll();
	
	Optional<WaiveOffApprovalGroup> findById(Long waiveOffTypeId);
	
	List<WaiveOffApprovalGroup> findByStatus(String status);
	
	List<WaiveOffApprovalGroup> findByName(String name);
	
	List<WaiveOffApprovalGroup> findByCode(String code);
	
	WaiveOffApprovalGroup addWaiveOffType(String tenantId, WaiveOffApprovalGroupAddResource addResource);
	
	WaiveOffApprovalGroup updateWaiveOffType(String tenantId, Long id, WaiveOffApprovalGroupUpdateResource updateResource);
	
}
