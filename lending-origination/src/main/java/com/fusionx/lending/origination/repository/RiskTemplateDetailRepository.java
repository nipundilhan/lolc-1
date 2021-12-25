package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.RiskGradingDetail;
import com.fusionx.lending.origination.domain.RiskMainCriteria;
import com.fusionx.lending.origination.domain.RiskTemplate;
import com.fusionx.lending.origination.domain.RiskTemplateDetail;
/**
 * Risk template  detail Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface RiskTemplateDetailRepository extends JpaRepository<RiskTemplateDetail, Long>{
	
	public List<RiskTemplateDetail> findByStatus(String status);

	public List<RiskTemplateDetail> findByRiskTemplatesId(Long templateId);
	
	public Optional<RiskTemplateDetail> findByIdAndStatus(Long id, String status);
		
	
	@Query("SELECT A "
	+ "FROM RiskTemplateDetail A "
	+ "WHERE A.riskTemplates.id = :templateId "
	+ " order by A.riskParameterTemplates.riskMainCriterias.id DESC")
	public List<RiskTemplateDetail> findByRiskTemplatesOrdrByMainCriteria(@Param("templateId") Long templateId);
	
	
	@Query("SELECT DISTINCT A.riskParameterTemplates.riskMainCriterias "
	+ "FROM RiskTemplateDetail A "
	+ "WHERE A.riskTemplates.id = :templateId ")
	public List<RiskMainCriteria> findByRiskTemplateDistinctRiskMainCriterias(@Param("templateId") Long templateId);
}
