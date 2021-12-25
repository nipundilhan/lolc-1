package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.SalaryIncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;
/**
 * Salary Income Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   12-08-2021   FXL-338 		 FXL-532 	Dilki        Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface SalaryIncomeTypeRepository extends JpaRepository<SalaryIncomeType, Long> {

	public List<SalaryIncomeType> findByStatus(CommonStatus status);

	public List<SalaryIncomeType> findByNameContaining(String name);

	public Optional<SalaryIncomeType> findByCode(String code);

	public Optional<SalaryIncomeType> findByCodeAndIdNotIn(String code, Long id);

}
