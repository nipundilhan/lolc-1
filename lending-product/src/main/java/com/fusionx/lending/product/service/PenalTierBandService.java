package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import com.fusionx.lending.product.domain.PenalTierBand;
import com.fusionx.lending.product.domain.PenalTierBandPending;
import com.fusionx.lending.product.resources.PenalTierBandAddRequest;
import com.fusionx.lending.product.resources.PenalTierBandUpdateRequest;

public interface PenalTierBandService {

	
	/**
	 * 
	 * Find InterestTierBand by ID
	 * 
	 * @author Dilhan
	 * @return -JSON Object of InterestTierBand
	 * 
	 */
	public Optional<PenalTierBand> getById(Long id);

	/**
	 * 
	 * Insert InterestTierBand
	 * 
	 * @author Dilhan
	 * @param - InterestTierBandAddResource
	 * @return - Successfully saved
	 * 
	 */
	public  Long addPenalTierBand(String tenantId, Long interestTierBandSetId,
			PenalTierBandAddRequest penalTierBandAddRequest);

	/**
	 * 
	 * Update PenalTierBand
	 * 
	 * @author Dilhan
	 * @param - InterestTierBandUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public Long updatePenalTierBand(String tenantId, Long interestTempId, Long interestTierBandSetId,
			PenalTierBandUpdateRequest penalTierBandUpdateRequest);


	/**
	 * 
	 * Find Pending PenalTierBand by ID
	 * 
	 * @author Dilhan
	 * @return -JSON object of PenalTierBand Pending
	 * 
	 */
	public Optional<PenalTierBandPending> getByPendingId(Long pendingId);
	
	/**
	 * 
	 * Find PenalTierBand by penalTierBandSetId
	 * 
	 * @author Dilhan
	 * @return -JSON array of all PenalTierBand
	 * 
	 */
	public List<PenalTierBand> getByPenalTierBandSetId(Long interestTierBandSetId);
	
	
	/**
	 * Search PenalTierBandPending
	 *
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	//public Page<PenalTierBandPending> searchPenalTierBandPending(String searchq, String status, String approveStatus, Pageable pageable);

}
