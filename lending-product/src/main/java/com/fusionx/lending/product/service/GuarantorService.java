package com.fusionx.lending.product.service;


import com.fusionx.lending.product.domain.Guarantor;
import com.fusionx.lending.product.resources.GuarantorAddResource;
import com.fusionx.lending.product.resources.GuarantorUpdateResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to Guarantor.
 *
 * @author Thushan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #          Date            Story Point     Task No       Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        26-10-2021          -               -           Thushan                  Created
 * <p>
 */
@Service
public interface GuarantorService {

    /**
     * Returns the guarantor by lending account id
     *
     * @param lendingAccountId the id of the lending account
     * @return  the Object of  guarantor.
     */
    Guarantor findByLendingAccountId(Long lendingAccountId);

    /**
     * Returns the guarantor by status
     *
     * @param status the status <code>ACTIVE | INACTIVE </code>
     * @return the list of guarantor
     */
    List<Guarantor> findByStatus(String status);

    /**
     * Gets the guarantor by id
     *
     * @param id the id of the record
     * @return the set of guarantors
     */
    Optional<Guarantor>  findById(Long id);

    /**
     * Create guarantor into DB
     *
     * @param tenantId the tenant id
     * @param guarantorAddResource the object to save
     * @return the saved guarantor.
     */
    Guarantor addGuarantor(String tenantId, GuarantorAddResource guarantorAddResource);

    /**
     * Updates the guarantor object by given object
     *
     * @param tenantId the tenant id
     * @param id the id need to be update
     * @param guarantorAddResource the object which contains data
     * @return the updated guarantor.
     */
    Guarantor updateGuarantor(String tenantId, Long id,GuarantorUpdateResource guarantorAddResource);

    /**
     * return all guarantors
     *
     * @return list og guarantor
     */
    List<Guarantor> getAll();
}
