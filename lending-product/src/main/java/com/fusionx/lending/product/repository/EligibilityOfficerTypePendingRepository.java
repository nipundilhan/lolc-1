package com.fusionx.lending.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fusionx.lending.product.domain.EligibilityOfficerTypePending;

/**
 * Eligibility Officer Type Pending Repository
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   01-07-2021      		     FX-6888	MiyuruW      Created
 *    
 *******************************************************************************************************
 */

@Repository
public interface EligibilityOfficerTypePendingRepository extends JpaRepository<EligibilityOfficerTypePending, Long> {

	@Query("SELECT up FROM EligibilityOfficerTypePending up WHERE "
			+ "("
				+ ":searchq IS NOT NULL AND "
					+ "("
						+ "(upper(up.status) LIKE '%' || upper(:searchq) || '%') "
						+ " OR (upper(up.eligibilityOfficerTypes.approveStatus) LIKE '%' || upper(:searchq) || '%') "
					+ ") "
					+ " AND "
					+ "("
						+ "(:status IS NULL OR (:status IS NOT NULL AND upper(up.status) = upper(:status)))"
						+ " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.eligibilityOfficerTypes.approveStatus) = upper(:approveStatus)))"
					+ ")"	
					+ " OR "
					+ "("
						+ ":searchq IS NULL AND "
						+ "("
						+ "(:status IS NULL OR (:status IS NOT NULL AND upper(up.status) = upper(:status)))"
						+ " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.eligibilityOfficerTypes.approveStatus) = upper(:approveStatus)))"
						+ ")"
					+ ") "
				+ ") "
			)
	public Page<EligibilityOfficerTypePending> searchEligibilityOfficerTypePending(@Param("searchq")String searchq, 
			@Param("status")String status,
			@Param("approveStatus")String approveStatus,
			Pageable pageable);
	
}
