package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.NetWorthAsset;
import com.fusionx.lending.origination.domain.NetWorthLiability;
import com.fusionx.lending.origination.resource.GuarantorDetailAddResource;
import com.fusionx.lending.origination.resource.GuarantorDetailResponseRecource;
import com.fusionx.lending.origination.resource.GuarantorDetailUpdateResource;

/**
 * Guarantor Details Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-04-2021   		         FX-6157    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@Service
public interface GuarantorNetWorthDetailService {
	
	/**
	 * 
	 * Find all Guarantor Detail guarantor ID
	 * @author Senitha
	 * @return -JSON array of Guarantor Details
	 * 
	 * */
	public GuarantorDetailResponseRecource getAllByGuarantorId(Long id);
	
	/**
	 * 
	 * Find Guarantor Detail by guarantor ID
	 * @author Senitha
	 * @return -JSON array of Guarantor Details
	 * 
	 * */
	public Guarantor getSummaryByGuarantorId(String tenantId, Long guarantorId);
	
	/**
	 * 
	 * Insert Guarantor Details
	 * @author Senitha
	 * @param  - GuarantorDetailsAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public Guarantor addGuarantorDetail(String tenantId , GuarantorDetailAddResource guarantorDetailsAddResource, Long guarantorId);

	/**
	 * 
	 * Update Guarantor Details
	 * @author Senitha
	 * @param  - GuarantorDetailUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public Guarantor updateGuarantorDetail(String tenantId, GuarantorDetailUpdateResource guarantorDetailUpdateResource);
	
	/**
	 * 
	 * Find Net Worth Asset by Guarantor ID
	 * @author Senitha
	 * @return -JSON array of Calculation Frequency
	 * 
	 * */
	public List<NetWorthAsset> getNetWorthAssetByGuarantorId(String tenantId, Long guarantorId);
	
	/**
	 * 
	 * Find Net Worth Liability by Guarantor ID
	 * @author Senitha
	 * @return -JSON array of Calculation Frequency
	 * 
	 * */
	public List<NetWorthLiability> getNetWorthLiabilityByGuarantorId(String tenantId, Long guarantorId);
	
}
