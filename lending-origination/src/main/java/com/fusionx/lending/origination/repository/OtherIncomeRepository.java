package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.OtherIncome;
import com.fusionx.lending.origination.domain.SalaryIncome;

/**
 * Salary Income Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Repository
public interface OtherIncomeRepository extends JpaRepository<OtherIncome, Long>{

	public List<OtherIncome> findByCustomerId(Long customerId);
	
	public List<OtherIncome> findByGuarantorId(Long guarantorId);
	
	public List<OtherIncome> findByCustomerIdAndStatus(Long customerId, String status);
	
	public List<OtherIncome> findByGuarantorIdAndStatus(Long guarantorId, String status);
	
	public List<OtherIncome> findByCustomerIdAndOtherIncomeTypeId(Long customerId, Long otherIncomeTypeId);
	
	public List<OtherIncome> findByCustomerIdAndOtherIncomeTypeIdAndIdNotIn(Long customerId, Long otherIncomeTypeId, Long id);
	
	public List<OtherIncome> findByGuarantorIdAndOtherIncomeTypeId(Long guarantorId, Long otherIncomeTypeId);
	
	public List<OtherIncome> findByGuarantorIdAndOtherIncomeTypeIdAndIdNotIn(Long guarantorId, Long otherIncomeTypeId, Long id);
	
	List<OtherIncome> findByStatus(String status);
}
