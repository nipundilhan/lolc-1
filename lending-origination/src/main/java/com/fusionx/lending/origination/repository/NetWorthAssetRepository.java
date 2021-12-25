package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.NetWorthAsset;

/**
 * Net Worth Asset Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   22-04-2021   		         FX-6157    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface NetWorthAssetRepository extends JpaRepository<NetWorthAsset, Long>{
	
	public List<NetWorthAsset> findByGuarantorId(Long guarantorDetail);
	
	public Optional<NetWorthAsset> findByGuarantorIdAndId(Long guarantorDetail, Long id);
	
}
