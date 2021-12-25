package com.fusionx.lending.transaction.service;

import com.fusionx.lending.transaction.domain.CoreTransactionMethod;
import org.springframework.stereotype.Service;


/**
 * Service Access Channel Service
 * <p>
 * *******************************************************************************************************
 * ###   Date         Story Point   Task No    Author       Description
 * -------------------------------------------------------------------------------------------------------
 * 1   04-10-2021    FXL-1052      FXL-1001   PasinduT        Created
 * <p>
 * *******************************************************************************************************
 */

@Service
public interface CoreTransactionMethodHistoryService {

    /**
     * save Core Transaction History
     *
     * @param - tenantId
     * @param - CoreTransaction
     * @param - historyCreatedUser
     * @author PasinduT
     */

    public void saveHistory(String tenantId, CoreTransactionMethod coreTransaction, String historyCreatedUser);


}
