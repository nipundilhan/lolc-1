package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CommonList;

/**
 * Common list Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   18-12-2020      		     FX-5273	MiyuruW      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface CommonListRepository extends JpaRepository<CommonList, Long> {

	public List<CommonList> findByStatus(String status);

	public List<CommonList> findByNameContaining(String name);

	public List<CommonList> findByCode(String code);

	public List<CommonList> findByReferenceCode(String referenceCode);

	public Boolean existsByCodeAndReferenceCodeAndStatus(String code, String referenceCode, String status);

	public Optional<CommonList> findByCodeAndReferenceCodeAndStatusAndIdNotIn(String code, String referenceCode, String status, Long id);

	public Optional<CommonList> findByIdAndNameAndReferenceCodeAndStatus(Long id, String name, String referenceCode, String status);

	public Optional<CommonList> findByReferenceCodeAndNameAndStatus(String refeCode, String internalCribStatus,String status);
	
	public Optional<CommonList> findByIdAndNameAndStatus(Long id, String name,String status);
	
	
}
