package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CultivationCategory;
import com.fusionx.lending.origination.domain.ExpenceTypeCultivationCategory;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.enums.CommonStatus;

/**
 * Expence Type Cultivation Category  Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		            	MenukaJ      Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface ExpenceTypeCultivationCategoryRepository extends JpaRepository<ExpenceTypeCultivationCategory, Long> {

	List<ExpenceTypeCultivationCategory> findByStatus(CommonStatus status);

	Optional<ExpenceTypeCultivationCategory> findByCultivationCategoryId(Long id);
	
	Optional<ExpenceTypeCultivationCategory> findByCultivationCategoryAndExpenseType(CultivationCategory cultivationCategory,ExpenseType expenseType);

	Optional<ExpenceTypeCultivationCategory> findByCultivationCategoryAndExpenseTypeAndIdNotIn(CultivationCategory cultivationCategory, ExpenseType expenseType, Long id);

	List<ExpenceTypeCultivationCategory> findByCultivationCategoryCode(String cultiCatCode);

}
