package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.FeeChargeDetails;
import com.fusionx.lending.product.domain.LoanApplicableRange;
import com.fusionx.lending.product.domain.PenalInterestType;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Other Fee Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   15-08-2020                       	    Dilhan     Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface PenalInterestTypeRepository extends JpaRepository<PenalInterestType, Long>{

	List<PenalInterestType> findByStatus(CommonStatus status);

	Optional<PenalInterestType> findByCode(String code);
	
	List<PenalInterestType> findByNameContaining(String name);
	
	public Optional<PenalInterestType> findByIdAndNameAndStatus(Long id, String name, CommonStatus active);
}
