package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.SalaryIncome;
import com.fusionx.lending.origination.resource.GuarantorSalaryIncomeAddResource;
import com.fusionx.lending.origination.resource.GuarantorSalaryIncomeUpdateResource;

@Service
public interface GuarantorSalaryIncomeService {


	/**
	 * Find by id.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @return the optional
	 */
	Optional<SalaryIncome> findById(String tenantId ,Long id);
	
	/**
	 * Find by id guarantor id.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @return the list
	 */
	List<SalaryIncome> findByIdGuarantorId(String tenantId ,Long id);
	

	/**
	 * Find all.
	 *
	 * @param tenantId the tenant id
	 * @return the list
	 */
	List<SalaryIncome> findAll(String tenantId );

	
	/**
	 * Find by status.
	 *
	 * @param tenantId the tenant id
	 * @param status the status
	 * @return the list
	 */
	List<SalaryIncome> findByStatus(String tenantId , String status);
	
	
	/**
	 * Save salary incomes.
	 *
	 * @param tenantId the tenant id
	 * @param guarantorSalaryIncomeAddResource the guarantor salary income add resource
	 */
	void saveSalaryIncomes(String tenantId, GuarantorSalaryIncomeAddResource guarantorSalaryIncomeAddResource);
	
	
	/**
	 * Update salary income.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @param guarantorSalaryIncomeUpdateResource the guarantor salary income update resource
	 * @return the salary income
	 */
	SalaryIncome updateSalaryIncome(String tenantId, Long id,  GuarantorSalaryIncomeUpdateResource guarantorSalaryIncomeUpdateResource);
}
