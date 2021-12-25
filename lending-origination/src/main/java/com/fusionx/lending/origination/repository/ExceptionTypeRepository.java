package com.fusionx.lending.origination.repository;
/**
 * 	Exception Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-627  	 FXL-563       Piyumi     Created
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

import com.fusionx.lending.origination.domain.ExceptionType;
import com.fusionx.lending.origination.enums.CommonStatus;



@Repository
public interface ExceptionTypeRepository extends JpaRepository<ExceptionType, Long>{
	
	List<ExceptionType> findByStatus(CommonStatus status);
	
	Optional<ExceptionType> findByCode(String code);

	List<ExceptionType> findByNameContaining(String name);

	Optional<ExceptionType> findByCodeAndIdNotIn(String code, Long id);

	Optional<ExceptionType> findByIdAndCodeAndStatus(Long id, String name, String status);
	
	@Query("SELECT up FROM ExceptionType up WHERE "
			+ "("
//				+ ":searchq IS NOT NULL AND "
//					+ "("
//						+ "(upper(up.name) LIKE '%' || upper(:searchq) || '%') "
//						+ " OR (upper(up.code) LIKE '%' || upper(:searchq) || '%') "
//					+ ") "
//					+ " AND "
					+ "("
						+ "(:name IS NULL OR (:name IS NOT NULL AND upper(up.name) LIKE '%' || upper(:name) || '%'))"
						+ " AND (:code IS NULL OR (:code IS NOT NULL AND up.code LIKE '%' || :code || '%'))"
					+ ")"	
//					+ " OR "
//					+ "("
//						+ ":searchq IS NULL AND "
//						+ "("
//						+ "(:name IS NULL OR (:name IS NOT NULL AND upper(up.name) LIKE '%' || upper(:name) || '%'))"
//						+ " AND (:code IS NULL OR (:code IS NOT NULL AND up.code LIKE '%' || :code || '%'))"
//						+ ")"
//					+ ") "
				+ ") "
			)
	public Page<ExceptionType> searchExceptionType(
			@Param("name")String name,
			@Param("code")String code,
			Pageable pageable);

	Optional<ExceptionType> findByIdAndNameAndStatus(Long id, String name, CommonStatus status);

}
