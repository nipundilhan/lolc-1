package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.RiskTemplate;
import com.fusionx.lending.origination.domain.RiskTemplateDetail;
import com.fusionx.lending.origination.domain.RiskTemplateDetailVariance;
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
public interface RiskTemplateDetailVarianceRepository extends JpaRepository<RiskTemplateDetailVariance, Long>{
	
	public List<RiskTemplateDetailVariance> findByStatus(String status);

	public List<RiskTemplateDetailVariance> findByRiskTemplateDetailsId(Long templateId);
	
	public Optional<RiskTemplateDetailVariance> findByIdAndStatus(Long id, String status);
	
	
}
