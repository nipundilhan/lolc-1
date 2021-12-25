package com.fusionx.lending.origination.repository;
/**
 * 	Income Source Details Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   31-08-2021   FXL-115  	 FXL-656       Piyumi       Created
 *    
 ********************************************************************************************************
*/


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.IncomeSourceDetails;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.IncomeTypeEnum;
import com.fusionx.lending.origination.enums.SourceTypeEnum;



@Repository
public interface IncomeSourceDetailsRepository extends JpaRepository<IncomeSourceDetails, Long>{

	List<IncomeSourceDetails> findByStatus(CommonStatus status);

	List<IncomeSourceDetails> findByLeadId(Long leadId);

	List<IncomeSourceDetails> findByCustomerId(Long customerId);

	List<IncomeSourceDetails> findByLinkedPersonId(Long linkedPersonId);

	Optional<IncomeSourceDetails> findByLeadIdAndSourceTypeAndStatus(Long leadId, SourceTypeEnum source, CommonStatus status);
	
	Optional<IncomeSourceDetails> findByCustomerIdAndSourceTypeAndStatus(Long customerId, SourceTypeEnum source, CommonStatus status);
	
	Optional<IncomeSourceDetails> findByLinkedPersonIdAndSourceTypeAndStatus(Long linkedPersonId, SourceTypeEnum source, CommonStatus status);

	Optional<IncomeSourceDetails> findByLeadIdAndSourceTypeAndStatusAndIdNotIn(Long leadId, SourceTypeEnum source,
			CommonStatus status, Long id);

	Optional<IncomeSourceDetails> findByCustomerIdAndSourceTypeAndStatusAndIdNotIn(Long customerId, SourceTypeEnum source,
			CommonStatus status, Long id);

	Optional<IncomeSourceDetails> findByLinkedPersonIdAndSourceTypeAndStatusAndIdNotIn(Long linkedPersonId, SourceTypeEnum source,
			CommonStatus status, Long id);
	
	Optional<IncomeSourceDetails> findByIdAndStatus(Long id, CommonStatus status);

	List<IncomeSourceDetails> findByCustomerIdAndIncomeTypeAndStatus(Long custormerId, IncomeTypeEnum business,CommonStatus status);
	
	List<IncomeSourceDetails> findByLeadIdAndIncomeTypeAndStatus(Long leadId, IncomeTypeEnum business,CommonStatus status);

	List<IncomeSourceDetails> findByLinkedPersonIdAndIncomeTypeAndStatus(Long linkedPersonId, IncomeTypeEnum business,CommonStatus status);

	List<IncomeSourceDetails> findByCustomerIdAndStatus(Long custormerId, CommonStatus status);

	List<IncomeSourceDetails> findByLinkedPersonIdAndStatus(Long linkedPersonId, CommonStatus status);

	List<IncomeSourceDetails> findByLeadIdAndStatus(Long leadId, CommonStatus status);
}
