package com.fusionx.lending.origination.service;
/**
 * 	Salary Income Details Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-09-2021   FXL-115  	 FXL-658       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.SalaryIncomeDetails;
import com.fusionx.lending.origination.resource.SalaryIncomeDetailsAddResource;
import com.fusionx.lending.origination.resource.SalaryIncomeDetailsUpdateResource;



@Service
public interface SalaryIncomeDetailsService {

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Salary Income Details Object
	 */
	Optional<SalaryIncomeDetails> findById(String tenantId ,Long id);
	

	/**
	 * Find all.
	 *
	 * @param tenantId the tenant id
	 * @return the list
	 */
	List<SalaryIncomeDetails> findAll(String tenantId );

	/**
	 * Find by Status.
	 *
	 * @param Status - the Status
	 * @return the Salary Income Details Object Array
	 */
	List<SalaryIncomeDetails> findByStatus(String tenantId , String status);
	
	
	/**
	 * Save 
	 *
	 * @param tenantId - the tenant id
	 * @param SalaryIncomeDetailsAddResource - the salary income details add resource
	 * @return the income source details id
	 */
	Long save(String tenantId, SalaryIncomeDetailsAddResource salaryIncomeDetailsAddResource);
	
	/**
	 * update
	 *
	 * @param tenantId - the tenant id
	 * @param id - the salary income details id
	 * @param SalaryIncomeDetailsUpdateResource - the salary income details update resource
	 * @return the Salary Income Details Object
	 */
	SalaryIncomeDetails update(String tenantId, Long id,  SalaryIncomeDetailsUpdateResource salaryIncomeDetailsUpdateResource);

	/**
	 * Find by income source details id.
	 *
	 * @param incomeSourceDetailsId - the income source details id
	 * @return the Salary Income Details Object Array
	 */
	List<SalaryIncomeDetails> findByIncomeSourceDetailsId(String tenantId , Long incomeSourceDetailsId);
}
