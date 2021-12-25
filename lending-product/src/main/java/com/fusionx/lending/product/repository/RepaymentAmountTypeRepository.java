package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.RepaymentAmountType;
import com.fusionx.lending.product.enums.CommonStatus;


/**
 * Repayment Amount Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   	Task No    	Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-06-2021     FX-6619 		FX-6669     RavishikaS      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface RepaymentAmountTypeRepository extends JpaRepository<RepaymentAmountType, Long> {
	
	public List<RepaymentAmountType> findByStatus(CommonStatus status);

	public Optional<RepaymentAmountType> findByCode(String code);

	public Optional<RepaymentAmountType> findByCodeAndIdNotIn(String code, Long id);

	public Optional<RepaymentAmountType> findByIdAndNameAndStatus(Long id, String name, CommonStatus status);

}
