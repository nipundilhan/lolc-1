package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.BusinessSubCenterEmpMap;
import com.fusionx.lending.product.resources.BusinessSubCenterEmpMapAddResource;
import com.fusionx.lending.product.resources.BusinessSubCenterEmpMapUpdateResource;



@Service
public interface BusinessSubCenterEmpMapService {
	
	List<BusinessSubCenterEmpMap> findAll();

	Optional<BusinessSubCenterEmpMap> findById(Long id);

	List<BusinessSubCenterEmpMap> findByStatus(String status);

	List<BusinessSubCenterEmpMap> findByBusinessSubCenterId(Long id);
	
	Long saveAndValidateBusinessSubCenterEmpMap(String tenantId, String userName,
			BusinessSubCenterEmpMapAddResource businessSubCenterEmpMapAddResource, Long businessSubCenterId);
	
	Long updateAndValidateBusinessCenterEmpMap(String tenantId, String userName, Long businessSubCenterId,
			@Valid BusinessSubCenterEmpMapUpdateResource businessSubCenterEmpMapUpdateResource);
	
}
