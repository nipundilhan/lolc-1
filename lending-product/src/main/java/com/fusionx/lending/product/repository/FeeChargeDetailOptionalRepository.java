package com.fusionx.lending.product.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import javax.persistence.TemporalType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.CommonListItem;
import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargeCap;
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
public interface FeeChargeDetailOptionalRepository  extends JpaRepository<FeeChargeDetailOptional, Long>{

	
	List<FeeChargeDetailOptional> findAllByFeeCharge(FeeCharge feeCharge);
	
	List<FeeChargeDetailOptional> findAllByStatus(CommonStatus status);
	
	List<FeeChargeDetailOptional> findAllByFeeCategory(CommonListItem feeCategory);
	
	List<FeeChargeDetailOptional> findAllByOtherFeeTypeId(Long otherFeeTypeId);
	
    @Query("SELECT  fcdo FROM FeeChargeDetailOptional fcdo "
    		+ "WHERE "
				+ " (:feeTypId IS NULL OR (:feeTypId IS NOT NULL AND fcdo.otherFeeType.id = :feeTypId)) "
				+ " AND (:feeChargeDetOpId IS NULL OR (:feeChargeDetOpId IS NOT NULL AND fcdo.id = :feeChargeDetOpId)) "
				+ " AND (:effecDate IS NULL OR (year(fcdo.effectiveDate)=year(:effecDate) AND month(fcdo.effectiveDate)=month(:effecDate) AND day(fcdo.effectiveDate)=day(:effecDate))) "
    		)
	List<FeeChargeDetailOptional> searchByFeeTypeEffDateAndId(
			 @Param("effecDate") @Temporal(TemporalType.DATE) Date effectiveDate,
			 @Param("feeTypId") Long feeTypeId,
			 @Param("feeChargeDetOpId") Long feeChargeDetailsOptionalId
			 );
	
	
}
