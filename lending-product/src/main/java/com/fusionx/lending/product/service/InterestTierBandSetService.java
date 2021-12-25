package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.InterestTierBandSet;
import com.fusionx.lending.product.domain.InterestTierBandSetPending;
import com.fusionx.lending.product.resources.InterestTierBandSetAddResource;
import com.fusionx.lending.product.resources.InterestTierBandSetUpdateResource;
/**
 * InterestTierBandSetService 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   19-07-2021    FXL_July_2021_2  	FXL-52		Piyumi      Created
 *    
 *******************************************************************************************************
 */

@Service
public interface InterestTierBandSetService {
	
	/**
	 * 
	 * Find InterestTierBandSet by ID
	 * 
	 * @author Piyumi
	 * @return -JSON Object of InterestTierBandSet
	 * 
	 */
	public Optional<InterestTierBandSet> getById(Long id);

	/**
	 * 
	 * Insert InterestTierBandSet
	 * 
	 * @author Piyumi
	 * @param - InterestTierBandSetAddResource
	 * @return - Successfully saved
	 * 
	 */
	public  Long addInterestTierBandSet(String tenantId, Long interestTempId,
			InterestTierBandSetAddResource interestTierBandSetAddResource);

	/**
	 * 
	 * Update InterestTierBandSet
	 * 
	 * @author Piyumi
	 * @param - InterestTierBandSetUpdateResource
	 * @return - Successfully saved
	 * 
	 */
	public Long updateInterestTierBandSet(String tenantId, Long interestTierBandSetId,
			InterestTierBandSetUpdateResource interestTierBandSetUpdateResource);

	/**
	 * 
	 * Find Pending InterestTierBandSet by ID
	 * 
	 * @author Piyumi
	 * @return -JSON object of InterestTierBandSet Pending
	 * 
	 */
	public Optional<InterestTierBandSetPending> getByPendingId(Long pendingId);
	
	/**
	 * 
	 * Find InterestTierBandSet by interestTempId
	 * 
	 * @author Piyumi
	 * @return -JSON array of all InterestTierBandSet
	 * 
	 */
	public List<InterestTierBandSet> getByInterestTemplateId(Long interestTempId);
	
	public Optional<InterestTierBandSet> getByCodeAndInterestTemplateId(String code,Long interestTempId);
	
	/**
	 * 
	 * Find Pending InterestTierBandSet by InterestTemplateId
	 * 
	 * @author Piyumi
	 * @return -JSON array of InterestTierBandSet Pending
	 * 
	 */
	public List<InterestTierBandSetPending> getPendingByInterestTemplateId(Long interestTempId);
	
	/**
	 * Search InterestTierBandSetPending
	 *
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<InterestTierBandSetPending> searchInterestTierBandSetPending(String searchq, String status, String approveStatus, Pageable pageable);

}
