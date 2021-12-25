package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.BusinessSubCenterProductMap;
import com.fusionx.lending.origination.resource.BusinessSubCenterProductMapAddResource;
import com.fusionx.lending.origination.resource.BusinessSubCenterProductMapUpdateResource;

/**
 * API Service related to Business Sub Center Product Mapping.
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

@Service
public interface BusinessSubCenterProductMapService {
	
    /**
     * Saves the Business Sub Center Product map
     *
     * @param tenantId                  the tenant id
     * @param Business Sub Center Product the object to save
     * @return the BusinessSubCenterProductMap
     */
	Long create(String tenantId, BusinessSubCenterProductMapAddResource businessSubCenterProductMapAddResource ,Long id);
	
    /**
     * Updates the  Business Sub Center Product map
     *
     * @param tenantId                     the tenant id
     * @param  id               the business sub center
     * @param businessSubCenterProductMapUpdateResource the object to update
     * @param username                     the user
     * @return modified object
     */
	void updateBusinessSubCenterProductMap(String tenantId,Long id,
			@Valid BusinessSubCenterProductMapUpdateResource businessSubCenterProductMapUpdateResource);
	
    /**
     * Returns the all Business Sub Center Product
     *
     * @return the list of Business Sub Center Product
     */
	List<BusinessSubCenterProductMap> findAll();
	
    /**
     * Gets the Business Sub Center Product by id
     *
     * @param id the id of the record
     * @return the Business Sub Center Product
     */
	Optional<BusinessSubCenterProductMap> findById(Long id);
	
    /**
     * Returns the Business Sub Center Product by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of Business Sub Center Product
     */
	List<BusinessSubCenterProductMap> findByStatus(String status);
	
    /**
     * Returns the Business Sub Center Product by status
     *
     * @param id the id of the business sub center
     * @return the list of Business Sub Center Product
     */
	List<BusinessSubCenterProductMap> findByBusinessSubCenterId(Long id);

}
