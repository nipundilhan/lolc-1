package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BusinessCenter;
import com.fusionx.lending.origination.resource.BusinessCenterAddResource;
import com.fusionx.lending.origination.resource.BusinessCenterUpdateResource;

/**
 * Business Center
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-08-2021      		     FX-	   Thsmokshi      Created
 *    
 ********************************************************************************************************
 */
@Service
public interface BusinessCenterService {

	/**
	 * 
	 * @return List<BusinessCenter>
	 */
	List<BusinessCenter> findAll();

	/**
	 * 
	 * @return Optional<BusinessCenter>
	 */
	Optional<BusinessCenter> findById(Long id);

	/**
	 * 
	 * @return List<BusinessCenter>
	 */
	List<BusinessCenter> findByStatus(String status);

	/**
	 * 
	 * @return List<BusinessCenter>
	 */
	List<BusinessCenter> findByName(String name);

	/**
	 * 
	 * @return Optional<BusinessCenter>
	 */
	Optional<BusinessCenter> findByCode(String code);

	/**
	 * 
	 * @param tenantId
	 * @param userName
	 * @param businessCenterAddResource
	 * @return
	 */
	Long saveAndValidateBusinessCenter(String tenantId, String userName,
			@Valid BusinessCenterAddResource businessCenterAddResource);

	boolean existsById(Long id);

	/**
	 * 
	 * @param tenantId
	 * @param userName
	 * @param id
	 * @param businessCenterUpdateResource
	 */
	void updateAndValidateBusinessCenter(String tenantId, String userName, Long id,
			@Valid BusinessCenterUpdateResource businessCenterUpdateResource);

}
