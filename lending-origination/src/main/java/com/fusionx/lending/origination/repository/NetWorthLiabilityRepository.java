package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.NetWorthLiability;

/**
 * Net Worth Liability Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-04-2021   		         FX-6157    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface NetWorthLiabilityRepository extends JpaRepository<NetWorthLiability, Long>{
	
	public List<NetWorthLiability> findByGuarantorId(Long guarantorDetail);
	
	public Optional<NetWorthLiability> findByGuarantorIdAndId(Long guarantorDetail, Long id);
	
}
