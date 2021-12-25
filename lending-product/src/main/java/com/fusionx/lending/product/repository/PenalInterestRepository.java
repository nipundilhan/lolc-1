package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

/**
 * Credit Interest Service - Repository
 * @author 	Amal
 * @Date    21-08-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   21-08-2019   FX-1508      FX-1600    Amal      Created
 *    
 ********************************************************************************************************
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.PenalInterest;
import com.fusionx.lending.product.enums.CommonStatus;


@Repository
public interface PenalInterestRepository extends JpaRepository<PenalInterest, Long> {
	
	List<PenalInterest> findByStatus(CommonStatus status);

	Optional<PenalInterest> findByCode(String code);
	
	Optional<PenalInterest>findByIdAndStatus(Long id, CommonStatus status);

	Optional<PenalInterest>findByCodeAndStatus(String code, CommonStatus status);
		
	Optional<PenalInterest>findByCodeAndId(String code, Long id);
	
	Optional<PenalInterest>findByCodeAndIdNotIn(String code, Long id);
	
	Optional<PenalInterest> findByIdAndNameAndStatus(Long id, String name, CommonStatus status);
	
	List<PenalInterest> findByNameContaining(String name);

}
