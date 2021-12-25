package com.fusionx.lending.origination.repository;
/**
 * 	Salary Income Documents Repository
 * 
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-09-2021   FXL-115  	 FXL-658       Piyumi       Created
 *    
 ********************************************************************************************************
*/


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.SalaryIncomeDocuments;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface SalaryIncomeDocumentsRepository extends JpaRepository<SalaryIncomeDocuments, Long>{

	Optional<SalaryIncomeDocuments> findBySalaryIncomeDetailsIdAndDocumentIdAndStatus(Long salaryIncomeDetailId, Long documentId,CommonStatus status);

	Optional<SalaryIncomeDocuments> findBySalaryIncomeDetailsIdAndDocumentIdAndStatusAndIdNotIn(Long salaryIncomeDetailId,
			Long documentId, CommonStatus status, Long id);

	List<SalaryIncomeDocuments> findBySalaryIncomeDetailsId(Long salaryIncomeDetailId);


}
