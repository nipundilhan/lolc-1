package com.fusionx.lending.origination.service;

/**
 * 	Other Income Expenses
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-09-2021   FXL-641  	 FXL-792       Dilki        Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.OtherIncomeExpenses;
import com.fusionx.lending.origination.resource.OtherIncomeExpensesResource;
import com.fusionx.lending.origination.resource.OtherIncomeExpensesAddResource;

@Service
public interface OtherIncomeExpensesService {

	/**
	 * Find by id.
	 *
	 * @param id
	 * @return the Other Income Expenses Object
	 */
	Optional<OtherIncomeExpenses> findById(Long id);

	List<OtherIncomeExpenses> findAll();

	/**
	 * Find by Status.
	 *
	 * @param Status
	 * @return the Other Income Expenses Object Array
	 */
	List<OtherIncomeExpenses> findByStatus(String status);

	/**
	 * Save
	 *
	 * @param tenantId
	 * @param OtherIncomeExpensesAddResource
	 * @return the Boolean
	 */
	void saveOtherIncome(String tenantId, OtherIncomeExpensesAddResource otherIncomeExpensesAddResource);

	/**
	 * update
	 *
	 * @param tenantId
	 * @param  other income expenses id
	 * @param OtherIncomeExpenseTypeResource
	 * @return the Other Income Expenses Object
	 */
	OtherIncomeExpenses update(String tenantId, Long id, OtherIncomeExpensesResource otherIncomeExpensesResource);

	/**
	 * Find by other income details id.
	 *
	 * @param otherIncomeDetailsId
	 * @return the Other Income Expenses Object Array
	 */
	List<OtherIncomeExpenses> findByOtherIncomeDetailsId(Long otherIncomeDetailsId);
}
