package com.fusionx.lending.origination.repository;
/**
 * 	Other Income Category Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-639  	 FXL-535       Piyumi     Created
 *    
 ********************************************************************************************************
*/
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.OtherIncomeCategory;
import com.fusionx.lending.origination.enums.CommonStatus;



@Repository
public interface OtherIncomeCategoryRepository extends JpaRepository<OtherIncomeCategory, Long>{
	
	List<OtherIncomeCategory> findByStatus(CommonStatus status);
	
	Optional<OtherIncomeCategory> findByCode(String code);

	List<OtherIncomeCategory> findByNameContaining(String name);

	Optional<OtherIncomeCategory> findByCodeAndIdNotIn(String code, Long id);

	Optional<OtherIncomeCategory> findByIdAndCodeAndStatus(Long id, String code, CommonStatus status);
	
	Optional<OtherIncomeCategory> findByIdAndNameAndStatus(Long id, String name, CommonStatus status);

}
