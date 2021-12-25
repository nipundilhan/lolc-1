package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.domain.RiskGradingDetail;
/**
 * Risk Grading Detail Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface RiskGradingDetailRepository extends JpaRepository<RiskGradingDetail, Long>{

	public Optional<RiskGradingDetail> findByRiskGradingsIdAndId(Long gradingId, Long riskGradingId);
							
	public List<RiskGradingDetail> findByRiskGradingsId(Long gradingId);

	public Optional<RiskGradingDetail> findByIdAndStatus(Long id, String status);

}
