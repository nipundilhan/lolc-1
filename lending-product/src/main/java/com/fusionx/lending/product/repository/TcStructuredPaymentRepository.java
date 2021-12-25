package com.fusionx.lending.product.repository;

import java.util.List;

/**
 * Tc Structured Payment Repository
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

import com.fusionx.lending.product.domain.TcStructuredPayment;





@Repository
public interface TcStructuredPaymentRepository extends JpaRepository<TcStructuredPayment, Long>{

	List<TcStructuredPayment> findByTcStructuredDetailId(Long tcStructuredDetailId);

}
