package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fusionx.lending.product.domain.PenalTierBandSet;
import com.fusionx.lending.product.domain.PenalTierBandSetPending;
import com.fusionx.lending.product.resources.InterestTierBandSetAddResource;
import com.fusionx.lending.product.resources.InterestTierBandSetUpdateResource;
import com.fusionx.lending.product.resources.PenalTierBandSetAddRequest;
import com.fusionx.lending.product.resources.PenalTierBandSetUpdateRequest;

/**
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *   1	  19-08-2021	  						Dilhan	     Created
 ********************************************************************************************************
 */

public interface PenalTierBandSetService {
	
	
	/**
	 * 
	 * Find InterestTierBandSet by ID
	 * 
	 * @author Piyumi
	 * @return -JSON Object of InterestTierBandSet
	 * 
	 */
	public Optional<PenalTierBandSet> getById(Long id);

	/**
	 * 
	 * Insert InterestTierBandSet
	 * 
	 * @author Piyumi
	 * @param - InterestTierBandSetAddResource
	 * @return - Successfully saved
	 * 
	 */
	public  Long addPenalTierBandSet(String tenantId, Long interestTempId,
			PenalTierBandSetAddRequest penalTierBandSetRequest);

	/**
	 * 
	 * Update InterestTierBandSet
	 * 
	 * @author Piyumi
	 * @param - InterestTierBandSetUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public Long updatePenalTierBandSet(String tenantId, Long interestTierBandSetId,
			PenalTierBandSetUpdateRequest penalTierBandSetUpdateRequest);

	/**
	 * 
	 * Find Pending InterestTierBandSet by ID
	 * 
	 * @author Piyumi
	 * @return -JSON object of InterestTierBandSet Pending
	 * 
	 */
	public Optional<PenalTierBandSetPending> getByPendingId(Long pendingId);
	
	/**
	 * 
	 * Find InterestTierBandSet by interestTempId
	 * 
	 * @author Piyumi
	 * @return -JSON array of all InterestTierBandSet
	 * 
	 */
	public List<PenalTierBandSet> getByPenalInterestTemplateId(Long interestTempId);
	
	/**
	 * 
	 * Find Pending InterestTierBandSet by InterestTemplateId
	 * 
	 * @author Piyumi
	 * @return -JSON array of InterestTierBandSet Pending
	 * 
	 */
	public List<PenalTierBandSetPending> getPendingByPenalInterestTemplateId(Long penalTempId);
	
	/**
	 * Search InterestTierBandSetPending
	 *
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	//public Page<PenalTierBandSetPending> searchPenalTierBandSetPending(String searchq, String status, String approveStatus, Pageable pageable);

}
