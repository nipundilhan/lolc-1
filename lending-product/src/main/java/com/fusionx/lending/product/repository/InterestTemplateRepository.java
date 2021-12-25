package com.fusionx.lending.product.repository;

/**
 * InterestTemplate
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

import com.fusionx.lending.product.domain.InterestTemplate;
import com.fusionx.lending.product.enums.CommonStatus;

@Repository
public interface InterestTemplateRepository extends JpaRepository<InterestTemplate, Long>{
	
	List<InterestTemplate>findByStatus(CommonStatus status);	

	Optional<InterestTemplate>findByIdAndStatus(Long id, CommonStatus status);

	Optional<InterestTemplate>findByCode(String code);
	
	Optional<InterestTemplate>findByCodeAndStatus(String code, CommonStatus status);
		
	Optional<InterestTemplate>findByCodeAndId(String code, Long id);
	
	Optional<InterestTemplate>findByCodeAndIdNotIn(String code, Long id);
	

	@Query("SELECT up FROM InterestTemplate up WHERE "
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
 Page<InterestTemplate>searchInterestTemplateList(@Param("searchq")String searchq, 
			@Param("name")String name,
			@Param("code")String code,
			@Param("status")String status,
			Pageable pageable);

	List<InterestTemplate> findByNameContaining(String name);
	
}
