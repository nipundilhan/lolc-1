package com.fusionx.lending.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.SystemGeneratedDocumentType;
import com.fusionx.lending.product.domain.SystemGeneratedDocumentTypeDetails;
import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.enums.CommonListReferenceCode;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.CommonListItemRepository;
import com.fusionx.lending.product.repository.SystemGeneratedDocumentTypeDetailsRepository;
import com.fusionx.lending.product.repository.SystemGeneratedDocumentTypeRepository;
import com.fusionx.lending.product.repository.ProductRepository;
import com.fusionx.lending.product.repository.SubProductRepository;
import com.fusionx.lending.product.resources.SystemGeneratedDocumentTypeAddResource;
import com.fusionx.lending.product.resources.SystemGeneratedDocumentTypeDetailsAddResource;
import com.fusionx.lending.product.resources.SystemGeneratedDocumentTypeUpdateResource;
import com.fusionx.lending.product.resources.DocumentResource;
import com.fusionx.lending.product.service.SystemGeneratedDocumentTypeService;
import com.fusionx.lending.product.service.ValidationService;
/**
 * System Generated Document Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-11-2021   FXL-358       FXL-1736   Dilki        Created
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class SystemGeneratedDocumentTypeServiceImpl extends MessagePropertyBase
		implements SystemGeneratedDocumentTypeService {

	@Autowired
	private SystemGeneratedDocumentTypeRepository systemGeneratedDocumentTypeRepository;

	@Autowired
	private SystemGeneratedDocumentTypeDetailsRepository systemGeneratedDocumentTypeDetailsRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SubProductRepository subProductRepository;

	@Autowired
	private CommonListItemRepository commonListItemRepository;

	@Autowired
	private ValidationService validateService;

	@Override
	public List<SystemGeneratedDocumentType> getAll() {

		List<SystemGeneratedDocumentType> systemGeneratedDocumentType = systemGeneratedDocumentTypeRepository.findAll();
		List<SystemGeneratedDocumentType> systemGeneratedDocumentTypeAll = new ArrayList<>();

		for (SystemGeneratedDocumentType item : systemGeneratedDocumentType) {
			List<SystemGeneratedDocumentTypeDetails> systemGeneratedDocumentTypeDetails = systemGeneratedDocumentTypeDetailsRepository
					.findBySystemGeneratedDocumentTypeId(item.getId());
			item.setDocumentDetails(systemGeneratedDocumentTypeDetails);
			systemGeneratedDocumentTypeAll.add(item);
		}
		return systemGeneratedDocumentTypeAll;
	}

	@Override
	public Optional<SystemGeneratedDocumentType> getById(Long id) {
		Optional<SystemGeneratedDocumentType> isPresentSystemGeneratedDocumentType = systemGeneratedDocumentTypeRepository
				.findById(id);
		if (isPresentSystemGeneratedDocumentType.isPresent()) {
			SystemGeneratedDocumentType systemGeneratedDocumentType = isPresentSystemGeneratedDocumentType.get();
			List<SystemGeneratedDocumentTypeDetails> systemGeneratedDocumentTypeDetails = systemGeneratedDocumentTypeDetailsRepository
					.findBySystemGeneratedDocumentTypeId(systemGeneratedDocumentType.getId());
			systemGeneratedDocumentType.setDocumentDetails(systemGeneratedDocumentTypeDetails);
			return isPresentSystemGeneratedDocumentType;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<SystemGeneratedDocumentType> getByStatus(String status) {
		List<SystemGeneratedDocumentType> systemGeneratedDocumentTypeAll = systemGeneratedDocumentTypeRepository
				.findByStatus(CommonStatus.valueOf(status));
		List<SystemGeneratedDocumentType> expenceTypeCultivationCategoryList = new ArrayList<>();

		for (SystemGeneratedDocumentType item : systemGeneratedDocumentTypeAll) {
			List<SystemGeneratedDocumentTypeDetails> systemGeneratedDocumentTypeDetails = systemGeneratedDocumentTypeDetailsRepository
					.findBySystemGeneratedDocumentTypeId(item.getId());
			item.setDocumentDetails(systemGeneratedDocumentTypeDetails);
			expenceTypeCultivationCategoryList.add(item);
		}
		return systemGeneratedDocumentTypeAll;
	}

	@Override
	public Optional<SystemGeneratedDocumentType> getByCode(String code) {
		Optional<SystemGeneratedDocumentType> isSystemGeneratedDocumentType = systemGeneratedDocumentTypeRepository
				.findByCode(code);
		if (isSystemGeneratedDocumentType.isPresent()) {
			SystemGeneratedDocumentType systemGeneratedDocumentType = isSystemGeneratedDocumentType.get();
			List<SystemGeneratedDocumentTypeDetails> systemGeneratedDocumentTypeDetails = systemGeneratedDocumentTypeDetailsRepository
					.findBySystemGeneratedDocumentTypeId(systemGeneratedDocumentType.getId());
			systemGeneratedDocumentType.setDocumentDetails(systemGeneratedDocumentTypeDetails);
			return isSystemGeneratedDocumentType;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public SystemGeneratedDocumentType addSystemGeneratedDocumentType(String tenantId,
			SystemGeneratedDocumentTypeAddResource systemGeneratedDocumentTypeAddResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);

		Optional<SystemGeneratedDocumentType> isPresentSystemGeneratedDocumentType = systemGeneratedDocumentTypeRepository
				.findByCode(systemGeneratedDocumentTypeAddResource.getCode());
		if (isPresentSystemGeneratedDocumentType.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE,
					EntityPoint.DOCUMENT_CHECKLIST);
		}

		Product product = validateProduct(
				validateService.stringToLong(systemGeneratedDocumentTypeAddResource.getProductId()));

		SubProduct subProduct = validateSubProduct(
				validateService.stringToLong(systemGeneratedDocumentTypeAddResource.getSubProductId()));

		CommonListItem applicableLevel = validateApplicableLevel(
				validateService.stringToLong(systemGeneratedDocumentTypeAddResource.getApplicableLevelId()));

		SystemGeneratedDocumentType systemGeneratedDocumentType = new SystemGeneratedDocumentType();
		systemGeneratedDocumentType.setTenantId(tenantId);
		systemGeneratedDocumentType.setApplicableLevel(applicableLevel);
		systemGeneratedDocumentType.setProduct(product);
		systemGeneratedDocumentType.setProductName(product.getProductName());
		systemGeneratedDocumentType.setSubProduct(subProduct);
		systemGeneratedDocumentType.setSubProductName(subProduct.getName());
		systemGeneratedDocumentType.setCode(systemGeneratedDocumentTypeAddResource.getCode());
		systemGeneratedDocumentType.setName(systemGeneratedDocumentTypeAddResource.getName());
		systemGeneratedDocumentType.setStatus(CommonStatus.valueOf(systemGeneratedDocumentTypeAddResource.getStatus()));
		systemGeneratedDocumentType.setCreatedDate(validateService.getCreateOrModifyDate());
		systemGeneratedDocumentType.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		systemGeneratedDocumentType.setSyncTs(validateService.getCreateOrModifyDate());

		systemGeneratedDocumentType = systemGeneratedDocumentTypeRepository.save(systemGeneratedDocumentType);

		if (systemGeneratedDocumentTypeAddResource.getDocumentDetails() != null
				&& !systemGeneratedDocumentTypeAddResource.getDocumentDetails().isEmpty()) {
			Integer index = 0;
			for (SystemGeneratedDocumentTypeDetailsAddResource documentDetailsListResource : systemGeneratedDocumentTypeAddResource
					.getDocumentDetails()) {
				DocumentResource documentResource = validateService.validateDocument(tenantId,
						validateService.stringToLong(documentDetailsListResource.getDocumentDetailId()),
						documentDetailsListResource.getDocumentDetailName());
				saveOrUpadateSystemGeneratedDocumentTypeDetails(documentDetailsListResource.getId(), tenantId,
						documentDetailsListResource.getDocumentDetailId(), documentResource.getName(),
						systemGeneratedDocumentType, documentDetailsListResource.getStatus(),
						documentDetailsListResource.getMandatory(), index);
				index++;
			}
		}

		return systemGeneratedDocumentType;
	}

	@Override
	public SystemGeneratedDocumentType updateSystemGeneratedDocumentType(String tenantId, Long id,
			SystemGeneratedDocumentTypeUpdateResource systemGeneratedDocumentTypeUpdateResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);

		Optional<SystemGeneratedDocumentType> isPresentSystemGeneratedDocumentType = systemGeneratedDocumentTypeRepository
				.findById(id);

		if (!isPresentSystemGeneratedDocumentType.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);

		if (!isPresentSystemGeneratedDocumentType.get().getVersion()
				.equals(Long.parseLong(systemGeneratedDocumentTypeUpdateResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION,
					EntityPoint.DOCUMENT_CHECKLIST);

		if (!isPresentSystemGeneratedDocumentType.get().getCode()
				.equalsIgnoreCase(systemGeneratedDocumentTypeUpdateResource.getCode()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_CODE_UPDATE), ServiceEntity.CODE,
					EntityPoint.DOCUMENT_CHECKLIST);

		Product product = validateProduct(
				validateService.stringToLong(systemGeneratedDocumentTypeUpdateResource.getProductId()));

		SubProduct subProduct = validateSubProduct(
				validateService.stringToLong(systemGeneratedDocumentTypeUpdateResource.getSubProductId()));

		CommonListItem applicableLevel = validateApplicableLevel(
				validateService.stringToLong(systemGeneratedDocumentTypeUpdateResource.getApplicableLevelId()));

		SystemGeneratedDocumentType systemGeneratedDocumentType = isPresentSystemGeneratedDocumentType.get();
		systemGeneratedDocumentType
				.setStatus(CommonStatus.valueOf(systemGeneratedDocumentTypeUpdateResource.getStatus()));
		systemGeneratedDocumentType.setName(systemGeneratedDocumentTypeUpdateResource.getName());
		systemGeneratedDocumentType.setApplicableLevel(applicableLevel);
		systemGeneratedDocumentType.setProduct(product);
		systemGeneratedDocumentType.setProductName(product.getProductName());
		systemGeneratedDocumentType.setSubProduct(subProduct);
		systemGeneratedDocumentType.setSubProductName(subProduct.getName());
		systemGeneratedDocumentType.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		systemGeneratedDocumentType.setModifiedDate(validateService.getCreateOrModifyDate());
		systemGeneratedDocumentType.setSyncTs(validateService.getCreateOrModifyDate());
		systemGeneratedDocumentType = systemGeneratedDocumentTypeRepository.saveAndFlush(systemGeneratedDocumentType);

		if (systemGeneratedDocumentTypeUpdateResource.getDocumentDetails() != null
				&& !systemGeneratedDocumentTypeUpdateResource.getDocumentDetails().isEmpty()) {
			Integer index = 0;
			for (SystemGeneratedDocumentTypeDetailsAddResource documentDetailsListResource : systemGeneratedDocumentTypeUpdateResource
					.getDocumentDetails()) {
				DocumentResource documentResource = validateService.validateDocument(tenantId,
						validateService.stringToLong(documentDetailsListResource.getDocumentDetailId()),
						documentDetailsListResource.getDocumentDetailName());
				saveOrUpadateSystemGeneratedDocumentTypeDetails(documentDetailsListResource.getId(), tenantId,
						documentDetailsListResource.getDocumentDetailId(), documentResource.getName(),
						systemGeneratedDocumentType, documentDetailsListResource.getStatus(),
						documentDetailsListResource.getMandatory(), index);
				index++;
			}
		}

		return systemGeneratedDocumentType;
	}

	private void saveOrUpadateSystemGeneratedDocumentTypeDetails(String id, String tenantId, String documentDetailId,
			String documentDetailName, SystemGeneratedDocumentType systemGeneratedDocumentType, String status,
			String mandatory, Integer index) {
		SystemGeneratedDocumentTypeDetails systemGeneratedDocumentTypeDetails = new SystemGeneratedDocumentTypeDetails();
		if (id != null && !id.isEmpty()) {
			Optional<SystemGeneratedDocumentTypeDetails> systemGeneratedDocumentTypeDetailOptional = systemGeneratedDocumentTypeDetailsRepository
					.findById(validateService.stringToLong(id));
			if (!systemGeneratedDocumentTypeDetailOptional.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
			} else {
				systemGeneratedDocumentTypeDetails = systemGeneratedDocumentTypeDetailOptional.get();
				systemGeneratedDocumentTypeDetails.setModifiedDate(validateService.getCreateOrModifyDate());
				systemGeneratedDocumentTypeDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			}
		} else {
			systemGeneratedDocumentTypeDetails.setTenantId(tenantId);
			systemGeneratedDocumentTypeDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			systemGeneratedDocumentTypeDetails.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		systemGeneratedDocumentTypeDetails.setDocumentTypeId(validateService.stringToLong(documentDetailId));
		systemGeneratedDocumentTypeDetails.setDocumentTypeName(documentDetailName);
		systemGeneratedDocumentTypeDetails.setStatus(CommonStatus.valueOf(status));
		systemGeneratedDocumentTypeDetails.setIsMandatory(YesNo.valueOf(mandatory));
		systemGeneratedDocumentTypeDetails.setSystemGeneratedDocumentType(systemGeneratedDocumentType);
		systemGeneratedDocumentTypeDetails.setSyncTs(validateService.getCreateOrModifyDate());

		systemGeneratedDocumentTypeDetailsRepository.saveAndFlush(systemGeneratedDocumentTypeDetails);

	}

	public CommonListItem validateApplicableLevel(Long id) {

		Optional<CommonListItem> commonListItem = commonListItemRepository.findByIdAndReferenceCodeAndStatus(id,
				CommonListReferenceCode.DOC_APPLICABLE_LEVEL.toString(), CommonStatus.ACTIVE);
		if (!commonListItem.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(RECORD_NOT_FOUND),
					ServiceEntity.APPLICABLE_LEVEL_ID, EntityPoint.DOCUMENT_CHECKLIST);

		return commonListItem.get();
	}

	public Product validateProduct(Long id) {

		Optional<Product> product = productRepository.findByIdAndStatus(id, CommonStatus.ACTIVE);
		if (!product.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.PRODUCT_ID,
					EntityPoint.DOCUMENT_CHECKLIST);
		}

		return product.get();
	}

	public SubProduct validateSubProduct(Long id) {

		Optional<SubProduct> subProduct = subProductRepository.findByIdAndStatus(id, CommonStatus.ACTIVE.toString());
		if (!subProduct.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.SUB_PRODUCT_ID,
					EntityPoint.DOCUMENT_CHECKLIST);
		}

		return subProduct.get();
	}

	@Override
	public List<SystemGeneratedDocumentType> findByProductName(String name) {
		List<SystemGeneratedDocumentType> systemGeneratedDocumentType = systemGeneratedDocumentTypeRepository
				.findByProductNameContaining(name);
		List<SystemGeneratedDocumentType> systemGeneratedDocumentTypeAll = new ArrayList<>();

		for (SystemGeneratedDocumentType item : systemGeneratedDocumentType) {
			List<SystemGeneratedDocumentTypeDetails> systemGeneratedDocumentTypeDetails = systemGeneratedDocumentTypeDetailsRepository
					.findBySystemGeneratedDocumentTypeId(item.getId());
			item.setDocumentDetails(systemGeneratedDocumentTypeDetails);
			systemGeneratedDocumentTypeAll.add(item);
		}
		return systemGeneratedDocumentTypeAll;
	}

	@Override
	public List<SystemGeneratedDocumentType> findBySubProductName(String name) {
		List<SystemGeneratedDocumentType> systemGeneratedDocumentType = systemGeneratedDocumentTypeRepository
				.findBySubProductNameContaining(name);
		List<SystemGeneratedDocumentType> systemGeneratedDocumentTypeAll = new ArrayList<>();

		for (SystemGeneratedDocumentType item : systemGeneratedDocumentType) {
			List<SystemGeneratedDocumentTypeDetails> systemGeneratedDocumentTypeDetails = systemGeneratedDocumentTypeDetailsRepository
					.findBySystemGeneratedDocumentTypeId(item.getId());
			item.setDocumentDetails(systemGeneratedDocumentTypeDetails);
			systemGeneratedDocumentTypeAll.add(item);
		}
		return systemGeneratedDocumentTypeAll;
	}

	@Override
	public List<SystemGeneratedDocumentType> findByName(String name) {
		List<SystemGeneratedDocumentType> systemGeneratedDocumentType = systemGeneratedDocumentTypeRepository
				.findByNameContaining(name);
		List<SystemGeneratedDocumentType> systemGeneratedDocumentTypeAll = new ArrayList<>();

		for (SystemGeneratedDocumentType item : systemGeneratedDocumentType) {
			List<SystemGeneratedDocumentTypeDetails> systemGeneratedDocumentTypeDetails = systemGeneratedDocumentTypeDetailsRepository
					.findBySystemGeneratedDocumentTypeId(item.getId());
			item.setDocumentDetails(systemGeneratedDocumentTypeDetails);
			systemGeneratedDocumentTypeAll.add(item);
		}
		return systemGeneratedDocumentTypeAll;
	}

	@Override
	public Optional<SystemGeneratedDocumentTypeDetails> findById(Long id) {
		boolean existById = systemGeneratedDocumentTypeDetailsRepository.existsById(id);
		Optional<SystemGeneratedDocumentTypeDetails> isPresentSystemGeneratedDocumentTypeDetails = systemGeneratedDocumentTypeDetailsRepository
				.findById(id);
		if (isPresentSystemGeneratedDocumentTypeDetails.isPresent()) {
			return Optional.ofNullable(isPresentSystemGeneratedDocumentTypeDetails.get());
		} else {
			return Optional.empty();
		}
	}
}
