package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.SalaryExpenseType;
import com.fusionx.lending.origination.enums.CommonStatus;


/**
 * Salary Expense Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *  1   30-08-2021   	FXL-521   	 FX-685		Piyumi      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface SalaryExpenseTypeRepository extends JpaRepository<SalaryExpenseType, Long> {

	List<SalaryExpenseType> findByStatus(CommonStatus status);
	
	Optional<SalaryExpenseType> findByExpenseTypeIdAndStatusAndIdNotIn(Long expenseTypeId, CommonStatus status, Long id);
	
	List<SalaryExpenseType> findByExpenseTypeId(Long expenseTypeId);

	Optional<SalaryExpenseType> findByExpenseTypeIdAndStatus(Long expenseTypeId, CommonStatus status);
	
	
	
}