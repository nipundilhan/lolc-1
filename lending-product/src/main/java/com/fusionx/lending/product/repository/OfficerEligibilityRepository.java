package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.OfficerEligibility;
import com.fusionx.lending.product.enums.CommonStatus;

/**
* Officer Eligibility Repository
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   09-06-2020   			   	        	Thamokshi    Created
*    
********************************************************************************************************
*/

@Repository
public interface OfficerEligibilityRepository extends JpaRepository<OfficerEligibility, Long>{

	List<OfficerEligibility> findByStatus(CommonStatus status);
	
	List<OfficerEligibility> findByOfficerTypeId(Long officerTypeId);

	Optional<OfficerEligibility> findByIdAndStatus(Long officerEligibilityId, CommonStatus status);
	
	Optional<OfficerEligibility> findByCode(String code);

	Optional<OfficerEligibility> findByCodeAndId(String code, Long id);
}
