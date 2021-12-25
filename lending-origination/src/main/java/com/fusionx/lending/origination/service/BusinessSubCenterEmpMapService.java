package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;


import com.fusionx.lending.origination.domain.BusinessSubCenterEmpMap;
import com.fusionx.lending.origination.resource.BusinessSubCenterEmpMapAddResource;
import com.fusionx.lending.origination.resource.BusinessSubCenterEmpMapUpdateResource;

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
