package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.DueDateTemplates;
import com.fusionx.lending.product.enums.CommonStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Due Date Templates Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-09-2021    FXL-139  	 FXL-926	Piyumi      Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface DueDateTemplatesRepository extends JpaRepository<DueDateTemplates, Long> {

	List<DueDateTemplates> findByStatus(CommonStatus status);

	Optional<DueDateTemplates> findByCode(String code);

	List<DueDateTemplates> findByNameContaining(String name);

	List<DueDateTemplates> findByEffectiveDateBetween(Date from , Date to);

	Optional<DueDateTemplates> findByCodeAndIdNotIn(String code, Long id);

	Optional<DueDateTemplates> findByIdAndStatus(Long id, CommonStatus status);

}
