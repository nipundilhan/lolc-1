package com.fusionx.lending.product.service;

/**
 * Product Group Service
 * @author 	Venuki
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019   FX-2879        FX-6532    Venuki      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.ProductGroup;
import com.fusionx.lending.product.resources.ProductGroupAddResource;
import com.fusionx.lending.product.resources.ProductGroupUpdateResource;

@Service
public interface ProductGroupService {
	
	/**
     * Find all Product Group
	 * @author Venuki
	 * 
	 * @return   		- JSON Array of all Product Group objects
	 * 
	 */
	List<ProductGroup> findAll();
	
	
	/**
     * Find Product Group by Id
	 * @author Venuki
	 * 
	 * @return   		- JSON Array of Product Group objects
	 * 
	 */
	public Optional<ProductGroup> findById(Long productGroupId);
	
	/**
     * Find Product Group by Status
	 * @author Venuki
	 * 
	 * @return   		- JSON Array of Product Group objects
	 * 
	 */
	public List<ProductGroup> findByStatus(String status);
	
	/**
     * Insert Product Group
	 * @author Venuki
	 * 
	 * @param	- ProductGroupAddResource
	 * @return  - Successfully saved message
	 * 
	 */
	public ProductGroup addProductGroup(String tenantId, ProductGroupAddResource productGroupAddResource, String userName);
	
	/**
     * Update Product Group
	 * @author Venuki
	 * 
	 * @param	- OtherEligibilityUpdateResource
	 * @return  - Successfully saved message
	 * 
	 */
	public ProductGroup updateProductGroup(String tenantId, Long id, ProductGroupUpdateResource productGroupUpdateResource, String userName);
	
	/**
     * Find Product Group by code
	 * @author Venuki
	 * 
	 * @return   		- JSON Array of Product Group objects
	 * 
	 */
	public Optional<ProductGroup> getProductGroupByCode(String productGroupCode);

	/**
     * Search Product Groups
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param status - the status
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<ProductGroup> searchProductGroupList(String searchq, String name, String code, String status,
			Pageable pageable);
	
}
