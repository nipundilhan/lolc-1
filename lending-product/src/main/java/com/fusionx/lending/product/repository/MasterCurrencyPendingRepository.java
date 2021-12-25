package com.fusionx.lending.product.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * Master Def Currency Eligibility Pending Repository
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   13-07-2021      		     FX-	Piyumi      Created
 *    
 *******************************************************************************************************
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.EligibilityCurrencyPending;
import com.fusionx.lending.product.domain.MasterCurrencyPending;
import com.fusionx.lending.product.domain.MasterDefinition;
import com.fusionx.lending.product.domain.MasterDefinitionPending;

@Repository
public interface MasterCurrencyPendingRepository extends JpaRepository<MasterCurrencyPending, Long>{
	
	List<MasterCurrencyPending> findByMasterDefinition(MasterDefinition masterDefinition);
	
	public Boolean existsByMasterDefinitionIdAndCurrencyId(Long masterDefId, Long currencyId);
	
	public Boolean existsByMasterDefinitionIdAndCurrencyIdAndIdNotIn(Long masterDefId, Long currencyId,Long Id);
	
	List<MasterCurrencyPending> findByMasterDefinitionPending(MasterDefinitionPending masterDefinitionPending);
	
	@Query("SELECT up FROM MasterCurrencyPending up WHERE "
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
	public Page<MasterCurrencyPending> searchMasterCurrencyPending(@Param("searchq")String searchq, 
			@Param("status")String status,
			@Param("approveStatus")String approveStatus,
			Pageable pageable);

}
