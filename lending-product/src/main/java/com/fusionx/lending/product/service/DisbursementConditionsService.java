package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.DisbursementConditions;
import com.fusionx.lending.product.resources.DisbursementConditionsAddResource;
import com.fusionx.lending.product.resources.DisbursementConditionsUpdateResource;

@Service
public interface DisbursementConditionsService {

	 /**
     * Returns the all account status
     *
     * @return the list of account status
     */
    List<DisbursementConditions> getAll();

    /**
     * Gets the account status by id
     *
     * @param id the id of the record
     * @return the account status
     */
    Optional<DisbursementConditions> findById(Long id);

    /**
     * Returns the account status by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of account status
     */
    List<DisbursementConditions> findByStatus(String status);

    public Optional<DisbursementConditions> findByCode(String code);
    
	public List<DisbursementConditions> findByName(String name);
	
    /**
     * Saves the account status
     *
     * @param tenantId                  the tenant id
     * @param accountStatusAddResource the object to save
     * @return the account status
     */
	DisbursementConditions addDisbursementConditions(String tenantId, DisbursementConditionsAddResource disbursementConditionsAddResource);

    /**
     * Updates the account status
     *
     * @param tenantId                     the tenant id
     * @param accountStatusId               the account status id
     * @param accountStatusUpdateResource the object to update
     * @return modified object
     */
	DisbursementConditions updateDisbursementConditions(String tenantId, Long id, DisbursementConditionsUpdateResource disbursementConditionsUpdateResource);

}
