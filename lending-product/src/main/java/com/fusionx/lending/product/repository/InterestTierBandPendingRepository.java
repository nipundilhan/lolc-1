package com.fusionx.lending.product.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * Interest Tier Band Pending Repository 
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   22-07-2021    FXL_July_2021_2  	FXL-53		Piyumi      Created
 *    
 *******************************************************************************************************
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.InterestTierBandPending;
import com.fusionx.lending.product.domain.InterestTierBandSet;
import com.fusionx.lending.product.enums.CommonApproveStatus;

@Repository
public interface InterestTierBandPendingRepository extends JpaRepository<InterestTierBandPending, Long>{
	
	List<InterestTierBandPending> findByInterestTierBandSetAndApproveStatus(InterestTierBandSet interestTierBandSet, CommonApproveStatus status);

	@Query("SELECT up FROM InterestTierBandPending up WHERE "
			+ "("
				+ ":searchq IS NOT NULL AND "
					+ "("
						+ "(upper(up.status) LIKE '%' || upper(:searchq) || '%') "
						+ " OR (upper(up.approveStatus) LIKE '%' || upper(:searchq) || '%') "
					+ ") "
					+ " AND "
					+ "("
						+ "(:status IS NULL OR (:status IS NOT NULL AND upper(up.status) = upper(:status)))"
						+ " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.approveStatus) = upper(:approveStatus)))"
					+ ")"	
					+ " OR "
					+ "("
						+ ":searchq IS NULL AND "
						+ "("
						+ "(:status IS NULL OR (:status IS NOT NULL AND upper(up.status) = upper(:status)))"
						+ " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.approveStatus) = upper(:approveStatus)))"
						+ ")"
					+ ") "
				+ ") "
			)
	public Page<InterestTierBandPending> searchInterestTierBandPending(@Param("searchq")String searchq, 
			@Param("status")String status,
			@Param("approveStatus")String approveStatus,
			Pageable pageable);

}
