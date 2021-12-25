package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.InterestTierBand;


@Service
public interface InterestTierBandHistoryService {
	

	/**
	 * @param tenantId
	 * @param InterestTierBand
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, InterestTierBand interestTierBand, String historyCreatedUser);

}
