package com.fusionx.lending.origination.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BusinessSubCenter;
import com.fusionx.lending.origination.resource.BusinessSubCenterAddResource;
import com.fusionx.lending.origination.resource.BusinessSubCenterUpdateResource;
/**
 * API Service related to Business Sub Center.
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
 * 1        28-08-2021      	             FXL-649          Nipun Dilhan      Created
 * <p>
 *
 */

@Service
public interface BusinessSubCenterService {
    /**
     * Saves the business sub center
     *
     * @param tenantId                  the tenant id
     * @param businessSubCenterAddResource the object to save
     * @return the business sub center
     */	
	BusinessSubCenter create(String tenentId,BusinessSubCenterAddResource businessSubCenterAddResource); 
	
    /**
     * Updates the business sub center
     *
     * @param tenantId                     the tenant id
     * @param id               the business sub center
     * @param businessSubCenterUpdateResource the object to update
     * @param username                     the user
     * @return modified object
     */
	BusinessSubCenter update(String tenantId,Long id,BusinessSubCenterUpdateResource businessSubCenterUpdateResource);

	/**
	 * Find by business center code.
	 *
	 * @param code - the code of the business center
	 * @return the list of BusinessSubCenter
	 */
	List<BusinessSubCenter> findByBusinessCenterCode(String code) ;

}
