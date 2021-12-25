package com.fusionx.lending.product.repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Product Service -  Product Notes Repository
 * @author 	Venuki
 * @Dat     07-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-06-2019   FX-2879        FX-6532    Venuki      Created
 *    
 ********************************************************************************************************
 */


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.ProductNotes;


@Repository
public interface ProductNotesRepository extends JpaRepository< ProductNotes, Long> {
	
	Collection< ProductNotes> findByStatus(String status);
	
	Optional< ProductNotes> findById(Long Id);
	
	public Collection< ProductNotes> findByProductId(Long productId);
}
