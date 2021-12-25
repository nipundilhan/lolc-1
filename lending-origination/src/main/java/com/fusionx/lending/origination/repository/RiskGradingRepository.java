package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.RiskGrading;
/**
 * Business Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface RiskGradingRepository extends JpaRepository<RiskGrading, Long>{
	
	public List<RiskGrading> findByStatus(String status);

	public List<RiskGrading> findByNameContaining(String name);

	public Optional<RiskGrading> findByCode(String code);

	public Optional<RiskGrading> findByCodeAndIdNotIn(String code, Long id);

	public Boolean existsByCodeAndStatus(String code, String status);
	
	public Optional<RiskGrading> findByIdAndStatus(Long id, String status);
	
	public Boolean existsByCodeAndName(String code, String name);
	
	public Boolean existsByName(String name);
	
	public Boolean existsByCode(String code);
	
	public Optional<RiskGrading> findByNameAndIdNotIn(String name, Long id);
	
	public Optional<RiskGrading> findByCodeAndNameAndIdNotIn(String code, String name, Long id);
	
	public List<RiskGrading> findAllByBusinessPersonTypeIdAndIndustryTypeIdAndBusinessTypesId(Long personType, Long industryType , Long businessType);

}
