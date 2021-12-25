package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BusinessIncomeExpense;
import com.fusionx.lending.origination.resource.GuarantorBusinessIncomeAddResource;
import com.fusionx.lending.origination.resource.GuarantorBusinessIncomeUpdateResource;

@Service
public interface GuarantorBusinessIncomeService {


	/**
	 * Find by id.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @return the optional
	 */
	Optional<BusinessIncomeExpense> findById(String tenantId ,Long id);
	
	/**
	 * Find by id guarantor id.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @return the list
	 */
	List<BusinessIncomeExpense> findByIdGuarantorId(String tenantId ,Long id);
	

	/**
	 * Find all.
	 *
	 * @param tenantId the tenant id
	 * @return the list
	 */
	List<BusinessIncomeExpense> findAll(String tenantId );

	
	/**
	 * Find by status.
	 *
	 * @param tenantId the tenant id
	 * @param status the status
	 * @return the list
	 */
	List<BusinessIncomeExpense> findByStatus(String tenantId , String status);
	
	
	/**
	 * Save business incomes.
	 *
	 * @param tenantId the tenant id
	 * @param guarantorBusinessIncomeAddResource the guarantor business income add resource
	 */
	void saveBusinessIncomes(String tenantId, GuarantorBusinessIncomeAddResource guarantorBusinessIncomeAddResource);
	
	
	/**
	 * Update business income.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @param guarantorBusinessIncomeUpdateResource the guarantor business income update resource
	 * @return the business income expense
	 */
	BusinessIncomeExpense updateBusinessIncome(String tenantId, Long id,  GuarantorBusinessIncomeUpdateResource guarantorBusinessIncomeUpdateResource);
}
