package com.fusionx.lending.origination.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.DocumentCheckList;
import com.fusionx.lending.origination.enums.CommonStatus;
/**
 * Document Check List Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-09-2021      		            	Dilhan     Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface DocumentCheckListRepository extends JpaRepository<DocumentCheckList, Long>{
	
	List<DocumentCheckList> findByStatus(CommonStatus status);
	
//	Optional<DocumentCheckList> findByCode(String code);
	
//	List<DocumentCheckList> findByProductNameContaining(String name);
//	
//	List<DocumentCheckList> findBySubProductNameContaining(String name);
//	
//	public List<DocumentCheckList> findByNameContaining(String name);

}
