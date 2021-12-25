package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeDetailAdhoc;
import com.fusionx.lending.product.domain.FeeChargeDetailAdhocPending;
import com.fusionx.lending.product.domain.FeeChargePending;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.resources.FeeChargeDetailAdhocAddResource;
import com.fusionx.lending.product.resources.FeeChargeDetailAdhocUpdateResource;

/**
 * API Service related to Fee Charge Details Optional options.
 *
 * @author Nipun Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        18-08-2021      -               -           Nipun Dilhan      Created
 * <p>
 *
 */

@Service
public interface FeeChargeDetailAdhocService {
	
	FeeChargeDetailAdhoc create(String tenantId, FeeChargeDetailAdhocAddResource feeChargeDetailAdhocAddResource);
	
	FeeChargePending updateFeeChargeDetailAdhoc(String tenantId, Long id,FeeChargeDetailAdhocUpdateResource feeChargeDetailAdhocUpdateResource);

	
	List<FeeChargeDetailAdhoc> getFeeChargeDetailAdhocByFeeChargeId(Long feeChargeId);
	
	FeeChargeDetailAdhoc getFeeChargeDetailAdhocDetailsById(Long feeChargeDetailAdhocId);
	
	List<FeeChargeDetailAdhocPending> getPendingAdhocListByFeeChargePendingId(Long feeChargePendingId);
	
	List<FeeChargeDetailAdhoc> findByStatus(String status);
	
	List<FeeChargeDetailAdhoc> findByCategory(String code);
	
	List<FeeChargeDetailAdhoc> findByFeeTypeCode(String feeTypeCode);
	
	List<FeeChargeDetailAdhoc> findAll();
	
	void approvePendingAdhoc(Long feeChargePendingId);
		
	FeeChargeDetailAdhoc directUpdateFeeChargeDetailAdhoc(Long id, FeeChargeDetailAdhocUpdateResource feeChargeDetailAdhocUpdateResource);



}
