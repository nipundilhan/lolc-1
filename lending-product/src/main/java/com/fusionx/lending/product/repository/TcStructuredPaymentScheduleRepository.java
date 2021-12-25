package com.fusionx.lending.product.repository;

import java.util.List;

/**
 * Tc Structured Payment Schedule Repository
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

import com.fusionx.lending.product.domain.TcRevolvingPaymentSchedule;
import com.fusionx.lending.product.domain.TcStructuredPaymentSchedule;


@Repository
public interface TcStructuredPaymentScheduleRepository extends JpaRepository<TcStructuredPaymentSchedule, Long>{

	List<TcStructuredPaymentSchedule> findByTcStructuredDetailId(Long tcStructuredDetailId);

}
