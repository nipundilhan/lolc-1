package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
/**
 * 	Other Income Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-09-2021   FXL-78  	     FXL-777       Dilki        Created
 *    
 ********************************************************************************************************
*/
import com.fusionx.lending.origination.domain.OtherIncomeDetails;
import com.fusionx.lending.origination.resource.OtherIncomeDetailsAddResource;
import com.fusionx.lending.origination.resource.OtherIncomeDetailsUpdateResource;

@Service
public interface OtherIncomeDetailsService {

	/**
	 * 
	 * Find all OtherIncomeDetails
	 * 
	 * @author Dilki
	 * @return JSON array of all OtherIncomeDetails
	 * 
	 */
	public List<OtherIncomeDetails> getAll();

	/**
	 * 
	 * Find OtherIncomeDetails by ID
	 * 
	 * @author Dilki
	 * @return JSON array of OtherIncomeDetails
	 * 
	 */
	public Optional<OtherIncomeDetails> getById(Long id);

	/**
	 * 
	 * Find OtherIncomeDetails by status
	 * 
	 * @author Dilki
	 * @return JSON array of OtherIncomeDetails
	 * 
	 */
	public List<OtherIncomeDetails> getByStatus(String status);

	/**
	 * 
	 * Insert OtherIncomeDetails
	 * 
	 * @author Dilki
	 * @param OtherIncomeDetailsAddResource
	 * @return Successfully saved
	 * 
	 */
	public OtherIncomeDetails addOtherIncomeDetails(String tenantId,
			OtherIncomeDetailsAddResource approvalCategoryDetailsAddResource);

	/**
	 * 
	 * Update OtherIncomeDetails
	 * 
	 * @author Dilki
	 * @param OtherIncomeDetailsUpdateResource
	 * @return Successfully updated
	 * 
	 */
	public OtherIncomeDetails updateOtherIncomeDetails(String tenantId, Long id,
			OtherIncomeDetailsUpdateResource approvalCategoryDetailsUpdateResource);
}
