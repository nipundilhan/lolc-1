package com.fusionx.lending.product.repository;

/**
 * Tc Structured Detail Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   11-10-2021    		 	FXL-1138	Piyumi       Created
 *    
 ********************************************************************************************************
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.TcStructuredDetail;




@Repository
public interface TcStructuredDetailRepository extends JpaRepository<TcStructuredDetail, Long>{

}
