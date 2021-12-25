package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.SalaryIncomeType;
import com.fusionx.lending.origination.resource.CommonAddResource;
import com.fusionx.lending.origination.resource.CommonUpdateResource;
/**
 * Salary Income Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   12-08-2021   FXL-338 		 FXL-532 	Dilki        Created
 *    
 ********************************************************************************************************
 */
@Service
public interface SalaryIncomeTypeService {

	/**
	 * 
	 * Find all SalaryIncomeType
	 * 
	 * @author Dilki
	 * @return JSON array of all SalaryIncomeType
	 * 
	 */
	public List<SalaryIncomeType> getAll();

	/**
	 * 
	 * Find SalaryIncomeType by ID
	 * 
	 * @author Dilki
	 * @return JSON array of SalaryIncomeType
	 * 
	 */
	public Optional<SalaryIncomeType> getById(Long id);

	/**
	 * 
	 * Find SalaryIncomeType by code
	 * 
	 * @author Dilki
	 * @return JSON array of SalaryIncomeType
	 * 
	 */
	public Optional<SalaryIncomeType> getByCode(String code);

	/**
	 * 
	 * Find SalaryIncomeType by name
	 * 
	 * @author Dilki
	 * @return JSON array of SalaryIncomeType
	 * 
	 */
	public List<SalaryIncomeType> getByName(String name);

	/**
	 * 
	 * Find SalaryIncomeType by status
	 * 
	 * @author Dilki
	 * @return JSON array of SalaryIncomeType
	 * 
	 */
	public List<SalaryIncomeType> getByStatus(String status);

	/**
	 * 
	 * Insert SalaryIncomeType
	 * 
	 * @author Dilki
	 * @param CommonAddResource
	 * @return Successfully saved
	 * 
	 */
	public SalaryIncomeType addSalaryIncomeType(String tenantId, CommonAddResource commonAddResource);

	/**
	 * 
	 * Update SalaryIncomeType
	 * 
	 * @author Dilki
	 * @param CommonUpdateResource
	 * @return Successfully updated
	 * 
	 */
	public SalaryIncomeType updateSalaryIncomeType(String tenantId, Long id, CommonUpdateResource commonUpdateResource);
}
