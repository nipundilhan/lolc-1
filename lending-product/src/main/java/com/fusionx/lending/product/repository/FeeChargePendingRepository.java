package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.FeeCharge;
import com.fusionx.lending.product.domain.FeeChargePending;
import com.fusionx.lending.product.domain.PenalInterestPending;
import com.fusionx.lending.product.enums.CommonApproveStatus;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Fee Charge  Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-06-2021      		      			MenukaJ      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FeeChargePendingRepository extends JpaRepository<FeeChargePending, Long> {

	@Query("SELECT up FROM FeeChargePending up WHERE "
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
	public Page<FeeChargePending> searchFeeChargePending(@Param("searchq")String searchq, 
			@Param("status")String status,
			@Param("approveStatus")String approveStatus,
			Pageable pageable);
	

	List<FeeChargePending> findAllByFeeCharge(FeeCharge feeCharge);
	
	List<FeeChargePending> findAllByFeeChargeAndApproveStatus(FeeCharge feeCharge,CommonApproveStatus approveStatus);
	
	@Query(" SELECT fcp  FROM FeeChargePending fcp "
			+ " LEFT JOIN LendingWorkflow lwf ON fcp.lendingWorkflow.id = lwf.id	 "
			+ " WHERE "
			+ "(:workFlowStatus is null or lwf.workflowStatus = :workFlowStatus) 	"			
			+ " AND (:feeChargeId is null or fcp.id = :feeChargeId) ")
	List<FeeChargePending> getPendingFeeChargeHasPendingWorkflow(@Param("feeChargeId") Long feeChargeId,@Param("workFlowStatus") String workFlowStatus );


	Optional<FeeChargePending> findByIdAndStatus(Long id, CommonStatus status);
}
