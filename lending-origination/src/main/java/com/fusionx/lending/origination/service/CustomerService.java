package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.resource.CustomerResource;
import com.fusionx.lending.origination.resource.UpdateCustomerResource;

/**
 * Customer Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-04-2021   							 Thamokshi        Created
 *    
 *    2	  05-08-2021	FXL-381        FXL-424	  Piyumi		  Modified
 ********************************************************************************************************
 */
@Service
public interface CustomerService {

	/**
	 * 
	 * @param tenantId
	 * @param customerResource
	 * @return
	 */
	Customer save(String tenantId, @Valid CustomerResource customerResource);

	/**
	 * 
	 * @return List<Customer>
	 */
	List<Customer> getAll();

	/**
	 * 
	 * @param id
	 * @return
	 */
	Optional<Customer> getById(Long id);

	/***
	 * 
	 * @param id
	 * @return
	 */
	boolean existsById(Long id);

	/**
	 * 
	 * @param tenantId
	 * @param userName
	 * @param id
	 * @param updateCustomerResource
	 * @return
	 */
	Customer update(String tenantId, String userName, Long id, @Valid UpdateCustomerResource updateCustomerResource);

	List<Customer> getByLeadId(Long leadId);
 

}
