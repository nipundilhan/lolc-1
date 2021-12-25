package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.BusinessCenter;


/**
 * Business Center
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-08-2021      		     FX-	   Thsmokshi      Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface BusinessCenterRepository extends JpaRepository<BusinessCenter, Long> {

	List<BusinessCenter> findByStatus(String status);

	List<BusinessCenter> findByNameContaining(String name);

	Optional<BusinessCenter> findByCode(String code);

	boolean existsByCodeAndStatus(String code, String string);

	Optional<BusinessCenter> findByCodeAndIdNotIn(String code, Long id);

}
