package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

/**
 * Tier ProductSegment Service - Repository
 * @author 	Amal
 * @Date    19-09-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   19-09-2019   FX-1508      FX-1800  Amal      Created
 *    
 ********************************************************************************************************
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.ProductSegment;
import com.fusionx.lending.product.enums.CommonStatus;

@Repository
public interface ProductSegmentRepository extends JpaRepository<ProductSegment, Long> {

	public List<ProductSegment> findByProductId(Long tierBandSetId);
	public List<ProductSegment> findByStatus(CommonStatus status);
	public Optional<ProductSegment> findByProductIdAndSegmentsId(Long productId,Long segmentsId);
    public Boolean existsByProductIdAndSegmentsId(Long productId,Long segmentsId);
	public Boolean existsByProductIdAndSegmentsIdAndIdNotIn(Long productId,Long segmentsId,Long id);
	public Boolean existsByProductIdAndSegmentsIdAndStatus(Long parseLong, Long parseLong2, CommonStatus string);
	public Boolean existsByProductIdAndSegmentsIdAndIdNotInAndStatus(Long id, Long stringToLong, Long id2,
			CommonStatus status);
}
