package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ExpenseType;


/**
 * Expense Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-12-2020      		     FX-5270	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {

	public List<ExpenseType> findByStatus(String status);

	public List<ExpenseType> findByNameContaining(String name);

	public Optional<ExpenseType> findByCode(String code);

	public Optional<ExpenseType> findByCodeAndIdNotIn(String code, Long id);

	public Boolean existsByCodeAndStatus(String code, String status);
	
	@Query("SELECT up FROM ExpenseType up WHERE "
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
	public Page<ExpenseType> searchExpenseType(@Param("searchq")String searchq, 
			@Param("name")String name,
			@Param("code")String code,
			Pageable pageable);

	public Optional<ExpenseType> findByIdAndStatus(Long id, String status);

	public Optional<ExpenseType> findByIdAndNameAndStatus(Long id, String name, String status);
}