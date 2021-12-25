package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FinancialCommitment;

/**
 * Financial Commitment Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Repository
public interface FinancialCommitmentRepository extends JpaRepository<FinancialCommitment, Long>{
	
	public List<FinancialCommitment> findByCustomerId(Long customerId);
	
	public List<FinancialCommitment> findByCustomerIdAndStatus(Long customerId, String status);

}
