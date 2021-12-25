package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.BusinessCenterEmpMap;
import com.fusionx.lending.product.resources.BusinessCenterEmpMapAddResource;
import com.fusionx.lending.product.resources.BusinessCenterEmpMapUpdateResource;

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
