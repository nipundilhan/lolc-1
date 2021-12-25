package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.AccountStatus;
import com.fusionx.lending.product.resources.AccountStatusAddResource;
import com.fusionx.lending.product.resources.AccountStatusUpdateResource;

/**
 * API Service related to Account Status.
 *
 * @author Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        15-09-2021      -               -           Dilhan      Created
 * <p>
 *
 */
@Service
public interface AccountStatusService {

    /**
     * Returns the all account status
     *
     * @return the list of account status
     */
    List<AccountStatus> getAll();

    /**
     * Gets the account status by id
     *
     * @param id the id of the record
     * @return the account status
     */
    Optional<AccountStatus> findById(Long id);

    /**
     * Returns the account status by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of account status
     */
    List<AccountStatus> findByStatus(String status);

    public Optional<AccountStatus> findByCode(String code);
    
	public List<AccountStatus> findByName(String name);
	
    /**
     * Saves the account status
     *
     * @param tenantId                  the tenant id
     * @param accountStatusAddResource the object to save
     * @return the account status
     */
    AccountStatus addAccountStatus(String tenantId, AccountStatusAddResource accountStatusAddResource);

    /**
     * Updates the account status
     *
     * @param tenantId                     the tenant id
     * @param accountStatusId               the account status id
     * @param accountStatusUpdateResource the object to update
     * @return modified object
     */
    AccountStatus updateAccountStatus(String tenantId, Long id, AccountStatusUpdateResource accountStatusUpdateResource);

}
