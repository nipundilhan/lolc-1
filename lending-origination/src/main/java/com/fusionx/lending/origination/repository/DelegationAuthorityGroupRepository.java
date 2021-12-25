package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.DelegationAuthorityGroup;


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
public interface DelegationAuthorityGroupRepository extends JpaRepository<DelegationAuthorityGroup, Long> {

	public Page<DelegationAuthorityGroup> findAll(Pageable pageable);

	public List<DelegationAuthorityGroup> findByStatus(String status);

	public List<DelegationAuthorityGroup> findByNameContaining(String name);

	public Optional<DelegationAuthorityGroup> findByCode(String code);

	public Optional<DelegationAuthorityGroup> findByCodeAndIdNotIn(String code, Long id);

	public Boolean existsByCodeAndStatus(String code, String status);
	
	@Query("SELECT up FROM DelegationAuthorityGroup up WHERE "
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
	public Page<DelegationAuthorityGroup> searchDelegationAuthorityGroup(@Param("searchq")String searchq, 
			@Param("name")String name,
			@Param("code")String code,
			Pageable pageable);

	public Optional<DelegationAuthorityGroup> findByIdAndStatus(Long id, String status);
	
	public Boolean existsByDaOrderAndStatus(Long order, String status);
	
	public Optional<DelegationAuthorityGroup> findByDaOrderAndIdNotIn(Long order, Long id);

	public Optional<DelegationAuthorityGroup> findByIdAndNameAndStatus(long parseLong, String daGroup, String string);
	
	
}