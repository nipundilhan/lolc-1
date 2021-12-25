package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.ApplicationFrequency;
import org.springframework.stereotype.Service;

/**
 * API Service related to Application Frequency.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6511             Senitha Perera          Created
 * <p>
 */
@Service
public interface ApplicationFrequencyHistoryService {

    /**
     * Insert the application frequency history
     *
     * @param tenantId             the tenant id
     * @param applicationFrequency the object to save.
     */
    void insertApplicationFrequencyHistory(String tenantId, ApplicationFrequency applicationFrequency);

}
