package com.fusionx.lending.origination.service.impl;

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
import com.fusionx.lending.origination.domain.BusinessSubCenter;
import com.fusionx.lending.origination.domain.BusinessSubCenterProductMap;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.ServiceEntity;
import com.fusionx.lending.origination.enums.ServicePoint;
import com.fusionx.lending.origination.exception.DetailListValidateException;
import com.fusionx.lending.origination.exception.ValidateRecordException;
import com.fusionx.lending.origination.repository.BusinessSubCenterProductMapRepository;
import com.fusionx.lending.origination.repository.BusinessSubCenterRepository;
import com.fusionx.lending.origination.resource.BusinessCenterProductMapUpdateResource;
import com.fusionx.lending.origination.resource.BusinessSubCenterProductMapAddResource;
import com.fusionx.lending.origination.resource.BusinessSubCenterProductMapUpdateResource;
import com.fusionx.lending.origination.resource.ProductRequestResource;
import com.fusionx.lending.origination.service.BusinessSubCenterProductMapService;
import com.fusionx.lending.origination.service.ValidateService;


@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessSubCenterProductMapServiceImpl   extends MessagePropertyBase implements BusinessSubCenterProductMapService{
	
	@Autowired
	private BusinessSubCenterRepository businessSubCenterRepository;	
	
	@Autowired
	private BusinessSubCenterProductMapRepository businessSubCenterProductMapRepository;
	
	@Autowired
	private ValidateService validateService;
	
	@Override
	public List<BusinessSubCenterProductMap> findAll() {
		return businessSubCenterProductMapRepository.findAll();
	}

	@Override
	public Optional<BusinessSubCenterProductMap> findById(Long id) {
		return businessSubCenterProductMapRepository.findById(id);
	}

	@Override
	public List<BusinessSubCenterProductMap> findByStatus(String status) {
		return businessSubCenterProductMapRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public List<BusinessSubCenterProductMap> findByBusinessSubCenterId(Long id) {
		// TODO Auto-generated method stub
		return businessSubCenterProductMapRepository.findByBusinessSubCenterId(id);
	}


	@Override
	public Long create(String tenantId, BusinessSubCenterProductMapAddResource businessSubCenterProductMapAddResource ,Long id) {
		
		Optional<BusinessSubCenter> businessSubCenterOptional = businessSubCenterRepository.findById(id);
		if(!businessSubCenterOptional.isPresent()) {
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), ID);
		}
		if(("ACTIVE").equals(businessSubCenterOptional.get().getStatus().toString())) {
			throw new ValidateRecordException("inactive business sub center", MESSAGE);
		}		
		
		
		if(businessSubCenterProductMapAddResource.getProducts() != null && !businessSubCenterProductMapAddResource.getProducts().isEmpty()) {
			Integer index = 0;
			for(ProductRequestResource productListResource : businessSubCenterProductMapAddResource.getProducts()) {
				saveOrUpadateProduct(productListResource, tenantId, businessSubCenterOptional.get(), index);
				index++;
			}
		}
		
		return businessSubCenterOptional.get().getId();
		
	}
	
	@Override
	public void updateBusinessSubCenterProductMap(String tenantId,Long id,
			@Valid BusinessSubCenterProductMapUpdateResource businessSubCenterProductMapUpdateResource) {
		
		Optional<BusinessSubCenter> businessSubCenterOptional = businessSubCenterRepository.findById(id);

		if(!businessSubCenterOptional.isPresent())
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), MESSAGE);
		if(businessSubCenterProductMapUpdateResource.getProducts() != null && !businessSubCenterProductMapUpdateResource.getProducts().isEmpty()) {
			Integer index = 0;
			for(ProductRequestResource productListResource : businessSubCenterProductMapUpdateResource.getProducts()) {
				saveOrUpadateProduct(productListResource, tenantId, businessSubCenterOptional.get(), index);
				index++;
			}
		}
		
	}	
	
	private void saveOrUpadateProduct(ProductRequestResource productResource, String tenantId,  BusinessSubCenter businesssubCenter, Integer index) {
		BusinessSubCenterProductMap product = new BusinessSubCenterProductMap();
		
		if(productResource.getId() != null && !productResource.getId().isEmpty()) {
			Optional<BusinessSubCenterProductMap> businessSubCenterProductMapOptional = businessSubCenterProductMapRepository.findById(validateService.stringToLong(productResource.getId()));
			if(!businessSubCenterProductMapOptional.isPresent()) {
				throw new DetailListValidateException(environment.getProperty(COMMON_INVALID_VALUE), ServiceEntity.PRODUCT_PRODUCT_CATEGORY_DET_ID, ServicePoint.PRODUCT_PRODUCT_CATEGORY, index);
			} else {
				product = businessSubCenterProductMapOptional.get();
				product.setModifiedDate(validateService.getCreateOrModifyDate());
				product.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
			}
		} else {
			product.setTenantId(tenantId);
			product.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
			product.setCreatedDate(validateService.getCreateOrModifyDate());
		}
		
		product.setBusinessSubCenter(businesssubCenter);
		if(productResource.getProductId() != null && !productResource.getProductId().isEmpty())
			product.setProductId(Long.parseLong(productResource.getProductId()));
		product.setProductCode(productResource.getProductCode());
		product.setStatus(CommonStatus.valueOf(productResource.getStatus()));
		product.setSyncTs(validateService.getCreateOrModifyDate());
		
		businessSubCenterProductMapRepository.saveAndFlush(product);
		
	}
}
