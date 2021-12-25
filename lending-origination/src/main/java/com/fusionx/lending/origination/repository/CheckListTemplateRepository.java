package com.fusionx.lending.origination.repository;
/**
 * Check List Template
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-75  		 FXL-551 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CheckListTemplate;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface CheckListTemplateRepository extends JpaRepository<CheckListTemplate, Long> {

	public List<CheckListTemplate> findByStatus(CommonStatus status);

	public List<CheckListTemplate> findByNameContaining(String name);

	public Optional<CheckListTemplate> findByCode(String code);

	public Optional<CheckListTemplate> findByCodeAndIdNotIn(String code, Long id);
	
	public Optional<CheckListTemplate> findByIdAndStatus(Long id,CommonStatus status);
	
	public Optional<CheckListTemplate> findByCodeAndStatus(String code, CommonStatus status);

}
