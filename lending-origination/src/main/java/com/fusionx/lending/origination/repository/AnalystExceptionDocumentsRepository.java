package com.fusionx.lending.origination.repository;
/**
 * 	Analyst Exception Documents Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-08-2021   FXL-117  	 FXL-543       Piyumi     Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.AnalystExceptionDocuments;
import com.fusionx.lending.origination.enums.CommonStatus;



@Repository
public interface AnalystExceptionDocumentsRepository extends JpaRepository<AnalystExceptionDocuments, Long>{
	
	List<AnalystExceptionDocuments> findByAnalystExceptionDetailId(Long analystExceptionDetailId);
	
	Optional<AnalystExceptionDocuments> findByAnalystExceptionDetailIdAndId(Long analystExceptionDetailId, Long id);
	
	Optional<AnalystExceptionDocuments> findByAnalystExceptionDetailIdAndDocumentIdAndStatus(Long analystExceptionDetailId, Long documentId, CommonStatus status);
	
	Optional<AnalystExceptionDocuments> findByAnalystExceptionDetailIdAndDocumentIdAndStatusAndIdNotIn(Long analystExceptionDetailId, Long documentId, CommonStatus status, Long id);

}
