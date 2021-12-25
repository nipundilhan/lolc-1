package com.fusionx.lending.origination.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.IncomeType;
import com.fusionx.lending.origination.domain.OtherIncomeType;
import com.fusionx.lending.origination.resource.IncomeTypeRequest;
import com.fusionx.lending.origination.resource.UpdateIncomeTypeRequest;


@Service
public interface IncomeTypeService {

	/**
     * Find all Income Type
	 * @author Nisalak
	 * 
	 * @return   		- JSON Array of all Legal Structure objects
	 * 
	 */
	public List<IncomeType> findAll();
	
	/**
     * Find Income Type by Id
	 * @author Nisalak
	 * 
	 * @return   		- JSON Array of Legal Structure objects
	 * 
	 */
	public Optional<IncomeType> findById(Long id);
	
	/**
     * Find Income Type by code
	 * @author Nisalak
	 * 
	 * @return   		- JSON Array of Legal Structure objects
	 * 
	 */
	public Optional<IncomeType> findByCode(String code);
	
	/**
     * Find Income Type by name
	 * @author Nisalak
	 * 
	 * @return   		- JSON Array of Legal Structure objects
	 * 
	 */
	public List<IncomeType> findByName(String name);
	
	/**
     * Find Income Type by Status
	 * @author Nisalak
	 * 
	 * @return   		- JSON Array of Legal Structure objects
	 * 
	 */
	public List<IncomeType> findByStatus(String status);
	
	/**
     * Insert Income Type
	 * @author Nisalak
	 * 
	 * @param	- AddBaseRequest
	 * @return  - Successfully saved message
	 * 
	 */
	public IncomeType addIncomeType(String tenantId, IncomeTypeRequest incomeTypeRequest);
	
	/**
     * Update Income Type
	 * @author Nisalak
	 * 
	 * @param	- UpdateBaseRequest
	 * @return  - Successfully saved message
	 * 
	 */
	public IncomeType updateIncomeType(String tenantId, UpdateIncomeTypeRequest updateIncomeTypeRequest);
	
	/**
	* Find by Business Type Id.
	*
	* @param id - the id
	* @return the Income Type Array
	*/
		
	List<IncomeType> findByBusinessType(Long id);
	
}
