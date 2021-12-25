package com.fusionx.lending.product.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.Product;
import com.fusionx.lending.product.domain.ProductGroup;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Product  Service - Product Repository
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


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	List<Product> findByStatus(CommonStatus status);	
	Optional<Product> findByProductCodeAndStatus(String code, CommonStatus status);
	Optional<Product> findById(Long id);
	
	Optional<Product> findByIdAndStatus(Long id, CommonStatus status);
   
    Collection<Product> findByBrandId(Long brandId);
    Collection<Product> findByProductNameContaining(String name);
    
	@Query("SELECT up FROM Product up LEFT JOIN ProductSegment ps ON up.id = ps.product.id"
			+ " WHERE "
			+ "("
				+ ":searchq IS NOT NULL AND "
					+ "("
						+ "(upper(up.productName) LIKE '%' || upper(:searchq) || '%') "
						+ " OR (upper(up.productCode) LIKE '%' || upper(:searchq) || '%') "
						+ " OR (upper(up.status) LIKE '%' || upper(:searchq) || '%') "
					+ ") "
					+ " AND "
					+ "("
						+ "(:name IS NULL OR (:name IS NOT NULL AND upper(up.productName) LIKE '%' || upper(:name) || '%'))"
						+ " AND (:code IS NULL OR (:code IS NOT NULL AND up.productCode LIKE '%' || :code || '%'))"
						+ " AND (:status IS NULL OR (:status IS NOT NULL AND up.status LIKE '%' || :status || '%'))"
					+ ")"	
					+ " OR "
					+ "("
						+ ":searchq IS NULL AND "
						+ "("
						+ "(:name IS NULL OR (:name IS NOT NULL AND upper(up.productName) LIKE '%' || upper(:name) || '%'))"
						+ " AND (:code IS NULL OR (:code IS NOT NULL AND up.productCode LIKE '%' || :code || '%'))"
						+ " AND (:status IS NULL OR (:status IS NOT NULL AND up.status LIKE '%' || :status || '%'))"
						+ ")"
					+ ") "
				+ ")"
			)	
	public Page<Product> searchProductList(@Param("searchq")String searchq, 
			@Param("name")String name,
			@Param("code")String code,
			@Param("status")String status,
			Pageable pageable);

	Optional<Product> findByIdAndProductNameAndStatus(Long id, String name,CommonStatus status);
	
	Optional<Product> findByIdAndProductCodeAndProductNameAndStatus(Long id, String code,String name,CommonStatus status);
}
