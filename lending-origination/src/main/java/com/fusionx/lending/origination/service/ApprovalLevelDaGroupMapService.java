package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ApprovalLevelDaGroupMapping;
import com.fusionx.lending.origination.repository.ApprovalLevelDaGroupMapRepository;
import com.fusionx.lending.origination.resource.ApprovalLevelCategoryMapResource;
import com.fusionx.lending.origination.resource.ApprovalLevelDaGroupMapResource;
import com.fusionx.lending.origination.resource.UpdateApprovalLevelDAgGroupMapResource;

@Service
public interface ApprovalLevelDaGroupMapService {

	List<ApprovalLevelDaGroupMapping> findAll();

	Optional<ApprovalLevelDaGroupMapping> findById(Long id);

	List<ApprovalLevelDaGroupMapping> findByStatus(String status);



	Long save(String tenantId, String userName,
			@Valid ApprovalLevelDaGroupMapResource approvalLevelCategoryMapResource);

	boolean existsById(Long id);

	Long updateAndValidateApprovalCategory(String tenantId, String userName, Long id,
			@Valid UpdateApprovalLevelDAgGroupMapResource approvalCategoryUpdateResource);

	List<ApprovalLevelDaGroupMapping> findByLevelId(Long levelId);

}
