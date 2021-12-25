package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.DisbursementDetails;

/**
 * Disbursement Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6484    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Repository
public interface DisbursementDetailsRepository extends JpaRepository<DisbursementDetails, Long>{
	
	public List<DisbursementDetails> findByStatus(String status);
	
	public List<DisbursementDetails> findByCustomerId(Long customerId);
	
	public List<DisbursementDetails> findByLeadId(Long leadId);
	
	public Optional<DisbursementDetails> findByCustomerIdAndBankIdAndBankBranchIdAndAccount(Long customerId, Long bankId, Long bankBranchId, String accountNo);

}
