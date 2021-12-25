package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.AdditionalDocument;

/**
 * Additional Document Detail Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6484    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Repository
public interface AdditionalDocumentRepository extends JpaRepository<AdditionalDocument, Long> {
	
	public List<AdditionalDocument> findByStatus(String status);
	
	public List<AdditionalDocument> findByCustomerId(Long customerId);

}
