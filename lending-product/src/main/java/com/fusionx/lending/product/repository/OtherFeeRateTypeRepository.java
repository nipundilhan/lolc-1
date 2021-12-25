package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.OtherFeeRateType;
import com.fusionx.lending.product.enums.CommonStatus;

/**
 * Other Fee Rate Type Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-06-2020   FX-6605   	 FX-6510	Senitha      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface OtherFeeRateTypeRepository extends JpaRepository<OtherFeeRateType, Long>{

	Optional<OtherFeeRateType> findByCode(String code);
	
	List <OtherFeeRateType> findByStatus(CommonStatus status);
	
	List <OtherFeeRateType> findByName(String name);
	
	Optional<OtherFeeRateType> findByCodeAndIdNotIn(String code, Long id);
	
	Optional<OtherFeeRateType> findByIdAndStatus(Long id,CommonStatus status);

}
