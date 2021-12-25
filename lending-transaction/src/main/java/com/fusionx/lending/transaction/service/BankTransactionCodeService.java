package com.fusionx.lending.transaction.service;

/**
 * Bank Transaction Code service
 *
 * @author Nisalak
 * @Dat 28-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   28-08-2019   FX-1678       FX-825     Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

import com.fusionx.lending.transaction.domain.BankTransactionCode;
import com.fusionx.lending.transaction.resource.BankTransactionCodeResource;
import com.fusionx.lending.transaction.resource.BankTransactionCodeUpdateResource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public interface BankTransactionCodeService {

    /**
     * Find all Bank Transaction Code
     * @author Nisalak
     *
     * @return        - JSON Array of all Merchant Detail objects
     *
     */
    public List<BankTransactionCode> findAll();

    /**
     * Find Bank Transaction Code by Id
     * @author Nisalak
     *
     * @return        - JSON Array of Bank Transaction Code objects
     *
     */
    public Optional<BankTransactionCode> findById(Long transCodeId);

    /**
     * Find Bank Transaction Code by Status
     * @author Nisalak
     *
     * @return        - JSON Array of Bank Transaction Code objects
     *
     */
    public Optional<Collection<BankTransactionCode>> findByStatus(String status);
    
    /**
     * Find Bank Transaction Code by Description
     * @author Sanatha
     *
     * @return        - JSON Array of Bank Transaction Code objects
     *
     */
    public Optional<Collection<BankTransactionCode>> findByDescription(String description);

    /**
     * Find Bank Transaction Code
     * @author Nisalak
     *
     * @return        - JSON Array of Bank Transaction Code objects
     *
     */
    public Optional<BankTransactionCode> findByCode(String code);


    /**
     * Insert Bank Transaction Code
     * @author Nisalak
     *
     * @param    - BankTransactionCodeAddResource
     * @return  - Successfully saved message
     *
     */
    public BankTransactionCode saveBankTransactionCode(String tenantId, BankTransactionCodeResource bankTransactionCodeResource);

    /**
     * Update Bank Transaction Code
     * @author Nisalak
     *
     * @param    - BankTransactionCodeUpdateResource
     * @return  - Successfully saved message
     *
     */
    public BankTransactionCode updateBankTransactionCode(String tenantId, BankTransactionCodeUpdateResource bankTransactionCodeUpdateResource);

}
