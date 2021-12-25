package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CultivationCategory;
import com.fusionx.lending.origination.domain.ExpenseType;
import com.fusionx.lending.origination.enums.CommonStatus;


/**
 * Cultivation Category Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-12-2020      		     FX-5240	MenukaJ      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface CultivationCategoryRepository extends JpaRepository<CultivationCategory, Long> {

	public Page<CultivationCategory> findAll(Pageable pageable);

	public List<CultivationCategory> findByStatus(CommonStatus commonStatus);

	public List<CultivationCategory> findByNameContaining(String name);

	public Optional<CultivationCategory> findByCode(String code);

	public Optional<CultivationCategory> findByCodeAndIdNotIn(String code, Long id);
	
	public Optional<CultivationCategory> findByIdAndStatus(Long id, String status);

	public Optional<CultivationCategory> findByIdAndNameAndStatus(Long id, String name, CommonStatus active);
	
	public Optional<CultivationCategory> findByIdAndCodeAndStatus(Long id, String name, CommonStatus active);
}