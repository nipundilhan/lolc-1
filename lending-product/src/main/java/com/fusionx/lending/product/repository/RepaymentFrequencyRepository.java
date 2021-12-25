package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.RepaymentFrequency;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Repayment Frequency Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021      		      			Dilki        Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface RepaymentFrequencyRepository extends JpaRepository<RepaymentFrequency, Long> {

	public List<RepaymentFrequency> findByStatus(CommonStatus status);

	public List<RepaymentFrequency> findByNameContaining(String name);

	public Optional<RepaymentFrequency> findByCode(String code);

	public Optional<RepaymentFrequency> findByCodeAndIdNotIn(String code, Long id);

	public Optional<RepaymentFrequency> findByIdAndNameAndStatus(Long id, String name, CommonStatus status);
	
	public Optional<RepaymentFrequency> findByIdAndStatus(Long id, CommonStatus status);

}
