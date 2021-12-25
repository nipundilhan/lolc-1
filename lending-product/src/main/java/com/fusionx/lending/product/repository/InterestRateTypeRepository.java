package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.InterestRateType;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Interest Rate Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6601   	 FX-6508	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface InterestRateTypeRepository extends JpaRepository<InterestRateType, Long>  {

	Optional<InterestRateType> findByCode(String code);
	
	List <InterestRateType> findByStatus(CommonStatus status);
	
	List <InterestRateType> findByName(String name);
	
	Optional<InterestRateType> findByIdAndName(Long id, String name);

	Optional<InterestRateType> findByCodeAndIdNotIn(String code, Long id);
	
	Optional<InterestRateType> findByIdAndNameAndStatus(Long id, String name, CommonStatus status);
}
