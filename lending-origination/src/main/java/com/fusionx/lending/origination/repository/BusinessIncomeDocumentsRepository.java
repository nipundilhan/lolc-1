package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

/**
 * Business Income Documents Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   06-09-2021   FXL-115  	 FXL-657       Piyumi       Created
 *    
 ********************************************************************************************************
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.BusinessIncomeDocuments;
import com.fusionx.lending.origination.enums.CommonStatus;



@Repository
public interface BusinessIncomeDocumentsRepository extends JpaRepository<BusinessIncomeDocuments, Long>{

	List<BusinessIncomeDocuments> findByBusinessIncomeDtlId(Long id);

	Optional<BusinessIncomeDocuments> findByBusinessIncomeDtlIdAndDocumentIdAndStatus(Long businessIncomeDtl, Long documentId,
			CommonStatus status);

	Optional<BusinessIncomeDocuments> findByBusinessIncomeDtlIdAndDocumentIdAndStatusAndIdNotIn(Long businessIncomeDtl, Long documentId,
			CommonStatus status, Long id);


}
