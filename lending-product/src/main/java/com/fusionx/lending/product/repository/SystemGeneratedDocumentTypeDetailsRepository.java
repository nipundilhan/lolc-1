package com.fusionx.lending.product.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.SystemGeneratedDocumentTypeDetails;

/**
 * System Generated Document Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-11-2021   FXL-358       FXL-1736   Dilki        Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface SystemGeneratedDocumentTypeDetailsRepository extends JpaRepository<SystemGeneratedDocumentTypeDetails, Long>{

	List<SystemGeneratedDocumentTypeDetails> findBySystemGeneratedDocumentTypeId(Long id);
	
	boolean existsById(Long id);

}
