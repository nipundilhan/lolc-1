package com.fusionx.lending.origination.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.origination.base.MessagePropertyBase;
import com.fusionx.lending.origination.core.LogginAuthentcation;
import com.fusionx.lending.origination.domain.BusinessCenter;
import com.fusionx.lending.origination.domain.BusinessCenterProductMap;
import com.fusionx.lending.origination.domain.ProductCategoryProductMap;
import com.fusionx.lending.origination.domain.ProductCategoryProductMapDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessCenterProductMapRepository;
import com.fusionx.lending.origination.repository.BusinessCenterRepository;
import com.fusionx.lending.origination.resource.BusinessCenterProductMapAddResource;
import com.fusionx.lending.origination.resource.BusinessCenterProductMapUpdateResource;
import com.fusionx.lending.origination.resource.ProductRequestResource;
import com.fusionx.lending.origination.service.BusinessCenterProductMapService;
import com.fusionx.lending.origination.service.ValidateService;

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
	ValidateService validateService;
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
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "businessCenterId");
		if(businessCenterProductMapAddResource.getProducts() != null && !businessCenterProductMapAddResource.getProducts().isEmpty()) {
			Integer index = 0;
			for(ProductRequestResource productListResource : businessCenterProductMapAddResource.getProducts()) {
				saveOrUpadateProduct(productListResource, tenantId, businessCenter.get(), index);
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
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "businessCenterId");
		if(businessCenterProductMapUpdateResource.getProducts() != null && !businessCenterProductMapUpdateResource.getProducts().isEmpty()) {
			Integer index = 0;
			for(ProductRequestResource productListResource : businessCenterProductMapUpdateResource.getProducts()) {
				saveOrUpadateProduct(productListResource, tenantId, businessCenter.get(), index);
				index++;
			}
		}
		
	}
	
	private Timestamp getCreateOrModifyDate() {
		Calendar calendar = Calendar.getInstance();
    	java.util.Date now = calendar.getTime();
    	return new Timestamp(now.getTime());
	}
	
	private void saveOrUpadateProduct(ProductRequestResource productResource, String tenantId,  BusinessCenter businessCenter, Integer index) {
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
		if(productResource.getProductId() != null && !productResource.getProductId().isEmpty())
			product.setProductId(Long.parseLong(productResource.getProductId()));
		product.setProductCode(productResource.getProductCode());
		product.setStatus(CommonStatus.valueOf(productResource.getStatus()));
		product.setSyncTs(validateService.getCreateOrModifyDate());
		
		repo.saveAndFlush(product);
		
	}

}
