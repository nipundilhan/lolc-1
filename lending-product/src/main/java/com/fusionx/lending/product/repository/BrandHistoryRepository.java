package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.BrandHistory;

/**
 * Brand History Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-06-2021      		     			MenukaJ      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface BrandHistoryRepository extends JpaRepository<BrandHistory, Long> {



}
