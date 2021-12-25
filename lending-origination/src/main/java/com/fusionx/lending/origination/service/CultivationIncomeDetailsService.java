package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.CultivationIncomeDetails;
import com.fusionx.lending.origination.resource.CultivationIncomeDetailsAddResource;
import com.fusionx.lending.origination.resource.CultivationIncomeDetailsUpdateResource;

@Service
public interface CultivationIncomeDetailsService {

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @return the Cultivation Income Details Object
	 */
	Optional<CultivationIncomeDetails> findById(String tenantId ,Long id);
	

	List<CultivationIncomeDetails> findAll(String tenantId );

	/**
	 * Find by Status.
	 *
	 * @param Status - the Status
	 * @return the Cultivation Income Details Object Array
	 */
	List<CultivationIncomeDetails> findByStatus(String tenantId , String status);
	
	
	/**
	 * Save 
	 *
	 * @param tenantId - the tenant id
	 * @param CultivationIncomeDetailsAddResource - the cultivation income details add resource
	 * @return the income source details id
	 */
	Long saveCultivationIncomeDetails(String tenantId, CultivationIncomeDetailsAddResource cultivationIncomeDetailsAddResource);
	
	/**
	 * update
	 *
	 * @param tenantId - the tenant id
	 * @param id - the exception type id
	 * @param CultivationIncomeDetailsUpdateResource - the cultivation income details update resource
	 * @return the Cultivation Income Details Object
	 */
	CultivationIncomeDetails updateCultivationIncomeDetails(String tenantId, Long id,  CultivationIncomeDetailsUpdateResource cultivationIncomeDetailsUpdateResource);

	/**
	 * Find by income source details id.
	 *
	 * @param incomeSourceDetailsId - the income source details id
	 * @return the Cultivation Income Details Object Array
	 */
	List<CultivationIncomeDetails> findByIncomeSourceDetailsId(String tenantId , Long incomeSourceDetailsId);
}
