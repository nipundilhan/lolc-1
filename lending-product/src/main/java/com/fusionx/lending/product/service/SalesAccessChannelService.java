package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.fusionx.lending.product.domain.SalesAccessChannel;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
/**
 * Sales Access Channel service
 * @author 	Piyumi
 * @Dat     07-07-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021   FXL-1          FXL-3      Piyumi       Created
 *    
 ********************************************************************************************************
 */

@Service
public interface SalesAccessChannelService {
	
	/**
     * get all Sales Access Channel
	 * @author Piyumi
	 * 
	 * @return   		- JSON Array of all Sales Access Channel objects
	 * 
	 */
	List<SalesAccessChannel> getAll();
	
	
	/**
     * Find Sales Access Channel by Id
	 * @author Piyumi
	 * 
	 * @return   		- JSON Object of Sales Access Channel 
	 * 
	 */
	public Optional<SalesAccessChannel> findById(Long salesAccessChannelId);
	
	/**
     * Find Sales Access Channel by Status
	 * @author Piyumi
	 * 
	 * @return   		- JSON Array of Sales Access Channel objects
	 * 
	 */
	public List<SalesAccessChannel> findByStatus(String status);
	
	/**
     * Insert Sales Access Channel
	 * @author Piyumi
	 * 
	 * @param	- SalesAccessChannelAddResource
	 * @return  - Successfully saved message
	 * 
	 */
	public SalesAccessChannel addSalesAccessChannel(String tenantId, CommonAddResource commonAddResource);
	
	/**
     * Update Sales Access Channel
	 * @author Piyumi
	 * 
	 * @param	- SalesAccessChannelUpdateResource
	 * @return  - Successfully saved message
	 * 
	 */
	public SalesAccessChannel updateSalesAccessChannel(String tenantId, Long id, CommonUpdateResource commonUpdateResource);
	
	/**
     * Find Sales Access Channel by code
	 * @author Piyumi
	 * 
	 * @return   		- JSON Object of Sales Access Channel
	 * 
	 */
	public Optional<SalesAccessChannel> findByCode(String salesAccessChannelCode);
	/**
     * Find Sales Access Channel by name
	 * @author Piyumi
	 * 
	 * @return   		- JSON Array of Sales Access Channel objects
	 * 
	 */
	public List<SalesAccessChannel> findByName(String name);
	

}
