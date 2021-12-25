package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.OtherIncomeExpenseType;
import com.fusionx.lending.origination.resource.OtherIncomeExpenseTypeAddResource;
import com.fusionx.lending.origination.resource.OtherIncomeExpenseTypeUpdateResource;

/**
 * Other Income Expense Type Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   19-08-2021   	FXL-524   	 FX-542		Piyumi      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface OtherIncomeExpenseTypeService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	 List<OtherIncomeExpenseType> findAll();

	
	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the OtherIncomeExpenseType
	 */
	 Optional<OtherIncomeExpenseType> findById(Long id);
	
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @return the OtherIncomeExpenseType
	 */
	 List<OtherIncomeExpenseType> findByStatus(String status);
	
	/**
	 * Save and validate Other Income Expense Type.
	 *
	 * @param tenantId - the tenant id
	 * @param OtherIncomeExpenseTypeAddResource - the Other Income Expense Type Add Resource
	 * @return the OtherIncomeExpenseType
	 */
	 void addOtherIncomeExpenseType(String tenantId, OtherIncomeExpenseTypeAddResource otherIncomeExpenseTypeAddResource);

	
	/**
	 * Update and validate Other Income Expense Type.
	 *
	 * @param tenantId - the tenant id
	 * @param id - the id
	 * @param OtherIncomeExpenseTypeUpdateResource - the other Income Expense Type Update Resource
	 * @return the OtherIncomeExpenseType
	 */
	 void updateOtherIncomeExpenseType(String tenantId, OtherIncomeExpenseTypeUpdateResource otherIncomeExpenseTypeUpdateResource);

	
}
