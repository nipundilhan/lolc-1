package com.fusionx.lending.origination.repository;

/**
 * 	Other Income Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-09-2021   FXL-78  	     FXL-777       Dilki        Created
 *    
 ********************************************************************************************************
*/

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.OtherIncomeDetailDocuments;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface OtherIncomeDocumentsRepository extends JpaRepository<OtherIncomeDetailDocuments, Long> {

	Optional<OtherIncomeDetailDocuments> findByOtherIncomeDetailsIdAndDocumentIdAndStatus(Long salaryIncomeDetailId,
			Long documentId, CommonStatus status);

	Optional<OtherIncomeDetailDocuments> findByOtherIncomeDetailsIdAndDocumentIdAndStatusAndIdNotIn(
			Long salaryIncomeDetailId, Long documentId, CommonStatus status, Long id);

	List<OtherIncomeDetailDocuments> findByOtherIncomeDetailsId(Long salaryIncomeDetailId);

}
