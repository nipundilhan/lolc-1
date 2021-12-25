package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionx.lending.origination.domain.StatementTemplate;

/**
 * Statement Template Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-AUG-2021   FXL-473	      FXL-426    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
public interface StatementTemplateRepository extends JpaRepository<StatementTemplate, Long> {
	
	Optional<StatementTemplate> findByCode(String code);

    List<StatementTemplate> findByNameContaining(String name);

    List<StatementTemplate> findByStatus(String status);

    Boolean existsByCodeAndStatus(String code, String status);

    Optional<StatementTemplate> findByCodeAndIdNotIn(String code, Long id);

}
