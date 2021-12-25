package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.OtherDetails;

/**
 * Other Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6484    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Repository
public interface OtherDetailsRepository  extends JpaRepository<OtherDetails, Long>{
	
	public List<OtherDetails> findByStatus(String status);
	
	public Optional<OtherDetails> findByLeadInfoId(Long id);

}
