package com.fusionx.lending.product.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.ApplicationFrequency;
import com.fusionx.lending.product.domain.CalculationFrequency;
import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeDetails;
import com.fusionx.lending.product.domain.OtherFeeType;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Fee Charge Details Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   28-07-2021      		      			Dilhan        Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FeeChargeDetailsRepository extends JpaRepository<FeeChargeDetails, Long>{

	List<FeeChargeDetails> findByStatus(CommonStatus status);

	Optional<FeeChargeDetails> findByCode(String code);
	
	List<FeeChargeDetails> findByFeeCharge(FeeCharge feeCharge);
	
	List<FeeChargeDetails> findByFeeCategory(CommonListItem catrgory);
	
	List<FeeChargeDetails> findByOtherFeeType(OtherFeeType feetype);
	
	List<FeeChargeDetails> findByFeeChargeAndOtherFeeTypeAndApplicationFrequencyAndCalculationFrequencyAndTypeAndStatusAndEffectiveDate(FeeCharge feeCharge,OtherFeeType feetype, ApplicationFrequency applicationFrequency, 
			CalculationFrequency calculationFrequency, String type,CommonStatus status, Timestamp date);
	
	List<FeeChargeDetails> findByFeeChargeAndOtherFeeTypeAndApplicationFrequencyAndCalculationFrequencyAndTypeAndStatusAndEffectiveDateAndIdNot(FeeCharge feeCharge,OtherFeeType feetype, ApplicationFrequency applicationFrequency, 
			CalculationFrequency calculationFrequency, String type,CommonStatus status, Timestamp date,Long id);
	
}
