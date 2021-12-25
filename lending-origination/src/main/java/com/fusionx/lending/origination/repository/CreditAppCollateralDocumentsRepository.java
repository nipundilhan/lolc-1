package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CreditAppCollateralDocuments;
import com.fusionx.lending.origination.enums.CommonStatus;


/**
 * Credit Appraisel Collateral Documents Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		     	        VenukiR      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface CreditAppCollateralDocumentsRepository extends JpaRepository<CreditAppCollateralDocuments, Long> {

	public List<CreditAppCollateralDocuments> findByStatus(String status);

	public Optional<CreditAppCollateralDocuments> findByIdAndStatus(Long id, String status);

	public List<CreditAppCollateralDocuments> findByCreditAppCollateralDetailId(Long id);

	public Optional<CreditAppCollateralDocuments> findByCreditAppCollateralDetailIdAndDocumentIdAndStatus(
			Long creditAppCollateralDetailId, Long documentId, CommonStatus status);

}