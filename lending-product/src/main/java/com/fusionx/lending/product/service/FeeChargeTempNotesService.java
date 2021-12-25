package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeeChargeTempNotes;
import com.fusionx.lending.product.resources.AddNotesRequest;
import com.fusionx.lending.product.resources.UpdateNotesRequest;

@Service
public interface FeeChargeTempNotesService {
	/**
     * Find all Fee Charge Template Notes
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of all Fee Charge Template Notes objects
	 * 
	 */
	public List<FeeChargeTempNotes> findAll();
	
	/**
     * Find Fee Charge Template Notes by Id
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of RFee Charge Template Notes objects
	 * 
	 */
	public Optional<FeeChargeTempNotes> findById(Long id);
	
	/**
     * Find Fee Charge Template Notes byFee Charge Id
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of Fee Charge Template Notes objects
	 * 
	 */
	public List<FeeChargeTempNotes> findByFeeChargeId(Long feeChargeId);
	
	/**
     * Find Fee Charge Template Notes by Status
	 * @author Rangana
	 * 
	 * @return   		- JSON Array of Fee Charge Template Notes objects
	 * 
	 */
	public List<FeeChargeTempNotes> findByStatus(String status);
	
	/**
     * Insert Fee Charge Template Notes 
     * @author Rangana
     * 
     * @param 		- AddNotesRequest
     * @return      - Success message.
     * 
     */
	public FeeChargeTempNotes addFeeChargeCapTempNotes(String tenantId,Long residencyEligibilityId,AddNotesRequest addNotesRequest, String username);
	

	/**
     * Update Fee Charge Template Notes 
     * @author Rangana
     * 
     * @param 		- UpdateNotesRequest
     * @return      - Success message.
     * 
     */
	public FeeChargeTempNotes updateFeeChargeCapTempNotes(String tenantId, Long id, UpdateNotesRequest updateNotesRequest, String username);

}
