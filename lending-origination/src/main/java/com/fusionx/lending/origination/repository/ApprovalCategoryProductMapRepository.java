package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ApprovalCategory;
import com.fusionx.lending.origination.domain.ApprovalCategoryProductMapping;
import com.fusionx.lending.origination.enums.CommonStatus;


/**
 * Approval Category Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		     	        VenukiR      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface ApprovalCategoryProductMapRepository extends JpaRepository<ApprovalCategoryProductMapping, Long> {

	public Page<ApprovalCategoryProductMapping> findAll(Pageable pageable);

	public List<ApprovalCategoryProductMapping> findByStatus(String status);


	//public Optional<ApprovalCategoryProductMapping> findByCode(String code);
	
	//public Boolean existsByCodeAndStatus(String code, String status);
	
	@Query("SELECT up FROM ApprovalCategoryProductMapping up WHERE "
			+ "("
				+ ":searchq IS NOT NULL AND "
					+ "("
						+ "(upper(up.approvalCategory.name) LIKE '%' || upper(:searchq) || '%') "
						+ " OR (upper(up.approvalCategory.code) LIKE '%' || upper(:searchq) || '%') "
					+ ") "
					+ " AND "
					+ "("
						+ "(:appCatName IS NULL OR (:appCatName IS NOT NULL AND upper(up.approvalCategory.name) LIKE '%' || upper(:appCatName) || '%'))"
						+ " AND (:appCatCode IS NULL OR (:appCatCode IS NOT NULL AND up.approvalCategory.code LIKE '%' || :appCatCode || '%'))"
					+ ")"	
					+ " OR "
					+ "("
						+ ":searchq IS NULL AND "
						+ "("
						+ "(:appCatName IS NULL OR (:appCatName IS NOT NULL AND upper(up.approvalCategory.name) LIKE '%' || upper(:appCatName) || '%'))"
						+ " AND (:appCatCode IS NULL OR (:appCatCode IS NOT NULL AND up.approvalCategory.code LIKE '%' || :appCatCode || '%'))"
						+ ")"
					+ ") "
				+ ") "
			)
	public Page<ApprovalCategoryProductMapping> searchByApprovalCategory(@Param("searchq")String searchq, 
			@Param("appCatName")String name,
			@Param("appCatCode")String code,
			Pageable pageable);

	public Optional<ApprovalCategoryProductMapping> findByIdAndStatus(Long id, String status);

	public boolean existsByApprovalCategoryId(Long appCatId);
	
	public boolean existsByProductId(Long productId);
	
	public boolean existsByApprovalCategoryIdAndProductId(Long appCatId, Long productId);
	
//	public boolean existByApprovalCategoryIdAndId(Long appCatId, Long id);
	
	public boolean existsByApprovalCategoryIdAndProductIdAndIdNotIn(Long appCatId, Long productId, Long id);
	
}