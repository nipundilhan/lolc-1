package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.LoanApplicableRange;
import com.fusionx.lending.product.resources.LoanApplicableRangeAddResource;
import com.fusionx.lending.product.resources.LoanApplicableRangeUpdateResource;

/**
 * Brand Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-07-2021                             Dilhan       Created
 *    
 ********************************************************************************************************
 */

@Service
public interface LoanApplicableRangeService {
	
	/**
	 * 
	 * Find all Loan Applicable Range
	 * @author Dilhan
	 * @return -JSON array of all Brand
	 * 
	 * */
	public List<LoanApplicableRange> getAll();
	
	/**
	 * 
	 * Find Loan Applicable Range by ID
	 * @author Dilhan
	 * @return -JSON array of Brand
	 * 
	 * */
	public Optional<LoanApplicableRange> getById(Long id);
	
	/**
	 * 
	 * Find Loan Applicable Range by code
	 * @author Dilhan
	 * @return -JSON array of Brand
	 * 
	 * */
	public Optional<LoanApplicableRange>getByCode(String code);
	
	/**
	 * 
	 * Find Loan Applicable Range by name
	 * @author Dilhan
	 * @return -JSON array of Brand
	 * 
	 * */
	public List<LoanApplicableRange> getByName(String name);
	
	/**
	 * 
	 * Find Loan Applicable Range by status
	 * @author Dilhan
	 * @return -JSON array of Brand
	 * 
	 * */
	public List<LoanApplicableRange> getByStatus(String status);
	
	/**
	 * 
	 * Insert Loan Applicable Range
	 * @author Dilhan
	 * @param  - LoanApplicableRangeResource
	 * @return - Successfully saved
	 * 
	 * */
	public LoanApplicableRange addLoanApplicableRange(String tenantId , LoanApplicableRangeAddResource loanApplicableRangeResource);
	
	/**
	 * 
	 * Update Loan Applicable Range
	 * @author Dilhan
	 * @param  - LoanApplicableRangeResource
	 * @return - Successfully saved
	 * 
	 * */
	public LoanApplicableRange updateLoanApplicableRange(String tenantId, Long id, LoanApplicableRangeUpdateResource loanApplicableRangeResource);

}
