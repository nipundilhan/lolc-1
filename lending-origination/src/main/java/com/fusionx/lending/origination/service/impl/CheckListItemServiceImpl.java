package com.fusionx.lending.origination.service.impl;

import java.util.List;
import java.util.Optional;
/**
 * Check List Item
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-75  		 FXL-550 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.CheckListItem;
import com.fusionx.lending.origination.domain.CommonList;
import com.fusionx.lending.origination.enums.CommonListReferenceCode;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.exception.InvalidServiceIdException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.CheckListItemRepository;
import com.fusionx.lending.origination.repository.CommonListRepository;
import com.fusionx.lending.origination.resource.CheckListItemAddResource;
import com.fusionx.lending.origination.resource.CheckListItemUpdateResource;
import com.fusionx.lending.origination.service.CheckListItemHistoryService;
import com.fusionx.lending.origination.service.CheckListItemService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor = Exception.class)
public class CheckListItemServiceImpl extends MessagePropertyBase implements CheckListItemService {

	@Autowired
	private CommonListRepository commonListRepository;

	@Autowired
	private CheckListItemRepository checkListItemRepository;

	@Autowired
	private ValidateService validationService;

	@Autowired
	private CheckListItemHistoryService checkListItemHistoryService;

	@Override
	public List<CheckListItem> getAll() {
		return checkListItemRepository.findAll();
	}

	@Override
	public Optional<CheckListItem> getById(Long id) {
		Optional<CheckListItem> isPresentCheckListItem = checkListItemRepository.findById(id);
		if (isPresentCheckListItem.isPresent()) {
			return Optional.ofNullable(isPresentCheckListItem.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<CheckListItem> getByCode(String code) {
		Optional<CheckListItem> isPresentCheckListItem = checkListItemRepository.findByCode(code);
		if (isPresentCheckListItem.isPresent()) {
			return Optional.ofNullable(isPresentCheckListItem.get());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<CheckListItem> getByName(String name) {
		return checkListItemRepository.findByNameContaining(name);
	}

	@Override
	public List<CheckListItem> getByStatus(String status) {
		return checkListItemRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public CheckListItem addCheckListItem(String tenantId, CheckListItemAddResource checkListItemAddResource) {

		Optional<CheckListItem> isPresentCheckListItem = checkListItemRepository.findByCodeAndStatus(
				checkListItemAddResource.getCode(), CommonStatus.valueOf(checkListItemAddResource.getStatus()));

		CommonList category = this.validateCategory(
				validationService.stringToLong(checkListItemAddResource.getCategoryId()),
				checkListItemAddResource.getCategoryName());

		if (isPresentCheckListItem.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE);

		CheckListItem checkListItem = new CheckListItem();
		checkListItem.setTenantId(tenantId);
		checkListItem.setCode(checkListItemAddResource.getCode());
		checkListItem.setName(checkListItemAddResource.getName());
		checkListItem.setCategory(category);
		checkListItem.setDescription(checkListItemAddResource.getDescription());
		checkListItem.setStatus(CommonStatus.valueOf(checkListItemAddResource.getStatus()));
		checkListItem.setCreatedDate(validationService.getCreateOrModifyDate());
		checkListItem.setSyncTs(validationService.getCreateOrModifyDate());
		checkListItem.setCreatedUser(LogginAuthentcation.getInstance().getUserName());

		return checkListItemRepository.save(checkListItem);

	}

	@Override
	public CheckListItem updateCheckListItem(String tenantId, Long id,
			CheckListItemUpdateResource checkListItemUpdateResource) {

		Optional<CheckListItem> isPresentCheckListItem = checkListItemRepository.findById(id);
		if (!isPresentCheckListItem.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		} else {
			checkListItemHistoryService.saveHistory(tenantId, isPresentCheckListItem.get(),
					LogginAuthentcation.getInstance().getUserName());

			if (!isPresentCheckListItem.get().getVersion()
					.equals(Long.parseLong(checkListItemUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE),
						ServiceEntity.VERSION);

			if (!isPresentCheckListItem.get().getCode().equals(checkListItemUpdateResource.getCode()))
				throw new InvalidServiceIdException(environment.getProperty("common.code-update"), ServiceEntity.CODE);

			CommonList category = this.validateCategory(
					validationService.stringToLong(checkListItemUpdateResource.getCategoryId()),
					checkListItemUpdateResource.getCategoryName());

			CheckListItem checkListItem = isPresentCheckListItem.get();
			checkListItem.setTenantId(tenantId);
			checkListItem.setName(checkListItemUpdateResource.getName());
			checkListItem.setCategory(category);
			checkListItem.setDescription(checkListItemUpdateResource.getDescription());
			checkListItem.setStatus(CommonStatus.valueOf(checkListItemUpdateResource.getStatus()));
			checkListItem.setModifiedDate(validationService.getCreateOrModifyDate());
			checkListItem.setSyncTs(validationService.getCreateOrModifyDate());
			checkListItem.setModifiedUser(LogginAuthentcation.getInstance().getUserName());

			return checkListItemRepository.saveAndFlush(checkListItem);
		}
	}

	public CommonList validateCategory(Long id, String name) {

		Optional<CommonList> commonListItem = commonListRepository.findById(id);
		if (!commonListItem.isPresent())
			throw new ValidateRecordException(environment.getProperty(NOT_FOUND), "checkListCategoryId");

		else if (!CommonStatus.ACTIVE.toString().equalsIgnoreCase(commonListItem.get().getStatus()))
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "checkListCategoryId");

		else if (!CommonListReferenceCode.CHECKLISTCATEGORY.toString()
				.equalsIgnoreCase(commonListItem.get().getReferenceCode())
				|| !commonListItem.get().getName().equalsIgnoreCase(name))
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "checkListCategoryId");

		return commonListItem.get();
	}

}
