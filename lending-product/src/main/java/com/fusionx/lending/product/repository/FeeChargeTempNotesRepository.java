package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeeChargeTempNotes;

/**
 * Fee Charge Temp Notes Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   21-07-2021      		      			Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FeeChargeTempNotesRepository extends JpaRepository<FeeChargeTempNotes, Long> {
	
	List<FeeChargeTempNotes> findByStatus(String status);
	
    List<FeeChargeTempNotes> findByFeeCharge_Id(Long feeChargeId);

}