package com.fusionx.lending.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.ServiceAccessChannelHistory;

/**
 * Service Access Channel History Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   07-07-2021      		     			Nipun      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface ServiceAccessChannelHistoryRepository extends JpaRepository<ServiceAccessChannelHistory, Long> {



}