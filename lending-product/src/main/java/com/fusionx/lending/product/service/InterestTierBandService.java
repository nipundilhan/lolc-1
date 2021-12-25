package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.InterestTierBand;
import com.fusionx.lending.product.domain.InterestTierBandPending;
import com.fusionx.lending.product.resources.InterestTierBandAddResource;
import com.fusionx.lending.product.resources.InterestTierBandUpdateResource;

@Service
public interface InterestTierBandService {
	
	/**
	 * 
	 * Find InterestTierBand by ID
	 * 
	 * @author Piyumi
	 * @return -JSON Object of InterestTierBand
	 * 
	 */
	public Optional<InterestTierBand> getById(Long id);

	/**
	 * 
	 * Insert InterestTierBand
	 * 
	 * @author Piyumi
	 * @param - InterestTierBandAddResource
	 * @return - Successfully saved
	 * 
	 */
	public  Long addInterestTierBand(String tenantId, Long interestTierBandSetId,
			InterestTierBandAddResource interestTierBandAddResource);

	/**
	 * 
	 * Update InterestTierBand
	 * 
	 * @author Piyumi
	 * @param - InterestTierBandUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public Long updateInterestTierBand(String tenantId, Long interestTempId, Long interestTierBandSetId,
			InterestTierBandUpdateResource interestTierBandUpdateResource);


	/**
	 * 
	 * Find Pending InterestTierBand by ID
	 * 
	 * @author Piyumi
	 * @return -JSON object of InterestTierBand Pending
	 * 
	 */
	public Optional<InterestTierBandPending> getByPendingId(Long pendingId);
	
	/**
	 * 
	 * Find InterestTierBand by interestTierBandSetId
	 * 
	 * @author Piyumi
	 * @return -JSON array of all InterestTierBand
	 * 
	 */
	public List<InterestTierBand> getByInterestTierBandSetId(Long interestTierBandSetId);
	
	
	/**
	 * Search InterestTierBandPending
	 *
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<InterestTierBandPending> searchInterestTierBandPending(String searchq, String status, String approveStatus, Pageable pageable);

}
