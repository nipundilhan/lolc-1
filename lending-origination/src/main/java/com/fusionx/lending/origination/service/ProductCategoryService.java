package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ProductCategory;
import com.fusionx.lending.origination.resource.ProductCategoryAddResource;
import com.fusionx.lending.origination.resource.ProductCategoryUpdateResource;



@Service
public interface ProductCategoryService {
	
	ProductCategory save(String tenantId, ProductCategoryAddResource productCategoryAddResource);


	Optional<ProductCategory> findById(Long id);
	

	List<ProductCategory> findAll();


	Optional<ProductCategory> findByCode(String code);


	List<ProductCategory> findByName(String name);


	List<ProductCategory> findByStatus(String status);


	ProductCategory update(String tenantId, @Valid ProductCategoryUpdateResource productCategoryUpdateResource);

}
