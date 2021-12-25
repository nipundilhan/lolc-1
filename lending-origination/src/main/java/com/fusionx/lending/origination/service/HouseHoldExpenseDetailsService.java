package com.fusionx.lending.origination.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.HouseHoldExpenseDetails;
import com.fusionx.lending.origination.resource.HouseHoldExpenseDetailsAddResource;
import com.fusionx.lending.origination.resource.HouseHoldExpenseDetailsUpdateResource;

/**
 * API Service related to House Hold Expense Details.
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
 * 1        02-09-2021      	             FXL-662    Dilhan                    Created
 * <p>
 *
 */

@Service
public interface HouseHoldExpenseDetailsService {

	/**
	 * Find by incomeSourceDetailsId.
	 *
	 * @param incomeSourceDetailsId - the incomeSourceDetailsId
	 * @return the House Hold Expense Details Object Array
	 */
	List<HouseHoldExpenseDetails> findByIncomeSourceDetailsId(Long incomeSourceDetailsId);
	
	/**
	 * Save 
	 *
	 * @param tenantId - the tenant id
	 * @param HouseHoldExpenseDetailsAddResource - the house hold expense details add resource
	 * @return 
	 */
	void saveHouseHoldExpenseDetails(String tenantId, HouseHoldExpenseDetailsAddResource houseHoldExpenseDetailsAddResource);
	
	/**
	 * update
	 *
	 * @param tenantId - the tenant id
	 * @param id - the exception type id
	 * @param HouseHoldExpenseDetailsUpdateResource - the house hold expense details update resource
	 * @return 
	 */
	void updateHouseHoldExpenseDetails(String tenantId, HouseHoldExpenseDetailsUpdateResource houseHoldExpenseDetailsUpdateResource);
}
