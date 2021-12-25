package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
public interface SalaryIncomeRepository extends JpaRepository<SalaryIncome, Long>{

	public List<SalaryIncome> findByCustomerId(Long customerId);
	
	public List<SalaryIncome> findByGuarantorId(Long guarantorId);
	
	public List<SalaryIncome> findByCustomerIdAndStatus(Long customerId, String status);
	
	public List<SalaryIncome> findByStatus(String status);
	
	public List<SalaryIncome> findByGuarantorIdAndStatus(Long guarantorId, String status);
	
	public List<SalaryIncome> findByEmployerNameAndCustomerId(String employerName, Long customerId);
	
	public List<SalaryIncome> findByEmployerNameAndKeyPersonIdAndCustomerId(String employerName, Long keyPersonId, Long customerId);
	
	public List<SalaryIncome> findByEmployerNameAndCustomerIdAndIdNotIn(String employerName, Long customerId, Long id);
	
	public List<SalaryIncome> findByEmployerNameAndKeyPersonIdAndCustomerIdAndIdNotIn(String employerName, Long keyPersonId, Long customerId, Long id);
	
	public List<SalaryIncome> findByEmployerNameAndGuarantorId(String employerName, Long guarantorId);
	
	public List<SalaryIncome> findByEmployerNameAndGuarantorIdAndIdNotIn(String employerName, Long guarantorId, Long id);
}
