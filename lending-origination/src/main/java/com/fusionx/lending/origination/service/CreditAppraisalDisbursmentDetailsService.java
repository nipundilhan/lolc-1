package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.CreditAppraisalDisbursmentDetails;
import com.fusionx.lending.origination.resource.CreditAppraisalDisbursmentDetailsAddResource;
import com.fusionx.lending.origination.resource.CreditAppraisalDisbursmentDetailsUpdateResource;

/**
 * CreditAppraisalDisbursmentDetails Service
 * 
 ********************************************************************************************************
 * ### Date Story Point Task No Author Description
 * -------------------------------------------------------------------------------------------------------
 * 1 06-10-2021 FXL-121 FXL-1005 PasinduT Created
 * 
 ********************************************************************************************************
 */

@Service
public interface CreditAppraisalDisbursmentDetailsService {

	/**
	 * 
	 * Find all CreditAppraisalDisbursmentDetails
	 * 
	 * @author PasinduT
	 * @return -JSON array of all CreditAppraisalDisbursmentDetails
	 * 
	 */
	public List<CreditAppraisalDisbursmentDetails> getAll();

	/**
	 * 
	 * Find CreditAppraisalDisbursmentDetails by ID
	 * 
	 * @author PasinduT
	 * @return -JSON array of CreditAppraisalDisbursmentDetails
	 * 
	 */
	public Optional<CreditAppraisalDisbursmentDetails> findById(Long creditAppraisalDisbursmentDetailsId);

	/**
	 * 
	 * Find CreditAppraisalDisbursmentDetails by name
	 * 
	 * @author PasinduT
	 * @return -JSON array of CreditAppraisalDisbursmentDetails
	 * 
	 */
	// public List<CreditAppraisalDisbursmentDetails> findByName(String name);

	/**
	 * 
	 * Find CreditAppraisalDisbursmentDetails by status
	 * 
	 * @author PasinduT
	 * @return -JSON array of CreditAppraisalDisbursmentDetails
	 * 
	 */
	public List<CreditAppraisalDisbursmentDetails> findByStatus(String status);

	/**
	 * 
	 * Insert CreditAppraisalDisbursmentDetails
	 * 
	 * @author PasinduT
	 * @param - CreditAppraisalDisbursmentDetailsAddResource
	 * @return - Successfully saved
	 * 
	 */
	public CreditAppraisalDisbursmentDetails addCreditAppraisalDisbursmentDetails(String tenantId,
			CreditAppraisalDisbursmentDetailsAddResource creditAppraisalDisbursmentDetailsAddResource);

	/**
	 * 
	 * Update CreditAppraisalDisbursmentDetails
	 * 
	 * @author PasinduT
	 * @param - CreditAppraisalDisbursmentDetailsUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public CreditAppraisalDisbursmentDetails updateCreditAppraisalDisbursmentDetails(String tenantId, Long id,
			CreditAppraisalDisbursmentDetailsUpdateResource creditAppraisalDisbursmentDetailsUpdateResource);

}
