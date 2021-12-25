package com.fusionx.lending.product.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeDetailOptional;
import com.fusionx.lending.product.domain.FeeChargeDetailOptionalPending;
import com.fusionx.lending.product.domain.FeeChargePending;
import com.fusionx.lending.product.resources.FeeChargeDetailOptionalAddResource;
import com.fusionx.lending.product.resources.FeeChargeDetailOptionalUpdateResource;

/**
 * API Service related to Fee Charge Details Optional options.
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
 * 1        18-08-2021      -               -           Nipun Dilhan      Created
 * <p>
 *
 */

@Service
public interface FeeChargeDetailOptionalService {
	
    /**
     * Saves the Fee Charge Detail Optional
     *
     * @param tenantId                  the tenant id
     * @param feeChargeDetailOptionalAddResource the object to save
     * @return the FeeChargeDetailOptional
     */
	FeeChargeDetailOptional addFeeChargeDetailOptional(String tenentId,FeeChargeDetailOptionalAddResource feeChargeDetailOptionalAddResource);

    /**
     * Updates the Fee Charge Detail Optional
     *
     * @param tenantId                     the tenant id
     * @param FeeChargeDetailOptional               the Fee Charge Detail Optional
     * @param feeChargeDetailOptionalAddResource the object to update
     * @param username                     the user
     * @return modified object
     */
	FeeChargePending updateFeeChargeDetailsOptional(String tenantId, Long id,
			FeeChargeDetailOptionalUpdateResource feeChargeDetailOptionalUpdateResource);
	
    /**
     * Returns the FeeChargeDetailOptional by fee charge id
     *
     * @param id the of the fee charge 
     * @return the list of FeeChargeDetailOptional
     */
	List<FeeChargeDetailOptional> getAllFeeChargeOptionalByFeeChargeId(Long feeChargeId);
	
    /**
     * Returns the FeeChargeDetailOptional by  id
     *
     * @param id the id of the record
     * @return the FeeChargeDetailOptional
     */
	FeeChargeDetailOptional getDetailsById(Long id);
	
    /**
     * Returns the FeeChargeDetailOptionalPending by fee charge pending id
     *
     * @param id the of the fee charge 
     * @return the list of FeeChargeDetailOptionalPending
     */
	List<FeeChargeDetailOptionalPending> getAllDetailsByFeeChargePendingId(Long feeChargePendingId);

    /**
     * Returns the FeeChargeDetailOptional by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of FeeChargeDetailOptional
     */
	List<FeeChargeDetailOptional> findByStatus(String status);
	
    /**
     * Returns the FeeChargeDetailOptional List by category id
     *
     * @param id the of the category
     * @return the list of FeeChargeDetailOptional
     */
	List<FeeChargeDetailOptional> findByCategory(Long categoryId);
	
	List<FeeChargeDetailOptional> findAll();
	
	List<FeeChargeDetailOptional> findByOtherFeeTypeId(Long otherFeeTypeId);
	
	void approvePendingFeeChargeDetailsOptional(Long feeChargePendingId);
	
	FeeChargeDetailOptional directUpdate(String tenantId, String user,FeeChargeDetailOptionalUpdateResource feeChargeDetailOptionalUpdateResource , FeeCharge feeCharge);

}
