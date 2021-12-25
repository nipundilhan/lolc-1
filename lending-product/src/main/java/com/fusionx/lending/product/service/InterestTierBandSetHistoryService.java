package com.fusionx.lending.product.service;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.InterestTierBandSet;

@Service
public interface InterestTierBandSetHistoryService {

	/**
	 * @param tenantId
	 * @param InterestTierBandSet
	 * @param historyCreatedUser
	 */
	public void saveHistory(String tenantId, InterestTierBandSet interestTierBandSet, String historyCreatedUser);
}
