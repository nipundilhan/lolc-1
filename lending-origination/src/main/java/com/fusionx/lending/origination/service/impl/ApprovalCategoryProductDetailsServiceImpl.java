package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * Risk Authorities
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-338 		 FXL-682 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ApprovalCategoryProductDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ApprovalCategoryProductDetailsRepository;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;
import com.fusionx.lending.origination.service.ApprovalCategoryProductDetailsService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class ApprovalCategoryProductDetailsServiceImpl extends MessagePropertyBase
		implements ApprovalCategoryProductDetailsService {

	@Autowired
	private ApprovalCategoryProductDetailsRepository approvalCategoryProductDetailsRepository;

	@Autowired
	private ValidateService validationService;

	@Override
	public List<ApprovalCategoryProductDetails> getAll() {
		return approvalCategoryProductDetailsRepository.findAll();
	}

	@Override
	public Optional<ApprovalCategoryProductDetails> getById(Long id) {
		Optional<ApprovalCategoryProductDetails> isPresentApprovalCategoryProductDetails = approvalCategoryProductDetailsRepository
				.findById(id);
		if (isPresentApprovalCategoryProductDetails.isPresent()) {
			return Optional.ofNullable(isPresentApprovalCategoryProductDetails.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<ApprovalCategoryProductDetails> getByCode(String code) {
		Optional<ApprovalCategoryProductDetails> isPresentApprovalCategoryProductDetails = approvalCategoryProductDetailsRepository
				.findByCode(code);
		if (isPresentApprovalCategoryProductDetails.isPresent()) {
			return Optional.ofNullable(isPresentApprovalCategoryProductDetails.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ApprovalCategoryProductDetails> getByName(String name) {
		return approvalCategoryProductDetailsRepository.findByNameContaining(name);
	}

	@Override
	public List<ApprovalCategoryProductDetails> getByStatus(String status) {
		return approvalCategoryProductDetailsRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public ApprovalCategoryProductDetails addApprovalCategoryProductDetails(String tenantId,
			CommonAddResource commonAddResource) {

		Optional<ApprovalCategoryProductDetails> isPresentApprovalCategoryProductDetails = approvalCategoryProductDetailsRepository
				.findByCode(commonAddResource.getCode());

		if (isPresentApprovalCategoryProductDetails.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE);

		ApprovalCategoryProductDetails approvalCategoryProductDetails = new ApprovalCategoryProductDetails();
		approvalCategoryProductDetails.setTenantId(tenantId);
		approvalCategoryProductDetails.setCode(commonAddResource.getCode());
		approvalCategoryProductDetails.setName(commonAddResource.getName());
		approvalCategoryProductDetails.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
		approvalCategoryProductDetails.setCreatedDate(validationService.getCreateOrModifyDate());
		approvalCategoryProductDetails.setSyncTs(validationService.getCreateOrModifyDate());
		approvalCategoryProductDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return approvalCategoryProductDetailsRepository.save(approvalCategoryProductDetails);

	}

	@Override
	public ApprovalCategoryProductDetails updateApprovalCategoryProductDetails(String tenantId, Long id,
			CommonUpdateResource commonUpdateResource) {

		Optional<ApprovalCategoryProductDetails> isPresentApprovalCategoryProductDetails = approvalCategoryProductDetailsRepository
				.findById(id);

		if (!isPresentApprovalCategoryProductDetails.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else {
			if (!isPresentApprovalCategoryProductDetails.get().getVersion()
					.equals(Long.parseLong(commonUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);

			if (!isPresentApprovalCategoryProductDetails.get().getCode()
					.equalsIgnoreCase(commonUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty(RECORD_UPDATED), ServiceEntity.CODE);

			ApprovalCategoryProductDetails approvalCategoryProductDetails = isPresentApprovalCategoryProductDetails
					.get();
			approvalCategoryProductDetails.setTenantId(tenantId);
			approvalCategoryProductDetails.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus()));
			approvalCategoryProductDetails.setModifiedDate(validationService.getCreateOrModifyDate());
			approvalCategoryProductDetails.setSyncTs(validationService.getCreateOrModifyDate());
			approvalCategoryProductDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

			return approvalCategoryProductDetailsRepository.saveAndFlush(approvalCategoryProductDetails);
		}
	}

}
