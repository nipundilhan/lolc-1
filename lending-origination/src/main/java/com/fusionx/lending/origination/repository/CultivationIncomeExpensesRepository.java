package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CultivationIncomeDetails;
import com.fusionx.lending.origination.domain.CultivationIncomeExpenses;
import com.fusionx.lending.origination.domain.CultivationIncomeType;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.enums.CommonStatus;

/**
 * 	Cultivation Income Documents Repository
 * 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-09-2021     	          FXL-661       Dilhan       Created
 *    
 ********************************************************************************************************
*/
@Repository
public interface CultivationIncomeExpensesRepository extends JpaRepository<CultivationIncomeExpenses, Long>{

	List<CultivationIncomeExpenses> findByStatus(CommonStatus status);
	
	List<CultivationIncomeExpenses> findByCultivationIncomeDetails(CultivationIncomeDetails cultivationIncomeDetails);
	
	List<CultivationIncomeExpenses> findAllByCultivationIncomeDetailsAndExpenseType(CultivationIncomeDetails cultivationIncomeDetails,ExpenseType expenseType);
	
	List<CultivationIncomeExpenses> findAllByCultivationIncomeDetailsAndCultivationIncomeType(CultivationIncomeDetails cultivationIncomeDetails,CultivationIncomeType cultivationIncomeType);
	
	List<CultivationIncomeExpenses> findByCultivationIncomeDetailsAndStatus(CultivationIncomeDetails cultivationIncomeDetails, CommonStatus status);
}
