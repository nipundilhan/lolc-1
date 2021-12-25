package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.CoreProductHistory;

/**
 * Core Product History
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-09-2021      		     FXL-795	Dilhan        Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface CoreProductHistoryRepository extends JpaRepository<CoreProductHistory, Long>{

	
}
