package com.fusionx.lending.product.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.BusinessCenter;
import com.fusionx.lending.product.domain.BusinessCenterProductMap;
import com.fusionx.lending.product.domain.BusinessSubCenter;
import com.fusionx.lending.product.domain.BusinessSubCenterProductMap;
import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.ServiceEntity;
import com.fusionx.lending.product.enums.ServicePoint;
import com.fusionx.lending.product.exception.DetailListValidateException;
import com.fusionx.lending.product.exception.ListRecordPrimitiveValidateException;
import com.fusionx.lending.product.exception.OtherException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.BusinessCenterProductMapRepository;
import com.fusionx.lending.product.repository.BusinessCenterRepository;
import com.fusionx.lending.product.repository.BusinessSubCenterProductMapRepository;
import com.fusionx.lending.product.repository.BusinessSubCenterRepository;
import com.fusionx.lending.product.repository.ProductRepository;
import com.fusionx.lending.product.resources.BusinessCenterProductMapAddResource;
import com.fusionx.lending.product.resources.BusinessCenterProductMapUpdateResource;
import com.fusionx.lending.product.resources.BusinessSubCenterProductMapAddResource;
import com.fusionx.lending.product.resources.BusinessSubCenterProductMapUpdateResource;
import com.fusionx.lending.product.resources.ProductRequestResource;
import com.fusionx.lending.product.service.BusinessCenterProductMapService;
import com.fusionx.lending.product.service.BusinessSubCenterProductMapService;
import com.fusionx.lending.product.service.ValidationService;


@Component
@Transactional(rollbackFor = Exception.class)
public class BusinessSubCenterProductMapServiceImpl   extends MessagePropertyBase implements BusinessSubCenterProductMapService{
	
	@Autowired
	private BusinessSubCenterRepository businessSubCenterRepository;	
	
	@Autowired
	private BusinessSubCenterProductMapRepository businessSubCenterProductMapRepository;
	
	@Autowired
	private ValidationService validateService;
	
	@Autowired
	private ProductRepository productRepository;
	
	 public static final String PRODUCTS = "products";
	
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
			throw new ValidateRecordException(environment.getProperty(RECORD_NOT_FOUND), "ID");
		}
		if(!("ACTIVE").equals(businessSubCenterOptional.get().getStatus().toString())) {
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
	public void updateBusinessSubCenterProductMap(String tenantId,Long id,BusinessSubCenterProductMapUpdateResource businessSubCenterProductMapUpdateResource) {
		
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
				throw new ListRecordPrimitiveValidateException(environment.getProperty(COMMON_INVALID_VALUE),null,PRODUCTS,index,"businessSubCenterProductId");
			} else {
				
				if(!(productResource.getVersion()).equals(businessSubCenterProductMapOptional.get().getVersion().toString())) {
					
					throw new ListRecordPrimitiveValidateException(environment.getProperty(COMMON_INVALID_VALUE),null,PRODUCTS,index,VERSION);
				}
				
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
		if(productResource.getProductId() != null && !productResource.getProductId().isEmpty()) {
			Optional<Product> productOptional = productRepository.findById(Long.parseLong(productResource.getProductId()));
		
			if(!productOptional.isPresent()) {
				throw new ListRecordPrimitiveValidateException(environment.getProperty(COMMON_INVALID_VALUE),null,PRODUCTS,index,"productId");
			}else {
				if(!(CommonStatus.ACTIVE.toString()).equals(productOptional.get().getStatus().toString())) {
					throw new ListRecordPrimitiveValidateException(environment.getProperty(INVALID_STATUS),null,PRODUCTS,index,"productId");
				}
			}
			product.setProductId(Long.parseLong(productResource.getProductId()));
			product.setProductCode(productOptional.get().getProductCode());
			product.setProductName(productOptional.get().getProductName());
		
		}
			
		product.setStatus(CommonStatus.valueOf(productResource.getStatus()));
		product.setSyncTs(validateService.getCreateOrModifyDate());
		
		businessSubCenterProductMapRepository.saveAndFlush(product);
		
	}
}
