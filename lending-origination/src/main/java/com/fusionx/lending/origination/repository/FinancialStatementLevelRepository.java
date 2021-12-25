package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FinancialStatementLevel;
import com.fusionx.lending.origination.enums.CommonStatus;
 

/**
 * Financial Statement Level Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-08-2021     FXL-356 	 FXL-591	Ishan        Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FinancialStatementLevelRepository extends JpaRepository<FinancialStatementLevel, Long> {

	List<FinancialStatementLevel> findByStatus(CommonStatus status);

    List<FinancialStatementLevel> findByNameContaining(String name);

    Optional<FinancialStatementLevel> findByCode(String code);
    
    Optional<FinancialStatementLevel> findByIdAndNameContaining(Long id, String name);
	
}
