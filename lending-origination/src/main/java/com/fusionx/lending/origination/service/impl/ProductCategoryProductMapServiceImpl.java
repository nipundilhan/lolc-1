package com.fusionx.lending.origination.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ProductCategory;
import com.fusionx.lending.origination.domain.ProductCategoryProductMap;
import com.fusionx.lending.origination.domain.ProductCategoryProductMapDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ProductCategoryProductMapDetailsRepository;
import com.fusionx.lending.origination.repository.ProductCategoryProductMapRepository;
import com.fusionx.lending.origination.repository.ProductCategoryRepository;
import com.fusionx.lending.origination.resource.ProductCategoryProductMapAddResource;
import com.fusionx.lending.origination.resource.ProductCategoryProductMapUpdateResource;
import com.fusionx.lending.origination.resource.ProductRequestResource;
import com.fusionx.lending.origination.service.ProductCategoryProductMapService;
import com.fusionx.lending.origination.service.ValidateService;

@Component
@Transactional(rollbackFor=Exception.class)
public class ProductCategoryProductMapServiceImpl extends MessagePropertyBase implements ProductCategoryProductMapService{

	@Autowired
	private ProductCategoryProductMapRepository productCategoryProductMapRepository;
	
	@Autowired
	private ProductCategoryProductMapDetailsRepository productCategoryProductMapDetailsRepository;
	
	@Autowired
	private ValidateService validateService;
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	


	@Override
	public List<ProductCategoryProductMap> getAll() {
		List<ProductCategoryProductMap> productCategoryProductMaps = productCategoryProductMapRepository.findAll();
		List<ProductCategoryProductMap> productCategoryProductMapList = new ArrayList<>();
		List<ProductCategoryProductMapDetails> productCategoryProductMapDetails = new ArrayList<>();

		for (ProductCategoryProductMap productCategoryProductMap : productCategoryProductMaps) {
			productCategoryProductMapDetails = productCategoryProductMapDetailsRepository.findByProductCategoryProductMapId(productCategoryProductMap.getId());
			productCategoryProductMap.setProducts(productCategoryProductMapDetails);
			productCategoryProductMapList.add(productCategoryProductMap);
		}
		return productCategoryProductMapList;
	}

	@Override
	public Optional<ProductCategoryProductMap> getById(Long id) {
		Optional<ProductCategoryProductMap> isPresentProductCategoryProductMap = productCategoryProductMapRepository.findById(id);
		if (isPresentProductCategoryProductMap.isPresent()) {
			ProductCategoryProductMap productCategoryProductMap = isPresentProductCategoryProductMap.get();
			List<ProductCategoryProductMapDetails> productCategoryProductMapDetails = productCategoryProductMapDetailsRepository.findByProductCategoryProductMapId(isPresentProductCategoryProductMap.get().getId());
			productCategoryProductMap.setProducts(productCategoryProductMapDetails);
			return isPresentProductCategoryProductMap;
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<ProductCategoryProductMap> getByStatus(String status) {
		List<ProductCategoryProductMap> productCategoryProductMaps = productCategoryProductMapRepository.findByStatus(CommonStatus.valueOf(status));
		List<ProductCategoryProductMap> productCategoryProductMapList = new ArrayList<>();
		List<ProductCategoryProductMapDetails> productCategoryProductMapDetails = new ArrayList<>();

		for (ProductCategoryProductMap productCategoryProductMap : productCategoryProductMaps) {
			productCategoryProductMapDetails = productCategoryProductMapDetailsRepository.findByProductCategoryProductMapId(productCategoryProductMap.getId());
			productCategoryProductMap.setProducts(productCategoryProductMapDetails);
			productCategoryProductMapList.add(productCategoryProductMap);
		}
		return productCategoryProductMapList;
	}

	@Override
	public ProductCategoryProductMap addProductCategoryProductMap(String tenantId,
			ProductCategoryProductMapAddResource productCategoryProductMapAddResource) {
        
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		ProductCategory productCategory = validateProductCategory(validateService.stringToLong(productCategoryProductMapAddResource.getProductCatId()), productCategoryProductMapAddResource.getProductCatCode());
		
		ProductCategoryProductMap productCategoryProductMap = new ProductCategoryProductMap();
		productCategoryProductMap.setTenantId(tenantId);
		productCategoryProductMap.setProductCategory(productCategory);
		productCategoryProductMap.setStatus(CommonStatus.valueOf(productCategoryProductMapAddResource.getStatus()));
		productCategoryProductMap.setCreatedDate(validateService.getCreateOrModifyDate());
		productCategoryProductMap.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
		productCategoryProductMap.setSyncTs(validateService.getCreateOrModifyDate());
		
		productCategoryProductMap = productCategoryProductMapRepository.save(productCategoryProductMap);
		
		if(productCategoryProductMapAddResource.getProducts() != null && !productCategoryProductMapAddResource.getProducts().isEmpty()) {
			Integer index = 0;
			for(ProductRequestResource productListResource : productCategoryProductMapAddResource.getProducts()) {
				saveOrUpadateProductCategoryProductMapDet(productListResource, tenantId, productCategoryProductMap, index);
				index++;
			}
		}
		
		return productCategoryProductMap;
	}

	@Override
	public ProductCategoryProductMap updateProductCategoryProductMap(String tenantId, Long id,
			ProductCategoryProductMapUpdateResource productCategoryProductMapUpdateResource) {
		if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), USERNAME);
		
		Optional<ProductCategoryProductMap> isPresentProductCategoryProductMap = productCategoryProductMapRepository.findById(id);
		
		if(!isPresentProductCategoryProductMap.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		
		if(!isPresentProductCategoryProductMap.get().getProductCategory().getId().toString().equalsIgnoreCase(productCategoryProductMapUpdateResource.getProductCatId()))
            throw new ValidateRecordException(environment.getProperty("cannot-change"), "productCategoryId");
		
		if(!isPresentProductCategoryProductMap.get().getProductCategory().getCode().toString().equalsIgnoreCase(productCategoryProductMapUpdateResource.getProductCatCode()))
            throw new ValidateRecordException(environment.getProperty(COMMON_NOT_MATCH), "productCategoryCode");
		
		ProductCategoryProductMap productCategoryProductMap = isPresentProductCategoryProductMap.get();
		productCategoryProductMap.setStatus(CommonStatus.valueOf(productCategoryProductMapUpdateResource.getStatus()));
		productCategoryProductMap.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		productCategoryProductMap.setModifiedDate(validateService.getCreateOrModifyDate());
		productCategoryProductMap.setSyncTs(validateService.getCreateOrModifyDate());
		productCategoryProductMapRepository.saveAndFlush(productCategoryProductMap);
		
		if(productCategoryProductMapUpdateResource.getProducts() != null && !productCategoryProductMapUpdateResource.getProducts().isEmpty()) {
			Integer index = 0;
			for(ProductRequestResource productListResource : productCategoryProductMapUpdateResource.getProducts()) {
				saveOrUpadateProductCategoryProductMapDet(productListResource, tenantId, productCategoryProductMap, index);
				index++;
			}
		}
		
		return isPresentProductCategoryProductMap.get();
	}
	
	private ProductCategory validateProductCategory(Long id, String code) {
		Optional<ProductCategory> isPresentProductCategory = productCategoryRepository.findByIdAndCodeAndStatus(id, code, CommonStatus.ACTIVE.toString());
		if(!isPresentProductCategory.isPresent())
			throw new ValidateRecordException(environment.getProperty(COMMON_INVALID_VALUE), "productCategoryId");
		
		Optional<ProductCategoryProductMap> isPresentProductCategoryProductMap = productCategoryProductMapRepository.findByProductCategoryId(id);
		
		if(isPresentProductCategoryProductMap.isPresent())
			throw new ValidateRecordException(environment.getProperty(COMMON_UNIQUE), "productCategoryId");
		
		return isPresentProductCategory.get();
	}
//	
//	private Product validateProduct(Long id, String name, Integer index) {
//		
//		Optional<Product> isPresentProduct = productRepository.findByIdAndNameAndStatus(id, name, CommonStatus.ACTIVE.toString());
//		
//		if(!isPresentProduct.isPresent())
//			throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PRODUCT_ID, ServicePoint.PRODUCT_PRODUCT_CATEGORY, index);
//		
//		return isPresentProduct.get();
//	}
	
	private void saveOrUpadateProductCategoryProductMapDet(ProductRequestResource productResource, String tenantId,  ProductCategoryProductMap productCategoryProductMap, Integer index) {
		ProductCategoryProductMapDetails productCategoryDetails = new ProductCategoryProductMapDetails();
		if(productResource.getId() != null && !productResource.getId().isEmpty()) {
			Optional<ProductCategoryProductMapDetails> productCategoryDetail = productCategoryProductMapDetailsRepository.findById(validateService.stringToLong(productResource.getId()));
			if(!productCategoryDetail.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PRODUCT_PRODUCT_CATEGORY_DET_ID, ServicePoint.PRODUCT_PRODUCT_CATEGORY, index);
			} else {
				productCategoryDetails = productCategoryDetail.get();
				productCategoryDetails.setModifiedDate(validateService.getCreateOrModifyDate());
				productCategoryDetails.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			}
		} else {
			productCategoryDetails.setTenantId(tenantId);
			productCategoryDetails.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			productCategoryDetails.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		productCategoryDetails.setProductCategoryProductMap(productCategoryProductMap);
		if(productResource.getProductId() != null && !productResource.getProductId().isEmpty())
			productCategoryDetails.setProductId(Long.parseLong(productResource.getProductId()));
		productCategoryDetails.setProductCode(productResource.getProductCode());
		productCategoryDetails.setProductName(productResource.getProductName());
		productCategoryDetails.setStatus(CommonStatus.valueOf(productResource.getStatus()));
		productCategoryDetails.setSyncTs(validateService.getCreateOrModifyDate());
		
		productCategoryProductMapDetailsRepository.saveAndFlush(productCategoryDetails);
		
	}
}
