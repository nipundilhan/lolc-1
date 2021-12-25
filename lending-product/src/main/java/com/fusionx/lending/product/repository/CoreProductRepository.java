package com.fusionx.lending.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.CoreProduct;
import com.fusionx.lending.product.enums.CommonStatus;


/**
 * Core Product Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-09-2021      		     FXL-795	Dilhan        Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface CoreProductRepository extends JpaRepository<CoreProduct, Long>{

	public List<CoreProduct> findByStatus(CommonStatus status);

	public Optional<CoreProduct> findByCode(String code);
	
	public List<CoreProduct> findByDescriptionContaining(String desc);
	
	public Optional<CoreProduct> findById(Long id);
}
