package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.CommonListItem;
import org.springframework.stereotype.Service;

/**
 * API Service related to Common List Item.
 *
 * @author Senitha Perera
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  Description     Verified By     Verified Date
 * <br/>
 * ....................................................................................................................................<br/>
 * 1        04-06-2020      -               FX-6514             Senitha Perera          Created         ChinthakaMa     16-Sep-2021
 * <p>
 */
@Service
public interface CommonListItemHistoryService {

    /**
     * Saves common list item history.
     *
     * @param tenantId       the tenant id
     * @param commonListItem the common list item.
     */
    void insertCommonListItemHistory(String tenantId, CommonListItem commonListItem);
}
