package com.fusionx.lending.product.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.DocumentCheckListDetails;

/**
* Document Check list Details Repository
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   12-07-2021      		            	Dilhan     Created
*    
********************************************************************************************************
*/
@Repository
public interface DocumentCheckListDetailsRepository extends JpaRepository<DocumentCheckListDetails, Long>{

	List<DocumentCheckListDetails> findByDocumentCheckListId(Long id);
	
	boolean existsById(Long id);

}
