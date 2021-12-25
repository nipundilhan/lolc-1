package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.Brand;
import org.springframework.stereotype.Service;


/**
 * API Service related to Brand
 *
 * @author Menuka Jayasinghe
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No             Author                  	Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        10-06-2020      -                            		Menuka Jayasinghe          Created
 * <p>
 */
@Service
public interface BrandHistoryService {

    /**
     * Saves the brand history
     *
     * @param tenantId           the tenant id
     * @param brand              the brand object to save
     * @param historyCreatedUser the user name.
     */
    void saveHistory(String tenantId, Brand brand, String historyCreatedUser);

}
