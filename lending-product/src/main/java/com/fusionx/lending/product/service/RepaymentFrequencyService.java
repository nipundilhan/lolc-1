package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.RepaymentFrequency;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import com.fusionx.lending.product.resources.RepaymentFrequencyAddResource;
import com.fusionx.lending.product.resources.RepaymentFrequencyUpdateResource;



/**
 * Repayment Frequency Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021                            Dilki        Created
 *    
 ********************************************************************************************************
 */


@Service
public interface RepaymentFrequencyService {

	/**
	 * 
	 * Find all Repayment Frequency
	 * @author Dilki
	 * @return -JSON array of all Repayment Frequency
	 * 
	 * */
	public List<RepaymentFrequency> getAll();
	
	/**
	 * 
	 * Find Repayment Frequency by ID
	 * @author Dilki
	 * @return -JSON array of Repayment Frequency
	 * 
	 * */
	public Optional<RepaymentFrequency> getById(Long id);
	
	/**
	 * 
	 * Find Repayment Frequency by code
	 * @author Dilki
	 * @return -JSON array of Repayment Frequency
	 * 
	 * */
	public Optional<RepaymentFrequency>getByCode(String code);
	
	/**
	 * 
	 * Find Repayment Frequency by name
	 * @author Dilki
	 * @return -JSON array of Repayment Frequency
	 * 
	 * */
	public List<RepaymentFrequency> getByName(String name);
	
	/**
	 * 
	 * Find Repayment Frequency by status
	 * @author Dilki
	 * @return -JSON array of Repayment Frequency
	 * 
	 * */
	public List<RepaymentFrequency> getByStatus(String status);
	
	
	/**
	 * 
	 * Insert Repayment Frequency
	 * @author Dilki
	 * @param  - CommonAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public RepaymentFrequency addRepaymentFrequency(String tenantId , RepaymentFrequencyAddResource repaymentFrequencyAddResource);

	/**
	 * 
	 * Update Repayment Frequency
	 * @author Dilki
	 * @param  - CommonUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public RepaymentFrequency updateRepaymentFrequency(String tenantId, Long id, RepaymentFrequencyUpdateResource repaymentFrequencyUpdateResource);
}
