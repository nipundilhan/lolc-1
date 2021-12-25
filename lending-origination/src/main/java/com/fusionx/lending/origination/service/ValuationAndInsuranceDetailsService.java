package com.fusionx.lending.origination.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.resource.ValuationAndInsuranceDetListResponseResource;
import com.fusionx.lending.origination.resource.ValuationAndInsuranceDetailsAddResource;
import com.fusionx.lending.origination.resource.ValuationAndInsuranceDetailsUpdateResource;

/**
 * Approval Category Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		            	VenukiR      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface ValuationAndInsuranceDetailsService {

	public void saveValuationAndInsuranceDetails(String tenantId, String createdUser,
			ValuationAndInsuranceDetailsAddResource valuationAndInsuranceDetailsAddResource);

	void updateValuationAndInsuranceDetails(String tenantId, String createdUser,
			Long id, ValuationAndInsuranceDetailsUpdateResource valuationAndInsuranceDetailsUpdateResource);

//	ValuationAndInsuranceDetListResponseResource findByAssetsEntityId(String tenantId, Long assetEntityId);

	public ValuationAndInsuranceDetListResponseResource findValuationAndInsuranceDetails(String tenantId,
			Long customerid, Long collateralRefNo);

	
}
