package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.RiskGrading;
import com.fusionx.lending.origination.domain.RiskGradingDetail;
import com.fusionx.lending.origination.domain.ValuePaireDetail;
/**
 * Value paire detail Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface ValuePaireDetailRepository extends JpaRepository<ValuePaireDetail, Long>{

	public Optional<ValuePaireDetail> findByIdAndStatus(Long id, String status);
	
	public List<ValuePaireDetail> findByMasterValuesId(Long masterValueId);

}
