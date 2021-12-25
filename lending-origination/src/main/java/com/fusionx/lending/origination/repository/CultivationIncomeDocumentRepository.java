package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CultivationIncomeDocuments;
import com.fusionx.lending.origination.domain.SalaryIncomeDocuments;
import com.fusionx.lending.origination.enums.CommonStatus;

/**
 * 	Cultivation Income Documents Repository
 * 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-09-2021   FXL-115  	 FXL-661       Dilhan       Created
 *    
 ********************************************************************************************************
*/
@Repository
public interface CultivationIncomeDocumentRepository extends JpaRepository<CultivationIncomeDocuments, Long> {

	Optional<CultivationIncomeDocuments> findByCultivationIncomeDetailsIdAndDocumentIdAndStatus(
			Long cultivationIncomeDetailId, Long documentId, CommonStatus status);

	Optional<CultivationIncomeDocuments> findByCultivationIncomeDetailsIdAndDocumentIdAndStatusAndIdNotIn(Long salaryIncomeDetailId,
			Long documentId, CommonStatus status, Long id);
	
	List<CultivationIncomeDocuments> findByCultivationIncomeDetailsId(Long cultivationIncomeDetailId);

}
