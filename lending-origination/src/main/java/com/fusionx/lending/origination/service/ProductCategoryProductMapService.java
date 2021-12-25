package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ProductCategoryProductMap;
import com.fusionx.lending.origination.resource.ProductCategoryProductMapAddResource;
import com.fusionx.lending.origination.resource.ProductCategoryProductMapUpdateResource;

@Service
public interface ProductCategoryProductMapService {
	
	
	public List<ProductCategoryProductMap> getAll();
	
	
	public Optional<ProductCategoryProductMap> getById(Long id);
	
	
	
	public List<ProductCategoryProductMap> getByStatus(String status);
	
	
	
	public ProductCategoryProductMap addProductCategoryProductMap(String tenantId , ProductCategoryProductMapAddResource productCategoryProductMapAddResource);

	
	public ProductCategoryProductMap updateProductCategoryProductMap(String tenantId, Long id, ProductCategoryProductMapUpdateResource productCategoryProductMapUpdateResource);

}
