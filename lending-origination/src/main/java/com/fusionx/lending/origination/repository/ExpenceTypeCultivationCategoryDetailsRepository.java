package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ExpenceTypeCultivationCategoryDetails;

/**
 * Expence Type Cultivation Category Details Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		            	MenukaJ      Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface ExpenceTypeCultivationCategoryDetailsRepository extends JpaRepository<ExpenceTypeCultivationCategoryDetails, Long> {

	List<ExpenceTypeCultivationCategoryDetails> findByExpenceTypeCultivationCategoryId(Long id);

	Optional<ExpenceTypeCultivationCategoryDetails> findByExpenceTypeCultivationCategoryIdAndExpenseTypeIdAndExpenseTypeName(
			Long id, Long expenseTypeId, String expenseTypeName);

	Optional<ExpenceTypeCultivationCategoryDetails> findByExpenceTypeCultivationCategoryIdAndExpenseTypeId(Long id,
			Long expenseTypeId);

}
