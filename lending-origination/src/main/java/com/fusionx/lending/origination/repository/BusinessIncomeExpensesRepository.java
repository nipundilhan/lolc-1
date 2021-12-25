package com.fusionx.lending.origination.repository;
/**
 * 	Business Income Expenses Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-09-2021   FXL-115  	 FXL-785       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BusinessIncomeExpenses;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface BusinessIncomeExpensesRepository extends JpaRepository<BusinessIncomeExpenses, Long> {

	List<BusinessIncomeExpenses> findByStatus(CommonStatus status);

	List<BusinessIncomeExpenses> findByBusinessIncomeDetailId(Long businessIncomeDetailId);

	List<BusinessIncomeExpenses> findByBusinessIncomeDetailIdAndStatus(Long businessIncomeDetailId, CommonStatus status);

	
}
