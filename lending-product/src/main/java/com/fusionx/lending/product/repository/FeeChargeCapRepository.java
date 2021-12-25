package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeCap;
import com.fusionx.lending.product.domain.OtherFeeType;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Fee Charge Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-07-2021      		      			Dilhan      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FeeChargeCapRepository extends JpaRepository<FeeChargeCap, Long>{
	
	List<FeeChargeCap> findByStatus(CommonStatus status);

	Optional<FeeChargeCap> findByCode(String code);
	
	List<FeeChargeCap> findByOtherFeeType(OtherFeeType otherFeeType);
	
	List<FeeChargeCap> findByFeeCharge(FeeCharge feeCharge);

}
