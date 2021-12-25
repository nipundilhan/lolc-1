package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.DocumentCheckList;
import com.fusionx.lending.product.domain.TcRevolvingPaymentSchedule;

/**
 * Tc Revolving Detail
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   05-10-2021                       	FXL-994	   Dilhan       Created
 *    
 *******************************************************************************************************
 */
@Repository
public interface TcRevolvingPaymentScheduleRepository  extends JpaRepository<TcRevolvingPaymentSchedule, Long>{

	List<TcRevolvingPaymentSchedule> findByTcRevolvingDetailId(Long id);
}
