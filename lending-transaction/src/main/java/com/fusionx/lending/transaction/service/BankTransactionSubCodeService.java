package com.fusionx.lending.transaction.service;

/**
 * Bank Transaction Code service
 *
 * @author Nisalak
 * @Dat 29-08-2019
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   29-08-2019   FX-1678       FX-825     Nisalak      Created
 * <p>
 * *******************************************************************************************************
 */

import com.fusionx.lending.transaction.domain.BankTransactionSubCode;
import com.fusionx.lending.transaction.resource.BankTransactionSubCodeResource;
import com.fusionx.lending.transaction.resource.BankTransactionSubCodeUpdateResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface BankTransactionSubCodeService {


    /**
     * Find Bank Transaction Sub Code by Id
     * @author Nisalak
     *
     * @return        - JSON Array of Bank Transaction Sub Code objects
     *
     */
    public Optional<BankTransactionSubCode> findById(Long transSubCodeId);

    /**
     * Find Bank Transaction Sub Code by codeId
     * @author Nisalak
     *
     * @return        - JSON Array of Bank Transaction Sub Code objects
     *
     */
    public List<BankTransactionSubCode> findByBankTransactionCodeId(Long transCodeId);

    /**
     * Find Bank Transaction Sub Code by codeId and Status
     * @author Nisalak
     *
     * @return        - JSON Array of Bank Transaction Sub Code objects
     *
     */
    public List<BankTransactionSubCode> findByBankTransactionCodeIdAndStatus(Long transCodeId, String status);
    
    /**
     * Find Bank Transaction Sub Code by codeId and Description
     * @author Sanatha
     *
     * @return        - JSON Array of Bank Transaction Sub Code objects
     *
     */
    public List<BankTransactionSubCode> findByBankTransactionCodeIdAndDescription(Long transCodeId, String description);

    /**
     * Find Bank Transaction Sub Code by codeId and sub code
     * @author Nisalak
     *
     * @return        - JSON Array of Bank Transaction Sub Code objects
     *
     */
    public Optional<BankTransactionSubCode> findByBankTransactionCodeIdAndSubCode(Long transCodeId, String subCode);


    /**
     * Insert Bank Transaction Sub Code
     * @author Nisalak
     *
     * @param    - BankTransactionSubCodeAddResource
     * @return  - Successfully saved message
     *
     */
    public BankTransactionSubCode saveBankTransactionSubCode(String tenantId, BankTransactionSubCodeResource bankTransactionSubCodeResource);

    /**
     * Update Bank Transaction Sub Code
     * @author Nisalak
     *
     * @param    - BankTransactionSubCodeUpdateResource
     * @return  - Successfully saved message
     *
     */
    public BankTransactionSubCode updateBankTransactionSubCode(String tenantId, BankTransactionSubCodeUpdateResource bankTransactionSubCodeUpdateResource);

    /**
     * Get all bank transaction sub codes
     * @author Udara Liyanage
     * @since 06/10/2020 11.17AM
     * @return  - List<BankTransactionSubCode>
     */
    public List<BankTransactionSubCode> getAllBankTransactionSubCodes(String tenantId);

}
