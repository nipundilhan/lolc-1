package com.fusionx.lending.origination.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.ProductCategory;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.ProductCategoryRepository;
import com.fusionx.lending.origination.resource.ProductCategoryAddResource;
import com.fusionx.lending.origination.resource.ProductCategoryUpdateResource;
import com.fusionx.lending.origination.service.ProductCategoryService;




@Component
@Transactional(rollbackFor = Exception.class)
public class ProductCategoryServiceImpl extends MessagePropertyBase implements ProductCategoryService{
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@Override
	public ProductCategory save(String tenantId, ProductCategoryAddResource productCategoryAddResource) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        if(LogginAuthentcation.getInstance().getUserName() == null || LogginAuthentcation.getInstance().getUserName().isEmpty())
        	throw new ValidateRecordException(environment.getProperty(COMMON_NOT_NULL), "username");
        
        Optional<ProductCategory> isPresentProductCategory = productCategoryRepository.findByCode(productCategoryAddResource.getCode());
        if (isPresentProductCategory.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
		}
        
        	
        ProductCategory productCategory = new ProductCategory();
        productCategory.setTenantId(tenantId);
        productCategory.setCode(productCategoryAddResource.getCode());
        productCategory.setName(productCategoryAddResource.getName());
        productCategory.setDescription(productCategoryAddResource.getDescription());
        productCategory.setStatus(productCategoryAddResource.getStatus());
        productCategory.setSyncTs(currentTimestamp);
        productCategory.setCreatedDate(currentTimestamp);
        productCategory.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        productCategory = productCategoryRepository.save(productCategory);
    	return productCategory;
	}

	@Override
	public Optional<ProductCategory> findById(Long id) {
		Optional<ProductCategory> isPresentProductCategory = productCategoryRepository.findById(id);
		if (isPresentProductCategory.isPresent()) {
			return Optional.ofNullable(isPresentProductCategory.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<ProductCategory> findAll() {
		return 	productCategoryRepository.findAll();
	}

	@Override
	public Optional<ProductCategory> findByCode(String code) {
		Optional<ProductCategory> isPresentProductCategory = productCategoryRepository.findByCode(code);
		if (isPresentProductCategory.isPresent()) {
			return Optional.ofNullable(isPresentProductCategory.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<ProductCategory> findByName(String name) {
		return productCategoryRepository.findByName(name);
	}

	@Override
	public List<ProductCategory> findByStatus(String status) {
		return 	productCategoryRepository.findByStatus(status);
	}

	@Override
	public ProductCategory update(String tenantId, @Valid ProductCategoryUpdateResource productCategoryUpdateResource) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		if (LogginAuthentcation.getInstance().getUserName()==null || LogginAuthentcation.getInstance().getUserName().isEmpty())
			throw new ValidateRecordException(getEnvironment().getProperty(COMMON_NOT_NULL), "username");
		
		Optional<ProductCategory> isPresentProductCategory = productCategoryRepository.findById(Long.parseLong(productCategoryUpdateResource.getId()));
		if (!isPresentProductCategory.isPresent()) 
			throw new ValidateRecordException(getEnvironment().getProperty(RECORD_NOT_FOUND), "message");
        
		
		if(!isPresentProductCategory.get().getVersion().equals(Long.parseLong(productCategoryUpdateResource.getVersion())))
			throw new ValidateRecordException(environment.getProperty(INVALID_VERSION), "version");
		
		Optional<ProductCategory> isPresentProductCategoryCode = productCategoryRepository.findByCodeAndIdNotIn(productCategoryUpdateResource.getCode(), Long.parseLong(productCategoryUpdateResource.getId()));
		if (isPresentProductCategoryCode.isPresent())
			throw new ValidateRecordException(environment.getProperty(COMMON_DUPLICATE), "code");
		
		
		ProductCategory productCategory = isPresentProductCategory.get();
		
		productCategory.setTenantId(tenantId);
		productCategory.setCode(productCategoryUpdateResource.getCode());
		productCategory.setName(productCategoryUpdateResource.getName());
		productCategory.setDescription(productCategoryUpdateResource.getDescription());
		productCategory.setStatus(productCategoryUpdateResource.getStatus());
		productCategory.setSyncTs(currentTimestamp);
		productCategory.setModifiedDate(currentTimestamp);
		productCategory.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
		productCategory = productCategoryRepository.saveAndFlush(productCategory);
    	return productCategory;
	}

}
