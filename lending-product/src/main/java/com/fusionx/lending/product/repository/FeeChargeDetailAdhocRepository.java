package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeDetailAdhoc;
import com.fusionx.lending.product.domain.FeeChargeDetailOptional;
import com.fusionx.lending.product.domain.OtherFeeType;
import com.fusionx.lending.product.enums.CommonStatus;

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
public interface FeeChargeDetailAdhocRepository  extends JpaRepository<FeeChargeDetailAdhoc, Long>{
	
	List<FeeChargeDetailAdhoc> findAllByFeeCharge(FeeCharge feeCharge);
	
	List<FeeChargeDetailAdhoc> findAllByStatus(CommonStatus status);
	
	List<FeeChargeDetailAdhoc> findAllByAndFeeCategoryReferenceCodeAndFeeCategoryCode(String refCode ,String code);
	
	List<FeeChargeDetailAdhoc> findAllByOtherFeeTypeCode(String otherFeeTypeCode);

}
