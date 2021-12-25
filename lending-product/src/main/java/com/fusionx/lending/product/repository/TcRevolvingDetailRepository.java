package com.fusionx.lending.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.TcRevolvingDetail;

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
public interface TcRevolvingDetailRepository extends JpaRepository<TcRevolvingDetail, Long>{

	//Optional<TcRevolvingDetail> findByTcHeaderId(Long id);

}
