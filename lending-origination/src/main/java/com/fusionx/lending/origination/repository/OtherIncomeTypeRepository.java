package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.OtherIncomeCategory;
import com.fusionx.lending.origination.domain.OtherIncomeType;


/**
 * Other Income Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-12-2020      		     FX-5272	MiyuruW      Created
 *    2   18-08-2021     FXL-639     FXL-537	Piyumi       Modified
 ********************************************************************************************************
 */

@Repository
public interface OtherIncomeTypeRepository extends JpaRepository<OtherIncomeType, Long> {

	 List<OtherIncomeType> findByStatus(String status);

	 List<OtherIncomeType> findByNameContaining(String name);

	 Optional<OtherIncomeType> findByCode(String code);

	 Optional<OtherIncomeType> findByCodeAndIdNotIn(String code, Long id);

	 boolean existsByCodeAndStatus(String code, String status);
	
	@Query("SELECT up FROM OtherIncomeType up WHERE "
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
	 Page<OtherIncomeType> searchOtherIncomeType(@Param("searchq")String searchq, 
			@Param("name")String name,
			@Param("code")String code,
			Pageable pageable);

	 Optional<OtherIncomeType> findByIdAndStatus(Long id, String status);
	 
	 Optional<OtherIncomeType> findByIdAndCodeAndStatus(Long id, String code, String status);
	 
	 List<OtherIncomeType> findByOtherIncomeCategory(OtherIncomeCategory otherIncomeCategory);
	
}