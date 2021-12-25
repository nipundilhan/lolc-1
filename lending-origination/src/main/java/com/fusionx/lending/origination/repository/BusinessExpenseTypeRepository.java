package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BusinessExpenseType;
import com.fusionx.lending.origination.domain.IncomeType;


/**
 * Business Expense Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-12-2020      		     FX-5271	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface BusinessExpenseTypeRepository extends JpaRepository<BusinessExpenseType, Long> {

	public List<BusinessExpenseType> findByStatus(String status);

	public List<BusinessExpenseType> findByBusinessTypeId(Long businessId);

	public Boolean existsByBusinessTypeIdAndExpenseTypeIdAndStatus(Long businessId, Long expenseId, String status);

	public Optional<BusinessExpenseType> findByBusinessTypeIdAndExpenseTypeIdAndStatusAndIdNotIn(Long businessId, Long expenseId, String status, Long id);

	@Query("SELECT up FROM BusinessExpenseType up WHERE "
			+ "("
//				+ ":searchq IS NOT NULL AND "
//					+ "("
//						+ "(upper(up.businessType.name) LIKE '%' || upper(:searchq) || '%') "
//						+ " OR (upper(up.businessType.code) LIKE '%' || upper(:searchq) || '%') "
//					+ ") "
//					+ " AND "
					+ "("
						+ "(:businessTypeName IS NULL OR (:businessTypeName IS NOT NULL AND upper(up.businessType.name) LIKE '%' || upper(:businessTypeName) || '%'))"
						+ " AND (:businessTypeCode IS NULL OR (:businessTypeCode IS NOT NULL AND up.businessType.code LIKE '%' || :businessTypeCode || '%'))"
					+ ")"	
//					+ " OR "
//					+ "("
//						+ ":searchq IS NULL AND "
//						+ "("
//						+ "(:businessTypeName IS NULL OR (:businessTypeName IS NOT NULL AND upper(up.businessType.name) LIKE '%' || upper(:businessTypeName) || '%'))"
//						+ " AND (:businessTypeCode IS NULL OR (:businessTypeCode IS NOT NULL AND up.businessType.code LIKE '%' || :businessTypeCode || '%'))"
//						+ ")"
//					+ ") "
				+ ") "
			)
	//public Page<BusinessExpenseType> searchBusinessExpenseType(@Param("searchq")String searchq, 
	public Page<BusinessExpenseType> searchBusinessExpenseType( 
			@Param("businessTypeName")String businessTypeName,
			@Param("businessTypeCode")String businessTypeCode,
			Pageable pageable);

	
	public Optional<BusinessExpenseType> findByBusinessTypeIdAndExpenseTypeId(Long businessTypeId, Long expenseTypeId);

	public Optional<BusinessExpenseType> findByIdAndStatus(Long id, String status);
	
}