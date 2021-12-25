package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.AgeEligibility;
import org.springframework.stereotype.Service;


/**
 * API Service related to Age eligibility.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        17-07-2021      -               -           Menuka Jayasinghe      Created
 * <p>
 */
@Service
public interface AgeEligibilityHistoryService {

    /**
     * Insert Age eligibility history
     *
     * @param tenantId           the tenant id
     * @param ageEligibility     the age eligibility
     * @param historyCreatedUser the user name
     */
    void insertAgeEligibilityHistory(String tenantId, AgeEligibility ageEligibility, String historyCreatedUser);

}
