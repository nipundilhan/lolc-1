package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Risk Authorities
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   02-09-2021   FXL-338 		 FXL-682 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.RiskAuthorities;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface RiskAuthoritiesRepository extends JpaRepository<RiskAuthorities, Long> {

	public List<RiskAuthorities> findByStatus(CommonStatus status);

	public List<RiskAuthorities> findByNameContaining(String name);

	public Optional<RiskAuthorities> findByCode(String code);

	public Optional<RiskAuthorities> findByCodeAndIdNotIn(String code, Long id);

}
