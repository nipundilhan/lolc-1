package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.RiskParameterTemplate;
import com.fusionx.lending.origination.domain.RiskTemplate;

/**
 * Credit risk parameter template Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface RiskTemplateRepository extends JpaRepository<RiskTemplate, Long>{

	public List<RiskTemplate> findByStatus(String status);

	public List<RiskTemplate> findByNameContaining(String name);

	public Optional<RiskTemplate> findByCode(String code);

	public Optional<RiskTemplate> findByCodeAndIdNotIn(String code, Long id);

	public Boolean existsByCodeAndStatus(String code, String status);
	
	public Optional<RiskTemplate> findByIdAndStatus(Long id, String status);
	
	public Boolean existsByCodeAndName(String code, String name);
	
	public Boolean existsByName(String name);
	
	public Boolean existsByCode(String code);
	
	public Optional<RiskTemplate> findByNameAndIdNotIn(String name, Long id);
	
	public Optional<RiskTemplate> findByCodeAndNameAndIdNotIn(String code, String name, Long id);

}
