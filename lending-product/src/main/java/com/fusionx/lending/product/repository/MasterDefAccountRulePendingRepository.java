package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fusionx.lending.product.domain.EligibilityCurrencyPending;
import com.fusionx.lending.product.domain.MasterDefAccountRulePending;
import com.fusionx.lending.product.domain.MasterDefinitionPending;

/**
 * Master Def Account Rule Pending Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-07-2021      		      			Nipun        Created
 *    
 ********************************************************************************************************
 */

public interface MasterDefAccountRulePendingRepository   extends JpaRepository<MasterDefAccountRulePending, Long> {
	
	Optional<MasterDefAccountRulePending> findByMasterDefinitionPending(MasterDefinitionPending masterDefinitionPending);
	
	List<MasterDefAccountRulePending> findAllByMasterDefinitionPending(MasterDefinitionPending masterDefinitionPending);
	
	@Query("SELECT up FROM MasterDefAccountRulePending up WHERE "
			+ "("
				+ ":searchq IS NOT NULL AND "
					+ "("
						+ "(upper(up.masterDefinitionPending.status) LIKE '%' || upper(:searchq) || '%') "
						+ " OR (upper(up.masterDefinitionPending.approveStatus) LIKE '%' || upper(:searchq) || '%') "
					+ ") "
					+ " AND "
					+ "("
						+ "(:status IS NULL OR (:status IS NOT NULL AND upper(up.masterDefinitionPending.status) = upper(:status)))"
						+ " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.masterDefinitionPending.approveStatus) = upper(:approveStatus)))"
					+ ")"	
					+ " OR "
					+ "("
						+ ":searchq IS NULL AND "
						+ "("
						+ "(:status IS NULL OR (:status IS NOT NULL AND upper(up.masterDefinitionPending.status) = upper(:status)))"
						+ " AND (:approveStatus IS NULL OR (:approveStatus IS NOT NULL AND upper(up.masterDefinitionPending.approveStatus) = upper(:approveStatus)))"
						+ ")"
					+ ") "
				+ ") "
			)
	public Page<MasterDefAccountRulePending> searchMasterDefAccountRulePending(@Param("searchq")String searchq, 
			@Param("status")String status,
			@Param("approveStatus")String approveStatus,
			Pageable pageable);


}
