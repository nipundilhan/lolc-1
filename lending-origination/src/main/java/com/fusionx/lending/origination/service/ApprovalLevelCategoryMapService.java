package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ApprovalCategoryProductMapping;
import com.fusionx.lending.origination.domain.ApprovalLevelCategoryMapping;
import com.fusionx.lending.origination.resource.ApprovalLevelCategoryMapResource;
import com.fusionx.lending.origination.resource.UpdateApprovalLevelCategoryMapResource;

@Service
public interface ApprovalLevelCategoryMapService {

	List<ApprovalLevelCategoryMapping> findAll();

	Optional<ApprovalLevelCategoryMapping> findById(Long id);

	List<ApprovalLevelCategoryMapping> findByStatus(String status);

	Long save(String tenantId, String userName,
			@Valid ApprovalLevelCategoryMapResource approvalLevelCategoryMapResource);

	boolean existsById(Long id);

	Long updateAndValidateApprovalCategory(String tenantId, String userName, Long id,
			@Valid UpdateApprovalLevelCategoryMapResource approvalCategoryUpdateResource);

	List<ApprovalLevelCategoryMapping> findBycategoryId(Long categoryId);

}
