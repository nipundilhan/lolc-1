package com.fusionx.lending.transaction.service.impl;

import com.fusionx.lending.transaction.base.MessagePropertyBase;
import com.fusionx.lending.transaction.domain.CoreTransactionHistory;
import com.fusionx.lending.transaction.domain.CoreTransactionMethod;
import com.fusionx.lending.transaction.enums.CoreTransactionMethodCode;
import com.fusionx.lending.transaction.repo.CoreTransactionHistoryRepository;
import com.fusionx.lending.transaction.service.CoreTransactionMethodHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

/**
 * Core Transaction Service Impl
 * <p>
 * *******************************************************************************************************
 * ### Date Story Point Task No Author Description
 * -------------------------------------------------------------------------------------------------------
 * 1 01-10-2021  FXL-1052 FXL-1001	Pasindu Thanthree 	Created
 * <p>
 * *******************************************************************************************************
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CoreTransactionMethodHistoryServiceImpl extends MessagePropertyBase implements CoreTransactionMethodHistoryService {

    @Autowired
    private CoreTransactionHistoryRepository coreTransactionHistoryRepository;

    //@Override
    public void saveHistory(String tenantId,
                            CoreTransactionMethod coreTransaction, String historyCreatedUser) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        CoreTransactionHistory coreTransactionHistory = new CoreTransactionHistory();

        coreTransactionHistory.setTenantId(tenantId);
        coreTransactionHistory.setCoreTransactionId(coreTransaction.getId());      
        coreTransactionHistory.setCode(coreTransaction.getCode());
        coreTransactionHistory.setName(coreTransaction.getName());
        coreTransactionHistory.setDescription(coreTransaction.getDescription());
        coreTransactionHistory.setStatus(coreTransaction.getStatus());
        coreTransactionHistory.setCreatedDate(coreTransaction.getCreatedDate());
        coreTransactionHistory.setCreatedUser(coreTransaction.getCreatedUser());
        coreTransactionHistory.setModifiedDate(coreTransaction.getModifiedDate());
        coreTransactionHistory.setModifiedUser(coreTransaction.getModifiedUser());
        coreTransactionHistory.setVersion(coreTransaction.getVersion());
        coreTransactionHistory.setHistoryCreatedUser(historyCreatedUser);
        coreTransactionHistory.setHistoryCreatedDate(currentTimestamp);
        coreTransactionHistory.setSyncTs(currentTimestamp);

        coreTransactionHistoryRepository.saveAndFlush(coreTransactionHistory);
    }


}
