package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.LoanApplicableRange;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Loan Applicable Range Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   08-07-2021      		      			Dilhan        Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface LoanApplicableRangeRepository extends JpaRepository<LoanApplicableRange, Long> {

	Optional<LoanApplicableRange> findByCode(String code);

	List<LoanApplicableRange> findByStatus(CommonStatus status);

	List<LoanApplicableRange> findByNameContaining(String name);

	Optional<LoanApplicableRange> findByIdAndName(Long id, String name);

}
