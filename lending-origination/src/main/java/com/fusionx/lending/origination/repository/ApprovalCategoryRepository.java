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
public interface ApprovalCategoryRepository extends JpaRepository<ApprovalCategory, Long> {

	public Page<ApprovalCategory> findAll(Pageable pageable);

	public List<ApprovalCategory> findByStatus(String status);

	public List<ApprovalCategory> findByNameContaining(String name);

	public Optional<ApprovalCategory> findByCode(String code);

	public Optional<ApprovalCategory> findByCodeAndIdNotIn(String code, Long id);

	public Boolean existsByCodeAndStatus(String code, String status);
	
	@Query("SELECT up FROM ApprovalCategory up WHERE "
			+ "("
				+ ":searchq IS NOT NULL AND "
					+ "("
						+ "(upper(up.name) LIKE '%' || upper(:searchq) || '%') "
						+ " OR (upper(up.code) LIKE '%' || upper(:searchq) || '%') "
					+ ") "
					+ " AND "
					+ "("
						+ "(:name IS NULL OR (:name IS NOT NULL AND upper(up.name) LIKE '%' || upper(:name) || '%'))"
						+ " AND (:code IS NULL OR (:code IS NOT NULL AND up.code LIKE '%' || :code || '%'))"
					+ ")"	
					+ " OR "
					+ "("
						+ ":searchq IS NULL AND "
						+ "("
						+ "(:name IS NULL OR (:name IS NOT NULL AND upper(up.name) LIKE '%' || upper(:name) || '%'))"
						+ " AND (:code IS NULL OR (:code IS NOT NULL AND up.code LIKE '%' || :code || '%'))"
						+ ")"
					+ ") "
				+ ") "
			)
	public Page<ApprovalCategory> searchApprovalCategory(@Param("searchq")String searchq, 
			@Param("name")String name,
			@Param("code")String code,
			Pageable pageable);

	public Optional<ApprovalCategory> findByIdAndStatus(Long id, String status);
	
	public Optional<ApprovalCategory> findByIdAndCodeAndStatus(long parseLong, String approvalCatCode,
			String status);

	public Optional<ApprovalCategory> findByIdAndNameAndStatus(long parseLong, String approvalCategory, String string);
}