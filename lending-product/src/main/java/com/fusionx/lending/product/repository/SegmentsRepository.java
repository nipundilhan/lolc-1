package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.Segments;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Segment Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-2880   	 FX-6515	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface SegmentsRepository extends JpaRepository<Segments, Long> {
	
	Optional<Segments> findByCode(String code);
	
	List <Segments> findByStatus(CommonStatus status);
	
	List <Segments> findByName(String name);
	
	Optional<Segments> findByCodeAndIdNotIn(String code, Long id);
	
	Optional<Segments> findByIdAndStatus(Long id, CommonStatus status);

}
