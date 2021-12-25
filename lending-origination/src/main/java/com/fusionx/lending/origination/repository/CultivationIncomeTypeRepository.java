package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CultivationIncomeType;
import com.fusionx.lending.origination.enums.CommonStatus;
/**
 * Cultivation Income Type
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-08-2021   FXL-338 		 FXL-533 	Dilki        Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface CultivationIncomeTypeRepository extends JpaRepository<CultivationIncomeType, Long> {

	public List<CultivationIncomeType> findByStatus(CommonStatus status);

	public List<CultivationIncomeType> findByNameContaining(String name);

	public Optional<CultivationIncomeType> findByCode(String code);

	public Optional<CultivationIncomeType> findByCodeAndIdNotIn(String code, Long id);
	
	public Optional<CultivationIncomeType> findByIdAndNameAndStatus(Long id,String name,CommonStatus status);

}
