package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BusinessCenterProductMap;
import com.fusionx.lending.origination.resource.BusinessCenterProductMapAddResource;
import com.fusionx.lending.origination.resource.BusinessCenterProductMapUpdateResource;
/**
 * Business Center product
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-08-2021      		     FX-	   Thsmokshi      Created
 *    
 ********************************************************************************************************
 */
@Service
public interface BusinessCenterProductMapService {

	List<BusinessCenterProductMap> findAll();

	Optional<BusinessCenterProductMap> findById(Long id);

	List<BusinessCenterProductMap> findByStatus(String status);

	List<BusinessCenterProductMap> findByBusinessCenterId(Long id);

	Long saveAndValidateBusinessCenterProductMap(String tenantId, String userName,
			@Valid BusinessCenterProductMapAddResource businessCenterProductMapAddResource, Long id);

	boolean existsById(Long id);

	void updateAndValidateBusinessCenterProductMap(String tenantId, String userName, Long id,
			@Valid BusinessCenterProductMapUpdateResource businessCenterProductMapUpdateResource);


}
