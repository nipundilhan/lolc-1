package com.fusionx.lending.origination.service;
/**
 * Other Income Category Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-639  	 FXL-535       Piyumi     Created
 *    
 ********************************************************************************************************
*/
import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.OtherIncomeCategory;
import com.fusionx.lending.origination.resource.OtherIncomeCategoryAddResource;
import com.fusionx.lending.origination.resource.OtherIncomeCategoryUpdateResource;



@Service
public interface OtherIncomeCategoryService {
	
	/**
	 * Save 
	 *
	 * @param tenantId - the tenant id
	 * @param OtherIncomeCategoryAddResource - the other income category add resource
	 * @return the Other Income Category Object
	 */
	OtherIncomeCategory save(String tenantId, OtherIncomeCategoryAddResource otherIncomeCategoryAddResource);

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Other Income Category Object
	 */
	Optional<OtherIncomeCategory> findById(Long id);
	

	List<OtherIncomeCategory> findAll();

	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the Other Income Category Object
	 */
	Optional<OtherIncomeCategory> findByCode(String code);

	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the Other Income Category Object Array
	 */
	List<OtherIncomeCategory> findByName(String name);

	/**
	 * Find by code.
	 *
	 * @param code - the code
	 * @return the Other Income Category Object Array
	 */
	List<OtherIncomeCategory> findByStatus(String status);

	/**
	 * update
	 *
	 * @param tenantId - the tenant id
	 * @param id - the other income category
	 * @param OtherIncomeCategoryUpdateResource - the other income category update resource
	 * @return the Other Income Category Object
	 */
	OtherIncomeCategory update(String tenantId, Long id,  OtherIncomeCategoryUpdateResource otherIncomeCategoryUpdateResource);

}
