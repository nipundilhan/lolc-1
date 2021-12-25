package com.fusionx.lending.origination.repository;

/**
 * 	Other Income Expenses
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-09-2021   FXL-641  	 FXL-792       Dilki        Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.OtherIncomeDetails;
import com.fusionx.lending.origination.domain.OtherIncomeExpenses;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface OtherIncomeExpensesRepository extends JpaRepository<OtherIncomeExpenses, Long> {

	List<OtherIncomeExpenses> findByStatus(CommonStatus status);

	List<OtherIncomeExpenses> findByOtherIncomeDetailsId(Long otherIncomeDetailsId);

	List<OtherIncomeExpenses> findByOtherIncomeDetailsIdAndStatus(OtherIncomeDetails otherIncomeDetails, CommonStatus status);

}
