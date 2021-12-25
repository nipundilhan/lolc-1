package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.SubProduct;

/**
 * Sub Product Service 
 * @author 	Sanatha
 * @Date    19-JUL-2021
 * 
 ********************************************************************************************************
 *  ###   	Date         	Story Point   	Task No    		Author      	 Description
 *-------------------------------------------------------------------------------------------------------
 *    1   	19-JUL-2021  	FXL-25      	FXL-25   		Sanatha     	 Initial Development.
 *    
 ********************************************************************************************************
 */
@Repository
public interface SubProductRepository extends JpaRepository<SubProduct, Long> {
	
	 List<SubProduct> findByStatus(String status);

	 List<SubProduct> findByNameContaining(String name);

	 Optional<SubProduct> findByCode(String code);

	 Optional<SubProduct> findByCodeAndIdNotIn(String code, Long id);

	 Optional<SubProduct> findByIdAndStatus(Long id, String status);

	 Optional<SubProduct> findByIdAndNameAndStatus(Long id, String name,
			String active);

	List<SubProduct> findByDueDateTemplateId(Long dueDateTemplateId);

	List<SubProduct> findByDueDateTemplateIdNotNull();

	List<SubProduct> findByRiskTemplateId(Long riskTemplateId);

}
