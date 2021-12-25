package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionx.lending.product.domain.BusinessSubCenterProductMap;
import com.fusionx.lending.product.enums.CommonStatus;


/**
 * API Service related to Business Sub Center Product Map.
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        31-08-2021      	             FXL-650          Nipun Dilhan      Created
 * <p>
 *
 */

public interface BusinessSubCenterProductMapRepository   extends JpaRepository<BusinessSubCenterProductMap, Long> {

	
	public List<BusinessSubCenterProductMap> findByStatus(CommonStatus status);

	public List<BusinessSubCenterProductMap> findByBusinessSubCenterId(Long id);
	
}
