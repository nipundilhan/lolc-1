package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.resources.CommonListAddResource;
import com.fusionx.lending.product.resources.CommonListUpdateResource;

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
public interface CommonListItemService {

    /**
     * Returns the list of all common list item.
     *
     * @return list of common list item.
     */
    List<CommonListItem> findAll();

    /**
     * Gets the Common List Item by id
     *
     * @param id the id of the record
     * @return the Common List Item
     */
    Optional<CommonListItem> findById(Long id);

    /**
     * Returns the common list item by the given status
     *
     * @param status the status should be either <code>ACTIVE</code> or <code>INACTIVE</code>
     * @return the list of common list item
     */
    List<CommonListItem> findByStatus(String status);

    /**
     * Returns the list item common list item by the given name
     *
     * @param name the name
     * @return the list of common list item.
     */
    List<CommonListItem> findByName(String name);

    /**
     * Returns the list of common list item by code
     *
     * @param code the code of the record
     * @return the list of the common list item
     */
    List<CommonListItem> findByCode(String code);

    /**
     * Returns the common list item by the given reference code
     *
     * @param referenceCode the reference code
     * @return the list of common list item
     */
    List<CommonListItem> findByReferenceCode(String referenceCode);

    /**
     * Saves the given common list item
     *
     * @param tenantId              the tenant id
     * @param createdUser           the user name
     * @param commonListAddResource the Resource file
     * @return the id of the saved record
     */
    Long saveAndValidateCommonList(String tenantId, String createdUser, CommonListAddResource commonListAddResource);

    /**
     * Updates the given record
     *
     * @param tenantId                 the tenant id
     * @param createdUser              the user name
     * @param id                       the id of updating record
     * @param commonListUpdateResource the resource file
     * @return the id of the updated record
     */
    Long updateAndValidateCommonList(String tenantId, String createdUser, Long id, CommonListUpdateResource commonListUpdateResource);

    /**
     * Checks whether record is exist or not.
     *
     * @param id the id of the record
     * @return <code>true</code> if record exists, otherwise <code>false</code>
     */
    Boolean existsById(Long id);

}
