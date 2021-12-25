package com.fusionx.lending.transaction.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.transaction.domain.AllocationTemplatePending;

@Repository
public interface AllocationTemplatePendingRepository extends JpaRepository<AllocationTemplatePending, Long>{

	@Query("SELECT up FROM AllocationTemplatePending up WHERE "
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
	public Page<AllocationTemplatePending> searchAllocationTemplatePending(@Param("searchq")String searchq, 
			@Param("status")String status,
			@Param("approveStatus")String approveStatus,
			Pageable pageable);
}
