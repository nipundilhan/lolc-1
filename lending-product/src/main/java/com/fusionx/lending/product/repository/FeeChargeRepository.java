package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.ResidencyEligibility;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Fee Charge Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-06-2021      		      			MenukaJ      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FeeChargeRepository extends JpaRepository<FeeCharge, Long> {

	public List<FeeCharge> findByStatus(CommonStatus status);

	public List<FeeCharge> findByNameContaining(String name);

	public Optional<FeeCharge> findByCode(String code);

	public Optional<FeeCharge> findByCodeAndIdNotIn(String code, Long id);
	
	public Optional<FeeCharge> findByIdAndStatus(Long feeChargeId, CommonStatus status);
	
	public Optional<FeeCharge> findByIdAndNameAndStatus(Long feeChargeId,String name, CommonStatus status);


}
