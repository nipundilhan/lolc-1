package com.fusionx.lending.origination.service;
/**
 * 	Business Income Expenses Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-09-2021   FXL-115  	 FXL-785       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BusinessIncomeExpenses;
import com.fusionx.lending.origination.resource.BusinessIncomeExpenseTypeResource;
import com.fusionx.lending.origination.resource.BusinessIncomeExpensesAddResource;



@Service
public interface BusinessIncomeExpensesService {

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Business Income Expenses Object
	 */
	Optional<BusinessIncomeExpenses> findById(Long id);
	

	List<BusinessIncomeExpenses> findAll();

	/**
	 * Find by Status.
	 *
	 * @param Status - the Status
	 * @return the Business Income Expenses Object Array
	 */
	List<BusinessIncomeExpenses> findByStatus(String status);
	
	
	/**
	 * Save 
	 *
	 * @param tenantId - the tenant id
	 * @param BusinessIncomeExpensesAddResource - the business income expenses add resource
	 * @return the Boolean
	 */
	Boolean save(String tenantId, BusinessIncomeExpensesAddResource businessIncomeExpensesAddResource);
	
	/**
	 * update
	 *
	 * @param tenantId - the tenant id
	 * @param id - the business income expenses id
	 * @param BusinessIncomeExpenseTypeResource - the business income expenses type resource
	 * @return the Business Income Expenses Object
	 */
	BusinessIncomeExpenses update(String tenantId, Long id,  BusinessIncomeExpenseTypeResource businessIncomeExpenseTypeResource);

	/**
	 * Find by  business income details id.
	 *
	 * @param businessIncomeDetailsId - the business income details id
	 * @return the Business Income Expenses Object Array
	 */
	List<BusinessIncomeExpenses> findByBusinessIncomeDetailsId( Long businessIncomeDetailsId);
}
