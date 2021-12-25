package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BusinessType;

/**
 * Business Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-12-2020      		     FX-5269	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface BusinessTypeRepository extends JpaRepository<BusinessType, Long> {

	public List<BusinessType> findByStatus(String status);

	public List<BusinessType> findByNameContaining(String name);

	public Optional<BusinessType> findByCode(String code);

	public Optional<BusinessType> findByCodeAndIdNotIn(String code, Long id);

	public Boolean existsByCodeAndStatus(String code, String status);
	
	@Query("SELECT up FROM BusinessType up WHERE "
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
	public Page<BusinessType> searchBusinessType(@Param("searchq")String searchq, 
			@Param("name")String name,
			@Param("code")String code,
			Pageable pageable);

	public Optional<BusinessType> findByIdAndStatus(Long id, String status);

	Boolean existsByIdAndName(Long id, String name);
}
