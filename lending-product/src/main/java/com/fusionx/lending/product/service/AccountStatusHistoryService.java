package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.AccountStatus;
import com.fusionx.lending.product.domain.AgeEligibility;

/**
 * API Service related to Age eligibility.
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
 * 1        15-09-2021      -               FXL-789     Dilhan      Created
 * <p>
 */
@Service
public interface AccountStatusHistoryService {

	 /**
     * Insert Account Status history
     *
     * @param tenantId           the tenant id
     * @param accountStatus     the account status
     * @param historyCreatedUser the user name
     */
    void insertAccountStatusHistory(String tenantId, AccountStatus accountStatus, String historyCreatedUser);
}
