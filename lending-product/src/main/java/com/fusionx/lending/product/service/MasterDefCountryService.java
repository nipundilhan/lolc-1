package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;
import com.fusionx.lending.product.resources.MasterDefCountryMainAddResource;
import com.fusionx.lending.product.resources.MasterDefCountryMainUpdateResource;
import com.fusionx.lending.product.resources.MasterDefLocationDetailsPendingReponse;
import com.fusionx.lending.product.resources.MasterDefLocationDetailsReponse;
/**
 * API Service related to Master defintion country mapping.
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
 * 1        10-09-2021      -               FXL-775     Nipun Dilhan      Created
 * <p>
 *
 */

@Service
public interface MasterDefCountryService {
	
    /**
     * Saves the master definition country mapping
     *
     * @param tenantId                  the tenant id
     * @param masterDefCountryMainAddResource the object to save
     * @return the master definitinon
     */
	MasterDefinition create(MasterDefCountryMainAddResource masterDefCountryMainAddResource ,Long masterDefinitionId,String user,String tenantId);
	
    /**
     * Gets the country mapping details  by master definition id
     *
     * @param id the id of the master definition
     * @return the MasterDefLocationDetailsReponse
     */
	MasterDefLocationDetailsReponse findByMasterDefinitionId(Long masterDefinitionId);

    /**
     * Gets the country mapping details  by master definition pending id
     *
     * @param id the id of the master definition
     * @return the MasterDefLocationDetailsReponse
     */
	MasterDefLocationDetailsPendingReponse findByMasterDefinitionPendingId(Long masterDefinitionPendingId);
		
    /**
     * Updates the master definition country mapping
     *
     * @param tenantId                     the tenant id
     * @param id               the  master definition id
     * @param masterDefCountryMainUpdateResource the object to update
     * @param username                     the user
     * @return modified object
     */
	MasterDefinitionPending updateMasterDefinitionCountry(String tenantId, String user,Long id , MasterDefCountryMainUpdateResource masterDefCountryMainUpdateResource);
	
	
	
	//tempory method
	MasterDefinitionPending updateMasterDefCountryUsingPending(Long masterDefinitionPendingId);
}
