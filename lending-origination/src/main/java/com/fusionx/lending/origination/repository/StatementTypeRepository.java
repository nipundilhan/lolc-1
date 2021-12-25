package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionx.lending.origination.domain.FinancialStatementType;
/**
 * Statement Type Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-AUG-2021   FXL-357	      FXL-427    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
public interface StatementTypeRepository extends JpaRepository<FinancialStatementType, Long>{
	
	 Optional<FinancialStatementType> findByCode(String code);

	    List<FinancialStatementType> findByNameContaining(String name);

	    List<FinancialStatementType> findByStatus(String status);

	    Boolean existsByCodeAndStatus(String code, String status);

	    Optional<FinancialStatementType> findByCodeAndIdNotIn(String code, Long id);
	    
	    Optional<FinancialStatementType> findByIdAndNameAndStatus(Long id, String name, String status);

}
