package com.fusionx.lending.origination.repository;
/**
 * 	Exception Approval Group Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-08-2021   FXL-632  	 FXL-564       Piyumi     Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ExceptionApprovalGroup;
import com.fusionx.lending.origination.domain.ExceptionType;
import com.fusionx.lending.origination.enums.CommonStatus;



@Repository
public interface ExceptionApprovalGroupRepository extends JpaRepository<ExceptionApprovalGroup, Long>{
	
	List<ExceptionApprovalGroup> findByStatus(CommonStatus status);
	
	Optional<ExceptionApprovalGroup> findByCode(String code);

	List<ExceptionApprovalGroup> findByNameContaining(String name);

	Optional<ExceptionApprovalGroup> findByCodeAndIdNotIn(String code, Long id);

	Optional<ExceptionApprovalGroup> findByIdAndCodeAndStatus(Long id, String name, String status);
	
	@Query("SELECT up FROM ExceptionApprovalGroup up WHERE "
			+ "("
//				+ ":searchq IS NOT NULL AND "
//					+ "("
//						+ "(upper(up.name) LIKE '%' || upper(:searchq) || '%') "
//						+ " OR (upper(up.code) LIKE '%' || upper(:searchq) || '%') "
//						+ " OR (upper(up.status) LIKE '%' || upper(:searchq) || '%') "
//					+ ") "
//					+ " AND "
					+ "("
						+ "(:name IS NULL OR (:name IS NOT NULL AND upper(up.name) LIKE '%' || upper(:name) || '%'))"
						+ " AND (:code IS NULL OR (:code IS NOT NULL AND up.code LIKE '%' || :code || '%'))"
						+ " AND (:status IS NULL OR (:status IS NOT NULL AND up.status LIKE '%' || :status || '%'))"
					+ ")"	
//					+ " OR "
//					+ "("
//						+ ":searchq IS NULL AND "
//						+ "("
//						+ "(:name IS NULL OR (:name IS NOT NULL AND upper(up.name) LIKE '%' || upper(:name) || '%'))"
//						+ " AND (:code IS NULL OR (:code IS NOT NULL AND up.code LIKE '%' || :code || '%'))"
//						+ " AND (:status IS NULL OR (:status IS NOT NULL AND up.status LIKE '%' || :status || '%'))"
//						+ ")"
//					+ ") "
				+ ") "
			)
	public Page<ExceptionApprovalGroup> searchExceptionApprovalGroup(
			@Param("name")String name,
			@Param("code")String code,
			@Param("status")String status,
			Pageable pageable);

}
