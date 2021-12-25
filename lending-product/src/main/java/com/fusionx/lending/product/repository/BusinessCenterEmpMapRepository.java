package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.BusinessCenterEmpMap;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Business Center emp
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-08-2021      		     FX-	   Thsmokshi      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface BusinessCenterEmpMapRepository extends JpaRepository<BusinessCenterEmpMap, Long> {

	public List<BusinessCenterEmpMap> findByStatus(CommonStatus commonStatus);

	public List<BusinessCenterEmpMap> findByBusinessCenterId(Long id);

}
