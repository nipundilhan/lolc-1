package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import com.fusionx.lending.product.domain.LeadApprovalDetail;
import com.fusionx.lending.product.resources.LeadApprovalDetailAddResources;

/**
 * API Service related to Account Status.
 *
 * @author Achini
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        10-12-2021      -               -           Achini      Created
 * <p>
 *
 */

public interface LeadApprovalDetailService {

	/**
     * Returns the all Lead Approval Details
     *
     * @return the list of Lead Approval Detail
     */
    List<LeadApprovalDetail> getAll();

    /**
     * Gets the Lead Approval Details by lead id
     *
     * @param leadId 
     * @return the list of Lead Approval Detail
     */
    List<LeadApprovalDetail> findAllByLeadId(Long leadId);
    
	/**
     * Saves the Lead Approval Detail
     *
     * @param tenantId                  the tenant id
     * @param leadApprovalDetailAddResources the object to save
     * @return the Lead Approval Detail
     */
	LeadApprovalDetail addLeadApprovalDetails(String tenantId, LeadApprovalDetailAddResources leadApprovalDetailAddResources);
}
