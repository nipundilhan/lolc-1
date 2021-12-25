package com.fusionx.lending.product.service;

import com.fusionx.lending.product.domain.Brand;
import com.fusionx.lending.product.resources.CommonAddResource;
import com.fusionx.lending.product.resources.CommonUpdateResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * API Service related to Brand.
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
public interface BrandService {

    /**
     * Gets the all brands
     *
     * @return the all brands
     */
    List<Brand> getAll();

    /**
     * Returns the brand by id
     *
     * @param id the id
     * @return the brand
     */
    Optional<Brand> getById(Long id);

    /**
     * Returns the brand by code
     *
     * @param code the code of brand
     * @return the brand
     */
    Optional<Brand> getByCode(String code);

    /**
     * Returns the brand by name
     *
     * @param name the name of brand
     * @return the brand
     */
    List<Brand> getByName(String name);

    /**
     * Returns the brand by status
     *
     * @param status the status
     * @return the brand
     */
    List<Brand> getByStatus(String status);


    /**
     * Saves the given brand
     *
     * @param tenantId          the tenant id
     * @param commonAddResource the object to save
     * @return the saved brand
     */
    Brand addBrand(String tenantId, CommonAddResource commonAddResource);

    /**
     * Updates the brand object by given object
     *
     * @param tenantId             the tenant id
     * @param id                   the id need to be update
     * @param commonUpdateResource the object which contains data
     * @return the updated object.
     */
    Brand updateBrand(String tenantId, Long id, CommonUpdateResource commonUpdateResource);
}
