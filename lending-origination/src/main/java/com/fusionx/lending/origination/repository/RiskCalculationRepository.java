package com.fusionx.lending.origination.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fusionx.lending.origination.domain.RiskCalculation;

@Repository
public interface RiskCalculationRepository 
extends JpaRepository<RiskCalculation, Long> 
{
	

}
