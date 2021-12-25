package com.fusionx.lending.transaction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.transaction.domain.AllocationTemplate;
import com.fusionx.lending.transaction.domain.AllocationTemplatePending;
import com.fusionx.lending.transaction.enums.WorkflowStatus;
import com.fusionx.lending.transaction.resource.AllocationTemplateAddResource;
import com.fusionx.lending.transaction.resource.AllocationTemplateUpdateResource;

@Service
public interface AllocationTemplateService {

	List<AllocationTemplate> findAll();
	
	Optional<AllocationTemplate> findById(Long allocationTemplateId);
	
	List<AllocationTemplate> findByStatus(String status);
	
	Optional<AllocationTemplate> findByCode(String code);

	AllocationTemplate addAllocationTemplate(String tenantId, AllocationTemplateAddResource allocationTemplateAddResource);
	
	AllocationTemplate updateAllocationTemplate(String tenantId, Long id, AllocationTemplateUpdateResource allocationTemplateUpdateResource);
	
	boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);
	
	Optional<AllocationTemplatePending> getByPendingId(Long pendingId);
	
	public List<AllocationTemplate> getByName(String name);
	
	public Page<AllocationTemplatePending> searchAllocationTemplate(String searchq, String status, String approveStatus,Pageable pageable);
}
