package com.fusionx.lending.product.service;

/**
 * Residency Eligibility Notes service
 * @author 	Rangana
 * @Dat     30-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-06-2021   FX-6       FX-6819     Rangana      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.ResidencyEligibilityNotes;
import com.fusionx.lending.product.resources.AddNotesRequest;
import com.fusionx.lending.product.resources.UpdateNotesRequest;


@Service
public interface ResidencyEligibilityNotesService {
	
	/**
     * Find all Residency Eligibility Notes
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of all Residency Eligibility Notes objects
	 * 
	 */
	public List<ResidencyEligibilityNotes> findAll();
	
	/**
     * Find Residency Eligibility Notes by Id
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of Residency Eligibility Notes objects
	 * 
	 */
	public Optional<ResidencyEligibilityNotes> findById(Long id);
	
	/**
     * Find Residency Eligibility Notes by Industry Eligibility Id
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of Residency Eligibility Notes objects
	 * 
	 */
	public List<ResidencyEligibilityNotes> findByResidencyEligiId(Long residencyEligibilityId);
	
	/**
     * Find Residency Eligibility Notes by Status
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of Residency Eligibility Notes objects
	 * 
	 */
	public List<ResidencyEligibilityNotes> findByStatus(String status);
	
	/**
     * Insert Residency Eligibility Notes 
     * @author Rangana
     * 
     * @param 		- AddNotesRequest
     * @return      - Success message.
     * 
     */
	public ResidencyEligibilityNotes addResidencyEligibilityNotes(String tenantId,Long residencyEligibilityId,AddNotesRequest addNotesRequest, String username);
	

	/**
     * Update Residency Eligibility Notes 
     * @author Rangana
     * 
     * @param 		- UpdateNotesRequest
     * @return      - Success message.
     * 
     */
	public ResidencyEligibilityNotes updateResidencyEligibilityNotes(String tenantId, Long id, UpdateNotesRequest updateNotesRequest, String username);

}
