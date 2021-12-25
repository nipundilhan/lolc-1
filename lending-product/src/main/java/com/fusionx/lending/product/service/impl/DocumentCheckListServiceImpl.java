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
import com.fusionx.lending.product.domain.DocumentCheckList;
import com.fusionx.lending.product.domain.DocumentCheckListDetails;
import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.enums.ApplicableLevel;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.CommonListItemRepository;
import com.fusionx.lending.product.repository.DocumentCheckListDetailsRepository;
import com.fusionx.lending.product.repository.DocumentCheckListRepository;
import com.fusionx.lending.product.repository.ProductRepository;
import com.fusionx.lending.product.repository.SubProductRepository;
import com.fusionx.lending.product.resources.DocumentCheckListAddResource;
import com.fusionx.lending.product.resources.DocumentCheckListUpdateResource;
import com.fusionx.lending.product.resources.DocumentDetailsListResource;
import com.fusionx.lending.product.resources.DocumentResource;
import com.fusionx.lending.product.service.DocumentCheckListService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor = Exception.class)
public class DocumentCheckListServiceImpl extends MessagePropertyBase implements DocumentCheckListService {

	@Autowired
	private DocumentCheckListRepository documentCheckListRepository;
	
	@Autowired
	private DocumentCheckListDetailsRepository documentCheckListDetailsRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SubProductRepository subProductRepository;
	
	@Autowired
	private CommonListItemRepository commonListItemRepository;
	
	@Autowired
	private ValidationService validateService;

	@Override
	public List<DocumentCheckList> getAll() {

		List<DocumentCheckList> documentCheckList = documentCheckListRepository.findAll();
		List<DocumentCheckList> documentCheckListAll = new ArrayList<>();
		List<DocumentCheckListDetails> documentCheckListDetails = new ArrayList<>();
		
		for(DocumentCheckList item : documentCheckList) {
			documentCheckListDetails = documentCheckListDetailsRepository.findByDocumentCheckListId(item.getId());
			item.setDocumentCheckListDetails(documentCheckListDetails);
			documentCheckListAll.add(item);
		}
		return documentCheckListAll;
	}
	
	
	@Override
	public Optional<DocumentCheckList> getById(Long id) {
		Optional<DocumentCheckList> isPresentDocumentCheckList = documentCheckListRepository.findById(id);
		if (isPresentDocumentCheckList.isPresent()) {
			DocumentCheckList documentCheckList = isPresentDocumentCheckList.get();
			List<DocumentCheckListDetails> documentCheckListDetails = documentCheckListDetailsRepository.findByDocumentCheckListId(documentCheckList.getId());
			documentCheckList.setDocumentCheckListDetails(documentCheckListDetails);
			return isPresentDocumentCheckList;
		} else {
			return Optional.empty();
		}
	}
	

	@Override
	public List<DocumentCheckList> getByStatus(String status) {
		List<DocumentCheckList> documentCheckListAll = documentCheckListRepository.findByStatus(CommonStatus.valueOf(status));
		List<DocumentCheckList> expenceTypeCultivationCategoryList = new ArrayList<>();
		List<DocumentCheckListDetails> documentCheckListDetails = new ArrayList<>();
		
		for (DocumentCheckList item : documentCheckListAll) {
			documentCheckListDetails = documentCheckListDetailsRepository.findByDocumentCheckListId(item.getId());
			item.setDocumentCheckListDetails(documentCheckListDetails);
			expenceTypeCultivationCategoryList.add(item);
		}
		return documentCheckListAll;
	}

	
	@Override
	public Optional<DocumentCheckList> getByCode(String code) {
		Optional<DocumentCheckList> isDocumentCheckList= documentCheckListRepository.findByCode(code);
		if (isDocumentCheckList.isPresent()) {
			DocumentCheckList documentCheckList = isDocumentCheckList.get();
			List<DocumentCheckListDetails> documentCheckListDetails = documentCheckListDetailsRepository.findByDocumentCheckListId(documentCheckList.getId());
			documentCheckList.setDocumentCheckListDetails(documentCheckListDetails);
			return isDocumentCheckList;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public DocumentCheckList addDocumentCheckList(String tenantId,
			DocumentCheckListAddResource documentCheckListAddResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);

		Optional<DocumentCheckList> isPresentDocumentCheckList = documentCheckListRepository.findByCode(documentCheckListAddResource.getCode());
		if (isPresentDocumentCheckList.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(COMMON_DUPLICATE), ServiceEntity.CODE, EntityPoint.DOCUMENT_CHECKLIST);
		}

		Product product = validateProduct(validateService.stringToLong(documentCheckListAddResource.getProductId()), documentCheckListAddResource.getProductName());
		
		SubProduct subProduct = validateSubProduct(validateService.stringToLong(documentCheckListAddResource.getSubProductId()), documentCheckListAddResource.getSubProductName());
		
		CommonListItem applicableLevel = validateApplicableLevel(validateService.stringToLong(documentCheckListAddResource.getApplicableLevelId()), documentCheckListAddResource.getApplicableLevelName());
		
		DocumentCheckList documentCheckList = new DocumentCheckList();
		documentCheckList.setTenantId(tenantId);
		documentCheckList.setApplicableLevel(applicableLevel);
		documentCheckList.setProduct(product);
		documentCheckList.setProductName(product.getProductName());
		documentCheckList.setSubProduct(subProduct);
		documentCheckList.setSubProductName(subProduct.getName());
		documentCheckList.setCode(documentCheckListAddResource.getCode());
		documentCheckList.setName(documentCheckListAddResource.getName());
		documentCheckList.setStatus(CommonStatus.valueOf(documentCheckListAddResource.getStatus()));
		documentCheckList.setCreatedDate(validateService.getCreateOrModifyDate());
		documentCheckList.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		documentCheckList.setSyncTs(validateService.getCreateOrModifyDate());
		
		documentCheckList = documentCheckListRepository.save(documentCheckList);
		
		
		if (documentCheckListAddResource.getDocumentDetails() != null
				&& !documentCheckListAddResource.getDocumentDetails().isEmpty()) {
			Integer index = 0;
			for (DocumentDetailsListResource documentDetailsListResource : documentCheckListAddResource.getDocumentDetails()) {
				DocumentResource documentResource = validateService.validateDocument(tenantId, validateService.stringToLong(documentDetailsListResource.getDocumentDetailId()), documentDetailsListResource.getDocumentDetailName());
				saveOrUpadateDocumentCheckListDetails(documentDetailsListResource.getId(), tenantId, documentDetailsListResource.getDocumentDetailId(),documentResource.getName(),
						documentCheckList,documentDetailsListResource.getStatus(),documentDetailsListResource.getMandatory(),index);
				index++;
			}
		}

		return documentCheckList;
	}

	@Override
	public DocumentCheckList updateDocumentCheckList(String tenantId, Long id,
			DocumentCheckListUpdateResource documentCheckListUpdateResource) {

		if (LogginAuthentcation.getInstance().getUserName() == null
				|| LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);

		Optional<DocumentCheckList> isPresentDocumentCheckList = documentCheckListRepository
				.findById(id);

		if (!isPresentDocumentCheckList.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		
		if(!isPresentDocumentCheckList.get().getVersion().equals(Long.parseLong(documentCheckListUpdateResource.getVersion())))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.VERSION, EntityPoint.DOCUMENT_CHECKLIST);
		
		if (!isPresentDocumentCheckList.get().getCode().equalsIgnoreCase(documentCheckListUpdateResource.getCode()))
			throw new InvalidServiceIdException(environment.getProperty(COMMON_CODE_UPDATE), ServiceEntity.CODE, EntityPoint.DOCUMENT_CHECKLIST);
		
		Product product = validateProduct(validateService.stringToLong(documentCheckListUpdateResource.getProductId()), documentCheckListUpdateResource.getProductName());
		
		SubProduct subProduct = validateSubProduct(validateService.stringToLong(documentCheckListUpdateResource.getSubProductId()), documentCheckListUpdateResource.getSubProductName());
		
		CommonListItem applicableLevel = validateApplicableLevel(validateService.stringToLong(documentCheckListUpdateResource.getApplicableLevelId()), documentCheckListUpdateResource.getApplicableLevelName());

		DocumentCheckList documentCheckList = isPresentDocumentCheckList.get();
		documentCheckList
				.setStatus(CommonStatus.valueOf(documentCheckListUpdateResource.getStatus()));
		documentCheckList.setName(documentCheckListUpdateResource.getName());
		documentCheckList.setApplicableLevel(applicableLevel);
		documentCheckList.setProduct(product);
		documentCheckList.setProductName(product.getProductName());
		documentCheckList.setSubProduct(subProduct);
		documentCheckList.setSubProductName(subProduct.getName());
		documentCheckList.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		documentCheckList.setModifiedDate(validateService.getCreateOrModifyDate());
		documentCheckList.setSyncTs(validateService.getCreateOrModifyDate());
		documentCheckList = documentCheckListRepository.saveAndFlush(documentCheckList);
		
		if (documentCheckListUpdateResource.getDocumentDetails() != null
				&& !documentCheckListUpdateResource.getDocumentDetails().isEmpty()) {
			Integer index = 0;
			for (DocumentDetailsListResource documentDetailsListResource : documentCheckListUpdateResource.getDocumentDetails()) {
				DocumentResource documentResource = validateService.validateDocument(tenantId, validateService.stringToLong(documentDetailsListResource.getDocumentDetailId()), documentDetailsListResource.getDocumentDetailName());
				saveOrUpadateDocumentCheckListDetails(documentDetailsListResource.getId(), tenantId, documentDetailsListResource.getDocumentDetailId(),documentResource.getName(),
						documentCheckList,documentDetailsListResource.getStatus(),documentDetailsListResource.getMandatory(),index);
				index++;
			}
		}

		return documentCheckList;
	}

	private void saveOrUpadateDocumentCheckListDetails(String id, String tenantId, String documentDetailId,
			String documentDetailName, DocumentCheckList documentCheckList, String status, String mandatory, Integer index) {
		DocumentCheckListDetails documentCheckListDetails = new DocumentCheckListDetails();
		if(id != null && !id.isEmpty()) {
			Optional<DocumentCheckListDetails> documentCheckListDetailOptional = documentCheckListDetailsRepository.findById(validateService.stringToLong(id));
			if(!documentCheckListDetailOptional.isPresent()) {
				throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
			} else {
				documentCheckListDetails = documentCheckListDetailOptional.get();
				documentCheckListDetails.setModifiedDate(validateService.getCreateOrModifyDate());
				documentCheckListDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			}
		} else {
			documentCheckListDetails.setTenantId(tenantId);
			documentCheckListDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			documentCheckListDetails.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		documentCheckListDetails.setDocumentTypeId(validateService.stringToLong(documentDetailId));
		documentCheckListDetails.setDocumentTypeName(documentDetailName);
		documentCheckListDetails.setStatus(CommonStatus.valueOf(status));
		documentCheckListDetails.setIsMandatory(YesNo.valueOf(mandatory));
		documentCheckListDetails.setDocumentCheckList(documentCheckList);
		documentCheckListDetails.setSyncTs(validateService.getCreateOrModifyDate());
		
		documentCheckListDetailsRepository.saveAndFlush(documentCheckListDetails);
		
	}

	public CommonListItem validateApplicableLevel(Long id, String name) {

		Optional<CommonListItem> commonListItem = commonListItemRepository.findByIdAndNameAndReferenceCodeAndStatus(id,name, ApplicableLevel.DOC_APPLICABLE_LEVEL.toString(),CommonStatus.ACTIVE);
		if (!commonListItem.isPresent())
			throw new InvalidServiceIdException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.APPLICABLE_LEVEL_ID, EntityPoint.DOCUMENT_CHECKLIST);

		return commonListItem.get();
	}
	
	public Product validateProduct(Long id, String name) {
		
		Optional<Product> product = productRepository.findByIdAndProductNameAndStatus(id, name, CommonStatus.ACTIVE);
		if (!product.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.PRODUCT_ID, EntityPoint.DOCUMENT_CHECKLIST);
		}
		
		return product.get();
	}
	
	public SubProduct validateSubProduct(Long id, String name) {
		
		Optional<SubProduct> subProduct = subProductRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE.toString());
		if (!subProduct.isPresent()) {
			throw new InvalidServiceIdException(environment.getProperty(RECORD_NOT_FOUND), ServiceEntity.SUB_PRODUCT_ID, EntityPoint.DOCUMENT_CHECKLIST);
		}
		
		return subProduct.get();
	}

	@Override
	public List<DocumentCheckList> findByProductName(String name) {
		List<DocumentCheckList> documentCheckList = documentCheckListRepository.findByProductNameContaining(name);
		List<DocumentCheckList> documentCheckListAll = new ArrayList<>();
		List<DocumentCheckListDetails> documentCheckListDetails = new ArrayList<>();
		
		for(DocumentCheckList item : documentCheckList) {
			documentCheckListDetails = documentCheckListDetailsRepository.findByDocumentCheckListId(item.getId());
			item.setDocumentCheckListDetails(documentCheckListDetails);
			documentCheckListAll.add(item);
		}
		return documentCheckListAll;
	}


	@Override
	public List<DocumentCheckList> findBySubProductName(String name) {
		List<DocumentCheckList> documentCheckList = documentCheckListRepository.findBySubProductNameContaining(name);
		List<DocumentCheckList> documentCheckListAll = new ArrayList<>();
		List<DocumentCheckListDetails> documentCheckListDetails = new ArrayList<>();
		
		for(DocumentCheckList item : documentCheckList) {
			documentCheckListDetails = documentCheckListDetailsRepository.findByDocumentCheckListId(item.getId());
			item.setDocumentCheckListDetails(documentCheckListDetails);
			documentCheckListAll.add(item);
		}
		return documentCheckListAll;
	}


	@Override
	public List<DocumentCheckList> findByName(String name) {
		List<DocumentCheckList> documentCheckList = documentCheckListRepository.findByNameContaining(name);
		List<DocumentCheckList> documentCheckListAll = new ArrayList<>();
		List<DocumentCheckListDetails> documentCheckListDetails = new ArrayList<>();
		
		for(DocumentCheckList item : documentCheckList) {
			documentCheckListDetails = documentCheckListDetailsRepository.findByDocumentCheckListId(item.getId());
			item.setDocumentCheckListDetails(documentCheckListDetails);
			documentCheckListAll.add(item);
		}
		return documentCheckListAll;
	}
	
	
	@Override
	public Optional<DocumentCheckListDetails> findById(Long id) {
		//Optional<DocumentCheckList> isPresentDocumentCheckList = 
		boolean existById = documentCheckListDetailsRepository.existsById(id);
		Optional<DocumentCheckListDetails> isPresentDocumentCheckListDetails = documentCheckListDetailsRepository.findById(id);
		if (isPresentDocumentCheckListDetails.isPresent()) {
			return Optional.ofNullable(isPresentDocumentCheckListDetails.get());
		} else {
			return Optional.empty();
		}
	}
}
