package com.fusionx.lending.product.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LoggerRequest;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.BusinessCenter;
import com.fusionx.lending.product.domain.BusinessCenterProductMap;
import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.ServicePoint;
import com.fusionx.lending.product.exception.DetailListValidateException;
import com.fusionx.lending.product.exception.OtherException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.BusinessCenterProductMapRepository;
import com.fusionx.lending.product.repository.BusinessCenterRepository;
import com.fusionx.lending.product.repository.ProductRepository;
import com.fusionx.lending.product.resources.BusinessCenterProductMapAddResource;
import com.fusionx.lending.product.resources.BusinessCenterProductMapUpdateResource;
import com.fusionx.lending.product.resources.ProductRequestResource;
import com.fusionx.lending.product.service.BusinessCenterProductMapService;



/**
 * Business Center
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-08-2021      		     FX-	   Thsmokshi      Created
 *    
 ********************************************************************************************************
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessCenterProductMapServiceImpl extends MessagePropertyBase implements BusinessCenterProductMapService{

	@Autowired
	private BusinessCenterProductMapRepository repo;
	
	@Autowired
	BusinessCenterRepository businessCenterRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ValidationServiceImpl validateService;
	@Override
	public List<BusinessCenterProductMap> findAll() {
		return repo.findAll();
	}

	@Override
	public Optional<BusinessCenterProductMap> findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public List<BusinessCenterProductMap> findByStatus(String status) {
		return repo.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public List<BusinessCenterProductMap> findByBusinessCenterId(Long id) {
		// TODO Auto-generated method stub
		return repo.findByBusinessCenterId(id);
	}

	@Override
	public Long saveAndValidateBusinessCenterProductMap(String tenantId, String userName,
			@Valid BusinessCenterProductMapAddResource businessCenterProductMapAddResource, Long id) {

		BusinessCenterProductMap businessCenterProductMap= new BusinessCenterProductMap();
		Optional<BusinessCenter> businessCenter=businessCenterRepository.findById(id);
		if(!businessCenter.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		if(businessCenterProductMapAddResource.getProducts() != null && !businessCenterProductMapAddResource.getProducts().isEmpty()) {
			Integer index = 0;
			for(ProductRequestResource productListResource : businessCenterProductMapAddResource.getProducts()) {									
				saveProduct(productListResource, tenantId, businessCenter.get(), index);
				index++;
			}
		}
		return businessCenter.get().getId();
	}
	
	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return repo.existsById(id);
	}

	@Override
	public void updateAndValidateBusinessCenterProductMap(String tenantId, String userName, Long id,
			@Valid BusinessCenterProductMapUpdateResource businessCenterProductMapUpdateResource) {
		Optional<BusinessCenter> businessCenter=businessCenterRepository.findById(id);
		if(!businessCenter.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		
			
		if(businessCenterProductMapUpdateResource.getProducts() != null && !businessCenterProductMapUpdateResource.getProducts().isEmpty()) {
			Integer index = 0;
			for(ProductRequestResource productListResource : businessCenterProductMapUpdateResource.getProducts()) {								
				upadateProduct(productListResource, tenantId, businessCenter.get(), index);
				index++;
			}
		}
		
	}
	
	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	private void saveProduct(ProductRequestResource productResource, String tenantId,  BusinessCenter businessCenter, Integer index) {
		
		LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate product*******************");

		Optional<Product> productOptional = productRepository.findById(Long.parseLong(productResource.getProductId()));
		
		if(!productOptional.isPresent()) {
			throw new OtherException(environment.getProperty("record-not-found"));
		}
		Product productVal = productOptional.get();

		BusinessCenterProductMap product = new BusinessCenterProductMap();
		if(productResource.getId() != null && !productResource.getId().isEmpty()) {
			Optional<BusinessCenterProductMap> productCategoryDetail = repo.findById(validateService.stringToLong(productResource.getId()));
			if(!productCategoryDetail.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PRODUCT_PRODUCT_CATEGORY_DET_ID, ServicePoint.PRODUCT_PRODUCT_CATEGORY, index);
			} else {
				product = productCategoryDetail.get();
				product.setModifiedDate(validateService.getCreateOrModifyDate());
				product.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			}
		} else {
			product.setTenantId(tenantId);
			product.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			product.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		product.setBusinessCenter(businessCenter);

		product.setProductId(Long.parseLong(productResource.getProductId()));
		product.setProductCode(productOptional.get().getProductCode());
		product.setStatus(CommonStatus.valueOf(productResource.getStatus()));
		product.setSyncTs(validateService.getCreateOrModifyDate());
		
		repo.saveAndFlush(product);
		
	}
	
	private void upadateProduct(ProductRequestResource productResource, String tenantId,  BusinessCenter businessCenter, Integer index) {
		
		LoggerRequest.getInstance().logInfo("**********Validate product*******************");
		Optional<Product> productOptional = productRepository.findById(Long.parseLong(productResource.getProductId()));
		
		if(!productOptional.isPresent()) {
			throw new OtherException(environment.getProperty("record-not-found"));
		}
		Product productVal = productOptional.get();
		BusinessCenterProductMap product = new BusinessCenterProductMap();
		if(productResource.getId() != null && !productResource.getId().isEmpty()) {
			Optional<BusinessCenterProductMap> productCategoryDetail = repo.findById(validateService.stringToLong(productResource.getId()));
			
			LoggerRequest.getInstance().logInfo("NotificationProcessSetup************Validate version*******************");
			if(!productCategoryDetail.get().getVersion().equals(Long.parseLong(productResource.getVersion())))
				throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");
			
			if(!productCategoryDetail.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PRODUCT_PRODUCT_CATEGORY_DET_ID, ServicePoint.PRODUCT_PRODUCT_CATEGORY, index);
			} else {
				product = productCategoryDetail.get();
				product.setModifiedDate(validateService.getCreateOrModifyDate());
				product.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			}
		} else {
			product.setTenantId(tenantId);
			product.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			product.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		product.setBusinessCenter(businessCenter);

		product.setProductId(Long.parseLong(productResource.getProductId()));
		product.setProductCode(productOptional.get().getProductCode());
		product.setStatus(CommonStatus.valueOf(productResource.getStatus()));
		product.setSyncTs(validateService.getCreateOrModifyDate());
		
		repo.saveAndFlush(product);
		
	}
	
	private Product setProductAndValidate(Long productId, String productCode, String productName) {
		Product product = null;
		Optional<Product> productOptional = productRepository.findByIdAndProductCodeAndProductNameAndStatus(productId,productCode,productName,CommonStatus.ACTIVE);
		if (!productOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "productId");
		} else {
			product = productOptional.get();
		}
		return product;
	}

}
