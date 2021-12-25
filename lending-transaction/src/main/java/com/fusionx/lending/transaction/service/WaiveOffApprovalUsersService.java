package com.fusionx.lending.transaction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.transaction.domain.WaiveOffApprovalUsers;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalUsersAddResource;
import com.fusionx.lending.transaction.resource.WaiveOffApprovalUsersUpdateResource;

@Service
public interface WaiveOffApprovalUsersService {

	List<WaiveOffApprovalUsers> findAll();
	
	Optional<WaiveOffApprovalUsers> findById(Long waiveOffTransactionTypeId);
	
	List<WaiveOffApprovalUsers> findByStatus(String status);
	
	List<WaiveOffApprovalUsers> findByUserId(String waiveOffTypeId);
	
	List<WaiveOffApprovalUsers> findByWaiveOffApprovalGroupId(Long waiveOffApprovalGroupId);
	
	WaiveOffApprovalUsers addWaiveOffApprovalUsers(String tenantId, WaiveOffApprovalUsersAddResource waiveOffApprovalUsersAddResource);
	
	WaiveOffApprovalUsers updateWaiveOffApprovalUsers(String tenantId, Long id, WaiveOffApprovalUsersUpdateResource waiveOffApprovalUsersUpdateResource);
	
}
