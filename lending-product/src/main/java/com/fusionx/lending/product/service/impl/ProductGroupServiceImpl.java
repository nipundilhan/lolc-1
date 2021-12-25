package com.fusionx.lending.product.service.impl;

/**
 * Product Group Service
 * @author 	Venuki
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019   FX-2879        FX-6532    Venuki      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.domain.ProductGroup;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.EntityPoint;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.exception.InvalidServiceIdException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.ProductGroupRepository;
import com.fusionx.lending.product.resources.ProductGroupAddResource;
import com.fusionx.lending.product.resources.ProductGroupUpdateResource;
import com.fusionx.lending.product.service.ProductGroupHistoryService;
import com.fusionx.lending.product.service.ProductGroupService;
import com.fusionx.lending.product.service.ValidationService;

@Component
@Transactional(rollbackFor=Exception.class)
public class ProductGroupServiceImpl implements ProductGroupService{

	@Autowired
	Environment environment;
	
	@Autowired
	private ProductGroupRepository productGroupRepository;
	
	@Autowired
	private ProductGroupHistoryService productGroupHistoryService;
	
	@Autowired
	private ValidationService validationService;


	@Override
	public List<ProductGroup> findAll() {
		return productGroupRepository.findAll();
	}

	@Override
	public Optional<ProductGroup> findById(Long productGroupId) {
		Optional<ProductGroup> productGroup = productGroupRepository.findById(productGroupId);
		if(productGroup.isPresent())
			return Optional.ofNullable(productGroup.get());
		else
			return Optional.empty();
	}


	@Override
	public List<ProductGroup> findByStatus(String status) {
		return productGroupRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public Optional<ProductGroup> getProductGroupByCode(String productGroupCode) {
		Optional<ProductGroup> productGroup = productGroupRepository.findByCode(productGroupCode);
		if(productGroup.isPresent())
			return Optional.ofNullable(productGroup.get());
		else
			return Optional.empty();
	}

	@Override
	public ProductGroup addProductGroup(String tenantId,ProductGroupAddResource productGroupAddResource, String userName) {
		
		ProductGroup productGroup = new ProductGroup();
		
		Optional<ProductGroup>isPresentProductGroupCode = productGroupRepository.findByCodeAndStatus(productGroupAddResource.getCode(), CommonStatus.ACTIVE);
		if (isPresentProductGroupCode.isPresent()) 
			throw new InvalidServiceIdException(environment.getProperty("productGroupCode.duplicate"), ServiceEntity.CODE, EntityPoint.PRODUCT_GROUP);
		
		productGroup.setTenantId(tenantId);
		productGroup.setCode(productGroupAddResource.getCode());
		productGroup.setName(productGroupAddResource.getName());
		productGroup.setDescription(productGroupAddResource.getDescription());
		productGroup.setStatus(CommonStatus.valueOf(productGroupAddResource.getStatus()));
		productGroup.setCreatedUser(userName);
		productGroup.setCreatedDate(validationService.getCreateOrModifyDate());
		productGroup.setSyncTs(validationService.getCreateOrModifyDate());
		productGroup = productGroupRepository.saveAndFlush(productGroup);
		return productGroup;
	}

	@Override
	public ProductGroup updateProductGroup(String tenantId, Long id,ProductGroupUpdateResource productGroupUpdateResource, String userName) {
	
		Optional<ProductGroup> isPresentProductGroup = productGroupRepository.findById(id);
		if(isPresentProductGroup.isPresent()) {
			
			if(!isPresentProductGroup.get().getVersion().equals(Long.parseLong(productGroupUpdateResource.getVersion())))
				throw new InvalidServiceIdException(environment.getProperty("common.version.mismatch"), ServiceEntity.VERSION, EntityPoint.PRODUCT_GROUP);
			
			
			Optional<ProductGroup>isPresentProductGroupCode = productGroupRepository.findByCodeAndId(productGroupUpdateResource.getCode(), id);
			if (!isPresentProductGroupCode.isPresent())
				throw new InvalidServiceIdException(environment.getProperty("code.update.mismatch"), ServiceEntity.CODE, EntityPoint.PRODUCT_GROUP);
			
			ProductGroup productGroup = isPresentProductGroup.get();
			productGroupHistoryService.insertProductGroupHistory(tenantId, productGroup, userName);
			
			//productGroup.setCode(productGroupUpdateResource.getCode());
			productGroup.setName(productGroupUpdateResource.getName());
			productGroup.setDescription(productGroupUpdateResource.getDescription());
			productGroup.setStatus(CommonStatus.valueOf(productGroupUpdateResource.getStatus()));
			productGroup.setModifiedUser(userName);
			productGroup.setModifiedDate(validationService.getCreateOrModifyDate());
			productGroup.setSyncTs(validationService.getCreateOrModifyDate());
			productGroup.setVersion(validationService.stringToLong(productGroupUpdateResource.getVersion()));
			productGroup = productGroupRepository.saveAndFlush(productGroup);
			return productGroup;
		}
		else
			throw new ValidateRecordException(environment.getProperty("record-not-found"),"message");
	}

	@Override
	public Page<ProductGroup> searchProductGroupList(String searchq, String name, String code, String status, Pageable pageable) {
		if(searchq==null || searchq.isEmpty())
			searchq=null;
		if(name==null || name.isEmpty())
			name=null;
		if(code==null || code.isEmpty())
			code=null;
		if(status==null || status.isEmpty())
			status=null;
		
		return productGroupRepository.searchProductGroupList(searchq, name, code,status, pageable);
	}
	
}
