package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.OtherFeeType;
import com.fusionx.lending.product.domain.ServiceAccessChannel;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Other Fee Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6604   	 FX-6509	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface OtherFeeTypeRepository extends JpaRepository<OtherFeeType, Long>{

	Optional <OtherFeeType> findByCode(String code);
	
	List <OtherFeeType> findByNameContaining(String name);
	
	List <OtherFeeType> findByStatus(CommonStatus status);
	
	Optional<OtherFeeType> findByCodeAndIdNotIn(String code, Long id);
	
	public Optional<OtherFeeType> findByIdAndNameAndStatus(Long id,String name,CommonStatus status);
	
	public Optional<OtherFeeType> findByIdAndStatus(Long id,CommonStatus status);
	
}
