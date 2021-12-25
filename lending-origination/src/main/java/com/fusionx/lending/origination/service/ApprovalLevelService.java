package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ApprovalLevel;
import com.fusionx.lending.origination.resource.ApprovalLevelAddResource;
import com.fusionx.lending.origination.resource.ApprovalLevelUpdateResource;

	

@Service
public interface ApprovalLevelService {

	ApprovalLevel save(String tenantId, ApprovalLevelAddResource approvalLevelAddResource);


	Optional<ApprovalLevel> findById(Long id);

	
	List<ApprovalLevel> findAll();

	
	Optional<ApprovalLevel> findByCode(String code);


	List<ApprovalLevel> findByName(String name);


	List<ApprovalLevel> findByStatus(String status);


	ApprovalLevel update(String tenantId, @Valid ApprovalLevelUpdateResource approvalLevelUpdateResource);
}
