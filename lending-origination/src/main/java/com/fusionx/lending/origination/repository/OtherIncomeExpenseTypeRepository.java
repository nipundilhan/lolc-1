package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CultivationCategory;
import com.fusionx.lending.origination.domain.ExpenceTypeCultivationCategory;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.domain.OtherIncomeCategory;
import com.fusionx.lending.origination.domain.OtherIncomeExpenseType;
import com.fusionx.lending.origination.enums.CommonStatus;

/**
 * Other Income Expense Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *  1   19-08-2021   	FXL-524   	 FX-542		Piyumi      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface OtherIncomeExpenseTypeRepository extends JpaRepository<OtherIncomeExpenseType, Long> {

	List<OtherIncomeExpenseType> findByStatus(CommonStatus status);

	Optional<OtherIncomeExpenseType> findByIdAndStatus(Long id, String status);
	
	Optional<OtherIncomeExpenseType> findByOtherIncomeCategoryAndExpenseType(OtherIncomeCategory otherIncomeCategory,ExpenseType expenseType);
	
	Optional<OtherIncomeExpenseType> findByOtherIncomeCategoryAndExpenseTypeAndIdNot(OtherIncomeCategory otherIncomeCategory,ExpenseType expenseType, Long id);

}