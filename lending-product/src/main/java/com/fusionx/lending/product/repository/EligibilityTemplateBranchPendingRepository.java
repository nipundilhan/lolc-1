package com.fusionx.lending.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.EligibilityTemplateBranchPending;

/**
Eligibility Template Legal Structure
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   21-07-2019   FXL-1         FXL-42     Dilki        Created
*    
********************************************************************************************************
*/

@Repository
public interface EligibilityTemplateBranchPendingRepository extends JpaRepository<EligibilityTemplateBranchPending, Long> {

	@Query("SELECT up FROM EligibilityTemplateBranchPending up WHERE "
			+ "("
				+ ":searchq IS NOT NULL AND "
					+ "("
						+ "(upper(up.status) LIKE '%' || upper(:searchq) || '%') "
						+ " OR (upper(up.eligibilityBranchId.approveStatus) LIKE '%' || upper(:searchq) || '%') "
					+ ") "
					+ " AND "
					+ "("
						+ "(:status IS NULL OR (:status IS NOT NULL AND upper(up.status) = upper(:status)))"
						+ " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.eligibilityBranchId.approveStatus) = upper(:approveStatus)))"
					+ ")"	
					+ " OR "
					+ "("
						+ ":searchq IS NULL AND "
						+ "("
						+ "(:status IS NULL OR (:status IS NOT NULL AND upper(up.status) = upper(:status)))"
						+ " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.eligibilityBranchId.approveStatus) = upper(:approveStatus)))"
						+ ")"
					+ ") "
				+ ") "
			)
	public Page<EligibilityTemplateBranchPending> searchEligibilityTemplateBranchPending(@Param("searchq")String searchq, 
			@Param("status")String status,
			@Param("approveStatus")String approveStatus,
			Pageable pageable);

}
