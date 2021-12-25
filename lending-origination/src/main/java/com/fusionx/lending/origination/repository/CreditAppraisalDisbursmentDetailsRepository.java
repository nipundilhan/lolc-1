package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CreditAppraisalDisbursmentDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

/**
 * CreditAppraisalDisbursmentDetails Repository
 * 
 ********************************************************************************************************
 * ### Date Story Point Task No Author Description
 * -------------------------------------------------------------------------------------------------------
 * 1 06-10-2021  FXL-121	FXL-1005	PasinduT Created
 * 
 ********************************************************************************************************
 */

@Repository
public interface CreditAppraisalDisbursmentDetailsRepository
		extends JpaRepository<CreditAppraisalDisbursmentDetails, Long> {

	// public List<CreditAppraisalDisbursmentDetails> findAll();

	public List<CreditAppraisalDisbursmentDetails> findByStatus(CommonStatus status);

}
