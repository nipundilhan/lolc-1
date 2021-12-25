package com.fusionx.lending.transaction.service;

import com.fusionx.lending.transaction.domain.CoreTransactionMethod;
import com.fusionx.lending.transaction.resource.CoreTransactionAddResource;
import com.fusionx.lending.transaction.resource.CoreTransactionUpdateResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * CoreTransactionService
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   01-10-2021    FXL-1052      FXL-1001   PasinduT        Created
 * <p>
 * *******************************************************************************************************
 */


@Service
public interface CoreTransactionMethodService {


    /**
     * Find all core Tranasaction
     *
     * @return -JSON array of all core transaction
     * @author PasinduT
     */
    public List<CoreTransactionMethod> getAll();


    /**
     * Find core transaction by ID
     *
     * @return -JSON array of core transaction
     * @author PasinduT
     */
    public Optional<CoreTransactionMethod> findById(Long coreTransactionId);

    /**
     * Find core transaction by code
     *
     * @return -JSON array of Brand
     * @author PasinduT
     */
    public Optional<CoreTransactionMethod> findByCode(String code);


    /**
     * Find core transaction by name
     *
     * @return -JSON array of Brand
     * @author PasinduT
     */
    public List<CoreTransactionMethod> findByName(String name);

    /**
     * Find core transaction by status
     *
     * @return -JSON array of Brand
     * @author PasinduT
     */
    public List<CoreTransactionMethod> findByStatus(String status);


    /**
     * Insert core transaction
     *
     * @param - CommonAddResource
     * @return - Successfully saved
     * @author PasinduT
     */
    public CoreTransactionMethod addCoreTransaction(String tenantId, CoreTransactionAddResource commonAddResource);


    /**
     * Update ServiceAccessChannel
     *
     * @param - CommonUpdateResource
     * @return - Successfully saved
     * @author PasinduT
     */
    public CoreTransactionMethod updateCoreTransaction(String tenantId, Long id, CoreTransactionUpdateResource commonUpdateResource);


}
