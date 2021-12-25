package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.RepaymentType;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;



@Service
public interface RepaymentTypeService {
	

	Optional<RepaymentType> findById(Long id);

	
	List<RepaymentType> findAll();

	
	Optional<RepaymentType> findByCode(String code);

	
	List<RepaymentType> findByName(String name);


	List<RepaymentType> findByStatus(String status);
	
	
	RepaymentType save(String tenantId, CommonAddResource commonAddResource);

	
	RepaymentType update(String tenantId, Long id, @Valid CommonUpdateResource commonUpdateResource);

}
