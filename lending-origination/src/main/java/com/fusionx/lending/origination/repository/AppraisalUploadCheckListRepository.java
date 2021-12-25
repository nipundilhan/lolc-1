package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.AppraisalUploadCheckList;
import com.fusionx.lending.origination.domain.RiskGrading;
/**
 * Business Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-09-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface AppraisalUploadCheckListRepository extends JpaRepository<AppraisalUploadCheckList, Long>{
	
	public List<AppraisalUploadCheckList> findByStatus(String status);
	
	public Optional<AppraisalUploadCheckList> findByIdAndStatus(Long id, String status);

	public List<AppraisalUploadCheckList> findByChecklistTemplatesId(Long checklistTemplatesId);
	
	public Boolean existsByChecklistTemplatesIdAndDocumentId(Long checklistTemplatesId, Long documentId);

	public Boolean existsByChecklistTemplatesIdAndDocumentIdAndIdNotIn(Long checklistTemplatesId, Long documentId, Long id);

	public Boolean existsByChecklistTemplatesIdAndDocumentIdAndStatus(Long checklistTemplatesId, Long documentId, String status);

}
