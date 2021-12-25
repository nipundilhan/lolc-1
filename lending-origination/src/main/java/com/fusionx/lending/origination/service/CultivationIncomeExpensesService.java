package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.CultivationIncomeExpenses;
import com.fusionx.lending.origination.resource.CultivationIncomeExpensesAddResource;
import com.fusionx.lending.origination.resource.CultivationIncomeExpensesUpdateResource;
/**
 * API Service related to Cultivation Income Expenses.
 *
 * @author Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        09-09-2021      	             FXL-661    Dilhan                    Created
 * <p>
 *
 */
@Service
public interface CultivationIncomeExpensesService {

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Cultivation Income Expenses Object
	 */
	Optional<CultivationIncomeExpenses> findById(String tenantId ,Long id);
	

	List<CultivationIncomeExpenses> findAll(String tenantId );

	/**
	 * Find by Status.
	 *
	 * @param Status - the Status
	 * @return the Cultivation Income Expenses Object Array
	 */
	List<CultivationIncomeExpenses> findByStatus(String tenantId , String status);
	
	/**
	 * Find by cultivation income details id.
	 *
	 * @param cultivationIncomeDetailsId - the cultivation income details id
	 * @return the Cultivation Income Expenses Object Array
	 */
	List<CultivationIncomeExpenses> findByCultivationIncomeDetailsId(String tenantId , Long incomeSourceDetailsId);
	
	/**
	 * Save 
	 *
	 * @param tenantId - the tenant id
	 * @param CultivationIncomeExpensesAddResource - the cultivation income expenses add resource
	 * @return 
	 */
	void saveCultivationIncomeExpenses(String tenantId,
			CultivationIncomeExpensesAddResource cultivationIncomeExpensesAddResource);

	/**
	 * update
	 *
	 * @param tenantId - the tenant id
	 * @param id - the salary income details id
	 * @param CultivationIncomeExpensesUpdateResource - the cultivation income expenses update resource
	 * @return the Cultivation Income Expenses Object
	 */
	CultivationIncomeExpenses updateCultivationIncomeExpenses(String tenantId,Long cultivationIncomeExpenseId,
			CultivationIncomeExpensesUpdateResource cultivationIncomeExpensesUpdateResource);

	/**
	 * Find by cultivation income details id.
	 *
	 * @param cultivationIncomeDetailsId - the cultivation income details id
	 * @return the Cultivation Income Expenses Object Array
	 */
	List<CultivationIncomeExpenses> getCultivationIncomeDetails(Long id);
	
	/**
	 * Find by cultivation income details id.
	 *
	 * @param cultivationIncomeDetailsId - the cultivation income details id
	 * @return the Cultivation Income Expenses Object Array
	 */
	List<CultivationIncomeExpenses> getCultivationExpenseDetails(Long id);
}
