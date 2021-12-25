package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.Eligibility;
import org.springframework.stereotype.Service;

/**
 * API Service related to Eligibility.
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        10-06-2021    	-               		             Menuka Jayasinghe		Created
 * <p>
 */
@Service
public interface EligibilityHistoryService {

    /**
     * Save history record
     *
     * @param tenantId           the tenant id
     * @param eligibility        the eligibility record
     * @param historyCreatedUser the user
     */
    void saveHistory(String tenantId, Eligibility eligibility, String historyCreatedUser);

}
