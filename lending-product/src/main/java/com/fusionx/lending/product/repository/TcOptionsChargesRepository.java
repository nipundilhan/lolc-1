package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.TcOptionsCharges;

/**
 * Tc Options Charges
 * 
 *******************************************************************************************************
 *  ###   Date         Story Point   		Task No    Author       Description
 *------------------------------------------------------------------------------------------------------
 *    1   06-10-2021                       	FXL-994	   Dilhan       Created
 *    
 *******************************************************************************************************
 */
@Repository
public interface TcOptionsChargesRepository extends JpaRepository<TcOptionsCharges, Long>{

	List<TcOptionsCharges> findByTcHeaderId(Long id);
}
