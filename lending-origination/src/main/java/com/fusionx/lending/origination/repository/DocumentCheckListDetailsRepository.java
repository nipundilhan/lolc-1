package com.fusionx.lending.origination.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.fusionx.lending.origination.domain.DocumentCheckListDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

/**
* Document Check list Details Repository
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   30-09-2021      		            	Dilhan     Created
*    
********************************************************************************************************
*/
@Repository
public interface DocumentCheckListDetailsRepository extends JpaRepository<DocumentCheckListDetails, Long>{

	List<DocumentCheckListDetails> findByDocumentCheckListId(Long id);
	
	Optional<DocumentCheckListDetails> findByDocumentCheckListIdAndDocumentIdAndStatus(Long id, Long docId, CommonStatus status);
	
	Optional<DocumentCheckListDetails> findByDocumentCheckListIdAndDocumentIdAndStatusAndIdNotIn(Long docCheckListId, Long docId, CommonStatus status, Long id);
	

}
