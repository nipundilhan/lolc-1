package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.Brand;
import com.fusionx.lending.product.domain.CoreProduct;

/**
 * API Service related to Core Product.
 *
 * @author Dilhan Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        25-09-2021      -               -           Dilhan Jayasinghe        Created
 * <p>
 */
@Service
public interface CoreProductHistoryService {
	
	/**
     * Saves the core product history
     *
     * @param tenantId           the tenant id
     * @param brand              the brand object to save
     * @param historyCreatedUser the user name.
     */
    void saveHistory(String tenantId, CoreProduct coreProduct, String historyCreatedUser);

}
