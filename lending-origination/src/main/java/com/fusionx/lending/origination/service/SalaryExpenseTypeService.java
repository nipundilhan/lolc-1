package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.ExceptionApprovalGroup;
import com.fusionx.lending.origination.domain.SalaryExpenseType;
import com.fusionx.lending.origination.resource.SalaryExpenseTypeAddResource;
import com.fusionx.lending.origination.resource.SalaryExpenseTypeUpdateResource;

/**
 * Salary Expense Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-08-2021   	FXL-521   	 FX-685		Piyumi      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface SalaryExpenseTypeService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	 List<SalaryExpenseType> findAll();

	
	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Salary Expense Type
	 */
	 Optional<SalaryExpenseType> findById(Long id);
	
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the SalaryExpenseTypes
	 */
	 List<SalaryExpenseType> findByStatus(String status);
	
	/**
	 * Save Salary Expense Type.
	 *
	 * @param tenantId - the tenant id
	 * @param SalaryExpenseTypeAddResource - the Salary Expense Type Add Resource
	 * @return the Boolean
	 */
	 Boolean addSalaryExpenseType(String tenantId, SalaryExpenseTypeAddResource salaryExpenseTypeAddResource);

	
	/**
	 * Update Salary Expense Type.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param SalaryExpenseTypeUpdateResource - the Salary Expense Type Update Resource
	 * @return the SalaryExpenseType
	 */
	 SalaryExpenseType updateSalaryExpenseType(String tenantId,  Long id, SalaryExpenseTypeUpdateResource salaryExpenseTypeUpdateResource);

	/**
	* Find by code.
	*
	* @param code - the code
	* @return the Salary Expense Types
	*/
	 List<SalaryExpenseType> findByCode(String code);
	
	/**
	 * Search SalaryExpenseType.
	 *
	 * @param searchq - the searchq
	 * @param name - the name
	 * @param code - the code
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<SalaryExpenseType> search(String searchq, String name, String code,Pageable pageable);
	
}
