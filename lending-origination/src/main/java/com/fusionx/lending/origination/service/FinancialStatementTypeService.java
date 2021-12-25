package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import com.fusionx.lending.origination.domain.FinancialStatementType;
import com.fusionx.lending.origination.resource.StatementTypeAddResource;
import com.fusionx.lending.origination.resource.StatementTypeUpdateResource;

/**
 * Statement Type Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-AUG-2021   FXL-357	      FXL-427    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
public interface FinancialStatementTypeService {
	
	
	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find all StatementType
	 * @return list of StatementType
	 */
	List<FinancialStatementType> findAll();

	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find by ID StatementType
	 * @return optional dataset of StatementType
	 */
	Optional<FinancialStatementType> findById(Long id);

	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find by CODE StatementType
	 * @return optional dataset of StatementType
	 */
	Optional<FinancialStatementType> findByCode(String code);

	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find by NAME StatementType
	 * @return list of StatementType
	 */
	List<FinancialStatementType> findByName(String name);

	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote find by STATUS StatementType
	 * @return list of StatementType
	 */
	List<FinancialStatementType> findByStatus(String status);

	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote add StatementType
	 * @return ID of StatementType
	 */
	Long validateAndSaveStatementType(String tenantId, String createdUser, StatementTypeAddResource statementTypeAddResource);

	/**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote exist by ID StatementType
	 * @return Boolean value
	 */
	Boolean existsById(Long id);

    /**
	 * @author Sanatha
	 * @since 09-AUG-2021
	 * @apiNote update StatementType
	 * @return ID of StatementType
	 */
    Long validateAndUpdateStatementType(String tenantId, String createdUser, Long id, StatementTypeUpdateResource statementTypeUpdateResource);

}
