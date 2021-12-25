package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BusinessCenterEmpMap;
import com.fusionx.lending.origination.resource.BusinessCenterEmpMapAddResource;
import com.fusionx.lending.origination.resource.BusinessCenterEmpMapUpdateResource;

@Service
public interface BusinessCenterEmpMapService {

	List<BusinessCenterEmpMap> findAll();

	Optional<BusinessCenterEmpMap> findById(Long id);

	List<BusinessCenterEmpMap> findByStatus(String status);

	List<BusinessCenterEmpMap> findByBusinessCenterId(Long id);

	Long saveAndValidateBusinessCenterEmpMap(String tenantId, String userName,
			@Valid BusinessCenterEmpMapAddResource businessCenterEmpMapAddResource, Long businessCenterId);

	boolean existsById(Long id);

	Long updateAndValidateBusinessCenterEmpMap(String tenantId, String userName, Long id,
			@Valid BusinessCenterEmpMapUpdateResource businessCenterEmpMapUpdateResource);

}
