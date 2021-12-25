package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FieldSetup;
import com.fusionx.lending.origination.domain.RiskGradingDetail;
import com.fusionx.lending.origination.domain.RiskParaTempCalFeild;
import com.fusionx.lending.origination.domain.RiskParameterTemplate;

/**
 * Credit risk parameter template calculate field Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface RiskParaTempCalFeildRepository extends JpaRepository<RiskParaTempCalFeild, Long>{

	public List<RiskParaTempCalFeild> findByStatus(String status);
	
	public List<RiskParaTempCalFeild> findByRiskParameterTemplatesId(Long calFieldId);

//	public List<RiskParaTempCalFeild> findByFieldNameContaining(String fieldName);
//
//	public Optional<RiskParaTempCalFeild> findByFieldId(String fieldId);
}
