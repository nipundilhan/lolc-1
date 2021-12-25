package com.fusionx.lending.origination.repository;
/**
 * 	Analyst Exception Details Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-08-2021   FXL-117  	 FXL-543       Piyumi     Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.AnalystExceptionDetails;
import com.fusionx.lending.origination.enums.AnalystExceptionApprovalStatus;
import com.fusionx.lending.origination.enums.CommonStatus;



@Repository
public interface AnalystExceptionDetailsRepository extends JpaRepository<AnalystExceptionDetails, Long>{
	
	List<AnalystExceptionDetails> findByAnalystDetailId(Long analystDetailId);
	
	Optional<AnalystExceptionDetails> findByAnalystDetailIdAndId(Long analystDetailId, Long id);
	
	Optional<AnalystExceptionDetails> findByAnalystDetailIdAndExceptionTypeId(Long analystDetailId, long exceptionTypeId);
	
	Optional<AnalystExceptionDetails> findByAnalystDetailIdAndExceptionTypeIdAndStatusAndIdNotIn(Long analystDetailId, long exceptionTypeId, CommonStatus status, Long id);

	List<AnalystExceptionDetails> findByAnalystDetailIdAndApprovalStatus(Long analystDetailId, AnalystExceptionApprovalStatus status);
}
