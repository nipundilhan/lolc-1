package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.BusinessCenterProductMap;
import com.fusionx.lending.product.enums.CommonStatus;


/**
 * Business Center Product
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-08-2021      		     FX-	   Thsmokshi      Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface BusinessCenterProductMapRepository extends JpaRepository<BusinessCenterProductMap, Long> {

	public List<BusinessCenterProductMap> findByStatus(CommonStatus commonStatus);

	public List<BusinessCenterProductMap> findByBusinessCenterId(Long id);

}
