package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.FinancialStatementLevel;
import com.fusionx.lending.origination.resource.FinancialStatementLevelAddResourcess;
import com.fusionx.lending.origination.resource.FinancialStatementLevelUpdateResourcess; 

/**
 * Financial Statement Level Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-08-2021     FXL-356 	 FXL-591	Ishan        Created
 *    
 ********************************************************************************************************
 */

@Service
public interface FinancialStatementLevelService {

	/**
	 * Gets the all FinancialStatementLevels
	 * 
	 * @return the all FinancialStatementLevels
	 */
	List<FinancialStatementLevel> getAll();
	
	/**
	 * return the FinancialStatementLevels by Id
	 * 
	 * @param id the id
	 * @return FinancialStatementLevel
	 */
	Optional<FinancialStatementLevel> getById(Long id);
	
	/**
	 * return the FinancialStatementLevels by code
	 * 
	 * @param code the code
	 * @return FinancialStatementLevel
	 */
	Optional<FinancialStatementLevel> getByCode(String code);
	
	/**
	 * return the all FinancialStatementLevels by name
	 * 
	 * @param name the name
	 * @return FinancialStatementLevels
	 */
	List<FinancialStatementLevel> getByName(String name);
	
	/**
	 * return the all FinancialStatementLevels by status
	 * 
	 * @param status the status
	 * @return FinancialStatementLevel
	 */
	List<FinancialStatementLevel> getByStatus(String status);
	
	/**
	 * add the FinancialStatementLevel details
	 * 
	 * @param tenantId the tenant id 
	 * @param request the the object to include data
	 * @return the saved FinancialStatementLevel
	 */
	FinancialStatementLevel addFinancialStatementLevel(String tenantId, FinancialStatementLevelAddResourcess request);
	
	/**
	 * update the given FinancialStatementLevel details
	 * 
	 * @param tenantId the tenant id 
	 * @param id the FinancialStatementLevel id 
	 * @param request the the object to include data
	 * @return the after updating FinancialStatementLevel
	 */
	FinancialStatementLevel updateFinancialStatementLevel(String tenantId, Long id, FinancialStatementLevelUpdateResourcess request);
	
}
