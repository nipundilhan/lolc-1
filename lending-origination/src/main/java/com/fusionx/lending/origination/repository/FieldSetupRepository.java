package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FieldSetup;
import com.fusionx.lending.origination.domain.RiskGrading;
/**
 * Credit risk parameter template field setup Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021      		     			SewwandiH      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface FieldSetupRepository extends JpaRepository<FieldSetup, Long>{
	
	public List<FieldSetup> findByStatus(String status);

	public List<FieldSetup> findByFieldNameContaining(String fieldName);

	public Optional<FieldSetup> findByFieldId(String fieldId);

	public Optional<FieldSetup> findByFieldIdAndIdNotIn(String fieldId, Long id);

	public Boolean existsByFieldIdAndStatus(String fieldId, String status);
	
	public Optional<FieldSetup> findByIdAndStatus(Long id, String status);
	
	public Boolean existsByFieldIdAndFieldName(String fieldId, String fieldName);
	
	public Boolean existsByFieldName(String fieldName);
	
	public Boolean existsByFieldId(String fieldId);
	
	public Optional<FieldSetup> findByFieldNameAndIdNotIn(String fieldName, Long id);
	
	public Optional<FieldSetup> findByFieldIdAndFieldNameAndIdNotIn(String fieldId, String fieldName, Long id);


}
