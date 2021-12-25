package com.fusionx.lending.product.repository;

/**
 * Product Group
 * @author 	Venuki
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019   FX-2879        FX-6532    Venuki      Created
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

import com.fusionx.lending.product.domain.ProductGroup;
import com.fusionx.lending.product.enums.CommonStatus;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long>{
	
	List<ProductGroup> findByStatus(CommonStatus status);	

	Optional<ProductGroup> findByIdAndStatus(Long id, CommonStatus status);

	Optional<ProductGroup> findByCode(String code);
	
	Optional<ProductGroup> findByCodeAndStatus(String code, CommonStatus status);
		
	Optional<ProductGroup> findByCodeAndId(String code, Long id);

	@Query("SELECT up FROM ProductGroup up WHERE "
			+ "("
				+ ":searchq IS NOT NULL AND "
					+ "("
						+ "(upper(up.name) LIKE '%' || upper(:searchq) || '%') "
						+ " OR (upper(up.code) LIKE '%' || upper(:searchq) || '%') "
						+ " OR (upper(up.status) LIKE '%' || upper(:searchq) || '%') "
					+ ") "
					+ " AND "
					+ "("
						+ "(:name IS NULL OR (:name IS NOT NULL AND upper(up.name) LIKE '%' || upper(:name) || '%'))"
						+ " AND (:code IS NULL OR (:code IS NOT NULL AND up.code LIKE '%' || :code || '%'))"
						+ " AND (:status IS NULL OR (:status IS NOT NULL AND up.status LIKE '%' || :status || '%'))"
					+ ")"	
					+ " OR "
					+ "("
						+ ":searchq IS NULL AND "
						+ "("
						+ "(:name IS NULL OR (:name IS NOT NULL AND upper(up.name) LIKE '%' || upper(:name) || '%'))"
						+ " AND (:code IS NULL OR (:code IS NOT NULL AND up.code LIKE '%' || :code || '%'))"
						+ " AND (:status IS NULL OR (:status IS NOT NULL AND up.status LIKE '%' || :status || '%'))"
						+ ")"
					+ ") "
				+ ") "
			)	
	public Page<ProductGroup> searchProductGroupList(@Param("searchq")String searchq, 
			@Param("name")String name,
			@Param("code")String code,
			@Param("status")String status,
			Pageable pageable);
	
}
