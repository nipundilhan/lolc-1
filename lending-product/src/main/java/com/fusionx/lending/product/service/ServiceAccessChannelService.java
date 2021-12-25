package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.ServiceAccessChannel;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;



/**
 * Service Access Channel Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021                           Nipun Dilhan        Created
 *    
 ********************************************************************************************************
 */


@Service
public interface ServiceAccessChannelService {

	/**
	 * 
	 * Find all Service Access Channel
	 * @author NipunD
	 * @return -JSON array of all Brand
	 * 
	 * */
	public List<ServiceAccessChannel> getAll();
	

	/**
	 * 
	 * Find ServiceAccessChannel by ID
	 * @author NipunD
	 * @return -JSON array of Brand
	 * 
	 * */
	public Optional<ServiceAccessChannel> findById(Long id);
	
	/**
	 * 
	 * Find ServiceAccessChannel by code
	 * @author NipunD
	 * @return -JSON array of Brand
	 * 
	 * */
	public Optional<ServiceAccessChannel> getByCode(String code);

	
	/**
	 * 
	 * Find ServiceAccessChannel by name
	 * @author NipunD
	 * @return -JSON array of Brand
	 * 
	 * */ 
	public List<ServiceAccessChannel> getByName(String name);
	
	/**
	 * 
	 * Find ServiceAccessChannel by status
	 * @author NipunD
	 * @return -JSON array of Brand
	 * 
	 * */
	public List<ServiceAccessChannel> getByStatus(String status);
	
	
	/**
	 * 
	 * Insert ServiceAccessChannel
	 * @author NipunD
	 * @param  - CommonAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public ServiceAccessChannel addServiceAccessChannel(String tenantId, CommonAddResource commonAddResource);
	

	/**
	 * 
	 * Update ServiceAccessChannel
	 * @author NipunD
	 * @param  - CommonUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public ServiceAccessChannel updateServiceAccessChannel(String tenantId, Long id, CommonUpdateResource commonUpdateResource);
}