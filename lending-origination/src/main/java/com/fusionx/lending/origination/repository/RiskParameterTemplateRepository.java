package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.RiskParameterTemplate;

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
public interface RiskParameterTemplateRepository extends JpaRepository<RiskParameterTemplate, Long>{

	public List<RiskParameterTemplate> findByStatus(String status);

	public List<RiskParameterTemplate> findByNameContaining(String name);

	public Optional<RiskParameterTemplate> findByCode(String code);

	public Optional<RiskParameterTemplate> findByCodeAndIdNotIn(String code, Long id);

	public Boolean existsByCodeAndStatus(String code, String status);
	
	public Optional<RiskParameterTemplate> findByIdAndStatus(Long id, String status);
	
	public Boolean existsByCodeAndName(String code, String name);
	
	public Boolean existsByName(String name);
	
	public Boolean existsByCode(String code);
	
	public Optional<RiskParameterTemplate> findByNameAndIdNotIn(String name, Long id);
	
	public Optional<RiskParameterTemplate> findByCodeAndNameAndIdNotIn(String code, String name, Long id);

}
