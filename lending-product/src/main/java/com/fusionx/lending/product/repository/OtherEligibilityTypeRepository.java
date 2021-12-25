package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.OtherEligibilityType;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Other Eligibility Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-06-2020   			   	        	Thamokshi    Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface OtherEligibilityTypeRepository extends JpaRepository<OtherEligibilityType, Long> {

	List<OtherEligibilityType> findByStatus(CommonStatus commonStatus);
	
	Optional<OtherEligibilityType> findByCode(String code);

	Optional<OtherEligibilityType> findByIdAndStatus(Long stringToLong, String status);
	
	List<OtherEligibilityType> findByNameContaining(String name);

	Optional<OtherEligibilityType> findByCodeAndIdNotIn(String code, Long id);

	Optional<OtherEligibilityType> findByIdAndNameAndStatus(Long id, String otherEligibilityTypeName,
			CommonStatus active);

	Optional<OtherEligibilityType> findByCodeAndId(String code, long parseLong);

}
