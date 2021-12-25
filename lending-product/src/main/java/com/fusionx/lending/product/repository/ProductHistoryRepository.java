package com.fusionx.lending.product.repository;

/**
 * Product  Service - Product History Repository
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

import com.fusionx.lending.product.domain.ProductHistory;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
	
}
