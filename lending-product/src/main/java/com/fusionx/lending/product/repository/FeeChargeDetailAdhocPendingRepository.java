package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeeChargeCap;
import com.fusionx.lending.product.domain.FeeChargeDetailAdhoc;
import com.fusionx.lending.product.domain.FeeChargeDetailAdhocPending;
import com.fusionx.lending.product.domain.FeeChargeDetailOptionalPending;
import com.fusionx.lending.product.domain.FeeChargePending;

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
@Repository
public interface FeeChargeDetailAdhocPendingRepository  extends JpaRepository<FeeChargeDetailAdhocPending, Long>{
	
	List<FeeChargeDetailAdhocPending> findAllByFeeChargePendingAndFeeChargeDetailAdhoc(FeeChargePending feeChargePending,FeeChargeDetailAdhoc feeChargeDetailAdhoc);

	List<FeeChargeDetailAdhocPending> findAllByFeeChargePending(FeeChargePending feeChargePending);
}
