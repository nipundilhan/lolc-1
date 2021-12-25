package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.MasterDefinition;
import org.springframework.stereotype.Service;


/**
 * API Service related to Lending Module Definition - Master Definition
 *
 * @author Dilki Kalansooriya (Inova)
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Version History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * .....................................................................................................................................<br/>
 * 1        12-July-2021	FXL-1			FXL-5				DilkiK (Inova)			Created			ChinthakaMa     16-Sep-2021
 * <p>
 */

@Service
public interface MasterDefinitionHistoryService {

    /**
     * Saves the history record.
     *
     * @param tenantId           the tenant id
     * @param masterDefinition   the master definition record
     * @param historyCreatedUser the user name
     */
    void saveHistory(String tenantId, MasterDefinition masterDefinition, String historyCreatedUser);

}
