package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.EligibilityCustomerType;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * EligibilityCustomerTypeRepository
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   28-07-2021    FXL_365  			FXL-56			Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Repository
public interface EligibilityCustomerTypeRepository extends JpaRepository<EligibilityCustomerType, Long>{

	List<EligibilityCustomerType> findByEligibilityId(Long eligibilityId);

	Optional<EligibilityCustomerType> findByEligibilityIdAndId(Long eligibilityId, Long id);

	List<EligibilityCustomerType> findByStatus(CommonStatus active);

	List<EligibilityCustomerType> findByCustomerTypeId(Long customerTypeId);

	Optional<EligibilityCustomerType> findByCustomerTypeIdAndEligibilityIdAndStatusAndIdNotIn(Long customerTypeId, Long eligibilityId, CommonStatus active, Long id);

	Optional<EligibilityCustomerType> findByCustomerTypeIdAndEligibilityIdAndStatus(Long customerTypeId, Long eligibilityId, CommonStatus active);

}
