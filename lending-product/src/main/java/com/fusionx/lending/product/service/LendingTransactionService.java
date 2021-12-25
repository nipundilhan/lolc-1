package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.LendingTransaction;
import com.fusionx.lending.product.resources.LendingTransactionAddResource;
import com.fusionx.lending.product.resources.TcCommonPaymentScheduleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Lending Transaction.
 *
 * @author Thushan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #          Date            Story Point     Task No       Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        28-10-2021          -               -           Thushan                  Created
 * <p>
 */
@Service
public interface LendingTransactionService  {

    /**
     * Returns the borrowers by lending account id
     *
     * @param lendingAccountId the id of the lending account
     * @return  the Object of lending account.
     */
    List<LendingTransaction> findByLendingAccountId(Long lendingAccountId);

    /**
     *  Gets the lending transaction by id
     *
     * @param id the id of the lending transaction
     * @return the set of lending transaction
     */
    Optional<LendingTransaction> findById(Long id);

    /**
     *Create lendingTransaction into DB
     *
     * @param tenantId the tenant id
     * @param lendingTransactionAddResource the object to save
     * @return the saved lendingTransaction.
     */
    LendingTransaction addLendingTransaction(String tenantId, LendingTransactionAddResource lendingTransactionAddResource);

    /**
     * Search lending transaction
     *
     * @param searchQ the search query
     * @param searchParam the search parameters
     * @param pageable the pageable
     * @return the pageable of lending transaction
     */
    Page<LendingTransaction> searchLendingTransactions(String searchQ, String searchParam , Pageable pageable);
    /**
     * save lending transaction details
     * @param tenantId the tenant id
     * @param lendingAccountId the lending account id
     */
    void saveLendingTransaction(String tenantId,String lendingAccountId);

    /**
     *
     * @param lendingTransaction
     * @param tcCommonPaymentScheduleResponse
     * @return LendingTransaction
     */
    LendingTransaction getLendingTransactionEntity(LendingTransaction lendingTransaction, TcCommonPaymentScheduleResponse tcCommonPaymentScheduleResponse);

    /**
     * get all lending Transaction
     *
     * @return all lending Transaction
     */
    List<LendingTransaction> getAll();
}
