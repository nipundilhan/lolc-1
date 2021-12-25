package com.fusionx.lending.product.service.impl;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.FeatureBenefitEligibility;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItem;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibility;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibilityHistory;
import com.fusionx.lending.product.domain.FeatureBenefitTemplateItemEligibilityPending;
import com.fusionx.lending.product.domain.FeatureBenifitTemplate;
import com.fusionx.lending.product.domain.FeatureBenifitTemplatePending;
import com.fusionx.lending.product.domain.LendingWorkflow;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.WorkflowType;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.FeatureBenefitEligibilityRepository;
import com.fusionx.lending.product.repository.FeatureBenefitTemplateItemEligibilityHistoryRepository;
import com.fusionx.lending.product.repository.FeatureBenefitTemplateItemEligibilityPendingRepository;
import com.fusionx.lending.product.repository.FeatureBenefitTemplateItemEligibilityRepository;
import com.fusionx.lending.product.repository.FeatureBenefitTemplateItemRepository;
import com.fusionx.lending.product.repository.FeatureBenifitTemplatePendingRepository;
import com.fusionx.lending.product.repository.FeatureBenifitTemplateRepository;
import com.fusionx.lending.product.resources.FeatureBenefitEligibilityUsageResource;
import com.fusionx.lending.product.resources.FeatureBenefitTemplateItemEligibilityAddResource;
import com.fusionx.lending.product.resources.FeatureBenefitTemplateItemEligibilityUpdateResource;
import com.fusionx.lending.product.resources.WorkflowInitiationRequestResource;
import com.fusionx.lending.product.resources.WorkflowRulesResource;
import com.fusionx.lending.product.service.FeatureBenefitTemplateItemEligibilityService;
import com.fusionx.lending.product.service.FeatureBenefitTemplateItemService;
import com.fusionx.lending.product.service.LendingWorkflowService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class FeatureBenefitTemplateItemEligibilityServiceImpl   extends MessagePropertyBase   implements FeatureBenefitTemplateItemEligibilityService {

	
    /**
     * Common Properties
     */
    protected static final String DUPLICATE_FEATURE_BENIFIT_ELIGIBILITY = "featureBenifitEligibility-duplicate";
    protected static final String INACTIVE_FEATURE_BENIFIT_ELIGIBILITY  = "featureBenifitEligibility-inactive-status";
	
    /**
     * JSON Properties
     */
    protected static final String FEATURE_BENIFIT_TEMPLATE_ID = "featureBenifitTemplateId";
    protected static final String FEATURE_BENIFIT_TEMPLATE_PENDING_ID = "featureBenifitTemplatePendingId";
    protected static final String FEATURE_BENIFIT_TEMPLATE_ITEM_ELIGIBILITY_ID = "featureBenifitItemEligibilityId";
    protected static final String FEATURE_BENIFIT_ELIGIBILITY_ID = "featureBenifiEligibilityId";
    
	@Autowired
	private FeatureBenefitTemplateItemRepository featureBenefitTemplateItemRepository;
	@Autowired
	private FeatureBenefitTemplateItemEligibilityRepository featureBenefitTemplateItemEligibilityRepository;
	@Autowired
	private FeatureBenefitTemplateItemEligibilityHistoryRepository featureBenefitTemplateItemEligibilityHistoryRepository;
	@Autowired
	private FeatureBenifitTemplateRepository featureBenefitTemplateRepository;
	@Autowired
	private FeatureBenefitEligibilityRepository featureBenefitEligibilityRepository;
	@Autowired
	private FeatureBenifitTemplatePendingRepository featureBenefitTemplatePendingRepository;
	@Autowired
	private FeatureBenefitTemplateItemEligibilityPendingRepository featureBenefitTemplateItemEligibilityPendingRepository;


	@Autowired
	private FeatureBenefitTemplateItemService featureBenefitTemplateItemService;
	@Autowired
	private ValidationService validationService;
	@Autowired
	private LendingWorkflowService lendingWorkflowService;

	private static final String DEFAULT_APPROVAL_USER = "kie-server";

	private static final String DOMAIN_PATH = "com.fusionx.LendingWF";

	private static final String DOMAIN = "LendingWF";

	@Override
	public FeatureBenefitTemplateItemEligibility createFeatureBenefitTemplateItemEligibility(String tenantId,
			FeatureBenefitTemplateItemEligibilityAddResource featureBenefitTemplateItemEligibilityAddResource) {

		Optional<FeatureBenefitTemplateItem> featureBenefitTemplateItemOptional = featureBenefitTemplateItemRepository
				.findById(validationService.stringToLong(featureBenefitTemplateItemEligibilityAddResource.getFeatureBenefitTemplateItemId()));
		if (!featureBenefitTemplateItemOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),
					"featureBenifitTemplateItemId");
		}

		FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource = featureBenefitTemplateItemEligibilityAddResource
				.getFeatureBenefitEligibilityUsageResource();

		List<Long> addedFeatureBenifitEligibilityList = getFeatureBenefitTemplateItemEligibilityRelatedToFeatureBenifitTemplateItem(
				featureBenefitTemplateItemOptional.get());

		Optional<FeatureBenefitEligibility> featureBenefitEligibilityOptional = featureBenefitEligibilityRepository
				.findById(validationService.stringToLong(featureBenefitEligibilityUsageResource.getFeatureBenefitEligibilityId()));
		if (!featureBenefitEligibilityOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),FEATURE_BENIFIT_ELIGIBILITY_ID);
		}

		if (!(CommonStatus.ACTIVE.toString()).equals(featureBenefitEligibilityOptional.get().getStatus())) {
			throw new ValidateRecordException(environment.getProperty(INACTIVE_FEATURE_BENIFIT_ELIGIBILITY),MESSAGE);
		}

		if ((addedFeatureBenifitEligibilityList != null) && (!addedFeatureBenifitEligibilityList.isEmpty())
			&& (addedFeatureBenifitEligibilityList.contains(validationService.stringToLong(featureBenefitEligibilityUsageResource.getFeatureBenefitEligibilityId()))) ) {
				
			throw new ValidateRecordException(environment.getProperty(DUPLICATE_FEATURE_BENIFIT_ELIGIBILITY),MESSAGE);			
		}

		FeatureBenefitTemplateItemEligibility featureBenefitTemplateItemEligibility = null;

		featureBenefitTemplateItemEligibility = new FeatureBenefitTemplateItemEligibility();

		featureBenefitTemplateItemEligibility.setTenantId(tenantId);
		featureBenefitTemplateItemEligibility.setFeatureBenefitTemplateItem(featureBenefitTemplateItemOptional.get());
		featureBenefitTemplateItemEligibility.setFeatureBenefitEligibility(featureBenefitEligibilityOptional.get());
		featureBenefitTemplateItemEligibility.setDescription(null);
		featureBenefitTemplateItemEligibility.setStatus(CommonStatus.valueOf(featureBenefitEligibilityUsageResource.getStatus()));
		featureBenefitTemplateItemEligibility.setCreatedDate(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItemEligibility.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		featureBenefitTemplateItemEligibility.setModifiedDate(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItemEligibility.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		featureBenefitTemplateItemEligibility.setSyncTs(validationService.getCreateOrModifyDate());

		featureBenefitTemplateItemEligibility = featureBenefitTemplateItemEligibilityRepository
				.save(featureBenefitTemplateItemEligibility);
		
		this.saveHistory(null,featureBenefitTemplateItemEligibility);

		return featureBenefitTemplateItemEligibility;

	}

	@Override
	public FeatureBenefitTemplateItemEligibility directUpdateFeatureBenefitTemplateItemEligibility(Long id,
			FeatureBenefitEligibilityUsageResource featureBenefitEligibilityUsageResource) {

		Optional<FeatureBenefitTemplateItemEligibility> featureBenefitTemplateItemEligibilityOptional = featureBenefitTemplateItemEligibilityRepository
				.findById(id);
		if (!featureBenefitTemplateItemEligibilityOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "id");
		}

		FeatureBenefitTemplateItemEligibility featureBenefitTemplateItemEligibility = featureBenefitTemplateItemEligibilityOptional
				.get();

		if (featureBenefitTemplateItemEligibility.getFeatureBenefitEligibility()
				.getId().longValue() != validationService.stringToLong(featureBenefitEligibilityUsageResource.getFeatureBenefitEligibilityId()).longValue()) {

			List<Long> addedFeatureBenifitEligibilityList = getFeatureBenefitTemplateItemEligibilityRelatedToFeatureBenifitTemplateItem(
					featureBenefitTemplateItemEligibility.getFeatureBenefitTemplateItem());

			Optional<FeatureBenefitEligibility> featureBenefitEligibilityOptional = featureBenefitEligibilityRepository
					.findById(validationService.stringToLong(featureBenefitEligibilityUsageResource.getFeatureBenefitEligibilityId()));
			if (!featureBenefitEligibilityOptional.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),FEATURE_BENIFIT_ELIGIBILITY_ID);
			}

			if (!(CommonStatus.ACTIVE.toString()).equals(featureBenefitEligibilityOptional.get().getStatus())) {
				throw new ValidateRecordException(environment.getProperty(DUPLICATE_FEATURE_BENIFIT_ELIGIBILITY),MESSAGE);
			}

			if ((addedFeatureBenifitEligibilityList != null) && (!addedFeatureBenifitEligibilityList.isEmpty())
				&& (addedFeatureBenifitEligibilityList.contains(validationService.stringToLong(featureBenefitEligibilityUsageResource.getFeatureBenefitEligibilityId()))) ){
					
				throw new ValidateRecordException(environment.getProperty(DUPLICATE_FEATURE_BENIFIT_ELIGIBILITY),MESSAGE);
				
			}

			featureBenefitTemplateItemEligibility.setFeatureBenefitEligibility(featureBenefitEligibilityOptional.get());
		}

		featureBenefitTemplateItemEligibility
				.setStatus(CommonStatus.valueOf(featureBenefitEligibilityUsageResource.getStatus()));
		featureBenefitTemplateItemEligibility.setDescription(null);
		featureBenefitTemplateItemEligibility.setModifiedDate(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItemEligibility.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		featureBenefitTemplateItemEligibility.setSyncTs(validationService.getCreateOrModifyDate());

		featureBenefitTemplateItemEligibility = featureBenefitTemplateItemEligibilityRepository
				.save(featureBenefitTemplateItemEligibility);

		return featureBenefitTemplateItemEligibility;

	}

	@Override
	public List<FeatureBenefitTemplateItemEligibility> findByFeatureBenifitTemplateItem(
			Long featureBenifitTemplateItemId) {

		Optional<FeatureBenefitTemplateItem> featureBenefitTemplateItemOptional = featureBenefitTemplateItemRepository
				.findById(featureBenifitTemplateItemId);
		if (!featureBenefitTemplateItemOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),
					"featureBenifitTemplateItemId");
		}

		List<FeatureBenefitTemplateItemEligibility> findAllByFeatureBenefitTemplateItemList = featureBenefitTemplateItemEligibilityRepository
				.findAllByFeatureBenefitTemplateItem(featureBenefitTemplateItemOptional.get());
		return findAllByFeatureBenefitTemplateItemList;

	}

	public void saveHistory(Long featureBenifitTemplateItemEligibilityPendingId,
			FeatureBenefitTemplateItemEligibility featureBenefitTemplateItemEligibility) {

		FeatureBenefitTemplateItemEligibilityHistory featureBenefitTemplateItemEligibilityHistory = new FeatureBenefitTemplateItemEligibilityHistory();
		featureBenefitTemplateItemEligibilityHistory.setTenantId(featureBenefitTemplateItemEligibility.getTenantId());
		featureBenefitTemplateItemEligibilityHistory
				.setFeatureBenifitTemplateEligibilityId(featureBenefitTemplateItemEligibility.getId());
		featureBenefitTemplateItemEligibilityHistory
				.setFeatureBenifitTemplateItemEligibilityPendingId(featureBenifitTemplateItemEligibilityPendingId);
		featureBenefitTemplateItemEligibilityHistory.setFeatureBenifitEligibilityId(
				featureBenefitTemplateItemEligibility.getFeatureBenefitEligibility().getId());
		featureBenefitTemplateItemEligibilityHistory.setDescription(null);
		featureBenefitTemplateItemEligibilityHistory.setStatus(featureBenefitTemplateItemEligibility.getStatus());
		featureBenefitTemplateItemEligibilityHistory.setCreatedDate(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItemEligibilityHistory.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		featureBenefitTemplateItemEligibilityHistory.setSyncTs(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItemEligibilityHistoryRepository.save(featureBenefitTemplateItemEligibilityHistory);

	}

	@Override
	public FeatureBenifitTemplatePending updateupdateFeatureBenifitTemplate(String tenantId, Long id,
			FeatureBenefitTemplateItemEligibilityUpdateResource featureBenefitTemplateItemEligibilityUpdateResource) {

		Optional<FeatureBenifitTemplate> featureBenifitTemplateOptional = featureBenefitTemplateRepository.findById(id);
		if (!featureBenifitTemplateOptional.isPresent()) {
			// throw new exception
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),FEATURE_BENIFIT_TEMPLATE_ID);
		}


		boolean validRecord = validateFeatureBenifitTemplateItemEligibility(
				featureBenefitTemplateItemEligibilityUpdateResource);

		FeatureBenifitTemplatePending featureBenifitTemplatePending = null;
		if (validRecord) {
			featureBenifitTemplatePending = approveOrGenerateWorkFlow(tenantId,
					LogginAuthentcation.getInstance().getUserName(), featureBenifitTemplateOptional.get(),
					featureBenefitTemplateItemEligibilityUpdateResource);
		} else {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),FEATURE_BENIFIT_TEMPLATE_ID);
		}
		return featureBenifitTemplatePending;
	}

	public boolean validateFeatureBenifitTemplateItemEligibility(
			FeatureBenefitTemplateItemEligibilityUpdateResource featureBenefitTemplateItemEligibilityUpdateResource) {

		boolean validRecord = true;

		Optional<FeatureBenefitTemplateItem> featureBenefitTemplateItemOptional = featureBenefitTemplateItemRepository
				.findById(validationService.stringToLong(featureBenefitTemplateItemEligibilityUpdateResource.getFeatureBenefitTemplateItemId()));
		if (!featureBenefitTemplateItemOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),FEATURE_BENIFIT_TEMPLATE_ID);
		}

		List<Long> addedFeatureBenifitItemEligibilityIdList = getFeatureBenefitTemplateItemEligibilityRelatedToFeatureBenifitTemplateItem(
				featureBenefitTemplateItemOptional.get());

		List<Long> pendingFeatureBenifitItemEligibilityIdList = null;

		if (featureBenefitTemplateItemEligibilityUpdateResource.getFeatureBenefitTemplatePendingId() != null) {
			Optional<FeatureBenifitTemplatePending> featureBenefitTemplatePendingOptional = featureBenefitTemplatePendingRepository
					.findById(validationService.stringToLong(featureBenefitTemplateItemEligibilityUpdateResource.getFeatureBenefitTemplatePendingId()));
			if (!featureBenefitTemplatePendingOptional.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),FEATURE_BENIFIT_TEMPLATE_PENDING_ID);
			}
			pendingFeatureBenifitItemEligibilityIdList = getFeatureBenefitTemplateItemEligibilityPendingRelatedToFeatureBenifitTemplateItem(
					featureBenefitTemplatePendingOptional.get(), featureBenefitTemplateItemOptional.get());
		}

		if (featureBenefitTemplateItemEligibilityUpdateResource.getFeatureBenefitTemplateItemEligibilityId() == null) {
			if ((addedFeatureBenifitItemEligibilityIdList != null) && (!addedFeatureBenifitItemEligibilityIdList.isEmpty())
				&& (addedFeatureBenifitItemEligibilityIdList.contains(validationService.stringToLong(featureBenefitTemplateItemEligibilityUpdateResource
								.getFeatureBenefitEligibilityUsageResource().getFeatureBenefitEligibilityId()))) ){
					
				throw new ValidateRecordException(environment.getProperty(DUPLICATE_FEATURE_BENIFIT_ELIGIBILITY),MESSAGE);
				
			}

			if ((pendingFeatureBenifitItemEligibilityIdList != null)	&& (!pendingFeatureBenifitItemEligibilityIdList.isEmpty())
				&& (pendingFeatureBenifitItemEligibilityIdList.contains(validationService.stringToLong(featureBenefitTemplateItemEligibilityUpdateResource
								.getFeatureBenefitEligibilityUsageResource().getFeatureBenefitEligibilityId()))) ) {
					
				throw new ValidateRecordException(environment.getProperty(DUPLICATE_FEATURE_BENIFIT_ELIGIBILITY),MESSAGE);
				
			}
		}

		return validRecord;
	}

	public FeatureBenefitTemplateItemEligibilityPending saveFeatureBenefitTemplateItemEligibilityPending(
			String tenantId, FeatureBenifitTemplatePending featureBenifitTemplatePending,
			FeatureBenefitTemplateItemEligibilityUpdateResource featureBenefitTemplateItemEligibilityUpdateResource) {

		Optional<FeatureBenefitEligibility> featureBenefitEligibilityOptional = featureBenefitEligibilityRepository
				.findById(validationService.stringToLong(featureBenefitTemplateItemEligibilityUpdateResource
						.getFeatureBenefitEligibilityUsageResource().getFeatureBenefitEligibilityId()));
		if (!featureBenefitEligibilityOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),FEATURE_BENIFIT_ELIGIBILITY_ID);
		}

		Optional<FeatureBenefitTemplateItem> featureBenefitTemplateItemOptional = featureBenefitTemplateItemRepository
				.findById(validationService.stringToLong(featureBenefitTemplateItemEligibilityUpdateResource.getFeatureBenefitTemplateItemId()));

		FeatureBenefitTemplateItemEligibility featureBenefitTemplateItemEligibility = null;
		FeatureBenefitTemplateItemEligibilityPending featureBenefitTemplateItemEligibilityPending = null;

		if (featureBenefitTemplateItemEligibilityUpdateResource.getFeatureBenefitTemplateItemEligibilityId() != null) {
			Optional<FeatureBenefitTemplateItemEligibility> featureBenefitTemplateItemEligibilityOptional = featureBenefitTemplateItemEligibilityRepository
					.findById(validationService.stringToLong(featureBenefitTemplateItemEligibilityUpdateResource.getFeatureBenefitTemplateItemEligibilityId()));
			
			if (!featureBenefitTemplateItemEligibilityOptional.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),FEATURE_BENIFIT_TEMPLATE_ITEM_ELIGIBILITY_ID);
			}
			featureBenefitTemplateItemEligibility = featureBenefitTemplateItemEligibilityOptional.get();

			List<FeatureBenefitTemplateItemEligibilityPending> featureBenefitTemplateItemEligibilityPendingList = featureBenefitTemplateItemEligibilityPendingRepository
					.findAllByFeatureBenefitTemplatePendingAndFeatureBenefitTemplateItemEligibility(
							featureBenifitTemplatePending, featureBenefitTemplateItemEligibility);
			if (!featureBenefitTemplateItemEligibilityPendingList.isEmpty()) {
				featureBenefitTemplateItemEligibilityPending = featureBenefitTemplateItemEligibilityPendingList.get(0);
			}

		}
		
		
		if(!featureBenefitTemplateItemOptional.isPresent()) {
			throw new ValidateRecordException("feature benifit template item not found", MESSAGE);
		}

		if (featureBenefitTemplateItemEligibilityPending == null) {
			featureBenefitTemplateItemEligibilityPending = new FeatureBenefitTemplateItemEligibilityPending();

			featureBenefitTemplateItemEligibilityPending.setTenantId(tenantId);
			featureBenefitTemplateItemEligibilityPending
					.setFeatureBenefitTemplateItem(featureBenefitTemplateItemOptional.get());
			featureBenefitTemplateItemEligibilityPending
					.setFeatureBenefitTemplatePending(featureBenifitTemplatePending);
			featureBenefitTemplateItemEligibilityPending.setCreatedDate(validationService.getCreateOrModifyDate());
			featureBenefitTemplateItemEligibilityPending
					.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

			featureBenefitTemplateItemEligibilityPending
					.setFeatureBenefitEligibility(featureBenefitEligibilityOptional.get());
		}

		featureBenefitTemplateItemEligibilityPending.setFeatureBenefitTemplateItemPending(null);
		featureBenefitTemplateItemEligibilityPending
				.setFeatureBenefitTemplateItemEligibility(featureBenefitTemplateItemEligibility);
		featureBenefitTemplateItemEligibilityPending.setDescription(null);
		featureBenefitTemplateItemEligibilityPending
				.setStatus(CommonStatus.valueOf(featureBenefitTemplateItemEligibilityUpdateResource
						.getFeatureBenefitEligibilityUsageResource().getStatus()));
		featureBenefitTemplateItemEligibilityPending.setSyncTs(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItemEligibilityPending = featureBenefitTemplateItemEligibilityPendingRepository
				.save(featureBenefitTemplateItemEligibilityPending);
		return featureBenefitTemplateItemEligibilityPending;
	}

	@Override
	public void updateFeatureBenifitTemplateItemEligibility(FeatureBenefitTemplateItemEligibilityPending fbtie ,  String user) {

		FeatureBenefitTemplateItemEligibilityPending featureBenefitTemplateItemEligibilityPending = fbtie;

		FeatureBenefitTemplateItemEligibility featureBenefitTemplateItemEligibility = null;
		if (fbtie.getFeatureBenefitTemplateItemEligibility() != null) {

			featureBenefitTemplateItemEligibility = featureBenefitTemplateItemEligibilityPending
					.getFeatureBenefitTemplateItemEligibility();

		} else {

			featureBenefitTemplateItemEligibility = new FeatureBenefitTemplateItemEligibility();

			featureBenefitTemplateItemEligibility
					.setTenantId(featureBenefitTemplateItemEligibilityPending.getTenantId());
			featureBenefitTemplateItemEligibility.setFeatureBenefitTemplateItem(
					featureBenefitTemplateItemEligibilityPending.getFeatureBenefitTemplateItem());
			featureBenefitTemplateItemEligibility.setCreatedDate(validationService.getCreateOrModifyDate());
			featureBenefitTemplateItemEligibility.setCreatedUser(featureBenefitTemplateItemEligibilityPending.getCreatedUser());
			featureBenefitTemplateItemEligibility.setFeatureBenefitEligibility(
					featureBenefitTemplateItemEligibilityPending.getFeatureBenefitEligibility());
		}

		featureBenefitTemplateItemEligibility.setStatus(featureBenefitTemplateItemEligibilityPending.getStatus());
		featureBenefitTemplateItemEligibility.setModifiedDate(validationService.getCreateOrModifyDate());
		featureBenefitTemplateItemEligibility.setModifiedUser(featureBenefitTemplateItemEligibilityPending.getCreatedUser());
		featureBenefitTemplateItemEligibility.setSyncTs(validationService.getCreateOrModifyDate());

		featureBenefitTemplateItemEligibility =featureBenefitTemplateItemEligibilityRepository.save(featureBenefitTemplateItemEligibility);
		
		this.saveHistory(null,featureBenefitTemplateItemEligibility);

	}

	private FeatureBenifitTemplatePending approveOrGenerateWorkFlow(String tenantId, String user,
			FeatureBenifitTemplate featureBenifitTemplate,
			FeatureBenefitTemplateItemEligibilityUpdateResource featureBenefitTemplateItemEligibilityUpdateResource) {

		FeatureBenifitTemplatePending featureBenifitTemplatePending = null;

		if (featureBenefitTemplateItemEligibilityUpdateResource.getFeatureBenefitTemplatePendingId() != null) {

			Optional<FeatureBenifitTemplatePending> featureBenefitTemplatePendingOptional = featureBenefitTemplatePendingRepository
					.findById(validationService.stringToLong(featureBenefitTemplateItemEligibilityUpdateResource.getFeatureBenefitTemplatePendingId()));

			if (!featureBenefitTemplatePendingOptional.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE),FEATURE_BENIFIT_TEMPLATE_PENDING_ID);
			}

			featureBenifitTemplatePending = featureBenefitTemplatePendingOptional.get();
			saveFeatureBenefitTemplateItemEligibilityPending(tenantId, featureBenifitTemplatePending,
					featureBenefitTemplateItemEligibilityUpdateResource);

		} else {

			WorkflowRulesResource workflowRulesResource = null;
			Long processId = null;
			WorkflowType workflowType = WorkflowType.ELIGI_TEMP_APPROVAL;
			LendingWorkflow lendingWorkflow = null;

			WorkflowInitiationRequestResource workflowInitiationRequestResource = new WorkflowInitiationRequestResource();
			workflowInitiationRequestResource.setApprovalUser(DEFAULT_APPROVAL_USER);
			workflowInitiationRequestResource.setApprovWorkflowType(workflowType);

			workflowRulesResource = validationService.invokedLendingProductRule(workflowType, DOMAIN_PATH, DOMAIN,
					tenantId);

//			featureBenifitTemplatePending = featureBenefitTemplateItemService.saveFeatureBenifitTemplatePending(featureBenifitTemplate , lendingWorkflow);
//			FeatureBenefitTemplateItemEligibilityPending saveFeatureBenefitTemplateItemEligibilityPending = saveFeatureBenefitTemplateItemEligibilityPending(tenantId ,featureBenifitTemplatePending ,featureBenefitTemplateItemEligibilityUpdateResource);				
//			updateFeatureBenifitTemplateItemEligibility(saveFeatureBenefitTemplateItemEligibilityPending);

			if (workflowRulesResource.getApprovalRequired().equalsIgnoreCase(CommonStatus.YES.toString())) {
				processId = validationService.initiateLendingProductWorkFlow(workflowInitiationRequestResource,
						tenantId);
				if (processId != null) {
					lendingWorkflow = lendingWorkflowService.save(tenantId, processId, workflowType, user);
					featureBenifitTemplatePending = featureBenefitTemplateItemService.saveFeatureBenifitTemplatePending(
							featureBenifitTemplate, lendingWorkflow, CommonApproveStatus.PENDING);
					saveFeatureBenefitTemplateItemEligibilityPending(tenantId, featureBenifitTemplatePending,featureBenefitTemplateItemEligibilityUpdateResource);
				}
			} else {

				featureBenifitTemplatePending = featureBenefitTemplateItemService.saveFeatureBenifitTemplatePending(
						featureBenifitTemplate, lendingWorkflow, CommonApproveStatus.APPROVED);
				FeatureBenefitTemplateItemEligibilityPending saveFeatureBenefitTemplateItemEligibilityPending = saveFeatureBenefitTemplateItemEligibilityPending(
						tenantId, featureBenifitTemplatePending, featureBenefitTemplateItemEligibilityUpdateResource);
				updateFeatureBenifitTemplateItemEligibility(saveFeatureBenefitTemplateItemEligibilityPending , user);

			}

		}

		return featureBenifitTemplatePending;

	}

	public List<Long> getFeatureBenefitTemplateItemEligibilityRelatedToFeatureBenifitTemplateItem(
			FeatureBenefitTemplateItem featureBenifitTemplateItem) {

		List<FeatureBenefitTemplateItemEligibility> findAllByFeatureBenefitTemplateItem = featureBenefitTemplateItemEligibilityRepository
				.findAllByFeatureBenefitTemplateItem(featureBenifitTemplateItem);
		List<FeatureBenefitEligibility> addedFeatureBenifitEligibilityList = findAllByFeatureBenefitTemplateItem
				.stream().map(FeatureBenefitTemplateItemEligibility::getFeatureBenefitEligibility)
				.collect(Collectors.toList());
		List<Long> addedFeatureBenifitEligibilityIdList = addedFeatureBenifitEligibilityList.stream()
				.map(FeatureBenefitEligibility::getId).collect(Collectors.toList());
		return addedFeatureBenifitEligibilityIdList;

	}

	public List<Long> getFeatureBenefitTemplateItemEligibilityPendingRelatedToFeatureBenifitTemplateItem(
			FeatureBenifitTemplatePending featureBenefitTemplatePending,
			FeatureBenefitTemplateItem featureBenifitTemplateItem) {

		List<FeatureBenefitTemplateItemEligibilityPending> featureBenefitTemplateItemEligibilityPendingList = featureBenefitTemplateItemEligibilityPendingRepository
				.findAllByFeatureBenefitTemplatePendingAndFeatureBenefitTemplateItem(featureBenefitTemplatePending,
						featureBenifitTemplateItem);
		List<FeatureBenefitEligibility> addedFeatureBenifitEligibilityList = featureBenefitTemplateItemEligibilityPendingList
				.stream().map(FeatureBenefitTemplateItemEligibilityPending::getFeatureBenefitEligibility)
				.collect(Collectors.toList());
		List<Long> addedFeatureBenifitEligibilityIdList = addedFeatureBenifitEligibilityList.stream()
				.map(FeatureBenefitEligibility::getId).collect(Collectors.toList());
		return addedFeatureBenifitEligibilityIdList;

	}

}
