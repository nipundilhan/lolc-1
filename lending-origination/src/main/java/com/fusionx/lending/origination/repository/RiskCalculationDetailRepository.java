package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.RiskCalculationDetail;

@Repository
public interface RiskCalculationDetailRepository extends JpaRepository<RiskCalculationDetail, Long> 
{
	
	@Query("SELECT A "
	+ "FROM RiskCalculationDetail A "
	+ "WHERE A.riskCalculation.id = :riskCalculationId "
	+ "AND  A.riskTemplateDetail.riskParameterTemplates.riskMainCriterias.id = :mainCriteriaId ")
	public List<RiskCalculationDetail> findByRiskCalIdAndRiskMainCriteriaId(@Param("riskCalculationId") Long riskCalculationId
			,@Param("mainCriteriaId") Long mainCriteriaId);
}
