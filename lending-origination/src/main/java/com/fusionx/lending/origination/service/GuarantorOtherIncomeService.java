package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.OtherIncome;
import com.fusionx.lending.origination.domain.SalaryIncome;
import com.fusionx.lending.origination.resource.GuarantorOtherIncomeAddResource;
import com.fusionx.lending.origination.resource.GuarantorOtherIncomeUpdateResource;
import com.fusionx.lending.origination.resource.GuarantorSalaryIncomeAddResource;
import com.fusionx.lending.origination.resource.GuarantorSalaryIncomeUpdateResource;

@Service
public interface GuarantorOtherIncomeService {

	/**
	 * Find by id.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @return the optional
	 */
	Optional<OtherIncome> findById(String tenantId ,Long id);
	
	/**
	 * Find by id guarantor id.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @return the list
	 */
	List<OtherIncome> findByIdGuarantorId(String tenantId ,Long id);
	

	/**
	 * Find all.
	 *
	 * @param tenantId the tenant id
	 * @return the list
	 */
	List<OtherIncome> findAll(String tenantId );

	
	/**
	 * Find by status.
	 *
	 * @param tenantId the tenant id
	 * @param status the status
	 * @return the list
	 */
	List<OtherIncome> findByStatus(String tenantId , String status);
	
	
	/**
	 * Save other incomes.
	 *
	 * @param tenantId the tenant id
	 * @param guarantorOtherIncomeAddResource the guarantor other income add resource
	 */
	void saveOtherIncomes(String tenantId, GuarantorOtherIncomeAddResource guarantorOtherIncomeAddResource);
	
	
	/**
	 * Update other income.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @param guarantorOtherIncomeUpdateResource the guarantor other income update resource
	 * @return the salary income
	 */
	OtherIncome updateOtherIncome(String tenantId, Long id,  GuarantorOtherIncomeUpdateResource guarantorOtherIncomeUpdateResource);
}
