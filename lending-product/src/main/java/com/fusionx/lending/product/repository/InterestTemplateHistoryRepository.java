package com.fusionx.lending.product.repository;

/**
 * InterestTemplate
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

import com.fusionx.lending.product.domain.InterestTemplateHistory;

@Repository
public interface InterestTemplateHistoryRepository extends JpaRepository<InterestTemplateHistory, Long> {

}
