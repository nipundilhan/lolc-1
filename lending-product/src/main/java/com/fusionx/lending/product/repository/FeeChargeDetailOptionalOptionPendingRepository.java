package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeeChargeDetailOptionalOptionPending;
import com.fusionx.lending.product.domain.FeeChargeDetailOptionalPending;

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
public interface FeeChargeDetailOptionalOptionPendingRepository    extends JpaRepository<FeeChargeDetailOptionalOptionPending, Long>{

	
	List<FeeChargeDetailOptionalOptionPending> findAllByFeeChargeDetailOptionalPending(FeeChargeDetailOptionalPending feeChargeDetailOptionalPending);
}
