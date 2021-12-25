package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.OtherIncomeExpenseTypeDetail;
import com.fusionx.lending.origination.enums.CommonStatus;


/**
 * Other Income Expense Type Detail Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *  1   19-08-2021   	FXL-524   	 FX-542		Piyumi      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface OtherIncomeExpenseTypeDetailRepository extends JpaRepository<OtherIncomeExpenseTypeDetail, Long> {

	List<OtherIncomeExpenseTypeDetail> findByOtherIncomeExpenseTypeId(Long otherIncomeExpenseTypeId);
	
	Optional<OtherIncomeExpenseTypeDetail> findByOtherIncomeExpenseTypeIdAndExpenseTypeIdAndStatus(Long otherIncomeExpenseTypeId, Long expenseTypeId, CommonStatus status);
	
	Optional<OtherIncomeExpenseTypeDetail> findByOtherIncomeExpenseTypeIdAndExpenseTypeIdAndIdNotIn(Long otherIncomeExpenseTypeId, Long expenseTypeId,Long id);
	
	
}